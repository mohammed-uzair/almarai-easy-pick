package com.almarai.repository.implementations

import android.util.Log
import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.easypick.data_source.interfaces.AppUpdateDataSource
import com.almarai.repository.api.AppUpdateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppUpdateRepositoryImplementation
@Inject constructor(
    private val appUpdateDataSource: AppUpdateDataSource
) :
    AppUpdateRepository {
    companion object {
        const val TAG = "AppUpdateRepoImpl"
    }

    override suspend fun getAppUpdates(): Flow<AppUpdate>? =
        appUpdateDataSource.getAppUpdates()

    override fun checkAppUpdate() {
        CoroutineScope(IO).launch {
            try {
                val appUpdate = appUpdateDataSource.checkAppUpdate()
                if (appUpdate != null) {
                    appUpdateDataSource.setAppUpdates(appUpdate)
                }
            } catch (exception: Exception) {
                Log.e(TAG, "Error in fetching app update", exception)
            }
        }
    }
}