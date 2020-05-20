package com.almarai.data_source.web

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebItemsDataSourceImplementation(private val webClient: WebService) : WebItemsDataSource {
    //Fetch the data from data source as a web service for now
    private var mWebService: ItemsApi? = webClient.client?.create(ItemsApi::class.java)

    override fun getAllItems(
        depotCode: Int,
        salesDate: String,
        routeNumber: Int,
        routesPreferences: Int,
        mutableItems: MutableLiveData<List<Item>>
    ) {
        mWebService?.getAllItems()
            ?.enqueue(object : Callback<List<Item>> {
                override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                    println("Debugger mine - Failed it ${t.message}")
                    mutableItems.postValue(listOf())
                }

                override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                    println("Debugger mine - failed it ${response.body()}")

                    mutableItems.postValue(response.body())
                }
            })
    }
}