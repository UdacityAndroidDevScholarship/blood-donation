package com.udacity.nanodegree.blooddonation.ui.login;

import com.udacity.nanodegree.blooddonation.base.BasePresenter;

/**
 * Created by riteshksingh on Apr, 2018
 */
public interface UserLoginContract {
  interface Presenter extends BasePresenter {
    void onIamInButtonClick(String phoneNumber,String phoneCode);

    void onVerifyOtpButtonClick(String otp);

    void onResendCodeButtonClick();
  }

  interface View {
    void showNotValidPhoneNumberMessage();

    void showLimitExceededMessage();

    void launchHomeScreen();

    void launchUserDetailsScreen();

    void showInvalidVerificationCodeMessage();

    void showHideLoader(boolean isActive);

    void setVerificationTitleBar();

    void setUserRegisInfoIsCodeFlag(boolean b);

    void setTimerCount(long seconds);

    void setPhoneNumber(String phoneNumber);

    void showCountdown(boolean visible);

    void hidePhoneNumberScreen();

    void showVerificationScreen(boolean visible);
  }
}
