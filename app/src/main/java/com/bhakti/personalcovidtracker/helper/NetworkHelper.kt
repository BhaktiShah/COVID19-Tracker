package com.bhakti.personalcovidtracker.helper


import android.content.Context
import android.net.ConnectivityManager
import com.bhakti.personalcovidtracker.MainApplication

object NetworkHelper {

    fun isOnline(): Boolean {
        val mCm = MainApplication.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager? ?: return false
        val networkInfo = mCm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun isOnlineViaWiFi(): Boolean {
        val mCm = MainApplication.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager? ?: return false
        val activeNetworkInfo = mCm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI && activeNetworkInfo.isConnected
    }
}