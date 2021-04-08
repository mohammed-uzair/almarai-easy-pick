package com.almarai.easypick.views.utils.progress

import android.app.Activity
import android.app.ProgressDialog
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Mohammed Uzair on 1/16/2018.
 * This class is used to show the progress dialogue
 */

@Singleton
class AppProgressDialog @Inject constructor() {
    //region Variables
    private var mProgressDialog: ProgressDialog? = null
    private val isProgressShown = false

    //region Process
    /**
     * This method is used to show the progress dialogue to the UI
     *
     * @param resourceMessage is the message that needs to be shown to the user while progress dialogue is shown
     */
    fun showProgress(resourceMessage: Int, activity: Activity?) {
        if (activity == null) {
            return
        }
        activity.runOnUiThread(Runnable {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog(activity)
            }

            mProgressDialog!!.setMessage(activity.getString(resourceMessage))
            mProgressDialog!!.setCancelable(false)
            if (!mProgressDialog!!.isShowing) {
                mProgressDialog!!.show()
            }
        })
    }

    /**
     * Dismiss progress dialog
     */
    fun dismissProgressBarDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
            mProgressDialog = null
        }
    }

    fun updateStatus(activity: Activity, updatedStatusText: Int) {
        mProgressDialog!!.setMessage(activity.getString(updatedStatusText))
    }

    fun updateStatus(updatedStatusText: String?) {
        mProgressDialog!!.setMessage(updatedStatusText)
    } //endregion
}