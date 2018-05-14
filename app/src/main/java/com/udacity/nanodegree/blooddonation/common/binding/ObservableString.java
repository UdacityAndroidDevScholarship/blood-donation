package com.udacity.nanodegree.blooddonation.common.binding;

import android.databinding.BaseObservable;
import android.databinding.BindingConversion;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class ObservableString extends BaseObservable implements Parcelable, Serializable {
  public static final Creator<ObservableString> CREATOR = new Creator<ObservableString>() {
    public ObservableString createFromParcel(Parcel in) {
      return new ObservableString(in);
    }

    public ObservableString[] newArray(int size) {
      return new ObservableString[size];
    }
  };
  static final long serialVersionUID = 1;
  private String value = "";

  public ObservableString(String value) {
    this.value = value;
  }

  public ObservableString() {
  }

  private ObservableString(Parcel in) {
    this.value = in.readString();
  }


  public String get() {
    return value != null ? value : "";
  }

  public void set(String value) {
    if (value == null) value = "";
    if (!this.value.contentEquals(value)) {
      this.value = value;
      notifyChange();
    }
  }

  public boolean isEmpty() {
    if (value != null) {
      if (!value.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  public void clear() {
    set(null);
  }

  @BindingConversion
  public static String convertToString(ObservableString observableString) {
    return observableString.get();
  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.value);
  }
}
