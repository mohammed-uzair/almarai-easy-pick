package com.almarai.repository.api

import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.route.RouteStatus
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getAllProducts(routeNumber: Int): Flow<List<Product>>
    suspend fun updateRouteData(routeNumber: Int, products: List<Product>): RouteStatus
    suspend fun discardAllChanges(routeNumber: Int)
}