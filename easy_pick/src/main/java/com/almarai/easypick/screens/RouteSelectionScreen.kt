package com.almarai.easypick.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.Route
import com.almarai.data.easy_pick_models.RouteStatus
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.easypick.R
import com.almarai.easypick.adapters.route.RoutesAdapter
import com.almarai.easypick.utils.Alert
import com.almarai.easypick.utils.FilterScreenSource
import com.almarai.easypick.utils.hideAlert
import com.almarai.easypick.utils.showAlert
import com.almarai.easypick.view_models.RouteSelectionViewModel
import kotlinx.android.synthetic.main.main_recycler_view.*
import kotlinx.android.synthetic.main.screen_route_selection.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RouteSelectionScreen : Fragment(R.layout.screen_route_selection) {
    private val routesViewModel: RouteSelectionViewModel by viewModel()
    private lateinit var navController: NavController
    private val adapter by lazy { RoutesAdapter() }

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

        adapter.setValues(listOf())

        routesViewModel.routes.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Fetching -> showAlert(Alert.Loading)
                    is Result.Success -> showDataUi(result.data)
                    is Result.Error -> showAlert(Alert.Error)
                }.exhaustive
            }
        })

        setRecyclerView()

        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Pair<Int, RouteStatus>>("ROUTE_PROCESSED")
            ?.observe(
                viewLifecycleOwner,
                Observer { result ->
                    routesViewModel.updateRouteProcessed(result)

                    adapter.notifyDataSetChanged()
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
                        FilterScreenSource.RouteSelectionScreenScreen
                    )
                navController.navigate(action)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDataUi(list: List<Route>) {
        //If the received data is empty
        if (list.isEmpty()) {
            showAlert(Alert.NoDataAvailable)
            return
        }

        //Hide the alert
        hideAlert()

        adapter.setValues(list)
        adapter.notifyDataSetChanged()

        val routeServiceDetails = routesViewModel.getRouteServiceDetails(list)

        requireActivity().title = "Routes ${list.size}"
        screen_route_selection_serviced_text.text = routeServiceDetails.servedRoute.toString()
        screen_route_selection_serving_text.text = routeServiceDetails.servingRoutes.toString()
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)

        screen_route_selection_background_image.startAnimation(topToBottom)

        screen_route_selection_serviced_label.startAnimation(topToBottom)
        screen_route_selection_serviced_text.startAnimation(topToBottom)
        screen_route_selection_serving_text.startAnimation(topToBottom)
        screen_route_selection_serving_label.startAnimation(topToBottom)

        screen_routes_header_titles_layout.startAnimation(topToBottom)

        layout_main_recyclerview.startAnimation(bottomToTop)
    }

    private fun setRecyclerView() {
        val recyclerView = layout_main_recyclerview as RecyclerView
        recyclerView.animation = null
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
}
