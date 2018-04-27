package com.udacity.nanodegree.blooddonation.data.model;

/**
 * Created by Kautilya on 25-04-2018.
 */
public class Receiver {

  Location location;
  String bGp;
  String purpose;

  public Receiver() {
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public String getbGp() {
    return bGp;
  }

  public void setbGp(String bGp) {
    this.bGp = bGp;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }
}
