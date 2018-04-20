package com.udacity.nanodegree.blooddonation.ui.home;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.util.PermissionUtils;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class HomeActivity extends BaseActivity {

    static int REQUEST_CODE_LOCATION_AND_SMS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        PermissionUtils permissionUtils = new PermissionUtils(this);
        permissionUtils.permissionCheck();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_LOCATION_AND_SMS){
            boolean locationPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED ;
            boolean smsPermissionGranted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

            if(!locationPermissionGranted || !smsPermissionGranted){
                PermissionUtils.requestLocationAndSms();
            }
        }
    }
}