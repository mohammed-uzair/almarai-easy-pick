package com.almarai.easypick.view_models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almarai.easypick.BuildConfig
import com.almarai.repository.api.ApplicationRepository
import dagger.hilt.android.internal.lifecycle.DefaultFragmentViewModelFactory

class LaunchViewModel @ViewModelInject constructor(private val applicationRepository: ApplicationRepository) : ViewModel() {
    companion object{
        const val TAG = "LaunchViewModel"
    }

    val appVersionName = MutableLiveData<String>(BuildConfig.VERSION_NAME)

    fun checkAppDataConfigurations() = applicationRepository.checkAppDataIsConfigured()
}