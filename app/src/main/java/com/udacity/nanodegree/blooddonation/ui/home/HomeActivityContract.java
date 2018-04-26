package com.udacity.nanodegree.blooddonation.ui.home;

import android.content.Intent;
import android.os.Bundle;
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

        /**
         * Method to switch to a particular activity.
         */
        void switchActivity(Class activity, int requestCode, @Nullable Bundle bundle);
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

        /**
         * Method to handle onActivityResult.
         */
        void handleActivityResult(int requestCode, int resultCode, Intent data);
    }
}
