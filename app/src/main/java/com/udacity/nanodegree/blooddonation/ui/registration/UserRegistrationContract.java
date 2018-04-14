package com.udacity.nanodegree.blooddonation.ui.registration;

import com.udacity.nanodegree.blooddonation.base.BasePresenter;

/**
 * Created by riteshksingh on Apr, 2018
 */
public interface UserRegistrationContract {
  interface Presenter extends BasePresenter {
    void onIamInButtonClick(String phoneNumber,String phoneCode);

    void onVerifyOtpButtonClick(String otp);
  }

  interface View {
    void showNotValidPhoneNumberMessage();

    void showLimitExceededMessage();

    void onSignInSuccess();

    void showInvalidVerificationCodeMessage();

    void showHideLoader(boolean isActive);

    void setVerificationTitleBar();

    void setUserRegisInfoIsCodeFlag(boolean b);
  }
}
