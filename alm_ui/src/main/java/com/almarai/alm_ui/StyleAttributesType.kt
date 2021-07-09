package com.almarai.alm_ui

import androidx.annotation.IntDef

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef
annotation class StyleAttributesType {
    companion object {
        const val DEFAULT = 0
        const val TEXT_TYPE_ALPHANUMERIC = 1
        const val TEXT_TYPE_NUMBER = 2
        const val TEXT_TYPE_NUMBER_POSITIVE_ONLY = 3
        const val TEXT_TYPE_AMOUNT = 4
        const val TEXT_TYPE_AMOUNT_POSITIVE_ONLY = 5
        const val TEXT_TYPE_RECYCLER_GRID = 6
        const val TEXT_TYPE_QUANTITY = 7
        const val TEXT_TYPE_QUANTITY_POSITIVE_ONLY = 8
    }
}