package com.udacity.nanodegree.blooddonation.ui.home;

import android.support.annotation.NonNull;

import com.udacity.nanodegree.blooddonation.base.BasePresenter;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;

/**
 * Created by riteshksingh on Apr, 2018
 */
public interface RequestDialogContract {
  interface View {
    void getLastLocation();

    void dismissDialog(@NonNull String userId, boolean isReceiver, ReceiverDonorRequestType receiverDonorRequestType);
  }

  interface Presenter extends BasePresenter {

    void onSubmitButtonClick(RequestDetails requestDetails);

    void onLocationClick();
  }
}
