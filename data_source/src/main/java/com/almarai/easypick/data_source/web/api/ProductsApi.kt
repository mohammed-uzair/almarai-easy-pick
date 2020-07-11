package com.almarai.easypick.data_source.web.api

import com.almarai.data.easy_pick_models.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApi {
//    @GET("hUiZubEZ")
    @GET("products")
    suspend fun getAllProducts(@Query("routeNumber") routeNumber:Int): List<Product>
}