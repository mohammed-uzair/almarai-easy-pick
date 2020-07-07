package com.almarai.easypick.utils

import androidx.annotation.StringDef

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@StringDef
annotation class BundleKeys {
    companion object {
        const val ROUTE_PROCESSED = "ROUTE_PROCESSED"
        const val FILTER_MODEL = "FILTER_MODEL"
    }
}