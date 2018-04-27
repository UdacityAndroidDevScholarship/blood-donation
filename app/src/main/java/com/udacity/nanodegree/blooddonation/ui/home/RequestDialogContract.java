package com.udacity.nanodegree.blooddonation.ui.home;

import com.udacity.nanodegree.blooddonation.base.BasePresenter;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;

/**
 * Created by riteshksingh on Apr, 2018
 */
public interface RequestDialogContract {
  interface View {
    void getLastLocation();
  }

  interface Presenter extends BasePresenter {

    void onSubmitButtonClick(RequestDetails requestDetails);

    void onLocationClick();
  }
}
