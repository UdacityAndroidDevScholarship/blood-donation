package com.udacity.nanodegree.blooddonation.ui.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;
import com.udacity.nanodegree.blooddonation.base.BasePresenter;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;

public interface HomeActivityContract {

  interface View extends GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    /**
     * Method to zoom camera to a particular location. If {@code position} is null the camera
     * must animate to the current location if it is available.
     *
     * @param position: Lat long to animate camera at.
     */

    void updateCamera(@Nullable LatLng position);

    void setSearchCircle(@NonNull LatLng latLng);

    void openCreateRequestDialog();

    void addRequestMarker(ReceiverDonorRequestType receiverDonorRequestType);

    void addDonorMarker(ReceiverDonorRequestType receiverDonorRequestType);

    void showHideLoader(boolean isActive);

    void putGeoKeyMarker(String key, GeoLocation location);

    void removeOldGeoMarker(String key);

    void animateGeoMarker(String key, GeoLocation location);

    void showGeoQueryErrorDialogBox(DatabaseError error);
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

    void queryGeoFire(LatLng latLng);

    void queryGeoFire(LatLng latLng, String bgp);

    void onBloodRequest(RequestDetails requestDetails);

    void onDonateRequest(RequestDetails requestDetails);
  }
}
