package com.almarai.easypick.ml

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object OnDeviceTextTranslation {
    private val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.ARABIC)
        .build()

    private val englishArabicTranslator = Translation.getClient(options)

    private val conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()

    private fun mlTranslation() {
        englishArabicTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                CoroutineScope(Dispatchers.Default).launch {
                    translateText("Hello, my name is Uzair")
                }
            }
            .addOnFailureListener { exception: Exception ->
            }
    }

    suspend fun translateText(textToTranslate: String): String {
        return suspendCoroutine {
            //mlTranslation()

            englishArabicTranslator.translate(textToTranslate)
                .addOnSuccessListener { translatedText ->
                    it.resume(translatedText)
                }
                .addOnFailureListener { exception ->
                    it.resume("NA")
                }
        }
    }
}