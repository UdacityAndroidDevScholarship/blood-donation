package com.udacity.nanodegree.blooddonation.ui.registration;

import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;


/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserRegistrationInfo {
    public ObservableField<String> phoneNumber =
            new ObservableField<>();

    public TextWatcher phoneNumberWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.equals(phoneNumber.get(), s)) {
                phoneNumber.set(s.toString());
            }
        }
    };
}
