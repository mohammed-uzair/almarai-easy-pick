package com.almarai.data.easy_pick_models

sealed class Result<out T : Any> {
    object Fetching : Result<Nothing>()
    data class Success<out  T : Any>(val data: T) : Result<T>()
    data class Error(val exceptionMessage: String = ERROR_OCCURRED) : Result<Nothing>()
}