package com.almarai.easypick.view_models

import android.util.Log
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.business.utils.AppDateTimeFormat
import com.almarai.common.date_time.DateUtil
import com.almarai.data.easy_pick_models.DataConfiguration
import com.almarai.repository.api.ApplicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class DataConfigurationViewModel @Inject constructor(private val applicationRepository: ApplicationRepository) :
    ViewModel() {
    companion object {
        const val TAG = "DataConfigViewModel"
    }

    private var salesDateToday = DateUtil.getCurrentDate(AppDateTimeFormat.formatDDMMYYYY)
    val salesDate: MutableLiveData<String> = MutableLiveData(salesDateToday ?: "0")
    val depotCode: MutableLiveData<String> = MutableLiveData("0")
    val routeGroup: MutableLiveData<String> = MutableLiveData()

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dataConfiguration = applicationRepository.getDataConfiguration()

                salesDate.postValue(
                    if (dataConfiguration.salesDate != null && dataConfiguration.salesDate!!.length > 6) dataConfiguration.salesDate else salesDateToday
                )
                depotCode.postValue(dataConfiguration.depotCode)
                routeGroup.postValue(dataConfiguration.routeGroup)
            } catch (exception: Exception) {
                Log.e(
                    TAG,
                    "Fetching data configuration: Error Occurred",
                    exception
                )
            }
        }
    }

    fun checkAppDataConfigurations() = applicationRepository.checkAppDataIsConfigured()

    fun saveDataConfiguration(routeGroup: String): Boolean {
        if (validateData()) {
            applicationRepository.setDataConfiguration(
                DataConfiguration(
                    salesDate.value,
                    depotCode.value,
                    routeGroup
                )
            )

            return true
        }

        return false
    }

    private fun validateData(): Boolean {
        return true
    }
}