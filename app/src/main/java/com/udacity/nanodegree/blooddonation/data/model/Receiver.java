package com.udacity.nanodegree.blooddonation.data.model;

/**
 * Created by Kautilya on 25-04-2018.
 */
public class Receiver {

    String id;
    Location mLocation;
    boolean mIsRequired;
    String mName;
    long mPhoneNumber;
    String mBloodType;
    String mProfilePic;

    public Receiver(String id, Location mLocation, boolean mIsRequired, String mName, long mPhoneNumber, String mBloodType, String mProfilePic) {
        this.id = id;
        this.mLocation = mLocation;
        this.mIsRequired = mIsRequired;
        this.mName = mName;
        this.mPhoneNumber = mPhoneNumber;
        this.mBloodType = mBloodType;
        this.mProfilePic = mProfilePic;
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

    public boolean ismIsRequired() {
        return mIsRequired;
    }

    public void setmIsRequired(boolean mIsRequired) {
        this.mIsRequired = mIsRequired;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public long getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(long mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmBloodType() {
        return mBloodType;
    }

    public void setmBloodType(String mBloodType) {
        this.mBloodType = mBloodType;
    }

    public String getmProfilePic() {
        return mProfilePic;
    }

    public void setmProfilePic(String mProfilePic) {
        this.mProfilePic = mProfilePic;
    }
}
