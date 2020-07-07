package com.almarai.easypick.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almarai.repository.api.ApplicationRepository

class LoginViewModel(val applicationRepository: ApplicationRepository) : ViewModel() {
    companion object {
        const val TAG = "LoginViewModel"
    }

    val userName: MutableLiveData<String> = MutableLiveData("")
}