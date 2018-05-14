package com.udacity.nanodegree.blooddonation.ui.home.presenter;

<<<<<<< HEAD
import android.content.Intent;

import com.firebase.geofire.GeoLocation;
||||||| merged common ancestors
import com.firebase.geofire.GeoLocation;
=======
>>>>>>> upstream/develop
import com.google.firebase.auth.FirebaseAuth;
import com.udacity.nanodegree.blooddonation.data.model.Location;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.data.source.DonationDataSource;
import com.udacity.nanodegree.blooddonation.databinding.ActivityHomeBinding;
import com.udacity.nanodegree.blooddonation.ui.home.RequestDialogContract;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;
import com.udacity.nanodegree.blooddonation.ui.home.view.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.home.view.RequestDialogFragment;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class RequestDialogPresenter implements RequestDialogContract.Presenter {

<<<<<<< HEAD
  private RequestDialogContract.View mView;
  private final FirebaseAuth mFirebaseAuth;
  private final DonationDataSource mDataRepo;
  private ReceiverDonorRequestType receiverDonorRequestType;

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

  private void prepareReceiverDonorRequestType(RequestDetails requestDetails) {
    Location location = new Location(requestDetails.latitude.get(), requestDetails.longitude.get());
    receiverDonorRequestType = new ReceiverDonorRequestType();
    receiverDonorRequestType.setLocation(location);
    receiverDonorRequestType.setPurpose(requestDetails.purpose.get());
    receiverDonorRequestType.setbGp(requestDetails.bloodGroup.get());
  }

  private void saveReceiverDetailsInDb(RequestDetails requestDetails) {
    prepareReceiverDonorRequestType(requestDetails);
    mDataRepo.saveReceiverDetails(mFirebaseAuth.getCurrentUser().getUid(),
        receiverDonorRequestType);
    mView.dismissDialog(true,receiverDonorRequestType);
  }

  private void saveDonorDetails(RequestDetails requestDetails) {
    prepareReceiverDonorRequestType(requestDetails);
    mDataRepo.saveDonorDetails(mFirebaseAuth.getCurrentUser().getUid(),
        requestDetails.bloodGroup.get(),
        new GeoLocation(requestDetails.latitude.get(), requestDetails.longitude.get()),
        new ISaveDonorDetails() {
          @Override public void success() {
            mView.dismissDialog(false,receiverDonorRequestType);
          }

          @Override public void fail() {
            mView.dismissDialog(false, receiverDonorRequestType);
          }
        });
  }



  @Override public void onSubmitButtonClick(RequestDetails requestDetails) {
    // Request Type is receiver
    if (requestDetails.requestType.get().trim().equalsIgnoreCase("0")) {
      saveReceiverDetailsInDb(requestDetails);
      return;
||||||| merged common ancestors
  private RequestDialogContract.View mView;
  private final FirebaseAuth mFirebaseAuth;
  private final DonationDataSource mDataRepo;
  private ReceiverDonorRequestType receiverDonorRequestType;

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

  private void prepareReceiverDonorRequestType(RequestDetails requestDetails) {
    Location location = new Location(requestDetails.latitude.get(), requestDetails.longitude.get());
    receiverDonorRequestType = new ReceiverDonorRequestType();
    receiverDonorRequestType.setLocation(location);
    receiverDonorRequestType.setPurpose(requestDetails.purpose.get());
    receiverDonorRequestType.setbGp(requestDetails.bloodGroup.get());
  }

  private void saveReceiverDetailsInDb(RequestDetails requestDetails) {
    prepareReceiverDonorRequestType(requestDetails);
    mDataRepo.saveReceiverDetails(mFirebaseAuth.getCurrentUser().getUid(),
        receiverDonorRequestType);
    mView.dismissDialog(true,receiverDonorRequestType);
  }

  private void saveDonorDetails(RequestDetails requestDetails) {
    prepareReceiverDonorRequestType(requestDetails);
    mDataRepo.saveDonorDetails(mFirebaseAuth.getCurrentUser().getUid(),
        requestDetails.bloodGroup.get(),
        new GeoLocation(requestDetails.latitude.get(), requestDetails.longitude.get()),
        new ISaveDonorDetails() {
          @Override public void success() {
            mView.dismissDialog(false,receiverDonorRequestType);
          }

          @Override public void fail() {
            mView.dismissDialog(false, receiverDonorRequestType);
          }
        });
  }

  @Override public void onSubmitButtonClick(RequestDetails requestDetails) {
    // Request Type is receiver
    if (requestDetails.requestType.get().trim().equalsIgnoreCase("0")) {
      saveReceiverDetailsInDb(requestDetails);
      return;
=======
    private final FirebaseAuth mFirebaseAuth;
    private final DonationDataSource mDataRepo;
    private User mUser;
    private RequestDialogContract.View mView;
    private ReceiverDonorRequestType receiverDonorRequestType;

    public RequestDialogPresenter(RequestDialogContract.View view, FirebaseAuth firebaseAuth,
                                  DonationDataSource dataRepo, User user) {
        this.mView = view;
        this.mFirebaseAuth = firebaseAuth;
        this.mDataRepo = dataRepo;
        mUser = user;
>>>>>>> upstream/develop
    }

<<<<<<< HEAD

    // Request Type is donor
    saveDonorDetails(requestDetails);
  }
||||||| merged common ancestors
    // Request Type is donor
    saveDonorDetails(requestDetails);
  }
=======
    @Override
    public void onCreate() {
>>>>>>> upstream/develop

<<<<<<< HEAD
   //Click cancel button will take you to HomeActivity
  @Override public void onCancelButtonClick() {

    mView.dismissDialog();

  }

  @Override public void onLocationClick() {
    mView.getLastLocation();
  }
||||||| merged common ancestors
  @Override public void onLocationClick() {
    mView.getLastLocation();
  }
=======
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    private void prepareReceiverDonorRequestType(RequestDetails requestDetails) {
>>>>>>> upstream/develop

        receiverDonorRequestType = new ReceiverDonorRequestType(new Location(requestDetails.latitude.get(), requestDetails.longitude.get()),
                requestDetails.bloodGroup.get(),
                requestDetails.purpose.get(),
                mUser.fName,
                mUser.lName,
                mFirebaseAuth.getCurrentUser().getPhoneNumber());
    }

    private void saveReceiverDetailsInDb(RequestDetails requestDetails) {
        prepareReceiverDonorRequestType(requestDetails);
        mDataRepo.saveReceiverDetails(mFirebaseAuth.getCurrentUser().getUid(),
                receiverDonorRequestType);
        mView.dismissDialog(mFirebaseAuth.getUid(), true, receiverDonorRequestType);
    }

    private void saveDonorDetails(RequestDetails requestDetails) {

        requestDetails.bloodGroup.set(mUser.bloodGroup);
        prepareReceiverDonorRequestType(requestDetails);
        ;
        mDataRepo.saveDonorDetails(mFirebaseAuth.getCurrentUser().getUid(), receiverDonorRequestType);
        mView.dismissDialog(mFirebaseAuth.getUid(), false, receiverDonorRequestType);
    }

    @Override
    public void onSubmitButtonClick(RequestDetails requestDetails) {
        // Request Type is receiver
        if (requestDetails.requestType.get().trim().equalsIgnoreCase("0"))
            saveReceiverDetailsInDb(requestDetails);
        else
            // Request Type is donor
            saveDonorDetails(requestDetails);
    }

    @Override
    public void onLocationClick() {
        mView.getLastLocation();
    }

}
