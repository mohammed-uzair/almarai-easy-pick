package com.almarai.repository.implementations

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Statistics
import com.almarai.data_source.web.interfaces.WebStatisticsDataSource
import com.almarai.repository.api.StatisticsRepository

class StatisticsRepositoryImplementation(private val webStatisticsDataSource: WebStatisticsDataSource) :
    StatisticsRepository {
    override fun getStatistics(depotCode: Int, mutableStatistics: MutableLiveData<Statistics>) {
        webStatisticsDataSource.getStatistics(
            depotCode,
            mutableStatistics
        )
    }
}