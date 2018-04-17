package com.udacity.nanodegree.blooddonation.ui.userdetail.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.common.picker.DatePickerFragment;
import com.udacity.nanodegree.blooddonation.databinding.ActivityUserDetailsBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.userdetail.UserDetailContract;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;
import com.udacity.nanodegree.blooddonation.ui.userdetail.presenter.UserDetailPresenter;
import timber.log.Timber;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetailActivity extends BaseActivity implements UserDetailContract.View {

  private UserDetailContract.Presenter mPresenter;
  private ActivityUserDetailsBinding mActivityUserDetailsBinding;

  private UserDetail mUserDetail;

  private FusedLocationProviderClient mFusedLocationClient;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter =
        new UserDetailPresenter(this, Injection.getFirebaseAuth(), Injection.getSharedPreference(),
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

  @Override public void getLastLocation() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return;
    }
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
  
  @Override
  public void launchHomeScreen() {
    Intent intent = new Intent(this, HomeActivity.class);
    finish();
    startActivity(intent);
  }
}
