package com.almarai.data_source.web.implementation

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Statistics
import com.almarai.data_source.web.WebService
import com.almarai.data_source.web.api.StatisticsApi
import com.almarai.data_source.web.interfaces.WebStatisticsDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebStatisticsDataSourceImplementation(private val webClient: WebService) :
    WebStatisticsDataSource {
    //Fetch the data from data source as a web service for now
    private var mWebService = webClient.client?.create(StatisticsApi::class.java)

    override fun getStatistics(depotCode: Int, mutableStatistics: MutableLiveData<Statistics>) {
        mWebService?.getStatistics()
            ?.enqueue(object : Callback<Statistics> {
                override fun onFailure(call: Call<Statistics>, t: Throwable) {
                    println("Debugger mine - Failed it ${t.message}")
                    mutableStatistics.postValue(Statistics())
                }

                override fun onResponse(call: Call<Statistics>, response: Response<Statistics>) {
                    println("Debugger mine - Got it ${response.body()}")
                    mutableStatistics.postValue(response.body())
                }
            })
    }
}