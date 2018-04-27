package com.udacity.nanodegree.blooddonation.ui.home.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udacity.nanodegree.blooddonation.constants.FireBaseConstants;
import com.udacity.nanodegree.blooddonation.data.model.Receiver;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivityContract;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/04/2018.
 */
public class HomeActivityPresenter implements HomeActivityContract.Presenter {

  private final HomeActivityContract.View mView;
  private FirebaseAuth mAuth;
  private FirebaseDatabase mFirebaseDatabase;

  public HomeActivityPresenter(HomeActivityContract.View view, FirebaseAuth firebaseAuth,
      FirebaseDatabase firebaseDatabase) {
    this.mView = view;
    this.mAuth = firebaseAuth;
    this.mFirebaseDatabase = firebaseDatabase;
  }

  @Override public void onCurrentLocationClicked() {
    mView.updateCamera(null);
  }

  @Override public void onAddClicked() {
    mView.openCreateRequestDialog();
  }

  @Override public void onCreate() {

  }

  @Override public void onStart() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {

  }

  @Override public void onBloodRequest() {
    DatabaseReference databaseReference =
        mFirebaseDatabase.getReference().child(FireBaseConstants.RECEIVER).child(mAuth.getUid());

    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        Receiver receiver = dataSnapshot.getValue(Receiver.class);
        mView.addRequestMarker(receiver);
      }

      @Override public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  @Override public void onDonateRequest() {

  }
}
