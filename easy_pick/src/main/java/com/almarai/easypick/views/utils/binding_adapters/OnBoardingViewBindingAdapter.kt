package com.almarai.easypick.views.utils.binding_adapters

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

@BindingAdapter("onBoardingIllustrationVisibility")
fun ImageView.bindOnBoardingViewVisibility(@DrawableRes onBoardingViewResource: Int) {
    visibility = if (onBoardingViewResource == -1) View.GONE else View.VISIBLE
}

@BindingAdapter("onBoardingAnimationVisibility")
fun LottieAnimationView.bindOnBoardingViewVisibility(@RawRes onBoardingViewResource: Int) {
    visibility = if (onBoardingViewResource == -1) View.GONE else View.VISIBLE
}

@BindingAdapter("onBoardingIllustration")
fun ImageView.bindOnBoardingIllustrator(@DrawableRes onBoardingViewResource: Int) {
    if (onBoardingViewResource != -1) setImageResource(onBoardingViewResource)
}

@BindingAdapter("onBoardingAnimation")
fun LottieAnimationView.bindOnBoardingAnimation(@RawRes onBoardingViewResource: Int) {
    if (onBoardingViewResource != -1) {
        setAnimation(onBoardingViewResource)
    }
}