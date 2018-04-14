package com.udacity.nanodegree.blooddonation.injection;

import com.google.firebase.auth.FirebaseAuth;
import com.udacity.nanodegree.blooddonation.storage.SharedPreferenceManager;

/**
 * Created by riteshksingh on Apr, 2018
 */
final public class Injection {
    private Injection() {
    }

    public static FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public static SharedPreferenceManager getSharedPreference() {
        return SharedPreferenceManager.getInstance();
    }
}
