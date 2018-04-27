package com.udacity.nanodegree.blooddonation.ui.home.view;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.databinding.FragmentBloodRequestBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.RequestDialogContract;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;
import com.udacity.nanodegree.blooddonation.ui.home.presenter.RequestDialogPresenter;
import com.udacity.nanodegree.blooddonation.util.permission.AppPermissionsUtil;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class RequestDialogFragment extends DialogFragment implements RequestDialogContract.View {

  private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 101;
  private FusedLocationProviderClient mFusedLocationClient;

  private RequestDetails mRequestDetails;

  public RequestDialogFragment() {
  }

  private FragmentBloodRequestBinding mFragmentBloodRequestBinding;

  private RequestDialogContract.Presenter mPresenter;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    setCancelable(false);
    mRequestDetails = new RequestDetails();
    mPresenter = new RequestDialogPresenter(this, Injection.provideFireBaseAuth(),
        Injection.providesDataRepo());
    mFragmentBloodRequestBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_blood_request, container, false);
    mFragmentBloodRequestBinding.setPresenter(mPresenter);
    mFragmentBloodRequestBinding.setRequestDetails(mRequestDetails);
    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    return mFragmentBloodRequestBinding.getRoot();
  }

  @SuppressLint("MissingPermission") private void getLocation() {
    mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
      if (location != null) {
        mRequestDetails.latitude.set(location.getLatitude());
        mRequestDetails.longitude.set(location.getLongitude());
      }
    });
  }

  @Override public void getLastLocation() {
    if (AppPermissionsUtil.checkIfLocationPermissionIsGiven((HomeActivity) getActivity())) {
      getLocation();
    } else {
      AppPermissionsUtil.requestForLocationPermission((HomeActivity) getActivity(),
          MY_PERMISSIONS_REQUEST_FINE_LOCATION);
    }
  }

  @Override public void dismissDialog(boolean isReceiver) {
    IRequestDialogFragmentListener listener = (IRequestDialogFragmentListener) getActivity();
    listener.onRequestDialogDismissed(isReceiver);
    dismiss();
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case MY_PERMISSIONS_REQUEST_FINE_LOCATION:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          getLocation();
        }
        break;
      default:
        break;
    }
  }

  @Override public void onResume() {
    Window window = getDialog().getWindow();
    Point size = new Point();
    Display display = window.getWindowManager().getDefaultDisplay();
    display.getSize(size);
    window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
    window.setGravity(Gravity.CENTER);
    super.onResume();
  }

  @Override public void onDestroyView() {
    if (mFragmentBloodRequestBinding != null) {
      mFragmentBloodRequestBinding = null;
    }
    super.onDestroyView();
  }

  public interface IRequestDialogFragmentListener{

    void onRequestDialogDismissed(boolean isReceiver);
  }
}
