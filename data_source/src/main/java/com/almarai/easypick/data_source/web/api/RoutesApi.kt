package com.almarai.easypick.data_source.web.api

import com.almarai.data.easy_pick_models.Route
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface RoutesApi {
//    @GET("B48qYZYN")
    @GET("routes")
    suspend fun getAllRoutes(): List<Route>
}