package com.almarai.repository.implementations

import com.almarai.data.easy_pick_models.product.Product
import com.almarai.easypick.data_source.interfaces.ProductsDataSource
import com.almarai.repository.api.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImplementation
@Inject constructor(private val productsDataSource: ProductsDataSource) :
    ProductsRepository {
    override suspend fun getAllProducts(routeNumber: Int) =
        productsDataSource.getAllProducts(routeNumber)
    override suspend fun updateRouteData(routeNumber: Int, products:List<Product>) =
        productsDataSource.updateRouteData(routeNumber, products)
    override suspend fun discardAllChanges(routeNumber: Int) = productsDataSource.discardAllChanges(routeNumber)
}