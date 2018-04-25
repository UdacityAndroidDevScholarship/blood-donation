package com.udacity.nanodegree.blooddonation.data.model;

/**
 * Created by Kautilya on 25-04-2018.
 */
public class Donor {

    String id;
    Location mLocation;
    boolean isAvailable;
    long mPhoneNumber;
    String mName;
    String mProfilePic;
    String mBloodType;

    public Donor(String id, Location mLocation, boolean isAvailable, long mPhoneNumber, String mName, String mProfilePic, String mBloodType) {
        this.id = id;
        this.mLocation = mLocation;
        this.isAvailable = isAvailable;
        this.mPhoneNumber = mPhoneNumber;
        this.mName = mName;
        this.mProfilePic = mProfilePic;
        this.mBloodType = mBloodType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getmLocation() {
        return mLocation;
    }

    public void setmLocation(Location mLocation) {
        this.mLocation = mLocation;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public long getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(long mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmProfilePic() {
        return mProfilePic;
    }

    public void setmProfilePic(String mProfilePic) {
        this.mProfilePic = mProfilePic;
    }

    public String getmBloodType() {
        return mBloodType;
    }

    public void setmBloodType(String mBloodType) {
        this.mBloodType = mBloodType;
    }
}
