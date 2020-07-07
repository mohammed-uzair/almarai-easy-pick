package com.almarai.easypick.extensions

import androidx.fragment.app.Fragment
import com.almarai.easypick.MainActivity

internal interface OnBackPressListener {
    fun onBackPressed()
}

internal fun Fragment.setOnBackPressListener(fragment: OnBackPressListener?) {
    (requireActivity() as MainActivity).backPressListener = fragment
}

internal fun Fragment.goBack() {
    (requireActivity() as MainActivity).backPressListener = null
    (requireActivity() as MainActivity).onBackPressed()
}