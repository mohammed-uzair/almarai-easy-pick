package com.almarai.easypick.extensions

import android.app.Dialog
import android.view.Gravity
import android.view.WindowManager

internal fun Dialog.positionDialogAtBottom() {
    // set "origin" to top left corner, so to speak
    val currentWindow = window
    val decor = ownerActivity?.window?.decorView
    if (currentWindow != null && decor != null) {
        currentWindow.setGravity(Gravity.BOTTOM or Gravity.CENTER)
        currentWindow.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )

        //Set the dialog to immersive
        currentWindow.decorView.systemUiVisibility = decor.systemUiVisibility

        //Clear the not focusable flag from the window
        currentWindow.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

        // after that, setting values for x and y works "naturally"
        val params = currentWindow.attributes
        params.y = -100
        currentWindow.attributes = params
    }
}