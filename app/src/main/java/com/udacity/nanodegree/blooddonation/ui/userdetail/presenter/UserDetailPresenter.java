package com.udacity.nanodegree.blooddonation.ui.userdetail.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.udacity.nanodegree.blooddonation.storage.SharedPreferenceManager;
import com.udacity.nanodegree.blooddonation.ui.userdetail.UserDetailContract;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetailPresenter implements UserDetailContract.Presenter {

  private FirebaseAuth mFirebaseAuth;
  private SharedPreferenceManager mSharedPreferenceManager;

  public UserDetailPresenter(FirebaseAuth firebaseAuth, SharedPreferenceManager sharedPreference) {
    mFirebaseAuth = firebaseAuth;
    mSharedPreferenceManager = sharedPreference;
  }

  @Override public void onCreate() {

  }

  @Override public void onStart() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {

  }

  @Override public void onCreateNowClick(UserDetail userDetail) {
    System.out.println("onCreateNowClick - " + userDetail.isMale.get());
  }
}
