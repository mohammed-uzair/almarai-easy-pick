package com.almarai.repository.implementations

import com.almarai.easypick.data_source.web.interfaces.WebStatisticsDataSource
import com.almarai.repository.api.StatisticsRepository

class StatisticsRepositoryImplementation(private val webStatisticsDataSource: WebStatisticsDataSource) :
    StatisticsRepository {
    override suspend fun getStatistics(depotCode: Int, fromDate: String, toDate: String) =
        webStatisticsDataSource.getStatistics(depotCode, fromDate, toDate)
}