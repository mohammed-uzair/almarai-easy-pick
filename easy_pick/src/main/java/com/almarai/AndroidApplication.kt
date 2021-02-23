package com.almarai

import android.app.Application
import android.content.res.Configuration
import com.almarai.common.date_time.DateUtil
import com.almarai.common.logging.FIREBASE_ANALYTICS
import com.almarai.easypick.common.AppUpdateFlow
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.extensions.IS_HARDWARE_KEYBOARD_AVAILABLE
import com.almarai.easypick.extensions.isHardwareKeyboardAvailable
import com.almarai.easypick.lifecycle_handlers.ActivityLifecycleHandler
import com.almarai.easypick.voice.VoiceRecognitionServer
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AndroidApplication : Application() {
    @Inject
    lateinit var appUpdateFlow: AppUpdateFlow

    @Inject
    lateinit var preferenceDataSource: SharedPreferenceDataSource

    @Inject
    lateinit var voiceRecognitionServer: VoiceRecognitionServer

    override fun onCreate() {
        super.onCreate()

        // Register the activity lifecycle callback listener object
        registerActivityLifecycleCallbacks(
            ActivityLifecycleHandler(
                appUpdateFlow,
                preferenceDataSource,
                voiceRecognitionServer
            )
        )

        FIREBASE_ANALYTICS = FirebaseAnalytics.getInstance(this)

        FIREBASE_ANALYTICS?.logEvent(FirebaseAnalytics.Event.APP_OPEN) {
            param(FirebaseAnalytics.Param.START_DATE, DateUtil.getCurrentDate())
        }
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