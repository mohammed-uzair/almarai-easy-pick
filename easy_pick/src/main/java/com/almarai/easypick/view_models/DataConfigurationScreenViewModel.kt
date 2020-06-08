package com.almarai.easypick.view_models

import androidx.lifecycle.ViewModel
import com.almarai.data.easy_pick_models.DataConfiguration
import com.almarai.repository.api.ApplicationRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class DataConfigurationScreenViewModel : ViewModel(), KoinComponent {
    private val applicationRepository: ApplicationRepository by inject()

    fun checkAppDataConfigurations() = applicationRepository.checkAppDataIsConfigured()

    fun saveDataConfiguration(dataConfiguration: DataConfiguration) =
        applicationRepository.setDataConfiguration(dataConfiguration)

    fun getDataConfiguration() =
        applicationRepository.getDataConfiguration()
}