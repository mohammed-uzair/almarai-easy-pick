package com.almarai.easypick.adapters.item

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Product
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ItemProductBinding

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private var list: List<Product>? = null

    fun setValues(list: List<Product>) {
        this.list = list
    }

    inner class ViewHolder(private val productBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(productBinding.root) {
        init {
            productBinding.root.setOnClickListener {
                Toast.makeText(
                    it.context,
                    "Product Clicked ${productBinding.itemProductNumberText.text}",
                    Toast.LENGTH_SHORT
                )
                    .show()
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

    override fun getItemCount() = list!!.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setData(list?.get(holder.layoutPosition))
}