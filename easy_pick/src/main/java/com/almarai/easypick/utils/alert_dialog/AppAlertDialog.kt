package com.almarai.easypick.utils.alert_dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.almarai.easypick.R
import com.almarai.easypick.databinding.DialogAlertBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppAlertDialog : DialogFragment() {
    companion object {
        const val TAG = "AppAlertDialog"

        //Bundle Arguments
        const val BundleAlertTitle = "BundleAlertTitle"
        const val BundleAlertMessage = "BundleAlertMessage"
        const val BundleButtonPositiveText = "BundleButtonPositiveText"
        const val BundleButtonNegativeText = "BundleButtonNegativeText"
        const val BundleButtonNeutralText = "BundleButtonNeutralText"
    }

    private val viewModel: AppAlertDialogViewModel by viewModel()
    private lateinit var alertDialogBinding: DialogAlertBinding

    //Buttons listeners
    var positiveButtonClickListener: OnPositiveButtonClickListener? = null
    var negativeButtonClickListener: OnNegativeButtonClickListener? = null
    var neutralButtonClickListener: OnNeutralButtonClickListener? = null

    var buttonNegativeText: String = ""

    fun setBundle(
        alertTitle: String,
        alertMessage: String,
        positiveButtonText: String,
        negativeButtonText: String,
        neutralButtonText: String
    ) {
        val bundle = Bundle()
        bundle.run {
            putString(BundleAlertTitle, alertTitle)
            putString(BundleAlertMessage, alertMessage)
            putString(BundleButtonPositiveText, positiveButtonText)
            putString(BundleButtonNegativeText, negativeButtonText)
            putString(BundleButtonNeutralText, neutralButtonText)
        }

        this.arguments = bundle
    }

    private fun processBundle() {
        val bundle = arguments
        if (bundle != null) {
            viewModel.apply {
                alertTitleText.value =
                    bundle.getString(BundleAlertTitle) ?: getString(R.string.alert_default_title)
                alertMessageText.value = bundle.getString(BundleAlertMessage)
                    ?: getString(R.string.alert_default_message)
                alertPositiveButtonText.value = bundle.getString(BundleButtonPositiveText)
                    ?: getString(R.string.alert_button_ok)
                alertNegativeButtonText.value = bundle.getString(BundleButtonNegativeText) ?: ""
                alertNeutralButtonText.value = bundle.getString(BundleButtonNeutralText) ?: ""
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        alertDialogBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.dialog_alert, null, false)

        val builder = AlertDialog.Builder(activity).apply {
            setView(alertDialogBinding.root)
        }

        return builder.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        alertDialogBinding.apply {
            lifecycleOwner = this@AppAlertDialog
            viewModel = this@AppAlertDialog.viewModel
        }

        return alertDialogBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hide the navigation bar from bottom
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        dialog?.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)

            //Avoid default color, use our custom color what we define
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            //Do not allow the dialog to dismiss if clicked outside of its frame
            isCancelable = false
        }

        processBundle()

        //Set the buttons listeners
        setButtonListeners()
    }

    private fun setButtonListeners() {
        viewModel.apply {
            if (this@AppAlertDialog.positiveButtonClickListener != null) {
                positiveButtonClickListener = this@AppAlertDialog.positiveButtonClickListener
            } else {
                object : OnPositiveButtonClickListener {
                    override fun onClick() {
                        dismiss()
                    }
                }
            }

            if (this@AppAlertDialog.negativeButtonClickListener != null) {
                negativeButtonClickListener = this@AppAlertDialog.negativeButtonClickListener
            } else {
                object : OnNegativeButtonClickListener {
                    override fun onClick() {
                        dismiss()
                    }
                }
            }

            if (this@AppAlertDialog.neutralButtonClickListener != null) {
                neutralButtonClickListener = this@AppAlertDialog.neutralButtonClickListener
            } else {
                object : OnNeutralButtonClickListener {
                    override fun onClick() {
                        dismiss()
                    }
                }
            }

            alertNegativeButtonText.value = buttonNegativeText
        }
    }

    override fun dismiss() {
        super.dismiss()
    }

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)

        //Provide the animations
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }
}