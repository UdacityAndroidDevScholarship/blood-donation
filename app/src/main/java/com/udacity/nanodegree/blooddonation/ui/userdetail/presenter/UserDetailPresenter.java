package com.udacity.nanodegree.blooddonation.ui.userdetail.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udacity.nanodegree.blooddonation.constants.Constants;
import com.udacity.nanodegree.blooddonation.constants.FirebaseConstants;
import com.udacity.nanodegree.blooddonation.constants.SharedPrefConstants;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.data.source.DonationDataSource;
import com.udacity.nanodegree.blooddonation.storage.SharedPreferenceManager;
import com.udacity.nanodegree.blooddonation.ui.userdetail.UserDetailContract;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;
import com.udacity.nanodegree.blooddonation.util.Util;
import timber.log.Timber;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetailPresenter implements UserDetailContract.Presenter {

  private final UserDetailContract.View mView;
  private final FirebaseAuth mFirebaseAuth;
  private final SharedPreferenceManager mSharedPreferenceManager;
  private final DonationDataSource mDataRepo;

  public UserDetailPresenter(UserDetailContract.View view, FirebaseAuth firebaseAuth,
      SharedPreferenceManager sharedPreference, DonationDataSource dataRepo) {
    mFirebaseAuth = firebaseAuth;
    mSharedPreferenceManager = sharedPreference;
    mView = view;
    mDataRepo = dataRepo;
  }

  @Override public void onCreate() {
  }

  @Override public void onStart() {
  }

  @Override public void onStop() {
  }

  @Override public void onDestroy() {

  }

  @Override public void onCreateNowClick(UserDetail userDetail) {
    User user = Util.getPreparedUser(userDetail);
    mDataRepo.writeNewUser(mFirebaseAuth.getCurrentUser().getUid(),
        user);
    mSharedPreferenceManager.put(SharedPrefConstants.IS_USER_DETAILS_ENTERED,true);
    mView.launchHomeScreen();
  }

  @Override public void onDobButtonClick() {
    mView.showDatePickerDialog();
  }

  @Override public void onLocationClick() {
    mView.getLastLocation();
  }
}
