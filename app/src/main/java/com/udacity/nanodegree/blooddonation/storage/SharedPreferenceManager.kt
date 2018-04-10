package com.udacity.nanodegree.blooddonation.storage

import android.content.Context
import android.content.SharedPreferences
import com.udacity.nanodegree.blooddonation.App

/**
 * Created by riteshksingh on Apr, 2018
 */
object SharedPreferenceManager {

    private const val NAME = "com.udacity.nanodegree.blooddonation"

    private var mSharedPreference: SharedPreferences

    init {
        mSharedPreference = App.instance.applicationContext
                .getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    fun put(key: String, value: String) {
        with(mSharedPreference.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun put(key: String, value: Int) {
        with(mSharedPreference.edit()) {
            putInt(key, value)
            apply()
        }
    }

    fun put(key: String, value: Boolean) {
        with(mSharedPreference.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    fun getString(key: String) = mSharedPreference.getString(key, "")

    fun getInt(key: String) = mSharedPreference.getInt(key, -1)

    fun getBoolean(key: String) = mSharedPreference.getBoolean(key, false)
}