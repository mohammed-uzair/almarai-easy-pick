package com.almarai.easypick.data_source.web.implementation

import android.util.Log
import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.easypick.data_source.BuildConfig
import com.almarai.easypick.data_source.interfaces.AppUpdateDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebAppUpdateDataSourceImplementation @Inject constructor() :
    AppUpdateDataSource {
    companion object {
        const val TAG = "WebAppUpdateDSImpl"
    }

    private val appUpdate: Channel<AppUpdate> = Channel()

    @ExperimentalCoroutinesApi
    override suspend fun getAppUpdates() = appUpdate.receiveAsFlow()

    /**
     * When ever you make any web request, if there is any app update,
     * the server will push the update in every request you make in its response,
     * and in the webservice class this method will automatically be called.
     */
    override fun setAppUpdates(appUpdate: AppUpdate?) = checkIfAppUpdateAvailable(appUpdate)

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
                this@WebAppUpdateDataSourceImplementation.appUpdate.send(appUpdate)
                Log.d(TAG, "Sent new app update available to flow")
            }
        }
    }

    override suspend fun checkAppUpdate(): AppUpdate? {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val appUpdate = webAppUpdateDataSource.checkAppUpdate()
//                if (appUpdate != null) {
//                    appUpdateDataSource.setAppUpdates(appUpdate)
//                }
//            } catch (exception: Exception) {
//                Log.e(TAG, "Error in fetching app update", exception)
//            }
//        }
        return null
    }
}