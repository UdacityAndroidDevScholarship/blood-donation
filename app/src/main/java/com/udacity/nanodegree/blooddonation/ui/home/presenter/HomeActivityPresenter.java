package com.udacity.nanodegree.blooddonation.ui.home.presenter;

import com.udacity.nanodegree.blooddonation.ui.home.HomeActivityContract;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/04/2018.
 */
public class HomeActivityPresenter implements HomeActivityContract.Presenter {

  private final HomeActivityContract.View view;

  public HomeActivityPresenter(HomeActivityContract.View view) {
    this.view = view;
  }

  @Override public void onAddClicked() {
    view.generalInfo(0);
  }

  @Override public void onCurrentLocationClicked() {
    view.updateCamera(null);
  }

  @Override public void onCreate() {

  }

  @Override public void onStart() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {

  }
}
