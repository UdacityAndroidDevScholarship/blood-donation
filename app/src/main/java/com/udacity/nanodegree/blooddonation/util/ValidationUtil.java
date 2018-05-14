package com.udacity.nanodegree.blooddonation.util;

import android.text.TextUtils;
import android.util.Pair;
import android.util.Patterns;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.data.model.User;

/**
 * Created by bloodybadboy on 11/5/18.
 */

public class ValidationUtil {
  public ValidationUtil() {
    throw new AssertionError();
  }

  public static boolean isValidPhoneNumber(CharSequence phoneNumber) {
    return !TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches();
  }

  public static boolean isValidEmail(CharSequence email) {
    return !(TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  public static Pair<Integer, Integer> validateUserDetails(User user) {

    if (user == null) {
      return new Pair<>(0, R.string.user_detail_error_msg_empty_user);
    }

    if (TextUtils.isEmpty(user.fullName)) {
      return new Pair<>(R.id.til_user_details_full_name, R.string.user_details_error_msg_full_name);
    }

    if (TextUtils.isEmpty(user.birthdayInMillis)) {
      return new Pair<>(R.id.til_user_details_birthday, R.string.user_details_error_msg_birthday);
    }

    if (TextUtils.isEmpty(user.email)) {
      return new Pair<>(R.id.til_user_details_email, R.string.user_details_error_msg_email);
    }

    if (!isValidEmail(user.email)) {
      return new Pair<>(R.id.til_user_details_email, R.string.user_details_error_msg_invalid_email);
    }

    if (TextUtils.isEmpty(user.address)) {
      return new Pair<>(R.id.til_user_details_address, R.string.user_details_error_msg_address);
    }

    if (TextUtils.isEmpty(user.city)) {
      return new Pair<>(R.id.til_user_details_city, R.string.user_details_error_msg_city);
    }

    if (TextUtils.isEmpty(user.state)) {
      return new Pair<>(R.id.til_user_details_state, R.string.user_details_error_msg_state);
    }

    return null;
  }
}
