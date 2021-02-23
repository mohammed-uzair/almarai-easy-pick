package com.almarai.easypick.screens

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.almarai.easypick.BuildConfig
import com.almarai.easypick.R
import com.almarai.easypick.common.AppUpdateFlow
import com.almarai.easypick.utils.alert_dialog.showAlertDialog
import com.almarai.easypick.voice.use_cases.NavigateScreen
import com.almarai.easypick.voice.use_cases.Screen
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*

class SettingsScreen(private val appUpdateFlow: AppUpdateFlow) : PreferenceFragmentCompat(),
    Preference.OnPreferenceChangeListener {
    private lateinit var navController: NavController

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_settings_preferences, rootKey)

        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_settings)

        NavigateScreen.CURRENT_SCREEN = Screen.SettingsScreen

        val appTheme = findPreference(getString(R.string.app_theme)) as ListPreference?
        val appLanguage = findPreference(getString(R.string.app_language)) as ListPreference?
        val appUpdate = findPreference(getString(R.string.app_update)) as Preference?
        val appNewTicketGeneration =
            findPreference(getString(R.string.app_ticket_generation)) as Preference?
        val appDbConnection =
            findPreference(getString(R.string.app_database)) as Preference?
        val appVoiceCommands =
            findPreference(getString(R.string.app_voice_commands)) as Preference?

        appTheme?.onPreferenceChangeListener = this
        appLanguage?.onPreferenceChangeListener = this
        appUpdate?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            appUpdate()
            true
        }
        appNewTicketGeneration?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            navController.navigate(R.id.action_settingsScreen_to_ticketScreen)
            true
        }
        appDbConnection?.onPreferenceChangeListener = this
        appVoiceCommands?.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        activity?.recreate()
        navController.popBackStack()
        return true
    }

    private fun appUpdate() {
        lifecycleScope.launch {
            val appUpdate = appUpdateFlow.checkManualAppUpdate()

            if (appUpdate != null) {
                if (BuildConfig.VERSION_CODE >= appUpdate.appVersionNumber) {
                    //App is already up to date
                    showAlertDialog(
                        alertTitle = R.string.app_update,
                        alertMessage = getString(R.string.app_up_to_date),
                        animationResourceId = R.raw.update_success
                    )
                }
            }

            cancel()
        }
    }
}