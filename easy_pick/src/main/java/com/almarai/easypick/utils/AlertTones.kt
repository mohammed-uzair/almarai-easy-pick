package com.almarai.easypick.utils

import android.media.AudioManager
import android.media.ToneGenerator

object AlertTones {
    private const val POSITIVE_TONE = ToneGenerator.TONE_PROP_ACK
    private const val NEGATIVE_TONE = ToneGenerator.TONE_SUP_ERROR
    private const val TONE_TIME_MILLI_SECONDS = 100
    private val mToneGenerator = ToneGenerator(
        AudioManager.STREAM_MUSIC,
        7 * 14
    ) // Raising volume to 100% (For eg. 7 * 14 ~ 100)
    private var audioManager: AudioManager? = null

    fun playTone(isPositive: Boolean) {
        if (audioManager != null) {
            mToneGenerator.stopTone()
            //Play positive tone
            if (isPositive) mToneGenerator.startTone(
                POSITIVE_TONE,
                TONE_TIME_MILLI_SECONDS
            ) else mToneGenerator.startTone(NEGATIVE_TONE, TONE_TIME_MILLI_SECONDS)
        }
    }
}