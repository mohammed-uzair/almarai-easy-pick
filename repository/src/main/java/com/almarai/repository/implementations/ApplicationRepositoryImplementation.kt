package com.almarai.repository.implementations

import com.almarai.business.utils.AppDateTimeFormat
import com.almarai.common.date_time.DateUtil
import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.data.easy_pick_models.DataConfiguration
import com.almarai.data.easy_pick_models.NetworkConfiguration
import com.almarai.data.easy_pick_models.User
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.APP_UPDATE
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.APP_UPDATE_DENY_COUNTER
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.APP_UPDATE_ELAPSED_TIMER
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.DATA_CONFIGURATION_STATUS
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.DEPOT_CODE
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.IS_ON_BOARDING_COMPLETED
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.NETWORK_CONFIGURATION_SERVER_IP
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.NETWORK_CONFIGURATION_SERVER_PORT
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.NETWORK_CONFIGURATION_STATUS
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.ROUTE_PREFERENCE
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.SALES_DATE
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.USER_LOGGED_IN_STATUS
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.USER_LOGGED_USER_ID
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys.Companion.USER_LOGGED_USER_NAME
import com.almarai.repository.api.ApplicationRepository
import com.almarai.repository.utils.AppDataConfiguration
import javax.inject.Inject

class ApplicationRepositoryImplementation
@Inject constructor(private val sharedPreferenceDataSource: SharedPreferenceDataSource) :
    ApplicationRepository {
    override fun setNetworkConfiguration(networkConfiguration: NetworkConfiguration) {
        sharedPreferenceDataSource.setSharedPreference(
            NETWORK_CONFIGURATION_SERVER_IP,
            networkConfiguration.serverIpAddress
        )
        sharedPreferenceDataSource.setSharedPreference(
            NETWORK_CONFIGURATION_SERVER_PORT,
            networkConfiguration.serverPort
        )

        sharedPreferenceDataSource.setSharedPreference(NETWORK_CONFIGURATION_STATUS, true)
    }

    override fun getNetworkConfiguration(): NetworkConfiguration {
        val serverIp =
            sharedPreferenceDataSource.getSharedPreferenceString(NETWORK_CONFIGURATION_SERVER_IP)
        val serverPort =
            sharedPreferenceDataSource.getSharedPreferenceString(NETWORK_CONFIGURATION_SERVER_PORT)

        return NetworkConfiguration(serverIp, serverPort)
    }

    override fun getNetworkConfigurationStatus() =
        sharedPreferenceDataSource.getSharedPreferenceBoolean(
            NETWORK_CONFIGURATION_STATUS
        )

    override fun setDataConfiguration(dataConfiguration: DataConfiguration) {
        val salesDate = DateUtil.convertDateToMilliseconds(
            dataConfiguration.salesDate,
            AppDateTimeFormat.formatDDMMYYYY
        )

        sharedPreferenceDataSource.setSharedPreference(SALES_DATE, salesDate.toString())

        sharedPreferenceDataSource.setSharedPreference(DEPOT_CODE, dataConfiguration.depotCode)

        sharedPreferenceDataSource.setSharedPreference(
            ROUTE_PREFERENCE,
            dataConfiguration.routeGroup
        )

        sharedPreferenceDataSource.setSharedPreference(DATA_CONFIGURATION_STATUS, true)
    }

    override fun getDataConfiguration(): DataConfiguration {
        val salesDate =
            sharedPreferenceDataSource.getSharedPreferenceString(SALES_DATE)
        val depotCode =
            sharedPreferenceDataSource.getSharedPreferenceString(DEPOT_CODE)
        val routePreference =
            sharedPreferenceDataSource.getSharedPreferenceString(ROUTE_PREFERENCE)

        return DataConfiguration(salesDate, depotCode, routePreference)
    }

    override fun getDataConfigurationStatus() =
        sharedPreferenceDataSource.getSharedPreferenceBoolean(DATA_CONFIGURATION_STATUS)

    override fun setUserLoggedIn(user: User) {
        sharedPreferenceDataSource.setSharedPreference(
            USER_LOGGED_USER_NAME,
            user.userName
        )
        sharedPreferenceDataSource.setSharedPreference(
            USER_LOGGED_USER_ID,
            user.userId
        )

        sharedPreferenceDataSource.setSharedPreference(USER_LOGGED_IN_STATUS, true)
    }

    override fun getUserLoggedIn(): User {
        val userName =
            sharedPreferenceDataSource.getSharedPreferenceString(USER_LOGGED_USER_NAME)
        val userId =
            sharedPreferenceDataSource.getSharedPreferenceInt(USER_LOGGED_USER_ID)

        return User(userName, userId)
    }

    override fun getUserLoggedInStatus() =
        sharedPreferenceDataSource.getSharedPreferenceBoolean(USER_LOGGED_IN_STATUS)

    override fun checkAppDataIsConfigured(): AppDataConfiguration {
        return if (!getNetworkConfigurationStatus())
            AppDataConfiguration.NetworkConfiguration
        else if (!getDataConfigurationStatus())
            AppDataConfiguration.DataConfiguration
        else
            AppDataConfiguration.Home
    }

    override fun setAppUpdate(appUpdate: AppUpdate?) =
        sharedPreferenceDataSource.setSharedPreferenceJson(
            APP_UPDATE, appUpdate
        )

    override fun getAppUpdate() =
        (sharedPreferenceDataSource.getSharedPreferenceJsonObject(
            APP_UPDATE,
            AppUpdate::class.java
        )) as AppUpdate?

    override fun getAppUpdateDenyCounter() =
        sharedPreferenceDataSource.getSharedPreferenceInt(APP_UPDATE_DENY_COUNTER)

    override fun increaseAppUpdateDenyCounter(): Int {
        val result = sharedPreferenceDataSource.getSharedPreferenceInt(APP_UPDATE_DENY_COUNTER) + 1
        sharedPreferenceDataSource.setSharedPreference(APP_UPDATE_DENY_COUNTER, result)

        return result
    }

    override fun setOnBoardingCompleted() = sharedPreferenceDataSource.setSharedPreference(
        IS_ON_BOARDING_COMPLETED, true
    )

    override fun isOnBoardingCompleted() =
        sharedPreferenceDataSource.getSharedPreferenceBoolean(IS_ON_BOARDING_COMPLETED)

    override fun getAppUpdateElapsedTimer() =
        sharedPreferenceDataSource.getSharedPreferenceLong(APP_UPDATE_ELAPSED_TIMER)

    override fun setAppUpdateElapsedTimer(updatedTimer: Long) =
        sharedPreferenceDataSource.setSharedPreference(APP_UPDATE_ELAPSED_TIMER, updatedTimer)

    override fun reSetAppUpdateDenyCounter() =
        sharedPreferenceDataSource.setSharedPreference(APP_UPDATE_DENY_COUNTER, 0)
}