package com.almarai.easypick.data_source.firebase.implementation

import android.net.Uri
import android.util.Log
import com.almarai.easypick.data_source.firebase.interfaces.FirebaseFileUploadDataSource
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.coroutineContext
import kotlin.random.Random

class FirebaseFileUploadDataSourceImplementation @Inject constructor() :
    FirebaseFileUploadDataSource {
    companion object {
        const val TAG = "FireFileUploadDSImpl"
    }

    override suspend fun uploadFiles(files: List<Uri>, ticketNumber: String): String {
        val supervisor = SupervisorJob()

        with(CoroutineScope(coroutineContext + supervisor)) {
            files.forEach { it ->
                val firebaseStorageRoot =
                    Firebase.storage.reference.child("${ticketNumber}_${Random.nextInt(1000, 9999)}")

                val child = launch {
                    firebaseStorageRoot.putFile(it)
                        .addOnSuccessListener {
                            Log.i(
                                TAG,
                                "File uploaded successfully - File Name : ${it.uploadSessionUri}"
                            )
                        }
                        .addOnFailureListener {
                            Log.i(TAG, "File upload failed - File Name : ${it.message}")
                        }
                }

                child.join()
            }

            val firstChild = launch(CoroutineExceptionHandler { _, _ -> }) {
                println("The first child is failing")
//                throw AssertionError("The first child is cancelled")
            }


            // launch the second child
            val secondChild = launch {
                firstChild.join()
                // Cancellation of the first child is not propagated to the second child
                println("The first child is cancelled: ${firstChild.isCancelled}, but the second one is still active")
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    // But cancellation of the supervisor is propagated
                    println("The second child is cancelled because the supervisor was cancelled")
                }
            }
            // wait until the first child fails & completes
            firstChild.join()
            println("Cancelling the supervisor")
            supervisor.cancel()
            secondChild.join()


        }

        files.forEach { it ->
            val firebaseStorageRoot =
                Firebase.storage.reference.child("${ticketNumber}_${Random.nextInt(1000, 9999)}")

            firebaseStorageRoot.putFile(it)
                .addOnSuccessListener {
                    Log.i(TAG, "File uploaded successfully - File Name : ${it.uploadSessionUri}")
                }
                .addOnFailureListener {
                    Log.i(TAG, "File upload failed - File Name : ${it.message}")
                }
        }

        return ""
    }
}