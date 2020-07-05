package com.almarai.easypick.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.data.easy_pick_models.NetworkConfiguration
import com.almarai.repository.api.ApplicationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NetworkConfigurationViewModel(private val applicationRepository: ApplicationRepository) :
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
        if (validateData()) {
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

    private fun validateData(): Boolean {
        validateIpAddress()
        return true
    }

    private fun validateIpAddress() {

    }
}