package com.almarai.data.easy_pick_models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Product(
    val id: Int = 0,
    val number: Int = 0,
    val description: String = "NA",
    val descriptionArabic: String = "NA",
    val upc: Int = 0,
    val truckStock: String = "NA",
    val freshLoad: String = "NA",
    var totalStock: String = "NA",
    val actualQuantity: Int = 0,
    val group: List<GroupType> = listOf()
) : Parcelable {
    var status: @RawValue ProductStatus = ProductStatus.NotPicked
        get() {
            //Note: Do not remove this below line, this is not a correct warning, will break the code if removed
            if (field == null) field = ProductStatus.NotPicked
            return field
        }

    var editedCrates: @RawValue Int = 0
    var editedPieces: @RawValue Int = 0
}

sealed class ProductStatus {
    object Picked : ProductStatus()
    object NotPicked : ProductStatus()
}