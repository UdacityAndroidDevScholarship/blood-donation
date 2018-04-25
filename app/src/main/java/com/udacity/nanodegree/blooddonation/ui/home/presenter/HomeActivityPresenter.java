package com.udacity.nanodegree.blooddonation.ui.home.presenter;

import com.udacity.nanodegree.blooddonation.ui.home.HomeActivityContract;

import android.content.Intent;

import com.udacity.nanodegree.blooddonation.ui.bloodrequest.view.BloodRequestActivity;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/04/2018.
 */
public class HomeActivityPresenter implements HomeActivityContract.Presenter {

  private final HomeActivityContract.View mView;

  public static final int BLOOD_REQUEST_RC = 100;

  public HomeActivityPresenter(HomeActivityContract.View view) {
    this.mView = view;
  }

  @Override public void onCurrentLocationClicked() {
    mView.updateCamera(null);
  }

  @Override public void onAddClicked() {
    mView.switchActivity(BloodRequestActivity.class, BLOOD_REQUEST_RC, null);
  }

  @Override public void onCreate() {

  }

  @Override public void onStart() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {

  }

  @Override public void handleActivityResult(int requestCode, int resultCode, Intent data) {

  }
}
