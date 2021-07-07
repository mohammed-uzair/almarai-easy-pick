package com.almarai.easypick.view_models

import android.net.Uri
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.data.easy_pick_models.Result
import com.almarai.repository.api.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class TicketViewModel @Inject constructor(private val ticketRepository: TicketRepository) :
    ViewModel() {
    private val _ticketResult = MutableLiveData<Result<String>>()
    val uploadResult = _ticketResult

    fun generateTicket(type: String, feedback: String, files: List<Uri>?) {
        viewModelScope.launch {
            //Start the upload process
            _ticketResult.value = Result.Fetching

            try {
                //Upload the files to data store
                val ticketNumber = ticketRepository.uploadFiles(type, feedback, files)

                //Post the result to the user
                _ticketResult.value = Result.Success(ticketNumber)
            } catch (exception: Exception) {
                _ticketResult.value = Result.Error()
            }
        }
    }
}