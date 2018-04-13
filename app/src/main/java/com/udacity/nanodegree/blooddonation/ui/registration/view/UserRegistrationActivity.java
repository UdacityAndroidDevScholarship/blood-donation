package com.udacity.nanodegree.blooddonation.ui.registration.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.udacity.nanodegree.blooddonation.databinding.ActivityUserRegisBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.ui.registration.UserRegistrationContract;
import com.udacity.nanodegree.blooddonation.ui.registration.UserRegistrationInfo;
import com.udacity.nanodegree.blooddonation.ui.registration.presenter.UserRegistrationPresenter;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserRegistrationActivity extends BaseActivity
    implements UserRegistrationContract.View {

  private UserRegistrationContract.Presenter mPresenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_regis);

    mPresenter = new UserRegistrationPresenter(this, Injection.getFirebaseAuth());

    ((ActivityUserRegisBinding) mBinding).setPresenter(mPresenter);
    ((ActivityUserRegisBinding) mBinding).setRegisInfo(new UserRegistrationInfo());
  }

  @Override protected void onStart() {
    super.onStart();
    mPresenter.onStart();
  }

  @Override protected void onStop() {
    super.onStop();
    mPresenter.onStop();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }

}
