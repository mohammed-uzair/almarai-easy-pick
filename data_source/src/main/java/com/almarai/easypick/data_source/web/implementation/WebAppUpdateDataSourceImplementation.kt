package com.almarai.easypick.data_source.web.implementation

import android.util.Log
import com.almarai.common.app_update.AppUpdates
import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.easypick.data_source.interfaces.AppUpdateDataSource
import com.almarai.easypick.data_source.web.WebService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebAppUpdateDataSourceImplementation @Inject constructor(
    private val webService: WebService,
    private val appUpdates: AppUpdates
) :
    AppUpdateDataSource {
    companion object {
        const val TAG = "WebAppUpdateDSImpl"
    }

    override suspend fun checkAppUpdate(): AppUpdate? {
        return try {
            val appUpdate = webService.appUpdateApi.checkAppUpdate()
            if (appUpdate != null && AppUpdates.isValidUpdate(appUpdate)) {
                appUpdates.setAppUpdate(appUpdate)
            }

            appUpdate
        } catch (exception: Exception) {
            Log.e(TAG, "Exception while fetching app update")
            null
        }
    }
}