package com.udacity.nanodegree.blooddonation.ui.login.presenter;


import com.udacity.nanodegree.blooddonation.ui.login.LoginActivityContract;
import com.udacity.nanodegree.blooddonation.ui.login.LoginInfo;


/**
 * Created by riteshksingh on Apr, 2018
 */
public class LoginActivityPresenter implements LoginActivityContract
        .Presenter {

    private LoginActivityContract.View mView;


    public LoginActivityPresenter(LoginActivityContract.View view) {
        mView = view;
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

    @Override
    public void onLoginClick(LoginInfo loginInfo) {

    }
}
