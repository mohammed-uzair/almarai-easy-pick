package com.almarai.easypick.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almarai.easypick.BuildConfig
import com.almarai.repository.api.ApplicationRepository

class LaunchViewModel(private val applicationRepository: ApplicationRepository) : ViewModel() {
    companion object{
        const val TAG = "LaunchViewModel"
    }

    val appVersionName = MutableLiveData<String>(BuildConfig.VERSION_NAME)

    fun checkAppDataConfigurations() = applicationRepository.checkAppDataIsConfigured()
}