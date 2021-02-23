package com.almarai.data.easy_pick_models.product

sealed class ProductStatus {
    object Picked : ProductStatus()
    object NotPicked : ProductStatus()
    object PartiallyPicked : ProductStatus()
}