package com.almarai.easypick.adapters.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.product.Product
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ItemProductBinding
import com.almarai.easypick.screens.ProductListScreenDirections
import com.almarai.easypick.view_models.ProductListViewModel
import javax.inject.Inject

class ProductsAdapter @Inject constructor() : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    lateinit var products: List<Product>
    lateinit var recyclerView: RecyclerView
    lateinit var productViewModel: ProductListViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product, parent,
                false
            )
        )

    inner class ViewHolder(private val productBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(productBinding.root) {
        fun setData(product: Product?, position: Int) {
            //Set the variables to bind with the view
            productBinding.adapter = this@ProductsAdapter
            productBinding.product = product
            productBinding.position = position

            //Set the animation
            animate(productBinding)

            // Bind focus listener
            productBinding.root.onFocusChangeListener =
                View.OnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        if (!productBinding.itemProductSelector.isVisible) {
                            productBinding.itemProductSelector.visibility = View.VISIBLE

                            val anim = AnimationUtils.loadAnimation(v.context, R.anim.anim_item_focused)
                            productBinding.itemProductSelector.startAnimation(anim)
                        }
                    } else {
                        val anim = AnimationUtils.loadAnimation(v.context, R.anim.anim_item_defocused)
                        productBinding.itemProductSelector.startAnimation(anim)

                        productBinding.itemProductSelector.visibility = View.INVISIBLE
                    }
                }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setData(products[holder.layoutPosition], holder.layoutPosition)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun getItemCount() = products.size

    fun showProductDetailsDialog(view: View, position: Int) {
        try {
            view.findNavController()
                .navigate(
                    ProductListScreenDirections.actionProductListScreenToProductDetailsDialog(
                        SelectedProductPosition = position
                    )
                )
        } catch (e: IllegalArgumentException) {
            val error = e.message
        }
    }

    private fun animate(productBinding: ItemProductBinding) {
        productBinding.itemProductStatusImage.animation =
            AnimationUtils.loadAnimation(
                productBinding.itemProductStatusImage.context,
                R.anim.anim_item_status
            )
        productBinding.itemProductDetailRoot.animation =
            AnimationUtils.loadAnimation(
                productBinding.itemProductDetailRoot.context,
                R.anim.anim_item
            )
    }

    fun setProductScreenViewModel(productViewModel: ProductListViewModel) {
        this.productViewModel = productViewModel
    }
}