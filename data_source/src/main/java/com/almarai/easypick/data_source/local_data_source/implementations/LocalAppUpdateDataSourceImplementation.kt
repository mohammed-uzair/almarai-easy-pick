package com.almarai.easypick.data_source.local_data_source.implementations

import android.util.Log
import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.easypick.data_source.BuildConfig
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys
import com.almarai.easypick.data_source.local_data_source.interfaces.LocalAppUpdateDataSource
import com.almarai.easypick.data_source.local_data_source.interfaces.SharedPreferenceDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LocalAppUpdateDataSourceImplementation(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : LocalAppUpdateDataSource {
    companion object {
        const val TAG = "LocalAppUpdateDS"
        private val APP_UPDATE: Channel<AppUpdate> = Channel()
    }

    override suspend fun getAppUpdates() = APP_UPDATE.receiveAsFlow()
    override fun setAppUpdates(appUpdate: AppUpdate?) = checkIfAppUpdateAvailable(appUpdate)

    private fun checkIfAppUpdateAvailable(appUpdate: AppUpdate?) {
        if (appUpdate != null && appUpdate.appVersionNumber > BuildConfig.VERSION_CODE) {
            Log.d(TAG, "App update available : $appUpdate")

            //Save to prefs
            sharedPreferenceDataSource.setSharedPreferenceJson(
                SharedPreferencesKeys.APP_UPDATE,
                appUpdate
            )

            Log.d(TAG, "New App update is Saved to shared preferences")

            CoroutineScope(Dispatchers.IO).launch {
                APP_UPDATE.send(appUpdate)
                Log.d(TAG, "Sent new app update available to flow")
            }
        }
    }
}