package com.almarai.easypick

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.almarai.easypick.data_source.firebase.InitializeFirebase.configureFirebase
import com.almarai.easypick.databinding.FragmentContainerBinding
import com.almarai.easypick.extensions.OnBackPressListener
import com.almarai.easypick.extensions.hideViewStateAlert
import com.almarai.easypick.utils.LanguageSetup
import com.almarai.easypick.utils.progress.AppProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var progressDialog: AppProgressDialog

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
        configureFirebase(this)
    }

    override fun onBackPressed() {
        backPressListener?.onBackPressed() ?: super.onBackPressed()
        hideViewStateAlert()
    }

    override fun attachBaseContext(newContext: Context) {
        //Delegate to super to apply
        super.attachBaseContext(LanguageSetup.setAppLanguage(newContext))
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