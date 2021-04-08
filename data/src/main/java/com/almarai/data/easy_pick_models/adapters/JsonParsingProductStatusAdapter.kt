package com.almarai.data.easy_pick_models.adapters

import com.almarai.data.easy_pick_models.product.ProductStatus
import com.almarai.data.easy_pick_models.util.exhaustive
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class JsonParsingProductStatusAdapter {
    @ToJson
    fun toJson(productStatus: ProductStatus) = when (productStatus) {
        ProductStatus.NotPicked -> "NotPicked"
        ProductStatus.PartiallyPicked -> "PartiallyPicked"
        ProductStatus.Picked -> "Picked"
    }

    @FromJson
    fun fromJson(productStatus: String?): ProductStatus {
        if (productStatus == null) return ProductStatus.NotPicked

        return when (productStatus) {
            "Picked" -> ProductStatus.Picked
            "NotPicked" -> ProductStatus.NotPicked
            "PartiallyPicked" -> ProductStatus.PartiallyPicked
            else -> ProductStatus.NotPicked
        }.exhaustive
    }
}