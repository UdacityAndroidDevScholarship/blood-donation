package com.udacity.nanodegree.blooddonation.ui.userdetail.model;

import android.databinding.ObservableBoolean;
import com.udacity.nanodegree.blooddonation.util.binding.ObservableString;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetail {
  public ObservableString firstName = new ObservableString();
  public ObservableString lastName = new ObservableString();
  public ObservableString bloodGroup = new ObservableString();
  public ObservableString email = new ObservableString();
  public ObservableString location = new ObservableString();
  public ObservableString dob = new ObservableString();
  public ObservableBoolean isMale = new ObservableBoolean(true);

  public UserDetail() {

  }
}
