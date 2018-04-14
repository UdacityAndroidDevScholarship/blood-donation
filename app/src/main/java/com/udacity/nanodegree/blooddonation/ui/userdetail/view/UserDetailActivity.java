package com.udacity.nanodegree.blooddonation.ui.userdetail.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetailActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);
    }
}
