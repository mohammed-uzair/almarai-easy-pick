package com.almarai.easypick.views.utils.alert_dialog

import android.view.View
import android.widget.Button
import androidx.databinding.BindingAdapter

@BindingAdapter("alertButtonVisibility")
fun Button.bindVisibility(buttonText: String) {
    visibility = if (buttonText.isNotEmpty()) View.VISIBLE else View.GONE
}