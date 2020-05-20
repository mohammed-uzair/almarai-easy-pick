package com.almarai.data_source.web

import com.almarai.data.easy_pick_models.Item
import retrofit2.Call
import retrofit2.http.GET

interface ItemsApi {
    @GET("hUiZubEZ")
    fun getAllItems(): Call<List<Item>>
}