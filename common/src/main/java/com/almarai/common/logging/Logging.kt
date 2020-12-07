package com.almarai.common.logging

import android.util.Log

object Logging {
    fun saveLog(
        tag: String, message: String, exception: Exception? = null, loggingLevel: LoggingLevel
    ) {
        //Show the logging only in the debug mode
        if (exception != null) Log.d(tag, "$message Exception: $exception") else Log.d(
            tag,
            message
        )
    }
}