package com.udacity.nanodegree.blooddonation.ui.home.presenter;

import android.view.View;
import android.widget.AdapterView;
import com.udacity.nanodegree.blooddonation.ui.home.RequestDialogContract;
import timber.log.Timber;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class RequestDialogPresenter implements RequestDialogContract.Presenter {

  private RequestDialogContract.View mView;

  public RequestDialogPresenter(RequestDialogContract.View view) {
    mView = view;
  }

  @Override public void onCreate() {

  }

  @Override public void onStart() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {

  }

  @Override
  public void onRequestTypeDropDownChange(AdapterView<?> parent, View view, int position, long id) {
    Timber.d(parent.getItemAtPosition(position).toString());
  }

  @Override
  public void onBloodGroupChange(AdapterView<?> parent, View view, int position, long id) {
    Timber.d(parent.getItemAtPosition(position).toString());
  }

  @Override public void onPurposeTextChanged(CharSequence s, int start, int before, int count) {
    Timber.d(s.toString());
  }

  @Override public void onSubmitButtonClick() {
    Timber.d("Submit button clicked");
  }

  @Override public void onLocationClick() {
    mView.getLastLocation();
  }
}
