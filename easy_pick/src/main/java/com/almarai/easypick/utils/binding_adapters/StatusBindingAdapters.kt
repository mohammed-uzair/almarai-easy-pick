package com.almarai.easypick.utils.binding_adapters

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import com.almarai.data.easy_pick_models.ProductStatus
import com.almarai.data.easy_pick_models.RouteStatus
import com.almarai.easypick.R
import com.almarai.easypick.extensions.exhaustive

@BindingAdapter("routeStatus")
fun ImageView.bindRouteStatus(status: RouteStatus) {
    when (status) {
        is RouteStatus.NotServed -> ImageViewCompat.setImageTintList(
            this, ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.green)
            )
        )
        is RouteStatus.Serving -> ImageViewCompat.setImageTintList(
            this, ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.blue)
            )
        )
        is RouteStatus.Served -> ImageViewCompat.setImageTintList(
            this, ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.red)
            )
        )
    }.exhaustive
}

@BindingAdapter("productStatus")
fun ImageView.bindProductStatus(status: ProductStatus) {
    when (status) {
        is ProductStatus.Picked -> ImageViewCompat.setImageTintList(
            this, ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.blue)
            )
        )
        is ProductStatus.NotPicked -> ImageViewCompat.setImageTintList(
            this, ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.green)
            )
        )
    }.exhaustive
}