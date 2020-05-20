package com.almarai.easypick.di

import com.almarai.easypick.screens.FragmentFactoryImpl
import org.koin.dsl.module

val FragmentFactoryModule = module {
    factory { FragmentFactoryImpl(get()) }
}