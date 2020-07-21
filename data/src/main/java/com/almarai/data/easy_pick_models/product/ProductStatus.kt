package com.almarai.data.easy_pick_models.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class ProductStatus : Parcelable {
    @Parcelize
    object Picked : ProductStatus()

    @Parcelize
    object NotPicked : ProductStatus()
}