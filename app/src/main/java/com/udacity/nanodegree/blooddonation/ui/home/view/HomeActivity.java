package com.udacity.nanodegree.blooddonation.ui.home.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.databinding.ActivityHomeBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivityContract;
import com.udacity.nanodegree.blooddonation.ui.home.presenter.HomeActivityPresenter;
import com.udacity.nanodegree.blooddonation.util.location.LocationUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/04/2018.
 */
public class HomeActivity extends BaseActivity
        implements HomeActivityContract.View, RequestDialogFragment.IRequestDialogFragmentListener {

    private static final int INITIAL_ZOOM_LEVEL = 14;

    private GoogleMap mMap;
    private Circle searchCircle;

    private HomeActivityContract.Presenter mPresenter;
    private LinearLayout mDonorSheet, mReceiver;
    private BottomSheetBehavior<LinearLayout> donorBehavior;
    private BottomSheetBehavior<LinearLayout> receiverBehaviour;

    private Marker mRequestMarker;
    private Marker mDonorMarker;

    private Map<String, Marker> markers;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        mPresenter = new HomeActivityPresenter(this, Injection.provideFireBaseAuth(),
                Injection.provideFireBaseDatabase());
        ((ActivityHomeBinding) mBinding).setPresenter(mPresenter);

        mDonorSheet = findViewById(R.id.donor_sheet);
        mReceiver = findViewById(R.id.receiver_sheet);
        donorBehavior = BottomSheetBehavior.from(mDonorSheet);
        receiverBehaviour = BottomSheetBehavior.from(mReceiver);
        setReceiverBehaviorBottomSheetCallBack();
        setDonorBehaviorBottomSheetCallBack();
        receiverBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
        donorBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragement_maps);
        mapFragment.getMapAsync(this);

        showHideLoader(true);

        markers = new HashMap<>();

        mPresenter.onCreate();
    }

    @Override
    public void showHideLoader(boolean isActive) {
        ((ActivityHomeBinding) mBinding).pbLoader.setVisibility(isActive ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
        for (Marker marker : markers.values()) {
            marker.remove();
        }
        markers.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void setDonorBehaviorBottomSheetCallBack() {
        donorBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
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

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void setReceiverBehaviorBottomSheetCallBack() {
        receiverBehaviour.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
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

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map_theme:
                Toast.makeText(this, "Map Theme", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_sign_out:
                logout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setSearchCircle(@NonNull LatLng latLng) {
        searchCircle = mMap.addCircle(new CircleOptions().center(latLng).radius(1000));
        searchCircle.setFillColor(Color.argb(66, 255, 0, 255));
        searchCircle.setStrokeColor(Color.argb(66, 0, 0, 0));
        updateCamera(latLng);
    }

    @Override
    public void updateCamera(@Nullable LatLng latLng) {
        if (latLng == null) {
            return;
        }
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latLng, INITIAL_ZOOM_LEVEL);
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

    @Override
    public void openCreateRequestDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RequestDialogFragment requestDialogFragment = new RequestDialogFragment();
        requestDialogFragment.show(fragmentManager, "request_dialog");
    }

    @Override
    public void onRequestDialogDismissed(boolean isReceiver,
                                         ReceiverDonorRequestType receiverDonorRequestType) {
        if (isReceiver) {
            addRequestMarker(receiverDonorRequestType);
            //mPresenter.onBloodRequest(requestDetails);
            return;
        }
        //mPresenter.onDonateRequest(requestDetails);
        addDonorMarker(receiverDonorRequestType);
    }

    private void removeMarkers() {
        if (mRequestMarker != null) {
            mRequestMarker.remove();
        }

        if (mDonorMarker != null) {
            mDonorMarker.remove();
        }
    }

    @Override
    public void addRequestMarker(ReceiverDonorRequestType receiverDonorRequestType) {
        removeMarkers();
        LatLng latLng = new LatLng(receiverDonorRequestType.getLocation().getLatitude(),
                receiverDonorRequestType.getLocation().getLongitude());
        mRequestMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                .title("Request")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        updateCamera(latLng);

        mPresenter.queryGeoFire(latLng, receiverDonorRequestType.getbGp());
    }

    @Override
    public void addDonorMarker(ReceiverDonorRequestType receiverDonorRequestType) {
        removeMarkers();
        LatLng latLng = new LatLng(receiverDonorRequestType.getLocation().getLatitude(),
                receiverDonorRequestType.getLocation().getLongitude());
        mDonorMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                .title("Donor")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        updateCamera(latLng);
    }

    // Google Map callbacks
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTitle() == null) {
            return false;
        }
        if (marker.getTitle().equals("Donor")) {
            toggleBottomSheet(donorBehavior, receiverBehaviour);
        } else if (marker.getTitle().equals("Blood Request")) {
            toggleBottomSheet(receiverBehaviour, donorBehavior);
        }
        return false;
    }

    @Override
    public void putGeoKeyMarker(String key, GeoLocation location) {
        Marker marker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(location.latitude, location.longitude))
                        .title("Donor")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        markers.put(key, marker);
    }

    @Override
    public void removeOldGeoMarker(String key) {
        Marker marker = this.markers.get(key);
        if (marker != null) {
            marker.remove();
            this.markers.remove(key);
        }
    }

    private void animateMarkerTo(final Marker marker, final double lat, final double lng) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long DURATION_MS = 3000;
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final LatLng startPosition = marker.getPosition();
        handler.post(new Runnable() {
            @Override
            public void run() {
                float elapsed = SystemClock.uptimeMillis() - start;
                float t = elapsed / DURATION_MS;
                float v = interpolator.getInterpolation(t);

                double currentLat = (lat - startPosition.latitude) * v + startPosition.latitude;
                double currentLng = (lng - startPosition.longitude) * v + startPosition.longitude;
                marker.setPosition(new LatLng(currentLat, currentLng));

                // if animation is not finished yet, repeat
                if (t < 1) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    @Override
    public void animateGeoMarker(String key, GeoLocation location) {
        Marker marker = this.markers.get(key);
        if (marker != null) {
            this.animateMarkerTo(marker, location.latitude, location.longitude);
        }
    }

    @Override
    public void showGeoQueryErrorDialogBox(DatabaseError error) {
        new AlertDialog.Builder(this).setTitle("Error")
                .setMessage("There was an unexpected error querying GeoFire: " + error.getMessage())
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }




}
