package com.udacity.nanodegree.blooddonation.ui.userdetail;

import com.udacity.nanodegree.blooddonation.base.BasePresenter;
import com.udacity.nanodegree.blooddonation.base.BaseView;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;

/**
 * Created by riteshksingh on Apr, 2018
 */
public interface UserDetailContract {
    interface Presenter extends BasePresenter {
        void onCreateNowClick(UserDetail userDetail);

        void onDobButtonClick();

        void onLocationClick();
    }

    interface View extends BaseView {
        void showDatePickerDialog();

        void getLastLocation();

        void launchHomeScreen();

    }
}
