package com.almarai.data.easy_pick_models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Product(
    val id: Int,
    val itemNumber: Int = 0,
    val itemDescription: String = "NA",
    val itemDescriptionArabic: String = "NA",
    val truckStock: String = "NA",
    val freshLoad: String = "NA",
    val totalStock: String = "NA",
    val actualQuantity: Int = 0,
    val editedCrates: Int = 0,
    val editedPieces: Int = 0
) : Parcelable {
    var status: @RawValue ProductStatus = ProductStatus.NotPicked
}

sealed class ProductStatus {
    object Picked : ProductStatus()
    object NotPicked : ProductStatus()
}