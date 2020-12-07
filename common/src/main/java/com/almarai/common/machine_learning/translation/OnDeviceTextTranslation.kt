package com.almarai.common.machine_learning.translation

import android.util.Log
import com.almarai.common.utils.APP_LOCALE
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object OnDeviceTextTranslation {
    const val TAG = "OnDeviceTextTranslation"
    private var options = buildNewTranslator()
    private var translator = Translation.getClient(options)

    public fun downloadModel(newModel: Boolean) {
        if (newModel) options = buildNewTranslator()

        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                CoroutineScope(Dispatchers.Default).launch {
                    translateText("Hello, my name is Uzair")
                }
            }
            .addOnFailureListener { exception: Exception ->
                //Alert the user
                Log.e(TAG, "Could not download the language model", exception)
            }
    }

    public suspend fun translateText(textToTranslate: String): String {
        return suspendCoroutine {
            translator.translate(textToTranslate)
                .addOnSuccessListener { translatedText ->
                    it.resume(translatedText)
                }
                .addOnFailureListener { _ ->
                    it.resume(textToTranslate)
                }
        }
    }

    private fun buildNewTranslator(): TranslatorOptions {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(APP_LOCALE.language)
            .build()

        translator = Translation.getClient(options)
        return options
    }

    fun closeTranslator() = translator.close()
}