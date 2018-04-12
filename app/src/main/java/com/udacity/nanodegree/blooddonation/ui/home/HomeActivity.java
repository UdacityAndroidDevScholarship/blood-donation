package com.udacity.nanodegree.blooddonation.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.base.PresenterFactory;
import com.udacity.nanodegree.blooddonation.databinding.ActivityHomeBinding;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class HomeActivity extends BaseActivity<HomeContract.Presenter, ActivityHomeBinding> implements HomeContract.View {

    @Override
    protected int getContentResource() {
        return R.layout.activity_home;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        getPresenter().checkView();
    }



    @Override
    protected void beforeView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void checkData() {

    }
}
