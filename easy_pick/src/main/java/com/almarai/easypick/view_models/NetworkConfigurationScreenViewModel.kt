package com.almarai.easypick.view_models

import androidx.lifecycle.ViewModel
import com.almarai.data.easy_pick_models.NetworkConfiguration
import com.almarai.repository.api.ApplicationRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class NetworkConfigurationScreenViewModel : ViewModel(), KoinComponent {
    private val applicationRepository: ApplicationRepository by inject()

    fun checkAppDataConfigurations() = applicationRepository.checkAppDataIsConfigured()

    fun saveNetworkConfiguration(networkConfiguration: NetworkConfiguration) =
        applicationRepository.setNetworkConfiguration(networkConfiguration)

    fun getNetworkConfiguration() =
        applicationRepository.getNetworkConfiguration()
}