package com.udacity.nanodegree.blooddonation.ui.splash;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivity;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class SplashActivity extends BaseActivity<SplashContract.Presenter, ViewDataBinding> implements SplashContract.View {
    private Long SPLASH_DELAY = 1000L;
    private Handler mDelayHandler = null;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()) {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelayHandler.removeCallbacks(runnable);
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {

        mDelayHandler = new Handler();

        mDelayHandler.postDelayed(runnable, SPLASH_DELAY);
    }


    @Override
    protected void beforeView(@Nullable Bundle savedInstanceState) {

    }
}
