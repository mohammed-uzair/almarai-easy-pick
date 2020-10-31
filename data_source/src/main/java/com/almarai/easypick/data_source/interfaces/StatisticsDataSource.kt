package com.almarai.easypick.data_source.interfaces

import com.almarai.data.easy_pick_models.statistics.Statistics
import javax.inject.Singleton

@Singleton
interface StatisticsDataSource {
    suspend fun getStatistics(depotCode: Int, fromDate:Long, toDate:Long): Statistics
}