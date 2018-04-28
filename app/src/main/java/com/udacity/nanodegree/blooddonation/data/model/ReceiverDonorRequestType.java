package com.udacity.nanodegree.blooddonation.data.model;

/**
 * Created by Kautilya on 25-04-2018.
 */
public class ReceiverDonorRequestType {

  Location location;
  String bGp;
  String purpose;

  public ReceiverDonorRequestType() {
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
