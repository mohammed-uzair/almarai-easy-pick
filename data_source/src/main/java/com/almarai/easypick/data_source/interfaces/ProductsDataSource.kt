package com.almarai.easypick.data_source.interfaces

import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.route.RouteStatus
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Singleton
interface ProductsDataSource {
    suspend fun getAllProducts(routeNumber: Int) : StateFlow<List<Product>>
    suspend fun updateRouteData(routeNumber: Int, products: List<Product>): RouteStatus
}