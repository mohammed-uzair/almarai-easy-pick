package com.almarai.easypick.data_source.web.implementation

import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.easypick.data_source.interfaces.RouteDataSource
import com.almarai.easypick.data_source.web.WebService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebRoutesDataSourceImplementation @Inject constructor(private val webService: WebService) :
    RouteDataSource {
    override suspend fun getAllRoutes(): StateFlow<List<Route>> {
        val flow: MutableStateFlow<List<Route>> = MutableStateFlow(listOf())

        val routes = webService.routesApi.getAllRoutes()
        if (routes.isNotEmpty()) {
            flow.value = routes
        }

        return flow
    }

    override suspend fun getAllRoutesStatus() =
        webService.routesApi.getAllRoutesStatus()

    override suspend fun getRouteStatus(routeNumber: Int) =
        webService.routesApi.getRouteStatus(routeNumber)

    override suspend fun updateRouteStatus(routeNumber: Int, status: RouteStatus) =
        webService.routesApi.updateRouteStatus(routeNumber, status)
}