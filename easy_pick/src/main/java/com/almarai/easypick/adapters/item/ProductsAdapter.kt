package com.almarai.easypick.adapters.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Product
import com.almarai.easypick.R
import com.almarai.easypick.utils.APP_SELECTED_LANGUAGE
import com.almarai.easypick.utils.AppLanguage
import com.almarai.easypick.utils.exhaustive

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private var list: List<Product>? = null

    fun setValues(list: List<Product>) {
        this.list = list
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productStatus: ImageView
        val productNumber: TextView
        val productDescription: TextView
        val truckStock: TextView
        val freshLoad: TextView
        val totalStock: TextView

        init {
            productStatus = view.findViewById(R.id.item_item_status_image)
            productNumber = view.findViewById(R.id.item_item_number_text)
            productDescription = view.findViewById(R.id.item_item_description_text)
            truckStock = view.findViewById(R.id.item_item_truck_stock)
            freshLoad = view.findViewById(R.id.item_item_fresh_load)
            totalStock = view.findViewById(R.id.item_item_total_stock)

            view.setOnClickListener {
                Toast.makeText(it.context, "Item Clicked ${productNumber.text}", Toast.LENGTH_SHORT)
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
        val item = list!![holder.bindingAdapterPosition]

//        holder.itemStatus.text = route.itemNumber.toString()
        holder.productNumber.text = item.itemNumber.toString()

        //Check the selected language
        when (APP_SELECTED_LANGUAGE) {
            AppLanguage.Arabic -> holder.productDescription.text = item.itemDescriptionArabic
            else -> holder.productDescription.text = item.itemDescription
        }.exhaustive

        holder.truckStock.text = item.truckStock
        holder.freshLoad.text = item.freshLoad
        holder.totalStock.text = item.totalStock
    }
}