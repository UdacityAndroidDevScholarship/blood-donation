package com.udacity.nanodegree.blooddonation.data.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by Kautilya on 25-04-2018.
 */
@SuppressWarnings("WeakerAccess") @IgnoreExtraProperties public class Location {

  @PropertyName("lat")
  public double latitude;

  @PropertyName("lon")
  public double longitude;

  public Location() {
  }

  public Location(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
