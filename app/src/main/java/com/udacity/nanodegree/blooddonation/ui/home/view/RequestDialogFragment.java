package com.udacity.nanodegree.blooddonation.ui.home.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.model.LatLng;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.databinding.FragmentBloodRequestBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.RequestDialogContract;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;
import com.udacity.nanodegree.blooddonation.ui.home.presenter.RequestDialogPresenter;
import com.udacity.nanodegree.blooddonation.util.location.LocationUtil;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class RequestDialogFragment extends DialogFragment implements RequestDialogContract.View, LocationUtil.LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 101;
    private static User sUser;
    private static LatLng sLocation;
    private RequestDetails mRequestDetails;
    private LocationUtil mLocationUtil;
    private FragmentBloodRequestBinding mFragmentBloodRequestBinding;
    private RequestDialogContract.Presenter mPresenter;

    public RequestDialogFragment() {
    }

    public static RequestDialogFragment getInstance(User user, @Nullable LatLng location) {

        sUser = user;
        sLocation = location;
        return new RequestDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        setCancelable(true);
        mRequestDetails = new RequestDetails();
        mPresenter = new RequestDialogPresenter(this, Injection.provideFireBaseAuth(),
                Injection.providesDataRepo(), sUser);
        mFragmentBloodRequestBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_blood_request, container, false);
        mFragmentBloodRequestBinding.setPresenter(mPresenter);
        mFragmentBloodRequestBinding.setRequestDetails(mRequestDetails);
        return mFragmentBloodRequestBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLocationUtil = new LocationUtil((AppCompatActivity) getActivity(), Injection.getSharedPreference());
        if (sLocation == null) {
            mFragmentBloodRequestBinding.tvLocationPicker.setOnClickListener(v -> mPresenter.onLocationClick());
            mLocationUtil.fetchPreciseLocation(this);
        } else
            mLocationUtil.fetchPlaceName(this, sLocation);


    }

    @Override
    public void getLastLocation() {
        mLocationUtil.fetchPreciseLocation(this);
    }

    @Override
    public void dismissDialog(@NonNull String uid, boolean isReceiver, ReceiverDonorRequestType receiverDonorRequestType) {
        IRequestDialogFragmentListener listener = (IRequestDialogFragmentListener) getActivity();
        listener.onRequestDialogDismissed(uid, isReceiver, receiverDonorRequestType);
        dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        mLocationUtil.onResolutionResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        mLocationUtil.onPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        if (mFragmentBloodRequestBinding != null) {
            mFragmentBloodRequestBinding = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onLocationReceived(@NonNull LatLng location, @NonNull String addressString) {

        mRequestDetails.latitude.set(location.latitude);
        mRequestDetails.longitude.set(location.longitude);

        mFragmentBloodRequestBinding.tvLocationPicker.setText(addressString);

    }

    public interface IRequestDialogFragmentListener {
        void onRequestDialogDismissed(@NonNull String uid, boolean isReceiver, ReceiverDonorRequestType receiverDonorRequestType);
    }
}
