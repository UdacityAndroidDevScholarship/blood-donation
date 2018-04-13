package com.udacity.nanodegree.blooddonation.ui.login.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.databinding.ActivityLoginBinding;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.login.LoginActivityContract;
import com.udacity.nanodegree.blooddonation.ui.login.LoginInfo;
import com.udacity.nanodegree.blooddonation.ui.login.presenter.LoginActivityPresenter;
import com.udacity.nanodegree.blooddonation.ui.signup.view.SignUpActivity;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class LoginActivity extends BaseActivity implements
        LoginActivityContract.View,View.OnClickListener{

    private LoginActivityContract.Presenter loginActivityPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginActivityPresenter = new LoginActivityPresenter(this,
                Injection.getFirebaseAuth());
        ((ActivityLoginBinding) mBinding).setPresenter(loginActivityPresenter);
        ((ActivityLoginBinding) mBinding).setLoginInfo(new LoginInfo());
        ((ActivityLoginBinding) mBinding).setListener(this);
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

    private void launchHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginSuccess() {
        launchHomeActivity();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(this, "Autentication failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bv_sign_up:
                launchSignUpActivity();
                break;
        }
    }

    private void launchSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
