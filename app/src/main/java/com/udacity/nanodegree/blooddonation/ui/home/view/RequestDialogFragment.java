package com.udacity.nanodegree.blooddonation.ui.home.view;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.location.Location;
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
import android.widget.ArrayAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.databinding.FragmentBloodRequestBinding;
import com.udacity.nanodegree.blooddonation.ui.home.RequestDialogContract;
import com.udacity.nanodegree.blooddonation.ui.home.presenter.RequestDialogPresenter;
import com.udacity.nanodegree.blooddonation.util.permission.AppPermissionsUtil;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class RequestDialogFragment extends DialogFragment implements RequestDialogContract.View {

  private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 101;
  private FusedLocationProviderClient mFusedLocationClient;

  public RequestDialogFragment() {
  }

  private FragmentBloodRequestBinding mFragmentBloodRequestBinding;

  private RequestDialogContract.Presenter mPresenter;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    setCancelable(false);
    mPresenter = new RequestDialogPresenter(this);
    mFragmentBloodRequestBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_blood_request, container, false);
    mFragmentBloodRequestBinding.setPresenter(mPresenter);
    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    return mFragmentBloodRequestBinding.getRoot();
  }

  @SuppressLint("MissingPermission") private void getLocation() {
    mFusedLocationClient.getLastLocation()
        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
          @Override public void onSuccess(Location location) {
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
              //mUserDetail.latitiude.set(location.getLatitude());
              //mUserDetail.longitude.set(location.getLongitude());
            }
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

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initRequestDropDownSpinner();
    initBloodRequestSpinner();
  }

  private void initRequestDropDownSpinner() {
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(getActivity(), R.array.blood_request,
            android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mFragmentBloodRequestBinding.requestDropDown.setAdapter(adapter);
  }

  private void initBloodRequestSpinner() {
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(getActivity(), R.array.blood_group,
            android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mFragmentBloodRequestBinding.bloodGroupDropDown.setAdapter(adapter);
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
}
