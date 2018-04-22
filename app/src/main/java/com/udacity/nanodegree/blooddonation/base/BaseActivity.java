package com.udacity.nanodegree.blooddonation.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class BaseActivity extends AppCompatActivity {

    protected ViewDataBinding mBinding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        mBinding = null;
        super.onDestroy();
    }


}
