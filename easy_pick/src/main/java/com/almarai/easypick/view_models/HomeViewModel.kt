package com.almarai.easypick.view_models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.almarai.repository.api.ApplicationRepository

class HomeViewModel @ViewModelInject constructor(private val applicationRepository: ApplicationRepository) : ViewModel()
