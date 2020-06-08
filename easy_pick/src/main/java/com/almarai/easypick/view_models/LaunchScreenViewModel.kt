package com.almarai.easypick.view_models

import androidx.lifecycle.ViewModel
import com.almarai.repository.api.ApplicationRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class LaunchScreenViewModel() :
    ViewModel(), KoinComponent {
    private val applicationRepository: ApplicationRepository by inject()

    fun checkAppDataConfigurations() = applicationRepository.checkAppDataIsConfigured()
}