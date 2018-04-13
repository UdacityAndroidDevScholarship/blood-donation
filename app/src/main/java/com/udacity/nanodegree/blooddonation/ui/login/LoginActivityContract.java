package com.udacity.nanodegree.blooddonation.ui.login;

import com.udacity.nanodegree.blooddonation.base.BasePresenter;

/**
 * Created by riteshksingh on Apr, 2018
 */
public interface LoginActivityContract {
    interface Presenter extends BasePresenter {
        void onLoginClick(LoginInfo loginInfo);
    }

    interface View {
        void loginSuccess();

        void loginFailed();
    }
}
