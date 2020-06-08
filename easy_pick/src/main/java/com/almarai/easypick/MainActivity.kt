package com.almarai.easypick

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import com.almarai.easypick.utils.APP_SELECTED_LANGUAGE
import com.almarai.easypick.utils.AppLanguage
import com.google.firebase.analytics.FirebaseAnalytics
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
        setAppLanguage()

        setContentView(R.layout.fragment_container)

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        init()
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
            getString(R.string.base_app_theme), getString(R.string.light_app_theme) -> setTheme(R.style.Theme_Base_LightAppTheme)
            getString(R.string.dark_app_theme) -> setTheme(R.style.Theme_Base_DarkAppTheme)
            getString(R.string.night_app_theme) -> setTheme(R.style.Theme_Base_NightAppTheme)
        }
    }

    private fun setAppLanguage() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val appTheme = sharedPreferences.getString(
            getString(R.string.app_language),
            getString(R.string.english_app_language)
        )

        var languageCode = "en"
        when (appTheme) {
            getString(R.string.english_app_language) -> {
                APP_SELECTED_LANGUAGE = AppLanguage.English
                languageCode = "en"
            }
            getString(R.string.arabic_app_language) -> {
                APP_SELECTED_LANGUAGE = AppLanguage.Arabic
                languageCode = "ar"
            }
        }

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }

    private fun init() {
        setSupportActionBar(toolbar as Toolbar)
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

    fun setLocale(context: Context) {
        val locale: Locale
//        val session = Sessions(context)
        //Log.e("Lan",session.getLanguage());
        //Log.e("Lan",session.getLanguage());
        locale = Locale("ar")

        val config =
            Configuration(context.resources.configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)

        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )
    }
}