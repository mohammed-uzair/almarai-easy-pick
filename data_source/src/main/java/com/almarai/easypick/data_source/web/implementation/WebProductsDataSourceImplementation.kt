package com.almarai.easypick.data_source.web.implementation

import com.almarai.common.machine_learning.translation.OnDeviceTextTranslation
import com.almarai.data.easy_pick_models.product.Product
import com.almarai.easypick.data_source.interfaces.ProductsDataSource
import com.almarai.easypick.data_source.web.WebService
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebProductsDataSourceImplementation @Inject constructor(private val webService: WebService) :
    ProductsDataSource {
    override suspend fun getAllProducts(routeNumber: Int) = flow{
        val products = webService.productsApi.getAllProducts(routeNumber)
        if (products.isNotEmpty()) {
            emit(products)
        }
    }.onEach {
        for (product in it) {
            //Translate the text for the received routes description
            product.translatedDescription = OnDeviceTextTranslation.translateText(product.description)
        }
    }

    override suspend fun updateRouteData(routeNumber: Int, products: List<Product>) =
        webService.productsApi.updateRouteData(routeNumber, products)

    override suspend fun discardAllChanges(routeNumber: Int) {
        webService.productsApi.discardAllChanges(routeNumber)
    }
}