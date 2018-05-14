package com.udacity.nanodegree.blooddonation.ui.userdetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
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

    void onPickPhotoClick();

    void handleActivityResult(Context context, int requestCode, int resultCode, Intent data);

    void handlePermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults);
  }

  interface View extends BaseView {
    void showDatePickerDialog();

    void startCityPickerActivity();

    void setUserDetailCityAndState(String city, String state);

    void clearAllTextInputErrors();

    void showTextInputError(@IdRes int resId, @StringRes int message);

    void setCreateAccountProgressVisibility(boolean isVisible);

    void launchHomeScreen();

    void checkPermissions();

    void setPermissionsGranted();

    void showPermissionGrantFromSettings();

    void setPickImageProgressVisibility(boolean isVisible);

    void setUserProfileImage(Bitmap profileImage);

    void startImageCropperActivity();
  }
}
