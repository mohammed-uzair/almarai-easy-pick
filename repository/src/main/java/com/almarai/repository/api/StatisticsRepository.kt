package com.almarai.repository.api

import com.almarai.data.easy_pick_models.statistics.Statistics
import kotlinx.coroutines.flow.Flow

interface StatisticsRepository {
    suspend fun getStatistics(depotCode: Int, fromDate:Long, toDate:Long): Flow<Statistics>
}