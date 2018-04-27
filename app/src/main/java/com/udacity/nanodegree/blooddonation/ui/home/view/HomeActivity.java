package com.udacity.nanodegree.blooddonation.ui.home.view;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.databinding.ActivityHomeBinding;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivityContract;
import com.udacity.nanodegree.blooddonation.ui.home.presenter.HomeActivityPresenter;
import com.udacity.nanodegree.blooddonation.util.permission.AppPermissionsUtil;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/04/2018.
 */
public class HomeActivity extends BaseActivity implements HomeActivityContract.View {

  private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 101;
  private GoogleMap mMap;
  private HomeActivityContract.Presenter mPresenter;
  private LinearLayout mDonorSheet, mReceiver;
  private BottomSheetBehavior<LinearLayout> donorBehavior;
  private BottomSheetBehavior<LinearLayout> receiverBehaviour;
  private FusedLocationProviderClient fusedLocationProviderClient;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

    mPresenter = new HomeActivityPresenter(this);
    mDonorSheet = findViewById(R.id.donor_sheet);
    mReceiver = findViewById(R.id.receiver_sheet);
    ((ActivityHomeBinding) mBinding).setPresenter(mPresenter);

    donorBehavior = BottomSheetBehavior.from(mDonorSheet);
    receiverBehaviour = BottomSheetBehavior.from(mReceiver);

    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    receiverBehaviour.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
        switch (newState) {
          case BottomSheetBehavior.STATE_HIDDEN:
            break;
          case BottomSheetBehavior.STATE_EXPANDED:
            break;
          case BottomSheetBehavior.STATE_COLLAPSED:
            break;
          case BottomSheetBehavior.STATE_DRAGGING:
            break;
          case BottomSheetBehavior.STATE_SETTLING:
            break;
        }
      }

      @Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {

      }
    });
    donorBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
        switch (newState) {
          case BottomSheetBehavior.STATE_HIDDEN:
            break;
          case BottomSheetBehavior.STATE_EXPANDED:
            break;
          case BottomSheetBehavior.STATE_COLLAPSED:
            break;
          case BottomSheetBehavior.STATE_DRAGGING:
            break;
          case BottomSheetBehavior.STATE_SETTLING:
            break;
        }
      }

      @Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {

      }
    });
    receiverBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
    donorBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    SupportMapFragment mapFragment =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragement_maps);
    mapFragment.getMapAsync(this);
  }

  @Override public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    mMap.setOnMarkerClickListener(this);

    tryToGetLocationAndUpdateCamera();

    // Sample Markers.

//    mMap.addMarker(new MarkerOptions().position(new LatLng(28.6315, 77.2167))
//        .title("Donor")
//        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
//
//    mMap.addMarker(new MarkerOptions().position(new LatLng(27.6315, 78.2167))
//        .title("Blood Request")
//        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
  }
  @Override
  public void tryToGetLocationAndUpdateCamera() {
    if (AppPermissionsUtil.checkIfLocationPermissionIsGiven(this)) {
      getLocationAndUpdateCamera();
    } else {
      AppPermissionsUtil.requestForLocationPermission(this, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
    }
  }

  @SuppressLint("MissingPermission")
  private void getLocationAndUpdateCamera() {
    fusedLocationProviderClient.getLastLocation()
            .addOnSuccessListener(new OnSuccessListener<Location>() {
              @Override
              public void onSuccess(Location location) {
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                updateCamera(currentLocation);
                mMap.addMarker(new MarkerOptions().position(currentLocation)
                        .title("Donor")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                );
              }
            });
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == MY_PERMISSIONS_REQUEST_FINE_LOCATION
            && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
          getLocationAndUpdateCamera();
    }else{
      Toast.makeText(this, "Location permission is required.", Toast.LENGTH_SHORT).show();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_map_theme:
        Toast.makeText(this, "Map Theme", Toast.LENGTH_SHORT).show();
        break;
      case R.id.action_about:
        Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
        break;
      case R.id.action_sign_out:
        Toast.makeText(this, "Sign Out", Toast.LENGTH_SHORT).show();
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void updateCamera(@Nullable LatLng position) {
    if (position == null) {
      position = new LatLng(28.6315,
          77.2167); // This is hard coded default location. This will be changed by current location
    }

    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(position, 8);
    mMap.animateCamera(location, 2000, null);
  }

  public void toggleBottomSheet(BottomSheetBehavior<LinearLayout> sheetBehavior,
      BottomSheetBehavior<LinearLayout> sheetBehavior2) {
    if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
      sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    } else {
      sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
    sheetBehavior2.setState(BottomSheetBehavior.STATE_HIDDEN);
  }

  public boolean onMarkerClick(Marker marker) {
    if (marker.getTitle().equals("Donor")) {
      toggleBottomSheet(donorBehavior, receiverBehaviour);
    } else if (marker.getTitle().equals("Blood Request")) {
      toggleBottomSheet(receiverBehaviour, donorBehavior);
    }
    return false;
  }

  @Override public void openCreateRequestDialog() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    RequestDialogFragment requestDialogFragment = new RequestDialogFragment();
    requestDialogFragment.show(fragmentManager, "request_dialog");
  }
}
