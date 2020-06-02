package com.almarai.data_source.web.interfaces

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Product

interface WebProductsDataSource {
    fun getAllProducts(
        depotCode: Int,
        salesDate: String,
        routeNumber: Int,
        routesPreferences: Int = 0,
        mutableItems: MutableLiveData<List<Product>>
    )
}