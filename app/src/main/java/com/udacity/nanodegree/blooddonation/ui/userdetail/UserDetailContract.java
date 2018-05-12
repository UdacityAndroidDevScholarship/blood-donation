package com.udacity.nanodegree.blooddonation.ui.userdetail;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import com.udacity.nanodegree.blooddonation.base.BasePresenter;
import com.udacity.nanodegree.blooddonation.base.BaseView;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;

/**
 * Created by riteshksingh on Apr, 2018
 */
public interface UserDetailContract {
  interface Presenter extends BasePresenter {
    void onCreateNowClick(UserDetail userDetail);

    void onSelectBirthdayClick();

    void onSelectCityClick();

    void handleActivityResult(Context context, int requestCode, int resultCode, Intent data);
  }

  interface View extends BaseView {
    void showDatePickerDialog();

    void startCityPickerActivity();

    void setUserDetailCityAndState(String city, String state);

    void clearAllTextInputErrors();

    void showTextInputError(@IdRes int resId, @StringRes int message);

    void setCreateAccountProgressVisibility(boolean isVisible);

    void launchHomeScreen();
  }
}
