package com.almarai.repository.api

import android.net.Uri

interface FileUploadRepository {
    suspend fun uploadFiles(files:List<Uri>):String
}