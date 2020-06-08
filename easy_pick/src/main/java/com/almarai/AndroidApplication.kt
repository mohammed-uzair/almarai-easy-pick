package com.almarai

import android.app.Application
import com.almarai.easypick.di.AppModule
import com.almarai.easypick.di.FragmentModule
import com.almarai.repository.di.RepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AndroidApplication : Application() {
    private val myModule = module {

    }

    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            androidLogger()
            // declare used Android context
            androidContext(this@AndroidApplication)
            fragmentFactory()
            // declare modules
            modules(AppModule, myModule, FragmentModule, RepositoryModule)
        }
    }
}