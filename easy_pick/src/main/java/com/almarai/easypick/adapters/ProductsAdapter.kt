package com.almarai.easypick.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.product.Product
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ItemProductBinding
import com.almarai.easypick.views.utils.OnItemClickListener
import javax.inject.Inject

const val TAG = "ProductsAdapter"

class ProductsAdapter @Inject constructor(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<Product, ProductsAdapter.ViewHolder>(ProductDiffCallback) {
    lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.bindLayout(parent, onItemClickListener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setData(getItem(holder.layoutPosition))

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    class ViewHolder private constructor(private val productBinding: ItemProductBinding, private val onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(productBinding.root) {
        fun setData(product: Product?) {
            //Set the variables to bind with the view
            productBinding.position = absoluteAdapterPosition
            productBinding.product = product
            productBinding.onItemClickListener = onItemClickListener

            //Set the animation
            animate(productBinding)

            // Bind focus listener
            productBinding.root.onFocusChangeListener =
                View.OnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        if (productBinding.itemProductSelector.visibility != View.VISIBLE) {
                            productBinding.itemProductSelector.visibility = View.VISIBLE

                            val anim =
                                AnimationUtils.loadAnimation(v.context, R.anim.anim_item_focused)
                            productBinding.itemProductSelector.startAnimation(anim)
                        }
                    } else {
                        val anim =
                            AnimationUtils.loadAnimation(v.context, R.anim.anim_item_defocused)
                        productBinding.itemProductSelector.startAnimation(anim)

                        productBinding.itemProductSelector.visibility = View.INVISIBLE
                    }
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

        companion object {
            fun bindLayout(parent: ViewGroup, onItemClickListener: OnItemClickListener) = ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_product, parent,
                    false
                ), onItemClickListener
            )
        }
    }

    companion object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.number == newItem.number

        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }
}