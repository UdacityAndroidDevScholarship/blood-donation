package com.udacity.nanodegree.blooddonation.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.ui.login.view.UserLoginActivity;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class HomeActivity extends BaseActivity {

  int REQUEST_CODE_LOCATION = 1;
  int REQUEST_CODE_SMS = 2;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    locationPermissionCheck();
    requestSmsPermission();
  }

  //Function to check and request permission for location
  public void locationPermissionCheck() {
    String location_permission = Manifest.permission.ACCESS_FINE_LOCATION;
    int grant = ContextCompat.checkSelfPermission(this, location_permission);
    if (grant != PackageManager.PERMISSION_GRANTED) {
      String[] permission_list = new String[1];
      permission_list[0] = location_permission;
      ActivityCompat.requestPermissions(this, permission_list, REQUEST_CODE_LOCATION);
    } else {
    }
  }

  //Function to check and request permission for reading sms
  private void requestSmsPermission() {
    String sms_permission = Manifest.permission.RECEIVE_SMS;
    int grant = ContextCompat.checkSelfPermission(this, sms_permission);
    if (grant != PackageManager.PERMISSION_GRANTED) {
      String[] permission_list = new String[1];
      permission_list[0] = sms_permission;
      ActivityCompat.requestPermissions(this, permission_list, REQUEST_CODE_SMS);
    } else {
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_CODE_LOCATION) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Log.v("Permission", "Permission: " + permissions[0] + "was " + grantResults[0]);
      } else {
      }
    } else if (requestCode == REQUEST_CODE_SMS) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Log.v("Permission", "Permission: " + permissions[0] + "was " + grantResults[0]);
      } else {
      }
    }
  }

  public void signOut() {
    FirebaseAuth.getInstance().signOut();
    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
      Intent intent = new Intent(HomeActivity.this, UserLoginActivity.class);
      startActivity(intent);
    }
  }
}
