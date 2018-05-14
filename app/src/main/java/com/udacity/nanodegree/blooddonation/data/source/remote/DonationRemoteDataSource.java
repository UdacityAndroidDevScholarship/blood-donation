package com.udacity.nanodegree.blooddonation.data.source.remote;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.udacity.nanodegree.blooddonation.constants.FireBaseConstants;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.data.source.DonationDataSource;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.presenter.RequestDialogPresenter;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class DonationRemoteDataSource implements DonationDataSource {

  public static DonationRemoteDataSource INSTANCE;

  private FirebaseDatabase mFirebaseDatabase;

  private DonationRemoteDataSource(FirebaseDatabase firebaseDatabase) {
    mFirebaseDatabase = firebaseDatabase;
  }

  public static DonationDataSource getInstance(FirebaseDatabase firebaseDatabase) {
    if (INSTANCE == null) {
      synchronized (DonationRemoteDataSource.class) {
        if (INSTANCE == null) {
          INSTANCE = new DonationRemoteDataSource(firebaseDatabase);
        }
      }
    }
    return INSTANCE;
  }

  @Override
  public void saveNewUser(String userId, User user, OnSaveCompletedListener completionListener) {
    mFirebaseDatabase.getReference()
        .child(FireBaseConstants.USERS)
        .child(userId)
        .setValue(user)
        .addOnCompleteListener(
            task -> completionListener.onCompleted(task.isSuccessful()));
  }

  @Override public void saveReceiverDetails(String userId,
      ReceiverDonorRequestType receiverDonorRequestType) {
    mFirebaseDatabase.getReference()
        .child(FireBaseConstants.RECEIVER)
        .child(userId)
        .setValue(receiverDonorRequestType);
  }

  @Override
  public void saveDonorDetails(String userId, ReceiverDonorRequestType receiverDonorRequestType) {

    mFirebaseDatabase.getReference()
        .child(FireBaseConstants.DONOR)
        .child(userId)
        .setValue(receiverDonorRequestType);
  }
}
