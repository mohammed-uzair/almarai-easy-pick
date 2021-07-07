package com.almarai.easypick.view_models

import android.util.Log
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.data.easy_pick_models.NetworkConfiguration
import com.almarai.easypick.utils.Util
import com.almarai.repository.api.ApplicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class NetworkConfigurationViewModel @Inject constructor(private val applicationRepository: ApplicationRepository) :
    ViewModel() {
    companion object {
        const val TAG = "NetworkConfigViewModel"
    }

    val serverIp: MutableLiveData<String> = MutableLiveData("0")
    val serverPort: MutableLiveData<String> = MutableLiveData("8080")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val networkConfiguration = applicationRepository.getNetworkConfiguration()
                serverIp.postValue(networkConfiguration.serverIpAddress)
                serverPort.postValue(networkConfiguration.serverPort)
            } catch (exception: Exception) {
                Log.e(TAG, "Fetching network configuration: Error Occurred", exception)
            }
        }
    }

    fun checkAppDataConfigurations() = applicationRepository.checkAppDataIsConfigured()

    fun saveNetworkConfiguration(): Boolean {
        if (Util.isValidIPAddress(serverIp.value ?: "")) {
            applicationRepository.setNetworkConfiguration(
                NetworkConfiguration(
                    serverIp.value,
                    serverPort.value
                )
            )

            return true
        }

        return false
    }
}