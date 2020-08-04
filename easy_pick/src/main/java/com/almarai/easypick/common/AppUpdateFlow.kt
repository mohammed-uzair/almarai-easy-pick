package com.almarai.easypick.common

import android.content.Intent
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.easypick.BuildConfig
import com.almarai.easypick.R
import com.almarai.easypick.utils.alert_dialog.AppAlertDialog
import com.almarai.easypick.utils.alert_dialog.OnNegativeButtonClickListener
import com.almarai.easypick.utils.alert_dialog.OnPositiveButtonClickListener
import com.almarai.easypick.utils.alert_dialog.showAlertDialog
import com.almarai.repository.api.AppUpdateRepository
import com.almarai.repository.api.ApplicationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.schedule

@Singleton
class AppUpdateFlow @Inject constructor(
    private val applicationRepository: ApplicationRepository,
    private val appUpdateRepository: AppUpdateRepository
) {
    companion object {
        const val TAG = "AppUpdateFlow"
        const val APP_UPDATE_TIMER = "APP_UPDATE_TIMER"
        const val MAX_APP_UPDATE_DENY_COUNTER = 2//Since it starts from 0
        const val APP_UPDATE_ELAPSED_TIMER = 60_000L
        const val ONE_MINUTE = 60_000L
    }

    private var isAppUpdateFlowStarted = false
    private var mAppUpdate: AppUpdate? = null
    private var activity: AppCompatActivity? = null
    private var alertDialog: AppAlertDialog? = null

    fun initiate(
        activity: AppCompatActivity
    ) {
        Log.i(TAG, "Inside app update flow initiate method")
        this.activity = activity

        //Cancel any old timers
        cancelAllTimers()

        //Check for app updates from data source
        val appUpdate = applicationRepository.getAppUpdate()
        val appUpdateRemainingTimer = applicationRepository.getAppUpdateElapsedTimer()
        val appUpdateDenyCounter = applicationRepository.getAppUpdateDenyCounter()

        if (appUpdate != null) {
            mAppUpdate = appUpdate

            Log.i(TAG, "Got app update from local memory : $mAppUpdate")
            Log.i(TAG, "App update deny count : $appUpdateDenyCounter")

            if (appUpdate.isForceUpdate ||
                (appUpdate.isMandatoryUpdate &&
                        (appUpdateDenyCounter > MAX_APP_UPDATE_DENY_COUNTER ||
                                appUpdateRemainingTimer < ONE_MINUTE)
                        )
            ) {
                showAppUpdateAlertToUser(true)
            } else if (!appUpdate.isMandatoryUpdate) showAppUpdateAlertToUser(false)
            else {
                //Start the app update dialog timer
                startNextDialogTimer(appUpdateRemainingTimer, appUpdateDenyCounter)
            }

            Log.i(TAG, "Updated the 'isAppUpdateFlowStarted' flag to true")
            isAppUpdateFlowStarted = true

            Log.i(TAG, "Next dialog should show in $appUpdateRemainingTimer milliseconds")
        }

        listenForAppUpdates()
    }

    private fun listenForAppUpdates() {
        Log.i(TAG, "Listening for app updates")
        //Listen for app updates
        activity?.lifecycleScope?.launch {
            appUpdateRepository.getAppUpdates()?.collect { appUpdate ->
                Log.d(TAG, "Received an update for the app : $appUpdate")

                mAppUpdate = appUpdate

                //Check if already started the app update flow
                if (!isAppUpdateFlowStarted) {
                    Log.i(TAG, "Updated the 'isAppUpdateFlowStarted' flag to true")
                    isAppUpdateFlowStarted = true

                    //Save the app update details locally
                    applicationRepository.setAppUpdate(appUpdate)

                    //Show the app update alert to the user
                    showAppUpdateAlertToUser(appUpdate.isForceUpdate)
                }
            }
        }
    }

    //region Timers
    private fun startNextDialogTimer(
        remainingTimer: Long,
        denyCounter: Int
    ) {
        if (remainingTimer > ONE_MINUTE) {
            //Start the timer
            CoroutineScope(Main).launch {
                Log.i(TAG, "Starting the delay timer for $remainingTimer milliseconds")
                delay(remainingTimer)
                showAppUpdateAlertToUser(denyCounter > MAX_APP_UPDATE_DENY_COUNTER)
            }

            startLoggingElapsedTime(remainingTimer)
        } else {
            showAppUpdateAlertToUser(denyCounter > MAX_APP_UPDATE_DENY_COUNTER)
        }
    }

    private fun startLoggingElapsedTime(remainingTimer: Long) {
        Log.i(TAG, "Starting the logging elapsed time timer")

        //Saved the elapsed time in the data source
        applicationRepository.setAppUpdateElapsedTimer(remainingTimer)

        Timer(APP_UPDATE_TIMER, false).schedule(APP_UPDATE_ELAPSED_TIMER) {
            val remainingDelay = remainingTimer - APP_UPDATE_ELAPSED_TIMER

            //Saved the elapsed time in the data source
            applicationRepository.setAppUpdateElapsedTimer(remainingDelay)
            Log.i(TAG, "Logged elapsed time to data store : $remainingDelay")

            //Close the timer if remaining delay is less than 10 seconds
            if (remainingDelay < (10_000L)) {
                Timer(APP_UPDATE_TIMER).cancel()
                Log.i(TAG, "Log elapsed timer is cancelled")
            }
        }
        Log.i(TAG, "Started logging elapsed timer")
        Log.i(TAG, "Next dialog should show in $remainingTimer milliseconds")
    }
    //endregion

    private fun showAppUpdateAlertToUser(isForceUpdate: Boolean, isManualUpdate: Boolean = false) {
        var updateMessage: String =
            activity?.getString(R.string.alert_message_update_available) ?: ""
        if (isForceUpdate) {
            updateMessage = activity?.getString(R.string.alert_message_force_update) ?: ""
        }

        updateMessage += mAppUpdate?.updateDescription ?: ""

        Log.i(TAG, "App update message : $updateMessage")
        Log.i(TAG, "Showing app update dialog")

        alertDialog = activity?.showAlertDialog(
            R.string.alert_title_update_available,
            updateMessage,
            R.string.alert_button_update,
            animationResourceId = R.raw.anim_update_available,
            positiveButtonClickListener = object : OnPositiveButtonClickListener {
                override fun onClick() {
                    Log.i(TAG, "Positive button clicked")

                    //Update the app on positive button clicked
                    updateApplication()

                    alertDialog?.dismiss()
                }
            }
        )

        //Show negative button only if this is not a force or manual update dialog
        addNegativeButtonToAppAlertDialog(isForceUpdate, isManualUpdate)
    }

    private fun addNegativeButtonToAppAlertDialog(isForceUpdate: Boolean, isManualUpdate: Boolean) {
        if (!isForceUpdate && (isManualUpdate || mAppUpdate?.isMandatoryUpdate == true)) {
            Log.i(TAG, "Adding negative button to the app update dialog")
            alertDialog?.buttonNegativeText = activity?.getString(R.string.alert_button_later) ?: ""

            alertDialog?.negativeButtonClickListener = object : OnNegativeButtonClickListener {
                override fun onClick() {
                    Log.i(TAG, "Negative button clicked")

                    //No need to handle any thing if this is a manual update
                    if (!isManualUpdate) {
                        //Handle negative button clicked
                        handleOnNegativeButtonClicked()
                    }

                    alertDialog?.dismiss()
                }
            }
        }
    }

    private fun handleOnNegativeButtonClicked() {
        if (mAppUpdate != null && mAppUpdate!!.isMandatoryUpdate) {
            //Update the app update deny counter
            val updatedDenyCounter = applicationRepository.increaseAppUpdateDenyCounter()
            Log.i(TAG, "Updated the deny counter to data store : $updatedDenyCounter")

            startNextDialogTimer(mAppUpdate?.intermediateRelaxTime ?: 0, updatedDenyCounter)
        }
    }

    private fun cancelAllTimers() {
        Log.i(TAG, "Cancelling all the timers")
        Timer(APP_UPDATE_TIMER).cancel()
    }

    private fun resetAppUpdateFlow() {
        Log.i(TAG, "Resetting app update flow")

        cancelAllTimers()

        isAppUpdateFlowStarted = false

        applicationRepository.setAppUpdate(null)
        applicationRepository.setAppUpdateElapsedTimer(0)
        applicationRepository.reSetAppUpdateDenyCounter()
    }

    fun checkManualAppUpdate() {
        if (isAppUpdateFlowStarted) {
            showAppUpdateAlertToUser(isForceUpdate = false, isManualUpdate = true)
        } else {
            appUpdateRepository.checkAppUpdate()
        }
    }

    private fun updateApplication() {
        if (activity != null) {
            //Reset app update flow
            resetAppUpdateFlow()

            Log.i(TAG, "Called the update application method")
            val externalStoragePublicDirectory: String =
                Environment.getExternalStorageDirectory().path
            val externalStoragePublicDirectoryFile =
                File(externalStoragePublicDirectory, BuildConfig.APP_NAME + ".apk")

            val uri = FileProvider.getUriForFile(
                activity!!.applicationContext,
                activity!!.applicationContext?.packageName + ".provider",
                externalStoragePublicDirectoryFile
            )

            val installAppIntent = Intent(Intent.ACTION_VIEW)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                .setDataAndType(
                    uri,
                    "application/vnd.android.package-archive"
                )
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                .putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
            activity!!.startActivity(installAppIntent)
        }
    }
}