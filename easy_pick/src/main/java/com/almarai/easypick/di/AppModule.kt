package com.almarai.easypick.di

import com.almarai.easypick.common.AppUpdateFlow
import com.almarai.easypick.lifecycle_handlers.ActivityLifecycleHandler
import com.almarai.easypick.utils.progress.AppProgressDialog
import org.koin.dsl.module

val AppModule = module {
    single { AppUpdateFlow(get(), get()) }
    single { AppProgressDialog() }
    factory { ActivityLifecycleHandler(get()) }
}