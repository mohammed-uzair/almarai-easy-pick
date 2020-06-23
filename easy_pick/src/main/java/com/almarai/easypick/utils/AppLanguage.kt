package com.almarai.easypick.utils

sealed class AppLanguage {
    object English : AppLanguage()
    object Arabic : AppLanguage()
    object Hindi : AppLanguage()
    object Tamil : AppLanguage()
    object Urdu : AppLanguage()
    object Nepali : AppLanguage()
    object Sinhala : AppLanguage()
    object Vietnamese : AppLanguage()
    object Pashto : AppLanguage()
    object Filipino : AppLanguage()
}

var APP_SELECTED_LANGUAGE: AppLanguage = AppLanguage.English