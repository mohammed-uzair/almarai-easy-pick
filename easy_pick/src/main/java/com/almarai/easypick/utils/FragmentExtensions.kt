package com.almarai.easypick.utils

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.setTitle(@StringRes id: Int) {
    activity?.title = getString(id)
}