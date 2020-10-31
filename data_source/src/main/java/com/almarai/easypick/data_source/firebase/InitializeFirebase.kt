package com.almarai.easypick.data_source.firebase

import android.content.Context
import com.google.firebase.FirebaseOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import java.lang.IllegalStateException

object InitializeFirebase {
    fun configureFirebase(context: Context) {
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

        try {
            Firebase.initialize(context /* Context */, options, "easy-pick-abc")
        }
        catch (exception : IllegalStateException){

        }
    }
}