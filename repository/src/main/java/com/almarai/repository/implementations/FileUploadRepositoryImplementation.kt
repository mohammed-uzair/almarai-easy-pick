package com.almarai.repository.implementations

import android.net.Uri
import com.almarai.business.Utils.AppDateTimeFormat
import com.almarai.business.Utils.DateUtil
import com.almarai.easypick.data_source.interfaces.TicketDataSource
import com.almarai.repository.api.FileUploadRepository
import javax.inject.Inject
import kotlin.random.Random

class FileUploadRepositoryImplementation @Inject constructor(private val ticketDataSource: TicketDataSource) :
    FileUploadRepository {
    override suspend fun uploadFiles(files: List<Uri>): String {
        //Upload the files to the respective data source
        val ticketNumber = generateTicketNumber()

        //In this case using only firebase storage
        return uploadFilesUsingFirebaseStorage(files, ticketNumber)
    }

    private suspend fun uploadFilesUsingFirebaseStorage(files: List<Uri>, ticketNumber:String) =
        ticketDataSource.uploadFiles(files, ticketNumber)

    private fun generateTicketNumber(): String {
        val currentDateAndTime = DateUtil.getCurrentDate(AppDateTimeFormat.formatDDMMMYYYYHHMMSS)
        val randomNumber = Random.nextInt(1000, 9999)

        return "$currentDateAndTime$randomNumber"
    }
}