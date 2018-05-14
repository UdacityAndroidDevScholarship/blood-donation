package com.udacity.nanodegree.blooddonation.ui.login.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.udacity.nanodegree.blooddonation.storage.SharedPreferenceManager;

/**
 * Created by bloodybadboy on 9/5/18.
 */

public class UserPhoneNumber implements Parcelable {
  public static final Creator<UserPhoneNumber> CREATOR = new Creator<UserPhoneNumber>() {
    public UserPhoneNumber createFromParcel(Parcel in) {
      return new UserPhoneNumber(in);
    }

    public UserPhoneNumber[] newArray(int size) {
      return new UserPhoneNumber[size];
    }
  };
  public static final String EXTRA_USER_PHONE_NUMBER = "userPhoneNumber";
  private static final String KEY_USER_PHONE_NUMBER = "user_phone_number";
  @SerializedName("countryName")
  private String countryName;
  @SerializedName("countryCode")
  private String iso;
  @SerializedName("phoneCode")
  private String phoneCode;
  @SerializedName("phoneNumber")
  private String phoneNumber;

  public UserPhoneNumber() {
  }

  private UserPhoneNumber(Parcel in) {
    String[] strings = new String[4];
    in.readStringArray(strings);
    iso = strings[0];
    countryName = strings[1];
    phoneCode = strings[2];
    phoneNumber = strings[3];
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public String getIso() {
    return iso;
  }

  public void setIso(String iso) {
    this.iso = iso;
  }

  public String getPhoneCode() {
    return phoneCode;
  }

  public void setPhoneCode(String phoneCode) {
    this.phoneCode = phoneCode;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public static UserPhoneNumber fromSharedPreferences(SharedPreferenceManager preferences) {
    String json = preferences.getString(KEY_USER_PHONE_NUMBER);
    return new Gson().fromJson(json, UserPhoneNumber.class);
  }

  public void saveToSharedPreferences(SharedPreferenceManager preferences) {
    preferences.put(KEY_USER_PHONE_NUMBER,
        new Gson().toJson(this));
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeStringArray(new String[] { iso, countryName, phoneCode, phoneNumber });
  }

  @Override public String toString() {
    return "UserPhoneNumber{" +
        "countryName='" + countryName + '\'' +
        ", iso='" + iso + '\'' +
        ", phoneCode='" + phoneCode + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}
