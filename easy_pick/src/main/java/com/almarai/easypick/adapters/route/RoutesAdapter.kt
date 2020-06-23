package com.almarai.easypick.adapters.route

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Route
import com.almarai.data.easy_pick_models.RouteStatus
import com.almarai.easypick.R
import com.almarai.easypick.screens.RouteSelectionScreenDirections
import com.almarai.easypick.utils.APP_SELECTED_LANGUAGE
import com.almarai.easypick.utils.AppLanguage
import com.almarai.easypick.utils.exhaustive

class RoutesAdapter : RecyclerView.Adapter<RoutesAdapter.ViewHolder>() {
    private var list: List<Route>? = null

    fun setValues(list: List<Route>) {
        this.list = list
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val routeStatus: ImageView
        val routeNumber: TextView
        val routeDescription: TextView

        init {
            routeStatus = view.findViewById(R.id.item_route_status_image)
            routeNumber = view.findViewById(R.id.item_route_number_text)
            routeDescription = view.findViewById(R.id.item_route_description_text)

            view.setOnClickListener {
                val action = RouteSelectionScreenDirections
                    .actionRouteSelectionScreenToProductListScreen(
                        routeNumber.text.toString().toInt()
                    )
                view.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_route, parent, false)
        )

    override fun getItemCount() = list!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val route = list!![holder.bindingAdapterPosition]

        holder.routeNumber.text = route.number.toString()

        //Check the selected language
        when (APP_SELECTED_LANGUAGE) {
            AppLanguage.Arabic -> holder.routeDescription.text = route.descriptionArabic
            else -> holder.routeDescription.text = route.description
        }.exhaustive

        when (route.serviceCurrentStatus) {
            is RouteStatus.NotServed -> ImageViewCompat.setImageTintList(
                holder.routeStatus, ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.context, R.color.green)
                )
            )
            is RouteStatus.Serving -> ImageViewCompat.setImageTintList(
                holder.routeStatus, ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.context, R.color.blue)
                )
            )
            is RouteStatus.Served -> ImageViewCompat.setImageTintList(
                holder.routeStatus, ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.context, R.color.red)
                )
            )
        }.exhaustive
    }
}