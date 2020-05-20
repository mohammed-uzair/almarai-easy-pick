package com.almarai.easypick.screens

import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Route
import com.almarai.easypick.MainActivity
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

//    val viewModel: RouteSelectionViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_route_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        routesViewModel = ViewModelProvider(this).get(RouteSelectionViewModel::class.java)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_route_selection)

        setHasOptionsMenu(true)

        animateUI()
        setToolbarTheme()
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
        return super.onOptionsItemSelected(item)
    }

    private fun showDataUi(list: List<Route>) {
//        no_event_found_layout.visibility = View.GONE
//        device_events_layout.visibility = View.VISIBLE

        adapter.setValues(list)
        adapter.notifyDataSetChanged()

        val routeServiceDetails = routesViewModel.getRouteServiceDetails(list)

        requireActivity().title = "${list.size} Total Routes"
        screen_route_selection_serviced_text.text = routeServiceDetails.servedRoute.toString()
        screen_route_selection_serving_text.text = routeServiceDetails.servingRoutes.toString()
    }

    private fun showNoDataUi() {
//        no_event_found_layout.visibility = View.VISIBLE
//        device_events_layout.visibility = View.GONE
    }

    private fun animateUI() {

    }

    private fun setToolbarTheme() {
        // Get the primary text color of the theme
        val typedValue = TypedValue()
        val theme: Resources.Theme = requireActivity().theme
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)
        val arr = requireActivity().obtainStyledAttributes(
            typedValue.data, intArrayOf(
                android.R.attr.colorPrimary
            )
        )
        val primaryColor = arr.getColor(0, -1)


        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(primaryColor)
        )

        val toolbar: Toolbar =
            (activity as MainActivity).findViewById(R.id.fragment_container_toolbar_included_layout)

        toolbar.setTitleTextColor(resources.getColor(android.R.color.white, theme))

        val upArrow = resources.getDrawable(R.drawable.ic_arrow_back, theme)
        upArrow.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            Color.parseColor(
                "#FFFFFF"
            ), BlendModeCompat.SRC_ATOP
        )

        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(upArrow)

        arr.recycle()

        requireActivity().actionBar?.themedContext?.setTheme(R.style.Theme_Base_ToolbarThemeWhite)
    }

    private fun setRecyclerView() {
        val recyclerView = layout_main_recyclerview as RecyclerView
        recyclerView.animation = null
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
}
