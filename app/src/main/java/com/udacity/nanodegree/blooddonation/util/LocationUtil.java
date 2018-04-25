package com.udacity.nanodegree.blooddonation.util;

import android.content.Context;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 25/4/18.
 */
public class LocationUtil {


    private LocationRequest mLocationRequest;

    private void createLocationRequest(Context context) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(8000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> task= settingsClient.checkLocationSettings(builder.build());
        
    }

}
