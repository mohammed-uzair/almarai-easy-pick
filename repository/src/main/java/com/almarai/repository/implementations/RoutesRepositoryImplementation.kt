package com.almarai.repository.implementations

import androidx.annotation.WorkerThread
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.easypick.data_source.interfaces.RouteDataSource
import com.almarai.repository.api.RoutesRepository
import javax.inject.Inject

@WorkerThread
class RoutesRepositoryImplementation @Inject constructor(
    private val routesDataSource: RouteDataSource
) : RoutesRepository {
    override suspend fun getAllRoutes() =
        routesDataSource.getAllRoutes()

    override suspend fun getAllRoutesStatus() =
        routesDataSource.getAllRoutesStatus()

    override suspend fun getRouteStatus(routeNumber: Int) =
        routesDataSource.getRouteStatus(routeNumber)

    override suspend fun updateRouteStatus(routeNumber: Int, statusCategory: RouteStatus) =
        routesDataSource.updateRouteStatus(routeNumber, statusCategory)
}