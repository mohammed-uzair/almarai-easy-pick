package com.almarai.repository.implementations

import com.almarai.easypick.data_source.firebase.interfaces.FirebaseRoutesDataSource
import com.almarai.easypick.data_source.web.interfaces.WebRoutesDataSource
import com.almarai.repository.api.RoutesRepository

class RoutesRepositoryImplementation(
    private val webRoutesDataSource: WebRoutesDataSource,
    private val firebaseRoutesDataSource: FirebaseRoutesDataSource
) : RoutesRepository {
    override suspend fun getAllRoutes(
        depotCode: Int,
        salesDate: String,
        routesPreferences: Int
    ) = webRoutesDataSource.getAllRoutes(depotCode, salesDate, routesPreferences)
}