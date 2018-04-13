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


    public static boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(CharSequence password) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(RegexConst.PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        if (password.toString().length() < 7 && !matcher.matches()) {
            return false;
        }

        return true;
    }
}
