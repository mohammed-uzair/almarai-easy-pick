package com.almarai.easypick.data_source.firebase.implementation

import android.util.Log
import com.almarai.data.easy_pick_models.statistics.Statistics
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.data_source.interfaces.StatisticsDataSource
import com.almarai.easypick.data_source.request.RequestHeaders
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseStatisticsDataSourceImplementation @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource, private val moshi: Moshi
) :
    StatisticsDataSource {
    companion object {
        const val TAG = "FirebaseRouteDSImpl"
    }

    override suspend fun getStatistics(
        depotCode: Int,
        fromDate: Long,
        toDate: Long
    ): StateFlow<Statistics> {
        val flow: MutableStateFlow<Statistics> = MutableStateFlow(Statistics(0, listOf()))

        val requestHeader = RequestHeaders(sharedPreferenceDataSource).getRequestHeader()

        val db = FirebaseFirestore.getInstance()

        val path = "Depots/${requestHeader.depotCode}/Routes"
        val docRef = db.collection(path)

        docRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e(
                    FirebaseRoutesDataSourceImplementation.TAG,
                    "Statistics fetching from firebase error",
                    exception
                )
                return@addSnapshotListener
            }
        }

//            if (snapshot != null && snapshot.documents.isNotEmpty()) {
//                val routes = ArrayList<Route>()
//                for (result in snapshot.documents) {
//                    Log.d(
//                        FirebaseRoutesDataSourceImplementation.TAG,
//                        "${result.id} => ${result.data}"
//                    )
//
//                    val dataInJson =
//                        gson.toJson(result.data).toString()
//                    val dataFormatted = gson.fromJson(dataInJson, Route::class.java)
//
//                    routes.add(dataFormatted)
//                }
//
//                flow.value = routes
//            } else {
//                Log.d(FirebaseRoutesDataSourceImplementation.TAG, "Current data: null")
//            }
//        }

        return flow
    }
}