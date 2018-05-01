package com.udacity.nanodegree.blooddonation.ui.login;

import com.udacity.nanodegree.blooddonation.base.BasePresenter;
import com.udacity.nanodegree.blooddonation.base.BaseView;

/**
 * Created by riteshksingh on Apr, 2018
 */
public interface UserLoginContract {
    interface Presenter extends BasePresenter {
        void onIamInButtonClick(String phoneNumber, String phoneCode);

        void onVerifyOtpButtonClick(String otp);

        void onResendCodeButtonClick();
    }

    interface View extends BaseView {

        void launchHomeScreen();

        void launchUserDetailsScreen();

        void showHideLoader(boolean isActive);

        void setVerificationTitleBar();

        void setUserRegisInfoIsCodeFlag(boolean b);

        void setTimerCount(long seconds);

        void setPhoneNumber(String phoneNumber);

        void showCountdown(boolean visible);

        /**
         * Method to toggle between registration and verification screen.
         *
         * @param isRegistrationVisible: if null Both screens will be invisible.
         */
        void toggleVerificationRegistrationScreen(Boolean isRegistrationVisible);

    }
}
