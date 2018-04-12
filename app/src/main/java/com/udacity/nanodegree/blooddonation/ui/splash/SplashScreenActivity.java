package com.udacity.nanodegree.blooddonation.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.storage.SharedPreferenceManager;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivity;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class SplashScreenActivity extends BaseActivity {
  private Long SPLASH_DELAY = 1000L;
  private Handler mDelayHandler = null;
  private final String HAS_SPLASH_SCREEN_DISPLAYED = "splash_screen";

  private Runnable runnable = new Runnable() {
    @Override public void run() {
      if (!isFinishing()) {
        SharedPreferenceManager.getInstance().put(HAS_SPLASH_SCREEN_DISPLAYED, true);
        Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
        startActivity(intent);
      }
    }
  };

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);

    mDelayHandler = new Handler();

    mDelayHandler.postDelayed(runnable, SPLASH_DELAY);
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    if (SharedPreferenceManager.getInstance().getBoolean(HAS_SPLASH_SCREEN_DISPLAYED)) {
      finish();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    SharedPreferenceManager.getInstance().put(HAS_SPLASH_SCREEN_DISPLAYED, false);
    mDelayHandler.removeCallbacks(runnable);
  }
}
