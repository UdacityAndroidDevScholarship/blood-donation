package com.udacity.nanodegree.blooddonation.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by tavishjain on 20-04-2018.
 */

public class PermissionUtils{

    static Activity mActivity;

    public PermissionUtils(Activity activity){
        mActivity = activity;
    }

    static int REQUEST_CODE_LOCATION_AND_SMS = 1;

    static String location_permission = Manifest.permission.ACCESS_FINE_LOCATION;
    static String sms_permission = Manifest.permission.RECEIVE_SMS;

    public static void permissionCheck(){

        int grant_location = ContextCompat.checkSelfPermission(mActivity,location_permission);
        int grant_sms = ContextCompat.checkSelfPermission(mActivity, sms_permission);

        if(grant_location != PackageManager.PERMISSION_GRANTED || grant_sms != PackageManager.PERMISSION_GRANTED){
            requestLocationAndSms();
        }
    }

    public static void requestLocationAndSms() {
        String[] permission_list = new String[2];
        permission_list[0] = location_permission;
        permission_list[1] = sms_permission;
        ActivityCompat.requestPermissions(mActivity,permission_list,REQUEST_CODE_LOCATION_AND_SMS);
    }

}
