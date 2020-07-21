package com.almarai.easypick.utils

import java.util.*

sealed class AppLanguage {
    object English : AppLanguage()
    object Arabic : AppLanguage()
    object Bangla : AppLanguage()
    object Filipino : AppLanguage()
    object Hindi : AppLanguage()
    object Indonesian : AppLanguage()
    object Kannada : AppLanguage()
    object Malayalam : AppLanguage()
    object Nepali : AppLanguage()
    object Pashto : AppLanguage()
    object Sinhala : AppLanguage()
    object Tamil : AppLanguage()
    object Telugu : AppLanguage()
    object Urdu : AppLanguage()
    object Vietnamese : AppLanguage()
}

var APP_SELECTED_LANGUAGE: AppLanguage = AppLanguage.English
var APP_LOCALE: Locale = Locale.US