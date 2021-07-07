package com.almarai.easypick.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almarai.easypick.BuildConfig
import com.almarai.repository.api.ApplicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(private val applicationRepository: ApplicationRepository) : ViewModel() {
    companion object{
        const val TAG = "LaunchViewModel"
    }

    val appVersionName = MutableLiveData<String>(BuildConfig.VERSION_NAME)

    fun checkAppDataConfigurations() = applicationRepository.checkAppDataIsConfigured()
}