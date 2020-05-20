package com.almarai.data_source.web

import com.almarai.data.easy_pick_models.Route
import retrofit2.Call
import retrofit2.http.GET

interface RoutesApi {
    @GET("B48qYZYN")
    fun getAllRoutes(): Call<List<Route>>
}