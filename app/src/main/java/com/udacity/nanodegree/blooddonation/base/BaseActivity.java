package com.udacity.nanodegree.blooddonation.base;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.udacity.nanodegree.blooddonation.ui.login.view.UserLoginActivity;

/**
 * Created by riteshksingh on Apr, 2018
 */
abstract public class BaseActivity extends AppCompatActivity {

  protected ViewDataBinding mBinding = null;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected void onDestroy() {
    mBinding = null;
    super.onDestroy();
  }

  protected void showSnackBar(String message) {
    showSnackBar(message, findViewById(android.R.id.content));
  }

  protected void showSnackBar(String message, View view) {
    Snackbar snackBar =
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
    View sbView = snackBar.getView();
    TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
    textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
    snackBar.show();
  }

  protected void setTitle(String title) {
    if (title == null) return;
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(title);
    }
  }

  protected void logout() {
    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
      FirebaseAuth.getInstance().signOut();
      Intent loginActivity = new Intent(this, UserLoginActivity.class);
      startActivity(loginActivity);
      finish();
    }
  }
}
