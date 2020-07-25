package com.almarai.easypick.data_source.web.api

import com.almarai.data.easy_pick_models.statistics.Statistics
import retrofit2.http.GET
import retrofit2.http.Query

interface StatisticsApi {
    //    @GET("sFKTen9b")
    @GET("statistics")
    suspend fun getStatistics(
        @Query("fromDate") fromDate: Long,
        @Query("toDate") toDate: Long
    ): Statistics
}