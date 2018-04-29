package com.udacity.nanodegree.blooddonation.util.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by tavishjain on 29-04-2018.
 */

public class PhoneNumberValidation {

    public static void validatePhoneNumber(EditText phoneNumberView , View iAmInButtonView){

        phoneNumberView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after){

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(count < 14 && count > 6){
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
