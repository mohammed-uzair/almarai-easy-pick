package com.almarai.repository.api

import com.almarai.data.easy_pick_models.Route

interface RoutesRepository {
    suspend fun getAllRoutes(
        depotCode: Int,
        salesDate: String,
        routesPreferences: Int = 0
    ): List<Route>
}