package com.almarai.easypick.lifecycle_handlers

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.app.Application
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import com.almarai.easypick.common.AppUpdateFlow
import com.almarai.easypick.extensions.IS_HARDWARE_KEYBOARD_AVAILABLE
import com.almarai.easypick.extensions.isHardwareKeyboardAvailable
import com.almarai.easypick.utils.AlertTones
import com.almarai.easypick.utils.ThemeSetup
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class ActivityLifecycleHandler @Inject constructor(private val appUpdateFlow: AppUpdateFlow) :
    Application.ActivityLifecycleCallbacks {
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        Log.d(TAG, "onActivityCreated at ${activity.localClassName}")

        ThemeSetup.setAppTheme(activity)

        IS_HARDWARE_KEYBOARD_AVAILABLE = isHardwareKeyboardAvailable(activity)

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity)

        val bundle = Bundle()
        bundle.putString("screen_name", "Main Activity")
        mFirebaseAnalytics.logEvent("screen_opened", bundle)

        appUpdateFlow.initiate(activity as AppCompatActivity)
    }

    override fun onActivityStarted(activity: Activity) {
        Log.d(TAG, "onActivityStarted at ${activity.localClassName}")

        AlertTones.audioManager = activity.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    override fun onActivityResumed(activity: Activity) {
        Log.d(TAG, "onActivityResumed at ${activity.localClassName}")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.d(TAG, "onActivityPaused at ${activity.localClassName}")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.d(TAG, "onActivityStopped at ${activity.localClassName}")
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