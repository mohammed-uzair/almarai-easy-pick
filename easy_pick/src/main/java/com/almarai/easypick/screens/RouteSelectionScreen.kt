package com.almarai.easypick.screens

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.Route
import com.almarai.data.easy_pick_models.RouteStatus
import com.almarai.data.easy_pick_models.filter.Filters
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.easypick.R
import com.almarai.easypick.adapters.route.RoutesAdapter
import com.almarai.easypick.databinding.ScreenRouteSelectionBinding
import com.almarai.easypick.extensions.Alert
import com.almarai.easypick.extensions.hideViewStateAlert
import com.almarai.easypick.extensions.showViewStateAlert
import com.almarai.easypick.utils.BundleKeys
import com.almarai.easypick.utils.FilterFunnel
import com.almarai.easypick.utils.FilterScreenSource
import com.almarai.easypick.view_models.RouteSelectionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RouteSelectionScreen : Fragment() {
    private val routesViewModel: RouteSelectionViewModel by viewModel()
    private lateinit var navController: NavController
    private val adapter by lazy { RoutesAdapter() }
    private lateinit var screenRouteSelectionBinding: ScreenRouteSelectionBinding
    private lateinit var routes: List<Route>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        screenRouteSelectionBinding =
            DataBindingUtil.inflate(inflater, R.layout.screen_route_selection, container, false)
        screenRouteSelectionBinding.apply {
            lifecycleOwner = this@RouteSelectionScreen
            viewModel = this@RouteSelectionScreen.routesViewModel
        }

        return screenRouteSelectionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_route_selection)

        setHasOptionsMenu(true)

        animateUI()

        adapter.routes = listOf()

        routesViewModel.routes.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Fetching -> showViewStateAlert(
                        Alert.Loading,
                        getString(R.string.no_data_available)
                    )
                    is Result.Success -> showDataUi(result.data)
                    is Result.Error -> showViewStateAlert(Alert.Error)
                }.exhaustive
            }
        })

        setRecyclerView()

        val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

        savedStateHandle?.getLiveData<Pair<Int, RouteStatus>>(BundleKeys.ROUTE_PROCESSED)?.observe(
            viewLifecycleOwner,
            Observer { result ->
                val position = routesViewModel.updateRouteProcessed(result)

                if (position != RecyclerView.NO_POSITION) adapter.notifyItemChanged(position)
            })

        savedStateHandle?.getLiveData<Filters>(BundleKeys.FILTER_MODEL)?.observe(
            viewLifecycleOwner,
            Observer { result ->
                routesViewModel.filtersModel = result

                //Filter the list
                FilterFunnel(adapter, result).filterRoutes(routes)

                //Notify the adapter
                adapter.notifyDataSetChanged()

                //The filter list might take some time to filter, so add some lag to it
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(700)
                    routesViewModel.setRouteServiceDetails(adapter.routes)
                    routesViewModel.isFiltered.value = routes.size != adapter.routes.size
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_routes_screen, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_route_action_filter -> {
                val action = RouteSelectionScreenDirections
                    .actionRouteSelectionScreenToFilterScreen(
                        FilterScreenSource.RouteSelectionScreenScreen,
                        routesViewModel.filtersModel
                    )
                navController.navigate(action)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDataUi(list: List<Route>) {
        //If the received data is empty
        if (list.isEmpty()) {
            showViewStateAlert(Alert.NoDataAvailable)
            return
        }

        //Hide the alert
        hideViewStateAlert()

        routes = list
        adapter.routes = list
        adapter.notifyDataSetChanged()

        routesViewModel.setRouteServiceDetails(list)
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)

        screenRouteSelectionBinding.screenRouteSelectionBackgroundImage.startAnimation(topToBottom)

        screenRouteSelectionBinding.screenRouteSelectionServicedLabel.startAnimation(topToBottom)
        screenRouteSelectionBinding.screenRouteSelectionServicedText.startAnimation(topToBottom)
        screenRouteSelectionBinding.screenProductFiltered.startAnimation(topToBottom)
        screenRouteSelectionBinding.screenRouteSelectionServingLabel.startAnimation(topToBottom)
        screenRouteSelectionBinding.screenRouteSelectionServingText.startAnimation(topToBottom)

//        screen_routes_header_titles_layout.startAnimation(topToBottom)

        screenRouteSelectionBinding.recyclerView.layoutMainRecyclerview.startAnimation(bottomToTop)
    }

    private fun setRecyclerView() {
        val recyclerView =
            screenRouteSelectionBinding.recyclerView.layoutMainRecyclerview
        recyclerView.animation = null
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
}
