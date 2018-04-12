package com.udacity.nanodegree.blooddonation.ui.home;

import com.udacity.nanodegree.blooddonation.base.BasePresenter;
import com.udacity.nanodegree.blooddonation.ui.home.HomeContract;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    @Override
    public void checkView() {
        getView().checkData();
    }
}
