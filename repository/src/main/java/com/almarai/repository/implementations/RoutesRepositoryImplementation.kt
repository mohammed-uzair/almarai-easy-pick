package com.almarai.repository.implementations

import com.almarai.data.easy_pick_models.route.RouteServiceStatus
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.easypick.data_source.firebase.interfaces.FirebaseRoutesDataSource
import com.almarai.easypick.data_source.web.interfaces.WebRoutesDataSource
import com.almarai.repository.api.RoutesRepository

class RoutesRepositoryImplementation(
    private val webRoutesDataSource: WebRoutesDataSource,
    private val firebaseRoutesDataSource: FirebaseRoutesDataSource
) : RoutesRepository {
    override suspend fun getAllRoutes() = webRoutesDataSource.getAllRoutes()

    override suspend fun getAllRoutesStatus() =
        webRoutesDataSource.getAllRoutesStatus()

    override suspend fun getRouteStatus(routeNumber: Int) =
        webRoutesDataSource.getRouteStatus(routeNumber)

    override suspend fun updateRouteStatus(routeNumber: Int, status: RouteStatus) =
        webRoutesDataSource.updateRouteStatus(routeNumber, status)
}