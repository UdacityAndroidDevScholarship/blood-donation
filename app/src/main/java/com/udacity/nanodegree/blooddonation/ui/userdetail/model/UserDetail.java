package com.udacity.nanodegree.blooddonation.ui.userdetail.model;

import android.databinding.ObservableBoolean;
import com.udacity.nanodegree.blooddonation.common.binding.ObservableString;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetail {
  private ObservableString fullName = new ObservableString();
  private ObservableBoolean isMale = new ObservableBoolean();
  private ObservableString birthdayInMillis = new ObservableString();
  private ObservableString phoneNumber = new ObservableString();
  private ObservableString email = new ObservableString();
  private ObservableString address = new ObservableString();
  private ObservableString landMark = new ObservableString();
  private ObservableString city = new ObservableString();
  private ObservableString country = new ObservableString();
  private ObservableString pinCode = new ObservableString();
  private ObservableString state = new ObservableString();
  private ObservableBoolean isPhotoPicked = new ObservableBoolean();

  public UserDetail() {
    isMale.set(true);
    isPhotoPicked.set(false);
  }

  public ObservableBoolean getIsPhotoPicked() {
    return isPhotoPicked;
  }

  public ObservableString getPinCode() {
    return pinCode;
  }

  public ObservableString getFullName() {
    return this.fullName;
  }

  public ObservableBoolean getIsMale() {
    return this.isMale;
  }

  public ObservableString getBirthdayInMillis() {
    return this.birthdayInMillis;
  }

  public ObservableString getPhoneNumber() {
    return this.phoneNumber;
  }

  public ObservableString getEmail() {
    return this.email;
  }

  public ObservableString getCountry() {
    return this.country;
  }

  public ObservableString getAddress() {
    return this.address;
  }

  public ObservableString getLandMark() {
    return this.landMark;
  }

  public ObservableString getCity() {
    return this.city;
  }

  public ObservableString getState() {
    return this.state;
  }
}
