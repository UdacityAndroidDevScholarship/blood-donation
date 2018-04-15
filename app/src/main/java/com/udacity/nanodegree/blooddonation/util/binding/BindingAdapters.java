package com.udacity.nanodegree.blooddonation.util.binding;

import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
      view.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
          bindableBoolean.set(checkedId == group.getChildAt(0).getId());
        }
      });
    }
    Boolean newValue = bindableBoolean.get();
    ((RadioButton) view.getChildAt(newValue ? 0 : 1)).setChecked(true);
  }

  @BindingAdapter({ "app:onClick" })
  public static void bindOnClick(View view, final Runnable runnable) {
    view.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        runnable.run();
      }
    });
  }
}
