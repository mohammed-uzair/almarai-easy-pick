package com.almarai.easypick.data_source.web.interfaces

import com.almarai.data.easy_pick_models.Statistics

interface WebStatisticsDataSource {
    suspend fun getStatistics(depotCode: Int, fromDate:String, toDate:String): Statistics
}