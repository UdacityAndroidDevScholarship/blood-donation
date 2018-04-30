package com.udacity.nanodegree.blooddonation.ui.login.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.PhoneAuthProvider;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.databinding.ActivityUserLoginBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.view.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.login.UserLoginContract;
import com.udacity.nanodegree.blooddonation.ui.login.UserLoginInfo;
import com.udacity.nanodegree.blooddonation.ui.login.presenter.UserLoginPresenter;
import com.udacity.nanodegree.blooddonation.ui.userdetail.view.UserDetailActivity;


/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserLoginActivity extends BaseActivity
        implements UserLoginContract.View {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private UserLoginContract.Presenter mPresenter;

    private UserLoginInfo userLoginInfo;

    private ActivityUserLoginBinding mLoginBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_login);
        mLoginBinding = ((ActivityUserLoginBinding) mBinding);
        mPresenter = new UserLoginPresenter(Injection.provideFireBaseAuth(), Injection.provideFireBaseDatabase(), Injection.getSharedPreference(), this);

        userLoginInfo = new UserLoginInfo();

        mLoginBinding.setRegisInfo(userLoginInfo);
        mLoginBinding.setPresenter(mPresenter);

        mLoginBinding.ccCountryCode.registerPhoneNumberTextView(
                mLoginBinding.etPhoneNumber);


        userLoginInfo.phoneCode.set("91");

        mLoginBinding.ccCountryCode.setOnCountryChangeListener(
                country -> userLoginInfo.phoneCode.set(country.getPhoneCode()));

        initEditTexts();

        mPresenter.onCreate();
    }

    @Override
    public void launchHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void launchUserDetailsScreen() {
        Intent intent = new Intent(this, UserDetailActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void showHideLoader(boolean isActive) {

        mLoginBinding.pbLoader.setVisibility(isActive ? View.VISIBLE : View.GONE);

    }

    @Override
    public void setVerificationTitleBar() {
        getSupportActionBar().setTitle(R.string.verification);
    }

    @Override
    public void setTimerCount(long seconds) {
        if (seconds > 0) {
            String secondsLeft = getString(R.string.countdown, (int) seconds);

            if (mBinding != null) {
                mLoginBinding.tvCountdown.setText(secondsLeft);
            }

        }
    }

    @Override
    public void toggleVerificationRegistrationScreen(Boolean isRegistrationVisible) {
        if (isRegistrationVisible == null) {
            mLoginBinding.layoutVerification.setVisibility(View.GONE);
            mLoginBinding.layoutRegistration.setVisibility(View.GONE);
        } else {
            mLoginBinding.layoutVerification.setVisibility(isRegistrationVisible ? View.GONE : View.VISIBLE);
            mLoginBinding.layoutRegistration.setVisibility(isRegistrationVisible ? View.VISIBLE : View.GONE);
        }

    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        String otpSentText = getString(R.string.otp_sent_text, phoneNumber);
        mLoginBinding.tvVerifyLabel.setText(otpSentText);
    }

    @Override
    public void showCountdown(boolean visible) {
        if (mBinding != null) {
            mLoginBinding.tvCountdown.setVisibility(visible ? View.VISIBLE : View.GONE);
            mLoginBinding.btnResendCode.setVisibility(visible ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void setUserRegisInfoIsCodeFlag(boolean b) {
        userLoginInfo.isCodeSent.set(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void generalResponse(int responseId) {
        Toast.makeText(this, responseId, Toast.LENGTH_SHORT).show();
    }

    private void initEditTexts() {
        int delay = 50;
        mLoginBinding.etPhoneNumber.setOnEditorActionListener((v, actionId, event) -> {
            new Handler().postDelayed(() -> mPresenter.onIamInButtonClick(userLoginInfo.phoneNumber.get(), userLoginInfo.phoneCode.get()), delay);
            return false;
        });

        mLoginBinding.etOtp.setOnEditorActionListener((v, actionId, event) -> {
            new Handler().postDelayed(() -> mPresenter.onVerifyOtpButtonClick(userLoginInfo.otp.get()), delay);
            return false;
        });
    }
}
