package com.almarai.repository.api

import androidx.annotation.WorkerThread
import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteAccessibility
import com.almarai.data.easy_pick_models.route.RouteServiceStatus
import com.almarai.data.easy_pick_models.route.RouteStatus
import kotlinx.coroutines.flow.Flow

@WorkerThread
interface RoutesRepository {
    suspend fun getAllRoutes(): Flow<List<Route>>
    suspend fun getAllRoutesStatus(): Flow<List<RouteServiceStatus>>
    suspend fun getRouteStatus(routeNumber: Int = 0): RouteAccessibility
    suspend fun updateRouteStatus(routeNumber: Int, statusCategory: RouteStatus)
}