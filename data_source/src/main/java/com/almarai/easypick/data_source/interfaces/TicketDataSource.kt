package com.almarai.easypick.data_source.interfaces

import android.net.Uri
import javax.inject.Singleton

@Singleton
interface TicketDataSource {
    suspend fun uploadFiles(type: String, feedback: String, files: List<Uri>?, ticketNumber: String): String
}