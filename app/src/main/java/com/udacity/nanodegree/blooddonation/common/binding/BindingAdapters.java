package com.udacity.nanodegree.blooddonation.common.binding;

import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.*;
import com.goodiebag.pinview.Pinview;
import com.udacity.nanodegree.blooddonation.R;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class BindingAdapters {
  private BindingAdapters() {
    throw new AssertionError();
  }

  @BindingAdapter({ "app:binding" })
  public static void bindEditText(EditText view, final ObservableString observableString) {
    Pair<ObservableString, TextWatcherAdapter> pair = (Pair) view.getTag(R.id.bound_observable);
    if (pair == null || pair.first != observableString) {
      if (pair != null) {
        view.removeTextChangedListener(pair.second);
      }
      TextWatcherAdapter watcher = new TextWatcherAdapter() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          observableString.set(s.toString());
        }
      };
      view.setTag(R.id.bound_observable, new Pair<>(observableString, watcher));
      view.addTextChangedListener(watcher);
    }
    String newValue = observableString.get();
    if (!view.getText().toString().equals(newValue)) {
      view.setText(newValue);
    }
  }

  @BindingAdapter({ "app:binding" })
  public static void bindRadioGroup(RadioGroup view, final ObservableBoolean bindableBoolean) {
    if (view.getTag(R.id.bound_observable) != bindableBoolean) {
      view.setTag(R.id.bound_observable, bindableBoolean);
      view.setOnCheckedChangeListener(
          (group, checkedId) -> bindableBoolean.set(checkedId == group.getChildAt(0).getId()));
    }
    Boolean newValue = bindableBoolean.get();
    ((RadioButton) view.getChildAt(newValue ? 0 : 1)).setChecked(true);
  }

  @BindingAdapter(value = { "observableString", "setPos" }, requireAll = false)
  public static void bindSpinner(Spinner view, final ObservableString observableString,
      final boolean setPos) {
    if (view.getTag(R.id.bound_observable) != observableString) {
      view.setTag(R.id.bound_observable, observableString);
      view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          if (setPos) {
            observableString.set(String.valueOf(position));
            return;
          }
          observableString.set(parent.getItemAtPosition(position).toString());
        }

        @Override public void onNothingSelected(AdapterView<?> parent) {

        }
      });
    }
  }

  @BindingAdapter({ "app:onClick" })
  public static void bindOnClick(View view, final Runnable runnable) {
    view.setOnClickListener(v -> runnable.run());
  }
}
