package com.almarai.easypick.utils.on_boarding

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes

data class OnBoarding(
    @RawRes val animationResourceId: Int = -1,
    @DrawableRes val illustrationResourceId: Int = -1,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val isLastScreen: Boolean = false
)