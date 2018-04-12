package com.udacity.nanodegree.blooddonation;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class App extends Application {

    private static App INSTANCE;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
        initLeakCanary();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) return;

        LeakCanary.install(this);
    }
}
