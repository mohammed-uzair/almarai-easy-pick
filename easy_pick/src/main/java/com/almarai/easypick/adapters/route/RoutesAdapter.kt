package com.almarai.easypick.adapters.route

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteAccessibility
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ItemRouteBinding
import com.almarai.easypick.screens.RouteSelectionScreen
import com.almarai.easypick.screens.RouteSelectionScreenDirections
import com.almarai.easypick.utils.AlertTones.playTone
import com.almarai.easypick.utils.alert_dialog.AppAlertDialog
import com.almarai.easypick.utils.alert_dialog.OnPositiveButtonClickListener
import com.almarai.easypick.utils.alert_dialog.hideAlertDialog
import com.almarai.easypick.utils.alert_dialog.showAlertDialog
import com.almarai.easypick.utils.progress.hideProgress
import com.almarai.easypick.utils.progress.showProgress
import com.almarai.easypick.view_models.RouteSelectionViewModel

private var selectedItemPosition = 0

class RoutesAdapter : RecyclerView.Adapter<RoutesAdapter.ViewHolder>() {
    companion object {
        const val TAG = "RoutesAdapter"
    }

    var routes: List<Route> = listOf()
    lateinit var fragment: RouteSelectionScreen
    lateinit var viewModel: RouteSelectionViewModel

    fun setData(
        routes: List<Route>,
        fragment: RouteSelectionScreen,
        viewModel: RouteSelectionViewModel
    ) {
        selectedItemPosition = 0
        this.routes = routes
        this.fragment = fragment
        this.viewModel = viewModel
    }

    inner class ViewHolder(private val routeBinding: ItemRouteBinding) :
        RecyclerView.ViewHolder(routeBinding.root) {
        init {
            routeBinding.root.setOnClickListener {
                selectedItemPosition = routes.indexOfFirst {
                    it.number == routeBinding.itemRouteNumberText.text.toString().toInt()
                }
                checkValidRoute(routeBinding)
            }
        }

        fun bindData(route: Route?) {
            routeBinding.route = route

            //Set the animation
            routeBinding.itemRouteStatusImage.animation =
                AnimationUtils.loadAnimation(
                    routeBinding.itemRouteStatusImage.context,
                    R.anim.anim_item_status
                )
            routeBinding.itemRouteDetailRoot.animation =
                AnimationUtils.loadAnimation(
                    routeBinding.itemRouteDetailRoot.context,
                    R.anim.anim_item
                )

            // Bind focus listener
            if(route?.number == selectedItemPosition){
                routeBinding.itemRouteSelector.visibility = View.VISIBLE

                val anim = AnimationUtils.loadAnimation(routeBinding.root.context, R.anim.anim_item_focused)
                routeBinding.itemRouteSelector.startAnimation(anim)
            }else{
                val anim =
                    AnimationUtils.loadAnimation(routeBinding.root.context, R.anim.anim_item_defocused)
                routeBinding.itemRouteSelector.startAnimation(anim)

                routeBinding.itemRouteSelector.visibility = View.GONE
            }

//            routeBinding.root.onFocusChangeListener =
//                View.OnFocusChangeListener { v, hasFocus ->
//                    if (hasFocus) {
//                        routeBinding.itemRouteSelector.visibility = View.VISIBLE
//
//                        val anim = AnimationUtils.loadAnimation(v.context, R.anim.anim_item_focused)
//                        routeBinding.itemRouteSelector.startAnimation(anim)
//                    } else {
//                        val anim =
//                            AnimationUtils.loadAnimation(v.context, R.anim.anim_item_defocused)
//                        routeBinding.itemRouteSelector.startAnimation(anim)
//
//                        routeBinding.itemRouteSelector.visibility = View.GONE
//                    }
//                }
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

    /**
     * This method will check for the current route status from the data source for the selected route.
     * If the status is green, it will move to the products screen
     */
    private fun observeRouteStatus(routeBinding: ItemRouteBinding) {
        viewModel.routeAccessibility.observe(
            fragment.viewLifecycleOwner,
            Observer {
                it?.let { result ->
                    fragment.hideAlertDialog()

                    when (result) {
                        is Result.Fetching -> {
                            //Show loading progress
                            fragment.showProgress(R.string.progress_fetching_route_status)
                        }
                        is Result.Success -> {
                            routeStatusReceived(result, routeBinding)
                        }
                        is Result.Error -> {
                            serverError(result)
                        }
                    }.exhaustive
                }
            })
    }

    private fun serverError(result: Result.Error): AppAlertDialog {
        playTone(false)

        //Alert the user
        return fragment.showAlertDialog(
            R.string.error,
            "${fragment.getString(R.string.server_error)}, ${result.exceptionMessage}"
        )
    }

    private fun routeStatusReceived(
        result: Result.Success<RouteAccessibility>,
        routeBinding: ItemRouteBinding
    ): Any {
        fragment.hideProgress()

        //Route IS Accessible
        return if (result.data.isAccessible) {
            routeAccessible(routeBinding)
        }

        //Route NOT Accessible
        else {
            routeNotAccessible()
        }
    }

    private fun routeNotAccessible(): AppAlertDialog {
        playTone(false)

        //Alert the user
        return fragment.showAlertDialog(
            R.string.alert_title_route_not_accessible,
            R.string.alert_message_route_not_accessible
        )
    }

    private fun routeAccessible(routeBinding: ItemRouteBinding): Any {
        val action = RouteSelectionScreenDirections
            .actionRouteSelectionScreenToProductListScreen(
                routeBinding.itemRouteNumberText.text.toString()
                    .toInt()
            )
        return try {
            routeBinding.root.findNavController()
                .navigate(action)
        } catch (exception: IllegalArgumentException) {
            //Intentionally left empty, do not show alert, useless
            Log.d(TAG, "Could not get navigation controller")
        }
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