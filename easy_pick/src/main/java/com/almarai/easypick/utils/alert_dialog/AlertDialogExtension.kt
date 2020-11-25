package com.almarai.easypick.utils.alert_dialog

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.almarai.easypick.R

internal fun AppCompatActivity.showAlertDialog(
    @StringRes alertTitle: Int = R.string.alert_default_title,
    alertMessage: String,
    @StringRes buttonPositiveText: Int = R.string.alert_button_ok,
    @StringRes buttonNegativeText: Int = R.string.empty,
    @StringRes buttonNeutralText: Int = R.string.empty,
    @RawRes animationResourceId: Int = R.raw.anim_tick_panda,
    positiveButtonClickListener: OnPositiveButtonClickListener? = null,
    negativeButtonClickListener: OnNegativeButtonClickListener? = null,
    neutralButtonClickListener: OnNeutralButtonClickListener? = null
) =
    showAlert(
        alertTitle,
        alertMessage,
        buttonPositiveText,
        buttonNegativeText,
        buttonNeutralText,
        animationResourceId,
        positiveButtonClickListener,
        negativeButtonClickListener,
        neutralButtonClickListener,
        this
    )

internal fun AppCompatActivity.showAlertDialog(
    @StringRes alertTitle: Int = R.string.alert_default_title,
    alertMessage: Int,
    @StringRes buttonPositiveText: Int = R.string.alert_button_ok,
    @StringRes buttonNegativeText: Int = R.string.empty,
    @StringRes buttonNeutralText: Int = R.string.empty,
    @RawRes animationResourceId: Int = R.raw.anim_tick_panda,
    positiveButtonClickListener: OnPositiveButtonClickListener? = null,
    negativeButtonClickListener: OnNegativeButtonClickListener? = null,
    neutralButtonClickListener: OnNeutralButtonClickListener? = null
) =
    showAlert(
        alertTitle,
        getString(alertMessage),
        buttonPositiveText,
        buttonNegativeText,
        buttonNeutralText,
        animationResourceId,
        positiveButtonClickListener,
        negativeButtonClickListener,
        neutralButtonClickListener,
        this
    )

internal fun AppCompatActivity.hideAlertDialog() = hideAlertDialog(supportFragmentManager)

internal fun Fragment.showAlertDialog(
    @StringRes alertTitle: Int = R.string.alert_default_title,
    alertMessage: String = getString(R.string.alert_default_message),
    @StringRes buttonPositiveText: Int = R.string.alert_button_ok,
    @StringRes buttonNegativeText: Int = R.string.empty,
    @StringRes buttonNeutralText: Int = R.string.empty,
    @RawRes animationResourceId: Int = R.raw.anim_tick_panda,
    positiveButtonClickListener: OnPositiveButtonClickListener? = null,
    negativeButtonClickListener: OnNegativeButtonClickListener? = null,
    neutralButtonClickListener: OnNeutralButtonClickListener? = null
) =
    showAlert(
        alertTitle,
        alertMessage,
        buttonPositiveText,
        buttonNegativeText,
        buttonNeutralText,
        animationResourceId,
        positiveButtonClickListener,
        negativeButtonClickListener,
        neutralButtonClickListener,
        requireActivity() as AppCompatActivity
    )

internal fun Fragment.showAlertDialog(
    @StringRes alertTitle: Int = R.string.alert_default_title,
    @StringRes alertMessage: Int = R.string.alert_default_message,
    @StringRes buttonPositiveText: Int = R.string.alert_button_ok,
    @StringRes buttonNegativeText: Int = R.string.empty,
    @StringRes buttonNeutralText: Int = R.string.empty,
    @RawRes animationResourceId: Int = R.raw.anim_tick_panda,
    positiveButtonClickListener: OnPositiveButtonClickListener? = null,
    negativeButtonClickListener: OnNegativeButtonClickListener? = null,
    neutralButtonClickListener: OnNeutralButtonClickListener? = null
) =
    showAlert(
        alertTitle,
        getString(alertMessage),
        buttonPositiveText,
        buttonNegativeText,
        buttonNeutralText,
        animationResourceId,
        positiveButtonClickListener,
        negativeButtonClickListener,
        neutralButtonClickListener,
        requireActivity() as AppCompatActivity
    )

internal fun Fragment.hideAlertDialog() = hideAlertDialog(requireActivity().supportFragmentManager)

interface OnPositiveButtonClickListener {
    fun onClick()
}

interface OnNegativeButtonClickListener {
    fun onClick()
}

interface OnNeutralButtonClickListener {
    fun onClick()
}

private fun showAlert(
    @StringRes alertTitle: Int = R.string.alert_default_message,
    alertMessage: String = "",
    @StringRes buttonPositiveText: Int,
    @StringRes buttonNegativeText: Int,
    @StringRes buttonNeutralText: Int,
    @RawRes animationResourceId: Int = R.raw.anim_tick_panda,
    positiveButtonClickListener: OnPositiveButtonClickListener? = null,
    negativeButtonClickListener: OnNegativeButtonClickListener? = null,
    neutralButtonClickListener: OnNeutralButtonClickListener? = null,
    activity: AppCompatActivity
): AppAlertDialog {
    val alertDialog = AppAlertDialog().apply {
        setBundle(
            activity.getString(alertTitle),
            alertMessage,
            activity.getString(buttonPositiveText),
            activity.getString(buttonNegativeText),
            activity.getString(buttonNeutralText),
            animationResourceId
        )

        this.positiveButtonClickListener = positiveButtonClickListener
        this.negativeButtonClickListener = negativeButtonClickListener
        this.neutralButtonClickListener = neutralButtonClickListener
    }

    val fragmentManager = activity.supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()

    hideAlertDialog(fragmentManager)

    //Show the dialog
    fragmentTransaction.addToBackStack(AppAlertDialog.TAG)

    alertDialog.show(fragmentManager, AppAlertDialog.TAG)
    return alertDialog
}

private fun hideAlertDialog(fragmentManager: FragmentManager) {
    //Remove previous dialog
    while (fragmentManager.findFragmentByTag(AppAlertDialog.TAG) != null && ((fragmentManager.findFragmentByTag(AppAlertDialog.TAG)) as AppAlertDialog).dialog!!.isShowing) {
        val previousDialog = fragmentManager.findFragmentByTag(AppAlertDialog.TAG)

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.remove(previousDialog!!)
        (previousDialog as AppAlertDialog).dismiss()
    }
}