package com.almarai.easypick.data_source.web.implementation

import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.product.Products
import com.almarai.easypick.data_source.web.WebService
import com.almarai.easypick.data_source.web.interfaces.WebProductsDataSource

class WebProductsDataSourceImplementation(private val webService: WebService) :
    WebProductsDataSource {
    override suspend fun getAllProducts(routeNumber: Int) =
        webService.productsApi.getAllProducts(routeNumber)

        override suspend fun updateRouteData(routeNumber: Int, products: List<Product>) =
        webService.productsApi.updateRouteData(routeNumber, Products(products))
}