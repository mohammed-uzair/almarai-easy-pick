package com.almarai

import android.app.Application
import com.almarai.easypick.di.AppModule
import com.almarai.easypick.di.FragmentModule
import com.almarai.easypick.di.ViewModelsModule
import com.almarai.repository.di.RepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            // declare used Android context
            androidContext(this@AndroidApplication)
            fragmentFactory()
            // declare modules
            modules(AppModule, FragmentModule, ViewModelsModule, RepositoryModule)
        }
    }
}