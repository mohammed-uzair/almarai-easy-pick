package com.almarai.easypick.data_source.web.api

import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.route.RouteStatus
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ProductsApi {
    @GET("products")
    suspend fun getAllProducts(@Query("routeNumber") routeNumber: Int): List<Product>

    @POST("updateRouteData")
    suspend fun updateRouteData(
        @Query("routeNumber") routeNumber: Int,
        @Body products: List<Product>
    ): RouteStatus

    @POST("discardChanges")
    suspend fun discardAllChanges(@Query("routeNumber") routeNumber: Int): String
}