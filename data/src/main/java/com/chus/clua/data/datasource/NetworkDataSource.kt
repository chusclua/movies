package com.chus.clua.data.datasource

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


interface NetworkDataSource {
    fun isInternetAvailable(): Boolean
}

@Singleton
class NetworkDataSourceImp @Inject constructor(
    @ApplicationContext private val appContext: Context
) : NetworkDataSource {
    override fun isInternetAvailable(): Boolean {
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWork = connectivityManager.activeNetwork ?: return false
        val actNetWork = connectivityManager.getNetworkCapabilities(netWork) ?: return false
        return when {
            actNetWork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || actNetWork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                // device how are able to connect with Ethernet
                || actNetWork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                // for check internet over Bluetooth
                || actNetWork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true

            else -> false
        }
    }
}