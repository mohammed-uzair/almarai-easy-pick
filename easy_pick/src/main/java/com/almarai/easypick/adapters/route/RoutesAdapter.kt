package com.almarai.easypick.adapters.route

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ItemRouteBinding
import com.almarai.easypick.screens.RouteSelectionScreen
import com.almarai.easypick.screens.RouteSelectionScreenDirections
import com.almarai.easypick.utils.alert_dialog.OnPositiveButtonClickListener
import com.almarai.easypick.utils.alert_dialog.hideAlertDialog
import com.almarai.easypick.utils.alert_dialog.showAlertDialog
import com.almarai.easypick.view_models.RouteSelectionViewModel


class RoutesAdapter : RecyclerView.Adapter<RoutesAdapter.ViewHolder>() {
    var routes: List<Route> = listOf()
    lateinit var fragment: RouteSelectionScreen
    lateinit var viewModel: RouteSelectionViewModel

    fun setData(
        routes: List<Route>,
        fragment: RouteSelectionScreen,
        viewModel: RouteSelectionViewModel
    ) {
        this.routes = routes
        this.fragment = fragment
        this.viewModel = viewModel
    }

    inner class ViewHolder(private val routeBinding: ItemRouteBinding) :
        RecyclerView.ViewHolder(routeBinding.root) {
        init {
            routeBinding.root.setOnClickListener {
                checkValidRoute(routeBinding)
            }
        }

        fun bindData(route: Route?) {
            routeBinding.route = route

            //Set the animation
            routeBinding.itemRouteStatusImage.animation =
                AnimationUtils.loadAnimation(
                    routeBinding.itemRouteStatusImage.context,
                    R.anim.list_status
                )
            routeBinding.itemRouteDetailRoot.animation =
                AnimationUtils.loadAnimation(
                    routeBinding.itemRouteDetailRoot.context,
                    R.anim.list_loading
                )

            // bind focus listener
            routeBinding.root.onFocusChangeListener =
                View.OnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        // run scale animation and make it bigger
                        val anim = AnimationUtils.loadAnimation(
                            v.context,
                            R.anim.item_focused
                        )
                        routeBinding.root.startAnimation(anim)
                        anim.fillAfter = true
                    } else {
                        // run scale animation and make it smaller
                        val anim = AnimationUtils.loadAnimation(
                            v.context,
                            R.anim.item_defocused
                        )
                        routeBinding.root.startAnimation(anim)
                        anim.fillAfter = true
                    }
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_route, parent,
                false
            )
        )

    override fun getItemCount() = routes.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(routes[holder.layoutPosition])

    private fun checkValidRoute(routeBinding: ItemRouteBinding) {
        if (isRouteNotServiced(routeBinding.route?.serviceStatus)) {
            //                val extras = FragmentNavigatorExtras(view to "shared_element_container")

            fragment.showAlertDialog(
                R.string.alert_title_serve_route,
                R.string.alert_message_route_serve,
                R.string.alert_button_yes,
                R.string.alert_button_no,
                positiveButtonClickListener = object : OnPositiveButtonClickListener {
                    override fun onClick() {
                        //Check if this routes can be serviced
                        val routeNumber = routeBinding.route?.number
                        if (routeNumber != null) {
                            viewModel.getRouteStatus(routeNumber)
                            observeRouteStatus(routeBinding)
                        }
                    }
                }
            )
        }
    }

    private fun observeRouteStatus(routeBinding: ItemRouteBinding) {
        viewModel.routeAccessibility.observe(
            fragment.viewLifecycleOwner,
            Observer {
                it?.let { result ->
                    fragment.hideAlertDialog()

                    when (result) {
                        is Result.Fetching -> {
                            //Show loading progress
                        }
                        is Result.Success -> {
                            if (result.data.isAccessible) {
                                val action = RouteSelectionScreenDirections
                                    .actionRouteSelectionScreenToProductListScreen(
                                        routeBinding.itemRouteNumberText.text.toString()
                                            .toInt()
                                    )
                                routeBinding.root.findNavController()
                                    .navigate(action)
                            } else {
                                //Alert the user
                                fragment.showAlertDialog(
                                    R.string.error,
                                    R.string.error
                                )

                                Toast.makeText(
                                    fragment.requireContext(),
                                    "Cannot access the route",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        is Result.Error -> {
                            //Alert the user
                            fragment.showAlertDialog(
                                R.string.error,
                                R.string.error
                            )

                            Toast.makeText(
                                fragment.requireContext(),
                                result.exceptionMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }.exhaustive
                }
            })
    }

    private fun isRouteNotServiced(routeStatus: RouteStatus?): Boolean {
        if (routeStatus == null) return false

        return when (routeStatus) {
            RouteStatus.NotServed, RouteStatus.Serving -> true
            RouteStatus.Served -> {
                fragment.showAlertDialog(
                    R.string.alert_title_route_served,
                    R.string.alert_message_route_served,
                    R.string.alert_button_ok
                )

                false
            }
        }
    }
}