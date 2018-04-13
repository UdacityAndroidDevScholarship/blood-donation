package com.udacity.nanodegree.blooddonation.ui.registration;

import com.udacity.nanodegree.blooddonation.base.BasePresenter;

/**
 * Created by riteshksingh on Apr, 2018
 */
public interface UserRegistrationContract {
    interface Presenter extends BasePresenter {
        void onIamInButtonClick(UserRegistrationInfo userRegistrationInfo);
    }

    interface View {

    }
}
