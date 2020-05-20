package com.almarai.data_source.web

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Route
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebRoutesDataSourceImplementation(private val webClient: WebService) : WebRoutesDataSource {
    //Fetch the data from data source as a web service for now
    private var mWebService: RoutesApi? = webClient.client?.create(RoutesApi::class.java)

    override fun getAllRoutes(
        depotCode: Int,
        salesDate: String,
        routesPreferences: Int,
        mutableRoutes: MutableLiveData<List<Route>>
    ) {
        mWebService?.getAllRoutes()
            ?.enqueue(object : Callback<List<Route>> {
                override fun onFailure(call: Call<List<Route>>, t: Throwable) {
                    println("Debugger mine - Failed it ${t.message}")
                    mutableRoutes.postValue(listOf())
                }

                override fun onResponse(call: Call<List<Route>>, response: Response<List<Route>>) {
                    println("Debugger mine - failed it ${response.body()}")

                    mutableRoutes.postValue(response.body())
                }
            })
    }
}