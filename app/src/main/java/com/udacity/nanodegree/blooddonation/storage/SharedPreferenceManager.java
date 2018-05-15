package com.udacity.nanodegree.blooddonation.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.udacity.nanodegree.blooddonation.App;

/**
 * Created by riteshksingh on Apr, 2018
 */
public final class SharedPreferenceManager {

    private static SharedPreferenceManager INSTANCE;
    private final String NAME = "com.udacity.nanodegree.blooddonation";
    private SharedPreferences mSharedPreferences;

    private SharedPreferenceManager() {
        mSharedPreferences =
                App.getInstance().getApplicationContext().getSharedPreferences(NAME,
                        Context.MODE_PRIVATE);
    }

    public static SharedPreferenceManager getInstance() {
        if (INSTANCE == null) {
            synchronized (SharedPreferenceManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SharedPreferenceManager();
                }
            }
        }
        return INSTANCE;
    }


    public void put(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public void put(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    public void put(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, -1);
    }

    public Boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

}
