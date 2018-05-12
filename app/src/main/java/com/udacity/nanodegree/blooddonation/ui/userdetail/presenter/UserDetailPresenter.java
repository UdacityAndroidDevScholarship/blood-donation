package com.udacity.nanodegree.blooddonation.ui.userdetail.presenter;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.constants.SharedPrefConstants;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.data.source.DonationDataSource;
import com.udacity.nanodegree.blooddonation.storage.SharedPreferenceManager;
import com.udacity.nanodegree.blooddonation.ui.userdetail.UserDetailContract;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;
import com.udacity.nanodegree.blooddonation.util.ValidationUtil;

import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;
import static com.udacity.nanodegree.blooddonation.constants.Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetailPresenter implements UserDetailContract.Presenter {

  private final UserDetailContract.View mView;
  private final FirebaseAuth mFirebaseAuth;
  private final DonationDataSource mDataRepo;
  private final SharedPreferenceManager mSharedPreferenceManager;

  public UserDetailPresenter(UserDetailContract.View view, FirebaseAuth firebaseAuth,
      SharedPreferenceManager sharedPreference, DonationDataSource dataRepo) {
    mFirebaseAuth = firebaseAuth;
    mSharedPreferenceManager = sharedPreference;
    mView = view;
    mDataRepo = dataRepo;
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

  @Override
  public void onCreateNowClick(UserDetail userDetail) {
    Pair<Integer, Integer> viewIdMessagePair =
        ValidationUtil.validateUserDetails(User.fromUserDetail(userDetail));
    mView.clearAllTextInputErrors();
    if (viewIdMessagePair != null) {
      mView.generalResponse(R.string.user_detail_error_msg_empty_field);
      mView.showTextInputError(viewIdMessagePair.first, viewIdMessagePair.second);
    } else {
      mView.setCreateAccountProgressVisibility(true);
      mDataRepo.saveNewUser(mFirebaseAuth.getUid(), User.fromUserDetail(userDetail),
          isSuccessful -> {
            if (!isSuccessful) {
              mView.setCreateAccountProgressVisibility(false);
              mView.generalResponse(R.string.user_detail_error_msg_unexpected);
            } else {
              mSharedPreferenceManager.put(SharedPrefConstants.IS_USER_DETAILS_ENTERED, true);
              mView.launchHomeScreen();
            }
          });
    }
  }

  @Override public void onSelectBirthdayClick() {
    mView.showDatePickerDialog();
  }

  @Override public void onSelectCityClick() {
    mView.startCityPickerActivity();
  }

  @Override
  public void handleActivityResult(Context context, int requestCode, int resultCode, Intent data) {
    if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
      if (resultCode == RESULT_OK) {
        Place place = PlaceAutocomplete.getPlace(context, data);
        Geocoder geocoder = new Geocoder(context);
        LatLng latLng = place.getLatLng();
        try {
          List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
          Address address = addresses.get(0);

          mView.setUserDetailCityAndState(place.getName().toString(), address.getAdminArea());
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
        Status status = PlaceAutocomplete.getStatus(context, data);
        // TODO: Handle the error.
        Log.i(TAG, status.getStatusMessage());
      } else if (resultCode == RESULT_CANCELED) {
        // The user canceled the operation.
      }
    }
  }
}
