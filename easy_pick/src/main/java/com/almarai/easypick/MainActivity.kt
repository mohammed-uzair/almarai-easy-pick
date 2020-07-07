package com.almarai.easypick

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.almarai.easypick.databinding.FragmentContainerBinding
import com.almarai.easypick.extensions.OnBackPressListener
import com.almarai.easypick.extensions.exhaustive
import com.almarai.easypick.extensions.hideViewStateAlert
import com.almarai.easypick.utils.*
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.core.KoinComponent
import java.util.*

class MainActivity : AppCompatActivity(), KoinComponent {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    lateinit var screenMainBinding: FragmentContainerBinding

    internal var backPressListener: OnBackPressListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)

        setAppTheme()

        screenMainBinding = DataBindingUtil.setContentView(this, R.layout.fragment_container)
        screenMainBinding.apply {
            lifecycleOwner = this@MainActivity
        }

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        init()
    }

    override fun onBackPressed() {
        hideViewStateAlert()

        backPressListener?.onBackPressed() ?: super.onBackPressed()
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
                APP_SELECTED_THEME = AppTheme.Light
                setTheme(R.style.Theme_Base_LightAppTheme)
            }
            getString(R.string.dark_app_theme) -> {
                APP_SELECTED_THEME = AppTheme.Dark
                setTheme(R.style.Theme_Base_DarkAppTheme)
            }
            getString(R.string.night_app_theme) -> {
                APP_SELECTED_THEME = AppTheme.Night
                setTheme(R.style.Theme_Base_NightAppTheme)
            }
        }
    }

    private fun setAppLanguage(newContext: Context): Context {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(newContext)
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

        //Workaround for platform bug on SDK <26(O - Oreo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            config.fontScale = 0f
        }

        return newContext.createConfigurationContext(config)
    }

    private fun init() {
        setSupportActionBar(screenMainBinding.fragmentContainerToolbar.toolbar)
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