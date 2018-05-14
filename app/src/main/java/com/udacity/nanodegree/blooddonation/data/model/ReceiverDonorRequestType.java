package com.udacity.nanodegree.blooddonation.data.model;

/**
 * Created by Kautilya on 25-04-2018.
 */
public class ReceiverDonorRequestType {

    Location location;
    String bGp;
    String purpose;
    String fullName;
    String phone;

    public ReceiverDonorRequestType() {
    }

    public ReceiverDonorRequestType(Location location, String bGp, String purpose, String fullName, String phone) {
        this.location = location;
        this.bGp = bGp;
        this.purpose = purpose;
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public ReceiverDonorRequestType setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
