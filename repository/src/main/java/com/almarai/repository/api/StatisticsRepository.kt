package com.almarai.repository.api

import com.almarai.data.easy_pick_models.Statistics

interface StatisticsRepository {
    suspend fun getStatistics(depotCode: Int): Statistics
}