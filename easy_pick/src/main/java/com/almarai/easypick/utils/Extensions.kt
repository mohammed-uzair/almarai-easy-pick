package com.almarai.easypick.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.almarai.easypick.MainActivity
import com.almarai.easypick.R
import kotlinx.android.synthetic.main.fragment_container.*
import kotlinx.android.synthetic.main.main_alert_dialog.*

val <T> T.exhaustive: T
    get() = this

sealed class Alert {
    object Loading : Alert()
    object Error : Alert()
    object NoDataAvailable : Alert()
}

internal fun Fragment.showAlert(alertType: Alert, message: String = "") {
    (requireActivity() as MainActivity).showAlert(alertType, message)
}

internal fun Fragment.hideAlert() {
    (requireActivity() as MainActivity).hideAlert()
}

internal fun AppCompatActivity.showAlert(alertType: Alert, message: String = "") {
    val mainActivity = this as MainActivity

    //Hide the root fragment view
    mainActivity.fragment_container_host_fragment.visibility = View.GONE

    //Show the alert view as visible
    mainActivity.main_alert_dialog_root.visibility = View.VISIBLE

    //Set the animation to the required one
    var text = message
    when (alertType) {
        Alert.Loading -> {
            if (text.isEmpty()) {
                text = getString(R.string.loading)
            }

            //Set the animation
            mainActivity.alert_animation.setAnimation(R.raw.anim_loading)
            mainActivity.alert_animation.post { mainActivity.alert_animation.playAnimation() }

            //Set the message
            mainActivity.alert_text.text = text.trim()
        }
        Alert.Error -> {
            if (text.isEmpty()) {
                text = getString(R.string.no_data_found)
            }

            //Set the animation
            mainActivity.alert_animation.setAnimation(R.raw.anim_error)
            mainActivity.alert_animation.post { mainActivity.alert_animation.playAnimation() }

            //Set the message
            mainActivity.alert_text.text = text.trim()
        }
        Alert.NoDataAvailable -> {
            if (text.isEmpty()) {
                text = getString(R.string.no_data_available)
            }

            //Set the animation
            mainActivity.alert_animation.setAnimation(R.raw.anim_no_data_available)
            mainActivity.alert_animation.post { mainActivity.alert_animation.playAnimation() }

            //Set the message
            mainActivity.alert_text.text = text.trim()
        }
    }.exhaustive
}

internal fun AppCompatActivity.hideAlert() {
    val mainActivity = this as MainActivity

    //Show the alert view as visible
    mainActivity.main_alert_dialog_root.visibility = View.GONE

    //Hide the root fragment view
    mainActivity.fragment_container_host_fragment.visibility = View.VISIBLE
}