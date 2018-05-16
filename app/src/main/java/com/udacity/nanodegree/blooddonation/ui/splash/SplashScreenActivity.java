package com.udacity.nanodegree.blooddonation.ui.splash;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.constants.SharedPrefConstants;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.view.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.login.view.UserLoginActivity;
import com.udacity.nanodegree.blooddonation.ui.userdetail.view.UserDetailActivity;
import com.udacity.nanodegree.blooddonation.util.AppVersionUtil;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class SplashScreenActivity extends BaseActivity {

    private static final Long SPLASH_DELAY = 2500L;
    private Handler mDelayHandler = new Handler();
    private TextView mAppNameView;
    private TextView mAppVersionView;
    private ImageView mAppLogoView;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()) {
                Intent intent;
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    intent = new Intent(SplashScreenActivity.this, UserLoginActivity.class);
                } else {
                    if (Injection.getSharedPreference().getBoolean(SharedPrefConstants.IS_USER_DETAILS_ENTERED)) {
                        intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    } else {
                        intent = new Intent(SplashScreenActivity.this, UserDetailActivity.class);
                    }
                }
                finish();
                overridePendingTransition(R.anim.enter, R.anim.exit);

                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

        mAppVersionView.setText(AppVersionUtil.getAppVersion(this));

        mDelayHandler.postDelayed(runnable, SPLASH_DELAY);

        startAnimation();
    }

    private void initViews() {
        setContentView(R.layout.activity_splash_screen);

        mAppLogoView = findViewById(R.id.iv_app_icon);
        mAppNameView = findViewById(R.id.tv_app_name);
        mAppVersionView = findViewById(R.id.tv_app_version);
    }

    private void startAnimation() {
        mAppNameView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mAppNameView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        AnimatorSet mAnimatorSet = new AnimatorSet();
                        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(mAppNameView, "alpha", 0, 1, 1, 1),
                                ObjectAnimator.ofFloat(mAppNameView, "scaleX", 0.3f, 1.05f, 0.9f, 1),
                                ObjectAnimator.ofFloat(mAppNameView, "scaleY", 0.3f, 1.05f, 0.9f, 1));
                        mAnimatorSet.setDuration(1500);
                        mAnimatorSet.start();
                    }
                });
        mAppVersionView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mAppVersionView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        AnimatorSet mAnimatorSet = new AnimatorSet();
                        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(mAppVersionView, "alpha", 0, 1, 1, 1),
                                ObjectAnimator.ofFloat(mAppVersionView, "scaleX", 0.3f, 1.05f, 0.9f, 1),
                                ObjectAnimator.ofFloat(mAppVersionView, "scaleY", 0.3f, 1.05f, 0.9f, 1));
                        mAnimatorSet.setDuration(1500);
                        mAnimatorSet.start();
                    }
                });
        mAppLogoView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mAppLogoView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        AnimatorSet mAnimatorSet = new AnimatorSet();
                        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(mAppLogoView, "alpha", 0, 1, 1, 1),
                                ObjectAnimator.ofFloat(mAppLogoView, "scaleX", 0.3f, 1.05f, 0.9f, 1),
                                ObjectAnimator.ofFloat(mAppLogoView, "scaleY", 0.3f, 1.05f, 0.9f, 1));
                        mAnimatorSet.setDuration(1500);
                        mAnimatorSet.start();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelayHandler.removeCallbacks(runnable);
    }
}
