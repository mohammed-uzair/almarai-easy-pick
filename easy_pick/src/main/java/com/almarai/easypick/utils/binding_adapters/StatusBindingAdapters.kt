package com.almarai.easypick.utils.binding_adapters

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.almarai.data.easy_pick_models.ProductStatus
import com.almarai.data.easy_pick_models.RouteStatus
import com.almarai.easypick.R
import com.almarai.easypick.extensions.exhaustive

@BindingAdapter("routeStatus")
fun ImageView.bindRouteStatus(status: RouteStatus) {
    imageTintList = when (status) {
        is RouteStatus.NotServed -> ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.green)
        )
        is RouteStatus.Serving -> ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.blue)
        )
        is RouteStatus.Served -> ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.red)
        )
    }.exhaustive
}

@BindingAdapter("productStatus")
fun ImageView.bindProductStatus(status: ProductStatus) {
    imageTintList = when (status) {
        is ProductStatus.Picked -> ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.blue)
        )
        is ProductStatus.NotPicked -> ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.green)
        )
    }.exhaustive
}