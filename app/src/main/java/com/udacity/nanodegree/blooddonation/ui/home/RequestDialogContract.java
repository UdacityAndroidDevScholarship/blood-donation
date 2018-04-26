package com.udacity.nanodegree.blooddonation.ui.home;


import android.widget.AdapterView;
import com.udacity.nanodegree.blooddonation.base.BasePresenter;

/**
 * Created by riteshksingh on Apr, 2018
 */
public interface RequestDialogContract {
  interface View {
    void getLastLocation();
  }

  interface Presenter extends BasePresenter{
    void onRequestTypeDropDownChange(AdapterView<?> parent, android.view.View view, int position, long id);
    void onBloodGroupChange(AdapterView<?> parent, android.view.View view, int position, long id);
    void onPurposeTextChanged(CharSequence s, int start, int before, int count);
    void onSubmitButtonClick();
    void onLocationClick();
  }
}
