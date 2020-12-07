package com.almarai.easypick.voice

import android.app.Activity
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.core.content.getSystemService
import javax.inject.Inject
import javax.inject.Singleton

const val ACTIVATION_KEYWORD: String = "phantom"

@Singleton
class VoiceRecognitionServer @Inject constructor(
    private val voiceCommandsManager: VoiceCommandsManager
) {
    companion object {
        private val TAG = "VoiceRecognition"
    }

    private var activity: Activity? = null

    private var isListening: Boolean = false

    private var audioManager: AudioManager? = null
    private lateinit var speechRecognizer: SpeechRecognizer

    /**
     * This will start listening for the keyword
     */
    fun startVoiceRecognition(activity: Activity?) {
        if (SpeechRecognizer.isRecognitionAvailable(activity)) {
            val isSameActivity = this.activity != null && this.activity == activity
            if (!isSameActivity) {
                this.activity = activity
                audioManager = activity?.getSystemService()

                stopVoiceRecognition()
                muteRecognition(true)

                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity)
                Log.d(TAG, "Initialized the voice listener")
                setVoiceRecognitionListener()

                Log.d(TAG, "Voice listening started")
            }

            speechRecognizer.startListening(getIntent())
        } else {
            Log.e(TAG, "No voice recognition service available")
        }
    }

    private fun getIntent() = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, activity?.packageName)
    }

    private fun setVoiceRecognitionListener() {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}

            override fun onError(error: Int) {
                Log.d(TAG, "onError: $error")

                isListening = false

                when (error) {
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> speechRecognizer.cancel()
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                        stopVoiceRecognition()
                        startVoiceRecognition(activity)
                    }
                    SpeechRecognizer.ERROR_CLIENT -> stopVoiceRecognition()
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> requestVoicePermissions()
                }

                speechRecognizer.startListening(getIntent())
            }

            override fun onResults(results: Bundle?) {
                Log.d(TAG, "onResults: ")

                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

                if (matches != null) {
                    if (isListening) {
                        isListening = false
                        stopVoiceRecognition()
                    } else {
                        matches.firstOrNull { it.contains(ACTIVATION_KEYWORD, true) }
                            ?.let {
                                isListening = true

                                //Parse the commands
                                voiceCommandsManager.processCommands(
                                    activity,
                                    matches,
                                    this@VoiceRecognitionServer
                                )
                            }

                        matches.forEach { Log.d(TAG, it) }

                        isListening = false
                        startVoiceRecognition(activity)
                    }
                }
            }
        })
    }

    private fun requestVoicePermissions() {

    }

    fun stopVoiceRecognition() {
        if (this::speechRecognizer.isInitialized) {
            try {
                speechRecognizer.cancel()
                speechRecognizer.stopListening()
                speechRecognizer.destroy()
            } catch (exception: IllegalArgumentException) {

            } catch (exception: RuntimeException) {

            }
        }

        muteRecognition(false)

        Log.d(TAG, "Voice listening stopped")
    }

    @Suppress("DEPRECATION")
    private fun muteRecognition(mute: Boolean) {
        audioManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val flag = if (mute) AudioManager.ADJUST_MUTE else AudioManager.ADJUST_UNMUTE
                it.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, flag, 0)
                it.adjustStreamVolume(AudioManager.STREAM_ALARM, flag, 0)
                it.adjustStreamVolume(AudioManager.STREAM_MUSIC, flag, 0)
                it.adjustStreamVolume(AudioManager.STREAM_RING, flag, 0)
                it.adjustStreamVolume(AudioManager.STREAM_SYSTEM, flag, 0)
            } else {
                it.setStreamMute(AudioManager.STREAM_NOTIFICATION, mute)
                it.setStreamMute(AudioManager.STREAM_ALARM, mute)
                it.setStreamMute(AudioManager.STREAM_MUSIC, mute)
                it.setStreamMute(AudioManager.STREAM_RING, mute)
                it.setStreamMute(AudioManager.STREAM_SYSTEM, mute)
            }
        }

        Log.d(TAG, "muteRecognition: $mute")
    }
}