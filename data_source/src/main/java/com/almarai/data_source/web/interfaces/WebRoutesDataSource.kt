package com.almarai.data_source.web.interfaces

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Route

interface WebRoutesDataSource {
    fun getAllRoutes(
        depotCode: Int,
        salesDate: String,
        routesPreferences: Int = 0,
        mutableRoutes: MutableLiveData<List<Route>>
    )
}