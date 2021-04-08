package com.almarai.easypick.common

import android.app.Activity
import androidx.preference.PreferenceManager
import com.almarai.easypick.R

object ThemeSetup {
    fun setAppTheme(activity: Activity) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)

        val appTheme = sharedPreferences.getString(
            activity.getString(R.string.app_theme),
            activity.getString(R.string.base_app_theme)
        )

        when (appTheme) {
            activity.getString(R.string.base_app_theme), activity.getString(R.string.light_app_theme) -> {
                APP_SELECTED_THEME = AppTheme.Light
                activity.setTheme(R.style.Theme_Base_LightAppTheme)
            }
            activity.getString(R.string.dark_app_theme) -> {
                APP_SELECTED_THEME = AppTheme.Dark
                activity.setTheme(R.style.Theme_Base_DarkAppTheme)
            }
            activity.getString(R.string.night_app_theme) -> {
                APP_SELECTED_THEME = AppTheme.Night
                activity.setTheme(R.style.Theme_Base_NightAppTheme)
            }
        }
    }
}