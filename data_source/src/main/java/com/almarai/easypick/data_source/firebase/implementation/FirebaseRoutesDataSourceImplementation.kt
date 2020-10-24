package com.almarai.easypick.data_source.firebase.implementation

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.route.Route
import com.almarai.easypick.data_source.firebase.interfaces.FirebaseRoutesDataSource
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class FirebaseRoutesDataSourceImplementation @Inject constructor(private val context: Context) :
    FirebaseRoutesDataSource {
    companion object {
        const val TAG = "FirebaseRouteDSImpl"
    }

    override fun getAllRoutes(
        depotCode: Int,
        salesDate: String,
        routesPreferences: Int,
        mutableRoutes: MutableLiveData<List<Route>>
    ) {
        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("Depots/123/Routes").document()

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
}