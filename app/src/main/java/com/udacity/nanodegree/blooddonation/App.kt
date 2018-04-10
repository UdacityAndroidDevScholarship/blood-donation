package com.udacity.nanodegree.blooddonation

import android.app.Application

/**
 * Created by riteshksingh on Apr, 2018
 */

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}