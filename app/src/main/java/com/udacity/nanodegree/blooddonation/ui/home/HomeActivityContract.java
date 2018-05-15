package com.udacity.nanodegree.blooddonation.ui.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.udacity.nanodegree.blooddonation.base.BasePresenter;
import com.udacity.nanodegree.blooddonation.base.BaseView;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;

import java.util.ArrayList;

public interface HomeActivityContract {

    interface View extends GoogleMap.OnMarkerClickListener, OnMapReadyCallback, BaseView {

        /**
         * Method to zoom camera to a particular location. If {@code position} is null the camera
         * must animate to the current location if it is available.
         *
         * @param position: Lat long to animate camera at.
         */

        void updateCamera(@Nullable LatLng position);

        void openCreateRequestDialog(@NonNull User user, @Nullable LatLng location);

        void addDonorMarkers(ArrayList<ReceiverDonorRequestType> donors);

        void addReceiverMarkers(ArrayList<ReceiverDonorRequestType> receivers);

        void addMarker(@NonNull ReceiverDonorRequestType request, boolean isDonor);

        void showHideLoader(boolean isActive);

        void fetchCurrentLocation();

        void removeOldMarkers(ArrayList<Marker> markers);
    }

    interface Presenter extends BasePresenter {

        /**
         * Action to perform on pressing add button.
         */
        void onAddClicked();

        /**
         * Method to create blood request for custom location.
         *
         * @param location
         */
        void createRequestDialogForCustomLocation(@NonNull LatLng location);

        /**
         * Action to perform on current location click.
         */
        void onCurrentLocationClicked();

        //void queryGeoFire(LatLng latLng);

        //void queryGeoFire(LatLng latLng, String bgp);

        void onBloodRequest(RequestDetails requestDetails);

        void onDonateRequest(RequestDetails requestDetails);
    }
}
