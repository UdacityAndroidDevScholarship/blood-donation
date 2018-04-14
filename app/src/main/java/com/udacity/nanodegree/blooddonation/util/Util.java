package com.udacity.nanodegree.blooddonation.util;

import android.text.TextUtils;
import android.util.Patterns;

import com.udacity.nanodegree.blooddonation.constants.RegexConst;

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
}
