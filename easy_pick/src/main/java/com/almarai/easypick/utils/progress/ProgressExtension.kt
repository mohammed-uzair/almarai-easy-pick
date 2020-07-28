package com.almarai.easypick.utils.progress

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.almarai.easypick.MainActivity
import com.almarai.easypick.R

internal fun AppCompatActivity.showProgress(@StringRes messageResourceId: Int = R.string.loading_title) {
    val activity = this as MainActivity
    activity.progressDialog.showProgress(messageResourceId, activity)
}

internal fun AppCompatActivity.hideProgress() {
    val activity = this as MainActivity
    activity.progressDialog.dismissProgressBarDialog()
}

internal fun Fragment.showProgress(@StringRes messageResourceId: Int = R.string.loading_title) {
    val activity = requireActivity() as MainActivity
    activity.progressDialog.showProgress(messageResourceId, activity)
}

internal fun Fragment.hideProgress() {
    val activity = requireActivity() as MainActivity
    activity.progressDialog.dismissProgressBarDialog()
}