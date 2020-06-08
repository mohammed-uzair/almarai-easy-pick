package com.almarai.easypick.adapters.route

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Route
import com.almarai.easypick.R
import com.almarai.easypick.utils.APP_SELECTED_LANGUAGE
import com.almarai.easypick.utils.AppLanguage

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
                Toast.makeText(it.context, "Route Clicked ${routeNumber.text}", Toast.LENGTH_SHORT)
                    .show()

                Navigation.findNavController(view)
                    .navigate(R.id.action_routeSelectionScreen_to_productListScreen)
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
            AppLanguage.English -> holder.routeDescription.text = route.description
            AppLanguage.Arabic -> holder.routeDescription.text = route.descriptionArabic
        }

        when (route.serviceStatus) {
            0 -> ImageViewCompat.setImageTintList(
                holder.routeStatus, ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.context, R.color.green)
                )
            )
            1 -> ImageViewCompat.setImageTintList(
                holder.routeStatus, ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.context, R.color.blue)
                )
            )
            2 -> ImageViewCompat.setImageTintList(
                holder.routeStatus, ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.context, R.color.red)
                )
            )
        }
    }
}