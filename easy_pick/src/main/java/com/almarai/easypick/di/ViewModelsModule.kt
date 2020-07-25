package com.almarai.easypick.di

import com.almarai.easypick.utils.alert_dialog.AppAlertDialogViewModel
import com.almarai.easypick.view_models.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelsModule = module {
    viewModel { LaunchViewModel(get()) }
    viewModel { NetworkConfigurationViewModel(get()) }
    viewModel { DataConfigurationViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { RouteSelectionViewModel(get()) }
    viewModel { ProductListViewModel(get()) }
    viewModel { FilterViewModel(get()) }
    viewModel { StatisticsViewModel(get()) }
    viewModel { AppAlertDialogViewModel() }
}