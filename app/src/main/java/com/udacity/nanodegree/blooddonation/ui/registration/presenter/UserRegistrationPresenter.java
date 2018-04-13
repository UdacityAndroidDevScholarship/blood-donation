package com.udacity.nanodegree.blooddonation.ui.registration.presenter;

import android.text.TextUtils;
import com.udacity.nanodegree.blooddonation.ui.registration.UserRegistrationContract;
import com.udacity.nanodegree.blooddonation.util.Util;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserRegistrationPresenter implements UserRegistrationContract.Presenter {

  private UserRegistrationContract.View mView;

  public UserRegistrationPresenter(UserRegistrationContract.View view) {
    mView = view;
  }

  @Override public void onCreate() {

  }

  @Override public void onStart() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {

  }

  @Override public void onIamInButtonClick(String phoneNumber) {
    if (phoneNumber != null && Util.isValidPhoneNumber(phoneNumber)) {
      mView.verifyPhoneNumber();
    } else {
      mView.showNotValidPhoneNumberMessage();
    }
  }

  @Override public void onVerifyOtpButtonClick(String otp) {
    if (otp != null && !TextUtils.isEmpty(otp)) {
      mView.signIn();
    }
  }
}
