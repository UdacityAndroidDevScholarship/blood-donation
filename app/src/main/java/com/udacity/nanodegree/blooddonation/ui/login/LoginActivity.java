package com.udacity.nanodegree.blooddonation.ui.login;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class LoginActivity extends BaseActivity<LoginContract.Presenter, ViewDataBinding> implements LoginContract.View {


    @Override
    protected int getContentResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {

    }


    @Override
    protected void beforeView(@Nullable Bundle savedInstanceState) {

    }
}
