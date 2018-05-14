package com.udacity.nanodegree.blooddonation.ui.userdetail.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.constants.SharedPrefConstants;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.data.source.DonationDataSource;
import com.udacity.nanodegree.blooddonation.storage.SharedPreferenceManager;
import com.udacity.nanodegree.blooddonation.ui.userdetail.UserDetailContract;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;
import com.udacity.nanodegree.blooddonation.util.ImageScaleTask;
import com.udacity.nanodegree.blooddonation.util.ValidationUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;
import static com.udacity.nanodegree.blooddonation.constants.Constants.PERMISSION_REQUEST_CODE;
import static com.udacity.nanodegree.blooddonation.constants.Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE;
import static com.udacity.nanodegree.blooddonation.constants.FireBaseConstants.IMAGES;
import static com.udacity.nanodegree.blooddonation.constants.FireBaseConstants.USERS;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetailPresenter implements UserDetailContract.Presenter {

  private final UserDetailContract.View mView;
  private final FirebaseAuth mFirebaseAuth;
  private final FirebaseStorage mFirebaseStorage;
  private final DonationDataSource mDataRepo;
  private final SharedPreferenceManager mSharedPreferenceManager;
  private File mProfilePicture;

  public UserDetailPresenter(UserDetailContract.View view, FirebaseAuth firebaseAuth,
      FirebaseStorage firebaseStorage,
      SharedPreferenceManager sharedPreference, DonationDataSource dataRepo) {
    mFirebaseAuth = firebaseAuth;
    mFirebaseStorage = firebaseStorage;
    mSharedPreferenceManager = sharedPreference;
    mView = view;
    mDataRepo = dataRepo;
  }

  @Override
  public void onCreate() {
    mView.checkPermissions();
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
      mView.generalResponse(R.string.user_details_error_msg_empty_field);
      mView.showTextInputError(viewIdMessagePair.first, viewIdMessagePair.second);
    } else {
      mView.setCreateAccountProgressVisibility(true);
      StorageReference storageReference =
          mFirebaseStorage.getReference()
              .child(USERS)
              .child(IMAGES + "/" + mFirebaseAuth.getUid() + "/profile.jpg");

      final User user = User.fromUserDetail(userDetail);

      if (mProfilePicture != null && mProfilePicture.exists()) {
        storageReference.putFile(Uri.fromFile(mProfilePicture)).addOnCompleteListener(
            task -> {
              if (task.isSuccessful()) {
                Uri downloadUri = task.getResult().getDownloadUrl();
                if (downloadUri != null) {
                  user.profilePhotoUrl = downloadUri.toString();
                }
                //noinspection ResultOfMethodCallIgnored
                mProfilePicture.delete(); // just try to delete ignore result
                saveUserDetails(user);
              } else {
                mView.generalResponse(R.string.user_details_error_msg_photo_upload);
                mView.setCreateAccountProgressVisibility(false);
              }
            });
      } else {
        saveUserDetails(user);
      }
    }
  }

  private void saveUserDetails(User user) {
    mDataRepo.saveNewUser(mFirebaseAuth.getUid(), user,
        isSuccessful -> {
          if (isSuccessful) {
            mSharedPreferenceManager.put(SharedPrefConstants.IS_USER_DETAILS_ENTERED, true);
            mView.launchHomeScreen();
          } else {
            mView.setCreateAccountProgressVisibility(false);
            mView.generalResponse(R.string.user_details_error_msg_unexpected);
          }
        });
  }

  @Override public void onSelectBirthdayClick() {
    mView.showDatePickerDialog();
  }

  @Override public void onSelectCityClick() {
    mView.startCityPickerActivity();
  }

  @Override public void onPickPhotoClick() {
    mView.startImageCropperActivity();
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
        Log.e(TAG, status.getStatusMessage());
        mView.generalResponse(R.string.user_details_error_msg_pick_city);
      }
    } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      CropImage.ActivityResult result = CropImage.getActivityResult(data);
      if (resultCode == RESULT_OK) {

        mView.setPickImageProgressVisibility(true);

        final File imageFile = new File(result.getUri().getPath());
        final File scaledFile =
            new File(context.getCacheDir(), "profile_" + imageFile.getName());

        new ImageScaleTask(imageFile, scaledFile, (success, scaledBitmap) -> {
          mView.setPickImageProgressVisibility(false);
          //noinspection ResultOfMethodCallIgnored
          imageFile.delete(); // just try to delete ignore result
          if (success) {
            mProfilePicture = scaledFile;
            mView.setUserProfileImage(scaledBitmap);
          }
        }).execute();
      } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        mView.generalResponse(R.string.user_details_error_msg_photo_prepare);
      }
    }
  }

  @Override public void handlePermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == PERMISSION_REQUEST_CODE) {
      if (grantResults.length > 1) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED
            && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
          mView.setPermissionsGranted();
        } else {
          mView.showPermissionGrantFromSettings();
        }
      }
    }
  }
}
