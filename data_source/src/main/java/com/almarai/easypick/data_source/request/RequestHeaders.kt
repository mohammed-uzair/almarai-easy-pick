package com.almarai.easypick.data_source.request

//import android.provider.Settings
import android.os.Build
import com.almarai.common.date_time.AppDateTimeFormat
import com.almarai.common.date_time.DateUtil.getCurrentDate
import com.almarai.common.date_time.DateUtil.getDate
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestHeaders @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) {
    fun getRequestHeader(): RequestHeader {
        val depotCode =
            sharedPreferenceDataSource.getSharedPreferenceString(SharedPreferencesKeys.DEPOT_CODE)
                ?: "0"

        var salesDate =
            sharedPreferenceDataSource.getSharedPreferenceString(SharedPreferencesKeys.SALES_DATE)

        if (salesDate == null || salesDate.length < 13) salesDate =
            getDate(getCurrentDate(), AppDateTimeFormat.formatDDMMYY).toString()

        val routePreference =
            sharedPreferenceDataSource.getSharedPreferenceString(SharedPreferencesKeys.ROUTE_PREFERENCE)
                ?: "NA"

        val deviceSerialNumber = getDeviceSerialNumber()

        return RequestHeader(
            depotCode, salesDate, routePreference, deviceSerialNumber = deviceSerialNumber
        )
    }

    private fun getDeviceSerialNumber(): String {
        //More promising one, try using below
//        val number = Settings.Secure.getString(context.contentResolver,
//            Settings.Secure.ANDROID_ID)

        var deviceId = ""
        try {
            deviceId = Build::class.java.getField("SERIAL")[null] as String
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return deviceId
    }
}