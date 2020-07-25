package com.almarai.easypick.data_source.web.interfaces

import com.almarai.data.easy_pick_models.statistics.Statistics

interface WebStatisticsDataSource {
    suspend fun getStatistics(depotCode: Int, fromDate:Long, toDate:Long): Statistics
}