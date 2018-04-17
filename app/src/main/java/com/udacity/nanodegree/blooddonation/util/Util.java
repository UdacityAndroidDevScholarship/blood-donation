package com.udacity.nanodegree.blooddonation.util;

import android.text.TextUtils;
import android.util.Patterns;

import com.udacity.nanodegree.blooddonation.constants.RegexConst;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by riteshksingh on Apr, 2018
 */
final public class Util {
  private Util() {
  }

  public static boolean isValidPhoneNumber(CharSequence phoneNumber) {
    return !TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches();
  }

  public static String getPhoneNumberWithPlus(String phoneNumber, String phoneCode) {
    return "+".concat(phoneCode).concat(phoneNumber).trim();
  }

  public static boolean isValidEmail(CharSequence email) {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  public static String convertDateToDobFormat(String time) {
    StringBuilder stringBuilder = new StringBuilder();
    try {
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(Long.valueOf(time));
      stringBuilder.append(calendar.get(Calendar.DAY_OF_MONTH))
          .append("/")
          .append(calendar.get(Calendar.MONTH) + 1)
          .append("/")
          .append(calendar.get(Calendar.YEAR));
    } catch (NumberFormatException ex) { }

    return stringBuilder.toString();
  }

  // TODO -> Use google reverse api, to get address
  public static String convertLatLongToAddress(double latitude, double longitiude){
    if (latitude == 0.0 || longitiude == 0.0){
      return null;
    }
    return String.valueOf(latitude).concat("      ")
        .concat(String.valueOf(longitiude));
  }
}
