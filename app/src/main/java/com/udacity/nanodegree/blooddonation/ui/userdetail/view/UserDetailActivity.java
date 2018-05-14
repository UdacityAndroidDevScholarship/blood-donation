package com.udacity.nanodegree.blooddonation.ui.userdetail.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocomplete.IntentBuilder;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.common.picker.DatePickerFragment;
import com.udacity.nanodegree.blooddonation.databinding.ActivityUserDetailsBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.view.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.login.model.UserPhoneNumber;
import com.udacity.nanodegree.blooddonation.ui.userdetail.UserDetailContract;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;
import com.udacity.nanodegree.blooddonation.ui.userdetail.presenter.UserDetailPresenter;

import static com.udacity.nanodegree.blooddonation.constants.Constants.PERMISSION_REQUEST_CODE;
import static com.udacity.nanodegree.blooddonation.constants.Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetailActivity extends BaseActivity implements UserDetailContract.View {

  private UserDetailContract.Presenter mPresenter;
  private ActivityUserDetailsBinding mActivityUserDetailsBinding;
  private UserDetail mUserDetail;
  private AutocompleteFilter.Builder autocompleteFilterBuilderCity;
  private boolean hasPermissions = false;

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    mPresenter.handleActivityResult(this, requestCode, resultCode, data);
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    mPresenter.handlePermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter =
        new UserDetailPresenter(this, Injection.provideFireBaseAuth(),
            Injection.provideFireBaseStorage(),
            Injection.getSharedPreference(),
            Injection.providesDataRepo());

    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);
    mActivityUserDetailsBinding = (ActivityUserDetailsBinding) mBinding;
    mUserDetail = new UserDetail();

    mActivityUserDetailsBinding.setPresenter(mPresenter);
    mActivityUserDetailsBinding.setUserDetail(mUserDetail);

    autocompleteFilterBuilderCity =
        new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES);

    if (getIntent() != null && getIntent().hasExtra(UserPhoneNumber.EXTRA_USER_PHONE_NUMBER)) {
      UserPhoneNumber userPhoneNumber =
          getIntent().getParcelableExtra(UserPhoneNumber.EXTRA_USER_PHONE_NUMBER);
      mUserDetail.getPhoneNumber()
          .set(String.format("+%s-%s", userPhoneNumber.getPhoneCode(),
              userPhoneNumber.getPhoneNumber()));
      mUserDetail.getCountry().set(userPhoneNumber.getCountryName());
      mActivityUserDetailsBinding.tietUserDetailsCountry.setText(userPhoneNumber.getCountryName());

      autocompleteFilterBuilderCity.setCountry(userPhoneNumber.getIso());
    }

    setTitle("Personal Details");
    if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    mPresenter.onCreate();
  }

  @Override
  protected void onStart() {
    super.onStart();
    mPresenter.onStart();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mPresenter.onStop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }

  @Override public void showDatePickerDialog() {
    DatePickerFragment datePickerFragment =
        DatePickerFragment.newInstance(mUserDetail.getBirthdayInMillis());
    datePickerFragment.show(getSupportFragmentManager(), "datePickerFragment");
  }

  @Override public void startCityPickerActivity() {
    try {
      Intent placeAutoCompleteIntent = new IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
          .setFilter(autocompleteFilterBuilderCity.build())
          .build(this);

      startActivityForResult(placeAutoCompleteIntent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
      e.printStackTrace();
    }
  }

  @Override public void setUserDetailCityAndState(String city, String state) {
    mUserDetail.getCity().set(city);
    mUserDetail.getState().set(state);
  }

  @Override public void showTextInputError(int resId, int message) {

    TextInputLayout textInputLayout = findViewById(resId);

    textInputLayout.setErrorEnabled(true);
    textInputLayout.setError(getString(message));
  }

  @Override public void setCreateAccountProgressVisibility(boolean isVisible) {
    if (isVisible) {
      mActivityUserDetailsBinding.cmlCreateAccount.revealFrom(
          mActivityUserDetailsBinding.tvCreateAccount.getWidth() / 2f,
          mActivityUserDetailsBinding.tvCreateAccount.getHeight() / 2f,
          mActivityUserDetailsBinding.tvCreateAccount.getWidth() / 2f,
          mActivityUserDetailsBinding.tvCreateAccount.getHeight() / 2f).setListener(
          () -> {
            mActivityUserDetailsBinding.tvCreateAccount.setVisibility(View.GONE);
            mActivityUserDetailsBinding.pbCreateAccount.setVisibility(View.VISIBLE);
          }).start();
    } else {
      mActivityUserDetailsBinding.tvCreateAccount.setVisibility(View.VISIBLE);
      mActivityUserDetailsBinding.pbCreateAccount.setVisibility(View.GONE);
      mActivityUserDetailsBinding.cmlCreateAccount.reverse();
    }
  }

  @Override public void clearAllTextInputErrors() {
    mActivityUserDetailsBinding.tilUserDetailsFullName.setErrorEnabled(false);
    mActivityUserDetailsBinding.tilUserDetailsFullName.setError(null);

    mActivityUserDetailsBinding.tilUserDetailsBirthday.setErrorEnabled(false);
    mActivityUserDetailsBinding.tilUserDetailsBirthday.setError(null);

    mActivityUserDetailsBinding.tilUserDetailsEmail.setErrorEnabled(false);
    mActivityUserDetailsBinding.tilUserDetailsEmail.setError(null);

    mActivityUserDetailsBinding.tilUserDetailsAddress.setErrorEnabled(false);
    mActivityUserDetailsBinding.tilUserDetailsAddress.setError(null);

    mActivityUserDetailsBinding.tilUserDetailsCity.setErrorEnabled(false);
    mActivityUserDetailsBinding.tilUserDetailsCity.setError(null);

    mActivityUserDetailsBinding.tilUserDetailsState.setErrorEnabled(false);
    mActivityUserDetailsBinding.tilUserDetailsState.setError(null);
  }

  @Override
  public void launchHomeScreen() {
    Intent intent = new Intent(this, HomeActivity.class);
    finish();
    startActivity(intent);
  }

  @Override public void setPermissionsGranted() {
    hasPermissions = true;
  }

  @Override public void showPermissionGrantFromSettings() {
    new AlertDialog.Builder(this)
        .setTitle("Permission denied")
        .setCancelable(false)
        .setMessage(
            "Without those permission(s) the app will not work. Click Settings to go to App settings to let you do so.")
        .setPositiveButton("Exit", (dialog, which) -> {
          dialog.dismiss();
          finishAffinity();
        })
        .setNegativeButton("Setting", (dialog, which) -> {
          dialog.dismiss();
          Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
          Uri uri = Uri.fromParts("package", getPackageName(), null);
          intent.setData(uri);
          startActivity(intent);
          finishAffinity();
        })
        .show();
  }

  @Override public void setPickImageProgressVisibility(boolean isVisible) {
    if (isVisible) {
      mActivityUserDetailsBinding.fabUserDetailsChooseProfilePhoto.hide();
      mActivityUserDetailsBinding.pbUserDetailsPhotoUpload.setVisibility(View.VISIBLE);
    } else {
      mActivityUserDetailsBinding.fabUserDetailsChooseProfilePhoto.show();
      mActivityUserDetailsBinding.pbUserDetailsPhotoUpload.setVisibility(View.INVISIBLE);
    }
  }

  @Override public void setUserProfileImage(Bitmap profileImage) {
    mUserDetail.getIsPhotoPicked().set(true);
    mActivityUserDetailsBinding.civUserDetailsProfileImage.setImageBitmap(profileImage);
  }

  @Override public void startImageCropperActivity() {
    if (hasPermissions) {
      CropImage.activity()
          .setAllowFlipping(false)
          .setFixAspectRatio(true)
          .setAspectRatio(1, 1)
          .setGuidelines(CropImageView.Guidelines.ON)
          .start(this);
    } else {
      checkPermissions();
    }
  }

  @Override
  public void generalResponse(int responseId) {
    showSnackBar(getString(responseId), findViewById(R.id.coordinator_layout));
  }

  @Override public void checkPermissions() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
          != PackageManager.PERMISSION_GRANTED ||
          ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
              != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        }, PERMISSION_REQUEST_CODE);
      } else {
        hasPermissions = true;
      }
    } else {
      hasPermissions = true;
    }
  }
}
