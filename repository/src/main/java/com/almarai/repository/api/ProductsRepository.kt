package com.almarai.repository.api

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Product

interface ProductsRepository {
    suspend fun getAllProducts(
        depotCode: Int,
        salesDate: String,
        routeNumber: Int,
        routesPreferences: Int = 0
    ):List<Product>
}