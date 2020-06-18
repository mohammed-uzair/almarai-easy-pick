package com.almarai.easypick.data_source.web.implementation

import com.almarai.easypick.data_source.web.WebService
import com.almarai.easypick.data_source.web.interfaces.WebRoutesDataSource

class WebRoutesDataSourceImplementation(private val webService: WebService) :
    WebRoutesDataSource {
    override suspend fun getAllRoutes(
        depotCode: Int,
        salesDate: String,
        routesPreferences: Int
    ) = webService.routesApi.getAllRoutes()
}