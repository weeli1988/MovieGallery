package com.example.moviegallery.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object DeviceUtils {

    // check for internet connection
    fun checkInternetConnection(context: Context) : Boolean {
        val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectionManager.activeNetwork
        val networkCapabilities = connectionManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}