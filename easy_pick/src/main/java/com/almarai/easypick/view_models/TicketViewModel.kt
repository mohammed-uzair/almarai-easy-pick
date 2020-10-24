package com.almarai.easypick.view_models

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.data.easy_pick_models.Result
import com.almarai.repository.api.FileUploadRepository
import kotlinx.coroutines.launch

class TicketViewModel @ViewModelInject constructor(private val filFileUploadRepository: FileUploadRepository) :
    ViewModel() {
    private val _uploadResult = MutableLiveData<Result<String>>()
    val uploadResult = _uploadResult

    fun uploadFiles(files: List<Uri>) {
        viewModelScope.launch {
            //Start the upload process
            _uploadResult.value = Result.Fetching

            try {
                //Upload the files to data store
                val ticketNumber = filFileUploadRepository.uploadFiles(files)

                //Post the result to the user
                _uploadResult.value = Result.Success(ticketNumber)
            } catch (exception: Exception) {
                _uploadResult.value = Result.Error()
            }
        }
    }
}