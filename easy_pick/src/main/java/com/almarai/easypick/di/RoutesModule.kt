package com.almarai.easypick.di

import com.almarai.easypick.view_models.RouteSelectionViewModel
import com.almarai.repository.api.RoutesRepository
import com.almarai.repository.api_implementations.RoutesRepositoryImplementation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val RoutesModule = module {

    viewModel { RouteSelectionViewModel() }
}