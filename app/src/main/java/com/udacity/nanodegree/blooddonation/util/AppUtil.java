package com.udacity.nanodegree.blooddonation.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by riteshksingh on Apr, 2018
 */
public final class AppUtil {

    public static int RETURN_VALUE_TRUE = 1;
    public static int RETURN_VALUE_FALSE = 0;

    private AppUtil() {
    }

    public static void replaceFragmentInActivity(FragmentManager fragmentManager, Fragment fragment,
                                                 int containerId) {
        fragmentManager.beginTransaction().replace(containerId, fragment).commit();
    }

    public static int phoneNumberDigitsWithinLimit(int count){
        if(count > 6 && count < 13){
            return RETURN_VALUE_TRUE;
        }else {
            return RETURN_VALUE_FALSE;
        }
    }

}