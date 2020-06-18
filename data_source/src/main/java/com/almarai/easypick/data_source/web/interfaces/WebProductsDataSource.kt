package com.almarai.easypick.data_source.web.interfaces

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Product

interface WebProductsDataSource {
    suspend fun getAllProducts(
        depotCode: Int,
        salesDate: String,
        routeNumber: Int,
        routesPreferences: Int = 0
    ):List<Product>
}