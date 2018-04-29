package com.udacity.nanodegree.blooddonation.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by riteshksingh on Apr, 2018
 */
public final class AppUtil {
    private AppUtil() {
    }

    public static void replaceFragmentInActivity(FragmentManager fragmentManager, Fragment fragment,
            int containerId) {
        fragmentManager.beginTransaction().replace(containerId, fragment).commit();
    }

    public static void validatePhoneNumber(EditText phoneNumberView , View iAmInButtonView){

        phoneNumberView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after){

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(count >6 && count <13){
                    iAmInButtonView.setEnabled(true);
                }else {
                    phoneNumberView.setError("The mobile number must have 10 digits !!!");
                    iAmInButtonView.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
