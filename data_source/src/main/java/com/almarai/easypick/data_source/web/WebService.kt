package com.almarai.easypick.data_source.web

import android.util.Log
import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.easypick.data_source.interfaces.AppUpdateDataSource
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.data_source.request.RequestHeaders
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
import javax.inject.Inject
import javax.inject.Singleton

const val DEFAULT_URL = "http://192.168.0.196:8080/"

@Singleton
class WebService @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource,
    private val gson: Gson,
    private val appUpdateDataSource: AppUpdateDataSource
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
            val requestHeader = RequestHeaders(sharedPreferenceDataSource).getRequestHeader()

            val request: Request = chain.request().newBuilder()
                .addHeader("app-version", requestHeader.appVersion)
                .addHeader("app-build-type", requestHeader.appBuildType)
                .addHeader("device-os-version", requestHeader.deviceOsVersion)
                .addHeader("device-sdk-version", requestHeader.deviceSdkVersion)
                .addHeader("device-name", requestHeader.deviceName)
                .addHeader("device-manufacturer", requestHeader.deviceManufacturer)
                .addHeader("device-serial-number", requestHeader.deviceSerialNumber)
                .addHeader("depot-code", requestHeader.depotCode)
                .addHeader("sales-date", requestHeader.salesDate)
                .addHeader("route-preference", requestHeader.routePreference)
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
}