package com.almarai.easypick.data_source.web.implementation

import com.almarai.easypick.data_source.web.WebService
import com.almarai.easypick.data_source.web.interfaces.WebStatisticsDataSource

class WebStatisticsDataSourceImplementation(private val webService: WebService) :
    WebStatisticsDataSource {
    override suspend fun getStatistics(depotCode: Int, fromDate: Long, toDate: Long) =
        webService.statisticsApi.getStatistics(fromDate, toDate)
}