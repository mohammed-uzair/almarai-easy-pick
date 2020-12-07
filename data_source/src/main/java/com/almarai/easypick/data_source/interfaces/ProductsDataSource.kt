package com.almarai.easypick.data_source.interfaces

import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.route.RouteStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface ProductsDataSource {
    suspend fun getAllProducts(routeNumber: Int): Flow<List<Product>>
    suspend fun updateRouteData(routeNumber: Int, products: List<Product>): RouteStatus
    suspend fun discardAllChanges(routeNumber: Int)
}