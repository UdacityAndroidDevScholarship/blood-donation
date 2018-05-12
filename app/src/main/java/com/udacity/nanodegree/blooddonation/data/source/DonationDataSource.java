package com.udacity.nanodegree.blooddonation.data.source;

import com.firebase.geofire.GeoLocation;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.ui.home.presenter.RequestDialogPresenter;

/**
 * Created by riteshksingh on Apr, 2018
 */
public interface DonationDataSource {
  void saveNewUser(String userId, User user);

  void saveReceiverDetails(String userId, ReceiverDonorRequestType receiverDonorRequestType);

  void saveDonorDetails(String userId, ReceiverDonorRequestType receiverDonorRequestType);
}
