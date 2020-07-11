package com.almarai.easypick.data_source.web.implementation

import com.almarai.easypick.data_source.web.WebService
import com.almarai.easypick.data_source.web.interfaces.WebProductsDataSource

class WebProductsDataSourceImplementation(private val webService: WebService) :
    WebProductsDataSource {
    override suspend fun getAllProducts(
        depotCode: Int,
        salesDate: String,
        routeNumber: Int,
        routesPreferences: Int
    ) = webService.productsApi.getAllProducts(routeNumber)
}