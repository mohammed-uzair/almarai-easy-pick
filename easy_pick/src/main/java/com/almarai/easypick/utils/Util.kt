package com.almarai.easypick.utils

import android.app.Activity
import android.content.Intent
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.system.exitProcess

/**
 * This is the Util class used for all of the common functions within this module
 */
object Util {
    /**
     * Function to exit the application.
     */
    fun exitApplication(activity: Activity) {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.startActivity(intent)
        activity.finish()
        exitProcess(0)
    }

    /**
     * Function to validate the IP address.
     */
    fun isValidIPAddress(ipAddress: String): Boolean {
        // Regex for digit from 0 to 255.
        val zeroTo255 = ("(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])")

        // Regex for a digit from 0 to 255 and
        // followed by a dot, repeat 4 times.
        // this is the regex to validate an IP address.
        val regex = (zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255)

        // Compile the ReGex
        val p: Pattern = Pattern.compile(regex)

        // Pattern class contains matcher() method
        // to find matching between given IP address
        // and regular expression.
        val m: Matcher = p.matcher(ipAddress)

        // Return if the IP address
        // matched the ReGex
        return m.matches()
    }
}