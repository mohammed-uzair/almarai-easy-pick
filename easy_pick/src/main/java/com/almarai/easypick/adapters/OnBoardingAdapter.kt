package com.almarai.easypick.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ItemOnBoardingBinding
import com.almarai.easypick.views.utils.on_boarding.OnBoarding
import com.almarai.easypick.views.utils.OnItemClickListener

class OnBoardingAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<OnBoarding, OnBoardingAdapter.ViewHolder>(OnBoardingDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.inflateLayout(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(getItem(holder.layoutPosition), onItemClickListener)

    class ViewHolder private constructor(private val itemOnBoardingBinding: ItemOnBoardingBinding) :
        RecyclerView.ViewHolder(itemOnBoardingBinding.root) {
        fun bindData(
            onBoarding: OnBoarding?,
            onItemClickListener: OnItemClickListener
        ) {
            itemOnBoardingBinding.onBoarding = onBoarding
            itemOnBoardingBinding.onBoardingCompletedListener = onItemClickListener
        }

        companion object {
            fun inflateLayout(parent: ViewGroup) =
                ViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_on_boarding, parent,
                        false
                    )
                )
        }
    }

    companion object OnBoardingDiffCallback : DiffUtil.ItemCallback<OnBoarding>() {
        override fun areItemsTheSame(oldItem: OnBoarding, newItem: OnBoarding) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: OnBoarding, newItem: OnBoarding) =
            oldItem == newItem
    }
}