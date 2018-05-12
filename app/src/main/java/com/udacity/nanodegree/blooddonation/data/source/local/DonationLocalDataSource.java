package com.udacity.nanodegree.blooddonation.data.source.local;

import com.firebase.geofire.GeoLocation;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.data.source.DonationDataSource;
import com.udacity.nanodegree.blooddonation.ui.home.presenter.RequestDialogPresenter;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class DonationLocalDataSource implements DonationDataSource {

  private static DonationLocalDataSource INSTANCE;

  private DonationLocalDataSource() {
  }

  public static DonationDataSource getInstance() {
    if (INSTANCE == null) {
      synchronized (DonationLocalDataSource.class) {
        if (INSTANCE == null) {
          INSTANCE = new DonationLocalDataSource();
        }
      }
    }
    return INSTANCE;
  }

  @Override public void saveNewUser(String userId, User user,
      OnSaveCompletedListener completionListener) {
    throw new UnsupportedOperationException("Not supported");
  }

  @Override public void saveReceiverDetails(String userId,
      ReceiverDonorRequestType receiverDonorRequestType) {

  }

  @Override public void saveDonorDetails(String uid, String s, GeoLocation geoLocation,
      RequestDialogPresenter.ISaveDonorDetails iSaveDonorDetails) {
  }
}
