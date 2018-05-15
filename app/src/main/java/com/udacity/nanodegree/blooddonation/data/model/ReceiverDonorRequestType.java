package com.udacity.nanodegree.blooddonation.data.model;

/**
 * Created by Kautilya on 25-04-2018.
 */
public class ReceiverDonorRequestType {

    Location location;
    String bGp;
    String purpose;
    String fName;
    String lName;
    String phone;
    String instanceId;

    public ReceiverDonorRequestType() {
    }

    public ReceiverDonorRequestType(Location location, String bGp, String purpose, String fName, String lName, String phone) {
        this.location = location;
        this.bGp = bGp;
        this.purpose = purpose;
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
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
