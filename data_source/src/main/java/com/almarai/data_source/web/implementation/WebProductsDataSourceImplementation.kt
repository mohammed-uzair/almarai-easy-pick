package com.almarai.data_source.web.implementation

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Product
import com.almarai.data_source.web.interfaces.WebProductsDataSource
import com.almarai.data_source.web.WebService
import com.almarai.data_source.web.api.ProductsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebProductsDataSourceImplementation(private val webClient: WebService) :
    WebProductsDataSource {
    //Fetch the data from data source as a web service for now
    private var mWebService: ProductsApi? = webClient.client?.create(
        ProductsApi::class.java)

    override fun getAllProducts(
        depotCode: Int,
        salesDate: String,
        routeNumber: Int,
        routesPreferences: Int,
        mutableItems: MutableLiveData<List<Product>>
    ) {
        mWebService?.getAllProducts()
            ?.enqueue(object : Callback<List<Product>> {
                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    println("Debugger mine - Failed it ${t.message}")
                    mutableItems.postValue(listOf())
                }

                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    println("Debugger mine - failed it ${response.body()}")

                    mutableItems.postValue(response.body())
                }
            })
    }
}