package com.almarai.easypick.utils.binding_adapters

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter

//@BindingAdapter("onEditorEnterAction")
//fun EditText.onEditorEnterAction(f: Function1<String, Unit>?) {
//    if (f == null) setOnEditorActionListener(null)
//    else setOnEditorActionListener { v, actionId, event ->
//
//        val imeAction = when (actionId) {
//            EditorInfo.IME_ACTION_DONE,
//            EditorInfo.IME_ACTION_SEND,
//            EditorInfo.IME_ACTION_GO -> true
//            else -> false
//        }
//
//        val keyEvent =
//            (event?.keyCode == KeyEvent.KEYCODE_ENTER || event?.keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
//                    && event.action == KeyEvent.ACTION_UP
//
//        if (imeAction or keyEvent) true.also { f(v.editableText.toString()) } else false
//    }
//}