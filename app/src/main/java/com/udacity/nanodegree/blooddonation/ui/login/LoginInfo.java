package com.udacity.nanodegree.blooddonation.ui.login;

import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;


/**
 * Created by riteshksingh on Apr, 2018
 */
public class LoginInfo {
    public ObservableField<String> email =
            new ObservableField<>();
    public ObservableField<String> password =
            new ObservableField<>();

    public TextWatcher emailWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.equals(email.get(), s)) {
                email.set(s.toString());
            }
        }
    };

    public TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.equals(password.get(), s)) {
                password.set(s.toString());
            }
        }
    };
}
