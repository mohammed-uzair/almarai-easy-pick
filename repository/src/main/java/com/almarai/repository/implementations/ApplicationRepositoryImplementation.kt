package com.almarai.repository.implementations

import com.almarai.repository.api.ApplicationRepository

object ApplicationRepositoryImplementation : ApplicationRepository {
    override fun networkConfigurationCompleted(): Boolean {
        return true
    }

    override fun userLoggedIn(): Boolean {
        return true
    }

    override fun dataConfigurationCompleted(): Boolean {
        return true
    }
}