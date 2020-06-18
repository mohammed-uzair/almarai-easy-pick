package com.almarai.easypick.data_source.web.api

import com.almarai.data.easy_pick_models.Statistics
import retrofit2.Call
import retrofit2.http.GET

interface StatisticsApi {
    @GET("sFKTen9b")
    suspend fun getStatistics(): Statistics
}