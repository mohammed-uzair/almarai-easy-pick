package com.almarai.repository.implementations

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Route
import com.almarai.data_source.web.interfaces.WebRoutesDataSource
import com.almarai.repository.api.RoutesRepository

class RoutesRepositoryImplementation(private val webRoutesDataSource: WebRoutesDataSource) :
    RoutesRepository {
    override fun getAllRoutes(
        depotCode: Int,
        salesDate: String,
        routesPreferences: Int,
        mutableRoutes: MutableLiveData<List<Route>>
    ) {
        webRoutesDataSource.getAllRoutes(depotCode, salesDate, routesPreferences, mutableRoutes)
    }
}