package com.almarai.easypick.screens

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.almarai.easypick.R
import com.almarai.easypick.common.AppUpdateFlow


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

        val appTheme = findPreference(getString(R.string.app_theme)) as ListPreference?
        val appLanguage = findPreference(getString(R.string.app_language)) as ListPreference?
        val appUpdate = findPreference(getString(R.string.app_update)) as Preference?

        appTheme?.onPreferenceChangeListener = this
        appLanguage?.onPreferenceChangeListener = this
        appUpdate?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            appUpdateFlow.checkManualAppUpdate()
            true
        }
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        activity?.recreate()
        navController.popBackStack()
        return true
    }
}