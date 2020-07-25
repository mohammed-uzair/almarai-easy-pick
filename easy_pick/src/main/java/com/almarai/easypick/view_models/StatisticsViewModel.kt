package com.almarai.easypick.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.statistics.Statistics
import com.almarai.data.easy_pick_models.util.ERROR_OCCURRED
import com.almarai.repository.api.StatisticsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatisticsViewModel(private val repository: StatisticsRepository) : ViewModel() {
    private val _statistics = MutableLiveData<Result<Statistics>>()
    val statistics: LiveData<Result<Statistics>> = _statistics
    val physicalPagesSaved: MutableLiveData<String> = MutableLiveData()
    val datePicker: MutableLiveData<String> = MutableLiveData()

    fun getStatistics(fromDate:Long, toDate:Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _statistics.postValue(Result.Fetching)
            try {
                _statistics.postValue(Result.Success(repository.getStatistics(1, fromDate, toDate)))
            } catch (exception: Exception) {
                _statistics.postValue(Result.Error(exception.message ?: ERROR_OCCURRED))
            }
        }
    }
}