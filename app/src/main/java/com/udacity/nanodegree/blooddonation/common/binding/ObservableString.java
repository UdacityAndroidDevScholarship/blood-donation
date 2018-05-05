package com.udacity.nanodegree.blooddonation.common.binding;

import android.databinding.BaseObservable;
import android.databinding.BindingConversion;

import java.io.Serializable;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class ObservableString extends BaseObservable implements Serializable {
  private String value = "";

  public ObservableString(String value) {
    this.value = value;
  }

  public ObservableString() {
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
    return value == null || value.isEmpty();
  }

  public void clear() {
    set(null);
  }

  @BindingConversion
  public static String convertToString(ObservableString observableString) {
    return observableString.get();
  }
}
