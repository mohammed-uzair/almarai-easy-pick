package com.almarai.easypick.data_source.firebase.implementation

import android.util.Log
import com.almarai.data.easy_pick_models.route.*
import com.almarai.easypick.data_source.interfaces.RouteDataSource
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.data_source.request.RequestHeaders
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRoutesDataSourceImplementation @Inject constructor(
    private val moshi: Moshi,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) :
    RouteDataSource {
    companion object {
        const val TAG = "FirebaseRouteDSImpl"
    }

    override suspend fun getAllRoutes(): Flow<List<Route>> = callbackFlow {
        val requestHeader = RequestHeaders(sharedPreferenceDataSource).getRequestHeader()

        try {
            val db = FirebaseFirestore.getInstance()

            val path = "Depots/${requestHeader.depotCode}/Routes"
            val docRef = db.collection(path)

            val subscription = docRef.addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e(TAG, "Routes fetching from firebase error", exception)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.documents.isNotEmpty()) {
                    val routes = ArrayList<Route>()
                    for (result in snapshot.documents) {
                        Log.d(TAG, "${result.id} => ${result.data}")

//                        val dataInJson = moshi.adapter(Route::class.java).toJson(result.data).toString()
//                        val dataFormatted = gson.fromJson(dataInJson, Route::class.java)

//                        routes.add(dataFormatted)
                    }

                    offer(routes)
                } else {
                    Log.d(TAG, "Current data: null")
                }
            }

            awaitClose { subscription?.remove() }

        } catch (exception: Throwable) {
            close(exception)
        }
    }

    override suspend fun getAllRoutesStatus(): Flow<List<RouteServiceStatus>> = flow {
        emit(listOf())
    }

    override suspend fun getRouteStatus(routeNumber: Int): RouteAccessibility {
        return RouteAccessibility(routeNumber, RouteStatus.NotServed, true)
    }

    override suspend fun updateRouteStatus(routeNumber: Int, statusCategory: RouteStatus) {

    }
}