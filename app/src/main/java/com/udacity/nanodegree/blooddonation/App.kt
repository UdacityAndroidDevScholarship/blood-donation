package com.udacity.nanodegree.blooddonation

import android.app.Application
import com.squareup.leakcanary.LeakCanary

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
        initLeakCanary()
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this))
            return

        LeakCanary.install(this)
    }
}