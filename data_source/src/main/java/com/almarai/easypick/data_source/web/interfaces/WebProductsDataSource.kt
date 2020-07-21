package com.almarai.easypick.data_source.web.interfaces

import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.route.RouteStatus

interface WebProductsDataSource {
    suspend fun getAllProducts(routeNumber: Int):List<Product>
    suspend fun updateRouteData(routeNumber: Int, products: List<Product>):RouteStatus
}