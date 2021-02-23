package com.almarai.easypick.adapters.route

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.almarai.common.logging.FIREBASE_ANALYTICS
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
import com.google.firebase.analytics.ktx.logEvent

private var selectedItemPosition = 0

class RoutesAdapter : RecyclerView.Adapter<RoutesAdapter.ViewHolder>() {
    private lateinit var binding: ItemRouteBinding

    companion object {
        const val TAG = "RoutesAdapter"
    }

    var routes: List<Route> = listOf()
    lateinit var fragment: RouteSelectionScreen
    lateinit var viewModel: RouteSelectionViewModel
    var selectedRoute = 0

    fun setData(
        routes: List<Route>,
        fragment: RouteSelectionScreen,
        viewModel: RouteSelectionViewModel
    ) {
        selectedItemPosition = 0
        this.fragment = fragment
        this.viewModel = viewModel

        val diffCallback = ListDifferentiators(this.routes, routes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.routes = routes
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val routeBinding: ItemRouteBinding) :
        RecyclerView.ViewHolder(routeBinding.root) {
        init {
            binding = routeBinding

            routeBinding.root.setOnClickListener {
                selectedItemPosition = routes.indexOfFirst {
                    binding = routeBinding
                    it.number == routeBinding.itemRouteNumberText.text.toString().toInt()
                }
                checkValidRoute()
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
            if (route?.number == selectedItemPosition) {
                routeBinding.itemRouteSelector.visibility = View.VISIBLE

                val anim = AnimationUtils.loadAnimation(
                    routeBinding.root.context,
                    R.anim.anim_item_focused
                )
                routeBinding.itemRouteSelector.startAnimation(anim)
            } else {
                val anim =
                    AnimationUtils.loadAnimation(
                        routeBinding.root.context,
                        R.anim.anim_item_defocused
                    )
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

    private fun checkValidRoute() {
        if (isRouteNotServiced(binding.route?.status)) {
            //                val extras = FragmentNavigatorExtras(view to "shared_element_container")

            fragment.showAlertDialog(
                R.string.alert_title_serve_route,
                R.string.alert_message_route_serve,
                R.string.alert_button_yes,
                R.string.alert_button_no,
                positiveButtonClickListener = object : OnPositiveButtonClickListener {
                    override fun onClick() {
                        selectedRoute = binding.route?.number ?: 0

                        processSelectedRoute(selectedRoute)
                    }
                }
            )
        }
    }

    fun processSelectedRoute(routeNumber: Int) {
        //Check if this routes can be serviced
        observeRouteStatus(binding)
        viewModel.getRouteStatus(routeNumber)
        selectedRoute = routeNumber
    }

    /**
     * This method will check for the current route status from the data source for the selected route.
     * If the status is green, it will move to the products screen
     */
    private fun observeRouteStatus(routeBinding: ItemRouteBinding) {
        viewModel.routeAccessibility.removeObservers(fragment.viewLifecycleOwner)
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
                            if (result.data.number == selectedRoute) {
                                viewModel.routeAccessibility.removeObservers(fragment.viewLifecycleOwner)
                                routeStatusReceived(result, routeBinding)
                            } else {
                                //Previous value thrown by live data
                            }
                        }
                        is Result.Error -> {
                            viewModel.routeAccessibility.removeObservers(fragment.viewLifecycleOwner)
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

        //Route is Accessible
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

        fragment.hideAlertDialog()

        //Alert the user
        return fragment.showAlertDialog(
            R.string.alert_title_route_not_accessible,
            R.string.alert_message_route_not_accessible
        )
    }

    private fun routeAccessible(routeBinding: ItemRouteBinding): Any {
        val action = RouteSelectionScreenDirections
            .actionRouteSelectionScreenToProductListScreen(
                selectedRoute
            )
        return try {
            FIREBASE_ANALYTICS?.logEvent("route_selected") {
                param("route_number", selectedRoute.toString())
            }

            //Update this route status
            viewModel.updateRouteStatus(selectedRoute)

            routeBinding.root.findNavController()
                .navigate(action)
        } catch (exception: IllegalArgumentException) {
            //Intentionally left empty, do not show alert, useless
            Log.d(TAG, "Could not get navigation controller")
        }
    }

    fun isRouteNotServiced(routeStatus: RouteStatus?): Boolean {
        if (routeStatus == null) return false

        return when (routeStatus) {
            RouteStatus.NotServed, RouteStatus.Serving, RouteStatus.PartialServed -> true
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

    /**
     * This diff callback informs the Adapter how to compute list differences when new lists arrive.
     */
    internal class ListDifferentiators(
        private val oldList: List<Route>,
        private val newList: List<Route>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].number == newList[newItemPosition].number

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}