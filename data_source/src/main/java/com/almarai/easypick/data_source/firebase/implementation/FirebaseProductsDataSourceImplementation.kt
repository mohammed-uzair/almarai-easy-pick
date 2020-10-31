package com.almarai.easypick.data_source.firebase.implementation

import android.content.Context
import android.util.Log
import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.easypick.data_source.interfaces.ProductsDataSource
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.data_source.request.RequestHeaders
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseProductsDataSourceImplementation @Inject constructor(
    private val context: Context,
    private val gson: Gson,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) :
    ProductsDataSource {
    companion object {
        const val TAG = "FirebaseRouteDSImpl"
    }

    override suspend fun getAllProducts(routeNumber: Int): StateFlow<List<Product>> {
        val flow: MutableStateFlow<List<Product>> = MutableStateFlow(listOf())

        val requestHeader = RequestHeaders(sharedPreferenceDataSource).getRequestHeader()

        val db = FirebaseFirestore.getInstance()

        val path = "Depots/${requestHeader.depotCode}/Routes/$routeNumber/Products"
        val docRef = db.collection(path)

        docRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e(FirebaseRoutesDataSourceImplementation.TAG, "Products fetching from firebase error", exception)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.documents.isNotEmpty()) {
                val products = ArrayList<Product>()
                for (result in snapshot.documents) {
                    Log.d(FirebaseRoutesDataSourceImplementation.TAG, "${result.id} => ${result.data}")

                    val dataInJson = gson.toJson(result.data).toString()
                    val dataFormatted = gson.fromJson(dataInJson, Product::class.java)

                    products.add(dataFormatted)
                }

                flow.value = products
            } else {
                Log.d(FirebaseRoutesDataSourceImplementation.TAG, "Current data: null")
            }
        }

        return flow
    }

    override suspend fun updateRouteData(routeNumber: Int, products: List<Product>): RouteStatus {
        TODO("Not yet implemented")
    }
}