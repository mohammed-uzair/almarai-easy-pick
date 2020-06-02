package com.almarai.repository.implementations

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Product
import com.almarai.data_source.web.interfaces.WebProductsDataSource
import com.almarai.repository.api.ProductsRepository

class ProductsRepositoryImplementation(private val webProductsDataSource: WebProductsDataSource) :
    ProductsRepository {
    override fun getAllProducts(
        depotCode: Int,
        salesDate: String,
        routeNumber: Int,
        routesPreferences: Int,
        mutableItems: MutableLiveData<List<Product>>
    ) {
        webProductsDataSource.getAllProducts(
            depotCode,
            salesDate,
            routeNumber,
            routesPreferences,
            mutableItems
        )
    }
}