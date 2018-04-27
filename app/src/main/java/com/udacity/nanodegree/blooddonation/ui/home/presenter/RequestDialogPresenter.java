package com.udacity.nanodegree.blooddonation.ui.home.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.udacity.nanodegree.blooddonation.data.model.Location;
import com.udacity.nanodegree.blooddonation.data.model.Receiver;
import com.udacity.nanodegree.blooddonation.data.source.DonationDataSource;
import com.udacity.nanodegree.blooddonation.ui.home.RequestDialogContract;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class RequestDialogPresenter implements RequestDialogContract.Presenter {

  private RequestDialogContract.View mView;
  private final FirebaseAuth mFirebaseAuth;
  private final DonationDataSource mDataRepo;

  public RequestDialogPresenter(RequestDialogContract.View view, FirebaseAuth firebaseAuth,
      DonationDataSource dataRepo) {
    this.mView = view;
    this.mFirebaseAuth = firebaseAuth;
    this.mDataRepo = dataRepo;
  }

  @Override public void onCreate() {

  }

  @Override public void onStart() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {

  }

  @Override public void onSubmitButtonClick(RequestDetails requestDetails) {
    Location location = new Location(requestDetails.latitude.get(), requestDetails.longitude.get());
    Receiver receiver = new Receiver();
    receiver.setLocation(location);
    receiver.setPurpose(requestDetails.purpose.get());
    receiver.setbGp(requestDetails.bloodGroup.get());

    mDataRepo.writeReceiverDetails(mFirebaseAuth.getCurrentUser().getUid(),
        receiver);
  }

  @Override public void onLocationClick() {
    mView.getLastLocation();
  }
}
