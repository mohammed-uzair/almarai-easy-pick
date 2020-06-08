package com.almarai.easypick.utils

sealed class AppLanguage {
    object English : AppLanguage()
    object Arabic : AppLanguage()
}

var APP_SELECTED_LANGUAGE: AppLanguage = AppLanguage.English