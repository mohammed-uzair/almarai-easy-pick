package com.almarai.data.easy_pick_models.product

import com.almarai.data.easy_pick_models.GroupType
import com.almarai.data.easy_pick_models.util.exhaustive

class Product(
    val number: Int = 0,
    val description: String = "NA",
    var translatedDescription: String = description,
    val truckStock: String = "NA",
    val freshLoad: String = "NA",
    var totalStock: String = "NA",
    val group: List<GroupType> = listOf(),
    val upc: Int = 0,
    private val productStatus: com.almarai.data.easy_pick_models.web.enum.ProductStatus = com.almarai.data.easy_pick_models.web.enum.ProductStatus.NotPicked
) {
    var status: ProductStatus = ProductStatus.NotPicked
        get() {
            return when (productStatus.name) {
                com.almarai.data.easy_pick_models.web.enum.ProductStatus.Picked.name -> ProductStatus.Picked
                com.almarai.data.easy_pick_models.web.enum.ProductStatus.NotPicked.name -> ProductStatus.NotPicked
                com.almarai.data.easy_pick_models.web.enum.ProductStatus.PartiallyPicked.name -> ProductStatus.PartiallyPicked
                else -> ProductStatus.NotPicked
            }.exhaustive
        }

    var editedCrates: Int = 0
    var editedPieces: Int = 0
}