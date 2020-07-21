package com.almarai.easypick.data_source.firebase.implementation

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.route.Route
import com.almarai.easypick.data_source.firebase.interfaces.FirebaseRoutesDataSource
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize


class FirebaseRoutesDataSourceImplementation(val context: Context) : FirebaseRoutesDataSource {
    companion object{
        const val TAG = "FirebaseRouteDSImpl"
    }

    override fun getAllRoutes(
        depotCode: Int,
        salesDate: String,
        routesPreferences: Int,
        mutableRoutes: MutableLiveData<List<Route>>
    ) {
        // Manually configure Firebase Options. The following fields are REQUIRED:
//   - Project ID
//   - App ID
//   - API Key
        val options = FirebaseOptions.Builder()
            .setProjectId("easy-pick-abc")
            .setApplicationId("1:1083305160395:android:48c09b2d49813611e2f54e")
            .setApiKey("AIzaSyCNmWv4HzKbbTfE7MzMxU6qo5R0oiNPb0s")
            // setDatabaseURL(...)
            // setStorageBucket(...)
            .build()

        Firebase.initialize(context /* Context */, options, "easy-pick-abc")

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