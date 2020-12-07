package com.almarai.easypick.data_source.firebase.implementation

import android.util.Log
import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.easypick.data_source.interfaces.AppUpdateDataSource
import com.almarai.easypick.data_source.web.implementation.WebAppUpdateDataSourceImplementation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAppUpdateDataSourceImplementation @Inject constructor() : AppUpdateDataSource {
    companion object {
        const val TAG = "FirebaseRouteDSImpl"
    }

    override suspend fun checkAppUpdate(): AppUpdate? {
        return try {
            null
        } catch (exception: Exception) {
            Log.e(WebAppUpdateDataSourceImplementation.TAG, "Exception while fetching app update")
            null
        }
    }
}