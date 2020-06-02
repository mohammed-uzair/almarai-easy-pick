package com.almarai.easypick.di

import com.almarai.easypick.view_models.RouteSelectionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val RoutesModule = module {

    viewModel { RouteSelectionViewModel() }
}