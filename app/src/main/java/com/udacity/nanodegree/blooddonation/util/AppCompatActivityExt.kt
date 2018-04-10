package com.udacity.nanodegree.blooddonation.util

import android.content.Context
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import android.net.ConnectivityManager

/**
 * Created by riteshksingh on Apr, 2018
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager
            .beginTransaction()
            .replace(frameId, fragment)
            .commit()
}

fun AppCompatActivity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration)
            .show()
}

fun AppCompatActivity.isNetworkActive(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo

    return activeNetwork != null &&
            activeNetwork.isConnectedOrConnecting
}