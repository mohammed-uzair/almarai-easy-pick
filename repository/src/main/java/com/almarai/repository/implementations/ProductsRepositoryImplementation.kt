package com.almarai.repository.implementations

import com.almarai.data.easy_pick_models.product.Product
import com.almarai.easypick.data_source.web.interfaces.WebProductsDataSource
import com.almarai.repository.api.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImplementation
@Inject constructor(private val webProductsDataSource: WebProductsDataSource) :
    ProductsRepository {
    override suspend fun getAllProducts(routeNumber: Int) =
        webProductsDataSource.getAllProducts(routeNumber)
    override suspend fun updateRouteData(routeNumber: Int, products:List<Product>) =
        webProductsDataSource.updateRouteData(routeNumber, products)
}