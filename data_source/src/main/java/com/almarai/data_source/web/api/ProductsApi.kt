package com.almarai.data_source.web.api

import com.almarai.data.easy_pick_models.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductsApi {
    @GET("hUiZubEZ")
    fun getAllProducts(): Call<List<Product>>
}