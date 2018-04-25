package com.udacity.nanodegree.blooddonation.ui.bloodrequest.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.databinding.ActivityBloodRequestBinding;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 25/04/2018.
 */
public class BloodRequestActivity extends BaseActivity {

  private ActivityBloodRequestBinding mActivityBloodRequestBinding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivityBloodRequestBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_blood_request);

    getSupportActionBar().setTitle(R.string.title_blood_request);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    initSpinner();
  }

  private void initSpinner() {
    ArrayAdapter<CharSequence> bloodGroupAdapter =
        ArrayAdapter.createFromResource(this, R.array.blood_group,
            android.R.layout.simple_spinner_item);
    bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mActivityBloodRequestBinding.bloodGroupDropDown.setAdapter(bloodGroupAdapter);

    ArrayAdapter<CharSequence> requestAdapter =
        ArrayAdapter.createFromResource(this, R.array.blood_request,
            android.R.layout.simple_spinner_item);
    requestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mActivityBloodRequestBinding.requestDropDown.setAdapter(requestAdapter);
  }
}




