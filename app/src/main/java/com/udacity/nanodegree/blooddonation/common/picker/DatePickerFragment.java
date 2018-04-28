package com.udacity.nanodegree.blooddonation.common.picker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import com.udacity.nanodegree.blooddonation.common.binding.ObservableString;
import java.util.Calendar;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

  private static final String KEY = "key";

  public static DatePickerFragment newInstance(ObservableString observableString) {
    DatePickerFragment datePickerFragment = new DatePickerFragment();

    Bundle args = new Bundle();
    args.putSerializable(KEY, observableString);
    datePickerFragment.setArguments(args);

    return datePickerFragment;
  }

  private ObservableString observableString;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    observableString = (ObservableString) getArguments().getSerializable(KEY);
  }

  @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    return new DatePickerDialog(getActivity(), this, year, month, day);
  }

  @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(year,month,dayOfMonth);
    observableString.set(String.valueOf(calendar.getTimeInMillis()));
  }
}
