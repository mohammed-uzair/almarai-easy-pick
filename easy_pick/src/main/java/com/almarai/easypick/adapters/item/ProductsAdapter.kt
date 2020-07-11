package com.almarai.easypick.adapters.item

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Product
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ItemProductBinding

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    lateinit var products: List<Product>

    inner class ViewHolder(private val productBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(productBinding.root) {
        init {
            productBinding.root.setOnClickListener {
                //Load the product details dialog
                it.findNavController()
                    .navigate(R.id.action_productListScreen_to_productDetailsDialog)
            }
        }

        fun setData(product: Product?) {
            productBinding.product = product

            //Set the animation
            productBinding.itemProductStatusImage.animation =
                AnimationUtils.loadAnimation(
                    productBinding.itemProductStatusImage.context,
                    R.anim.list_status
                )
            productBinding.itemProductDetailRoot.animation =
                AnimationUtils.loadAnimation(
                    productBinding.itemProductDetailRoot.context,
                    R.anim.list_loading
                )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product, parent,
                false
            )
        )

    override fun getItemCount() = products.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setData(products[holder.layoutPosition])
}