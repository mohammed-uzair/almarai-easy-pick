package com.almarai.easypick.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat.getSystemService

fun isInternetConnected(): Boolean {
//    //Check internet connection:
//    val connectivityManager =
//        getSystemService<Any>(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
//
//    //Means that we are connected to a network (mobile or wi-fi)
//    connected = connectivityManager!!getAllNetworks().getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
//        .state == NetworkInfo.State.CONNECTED ||
//            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
//                .state == NetworkInfo.State.CONNECTED
//    return connected

    return true
}