package com.udacity.nanodegree.blooddonation.ui.home.presenter;

import com.udacity.nanodegree.blooddonation.ui.home.HomeActivityContract;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/04/2018.
 */
public class HomeActivityPresenter implements HomeActivityContract.Presenter {

  private final HomeActivityContract.View mView;

  public HomeActivityPresenter(HomeActivityContract.View view) {
    this.mView = view;
  }

  @Override public void onCurrentLocationClicked() {
    mView.updateCamera(null);
  }

  @Override public void onAddClicked() {
    mView.openCreateRequestDialog();
  }

  @Override public void onCreate() {

  }

  @Override public void onStart() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {

  }

  @Override public void onBloodRequest(RequestDetails requestDetails) {

  }

  @Override public void onDonateRequest(RequestDetails requestDetails) {

  }
}
