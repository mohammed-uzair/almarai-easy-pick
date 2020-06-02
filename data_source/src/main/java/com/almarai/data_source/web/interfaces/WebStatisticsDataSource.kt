package com.almarai.data_source.web.interfaces

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Statistics

interface WebStatisticsDataSource {
    fun getStatistics(
        depotCode: Int,
        mutableStatistics: MutableLiveData<Statistics>
    )
}