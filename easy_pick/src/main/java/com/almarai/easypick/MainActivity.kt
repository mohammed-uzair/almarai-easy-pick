package com.almarai.easypick

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.core.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)

        setAppTheme()

        setContentView(R.layout.fragment_container)

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

    private fun init() {
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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