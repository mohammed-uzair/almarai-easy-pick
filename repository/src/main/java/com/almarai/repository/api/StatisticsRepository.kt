package com.almarai.repository.api

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Statistics

interface StatisticsRepository {
    fun getStatistics(
        depotCode: Int,
        mutableStatistics: MutableLiveData<Statistics>
    )
}