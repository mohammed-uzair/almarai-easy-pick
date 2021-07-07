package com.almarai.easypick.view_models

import javax.inject.Inject
import androidx.lifecycle.ViewModel
import com.almarai.repository.api.ApplicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class HomeViewModel @Inject constructor(private val applicationRepository: ApplicationRepository) : ViewModel()
