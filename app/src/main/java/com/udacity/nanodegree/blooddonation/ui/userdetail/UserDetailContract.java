package com.udacity.nanodegree.blooddonation.ui.userdetail;

import android.support.annotation.StringRes;

import com.udacity.nanodegree.blooddonation.base.BasePresenter;
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

    interface View {
        void showDatePickerDialog();

        void getLastLocation();

        void launchHomeScreen();

        /**
         * Method to display general response.
         *
         * @param responseId: String resource id of the message.
         */
        void generalResponse(@StringRes int responseId);


    }
}
