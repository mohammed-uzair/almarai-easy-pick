package com.almarai.data.easy_pick_models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Product(
    val id: Int,
    val number: Int = 0,
    val description: String = "NA",
    val descriptionArabic: String = "NA",
    val truckStock: String = "NA",
    val freshLoad: String = "NA",
    val totalStock: String = "NA",
    val actualQuantity: Int = 0,
    val editedCrates: Int = 0,
    val editedPieces: Int = 0,
    val group: List<GroupType>
) : Parcelable {
    var status: @RawValue ProductStatus = ProductStatus.NotPicked
        get() {
            //Note: Do not remove this below line, this is not a correct warning, will break the code if removed
            if (field == null) field = ProductStatus.NotPicked
            return field
        }
}

sealed class ProductStatus {
    object Picked : ProductStatus()
    object NotPicked : ProductStatus()
}