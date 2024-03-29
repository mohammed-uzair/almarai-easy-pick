package com.almarai.easypick.views.screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.filter.Filters
import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteServiceStatus
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.easypick.R
import com.almarai.easypick.adapters.RoutesAdapter
import com.almarai.easypick.views.custom.search_view.OnBarcodeScanClickListener
import com.almarai.easypick.views.custom.search_view.OnSpeechToTextClickListener
import com.almarai.easypick.databinding.ScreenRouteSelectionBinding
import com.almarai.easypick.extensions.*
import com.almarai.easypick.utils.AlertTones
import com.almarai.easypick.utils.BundleKeys
import com.almarai.easypick.utils.FilterFunnel
import com.almarai.easypick.utils.FilterScreenSource
import com.almarai.easypick.views.utils.alert_dialog.showAlertDialog
import com.almarai.easypick.view_models.RouteSelectionViewModel
import com.almarai.easypick.voice.use_cases.NavigateScreen
import com.almarai.easypick.voice.use_cases.Screen
import com.almarai.machine_learning.LiveBarcodeScanningActivity
import com.almarai.machine_learning.LiveBarcodeScanningActivity.Companion.BARCODE_SCANNER_ACTIVITY_RESULT
import com.almarai.machine_learning.LiveBarcodeScanningActivity.Companion.EXTRA_SCANNED_BARCODE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_search.view.*
import kotlinx.coroutines.*
import java.util.*

@AndroidEntryPoint
class RouteSelectionScreen : Fragment(), SearchView.OnQueryTextListener {
    private val routesViewModel: RouteSelectionViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var screenRouteSelectionBinding: ScreenRouteSelectionBinding
    private lateinit var routes: List<Route>
    private lateinit var adapter: RoutesAdapter

    private lateinit var mSearchView: com.almarai.easypick.views.custom.search_view.SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = bindUIScreen(inflater, container)

    private fun bindUIScreen(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        screenRouteSelectionBinding =
            DataBindingUtil.inflate(inflater, R.layout.screen_route_selection, container, false)
        screenRouteSelectionBinding.apply {
            lifecycleOwner = this@RouteSelectionScreen
            viewModel = this@RouteSelectionScreen.routesViewModel
        }

        setHasOptionsMenu(true)

        return screenRouteSelectionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BARCODE_SCANNER_ACTIVITY_RESULT) {
            handleBarcodeScannedResult(resultCode, data)
        } else if (requestCode == TEXT_TO_SPEECH_ACTIVITY_RESULT) {
            handleSpeechToTextResult(resultCode, data)
        }
    }

    private fun handleSpeechToTextResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""

            if (result.isNotEmpty()) {
                if (result.toLowerCase(Locale.ROOT)
                        .contains(VOICE_START_SERVING_COMMAND.toLowerCase(Locale.ROOT))
                ) {
                    val routeNumber = result.filter { it.isDigit() }

                    //If the above is a valid route number, start serving it
                    try {
                        val route = routes.first { it.number == routeNumber.toInt() }
                        if (adapter.isRouteNotServiced(route.routeStatus)) {
                            adapter.processSelectedRoute(route.number)
                        }
                    } catch (exception: NoSuchElementException) {

                    }
                } else {
                    mSearchView.setText(result)
                }
            }
        }
    }

    private fun handleBarcodeScannedResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result = data?.getStringExtra(EXTRA_SCANNED_BARCODE) ?: ""

            //Set the scanned barcode in the search view
            mSearchView.setText(result)
        }
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_route_selection)

        NavigateScreen.CURRENT_SCREEN = Screen.RouteSelectionScreen

        setHasSearchView(true)

        animateUI()

        observeRoutes()
        observeRoutesStatuses()

        setRecyclerView()

        handleOtherScreenResults()
    }

    private fun handleOtherScreenResults() {
        val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

        handleRouteProcessedFromProductsScreen(savedStateHandle)
        handleFiltersFromFilterScreen(savedStateHandle)
    }

    private fun handleRouteProcessedFromProductsScreen(savedStateHandle: SavedStateHandle?) {
        savedStateHandle?.getLiveData<Pair<Int, RouteStatus>>(BundleKeys.ROUTE_PROCESSED)?.observe(
            viewLifecycleOwner,
            Observer { result ->
                if (result.first != -1) {
                    AlertTones.playTone(true)

                    //Update the user
                    showAlertDialog(
                        R.string.alert_title_route_served,
                        R.string.alert_message_route_served_updated,
                        animationResourceId = R.raw.anim_tick
                    )
                }
            })
    }

    private fun handleFiltersFromFilterScreen(savedStateHandle: SavedStateHandle?) {
        savedStateHandle?.getLiveData<Filters>(BundleKeys.FILTER_MODEL)?.observe(
            viewLifecycleOwner,
            Observer { result ->
                routesViewModel.filtersModel = result
                screenRouteSelectionBinding.executePendingBindings()

                //Filter the list
                FilterFunnel(adapter, result).filterRoutes(routes)

                //Notify the adapter
                adapter.notifyDataSetChanged()

                //The filter list might take some time to filter, so add some lag to it
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(700)
                    routesViewModel.setRouteServiceDetails(adapter.currentList)
                    routesViewModel.isFiltered.value = routes.size != adapter.currentList.size
                }
            })
    }

    private fun observeRoutes() {
        routesViewModel.routes.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Fetching -> showViewStateAlert(
                        Alert.Loading,
                        R.string.fetching_routes
                    )
                    is Result.Success -> showDataUi(result.data)
                    is Result.Error -> showViewStateAlert(Alert.Error, result.exceptionMessage)
                }.exhaustive
            }
        })
    }

    private fun observeRoutesStatuses() {
        routesViewModel.routesServiceStatus.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Success -> updateRoutesStatuses(result.data)
                    else -> {
                    }
                }.exhaustive
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_routes_screen, menu)
        mSearchView =
            (menu.findItem(R.id.menu_route_action_search).actionView as com.almarai.easypick.views.custom.search_view.SearchView)
        addBarcodeScanListenerToSearchView()
        addSpeechToTextListenerToSearchView()
        addTextChangeListenerToSearchView()
    }

    private fun addSpeechToTextListenerToSearchView() {
        mSearchView.onSpeechToTextClickListener = object : OnSpeechToTextClickListener {
            override fun onClick() {
                val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                    )
                }

                startActivityForResult(speechIntent, TEXT_TO_SPEECH_ACTIVITY_RESULT)
            }
        }
    }

    private fun addBarcodeScanListenerToSearchView() {
        mSearchView.onBarcodeScanClickListener = object : OnBarcodeScanClickListener {
            override fun onClick() {
                val intent = Intent(
                    this@RouteSelectionScreen.requireContext(),
                    LiveBarcodeScanningActivity::class.java
                )
                startActivityForResult(intent, BARCODE_SCANNER_ACTIVITY_RESULT)
            }
        }
    }

    private fun addTextChangeListenerToSearchView() {
        mSearchView.search_input_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //Filter the list
                FilterFunnel(adapter, Filters()).searchRoutes(s.toString().trim(), routes)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filterScreen -> {
                val action = RouteSelectionScreenDirections
                    .actionRouteSelectionScreenToFilterScreen(
                        FilterScreenSource.RouteSelectionScreen,
                        routesViewModel.filtersModel
                    )
                navController.navigate(action)
            }
            R.id.menu_route_action_search -> {
                mSearchView.openSearch()
                SearchView(requireContext())

                val searchView =
                    item.actionView as com.almarai.easypick.views.custom.search_view.SearchView
                com.almarai.easypick.views.custom.search_view.SearchView(
                    requireContext(),
                    null
                )
                SearchView(requireContext())
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateRoutesStatuses(list: List<RouteServiceStatus>) {
        if (list.isNotEmpty()) {
            //Update all routes status
            list.forEach { updatedRouteStatus ->
                //val route = routes.first { it.number == updatedRouteStatus.number }
                for ((index, route) in routes.withIndex()) {
                    if (route.number == updatedRouteStatus.number
                        && route.routeStatus != updatedRouteStatus.status
                    ) {
                        route.routeStatus = updatedRouteStatus.status

                        //Notify the adapter for the route status change
                        adapter.notifyItemChanged(index)

                        break
                    }
                }
            }
        }
    }

    private fun showDataUi(list: List<Route>) {
        //If the received data is empty
        if (list.isEmpty()) {
            showViewStateAlert(Alert.NoDataAvailable, R.string.no_data_available_routes)
            return
        }

        //Hide the alert
        hideViewStateAlert()

        routes = list
        adapter.submitList(list)
        //adapter.notifyDataSetChanged()

        routesViewModel.setRouteServiceDetails(list)

        setListFocus()

        routesViewModel.getAllRouteStatus()
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.anim_bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.anim_top_to_bottom)

        screenRouteSelectionBinding.screenRouteSelectionBackgroundImage.startAnimation(topToBottom)

        screenRouteSelectionBinding.screenRouteSelectionServicedLabel.startAnimation(topToBottom)
        screenRouteSelectionBinding.screenRouteSelectionServicedText.startAnimation(topToBottom)
        screenRouteSelectionBinding.screenProductFiltered.startAnimation(topToBottom)
        screenRouteSelectionBinding.screenRouteSelectionServingLabel.startAnimation(topToBottom)
        screenRouteSelectionBinding.screenRouteSelectionServingText.startAnimation(topToBottom)

        screenRouteSelectionBinding.recyclerView.layoutMainRecyclerview.startAnimation(bottomToTop)
    }

    private fun setRecyclerView() {
        val recyclerView = screenRouteSelectionBinding.recyclerView.layoutMainRecyclerview
        recyclerView.animation = null
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = RoutesAdapter(this, routesViewModel)
        recyclerView.adapter = adapter
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        //Show the product details dialog

        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        //Filter the list
        FilterFunnel(adapter, Filters()).searchRoutes(newText, routes)
        return false
    }

    private fun setListFocus() =
        screenRouteSelectionBinding.recyclerView.layoutMainRecyclerview
            .showFocus(lifecycleScope = viewLifecycleOwner.lifecycleScope)

    companion object {
        const val TAG = "RouteSelectionScreen"
        private const val TEXT_TO_SPEECH_ACTIVITY_RESULT = 10020
        private const val VOICE_START_SERVING_COMMAND = "Start Serving"
    }
}
