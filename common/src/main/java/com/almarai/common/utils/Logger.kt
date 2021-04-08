package com.almarai.common.utils

import android.util.Log

object Logger {
    fun logInfo(tag: String, message: String) {
        Log.i(tag, message)
        saveLog(Log.INFO, tag, message)
    }

    fun logWarning(tag: String, message: String) {
        Log.w(tag, message)
        saveLog(Log.WARN, tag, message)
    }

    fun logError(tag: String, message: String, exception: Exception? = null) {
        Log.e(tag, message, exception)
        saveLog(Log.ERROR, tag, message)
    }

    private fun saveLog(priority: Int, tag: String, message: String, exception: Exception? = null) {
        // Do something with the log, save to the cloud

    }
}