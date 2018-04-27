package com.udacity.nanodegree.blooddonation.ui.userdetail.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.common.picker.DatePickerFragment;
import com.udacity.nanodegree.blooddonation.databinding.ActivityUserDetailsBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.view.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.userdetail.UserDetailContract;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;
import com.udacity.nanodegree.blooddonation.ui.userdetail.presenter.UserDetailPresenter;
import com.udacity.nanodegree.blooddonation.util.permission.AppPermissionsUtil;


/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetailActivity extends BaseActivity implements UserDetailContract.View {

  private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 100;

  private UserDetailContract.Presenter mPresenter;
  private ActivityUserDetailsBinding mActivityUserDetailsBinding;
  private UserDetail mUserDetail;
  private FusedLocationProviderClient mFusedLocationClient;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter =
        new UserDetailPresenter(this, Injection.provideFireBaseAuth(), Injection.getSharedPreference(),
            Injection.providesDataRepo());
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);
    mActivityUserDetailsBinding = (ActivityUserDetailsBinding) mBinding;
    mUserDetail = new UserDetail();
    mActivityUserDetailsBinding.setPresenter(mPresenter);
    mActivityUserDetailsBinding.setUserdetail(mUserDetail);
    getSupportActionBar().setTitle(R.string.user_profile);
    initSpinner();

    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    mPresenter.onCreate();
  }

  @Override protected void onStart() {
    super.onStart();
    mPresenter.onStart();
  }

  @Override protected void onStop() {
    super.onStop();
    mPresenter.onStop();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }

  private void initSpinner() {
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.blood_group,
        android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mActivityUserDetailsBinding.bloodGroupDropDown.setAdapter(adapter);
  }

  @Override public void showDatePickerDialog() {
    DialogFragment dialogFragment = DatePickerFragment.newInstance(mUserDetail.dob);
    dialogFragment.show(getSupportFragmentManager(), "datefragment");
  }

  @SuppressLint("MissingPermission") private void getLocation() {
    mFusedLocationClient.getLastLocation()
        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
          @Override public void onSuccess(Location location) {
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
              mUserDetail.latitiude.set(location.getLatitude());
              mUserDetail.longitude.set(location.getLongitude());
            }
          }
        });
  }

  @Override public void getLastLocation() {
    if (AppPermissionsUtil.checkIfLocationPermissionIsGiven(this)) {
      getLocation();
    } else {
      AppPermissionsUtil.requestForLocationPermission(this, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case MY_PERMISSIONS_REQUEST_FINE_LOCATION:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          getLocation();
        }
        break;
      default:
        break;
    }
  }

  @Override public void launchHomeScreen() {
    Intent intent = new Intent(this, HomeActivity.class);
    finish();
    startActivity(intent);
  }
}
