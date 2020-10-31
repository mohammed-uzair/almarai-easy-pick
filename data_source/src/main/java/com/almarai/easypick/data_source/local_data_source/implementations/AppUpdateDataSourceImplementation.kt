package com.almarai.easypick.data_source.local_data_source.implementations

import android.util.Log
import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.easypick.data_source.BuildConfig
import com.almarai.easypick.data_source.interfaces.AppUpdateDataSource
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUpdateDataSourceImplementation
@Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : AppUpdateDataSource {
    companion object {
        const val TAG = "LocalAppUpdateDS"
    }

    private val appUpdate: Channel<AppUpdate> = Channel()

    @ExperimentalCoroutinesApi
    override suspend fun getAppUpdates() = appUpdate.receiveAsFlow()

    override fun setAppUpdates(appUpdate: AppUpdate?) = checkIfAppUpdateAvailable(appUpdate)

    override suspend fun checkAppUpdate(): AppUpdate? {

        return AppUpdate(
            100,
            "",
            isMandatoryUpdate = false,
            isForceUpdate = false,
            intermediateRelaxTime = 1000000
        )
    }

    private fun checkIfAppUpdateAvailable(appUpdate: AppUpdate?) {
        if (appUpdate != null && appUpdate.appVersionNumber > BuildConfig.VERSION_CODE) {
            Log.d(TAG, "App update available : $appUpdate")

            //Save to prefs
//            sharedPreferenceDataSource.setSharedPreferenceJson(
//                SharedPreferencesKeys.APP_UPDATE,
//                appUpdate
//            )

            Log.d(TAG, "New App update is Saved to shared preferences")

            CoroutineScope(Dispatchers.IO).launch {
                this@AppUpdateDataSourceImplementation.appUpdate.send(appUpdate)
                Log.d(TAG, "Sent new app update available to flow")
            }
        }
    }
}