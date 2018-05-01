package com.udacity.nanodegree.blooddonation.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Patterns;

import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.constants.Constants;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;

import java.util.Calendar;

import timber.log.Timber;

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
        return !(TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
        } catch (NumberFormatException ex) {
        }

        return stringBuilder.toString();
    }

    // TODO -> Use google reverse api, to get address
    public static String convertLatLongToAddress(double latitude, double longitiude) {
        if (latitude == 0.0 || longitiude == 0.0) {
            return null;
        }
        return String.valueOf(latitude).concat("      ")
                .concat(String.valueOf(longitiude));
    }

    public static User getPreparedUser(UserDetail userDetail) {
        User user = new User();
        user.fName = userDetail.firstName.get();
        user.lName = userDetail.lastName.get();
        user.email = userDetail.email.get();
        user.bloodGroup = userDetail.bloodGroup.get();
        user.dob = userDetail.dob.get();
        boolean isMale = userDetail.isMale.get();
        user.gender = isMale ? Constants.MALE : Constants.FEMALE;
        user.latitude = userDetail.latitiude.get();
        user.longitude = userDetail.longitude.get();

        return user;
    }


    public static int isValidUser(@Nullable User user) {

        if (user == null)
            return R.string.msg_empty_user;

        if (user.fName.isEmpty())
            return R.string.msg_first_name_empty;

        if (user.lName.isEmpty())
            return R.string.msg_last_name_empty;

        if (user.email.isEmpty())
            return R.string.msg_email_empty;

        if (!Util.isValidEmail(user.email))
            return R.string.msg_invalid_email;

        if (user.dob.isEmpty())
            return R.string.msg_invalid_age;

        if (!(user.latitude > 0 && user.longitude > 0)) {
            return R.string.msg_invalid_location;
        }

        return 0;
    }
}
