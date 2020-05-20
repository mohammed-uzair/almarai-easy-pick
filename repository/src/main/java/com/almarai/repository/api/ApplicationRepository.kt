package com.almarai.repository.api

interface ApplicationRepository {
    fun networkConfigurationCompleted(): Boolean
    fun dataConfigurationCompleted(): Boolean
    fun userLoggedIn(): Boolean
}