package com.udacity.nanodegree.blooddonation.util.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.constants.SharedPrefConstants;
import com.udacity.nanodegree.blooddonation.storage.SharedPreferenceManager;
import com.udacity.nanodegree.blooddonation.util.permission.AppPermissionsUtil;

import java.lang.ref.WeakReference;


/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 25/4/18.
 */
public class LocationUtil {


    private static final int LOCATION_SETTINGS_REQUEST_CODE = 1001;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1002;
    private final Geocoder mGeocoder;
    private boolean mIsLocationCallbackAdded;
    private WeakReference<AppCompatActivity> mActivity;
    private WeakReference<LocationListener> mListener;
    /**
     * if false: City/locality is returned.
     * if true: Complete Address is returned.
     */
    private boolean mIsAddressRequired;
    private SharedPreferenceManager mPreferenceManager;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;

    public <T extends AppCompatActivity> LocationUtil(T activity, SharedPreferenceManager sharedPreferenceManager) {
        mActivity = new WeakReference<>(activity);


        mPreferenceManager = sharedPreferenceManager;
        mGeocoder = new Geocoder(activity);

        mIsLocationCallbackAdded = false;

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    Location location = locationResult.getLastLocation();
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    new FetchPlaceTask(mGeocoder, latLng, mIsAddressRequired, addressString -> {
                        mListener.get().onLocationReceived(latLng, addressString);
                        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
                        mIsLocationCallbackAdded = false;

                    });
                }

            }
        };

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity.get());
    }

    /**
     * Method to fetch location for sign up. This type of request returns accurate location with
     * city/locality string.
     */
    public void fetchApproximateLocation(LocationListener listener) {

        mListener = new WeakReference<>(listener);


        String neverAskMessage = mActivity.get().getString(R.string.msg_never_ask_sign_up);
        String rationaleMessage = mActivity.get().getString(R.string.msg_sign_up_location_message);

        fetchLocation(neverAskMessage, rationaleMessage, false);

    }

    /**
     * Method to fetch location for creating blood request. This type of request returns a really
     * precise location with complete address.
     */
    public void fetchPreciseLocation(LocationListener listener) {

        mListener = new WeakReference<>(listener);

        String neverAskMessage = mActivity.get().getString(R.string.msg_never_ask_blood_request);
        String rationaleMessage = mActivity.get().getString(R.string.msg_blood_request_location_message);

        fetchLocation(neverAskMessage, rationaleMessage, true);

    }

    public void fetchPlaceName(LocationListener listener, LatLng location) {
        mListener = new WeakReference<>(listener);

        new FetchPlaceTask(mGeocoder, location, true, addressString ->
                mListener.get().onLocationReceived(location, addressString));
    }

    public void fetchLocation(String neverAskMessage, String rationaleMessage, boolean isCompleteAddressRequired) {
        mIsAddressRequired = isCompleteAddressRequired;
        //Permission is granted.
        if (AppPermissionsUtil.checkIfLocationPermissionIsGiven(mActivity.get()))
            createLocationRequest();
        else {
            // Never Ask scenario
            if (mPreferenceManager.getBoolean(SharedPrefConstants.IS_LOCATION_PERMISSION_DIALOG_SHOWN)
                    && !AppPermissionsUtil.shouldShowPermissionRationaleForLocation(mActivity.get())) {


                new AlertDialog.Builder(mActivity.get())
                        .setTitle(R.string.msg_location_permission_title)
                        .setMessage(neverAskMessage)
                        .setPositiveButton(mActivity.get().getString(R.string.txt_open_settings), ((dialog, which) -> {
                            dialog.dismiss();
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", mActivity.get().getPackageName(), null);
                            intent.setData(uri);
                            mActivity.get().startActivity(intent);
                        })).setNegativeButton(mActivity.get().getString(R.string.cancel), ((dialog, which) -> dialog.dismiss()))
                        .create().show();

            } else if (AppPermissionsUtil.shouldShowPermissionRationaleForLocation(mActivity.get())) {
                // Permission has been denied once.
                new AlertDialog.Builder(mActivity.get())
                        .setTitle(R.string.msg_location_permission_title)
                        .setMessage(rationaleMessage)
                        .setPositiveButton(mActivity.get().getString(R.string.ok), (dialog, which) -> {
                            dialog.dismiss();
                            AppPermissionsUtil.requestForLocationPermission(mActivity.get(), LOCATION_PERMISSION_REQUEST_CODE);

                        })
                        .setNegativeButton(mActivity.get().getString(R.string.cancel), ((dialog, which) -> dialog.dismiss()))
                        .create().show();
            } else //Ask for permission
                AppPermissionsUtil.requestForLocationPermission(mActivity.get(), LOCATION_PERMISSION_REQUEST_CODE);
        }


    }

    private void createLocationRequest() {


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(mActivity.get());
        settingsClient.checkLocationSettings(builder.build()).
                addOnSuccessListener(locationSettingsResponse -> getLocation())
                .addOnFailureListener(e -> {
                    if (e instanceof ResolvableApiException) {
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.

                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;

                        try {
                            resolvable.startResolutionForResult(mActivity.get(), LOCATION_SETTINGS_REQUEST_CODE);
                        } catch (IntentSender.SendIntentException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        if (!mIsLocationCallbackAdded) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
            mIsLocationCallbackAdded = true;
        }


    }

    public void onResolutionResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == LOCATION_SETTINGS_REQUEST_CODE && resultCode == Activity.RESULT_OK)
            getLocation();


    }


    public void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            mPreferenceManager.put(SharedPrefConstants.IS_LOCATION_PERMISSION_DIALOG_SHOWN, true);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                createLocationRequest();
        }
    }

    public void onDestroy() {
        if (mIsLocationCallbackAdded) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            mIsLocationCallbackAdded = false;
        }
    }

    public interface LocationListener {
        void onLocationReceived(@NonNull LatLng location, @NonNull String addressString);
    }

}
