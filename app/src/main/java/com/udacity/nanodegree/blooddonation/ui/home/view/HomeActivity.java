package com.udacity.nanodegree.blooddonation.ui.home.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.databinding.ActivityHomeBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.about.AboutActivity;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivityContract;
import com.udacity.nanodegree.blooddonation.ui.home.presenter.HomeActivityPresenter;
import com.udacity.nanodegree.blooddonation.ui.myprofile.MyProfileActivity;
import com.udacity.nanodegree.blooddonation.util.location.LocationUtil;

import java.util.ArrayList;


/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/04/2018.
 */
public class HomeActivity extends BaseActivity
        implements HomeActivityContract.View, RequestDialogFragment.IRequestDialogFragmentListener, LocationUtil.LocationListener, GoogleMap.OnMapLongClickListener {

    private static final int INITIAL_ZOOM_LEVEL = 14;

    private GoogleMap mMap;
    private Circle searchCircle;

    private HomeActivityContract.Presenter mPresenter;
    private LinearLayout mDonorSheet, mReceiver;
    private BottomSheetBehavior<LinearLayout> donorBehavior;
    private BottomSheetBehavior<LinearLayout> receiverBehaviour;

    private LocationUtil mLocationUtil;

    private ArrayList<Marker> donorMarkers;
    private ArrayList<Marker> receiverMarkers;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        mLocationUtil.onPermissionResult(requestCode, permissions, grantResults);

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        mLocationUtil.onResolutionResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

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

        donorMarkers = new ArrayList<>();
        receiverMarkers = new ArrayList<>();

        mLocationUtil = new LocationUtil(this, Injection.getSharedPreference());
        (mDonorSheet.findViewById(R.id.direction)).setOnClickListener(view -> {
            com.udacity.nanodegree.blooddonation.data.model.Location location = (com.udacity.nanodegree.blooddonation.data.model.Location) view.getTag();
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + location.getLatitude() + "," + location.getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
        (mReceiver.findViewById(R.id.direction)).setOnClickListener(view -> {
            com.udacity.nanodegree.blooddonation.data.model.Location location = (com.udacity.nanodegree.blooddonation.data.model.Location) view.getTag();
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + location.getLatitude() + "," + location.getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        removeOldMarkers(donorMarkers);
        removeOldMarkers(receiverMarkers);
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
            case R.id.action_my_profile:
                Intent myprofile = new Intent(HomeActivity.this, MyProfileActivity.class);
                startActivity(myprofile);
                break;
            case R.id.action_map_theme:
                Toast.makeText(this, "Map Theme", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_about:
                Intent about = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(about);
                break;
            case R.id.action_sign_out:
                logout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void updateCamera(@Nullable LatLng latLng) {
        if (latLng == null) {
            return;
        }
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latLng, INITIAL_ZOOM_LEVEL);
        mMap.animateCamera(location, 2000, null);
    }

    @Override
    public void fetchCurrentLocation() {
        mLocationUtil.fetchPreciseLocation(this);
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
    public void openCreateRequestDialog(@NonNull User user, @Nullable LatLng location) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RequestDialogFragment requestDialogFragment = RequestDialogFragment.getInstance(user, location);
        requestDialogFragment.show(fragmentManager, "request_dialog");
    }

    @Override
    public void onRequestDialogDismissed(@NonNull String uid, boolean isReceiver,
                                         ReceiverDonorRequestType receiverDonorRequestType) {

        addMarker(receiverDonorRequestType, !isReceiver);
        updateCamera(new LatLng(receiverDonorRequestType.getLocation().getLatitude(),
                receiverDonorRequestType.getLocation().getLongitude()));


    }

    @Override
    synchronized public void addMarker(@NonNull ReceiverDonorRequestType request, boolean isDonor) {


        LatLng latLng = new LatLng(request.getLocation().getLatitude(), request.getLocation().getLongitude());
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
                .title(String.format("%s (%s)", getString(isDonor ? R.string.donor : R.string.blood_request), request.getbGp()))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(isDonor ? BitmapDescriptorFactory.HUE_GREEN : BitmapDescriptorFactory.HUE_RED)));
        marker.setTag(request);

        if (isDonor)
            donorMarkers.add(marker);
        else receiverMarkers.add(marker);
    }

    @Override
    public void addDonorMarkers(ArrayList<ReceiverDonorRequestType> donors) {
        removeOldMarkers(donorMarkers);
        for (ReceiverDonorRequestType donor : donors) {
            addMarker(donor, true);
        }
    }

    @Override
    public void addReceiverMarkers(ArrayList<ReceiverDonorRequestType> receivers) {
        removeOldMarkers(receiverMarkers);
        for (ReceiverDonorRequestType receiver : receivers) {
            addMarker(receiver, false);
        }
    }

    // Google Map callbacks
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mLocationUtil.fetchApproximateLocation(this);
        mPresenter.onCreate();


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTitle() == null || marker.getTag() == null || !(marker.getTag() instanceof ReceiverDonorRequestType)) {
            return false;
        }
        ReceiverDonorRequestType mReceiverDonorRequestType = (ReceiverDonorRequestType) marker.getTag();
        if (marker.getTitle().contains(getString(R.string.donor))) {
            ((TextView) mDonorSheet.findViewById(R.id.name)).setText(mReceiverDonorRequestType.getfName() + " " + mReceiverDonorRequestType.getlName());
            ((TextView) mDonorSheet.findViewById(R.id.blood_group)).setText(mReceiverDonorRequestType.getbGp());
            ((TextView) mDonorSheet.findViewById(R.id.phone)).setText(mReceiverDonorRequestType.getPhone());
            ((TextView) mDonorSheet.findViewById(R.id.purpose)).setText(mReceiverDonorRequestType.getPurpose());
            (mDonorSheet.findViewById(R.id.direction)).setTag(mReceiverDonorRequestType.getLocation());
            toggleBottomSheet(donorBehavior, receiverBehaviour);
        } else if (marker.getTitle().contains(getString(R.string.blood_request))) {
            ((TextView) mReceiver.findViewById(R.id.name)).setText(mReceiverDonorRequestType.getfName() + " " + mReceiverDonorRequestType.getlName());
            ((TextView) mReceiver.findViewById(R.id.blood_group)).setText(mReceiverDonorRequestType.getbGp());
            ((TextView) mReceiver.findViewById(R.id.phone)).setText(mReceiverDonorRequestType.getPhone());
            ((TextView) mReceiver.findViewById(R.id.purpose)).setText(mReceiverDonorRequestType.getPurpose());
            (mReceiver.findViewById(R.id.direction)).setTag(mReceiverDonorRequestType.getLocation());
            toggleBottomSheet(receiverBehaviour, donorBehavior);
        }
        return false;
    }

    @Override
    synchronized public void removeOldMarkers(ArrayList<Marker> markers) {
        for (Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
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
    public void generalResponse(int responseId) {
        Toast.makeText(this, responseId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationReceived(@NonNull LatLng location, @NonNull String addressString) {
        LatLng latLng = new LatLng(location.latitude, location.longitude);
        mMap.addMarker(new MarkerOptions().position(latLng)
                .title(getString(R.string.you_are_here))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location)));
        updateCamera(location);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mPresenter.createRequestDialogForCustomLocation(latLng);
    }
}
