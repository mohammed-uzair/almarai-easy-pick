package com.almarai.easypick.utils.alert_dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlertDialogViewModel : ViewModel() {
    companion object {
        const val TAG = "AlertDialogViewModel"
    }

    var positiveButtonClick: OnPositiveButtonClick? = null
    var negativeButtonClick: OnNegativeButtonClick? = null
    var neutralButtonClick: OnNeutralButtonClick? = null

    val alertTitleText: MutableLiveData<String> = MutableLiveData("")
    val alertMessageText: MutableLiveData<String> = MutableLiveData("")
    val alertPositiveButtonText: MutableLiveData<String> = MutableLiveData("")
    val alertNegativeButtonText: MutableLiveData<String> = MutableLiveData("")
    val alertNeutralButtonText: MutableLiveData<String> = MutableLiveData("")

    fun positiveButtonClicked() = positiveButtonClick?.onClick()
    fun negativeButtonClicked() = negativeButtonClick?.onClick()
    fun neutralButtonClicked() = neutralButtonClick?.onClick()
}