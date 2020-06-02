package com.almarai.easypick.screens

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.almarai.easypick.R

class SettingsScreen : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_settings)

        val appTheme = findPreference(getString(R.string.app_theme)) as ListPreference?
        val appLanguage = findPreference(getString(R.string.app_language)) as ListPreference?

        appTheme?.onPreferenceChangeListener = this
        appLanguage?.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        activity?.recreate()
        return true
    }
}