package com.udacity.nanodegree.blooddonation.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.udacity.nanodegree.blooddonation.constants.Constants;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;

/**
 * Created by riteshksingh on Apr, 2018
 */
@SuppressWarnings("WeakerAccess") @IgnoreExtraProperties public class User {

  @PropertyName("photo_url")
  public String profilePhotoUrl;

  @PropertyName("full_name")
  public String fullName;

  @PropertyName("gender")
  public String gender;

  @PropertyName("birthday")
  public String birthdayInMillis;

  @PropertyName("phone_number")
  public String phoneNumber;

  @PropertyName("email")
  public String email;

  @PropertyName("address")
  public String address;

  @PropertyName("landmark")
  public String landMark;

  @PropertyName("city")
  public String city;

  @PropertyName("country")
  public String country;

  @PropertyName("pincode")
  public String pinCode;

  @PropertyName("state")
  public String state;

  @Exclude
  public static User fromUserDetail(UserDetail userDetail) {
    User user = new User();
    user.fullName = userDetail.getFullName().get();
    user.gender = userDetail.getIsMale().get() ? Constants.MALE : Constants.FEMALE;
    user.birthdayInMillis = userDetail.getBirthdayInMillis().get();
    user.phoneNumber = userDetail.getPhoneNumber().get();
    user.email = userDetail.getEmail().get();
    user.address = userDetail.getAddress().get();
    user.landMark = userDetail.getLandMark().get();
    user.pinCode = userDetail.getPinCode().get();
    user.city = userDetail.getCity().get();
    user.state = userDetail.getState().get();
    user.country = userDetail.getCountry().get();
    return user;
  }
}
