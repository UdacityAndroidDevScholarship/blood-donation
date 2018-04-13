package com.udacity.nanodegree.blooddonation.ui.login.presenter;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.udacity.nanodegree.blooddonation.ui.login.LoginActivityContract;
import com.udacity.nanodegree.blooddonation.ui.login.LoginInfo;
import com.udacity.nanodegree.blooddonation.util.Util;


/**
 * Created by riteshksingh on Apr, 2018
 */
public class LoginActivityPresenter implements LoginActivityContract
        .Presenter {

    private static final String TAG = LoginActivityPresenter.class.getName();

    private LoginActivityContract.View mView;


    private FirebaseAuth mFirebaseAuth;

    public LoginActivityPresenter(LoginActivityContract.View view,
            FirebaseAuth firebaseAuth) {
        mView = view;
        mFirebaseAuth = firebaseAuth;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onLoginClick(LoginInfo loginInfo) {
        String email = loginInfo.email.get();
        String password = loginInfo.password.get();

        if (email == null || password == null) {
            // TODO -> error toast or message
            return;
        }

        if (Util.isValidEmail(email) && Util.isValidPassword(password)) {
            doLogin(email, password);
        } else {
            // TODO -> error toast or message
        }
    }

    private void doLogin(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            mView.loginSuccess();
                        }else{
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            mView.loginFailed();
                        }
                    }
                });
    }
}
