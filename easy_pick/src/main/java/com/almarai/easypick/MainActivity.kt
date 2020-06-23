package com.almarai.easypick

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import com.almarai.easypick.utils.*
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.fragment_container.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.core.KoinComponent
import java.util.*

class MainActivity : AppCompatActivity(), KoinComponent {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)

        setAppTheme()

        setContentView(R.layout.fragment_container)

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        init()
    }

    override fun onBackPressed() {
        hideAlert()

        super.onBackPressed()
    }

    override fun attachBaseContext(newContext: Context) {
        //Delegate to super to apply
        super.attachBaseContext(setAppLanguage(newContext))
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //Hide the navigation bar from bottom
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    private fun setAppTheme() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val appTheme = sharedPreferences.getString(
            getString(R.string.app_theme),
            getString(R.string.base_app_theme)
        )

        when (appTheme) {
            getString(R.string.base_app_theme), getString(R.string.light_app_theme) -> {
                setTheme(R.style.Theme_Base_LightAppTheme)
                APP_SELECTED_THEME = AppTheme.Light
            }
            getString(R.string.dark_app_theme) -> {
                setTheme(R.style.Theme_Base_DarkAppTheme)
                APP_SELECTED_THEME = AppTheme.Dark
            }
            getString(R.string.night_app_theme) -> {
                setTheme(R.style.Theme_Base_NightAppTheme)
                APP_SELECTED_THEME = AppTheme.Night
            }
        }
    }

    private fun setAppLanguage(newContext: Context): Context {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(newContext)
        val appLanguage = sharedPreferences.getString(
            newContext.getString(R.string.app_language),
            newContext.getString(R.string.english_app_language)
        )

        var languageCode = "en"
        when (appLanguage) {
            newContext.getString(R.string.english_app_language) -> {
                APP_SELECTED_LANGUAGE = AppLanguage.English
                languageCode = "en"
            }
            newContext.getString(R.string.arabic_app_language) -> {
                APP_SELECTED_LANGUAGE = AppLanguage.Arabic
                languageCode = "ar"
            }
            newContext.getString(R.string.hindi_app_language) -> {
                APP_SELECTED_LANGUAGE = AppLanguage.Hindi
                languageCode = "hi"
            }
            newContext.getString(R.string.tamil_app_language) -> {
                APP_SELECTED_LANGUAGE = AppLanguage.Tamil
                languageCode = "ta"
            }
            newContext.getString(R.string.urdu_app_language) -> {
                APP_SELECTED_LANGUAGE = AppLanguage.Urdu
                languageCode = "ur"
            }
            newContext.getString(R.string.nepali_app_language) -> {
                APP_SELECTED_LANGUAGE = AppLanguage.Nepali
                languageCode = "ne"
            }
            newContext.getString(R.string.sinhala_app_language) -> {
                APP_SELECTED_LANGUAGE = AppLanguage.Sinhala
                languageCode = "si"
            }
            newContext.getString(R.string.vietnamese_app_language) -> {
                APP_SELECTED_LANGUAGE = AppLanguage.Vietnamese
                languageCode = "vi"
            }
            newContext.getString(R.string.pashto_app_language) -> {
                APP_SELECTED_LANGUAGE = AppLanguage.Pashto
                languageCode = "ps"
            }
            newContext.getString(R.string.filipino_app_language) -> {
                APP_SELECTED_LANGUAGE = AppLanguage.Filipino
                languageCode = "fil"
            }
        }

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()

        config.setLocale(locale)

        //Workaround for platform bug on SDK <26(O - Oreo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            config.fontScale = 0f
        }

        return newContext.createConfigurationContext(config)
    }

    private fun init() {
        setSupportActionBar(fragment_container_toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = Bundle()
        bundle.putString("screen_name", "Main Activity")
        mFirebaseAnalytics.logEvent("screen_opened", bundle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}