package com.almarai.easypick.adapters.route

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Route
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ItemRouteBinding
import com.almarai.easypick.screens.RouteSelectionScreenDirections

class RoutesAdapter : RecyclerView.Adapter<RoutesAdapter.ViewHolder>() {
    lateinit var routes: List<Route>

    inner class ViewHolder(private val routeBinding: ItemRouteBinding) :
        RecyclerView.ViewHolder(routeBinding.root) {
        init {
            routeBinding.root.setOnClickListener {
//                val extras = FragmentNavigatorExtras(view to "shared_element_container")

                val action = RouteSelectionScreenDirections
                    .actionRouteSelectionScreenToProductListScreen(
                        routeBinding.itemRouteNumberText.text.toString().toInt()
                    )
                routeBinding.root.findNavController().navigate(action)
            }
        }

        fun setData(route : Route?) {
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

    override fun getItemCount() = routes!!.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.setData(routes?.get(holder.layoutPosition))
}