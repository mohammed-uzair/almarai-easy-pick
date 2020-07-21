package com.almarai.easypick.utils.alert_dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.almarai.easypick.R
import com.almarai.easypick.databinding.DialogAlertBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlertDialog : DialogFragment() {
    companion object {
        const val TAG = "AlertDialog"

        //Bundle Arguments
        const val BundleAlertTitle = "BundleAlertTitle"
        const val BundleAlertMessage = "BundleAlertMessage"
        const val BundleButtonPositiveText = "BundleButtonPositiveText"
        const val BundleButtonNegativeText = "BundleButtonNegativeText"
        const val BundleButtonNeutralText = "BundleButtonNeutralText"
    }

    private val viewModel: AlertDialogViewModel by viewModel()
    private lateinit var alertDialogBinding: DialogAlertBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        alertDialogBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_alert, container, false)
        alertDialogBinding.apply {
            lifecycleOwner = this@AlertDialog
            viewModel = this@AlertDialog.viewModel
        }

        return alertDialogBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hide the navigation bar from bottom
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        //Avoid default color, use our custom color what we define
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog?.setCancelable(false)
    }

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)

        //Provide the animations
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    fun setBundleDate(
        @StringRes alertTitle: Int,
        @StringRes alertMessage: Int,
        @StringRes positiveButtonText: Int = R.string.alert_button_ok,
        @StringRes negativeButtonText: Int = -1,
        @StringRes neutralButtonText: Int = -1
    ) {
        val bundle = Bundle()
        bundle.putInt(BundleAlertTitle, alertTitle)
        bundle.putInt(BundleAlertMessage, alertMessage)
        bundle.putInt(BundleButtonPositiveText, positiveButtonText)
        bundle.putInt(BundleButtonNegativeText, negativeButtonText)
        bundle.putInt(BundleButtonNeutralText, neutralButtonText)

        this.arguments = bundle
    }

    override fun onResume() {
        super.onResume()

//        val width = (dialog?.window?.decorView?.measuredWidth)?.minus(20)

        dialog?.window?.setLayout(
//            width ?:
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        processBundle()
    }

    private fun processBundle() {
        val bundle = arguments
        if (bundle != null) {
            val alertTitle = bundle.getInt(BundleAlertTitle)
            val alertMessage = bundle.getInt(BundleAlertMessage)
            val alertPositiveButton = bundle.getInt(BundleButtonPositiveText)
            val alertNegativeButton = bundle.getInt(BundleButtonNegativeText)
            val alertNeutralButton = bundle.getInt(BundleButtonNeutralText)

            viewModel.alertTitleText.value = if (alertTitle != -1) getString(alertTitle) else ""
            viewModel.alertMessageText.value =
                if (alertMessage != -1) getString(alertMessage) else ""
            viewModel.alertPositiveButtonText.value =
                if (alertPositiveButton != -1) getString(alertPositiveButton) else ""
            viewModel.alertNegativeButtonText.value =
                if (alertNegativeButton != -1) getString(alertNegativeButton) else ""
            viewModel.alertNeutralButtonText.value =
                if (alertNeutralButton != -1) getString(alertNeutralButton) else ""
        }
    }

    fun setPositiveButtonListener(positiveButtonListener: OnPositiveButtonClick?) {
        viewModel.positiveButtonClick = positiveButtonListener
    }

    fun setNegativeButtonListener(negativeButtonListener: OnNegativeButtonClick?) {
        viewModel.negativeButtonClick = negativeButtonListener
    }

    fun setNeutralButtonListener(neutralButtonListener: OnNeutralButtonClick?) {
        viewModel.neutralButtonClick = neutralButtonListener
    }
}