package com.udacity.nanodegree.blooddonation.data.source.remote;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.udacity.nanodegree.blooddonation.constants.FireBaseConstants;
import com.udacity.nanodegree.blooddonation.data.model.Receiver;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.data.source.DonationDataSource;
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

  @Override public void saveNewUser(String userId, User user) {
    mFirebaseDatabase.getReference().child(FireBaseConstants.USERS).child(userId).setValue(user);
  }

  @Override public void saveReceiverDetails(String userId, Receiver receiver) {
    mFirebaseDatabase.getReference()
        .child(FireBaseConstants.RECEIVER)
        .child(userId)
        .setValue(receiver);
  }

  @Override public void saveDonorDetails(String uid, String bgp, GeoLocation geoLocation,
      RequestDialogPresenter.ISaveDonorDetails iSaveDonorDetails) {
    DatabaseReference databaseReference =
        mFirebaseDatabase.getReference().child(FireBaseConstants.DONOR).child(bgp);
    GeoFire geoFire = new GeoFire(databaseReference);
    geoFire.setLocation(uid, geoLocation, (key, error) -> {
      if (error != null) {
        iSaveDonorDetails.success();
      } else {
        iSaveDonorDetails.fail();
      }
    });
  }
}
