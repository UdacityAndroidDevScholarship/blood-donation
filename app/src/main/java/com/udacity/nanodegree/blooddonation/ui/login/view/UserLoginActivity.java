package com.udacity.nanodegree.blooddonation.ui.login.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;
import com.udacity.nanodegree.blooddonation.databinding.ActivityUserLoginBinding;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.login.UserLoginContract;
import com.udacity.nanodegree.blooddonation.ui.login.UserLoginInfo;
import com.udacity.nanodegree.blooddonation.ui.login.presenter.UserLoginPresenter;
import com.udacity.nanodegree.blooddonation.ui.userdetail.view.UserDetailActivity;


/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserLoginActivity extends BaseActivity
    implements UserLoginContract.View {

  private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

  private UserLoginContract.Presenter mPresenter;

  private UserLoginInfo userLoginInfo;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_login);

    mPresenter = new UserLoginPresenter(Injection.getFirebaseAuth(),Injection.getSharedPreference(), this);

    userLoginInfo = new UserLoginInfo();

    ((ActivityUserLoginBinding) mBinding).setRegisInfo(userLoginInfo);
    ((ActivityUserLoginBinding) mBinding).setPresenter(mPresenter);

    ((ActivityUserLoginBinding) mBinding).ccCountryCode.registerPhoneNumberTextView(
        ((ActivityUserLoginBinding) mBinding).etPhoneNumber);

    userLoginInfo.phoneCode.set("91");

    ((ActivityUserLoginBinding) mBinding).ccCountryCode.setOnCountryChangeListener(
        new CountryCodePicker.OnCountryChangeListener() {
          @Override public void onCountrySelected(Country country) {
            userLoginInfo.phoneCode.set(country.getPhoneCode());
          }
        });

    mPresenter.onCreate();
  }

  @Override public void showNotValidPhoneNumberMessage() {
    Toast.makeText(this, "Not a valid phone number", Toast.LENGTH_SHORT).show();
  }

  @Override public void showLimitExceededMessage() {
    Toast.makeText(this, "Limit Exceeded", Toast.LENGTH_SHORT).show();
  }


  @Override
  public void launchHomeScreen() {
    Intent intent = new Intent(this, HomeActivity.class);
    finish();
    startActivity(intent);
  }

  @Override
  public void launchUserDetailsScreen() {
    Intent intent = new Intent(this, UserDetailActivity.class);
    finish();
    startActivity(intent);
  }

  @Override public void showInvalidVerificationCodeMessage() {
    Toast.makeText(UserLoginActivity.this, "Invalid verification code", Toast.LENGTH_SHORT)
        .show();
  }

  @Override public void showHideLoader(boolean isActive) {
    if (isActive) {
      ((ActivityUserLoginBinding) mBinding).pbLoader.setVisibility(View.VISIBLE);
      return;
    }

    ((ActivityUserLoginBinding) mBinding).pbLoader.setVisibility(View.GONE);
  }

  @Override public void setVerificationTitleBar() {
    getSupportActionBar().setTitle(R.string.verification);
  }

  @Override
  public void setTimerCount(long seconds) {
    if (seconds>0) {
      String secondsLeft = getString(R.string.countdown, (int) seconds);

      if (((ActivityUserLoginBinding) mBinding) != null) {
        ((ActivityUserLoginBinding) mBinding).tvCountdown.setText(secondsLeft);
      }

    }
  }

  @Override
  public void hidePhoneNumberScreen() {
    ((ActivityUserLoginBinding)mBinding).layoutRegistration.setVisibility(View.GONE);
  }

  @Override
  public void showVerificationScreen(boolean visible) {
    ((ActivityUserLoginBinding)mBinding).layoutVerification.setVisibility(visible ? View.VISIBLE : View.GONE);
  }

  @Override
  public void setPhoneNumber(String phoneNumber) {
    String otpSentText = getString(R.string.otp_sent_text,phoneNumber);
    ((ActivityUserLoginBinding) mBinding).tvVerifyLabel.setText(otpSentText);
  }

  @Override
  public void showCountdown(boolean visible) {
    if (((ActivityUserLoginBinding)mBinding)!= null) {
      ((ActivityUserLoginBinding) mBinding).tvCountdown.setVisibility(visible ? View.VISIBLE : View.GONE);
      ((ActivityUserLoginBinding) mBinding).btnResendCode.setVisibility(visible ? View.GONE : View.VISIBLE);
    }
  }

  @Override public void setUserRegisInfoIsCodeFlag(boolean b) {
    userLoginInfo.isCodeSent.set(true);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }
}
