package com.almarai.easypick.screens

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Route
import com.almarai.easypick.R
import com.almarai.easypick.adapters.route.RoutesAdapter
import com.almarai.easypick.view_models.RouteSelectionViewModel
import kotlinx.android.synthetic.main.main_recycler_view.*
import kotlinx.android.synthetic.main.screen_route_selection.*


class RouteSelectionScreen(
//    private val adapter: RoutesAdapter
) : Fragment() {
    //     Lazy Inject ViewModel
    private lateinit var routesViewModel: RouteSelectionViewModel
    private val adapter by lazy { RoutesAdapter() }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        routesViewModel = ViewModelProvider(this).get(RouteSelectionViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_route_selection, container, false)
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

        adapter.setValues(listOf())

        routesViewModel.mutableRoutes.observe(viewLifecycleOwner, Observer { list ->
            //If the list is empty
            if (list.isNotEmpty()) showDataUi(list) else showNoDataUi()
        })

        setRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_routes_screen, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_route_action_filter -> navController.navigate(R.id.action_routeSelectionScreen_to_filterScreen)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDataUi(list: List<Route>) {
//        no_event_found_layout.visibility = View.GONE
//        device_events_layout.visibility = View.VISIBLE

        adapter.setValues(list)
        adapter.notifyDataSetChanged()

        val routeServiceDetails = routesViewModel.getRouteServiceDetails(list)

        requireActivity().title = "Routes ${list.size}"
        screen_route_selection_serviced_text.text = routeServiceDetails.servedRoute.toString()
        screen_route_selection_serving_text.text = routeServiceDetails.servingRoutes.toString()
    }

    private fun showNoDataUi() {
//        no_event_found_layout.visibility = View.VISIBLE
//        device_events_layout.visibility = View.GONE
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
