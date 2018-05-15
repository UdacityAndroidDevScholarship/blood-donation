package com.udacity.nanodegree.blooddonation.ui.home.presenter;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.constants.FireBaseConstants;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivityContract;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;

import java.util.ArrayList;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/04/2018.
 */
public class HomeActivityPresenter
        implements HomeActivityContract.Presenter {

    private final HomeActivityContract.View mView;

    private FirebaseAuth mFireBaseAuth;
    private FirebaseDatabase mFireBaseDataBase;

    private ValueEventListener mUserDetailsValueEventListener;
    private ValueEventListener mUserRequestDetailsValueEventListener;
    private ValueEventListener mDonorDetailsValueEventListener;

    private DatabaseReference mUserDetailDabaseReference;
    private DatabaseReference mUserRequestDetailsDatabaseRef;
    private DatabaseReference mDonorDetailsDatabaseRef;


    private User mUser;
    private ReceiverDonorRequestType mReceiverDonorRequestType;


    private ArrayMap<String, ReceiverDonorRequestType> mDonors; // Created for future purposes.
    private ArrayMap<String, ReceiverDonorRequestType> mReceivers; // Created for future purposes.

    //private GeoFire geoFire;
    //private GeoQuery geoQuery;

    public HomeActivityPresenter(HomeActivityContract.View view, FirebaseAuth firebaseAuth,
                                 FirebaseDatabase firebaseDatabase) {
        this.mView = view;
        mFireBaseAuth = firebaseAuth;
        mFireBaseDataBase = firebaseDatabase;
    }

    @Override
    public void onCurrentLocationClicked() {

        mView.fetchCurrentLocation();
    }

    @Override
    public void onAddClicked() {
        if (mUser == null)
            mView.generalResponse(R.string.msg_please_wait);
        else
            mView.openCreateRequestDialog(mUser, null);
    }

    @Override
    public void createRequestDialogForCustomLocation(@NonNull LatLng location) {
        if (mUser == null)
            mView.generalResponse(R.string.msg_please_wait);
        else
            mView.openCreateRequestDialog(mUser, location);
    }

    @Override
    public void onCreate() {

        mView.showHideLoader(false);

        mDonors = new ArrayMap<>();
        mReceivers = new ArrayMap<>();

        createUserDetailsValueEventListener();
        createUserRequestDetailsValueEventListener();
        createDonorDetailsValueEventListener();

        mUserDetailDabaseReference = mFireBaseDataBase.getReference()
                .child(FireBaseConstants.USERS)
                .child(mFireBaseAuth.getUid());

        mUserRequestDetailsDatabaseRef = mFireBaseDataBase.getReference()
                .child(FireBaseConstants.RECEIVER);


        mDonorDetailsDatabaseRef = mFireBaseDataBase.getReference()
                .child(FireBaseConstants.DONOR);


        mUserDetailDabaseReference.addListenerForSingleValueEvent(mUserDetailsValueEventListener);
    }

    private void createDonorDetailsValueEventListener() {
        mDonorDetailsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<ReceiverDonorRequestType> donors = new ArrayList<>();
                for (DataSnapshot donor : dataSnapshot.getChildren()) {
                    if (donor.exists()) {
                        ReceiverDonorRequestType request = donor.getValue(ReceiverDonorRequestType.class);
                        if (request != null)
                            donors.add(request);

                    }
                }

                mView.addDonorMarkers(donors);

                mView.showHideLoader(false);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                mView.showHideLoader(false);

            }
        };
    }

    private void createUserDetailsValueEventListener() {
        mUserDetailsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mUser = dataSnapshot.getValue(User.class);
                }
                mUserRequestDetailsDatabaseRef.addValueEventListener(
                        mUserRequestDetailsValueEventListener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mUserRequestDetailsDatabaseRef.addValueEventListener(
                        mUserRequestDetailsValueEventListener);
            }
        };
    }

    private void createUserRequestDetailsValueEventListener() {
        mUserRequestDetailsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<ReceiverDonorRequestType> receivers = new ArrayList<>();

                for (DataSnapshot receiver : dataSnapshot.getChildren()) {
                    if (receiver.exists()) {
                        ReceiverDonorRequestType request = receiver.getValue(ReceiverDonorRequestType.class);
                        if (request != null) {
                            receivers.add(request);
                        }
                    }
                }
                mView.addReceiverMarkers(receivers);

                mDonorDetailsDatabaseRef.addValueEventListener(mDonorDetailsValueEventListener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mDonorDetailsDatabaseRef.addValueEventListener(mDonorDetailsValueEventListener);

            }
        };
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
        mUserDetailDabaseReference.removeEventListener(mUserDetailsValueEventListener);
        mUserRequestDetailsDatabaseRef.removeEventListener(mUserRequestDetailsValueEventListener);
        mDonorDetailsDatabaseRef.removeEventListener(mDonorDetailsValueEventListener);
    }

    @Override
    public void onBloodRequest(RequestDetails requestDetails) {

    }

    @Override
    public void onDonateRequest(RequestDetails requestDetails) {

    }

    // GeoQuery Listeners
  /*@Override public void onKeyEntered(String key, GeoLocation location) {
    mView.putGeoKeyMarker(key, location);
  }

  @Override public void onKeyExited(String key) {
    mView.removeOldGeoMarker(key);
  }

  @Override public void onKeyMoved(String key, GeoLocation location) {
    mView.animateGeoMarker(key, location);
  }

  @Override public void onGeoQueryReady() {
  }

  @Override public void onGeoQueryError(DatabaseError error) {
    mView.showGeoQueryErrorDialogBox(error);
  }*/
}
