package com.almarai.repository.api

import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.route.RouteStatus
import kotlinx.coroutines.flow.StateFlow

interface ProductsRepository {
    suspend fun getAllProducts(routeNumber: Int): StateFlow<List<Product>>
    suspend fun updateRouteData(routeNumber: Int, products:List<Product>): RouteStatus
}