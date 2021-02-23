package com.almarai.easypick.data_source.web

import android.util.Log
import com.almarai.common.app_update.AppUpdates
import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.easypick.data_source.BuildConfig
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.data_source.local_data_source.SharedPreferencesKeys
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
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "WebService"
const val DEFAULT_URL = "http://192.168.1.12:8080/"

@Singleton
class WebService @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource,
    private val gson: Gson,
    private val appUpdates: AppUpdates
) {
    private var retrofit: Retrofit? = buildRetrofit()
    private var serverIp: String = DEFAULT_URL

    val routesApi: RoutesApi
        get() {
            return getRetrofitService().create(RoutesApi::class.java)
        }
    val productsApi: ProductsApi
        get() {
            return getRetrofitService().create(ProductsApi::class.java)
        }
    val statisticsApi: StatisticsApi
        get() {
            return getRetrofitService().create(StatisticsApi::class.java)
        }
    val appUpdateApi: AppUpdateApi
        get() {
            return getRetrofitService().create(AppUpdateApi::class.java)
        }

    private fun getRetrofitService(): Retrofit {
        if (serverIp == getServerIp() && retrofit != null)
            return retrofit!!

        return buildRetrofit()
    }

    private fun buildRetrofit(): Retrofit {
        val baseUrl = getBaseUrl()

        val builder: OkHttpClient.Builder = OkHttpClient().newBuilder().apply {
            readTimeout(10, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
        }

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
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
            val newAppUpdates: String = response.header("app-update") ?: ""

            if (newAppUpdates.isNotEmpty()) {
                //Convert the app update from Json
                try {
                    val appUpdate = gson.fromJson(newAppUpdates, AppUpdate::class.java)
                    appUpdates.setAppUpdate(appUpdate)
                } catch (exception: JsonSyntaxException) {
                    Log.e(TAG, "Error on parsing app update", exception)
                }
            }

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
        var url = getServerIp()

        if (url.length < 10) {
            url = DEFAULT_URL
        } else {
            serverIp = url
        }

        return url
    }

    private fun getServerIp() = (
            "http://${sharedPreferenceDataSource.getSharedPreferenceString(SharedPreferencesKeys.NETWORK_CONFIGURATION_SERVER_IP)}:${
                sharedPreferenceDataSource.getSharedPreferenceString(
                    SharedPreferencesKeys.NETWORK_CONFIGURATION_SERVER_PORT
                )
            }/")
}