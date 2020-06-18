package com.almarai.easypick.data_source.web.interfaces

import com.almarai.data.easy_pick_models.Route

interface WebRoutesDataSource {
    suspend fun getAllRoutes(
        depotCode: Int,
        salesDate: String,
        routesPreferences: Int = 0
    ): List<Route>
}