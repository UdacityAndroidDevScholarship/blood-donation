package com.udacity.nanodegree.blooddonation.ui.home.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udacity.nanodegree.blooddonation.constants.FireBaseConstants;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivityContract;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/04/2018.
 */
public class HomeActivityPresenter implements HomeActivityContract.Presenter {

  private final HomeActivityContract.View mView;

  private FirebaseAuth mFireBaseAuth;
  private FirebaseDatabase mFireBaseDataBase;

  private ValueEventListener mUserDetailsValueEventListener;
  private ValueEventListener mUserRequestDetailsValueEventListener;

  private DatabaseReference mUserDetailDabaseReference;
  private DatabaseReference mUserRequestDetailsDatabaseRef;

  private User mUser;
  private ReceiverDonorRequestType mReceiverDonorRequestType;

  public HomeActivityPresenter(HomeActivityContract.View view, FirebaseAuth firebaseAuth,
      FirebaseDatabase firebaseDatabase) {
    this.mView = view;
    mFireBaseAuth = firebaseAuth;
    mFireBaseDataBase = firebaseDatabase;
  }

  @Override public void onCurrentLocationClicked() {
    mView.updateCamera(null);
  }

  @Override public void onAddClicked() {
    mView.openCreateRequestDialog();
  }

  @Override public void onCreate() {
    createUserDetailsValueEventListener();
    createUserRequestDetailsValueEventListener();
    mUserDetailDabaseReference = mFireBaseDataBase.getReference()
        .child(FireBaseConstants.USERS)
        .child(mFireBaseAuth.getUid());

    mUserRequestDetailsDatabaseRef = mFireBaseDataBase.getReference()
        .child(FireBaseConstants.RECEIVER)
        .child(mFireBaseAuth.getUid());

    mUserDetailDabaseReference.addListenerForSingleValueEvent(mUserDetailsValueEventListener);
  }

  private void createUserDetailsValueEventListener() {
    mUserDetailsValueEventListener = new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
          mUser = dataSnapshot.getValue(User.class);
        }
        mUserRequestDetailsDatabaseRef.addListenerForSingleValueEvent(
            mUserRequestDetailsValueEventListener);
      }

      @Override public void onCancelled(DatabaseError databaseError) {

      }
    };
  }

  private void createUserRequestDetailsValueEventListener() {
    mUserRequestDetailsValueEventListener = new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
          mReceiverDonorRequestType = dataSnapshot.getValue(ReceiverDonorRequestType.class);
        }
        mView.showHideLoader(false);
        if (mReceiverDonorRequestType != null) {
          mView.updateCamera(new LatLng(mReceiverDonorRequestType.getLocation().getLatitude(),
              mReceiverDonorRequestType.getLocation().getLongitude()));
          mView.addRequestMarker(mReceiverDonorRequestType);
        } else {
          mView.updateCamera(new LatLng(mUser.latitude, mUser.longitude));
        }
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        mView.showHideLoader(false);
        if (mReceiverDonorRequestType != null) {
          mView.updateCamera(new LatLng(mReceiverDonorRequestType.getLocation().getLatitude(),
              mReceiverDonorRequestType.getLocation().getLongitude()));
          mView.addRequestMarker(mReceiverDonorRequestType);
        } else {
          mView.updateCamera(new LatLng(mUser.latitude, mUser.longitude));
        }
      }
    };
  }

  @Override public void onStart() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {
    mUserDetailDabaseReference.removeEventListener(mUserDetailsValueEventListener);
    mUserRequestDetailsDatabaseRef.removeEventListener(mUserRequestDetailsValueEventListener);
  }

  @Override public void onBloodRequest(RequestDetails requestDetails) {

  }

  @Override public void onDonateRequest(RequestDetails requestDetails) {

  }
}
