package com.almarai.easypick.data_source.local_data_source.interfaces

import com.almarai.data.easy_pick_models.AppUpdate
import kotlinx.coroutines.flow.Flow

interface LocalAppUpdateDataSource{
    suspend fun getAppUpdates(): Flow<AppUpdate>
    fun setAppUpdates(appUpdate: AppUpdate?)
}