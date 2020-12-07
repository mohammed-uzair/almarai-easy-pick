package com.almarai.easypick.extensions

import android.widget.TextView
import com.almarai.common.machine_learning.translation.OnDeviceTextTranslation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal fun TextView.setTranslatedText(textValue: String) {
    CoroutineScope(Dispatchers.Default).launch {
        val translatedText = OnDeviceTextTranslation.translateText(textValue)

        withContext(Dispatchers.Main) {
            text = translatedText
        }
    }
}