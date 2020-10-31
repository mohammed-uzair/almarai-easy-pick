package com.almarai.repository.implementations

import com.almarai.easypick.data_source.interfaces.StatisticsDataSource
import com.almarai.repository.api.StatisticsRepository
import javax.inject.Inject

class StatisticsRepositoryImplementation @Inject constructor(private val statisticsDataSource: StatisticsDataSource) :
    StatisticsRepository {
    override suspend fun getStatistics(depotCode: Int, fromDate: Long, toDate: Long) =
        statisticsDataSource.getStatistics(depotCode, fromDate, toDate)
}