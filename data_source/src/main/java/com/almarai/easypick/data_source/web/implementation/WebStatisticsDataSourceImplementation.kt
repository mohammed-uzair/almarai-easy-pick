package com.almarai.easypick.data_source.web.implementation

import com.almarai.easypick.data_source.interfaces.StatisticsDataSource
import com.almarai.easypick.data_source.web.WebService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebStatisticsDataSourceImplementation @Inject constructor(private val webService: WebService) :
    StatisticsDataSource {
    override suspend fun getStatistics(depotCode: Int, fromDate: Long, toDate: Long) =
        webService.statisticsApi.getStatistics(fromDate, toDate)
}