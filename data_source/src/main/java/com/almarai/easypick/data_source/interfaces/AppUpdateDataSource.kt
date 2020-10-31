package com.almarai.easypick.data_source.interfaces

import com.almarai.data.easy_pick_models.AppUpdate
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface AppUpdateDataSource {
    suspend fun getAppUpdates(): Flow<AppUpdate>
    fun setAppUpdates(appUpdate: AppUpdate?)
    suspend fun checkAppUpdate(): AppUpdate?
}