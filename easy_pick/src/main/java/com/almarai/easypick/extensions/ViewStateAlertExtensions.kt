package com.almarai.easypick.extensions

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.almarai.easypick.MainActivity
import com.almarai.easypick.R

sealed class Alert {
    object Loading : Alert()
    object Error : Alert()
    object NoDataAvailable : Alert()
}

internal fun Fragment.showViewStateAlert(alertType: Alert, message: String = "") {
    (requireActivity() as MainActivity).showViewStateAlert(alertType, message)
}

internal fun Fragment.hideViewStateAlert() {
    (requireActivity() as MainActivity).hideViewStateAlert()
}

internal fun AppCompatActivity.showViewStateAlert(alertType: Alert, message: String = "") {
    val mainActivity = this as MainActivity

    //Hide the root fragment view
    mainActivity.screenMainBinding.fragmentContainerHostFragment.visibility = View.GONE

    //Show the alert view as visible
    mainActivity.screenMainBinding.fragmentContainerAlert.mainAlertDialogRoot.visibility =
        View.VISIBLE

    //Set the animation to the required one
    var text = message

    val binding = mainActivity.screenMainBinding.fragmentContainerAlert
    when (alertType) {
        is Alert.Loading -> {
            if (text.isEmpty()) {
                text = getString(R.string.loading)
            }

            //Set the animation
            binding.alertAnimation.setAnimation(R.raw.anim_loading)
            binding.alertAnimation.post { binding.alertAnimation.playAnimation() }

            //Set the message
            binding.alertTextDetails.text = text.trim()
        }
        is Alert.Error -> {
            if (text.isEmpty()) {
                text = getString(R.string.no_data_found)
            }

            //Set the animation
            binding.alertAnimation.setAnimation(R.raw.anim_error)
            binding.alertAnimation.post { binding.alertAnimation.playAnimation() }

            //Set the message
            binding.alertTextDetails.text = text.trim()
        }
        is Alert.NoDataAvailable -> {
            if (text.isEmpty()) {
                text = getString(R.string.no_data_available)
            }

            //Set the animation
            binding.alertAnimation.setAnimation(R.raw.anim_no_data_available)
            binding.alertAnimation.post { binding.alertAnimation.playAnimation() }

            //Set the message
            binding.alertTextDetails.text = text.trim()
        }
    }.exhaustive
}

internal fun AppCompatActivity.hideViewStateAlert() {
    val mainActivity = this as MainActivity
    val binding = mainActivity.screenMainBinding.fragmentContainerAlert

    //Hide the view state alert
    if (mainActivity.screenMainBinding.fragmentContainerAlert.mainAlertDialogRoot.visibility == View.VISIBLE)
        mainActivity.screenMainBinding.fragmentContainerAlert.mainAlertDialogRoot.visibility =
            View.GONE

    //Show the root fragment container
    if (mainActivity.screenMainBinding.fragmentContainerHostFragment.visibility == View.GONE ||
        mainActivity.screenMainBinding.fragmentContainerHostFragment.visibility == View.INVISIBLE
    )
        mainActivity.screenMainBinding.fragmentContainerHostFragment.visibility = View.VISIBLE
}