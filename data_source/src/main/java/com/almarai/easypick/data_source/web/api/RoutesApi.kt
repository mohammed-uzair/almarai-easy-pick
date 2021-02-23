package com.almarai.easypick.data_source.web.api

import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteAccessibility
import com.almarai.data.easy_pick_models.route.RouteServiceStatus
import com.almarai.data.easy_pick_models.route.RouteStatus
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface RoutesApi {
    @GET("routes")
    suspend fun getAllRoutes(): List<Route>

    @GET("routesStatus")
    suspend fun getAllRoutesStatus(): List<RouteServiceStatus>

    @GET("routeStatus")
    suspend fun getRouteStatus(@Query("routeNumber") routeNumber: Int): RouteAccessibility

    @POST("updateRouteStatus")
    suspend fun updateRouteStatus(
        @Query("routeNumber") routeNumber: Int,
        @Body statusCategory: RouteStatus
    )
}