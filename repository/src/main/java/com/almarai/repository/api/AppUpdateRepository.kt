package com.almarai.repository.api

import com.almarai.data.easy_pick_models.AppUpdate
import kotlinx.coroutines.flow.Flow

interface AppUpdateRepository {
    suspend fun checkAppUpdate(): AppUpdate?
}