package com.almarai.data_source.web.api

import com.almarai.data.easy_pick_models.Statistics
import retrofit2.Call
import retrofit2.http.GET

interface StatisticsApi {
    @GET("sFKTen9b")
    fun getStatistics(): Call<Statistics>
}