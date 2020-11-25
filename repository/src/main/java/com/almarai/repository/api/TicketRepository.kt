package com.almarai.repository.api

import android.net.Uri

interface TicketRepository {
    suspend fun uploadFiles(type: String, feedback: String, files: List<Uri>):String
}