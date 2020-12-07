package com.almarai.common.app_update

import com.almarai.common.BuildConfig
import com.almarai.data.easy_pick_models.AppUpdate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUpdates @Inject constructor() {
    companion object{
        fun isValidUpdate(appUpdate: AppUpdate) = appUpdate.appVersionNumber > BuildConfig.VERSION_CODE
    }

    private val _APP_UPDATE = MutableStateFlow(AppUpdate())

    public fun setAppUpdate(appUpdate: AppUpdate) {
        if (isValidUpdate(appUpdate)) _APP_UPDATE.value = appUpdate
    }

    public fun getAppUpdates(): Flow<AppUpdate> = _APP_UPDATE
}