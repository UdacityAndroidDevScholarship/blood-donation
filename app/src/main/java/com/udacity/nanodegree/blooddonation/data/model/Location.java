package com.udacity.nanodegree.blooddonation.data.model;

/**
 * Created by Kautilya on 25-04-2018.
 */
public class Location {

    double latitude;
    double longitiude;

    public Location(double latitude, double longitiude) {
        this.latitude = latitude;
        this.longitiude = longitiude;
    }

    public double getLatitude() {

        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitiude() {
        return longitiude;
    }

    public void setLongitiude(double longitiude) {
        this.longitiude = longitiude;
    }
}
