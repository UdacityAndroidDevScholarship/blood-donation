package com.udacity.nanodegree.blooddonation.ui.registration.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.udacity.nanodegree.blooddonation.ui.registration.UserRegistrationContract;
import com.udacity.nanodegree.blooddonation.ui.registration.UserRegistrationInfo;
import com.udacity.nanodegree.blooddonation.util.Util;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserRegistrationPresenter implements UserRegistrationContract.Presenter {

  private static final String TAG = UserRegistrationPresenter.class.getName();

  private UserRegistrationContract.View mView;

  private FirebaseAuth mFirebaseAuth;

  public UserRegistrationPresenter(UserRegistrationContract.View view, FirebaseAuth firebaseAuth) {
    mView = view;
    mFirebaseAuth = firebaseAuth;
  }

  @Override public void onCreate() {

  }

  @Override public void onStart() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {

  }

  @Override public void onIamInButtonClick(UserRegistrationInfo userRegistrationInfo) {
    if (userRegistrationInfo.phoneNumber.get() == null || !Util.isValidPhoneNumber(
        userRegistrationInfo.phoneNumber.get())) {
      return;
    }
  }
}
