package com.udacity.nanodegree.blooddonation.ui.login.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.PhoneAuthProvider;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.databinding.ActivityUserLoginBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.view.HomeActivity;
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

  private EditText etPhoneNumber;

  private Button iAmInButton;

  private int PHONE_NUMBER_DIGITS;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_login);

    mPresenter = new UserLoginPresenter(Injection.provideFireBaseAuth(),Injection.getSharedPreference(), this);

    userLoginInfo = new UserLoginInfo();

    etPhoneNumber = findViewById(R.id.et_phone_number);
    iAmInButton = findViewById(R.id.bv_in);
    PHONE_NUMBER_DIGITS = 10;

    etPhoneNumber.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
           if(count == PHONE_NUMBER_DIGITS){
               iAmInButton.setEnabled(true);
           }else {
               etPhoneNumber.setError("The mobile number must have 10 digits !!!");
               iAmInButton.setEnabled(false);
           }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });

    ((ActivityUserLoginBinding) mBinding).setRegisInfo(userLoginInfo);
    ((ActivityUserLoginBinding) mBinding).setPresenter(mPresenter);

    ((ActivityUserLoginBinding) mBinding).ccCountryCode.registerPhoneNumberTextView(
        ((ActivityUserLoginBinding) mBinding).etPhoneNumber);

    userLoginInfo.phoneCode.set("91");

    ((ActivityUserLoginBinding) mBinding).ccCountryCode.setOnCountryChangeListener(
        country -> userLoginInfo.phoneCode.set(country.getPhoneCode()));

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

      if (mBinding != null) {
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
    if (mBinding != null) {
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
