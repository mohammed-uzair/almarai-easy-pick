package com.almarai.easypick.data_source.firebase.implementation

import android.net.Uri
import android.util.Log
import com.almarai.easypick.data_source.firebase.interfaces.FirebaseFileUploadDataSource
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import javax.inject.Inject
import kotlin.random.Random

class FirebaseFileUploadDataSourceImplementation @Inject constructor() :
    FirebaseFileUploadDataSource {
    companion object {
        const val TAG = "FireFileUploadDSImpl"
    }

    override suspend fun uploadFiles(files: List<Uri>, ticketNumber: String): String {
        files.forEach { it ->
            val firebaseStorageRoot =
                Firebase.storage.reference.child("${ticketNumber}/${ticketNumber}_${Random.nextInt(1000, 9999)}")

            firebaseStorageRoot.putFile(it)
                .addOnSuccessListener {
                    Log.i(TAG, "File uploaded successfully - File Name : ${it.uploadSessionUri}")
                }
                .addOnFailureListener {
                    Log.i(TAG, "File upload failed - File Name : ${it.message}")
                }
        }

        return ticketNumber
    }
}