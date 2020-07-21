package com.almarai.easypick.data_source.web

import android.os.Build
import com.almarai.easypick.data_source.BuildConfig
import com.almarai.easypick.data_source.shared_preference.SharedPreferencesKeys
import com.almarai.easypick.data_source.shared_preference.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.data_source.web.api.ProductsApi
import com.almarai.easypick.data_source.web.api.RoutesApi
import com.almarai.easypick.data_source.web.api.StatisticsApi
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


const val DEFAULT_URL = "http://192.168.1.237:8080/"

class WebService(private val sharedPreferenceDataSource: SharedPreferenceDataSource) {
    val routesApi: RoutesApi = getRetrofit().create(RoutesApi::class.java)
    val productsApi: ProductsApi = getRetrofit().create(ProductsApi::class.java)
    val statisticsApi: StatisticsApi = getRetrofit().create(StatisticsApi::class.java)

    private fun getRetrofit() = buildRetrofit()

    private fun buildRetrofit(): Retrofit {
        val baseUrl = getBaseUrl()
        val builder: OkHttpClient.Builder = OkHttpClient().newBuilder().apply {
//            readTimeout(10, TimeUnit.SECONDS)
//            connectTimeout(5, TimeUnit.SECONDS)
        }

        builder.addInterceptor { chain ->
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
                val format = SimpleDateFormat("dd/MM/yy")
                try {
                    val d = format.parse(curdate)
                    result = d.time
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                salesDate = result.toString()
            }

            val request: Request = chain.request().newBuilder()
                .addHeader("app-version", BuildConfig.VERSION_NAME)
                .addHeader("app-build-type", BuildConfig.BUILD_TYPE)
                .addHeader("device-os-version", Build.VERSION.RELEASE)
                .addHeader("device-sdk-version", Build.VERSION.SDK_INT.toString())
                .addHeader("device-name", Build.MODEL)
                .addHeader("device-manufacturer", Build.MANUFACTURER)
                .addHeader("device-serial-number", getDeviceSerialNumber())
                .addHeader(
                    "depotCode",
                    sharedPreferenceDataSource.getSharedPreferenceString(SharedPreferencesKeys.DEPOT_CODE)
                        ?: "0"
                )
                .addHeader("salesDate", salesDate)
                .addHeader(
                    "routePreference",
                    sharedPreferenceDataSource.getSharedPreferenceString(SharedPreferencesKeys.ROUTE_PREFERENCE)
                        ?: "NA"
                )
                .build()
            chain.proceed(request)
        }

        val client: OkHttpClient = builder.build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun getBaseUrl(): String {
        var url =
            ("${sharedPreferenceDataSource.getSharedPreferenceString(SharedPreferencesKeys.NETWORK_CONFIGURATION_SERVER_IP)}" +
                    ":" +
                    "${sharedPreferenceDataSource.getSharedPreferenceString(SharedPreferencesKeys.NETWORK_CONFIGURATION_SERVER_IP)}" +
                    "/")

        if (url.length < 10) {
            url = DEFAULT_URL
        }

        return url
    }

    private fun getDeviceSerialNumber(): String {
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