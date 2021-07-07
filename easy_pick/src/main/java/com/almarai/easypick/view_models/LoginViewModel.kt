package com.almarai.easypick.view_models

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almarai.repository.api.ApplicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class LoginViewModel @Inject constructor(val applicationRepository: ApplicationRepository) : ViewModel() {
    companion object {
        const val TAG = "LoginViewModel"
    }

    val userName: MutableLiveData<String> = MutableLiveData("")
}