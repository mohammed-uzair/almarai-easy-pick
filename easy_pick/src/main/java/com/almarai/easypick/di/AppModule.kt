package com.almarai.easypick.di

import com.almarai.easypick.common.AppUpdateFlow
import com.almarai.easypick.lifecycle_handlers.ActivityLifecycleHandler
import org.koin.dsl.module

val AppModule = module {
    single { AppUpdateFlow(get(), get()) }
    factory { ActivityLifecycleHandler(get()) }
}