package com.udacity.nanodegree.blooddonation.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by riteshksingh on Apr, 2018
 */
public final class AppUtil {
    private AppUtil() {
    }

    public static void replaceFragmentInActivity(FragmentManager fragmentManager, Fragment fragment,
            int containerId) {
        fragmentManager.beginTransaction().replace(containerId, fragment).commit();
    }
}
