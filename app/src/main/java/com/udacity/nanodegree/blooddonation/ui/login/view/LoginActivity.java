package com.udacity.nanodegree.blooddonation.ui.login.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.databinding.ActivityLoginBinding;
import com.udacity.nanodegree.blooddonation.ui.login.LoginActivityContract;
import com.udacity.nanodegree.blooddonation.ui.login.LoginInfo;
import com.udacity.nanodegree.blooddonation.ui.login.presenter.LoginActivityPresenter;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class LoginActivity extends BaseActivity implements
        LoginActivityContract.View {

    private LoginActivityContract.Presenter loginActivityPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginActivityPresenter = new LoginActivityPresenter(this);
        ((ActivityLoginBinding) mBinding).setPresenter(loginActivityPresenter);
        ((ActivityLoginBinding) mBinding).setLoginInfo(new LoginInfo());

        loginActivityPresenter.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginActivityPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginActivityPresenter.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginActivityPresenter.onDestroy();
    }

}
