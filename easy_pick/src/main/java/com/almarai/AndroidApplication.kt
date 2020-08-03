package com.almarai

import android.app.Application
import android.content.res.Configuration
import com.almarai.easypick.common.AppUpdateFlow
//import com.almarai.easypick.di.AppModule
//import com.almarai.easypick.di.FragmentModule
import com.almarai.easypick.extensions.IS_HARDWARE_KEYBOARD_AVAILABLE
import com.almarai.easypick.extensions.isHardwareKeyboardAvailable
import com.almarai.easypick.lifecycle_handlers.ActivityLifecycleHandler
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.components.ApplicationComponent
//import org.koin.android.ext.android.inject
//import org.koin.android.ext.koin.androidContext
//import org.koin.android.ext.koin.androidLogger
//import org.koin.androidx.fragment.koin.fragmentFactory
//import org.koin.core.context.startKoin
import javax.inject.Inject

@HiltAndroidApp
class AndroidApplication : Application() {
    @Inject
    lateinit var appUpdateFlow: AppUpdateFlow

    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            androidLogger()
//            // declare used Android context
//            androidContext(this@AndroidApplication)
//            fragmentFactory()
//            // declare modules
//            modules(AppModule, FragmentModule, ViewModelsModule, RepositoryModule, DataSourceModule)
//        }

        // Register the activity lifecycle callback listener object
        registerActivityLifecycleCallbacks(ActivityLifecycleHandler(appUpdateFlow))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            //External keyboard is being connected
            IS_HARDWARE_KEYBOARD_AVAILABLE = true
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            //External keyboard is being removed, check for on device attached keyboard
            IS_HARDWARE_KEYBOARD_AVAILABLE = isHardwareKeyboardAvailable(applicationContext)
        }
    }
}