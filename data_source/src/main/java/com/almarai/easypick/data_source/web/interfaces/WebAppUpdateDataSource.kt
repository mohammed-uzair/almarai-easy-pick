package com.almarai.easypick.data_source.web.interfaces

import com.almarai.data.easy_pick_models.AppUpdate

interface WebAppUpdateDataSource {
    suspend fun checkAppUpdate(): AppUpdate?
}