package com.udacity.nanodegree.blooddonation.ui.home.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.udacity.nanodegree.blooddonation.data.model.Location;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.data.source.DonationDataSource;
import com.udacity.nanodegree.blooddonation.ui.home.RequestDialogContract;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class RequestDialogPresenter implements RequestDialogContract.Presenter {

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
    }

    @Override
    public void onCreate() {

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
