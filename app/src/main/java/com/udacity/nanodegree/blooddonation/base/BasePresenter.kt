package com.udacity.nanodegree.blooddonation.base

/**
 * Created by riteshksingh on Apr, 2018
 */
interface BasePresenter {
    fun onCreate()

    fun onStart()

    fun onStop()

    fun onDestroy()
}