package com.udacity.nanodegree.blooddonation.ui.home;

<<<<<<< HEAD
import android.content.Intent;

||||||| merged common ancestors
=======
import android.support.annotation.NonNull;

>>>>>>> upstream/develop
import com.udacity.nanodegree.blooddonation.base.BasePresenter;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;
import com.udacity.nanodegree.blooddonation.ui.home.view.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.home.view.RequestDialogFragment;

/**
 * Created by riteshksingh on Apr, 2018
 */
public interface RequestDialogContract {
  interface View {
    void getLastLocation();

<<<<<<< HEAD
    void dismissDialog(boolean isReceiver, ReceiverDonorRequestType receiverDonorRequestType);

    void dismissDialog();
||||||| merged common ancestors
    void dismissDialog(boolean isReceiver, ReceiverDonorRequestType receiverDonorRequestType);
=======
    void dismissDialog(@NonNull String userId, boolean isReceiver, ReceiverDonorRequestType receiverDonorRequestType);
>>>>>>> upstream/develop
  }

  interface Presenter extends BasePresenter {

    void onSubmitButtonClick(RequestDetails requestDetails);

    void onCancelButtonClick();

    void onLocationClick();
  }
}
