package com.udacity.nanodegree.blooddonation.ui.home.presenter;

import com.udacity.nanodegree.blooddonation.ui.home.HomeActivityContract;

import android.content.Intent;

import com.udacity.nanodegree.blooddonation.ui.bloodrequest.view.BloodRequestActivity;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/04/2018.
 */
public class HomeActivityPresenter implements HomeActivityContract.Presenter {


    private final HomeActivityContract.View view;

    public static final int BLOOD_REQUEST_RC = 100;

    public HomeActivityPresenter(HomeActivityContract.View view) {
        this.view = view;
    }


    @Override
    public void onAddClicked() {
        view.switchActivity(BloodRequestActivity.class, BLOOD_REQUEST_RC, null);
    }

    @Override
    public void onCurrentLocationClicked() {
        view.updateCamera(null);
    }

    @Override
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
