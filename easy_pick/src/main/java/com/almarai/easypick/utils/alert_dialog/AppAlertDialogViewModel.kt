package com.almarai.easypick.utils.alert_dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppAlertDialogViewModel : ViewModel() {
    companion object {
        const val TAG = "AlertDialogViewModel"
    }

    var positiveButtonClickListener: OnPositiveButtonClickListener? = null
    var negativeButtonClickListener: OnNegativeButtonClickListener? = null
    var neutralButtonClickListener: OnNeutralButtonClickListener? = null

    val alertTitleText: MutableLiveData<String> = MutableLiveData("")
    val alertMessageText: MutableLiveData<String> = MutableLiveData("")
    val alertPositiveButtonText: MutableLiveData<String> = MutableLiveData("")
    val alertNegativeButtonText: MutableLiveData<String> = MutableLiveData("")
    val alertNeutralButtonText: MutableLiveData<String> = MutableLiveData("")

    fun positiveButtonClicked() = positiveButtonClickListener?.onClick()
    fun negativeButtonClicked() = negativeButtonClickListener?.onClick()
    fun neutralButtonClicked() = neutralButtonClickListener?.onClick()
}