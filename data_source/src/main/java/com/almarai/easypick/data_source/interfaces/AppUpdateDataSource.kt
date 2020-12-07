package com.almarai.easypick.data_source.interfaces

import com.almarai.data.easy_pick_models.AppUpdate
import javax.inject.Singleton

@Singleton
interface AppUpdateDataSource {
    suspend fun checkAppUpdate(): AppUpdate?
}