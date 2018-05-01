package com.udacity.nanodegree.blooddonation.base;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.udacity.nanodegree.blooddonation.ui.login.view.UserLoginActivity;

/**
 * Created by riteshksingh on Apr, 2018
 */
abstract public class BaseActivity extends AppCompatActivity {

    protected ViewDataBinding mBinding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        mBinding = null;
        super.onDestroy();
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
