package com.udacity.nanodegree.blooddonation.ui.login;

import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import com.goodiebag.pinview.Pinview;
import com.udacity.nanodegree.blooddonation.common.binding.TextWatcherAdapter;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserLoginInfo {
  public ObservableField<String> phoneNumber = new ObservableField<>();

  public ObservableField<String> otp = new ObservableField<>();

  public ObservableField<String> phoneCode = new ObservableField<>();

  public UserLoginInfo() {
    phoneNumber.set("");
    otp.set("");
    phoneCode.set("");
  }

  public TextWatcher phoneNumberWatcher = new TextWatcherAdapter() {
    @Override public void afterTextChanged(Editable s) {
      if (!TextUtils.equals(phoneNumber.get(), s)) {
        phoneNumber.set(s.toString());
      }
    }
  };

  public Pinview.PinViewEventListener pinViewEventListener = (pinview, fromUser) -> {

    if (!TextUtils.equals(otp.get(), pinview.getValue())) {
      otp.set(pinview.getValue());
    }
  };
}
