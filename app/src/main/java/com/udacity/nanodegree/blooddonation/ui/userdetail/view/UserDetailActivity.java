package com.udacity.nanodegree.blooddonation.ui.userdetail.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.databinding.ActivityUserDetailsBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.userdetail.UserDetailContract;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;
import com.udacity.nanodegree.blooddonation.ui.userdetail.presenter.UserDetailPresenter;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetailActivity extends BaseActivity {

  private UserDetailContract.Presenter mPresenter;
  private ActivityUserDetailsBinding mActivityUserDetailsBinding;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter =
        new UserDetailPresenter(Injection.getFirebaseAuth(), Injection.getSharedPreference());

    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);
    mActivityUserDetailsBinding = (ActivityUserDetailsBinding) mBinding;

    mActivityUserDetailsBinding.setPresenter(mPresenter);
    mActivityUserDetailsBinding.setUserdetail(new UserDetail());

    getSupportActionBar().setTitle(R.string.user_profile);
  }
}
