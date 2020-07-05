package com.almarai.easypick.data_source.web.api

import com.almarai.data.easy_pick_models.Statistics
import retrofit2.http.GET
import retrofit2.http.Query

interface StatisticsApi {
    //    @GET("sFKTen9b")
    @GET("Statistics")
    suspend fun getStatistics(
        @Query("fromDate") fromDate: String,
        @Query("toDate") toDate: String
    ): Statistics
}