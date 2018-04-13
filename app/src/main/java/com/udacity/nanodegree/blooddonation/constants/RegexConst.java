package com.udacity.nanodegree.blooddonation.constants;

/**
 * Created by riteshksingh on Apr, 2018
 */
final public class RegexConst {
    private RegexConst() {
    }

    public final static String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
}
