package com.almarai.easypick.data_source.web.implementation

import com.almarai.data.easy_pick_models.route.RouteServiceStatus
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.easypick.data_source.web.WebService
import com.almarai.easypick.data_source.web.interfaces.WebRoutesDataSource

class WebRoutesDataSourceImplementation(private val webService: WebService) :
    WebRoutesDataSource {
    override suspend fun getAllRoutes() = webService.routesApi.getAllRoutes()
    override suspend fun getAllRoutesStatus() =
        webService.routesApi.getAllRoutesStatus()

    override suspend fun getRouteStatus(routeNumber: Int) =
        webService.routesApi.getRouteStatus(routeNumber)

    override suspend fun updateRouteStatus(routeNumber: Int, status: RouteStatus) =
        webService.routesApi.updateRouteStatus(routeNumber, status)
}