package com.udacity.nanodegree.blooddonation.ui.home.model;

import android.databinding.ObservableDouble;
import com.udacity.nanodegree.blooddonation.common.binding.ObservableString;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class RequestDetails {
  public ObservableString requestType = new ObservableString();
  public ObservableString bloodGroup = new ObservableString();
  public ObservableDouble latitude = new ObservableDouble();
  public ObservableDouble longitude = new ObservableDouble();
  public ObservableString purpose = new ObservableString();
}
