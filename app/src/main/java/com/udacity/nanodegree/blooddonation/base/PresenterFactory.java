package com.udacity.nanodegree.blooddonation.base;

import com.udacity.nanodegree.blooddonation.ui.home.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.home.HomePresenter;
import com.udacity.nanodegree.blooddonation.ui.login.LoginActivity;
import com.udacity.nanodegree.blooddonation.ui.login.LoginPresenter;
import com.udacity.nanodegree.blooddonation.ui.splash.SplashActivity;
import com.udacity.nanodegree.blooddonation.ui.splash.SplashPresenter;

public class PresenterFactory {
    public static <T extends BaseFragment, S extends BaseMvpPresenter> S getPresenter(T claxx) {


        throw new IllegalStateException("Fragment presenter not supported yet");
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseActivity, S extends BaseMvpPresenter> S getPresenter(T claxx) {
        S presenter = null;
        if (claxx instanceof HomeActivity) {
            presenter = (S) new HomePresenter();
        } else if (claxx instanceof LoginActivity) {
            presenter = (S) new LoginPresenter();
        } else if (claxx instanceof SplashActivity) {
            presenter = (S) new SplashPresenter();
        } else {
            throw new IllegalStateException("Activity presenter not supported yet");
        }
        return presenter;
    }
}
