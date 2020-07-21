package com.almarai.easypick.extensions

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal fun RecyclerView.showFocus(position: Int = 0, lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launch {
        delay(500)

        val view = findViewHolderForAdapterPosition(position)?.itemView

        if (view != null && view.isEnabled) {
            //Enable all the hurdle parameters
            view.isFocusable = true
            view.isFocusableInTouchMode = true

            //Request the focus now
            if (view.requestFocus()) {
                view.isFocusableInTouchMode = false
            }
        }
    }
}