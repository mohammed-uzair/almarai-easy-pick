package com.almarai.common.logging

sealed class LoggingLevel {
    object UseCases : LoggingLevel()
    object Shallow : LoggingLevel()
    object Deep : LoggingLevel()
}