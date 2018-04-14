package com.udacity.nanodegree.blooddonation.ui.registration.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.udacity.nanodegree.blooddonation.databinding.ActivityUserRegisBinding;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.registration.UserRegistrationContract;
import com.udacity.nanodegree.blooddonation.ui.registration.UserRegistrationInfo;
import com.udacity.nanodegree.blooddonation.ui.registration.presenter.UserRegistrationPresenter;
import java.util.concurrent.TimeUnit;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserRegistrationActivity extends BaseActivity
    implements UserRegistrationContract.View {

  private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

  private UserRegistrationContract.Presenter mPresenter;

  private UserRegistrationInfo userRegistrationInfo;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_regis);

    mPresenter = new UserRegistrationPresenter(Injection.getFirebaseAuth(), this);

    userRegistrationInfo = new UserRegistrationInfo();

    ((ActivityUserRegisBinding) mBinding).setRegisInfo(userRegistrationInfo);
    ((ActivityUserRegisBinding) mBinding).setPresenter(mPresenter);

    mPresenter.onCreate();
  }

  @Override public void showNotValidPhoneNumberMessage() {
    Toast.makeText(this, "No a valid phone number", Toast.LENGTH_SHORT).show();
  }

  @Override public void showLimitExceededMessage() {
    Toast.makeText(this, "Limit Exceeded", Toast.LENGTH_SHORT).show();
  }

  @Override public void onSignInSuccess() {
    Intent intent = new Intent(this, HomeActivity.class);
    finish();
    startActivity(intent);
  }

  @Override public void showInvalidVerificationCodeMessage() {
    Toast.makeText(UserRegistrationActivity.this, "Invalid verification code", Toast.LENGTH_SHORT)
        .show();
  }

  @Override public void showHideLoader(boolean isActive) {
    if (isActive) {
      ((ActivityUserRegisBinding) mBinding).pbLoader.setVisibility(View.VISIBLE);
      return;
    }

    ((ActivityUserRegisBinding) mBinding).pbLoader.setVisibility(View.GONE);
  }

  @Override public void setVerificationTitleBar() {
    getSupportActionBar().setTitle(R.string.verification);
  }

  @Override public void setUserRegisInfoIsCodeFlag(boolean b) {
    userRegistrationInfo.isCodeSent.set(true);
  }
}
