package com.almarai.easypick.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

var IS_HARDWARE_KEYBOARD_AVAILABLE = false

internal fun Fragment.hideKeyboard(byForce: Boolean = true) {
    //Check if this device has hardware keyboard, if it does hide the soft keyboard
    //Todo - Change the by force from true to false, for testing in emulator is kept to true
    if (IS_HARDWARE_KEYBOARD_AVAILABLE || byForce) {
        //Close the keyboard on load
        val imm: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }
}

/**
 * This method will let us know if the current device has a hardware keyboard
 *
 * @return true, if the device has a hardware keyboard
 */
internal fun isHardwareKeyboardAvailable(context: Context): Boolean {
    return context.resources.configuration.keyboard != Configuration.KEYBOARD_NOKEYS;


    /*//Todo - Temp work around for honeywell devices
    //Check if the device is a honeywell Cn75 or Cn75e
    val deviceModel = Build.MODEL

    //If this is a Honeywell CX device, it probably contains a hardware keyboard inbuilt,
    //hide the soft keyboard, if so.
    return deviceModel.startsWith("CX") && deviceModel.contains("75")*/
}