package com.almarai.easypick.lifecycle_handlers

import android.app.Activity
import android.app.Application
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.almarai.common.date_time.DateUtil.getCurrentDate
import com.almarai.common.logging.FIREBASE_ANALYTICS
import com.almarai.common.machine_learning.translation.OnDeviceTextTranslation
import com.almarai.common.utils.Logger
import com.almarai.easypick.R
import com.almarai.easypick.common.AppUpdateFlow
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.extensions.IS_HARDWARE_KEYBOARD_AVAILABLE
import com.almarai.easypick.extensions.isHardwareKeyboardAvailable
import com.almarai.easypick.utils.AlertTones
import com.almarai.easypick.common.ThemeSetup
import com.almarai.easypick.voice.VoiceRecognitionServer
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import javax.inject.Inject

class ActivityLifecycleHandler @Inject constructor(
    private val appUpdateFlow: AppUpdateFlow,
    private val preferenceDataSource: SharedPreferenceDataSource,
    private val voiceRecognitionServer: VoiceRecognitionServer
) :
    Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        Logger.logInfo(
            activity.localClassName,
            "onActivityCreated at ${activity.localClassName}"
        )

        ThemeSetup.setAppTheme(activity)

        IS_HARDWARE_KEYBOARD_AVAILABLE = isHardwareKeyboardAvailable(activity)

        FIREBASE_ANALYTICS?.logEvent("activity_started") {
            param(FirebaseAnalytics.Param.START_DATE, getCurrentDate() ?: "")
        }

        appUpdateFlow.initiate(activity as AppCompatActivity)
    }

    override fun onActivityStarted(activity: Activity) {
        Log.d(TAG, "onActivityStarted at ${activity.localClassName}")

        AlertTones.audioManager = activity.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    override fun onActivityResumed(activity: Activity) {
        Log.d(TAG, "onActivityResumed at ${activity.localClassName}")

        //Listen for voice commands, if the flag is enabled
        if (preferenceDataSource.getSharedPreferenceBoolean(activity.getString(R.string.app_voice_commands)))
            voiceRecognitionServer.startVoiceRecognition(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        Log.d(TAG, "onActivityPaused at ${activity.localClassName}")

        //Stop listening voice commands
        voiceRecognitionServer.stopVoiceRecognition()
    }

    override fun onActivityStopped(activity: Activity) {
        Log.d(TAG, "onActivityStopped at ${activity.localClassName}")

        //CLose the ML Translator
        OnDeviceTextTranslation.closeTranslator()
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.d(TAG, "onActivityDestroyed at ${activity.localClassName}")
    }

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
        Log.d(TAG, "onActivitySaveInstanceState at ${activity.localClassName}")
    }

    companion object {
        private const val TAG = "LifecycleCallbacks"
    }
}