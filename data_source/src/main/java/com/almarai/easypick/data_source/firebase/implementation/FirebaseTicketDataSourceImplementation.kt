package com.almarai.easypick.data_source.firebase.implementation

import android.net.Uri
import android.util.Log
import com.almarai.easypick.data_source.interfaces.TicketDataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class FirebaseTicketDataSourceImplementation @Inject constructor() :
    TicketDataSource {
    companion object {
        const val TAG = "FireFileUploadDSImpl"
    }

    override suspend fun uploadFiles(
        type: String,
        feedback: String,
        files: List<Uri>?,
        ticketNumber: String
    ): String {
        files?.let {
            it.forEach { file ->
                val firebaseStorageRoot =
                    Firebase.storage.reference.child(
                        "Tickets/${ticketNumber}/${ticketNumber}_${
                            Random.nextInt(
                                1000,
                                9999
                            )
                        }"
                    )

                //addTicketDetailsToDataSource(type, feedback, ticketNumber, )

                firebaseStorageRoot.putFile(file)
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
        }

        return ticketNumber
    }

    private fun addTicketDetailsToDataSource(
        ticketType: String,
        feedback: String,
        ticketNumber: String,
        proofsPath: String
    ) {
        val db = FirebaseFirestore.getInstance()

        val ticket = hashMapOf(
            "ProofsPath" to proofsPath,
            "TicketDescription" to "",
            "TicketType" to ticketType
        )

        db.collection("Tickets").document(ticketNumber)
            .set(ticket)
    }
}