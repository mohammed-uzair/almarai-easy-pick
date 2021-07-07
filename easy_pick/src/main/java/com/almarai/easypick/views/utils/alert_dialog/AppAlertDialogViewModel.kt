package com.almarai.easypick.views.utils.alert_dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppAlertDialogViewModel @Inject constructor() : ViewModel() {
    companion object {
        const val TAG = "AppAlertDialogViewModel"
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