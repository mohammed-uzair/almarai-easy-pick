package com.almarai.easypick.data_source.firebase.implementation

import android.content.Context
import android.util.Log
import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteAccessibility
import com.almarai.data.easy_pick_models.route.RouteServiceStatus
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.easypick.data_source.interfaces.RouteDataSource
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.data_source.request.RequestHeaders
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRoutesDataSourceImplementation @Inject constructor(
    private val gson: Gson,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) :
    RouteDataSource {
    companion object {
        const val TAG = "FirebaseRouteDSImpl"
    }

    override suspend fun getAllRoutes(): StateFlow<List<Route>> {
        val flow: MutableStateFlow<List<Route>> = MutableStateFlow(listOf())

        val requestHeader = RequestHeaders(sharedPreferenceDataSource).getRequestHeader()

        val db = FirebaseFirestore.getInstance()

        val path = "Depots/${requestHeader.depotCode}/Routes"
        val docRef = db.collection(path)

        docRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e(TAG, "Routes fetching from firebase error", exception)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.documents.isNotEmpty()) {
                val routes = ArrayList<Route>()
                for (result in snapshot.documents) {
                    Log.d(TAG, "${result.id} => ${result.data}")

                    val dataInJson = gson.toJson(result.data).toString()
                    val dataFormatted = gson.fromJson(dataInJson, Route::class.java)

                    routes.add(dataFormatted)
                }

                flow.value = routes
            } else {
                Log.d(TAG, "Current data: null")
            }
        }

        return flow
    }

    override suspend fun getAllRoutesStatus(): List<RouteServiceStatus> {
        return listOf()
    }

    override suspend fun getRouteStatus(routeNumber: Int): RouteAccessibility {
        return RouteAccessibility(routeNumber, RouteStatus.NotServed, true)
    }

    override suspend fun updateRouteStatus(routeNumber: Int, status: RouteStatus) {

    }
}