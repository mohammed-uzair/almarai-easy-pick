package com.almarai.easypick.extensions

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.almarai.easypick.MainActivity
import com.almarai.easypick.R
import kotlinx.android.synthetic.main.fragment_container.*
import kotlinx.android.synthetic.main.main_alert_dialog.*

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
    mainActivity.fragment_container_host_fragment.visibility = View.GONE

    //Show the alert view as visible
    mainActivity.main_alert_dialog_root.visibility = View.VISIBLE

    //Set the animation to the required one
    var text = message
    when (alertType) {
        is Alert.Loading -> {
            if (text.isEmpty()) {
                text = getString(R.string.loading)
            }

            //Set the animation
            mainActivity.alert_animation.setAnimation(R.raw.anim_loading)
            mainActivity.alert_animation.post { mainActivity.alert_animation.playAnimation() }

            //Set the message
            mainActivity.alert_text_details.text = text.trim()
        }
        is Alert.Error -> {
            if (text.isEmpty()) {
                text = getString(R.string.no_data_found)
            }

            //Set the animation
            mainActivity.alert_animation.setAnimation(R.raw.anim_error)
            mainActivity.alert_animation.post { mainActivity.alert_animation.playAnimation() }

            //Set the message
            mainActivity.alert_text_details.text = text.trim()
        }
        is Alert.NoDataAvailable -> {
            if (text.isEmpty()) {
                text = getString(R.string.no_data_available)
            }

            //Set the animation
            mainActivity.alert_animation.setAnimation(R.raw.anim_no_data_available)
            mainActivity.alert_animation.post { mainActivity.alert_animation.playAnimation() }

            //Set the message
            mainActivity.alert_text_details.text = text.trim()
        }
    }.exhaustive
}

internal fun AppCompatActivity.hideViewStateAlert() {
    val mainActivity = this as MainActivity

    //Hide the view state alert
    if (mainActivity.main_alert_dialog_root.visibility == View.VISIBLE)
        mainActivity.main_alert_dialog_root.visibility = View.GONE

    //Show the root fragment container
    if (mainActivity.fragment_container_host_fragment.visibility == View.GONE ||
        mainActivity.fragment_container_host_fragment.visibility == View.INVISIBLE
    )
        mainActivity.fragment_container_host_fragment.visibility = View.VISIBLE
}