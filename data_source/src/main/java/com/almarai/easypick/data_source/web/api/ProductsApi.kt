package com.almarai.easypick.data_source.web.api

import com.almarai.data.easy_pick_models.Product
import retrofit2.http.GET

interface ProductsApi {
    @GET("hUiZubEZ")
    suspend fun getAllProducts(): List<Product>
}