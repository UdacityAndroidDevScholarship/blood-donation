package com.udacity.nanodegree.blooddonation.ui.splash;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivity;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class SplashScreenActivity extends BaseActivity {
    private Long SPLASH_DELAY = 3000L;
    private Handler mDelayHandler = null;
    LinearLayout mlayoutMain;
    ImageView mImgViewLogo;
    TextView mTextViewAppName;


    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()) {
                Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        StartAnimations();

        mDelayHandler = new Handler();

        mDelayHandler.postDelayed(runnable, SPLASH_DELAY);
    }


    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        mlayoutMain = (LinearLayout) findViewById(R.id.lin_lay);
        mlayoutMain.clearAnimation();
        mlayoutMain.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.trans_anim);
        anim.reset();
        mImgViewLogo = (ImageView) findViewById(R.id.image_view_logo);
        mImgViewLogo.clearAnimation();
        mImgViewLogo.startAnimation(anim);


        anim = AnimationUtils.loadAnimation(this, R.anim.trans_anim);
        anim.reset();
        mTextViewAppName = (TextView) findViewById(R.id.text_view_app_name);
        mTextViewAppName.clearAnimation();
        mTextViewAppName.startAnimation(anim);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelayHandler.removeCallbacks(runnable);
    }
}
