package com.almarai.repository.implementations

import com.almarai.easypick.data_source.web.interfaces.WebProductsDataSource
import com.almarai.repository.api.ProductsRepository

class ProductsRepositoryImplementation(private val webProductsDataSource: WebProductsDataSource) :
    ProductsRepository {
    override suspend fun getAllProducts(
        depotCode: Int,
        salesDate: String,
        routeNumber: Int,
        routesPreferences: Int
    ) =
        webProductsDataSource.getAllProducts(
            depotCode,
            salesDate,
            routeNumber,
            routesPreferences
        )
}