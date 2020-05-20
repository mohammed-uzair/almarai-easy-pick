package com.almarai.easypick.adapters.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Item
import com.almarai.easypick.R

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {
    private var list: List<Item>? = null

    fun setValues(list: List<Item>) {
        this.list = list
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemStatus: ImageView
        val itemNumber: TextView
        val itemDescription: TextView
        val truckStock: TextView
        val freshLoad: TextView
        val totalStock: TextView

        init {
            itemStatus = view.findViewById(R.id.item_item_status_image)
            itemNumber = view.findViewById(R.id.item_item_number_text)
            itemDescription = view.findViewById(R.id.item_item_description_text)
            truckStock = view.findViewById(R.id.item_item_truck_stock)
            freshLoad = view.findViewById(R.id.item_item_fresh_load)
            totalStock = view.findViewById(R.id.item_item_total_stock)

            view.setOnClickListener {
                Toast.makeText(it.context, "Item Clicked ${itemNumber.text}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_item, parent, false)
        )

    override fun getItemCount() = list!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list!![holder.adapterPosition]

//        holder.itemStatus.text = route.itemNumber.toString()
        holder.itemNumber.text = item.itemNumber.toString()
        holder.itemDescription.text = item.itemDescription
        holder.truckStock.text = item.truckStock
        holder.freshLoad.text = item.freshLoad
        holder.totalStock.text = item.totalStock
    }
}