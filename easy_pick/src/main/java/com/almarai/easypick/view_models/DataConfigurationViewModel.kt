package com.almarai.easypick.view_models

import androidx.lifecycle.ViewModel
import com.almarai.data.easy_pick_models.DataConfiguration
import com.almarai.repository.api.ApplicationRepository

class DataConfigurationViewModel(private val applicationRepository: ApplicationRepository) :
    ViewModel() {
    fun checkAppDataConfigurations() = applicationRepository.checkAppDataIsConfigured()

    fun saveDataConfiguration(dataConfiguration: DataConfiguration) =
        applicationRepository.setDataConfiguration(dataConfiguration)

    fun getDataConfiguration() =
        applicationRepository.getDataConfiguration()
}