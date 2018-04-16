package com.udacity.nanodegree.blooddonation.ui.userdetail.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.common.picker.DatePickerFragment;
import com.udacity.nanodegree.blooddonation.databinding.ActivityUserDetailsBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.userdetail.UserDetailContract;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;
import com.udacity.nanodegree.blooddonation.ui.userdetail.presenter.UserDetailPresenter;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetailActivity extends BaseActivity implements UserDetailContract.View {

  private UserDetailContract.Presenter mPresenter;
  private ActivityUserDetailsBinding mActivityUserDetailsBinding;

  private UserDetail mUserDetail;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter =
        new UserDetailPresenter(this,Injection.getFirebaseAuth(), Injection.getSharedPreference());

    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);
    mActivityUserDetailsBinding = (ActivityUserDetailsBinding) mBinding;

    mUserDetail = new UserDetail();

    mActivityUserDetailsBinding.setPresenter(mPresenter);
    mActivityUserDetailsBinding.setUserdetail(mUserDetail);

    getSupportActionBar().setTitle(R.string.user_profile);

    initSpinner();
  }

  private void initSpinner() {
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.blood_group, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mActivityUserDetailsBinding.bloodGroupDropDown.setAdapter(adapter);
  }

  @Override public void showDatePickerDialog() {
    DialogFragment dialogFragment = DatePickerFragment.newInstance(mUserDetail.dob);
    dialogFragment.show(getSupportFragmentManager(),"datefragment");
  }
}
