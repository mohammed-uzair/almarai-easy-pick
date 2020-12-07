package com.almarai.easypick.data_source.interfaces

import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteAccessibility
import com.almarai.data.easy_pick_models.route.RouteServiceStatus
import com.almarai.data.easy_pick_models.route.RouteStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Singleton
interface RouteDataSource {
    suspend fun getAllRoutes(): Flow<List<Route>>
    suspend fun getAllRoutesStatus(): Flow<List<RouteServiceStatus>>
    suspend fun getRouteStatus(routeNumber: Int): RouteAccessibility
    suspend fun updateRouteStatus(routeNumber: Int, status: RouteStatus)
}