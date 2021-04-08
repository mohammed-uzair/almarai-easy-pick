package com.almarai.data.easy_pick_models.product

import com.almarai.data.easy_pick_models.Group
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Product(
    val number: Int = 0,
    val description: String = "NA",
    var translatedDescription: String = description,
    val truckStock: String = "NA",
    val freshLoad: String = "NA",
    var totalStock: String = "NA",
    val group: List<Group> = listOf(),
    val upc: Int = 0,
    var productStatus: ProductStatus = ProductStatus.NotPicked
) {
    var editedCrates: Int = 0
    var editedPieces: Int = 0
}