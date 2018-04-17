package com.udacity.nanodegree.blooddonation.ui.userdetail.model;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableDouble;
import com.udacity.nanodegree.blooddonation.common.binding.ObservableString;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetail {
  public ObservableString firstName = new ObservableString();
  public ObservableString lastName = new ObservableString();
  public ObservableString email = new ObservableString();
  public ObservableString bloodGroup = new ObservableString();
  public ObservableString dob = new ObservableString();
  public ObservableBoolean isMale = new ObservableBoolean(true);
  public ObservableDouble latitiude = new ObservableDouble();
  public ObservableDouble longitude = new ObservableDouble();

  public UserDetail() {

  }
}
