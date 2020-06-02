package com.bhakti.personalcovidtracker

import android.app.Application
import android.content.Context

/***
 * Main application entry point for this app
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}