package com.almarai.easypick.utils

sealed class AppTheme {
    object Light : AppTheme()
    object Dark : AppTheme()
    object Night : AppTheme()
}

var APP_SELECTED_THEME: AppTheme = AppTheme.Light