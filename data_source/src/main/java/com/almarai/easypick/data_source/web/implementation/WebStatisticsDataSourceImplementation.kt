package com.almarai.easypick.data_source.web.implementation

import com.almarai.data.easy_pick_models.statistics.Statistics
import com.almarai.easypick.data_source.interfaces.StatisticsDataSource
import com.almarai.easypick.data_source.web.WebService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebStatisticsDataSourceImplementation @Inject constructor(private val webService: WebService) :
    StatisticsDataSource {
    override suspend fun getStatistics(
        depotCode: Int,
        fromDate: Long,
        toDate: Long
    ): StateFlow<Statistics> {
        val flow: MutableStateFlow<Statistics> = MutableStateFlow(Statistics(0, listOf()))

        val stats = webService.statisticsApi.getStatistics(fromDate, toDate)

        flow.value = stats

        return flow
    }
}