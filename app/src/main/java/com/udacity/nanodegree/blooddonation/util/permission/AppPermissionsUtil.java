package com.udacity.nanodegree.blooddonation.util.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by riteshksingh on Apr, 2018
 */

public class AppPermissionsUtil {

    private AppPermissionsUtil() {
        throw new AssertionError();
    }

    public static <T extends AppCompatActivity> boolean checkIfLocationPermissionIsGiven(
            final T activityRef) {
        if (ContextCompat.checkSelfPermission(activityRef, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    public static <T extends AppCompatActivity> void requestForLocationPermission(final T activityRef,
                                                                                  final int permissionRequestCode) {
        ActivityCompat.requestPermissions(activityRef,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, permissionRequestCode);
    }

    public static <T extends AppCompatActivity> boolean shouldShowPermissionRationaleForLocation(T activity) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION);
    }
}
