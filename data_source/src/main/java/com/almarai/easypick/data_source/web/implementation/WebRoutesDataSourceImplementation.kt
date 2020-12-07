package com.almarai.easypick.data_source.web.implementation

import com.almarai.common.machine_learning.translation.OnDeviceTextTranslation.translateText
import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteServiceStatus
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.easypick.data_source.interfaces.RouteDataSource
import com.almarai.easypick.data_source.web.WebService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebRoutesDataSourceImplementation @Inject constructor(private val webService: WebService) :
    RouteDataSource {
    override suspend fun getAllRoutes(): Flow<List<Route>> = flow {
        val routes = webService.routesApi.getAllRoutes()
        if (routes.isNotEmpty()) {
            emit(routes)
        }
    }.onEach {
        for (route in it) {
            //Translate the text for the received routes description
            route.translatedDescription = translateText(route.description)
        }
    }

    override suspend fun getAllRoutesStatus():
            Flow<List<RouteServiceStatus>> = flow {
        while (true) {
            emit(webService.routesApi.getAllRoutesStatus())
            delay(3_000)
        }
    }

    override suspend fun getRouteStatus(routeNumber: Int) =
        webService.routesApi.getRouteStatus(routeNumber)

    override suspend fun updateRouteStatus(routeNumber: Int, status: RouteStatus) =
        webService.routesApi.updateRouteStatus(routeNumber, status)
}