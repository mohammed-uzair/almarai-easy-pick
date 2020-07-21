package com.almarai.repository.api

import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteAccessibility
import com.almarai.data.easy_pick_models.route.RouteServiceStatus
import com.almarai.data.easy_pick_models.route.RouteStatus

interface RoutesRepository {
    suspend fun getAllRoutes(): List<Route>
    suspend fun getAllRoutesStatus(): List<RouteServiceStatus>
    suspend fun getRouteStatus(routeNumber: Int = 0): RouteAccessibility
    suspend fun updateRouteStatus(routeNumber: Int, status: RouteStatus)
}