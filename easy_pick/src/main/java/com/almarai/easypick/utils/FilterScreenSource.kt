package com.almarai.easypick.utils

import androidx.annotation.StringDef

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@StringDef
annotation class FilterScreenSource {
    companion object {
        const val RouteSelectionScreen = 1
        const val ProductListScreen = 2
    }
}