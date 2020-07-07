package com.almarai.easypick.data_source.web

import com.almarai.easypick.data_source.web.api.ProductsApi
import com.almarai.easypick.data_source.web.api.RoutesApi
import com.almarai.easypick.data_source.web.api.StatisticsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//private const val BASE_URL = "https://pastebin.com/raw/"
private var BASE_URL = "http://192.168.1.237:8080/"

class WebService {
    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val routesApi: RoutesApi = getRetrofit().create(RoutesApi::class.java)
    val productsApi: ProductsApi = getRetrofit().create(ProductsApi::class.java)
    val statisticsApi: StatisticsApi = getRetrofit().create(StatisticsApi::class.java)
}