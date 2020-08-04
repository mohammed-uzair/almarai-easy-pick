package com.almarai.easypick.data_source.web

import android.os.Build
import android.util.Log
import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.easypick.data_source.BuildConfig
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys
import com.almarai.easypick.data_source.local_data_source.interfaces.LocalAppUpdateDataSource
import com.almarai.easypick.data_source.local_data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.data_source.web.api.AppUpdateApi
import com.almarai.easypick.data_source.web.api.ProductsApi
import com.almarai.easypick.data_source.web.api.RoutesApi
import com.almarai.easypick.data_source.web.api.StatisticsApi
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

const val DEFAULT_URL = "http://192.168.1.237:8080/"

@Singleton
class WebService @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource,
    private val gson: Gson,
    private val appUpdateDataSource: LocalAppUpdateDataSource
) {
    companion object {
        const val TAG = "WebService"
    }

    val routesApi: RoutesApi = getRetrofit().create(RoutesApi::class.java)
    val productsApi: ProductsApi = getRetrofit().create(ProductsApi::class.java)
    val statisticsApi: StatisticsApi = getRetrofit().create(StatisticsApi::class.java)
    val appUpdateApi: AppUpdateApi = getRetrofit().create(AppUpdateApi::class.java)

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

            val depotCode =
                sharedPreferenceDataSource.getSharedPreferenceString(SharedPreferencesKeys.DEPOT_CODE)
                    ?: "0"
            val routePreference =
                sharedPreferenceDataSource.getSharedPreferenceString(SharedPreferencesKeys.ROUTE_PREFERENCE)
                    ?: "NA"
            val request: Request = chain.request().newBuilder()
                .addHeader("app-version", BuildConfig.VERSION_NAME)
                .addHeader("app-build-type", BuildConfig.BUILD_TYPE)
                .addHeader("device-os-version", Build.VERSION.RELEASE)
                .addHeader("device-sdk-version", Build.VERSION.SDK_INT.toString())
                .addHeader("device-name", Build.MODEL)
                .addHeader("device-manufacturer", Build.MANUFACTURER)
                .addHeader("device-serial-number", getDeviceSerialNumber())
                .addHeader("depot-code", depotCode)
                .addHeader("sales-date", salesDate)
                .addHeader("route-preference", routePreference)
                .build()

            val response: Response = chain.proceed(request)
            val appUpdates: String = response.header("app-update") ?: ""

            var appUpdate: AppUpdate? = null
            if (appUpdates.isNotEmpty()) {
                //Convert the app update from Json
                try {
                    appUpdate = gson.fromJson(appUpdates, AppUpdate::class.java)
                } catch (exception: JsonSyntaxException) {
                    Log.e(TAG, "Error on parsing app update", exception)
                }
            }
            appUpdateDataSource.setAppUpdates(appUpdate)

            response
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
        //More promissing one, try using below
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