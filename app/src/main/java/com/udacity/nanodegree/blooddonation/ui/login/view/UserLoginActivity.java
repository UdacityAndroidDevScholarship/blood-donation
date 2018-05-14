package com.udacity.nanodegree.blooddonation.ui.login.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.databinding.ActivityUserLoginBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.view.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.login.UserLoginContract;
import com.udacity.nanodegree.blooddonation.ui.login.UserLoginInfo;
import com.udacity.nanodegree.blooddonation.ui.login.model.UserPhoneNumber;
import com.udacity.nanodegree.blooddonation.ui.login.presenter.UserLoginPresenter;
import com.udacity.nanodegree.blooddonation.ui.userdetail.view.UserDetailActivity;

import java.util.Locale;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserLoginActivity extends BaseActivity implements UserLoginContract.View {

  private ActivityUserLoginBinding mLoginBinding;

  private UserLoginContract.Presenter mPresenter;

  private UserLoginInfo mUserLoginInfo;

  private UserPhoneNumber mUserPhoneNumber;

  private Observable.OnPropertyChangedCallback mPhoneNumberChangedCallback =
      new Observable.OnPropertyChangedCallback() {
        public void onPropertyChanged(Observable sender, int propertyId) {
          mUserPhoneNumber.setPhoneNumber(mUserLoginInfo.phoneNumber.get());
        }
      };

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_login);
    mLoginBinding = (ActivityUserLoginBinding) mBinding;

    mPresenter = new UserLoginPresenter(Injection.provideFireBaseAuth(),
        Injection.provideFireBaseDatabase(), Injection.getSharedPreference(), this);

    mUserLoginInfo = new UserLoginInfo();

    mUserPhoneNumber = new UserPhoneNumber();

    mLoginBinding.setUserLoginInfo(mUserLoginInfo);
    mLoginBinding.setPresenter(mPresenter);

    mLoginBinding.ccCountryCode.registerPhoneNumberTextView(
        mLoginBinding.etPhoneNumber);

    mUserLoginInfo.phoneCode.set("91");

    mUserPhoneNumber.setCountryName(mLoginBinding.ccCountryCode.getDefaultCountryName());
    mUserPhoneNumber.setIso(mLoginBinding.ccCountryCode.getSelectedCountryNameCode());
    mUserPhoneNumber.setPhoneCode(mLoginBinding.ccCountryCode.getDefaultCountryCode());

    mLoginBinding.ccCountryCode.setOnCountryChangeListener(
        country -> {
          mUserLoginInfo.phoneCode.set(country.getPhoneCode());
          mUserPhoneNumber.setCountryName(country.getName());
          mUserPhoneNumber.setIso(country.getIso());
          mUserPhoneNumber.setPhoneCode(country.getPhoneCode());
        }
    );

    mUserLoginInfo.phoneNumber.addOnPropertyChangedCallback(mPhoneNumberChangedCallback);
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
    mUserLoginInfo.phoneNumber.removeOnPropertyChangedCallback(mPhoneNumberChangedCallback);
    mPresenter.onDestroy();
  }

  @Override public void launchHomeScreen() {
    Intent intent = new Intent(this, HomeActivity.class);
    finish();
    startActivity(intent);
  }

  @Override public void launchUserDetailsScreen() {
    mUserPhoneNumber.saveToSharedPreferences(Injection.getSharedPreference());
    Intent intent = new Intent(this, UserDetailActivity.class);
    intent.putExtra(UserPhoneNumber.EXTRA_USER_PHONE_NUMBER, mUserPhoneNumber);
    finish();
    startActivity(intent);
  }

  @Override public void setProceedProgressVisibility(boolean isVisible) {
    if (isVisible) {
      mLoginBinding.cmlProceedLayout.revealFrom(mLoginBinding.tvProceed.getWidth() / 2f,
          mLoginBinding.tvProceed.getHeight() / 2f,
          mLoginBinding.tvProceed.getWidth() / 2f,
          mLoginBinding.tvProceed.getHeight() / 2f).setListener(
          () -> {
            mLoginBinding.tvProceed.setVisibility(View.GONE);
            mLoginBinding.pbProcessing.setVisibility(View.VISIBLE);
          }).start();
    } else {
      mLoginBinding.tvProceed.setVisibility(View.VISIBLE);
      mLoginBinding.pbProcessing.setVisibility(View.GONE);
      mLoginBinding.cmlProceedLayout.reverse();
    }
  }

  @Override public void setVerifyProgressVisibility(boolean isVisible) {
    if (isVisible) {
      mLoginBinding.cmlVerifyLayout.revealFrom(mLoginBinding.tvVerify.getWidth() / 2f,
          mLoginBinding.tvVerify.getHeight() / 2f,
          mLoginBinding.tvVerify.getWidth() / 2f,
          mLoginBinding.tvVerify.getHeight() / 2f).setListener(
          () -> {
            mLoginBinding.tvVerify.setVisibility(View.GONE);
            mLoginBinding.pbVerifying.setVisibility(View.VISIBLE);
          }).start();
    } else {
      mLoginBinding.tvVerify.setVisibility(View.VISIBLE);
      mLoginBinding.pbVerifying.setVisibility(View.GONE);
      mLoginBinding.cmlVerifyLayout.reverse();
    }
  }

  @Override public void showPhoneNumberLayout() {
    setTitle(getString(R.string.app_name));

    mLoginBinding.layoutVerification.setVisibility(View.GONE);
    mLoginBinding.layoutRegistration.setVisibility(View.VISIBLE);
  }

  @Override public void showVerifyOtpLayout() {
    setTitle(getString(R.string.user_login_title_verification));

    mLoginBinding.layoutVerification.setVisibility(View.VISIBLE);
    mLoginBinding.layoutRegistration.setVisibility(View.GONE);
  }

  @Override public void setResendButtonEnabled(boolean isEnabled) {
    if (isEnabled) {
      mLoginBinding.btResendCode.setEnabled(true);
      mLoginBinding.btResendCode.setText(R.string.user_login_resend_code);
    } else {
      mLoginBinding.btResendCode.setEnabled(false);
    }
  }

  @Override public void setResendButtonTimerCount(long secondsRemaining) {
    mLoginBinding.btResendCode.setText(
        String.format(Locale.ENGLISH, getString(R.string.user_login_resend_code_timer), secondsRemaining));
  }

  @Override public void setVerifyScreenPhoneNumber(String phoneNumber) {
    mLoginBinding.tvPhoneNumber.setText(phoneNumber);
  }

  @Override public void showEditPhoneDialog() {
    new AlertDialog.Builder(this)
        .setMessage(
            String.format(
                getString(R.string.user_login_msg_currently_verifying_number),
                mUserLoginInfo.phoneCode.get(), mUserLoginInfo.phoneNumber.get()))
        .setCancelable(false)
        .setPositiveButton(android.R.string.yes,
            (dialog, which) -> mPresenter.onEditPhoneNumberActionYes())
        .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
        .show();
  }

  @Override public void generalResponse(int responseId) {
    showSnackBar(getString(responseId));
  }
}
