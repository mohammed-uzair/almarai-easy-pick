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
import com.almarai.business.Utils.DateUtil
import com.almarai.easypick.databinding.FragmentContainerBinding
import com.almarai.easypick.extensions.*
import com.almarai.easypick.lifecycle_handlers.ActivityLifecycleHandler
import com.almarai.easypick.utils.*
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.core.KoinComponent
import java.util.*

class MainActivity : AppCompatActivity(), KoinComponent {
    lateinit var screenMainBinding: FragmentContainerBinding

    internal var backPressListener: OnBackPressListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenMainBinding = DataBindingUtil.setContentView(this, R.layout.fragment_container)
        screenMainBinding.apply {
            lifecycleOwner = this@MainActivity
        }

        setSupportActionBar(screenMainBinding.fragmentContainerToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        hideViewStateAlert()

        backPressListener?.onBackPressed() ?: super.onBackPressed()
    }

    override fun attachBaseContext(newContext: Context) {
        //Delegate to super to apply
        super.attachBaseContext(ActivityLifecycleHandler.setAppLanguage(newContext))
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //Hide the navigation bar from bottom
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
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