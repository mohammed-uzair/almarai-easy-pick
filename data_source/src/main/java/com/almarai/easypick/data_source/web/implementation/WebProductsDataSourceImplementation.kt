package com.almarai.easypick.data_source.web.implementation

import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.product.Products
import com.almarai.easypick.data_source.interfaces.ProductsDataSource
import com.almarai.easypick.data_source.web.WebService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebProductsDataSourceImplementation @Inject constructor(private val webService: WebService) :
    ProductsDataSource {
    override suspend fun getAllProducts(routeNumber: Int): StateFlow<List<Product>> {
        val flow: MutableStateFlow<List<Product>> = MutableStateFlow(listOf())

        val products = webService.productsApi.getAllProducts(routeNumber)
        if (products.isNotEmpty()) {
            flow.value = products
        }

        return flow
    }

    override suspend fun updateRouteData(routeNumber: Int, products: List<Product>) =
        webService.productsApi.updateRouteData(routeNumber, Products(products))

    override suspend fun discardAllChanges(routeNumber: Int) {
        webService.productsApi.discardAllChanges(routeNumber)
    }
}