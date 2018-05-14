package com.udacity.nanodegree.blooddonation.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Patterns;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.common.binding.ObservableString;
import com.udacity.nanodegree.blooddonation.constants.Constants;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by riteshksingh on Apr, 2018
 */
final public class Util {
  private Util() {
  }


  public static String getPhoneNumberWithPlus(String phoneNumber, String phoneCode) {
    return "+".concat(phoneCode).concat(phoneNumber).trim();
  }

  public static String getHumanReadableDate(ObservableString timeInMillis) {
    if (TextUtils.isEmpty(timeInMillis.get())) return "";
    try {
      long l = Long.parseLong(timeInMillis.get());
      return new SimpleDateFormat("dd MMM, yyyy - EEEE", Locale.ENGLISH).format(new Date(l));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    return "";
  }

  // TODO -> Use google reverse api, to get address
  public static String convertLatLongToAddress(double latitude, double longitiude) {
    if (latitude == 0.0 || longitiude == 0.0) {
      return null;
    }
    return String.valueOf(latitude).concat("      ")
        .concat(String.valueOf(longitiude));
  }

}
