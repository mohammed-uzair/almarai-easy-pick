package com.almarai.easypick.utils.alert_dialog

import android.app.Activity
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.almarai.easypick.R

sealed class ButtonFocus {
    object Positive : ButtonFocus()
    object Negative : ButtonFocus()
    object Neutral : ButtonFocus()
}

sealed class ButtonEmphasis {
    object High : ButtonEmphasis()
    object Medium : ButtonEmphasis()
    object Low : ButtonEmphasis()
}

@FunctionalInterface
interface OnPositiveButtonClick {
    fun onClick()
}

interface OnNegativeButtonClick {
    fun onClick()
}

interface OnNeutralButtonClick {
    fun onClick()
}

internal fun Fragment.showAlertDialog(
    @StringRes alertTitle: Int = R.string.alert_default_title,
    @StringRes alertMessage: Int = R.string.alert_default_message,
    @StringRes buttonPositiveText: Int = R.string.alert_button_ok,
    @StringRes buttonNegativeText: Int = -1,
    @StringRes buttonNeutralText: Int = -1,
    positiveButtonListener: OnPositiveButtonClick? = null,
    negativeButtonListener: OnNegativeButtonClick? = null,
    neutralButtonListener: OnNeutralButtonClick? = null
) {
    val alertDialog = AlertDialog()
    alertDialog.setBundleDate(
        alertTitle,
        alertMessage,
        buttonPositiveText,
        buttonNegativeText,
        buttonNeutralText
    )

    val fragmentManager = requireActivity().supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()

    hideAlertDialog(fragmentManager, fragmentTransaction)

    //Show the dialog
    fragmentTransaction.addToBackStack(AlertDialog.TAG)
    alertDialog.show(fragmentManager, AlertDialog.TAG)

    alertDialog.setPositiveButtonListener(positiveButtonListener)
    alertDialog.setNegativeButtonListener(negativeButtonListener)
    alertDialog.setNeutralButtonListener(neutralButtonListener)
}

private fun hideAlertDialog(
    fragmentManager: FragmentManager,
    fragmentTransaction: FragmentTransaction
) {
    //Remove previous dialog
    val previousDialog = fragmentManager.findFragmentByTag(AlertDialog.TAG)
    if (previousDialog != null) {
        fragmentTransaction.remove(previousDialog)
        (previousDialog as AlertDialog).dismiss()
    }
}


/*
private fun Activity.showAlertDialog(
    @StringRes alertTitle: Int = R.string.alert,
    @StringRes alertMessage: Int = R.string.alert,
    @StringRes buttonPositiveText: Int = R.string.alert_button_ok,
    @StringRes buttonNegativeText: Int = -1,
    @StringRes buttonNeutralText: Int = -1,
    positiveButton: OnPositiveButtonClick?,
    negativeButton: OnNegativeButtonClick?,
    neutralButton: OnNeutralButtonClick?
) {
    val alertDialog = AlertDialog()


}
*/


//
//internal fun Dialog.showAlert(
//    @StringRes alertTitle: Int = R.string.alert,
//    @StringRes alertMessage: Int = R.string.alert,
//    @StringRes buttonPositiveText: Int = R.string.alert_button_ok,
//    @StringRes buttonNegativeText: Int = -1,
//    @StringRes buttonNeutralText: Int = -1,
//    positiveButtonListener: OnAlertPositiveButtonClickListener?,
//    negativeButtonListener: OnAlertNegativeButtonClickListener?,
//    neutralButtonListener: OnAlertNeutralButtonClickListener?
//) = showAlertDialog(
//    alertTitle,
//    alertMessage,
//    buttonPositiveText,
//    buttonNegativeText,
//    buttonNeutralText,
//    positiveButtonListener,
//    negativeButtonListener,
//    neutralButtonListener
//)
//
//internal fun Activity.showAlert(
//    @StringRes alertTitle: Int = R.string.alert,
//    @StringRes alertMessage: Int = R.string.alert,
//    @StringRes buttonPositiveText: Int = R.string.alert_button_ok,
//    @StringRes buttonNegativeText: Int = -1,
//    @StringRes buttonNeutralText: Int = -1,
//    positiveButtonListener: OnAlertPositiveButtonClickListener?,
//    negativeButtonListener: OnAlertNegativeButtonClickListener?,
//    neutralButtonListener: OnAlertNeutralButtonClickListener?
//) = showAlertDialog(
//    alertTitle,
//    alertMessage,
//    buttonPositiveText,
//    buttonNegativeText,
//    buttonNeutralText,
//    positiveButtonListener,
//    negativeButtonListener,
//    neutralButtonListener
//)

internal fun Fragment.hideAlertDialog(){
    val fragmentManager = requireActivity().supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()

    hideAlertDialog(fragmentManager, fragmentTransaction)
}









