package com.almarai.easypick

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.core.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)

//        when (THEME_SET) {
//            0 -> setTheme(R.style.Theme_Base_LightAppTheme)
//            1 -> setTheme(R.style.Theme_Base_DarkAppTheme)
//            2 -> setTheme(R.style.Theme_Base_NightAppTheme)
//        }

        setContentView(R.layout.fragment_container)

        init()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //Hide the navigation bar from bottom
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
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