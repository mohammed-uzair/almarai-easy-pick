package com.almarai.easypick.view_models

import androidx.lifecycle.ViewModel
import com.almarai.data.easy_pick_models.NetworkConfiguration
import com.almarai.repository.api.ApplicationRepository

class NetworkConfigurationViewModel(private val applicationRepository: ApplicationRepository) :
    ViewModel() {
    fun checkAppDataConfigurations() = applicationRepository.checkAppDataIsConfigured()

    fun saveNetworkConfiguration(networkConfiguration: NetworkConfiguration) =
        applicationRepository.setNetworkConfiguration(networkConfiguration)

    fun getNetworkConfiguration() =
        applicationRepository.getNetworkConfiguration()
}