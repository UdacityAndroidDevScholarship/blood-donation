package com.udacity.nanodegree.blooddonation.data.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by Kautilya on 25-04-2018.
 */
@SuppressWarnings("WeakerAccess") @IgnoreExtraProperties public class ReceiverDonorRequestType {

  @PropertyName("request_location")
  public Location location;

  @PropertyName("blood_group")
  public String bloodGroup;

  @PropertyName("purpose")
  public String purpose;

  @PropertyName("full_name")
  public String fullName;

  @PropertyName("phone_number")
  public String phoneNumber;

  public ReceiverDonorRequestType() {
  }

  public ReceiverDonorRequestType(Location location, String bloodGroup, String purpose, String fullName,
      String phoneNumber) {
    this.location = location;
    this.bloodGroup = bloodGroup;
    this.purpose = purpose;
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
  }
}
