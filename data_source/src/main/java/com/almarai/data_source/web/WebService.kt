package com.almarai.data_source.web

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://pastebin.com/raw/"

class WebService {
    private var mApi: Retrofit? = null

    val client: Retrofit?
        get() {
            if (mApi == null) {
                mApi = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return mApi
        }
}