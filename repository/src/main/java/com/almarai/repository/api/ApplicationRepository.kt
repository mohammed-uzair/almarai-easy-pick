package com.almarai.repository.api

import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.data.easy_pick_models.DataConfiguration
import com.almarai.data.easy_pick_models.NetworkConfiguration
import com.almarai.data.easy_pick_models.User
import com.almarai.repository.utils.AppDataConfiguration

interface ApplicationRepository {
    fun setNetworkConfiguration(networkConfiguration: NetworkConfiguration)
    fun getNetworkConfiguration(): NetworkConfiguration
    fun getNetworkConfigurationStatus(): Boolean

    fun setDataConfiguration(dataConfiguration: DataConfiguration)
    fun getDataConfiguration(): DataConfiguration
    fun getDataConfigurationStatus(): Boolean

    fun setUserLoggedIn(user: User)
    fun getUserLoggedIn(): User
    fun getUserLoggedInStatus(): Boolean

    fun checkAppDataIsConfigured(): AppDataConfiguration

    fun setAppUpdate(appUpdate: AppUpdate?)
    fun setAppUpdateElapsedTimer(updatedTimer: Long)
    fun reSetAppUpdateDenyCounter()
    fun getAppUpdate(): AppUpdate?
    fun getAppUpdateDenyCounter(): Int
    fun getAppUpdateElapsedTimer(): Long
    fun increaseAppUpdateDenyCounter(): Int

    fun isOnBoardingCompleted():Boolean
    fun setOnBoardingCompleted()
}