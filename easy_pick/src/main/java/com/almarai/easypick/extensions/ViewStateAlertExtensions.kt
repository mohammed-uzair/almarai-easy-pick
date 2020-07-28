package com.almarai.easypick.extensions

import android.view.View
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.almarai.easypick.MainActivity
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ScreenViewStateAlertBinding
import com.almarai.easypick.utils.AlertTones.playTone

sealed class Alert {
    object Loading : Alert()
    object Error : Alert()
    object NoDataAvailable : Alert()
}

internal fun Fragment.showViewStateAlert(
    alertType: Alert,
    @StringRes message: Int = R.string.empty
) {
    (requireActivity() as MainActivity).showViewStateAlert(alertType, message)
}

internal fun Fragment.showViewStateAlert(
    alertType: Alert,
    message: String
) {
    (requireActivity() as MainActivity).showViewStateAlert(alertType, message)
}

internal fun Fragment.hideViewStateAlert() {
    (requireActivity() as MainActivity).hideViewStateAlert()
}

internal fun AppCompatActivity.showViewStateAlert(
    alertType: Alert,
    @StringRes message: Int = R.string.empty
) {
    showUserViewStateAlert(getString(message), alertType)
}

internal fun AppCompatActivity.showViewStateAlert(
    alertType: Alert,
    message: String
) {
    showUserViewStateAlert(message, alertType)
}

private fun AppCompatActivity.showUserViewStateAlert(
    message: String,
    alertType: Alert
) {
    val mainActivity = this as MainActivity

    //Hide toolbar
    supportActionBar?.hide()

    //Hide fragment container
    mainActivity.screenMainBinding.fragmentContainerHostFragment.visibility = View.GONE

    //Show ViewStateAlert screen
    mainActivity.screenMainBinding.fragmentContainerAlert.mainAlertDialogRoot.visibility =
        View.VISIBLE

    val binding = mainActivity.screenMainBinding.fragmentContainerAlert
    when (alertType) {
        is Alert.Loading -> {
            val text = getTextMessage(message, R.string.loading)

            //Set the message
            setMessageAndTitle(binding, R.string.loading_title, text)

            //Set the animation
            setAnimation(binding, R.raw.anim_loading)

            //Set button visibility
            setButtonVisibility(binding, View.GONE)
        }
        is Alert.Error -> {
            playTone(false)

            val text = getTextMessage(message, R.string.error)

            //Set the message
            setMessageAndTitle(binding, R.string.error, text)

            //Set the animation
            setAnimation(binding, R.raw.anim_error)

            //Set button visibility
            setButtonVisibility(binding, View.VISIBLE)
        }
        is Alert.NoDataAvailable -> {
            playTone(false)

            val text = getTextMessage(message, R.string.empty_data)

            //Set the message
            setMessageAndTitle(binding, R.string.error, text)

            //Set the animation
            setAnimation(binding, R.raw.anim_no_data_available)

            //Set button visibility
            setButtonVisibility(binding, View.VISIBLE)
        }
    }.exhaustive

    binding.alertButton.setOnClickListener {
        onBackPressed()
    }
}

internal fun AppCompatActivity.hideViewStateAlert() {
    val mainActivity = this as MainActivity
    val binding = mainActivity.screenMainBinding

    //Hide ViewStateAlert
    if (binding.fragmentContainerAlert.mainAlertDialogRoot.visibility == View.VISIBLE)
        binding.fragmentContainerAlert.mainAlertDialogRoot.visibility =
            View.GONE

    //Show fragment container
    if (binding.fragmentContainerHostFragment.visibility == View.GONE ||
        binding.fragmentContainerHostFragment.visibility == View.INVISIBLE
    )
        binding.fragmentContainerHostFragment.visibility = View.VISIBLE

    //Show toolbar
    supportActionBar?.show()
}

private fun setButtonVisibility(binding: ScreenViewStateAlertBinding, visibility: Int) {
    binding.alertButton.visibility = visibility
}

private fun MainActivity.getTextMessage(text: String, @StringRes defaultMessage: Int) =
    if (text.isEmpty()) {
        getString(defaultMessage)
    } else text

private fun MainActivity.setMessageAndTitle(
    binding: ScreenViewStateAlertBinding,
    @StringRes title: Int,
    text: String
) {
    binding.alertTextDetails.text = text.trim()
    binding.alertTextTitle.text = getString(title)
}

private fun setAnimation(binding: ScreenViewStateAlertBinding, @RawRes animation: Int) {
    binding.alertAnimation.setAnimation(animation)
    binding.alertAnimation.post { binding.alertAnimation.playAnimation() }
}