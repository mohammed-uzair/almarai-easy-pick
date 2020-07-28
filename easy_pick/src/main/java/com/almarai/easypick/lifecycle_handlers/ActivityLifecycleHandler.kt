package com.almarai.easypick.lifecycle_handlers

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import com.almarai.easypick.R
import com.almarai.easypick.common.AppUpdateFlow
import com.almarai.easypick.extensions.IS_HARDWARE_KEYBOARD_AVAILABLE
import com.almarai.easypick.extensions.exhaustive
import com.almarai.easypick.extensions.isHardwareKeyboardAvailable
import com.almarai.easypick.utils.*
import com.almarai.machine_learning.Utils
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import java.util.*


class ActivityLifecycleHandler(
    private val appUpdateFlow: AppUpdateFlow
) :
    Application.ActivityLifecycleCallbacks {
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        Log.d(TAG, "onActivityCreated at ${activity.localClassName}")

        (activity as FragmentActivity).setupKoinFragmentFactory()

        setAppTheme(activity)

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

        private fun setAppTheme(activity: Activity) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)

            val appTheme = sharedPreferences.getString(
                activity.getString(R.string.app_theme),
                activity.getString(R.string.base_app_theme)
            )

            when (appTheme) {
                activity.getString(R.string.base_app_theme), activity.getString(R.string.light_app_theme) -> {
                    APP_SELECTED_THEME = AppTheme.Light
                    activity.setTheme(R.style.Theme_Base_LightAppTheme)
                }
                activity.getString(R.string.dark_app_theme) -> {
                    APP_SELECTED_THEME = AppTheme.Dark
                    activity.setTheme(R.style.Theme_Base_DarkAppTheme)
                }
                activity.getString(R.string.night_app_theme) -> {
                    APP_SELECTED_THEME = AppTheme.Night
                    activity.setTheme(R.style.Theme_Base_NightAppTheme)
                }
            }
        }

        fun setAppLanguage(newContext: Context): Context {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(newContext)

            val appLanguage = sharedPreferences.getString(
                newContext.getString(R.string.app_language),
                newContext.getString(R.string.english_app_language)
            )

            val languageCode: String
            when (appLanguage) {
                newContext.getString(R.string.arabic_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Arabic
                    languageCode = "ar"
                    APP_LOCALE = Locale("ar", "rSA")
                }
                newContext.getString(R.string.bangla_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Bangla
                    languageCode = "bn"
                    APP_LOCALE = Locale("bn", "rBD")
                }
                newContext.getString(R.string.filipino_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Filipino
                    languageCode = "fil"
                    APP_LOCALE = Locale("il", "rPH")
                }
                newContext.getString(R.string.hindi_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Hindi
                    languageCode = "hi"
                    APP_LOCALE = Locale("hi", "rIN")
                }
                newContext.getString(R.string.indonesian_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Indonesian
                    languageCode = "in"
                    APP_LOCALE = Locale("in", "rID")
                }
                newContext.getString(R.string.kannada_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Kannada
                    languageCode = "kn"
                    APP_LOCALE = Locale("kn", "rIN")
                }
                newContext.getString(R.string.malayalam_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Malayalam
                    languageCode = "ml"
                    APP_LOCALE = Locale("ml", "rIN")
                }
                newContext.getString(R.string.nepali_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Nepali
                    languageCode = "ne"
                    APP_LOCALE = Locale("ne", "rNP")
                }
                newContext.getString(R.string.pashto_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Pashto
                    languageCode = "ps"
                    APP_LOCALE = Locale("ps", "rAF")
                }
                newContext.getString(R.string.sinhala_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Sinhala
                    languageCode = "si"
                    APP_LOCALE = Locale("si", "rLK")
                }
                newContext.getString(R.string.tamil_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Tamil
                    languageCode = "ta"
                    APP_LOCALE = Locale("ta", "rIN")
                }
                newContext.getString(R.string.telugu_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Telugu
                    languageCode = "te"
                    APP_LOCALE = Locale("te", "rIN")
                }
                newContext.getString(R.string.urdu_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Urdu
                    languageCode = "ur"
                    APP_LOCALE = Locale("ur", "rPK")
                }
                newContext.getString(R.string.vietnamese_app_language) -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.Vietnamese
                    languageCode = "vi"
                    APP_LOCALE = Locale("vi", "rVN")
                }
                else -> {
                    APP_SELECTED_LANGUAGE = AppLanguage.English
                    languageCode = "en"
                    APP_LOCALE = Locale("en", "rUS")
                }
            }.exhaustive

            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            val config = Configuration()

            config.setLocale(locale)
            Utils.APP_LOCALE = locale

            //Workaround for platform bug on SDK <26(O - Oreo)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                config.fontScale = 0f
            }

            return newContext.createConfigurationContext(config)
        }
    }
}