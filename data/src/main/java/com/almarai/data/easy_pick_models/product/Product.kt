package com.almarai.data.easy_pick_models.product

import android.os.Parcelable
import com.almarai.data.easy_pick_models.GroupType
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Product(
    val number: Int = 0,
    val description: String = "NA",
    var translatedDescription: String = description,
    val truckStock: String = "NA",
    val freshLoad: String = "NA",
    var totalStock: String = "NA",
    val group: List<GroupType> = listOf(),
    val upc: Int = 0
) : Parcelable {
    var productStatus: @RawValue ProductStatus =
        ProductStatus.NotPicked
        get() {
            //Note: Do not remove this below line, this is not a correct warning, will break the code if removed
            if (field == null) field =
                ProductStatus.NotPicked
            return field
        }

    var editedCrates: @RawValue Int = 0
    var editedPieces: @RawValue Int = 0
}