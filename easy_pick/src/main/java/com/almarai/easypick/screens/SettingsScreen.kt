package com.almarai.easypick.screens

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.almarai.easypick.R


class SettingsScreen : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
    private lateinit var navController: NavController

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_settings_preferences, rootKey)

        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val typedValue = TypedValue()
        val theme = requireContext().theme
        theme.resolveAttribute(R.attr.colorBackgroundScreenBody, typedValue, true)
        @ColorInt val color = typedValue.data

        view.setBackgroundColor(color)

        navController = Navigation.findNavController(view)

        super.onViewCreated(view, savedInstanceState)
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
        navController.popBackStack()
        return true
    }
}