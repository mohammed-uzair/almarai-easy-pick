package com.almarai.easypick.data_source.request

import android.os.Build
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestHeaders @Inject constructor(private val sharedPreferenceDataSource: SharedPreferenceDataSource) {
    fun getRequestHeader(): RequestHeader {
        val depotCode =
            sharedPreferenceDataSource.getSharedPreferenceString(SharedPreferencesKeys.DEPOT_CODE)
                ?: "0"

        var salesDate =
            sharedPreferenceDataSource.getSharedPreferenceString(SharedPreferencesKeys.SALES_DATE)

        if (salesDate == null || salesDate.length < 13) {
            val dateFormat: DateFormat = SimpleDateFormat(
                "dd/MM/yy",
                Locale.ENGLISH
            )
            val calendar = Calendar.getInstance(Locale.ENGLISH)
            val curdate = dateFormat.format(calendar.time)


            var result = 1L

            //Locale should always be in English for the date
            val format = SimpleDateFormat("dd/MM/yy", Locale.ENGLISH)
            try {
                val d = format.parse(curdate)
                result = d.time
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            salesDate = result.toString()
        }


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
//        Settings.Secure.getString(context.getContentResolver(),
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