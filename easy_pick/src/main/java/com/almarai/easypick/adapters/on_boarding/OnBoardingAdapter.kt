package com.almarai.easypick.adapters.on_boarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ItemOnBoardingBinding
import com.almarai.easypick.utils.on_boarding.OnBoarding
import com.almarai.easypick.utils.on_boarding.OnBoardingCompletedListener
import com.almarai.easypick.utils.on_boarding.OnBoardingScreens

class OnBoardingAdapter(private val onBoardingCompletedListener: OnBoardingCompletedListener) :
    RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {
    private val onBoardingScreens: List<OnBoarding> = OnBoardingScreens

    class ViewHolder(private val itemOnBoardingBinding: ItemOnBoardingBinding) :
        RecyclerView.ViewHolder(itemOnBoardingBinding.root) {
        fun setData(
            onBoarding: OnBoarding?,
            onBoardingCompletedListener: OnBoardingCompletedListener
        ) {
            itemOnBoardingBinding.onBoarding = onBoarding
            itemOnBoardingBinding.onBoardingCompletedListener = onBoardingCompletedListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_on_boarding, parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setData(onBoardingScreens[holder.layoutPosition], onBoardingCompletedListener)

    override fun getItemCount() = onBoardingScreens.size
}