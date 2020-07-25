package com.almarai.easypick.data_source.web.api

import com.almarai.data.easy_pick_models.AppUpdate
import retrofit2.http.GET

interface AppUpdateApi {
    @GET("appUpdate")
    suspend fun checkAppUpdate(): AppUpdate?
}