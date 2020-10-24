package com.almarai.easypick.data_source.firebase.interfaces

import android.net.Uri

interface FirebaseFileUploadDataSource {
    suspend fun uploadFiles(files: List<Uri>, ticketNumber: String): String
}