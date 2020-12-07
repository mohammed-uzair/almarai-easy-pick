package com.almarai.easypick.voice.use_cases

import android.app.Activity
import com.almarai.easypick.R
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.voice.VoiceRecognitionServer
import javax.inject.Inject

class ChangeThemes @Inject constructor(private val sharedPreferenceDataSource: SharedPreferenceDataSource) {
    fun changeTheme(
        activity: Activity?,
        voiceRecognitionServer: VoiceRecognitionServer?,
        command: String
    ) {
        val theme = getTheme(command)

        if (theme.isNotEmpty()) {
            //Change the theme in the app preference
            sharedPreferenceDataSource.setSharedPreference(
                activity?.getString(R.string.app_theme), theme
            )

            voiceRecognitionServer?.stopVoiceRecognition()
            activity?.recreate()
        }
    }

    private fun getTheme(command: String): String {
        return when {
            command.contains("light") -> "light"
            command.contains("dark") -> "dark"
            command.contains("night") -> "night"
            else -> ""
        }
    }
}