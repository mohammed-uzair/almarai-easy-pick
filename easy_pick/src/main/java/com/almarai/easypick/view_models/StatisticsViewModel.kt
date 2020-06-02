package com.almarai.easypick.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almarai.data.easy_pick_models.Statistics
import com.almarai.repository.api.StatisticsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class StatisticsViewModel : ViewModel(), KoinComponent {
    private val repository: StatisticsRepository by inject()

    val mutableStatistics = MutableLiveData<Statistics>()

    init {
        repository.getStatistics(1, mutableStatistics)
    }
}