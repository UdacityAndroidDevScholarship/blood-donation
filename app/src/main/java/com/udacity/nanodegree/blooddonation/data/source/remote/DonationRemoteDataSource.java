package com.udacity.nanodegree.blooddonation.data.source.remote;

import com.google.firebase.database.FirebaseDatabase;
import com.udacity.nanodegree.blooddonation.constants.FirebaseConstants;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.data.source.DonationDataSource;

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

  @Override public void writeNewUser(String userId, User user) {
    mFirebaseDatabase.getReference().child(FirebaseConstants.USERS).child(userId).setValue(user);
  }
}
