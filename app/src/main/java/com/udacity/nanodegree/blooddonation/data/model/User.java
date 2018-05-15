package com.udacity.nanodegree.blooddonation.data.model;


import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by riteshksingh on Apr, 2018
 */
@IgnoreExtraProperties public class User {
  public String fName;
  public String lName;
  public String email;
  public String bloodGroup;
  public String dob;
  public String gender;
  public double latitude;
  public double longitude;

  public User() {
  }
}
