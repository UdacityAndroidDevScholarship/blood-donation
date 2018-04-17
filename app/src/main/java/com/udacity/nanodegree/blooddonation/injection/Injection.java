package com.udacity.nanodegree.blooddonation.injection;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.udacity.nanodegree.blooddonation.data.source.DonationDataRepository;
import com.udacity.nanodegree.blooddonation.data.source.DonationDataSource;
import com.udacity.nanodegree.blooddonation.data.source.local.DonationLocalDataSource;
import com.udacity.nanodegree.blooddonation.data.source.remote.DonationRemoteDataSource;
import com.udacity.nanodegree.blooddonation.storage.SharedPreferenceManager;

/**
 * Created by riteshksingh on Apr, 2018
 */
final public class Injection {
  private Injection() {
  }

  public static FirebaseAuth getFirebaseAuth() {
    return FirebaseAuth.getInstance();
  }

  public static FirebaseDatabase getFirebaseDatabase() {
    return FirebaseDatabase.getInstance();
  }

  public static SharedPreferenceManager getSharedPreference() {
    return SharedPreferenceManager.getInstance();
  }

  public static DonationDataSource provideLocalDataSource() {
    return DonationLocalDataSource.getInstance();
  }

  public static DonationDataSource provideRemoteDataSource() {
    return DonationRemoteDataSource.getInstance(Injection.getFirebaseDatabase());
  }

  public static DonationDataSource providesDataRepo() {
    return DonationDataRepository.getInstance(Injection.provideLocalDataSource(),
        Injection.provideRemoteDataSource());
  }
}
