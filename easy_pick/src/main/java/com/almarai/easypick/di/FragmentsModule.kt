package com.almarai.easypick.di

import com.almarai.easypick.screens.*
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val FragmentModule = module {
    fragment { LaunchScreen() }
    fragment { NetworkConfigurationScreen() }
    fragment { DataConfigurationScreen() }
    fragment { LoginScreen() }
    fragment { HomeScreen() }
    fragment { RouteSelectionScreen() }
    fragment { ProductListScreen() }
    fragment { FilterScreen() }
}