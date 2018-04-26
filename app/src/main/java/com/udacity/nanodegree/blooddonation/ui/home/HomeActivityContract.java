package com.udacity.nanodegree.blooddonation.ui.home;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.udacity.nanodegree.blooddonation.base.BasePresenter;

public interface HomeActivityContract {

  interface View extends GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    /**
     * Method to zoom camera to a particular location. If {@code position} is null the camera
     * must animate to the current location if it is available.
     *
     * @param position: Lat long to animate camera at.
     */
    void updateCamera(@Nullable LatLng position);

    void openCreateRequestDialog();
  }

  interface Presenter extends BasePresenter {

    /**
     * Action to perform on pressing add button.
     */
    void onAddClicked();

    /**
     * Action to perform on current location click.
     */
    void onCurrentLocationClicked();
  }
}
