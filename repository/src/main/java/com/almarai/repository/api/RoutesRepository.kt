package com.almarai.repository.api

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Route

interface RoutesRepository {
    fun getAllRoutes(
        depotCode: Int,
        salesDate: String,
        routesPreferences: Int = 0,
        mutableRoutes: MutableLiveData<List<Route>>
    )
}