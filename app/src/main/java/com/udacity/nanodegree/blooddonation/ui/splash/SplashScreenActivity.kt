package com.udacity.nanodegree.blooddonation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.udacity.nanodegree.blooddonation.R
import com.udacity.nanodegree.blooddonation.base.BaseActivity
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivity

/**
 * Created by riteshksingh on Apr, 2018
 */

class SplashScreenActivity : BaseActivity() {

    private val SPLASH_DELAY: Long = 1000
    private var mDelayHandler: Handler? = null

    private val runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        mDelayHandler = Handler()

        mDelayHandler!!.postDelayed(runnable, SPLASH_DELAY)

    }

    override fun onDestroy() {
        super.onDestroy()
        mDelayHandler?.removeCallbacks(runnable)
    }
}
