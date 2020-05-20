package com.almarai.alm_ui

import androidx.annotation.IntDef

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef
annotation class StyleAttributesType {
    companion object {
        var DEFAULT = 0
        var TEXT_TYPE_ALPHANUMERIC = 1
        var TEXT_TYPE_NUMBER = 2
        var TEXT_TYPE_NUMBER_POSITIVE_ONLY = 3
        var TEXT_TYPE_AMOUNT = 4
        var TEXT_TYPE_AMOUNT_POSITIVE_ONLY = 5
        var TEXT_TYPE_RECYCLER_GRID = 6
        var TEXT_TYPE_QUANTITY = 7
        var TEXT_TYPE_QUANTITY_POSITIVE_ONLY = 8
    }
}