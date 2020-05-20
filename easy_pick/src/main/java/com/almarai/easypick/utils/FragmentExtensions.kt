package com.almarai.easypick.utils

import androidx.fragment.app.Fragment

fun Fragment.setTitle(id: Int) {
    activity?.title = getString(id)
}