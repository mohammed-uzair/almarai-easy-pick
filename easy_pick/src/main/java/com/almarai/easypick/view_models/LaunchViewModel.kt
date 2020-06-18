package com.almarai.easypick.view_models

import androidx.lifecycle.ViewModel
import com.almarai.repository.api.ApplicationRepository

class LaunchViewModel(private val applicationRepository: ApplicationRepository) : ViewModel() {
    fun checkAppDataConfigurations() = applicationRepository.checkAppDataIsConfigured()
}