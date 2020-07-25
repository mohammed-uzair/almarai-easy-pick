package com.almarai.repository.implementations

import android.util.Log
import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.easypick.data_source.local_data_source.interfaces.LocalAppUpdateDataSource
import com.almarai.easypick.data_source.web.interfaces.WebAppUpdateDataSource
import com.almarai.repository.api.AppUpdateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AppUpdateRepositoryImplementation(
    private val localAppUpdateDataSource: LocalAppUpdateDataSource,
    private val webAppUpdateDataSource: WebAppUpdateDataSource
) :
    AppUpdateRepository {
    companion object {
        const val TAG = "AppUpdateRepoImpl"
    }

    override suspend fun getAppUpdates(): Flow<AppUpdate>? =
        localAppUpdateDataSource.getAppUpdates()

    override fun checkAppUpdate() {
        CoroutineScope(IO).launch {
            try {
                val appUpdate = webAppUpdateDataSource.checkAppUpdate()
                if (appUpdate != null) {
                    localAppUpdateDataSource.setAppUpdates(appUpdate)
                }
            } catch (exception: Exception) {
                Log.e(TAG, "Error in fetching app update", exception)
            }
        }
    }
}