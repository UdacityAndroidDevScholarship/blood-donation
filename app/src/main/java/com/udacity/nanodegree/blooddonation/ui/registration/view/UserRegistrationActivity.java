package com.udacity.nanodegree.blooddonation.ui.registration.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.udacity.nanodegree.blooddonation.databinding.ActivityUserRegisBinding;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.registration.UserRegistrationContract;
import com.udacity.nanodegree.blooddonation.ui.registration.UserRegistrationInfo;
import com.udacity.nanodegree.blooddonation.ui.registration.presenter.UserRegistrationPresenter;
import java.util.concurrent.TimeUnit;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserRegistrationActivity extends BaseActivity
    implements UserRegistrationContract.View {

  private static final String TAG = UserRegistrationActivity.class.getName();
  private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
  private boolean isVerificationInProgress = false;

  private String mVerificationId;

  private UserRegistrationContract.Presenter mPresenter;

  private UserRegistrationInfo userRegistrationInfo;

  private FirebaseAuth mFirebaseAuth;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_regis);

    mFirebaseAuth = Injection.getFirebaseAuth();

    mPresenter = new UserRegistrationPresenter(this);

    userRegistrationInfo = new UserRegistrationInfo();
    userRegistrationInfo.isCodeSent.set(false);

    ((ActivityUserRegisBinding) mBinding).setRegisInfo(userRegistrationInfo);
    ((ActivityUserRegisBinding) mBinding).setPresenter(mPresenter);

    createCallBack();
  }

  private void createCallBack() {
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
      @Override public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        isVerificationInProgress = false;
        Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
        signInWithPhoneAuthCredential(phoneAuthCredential);
      }

      @Override public void onVerificationFailed(FirebaseException e) {
        isVerificationInProgress = false;
        Log.w(TAG, "onVerificationFailed", e);

        if (e instanceof FirebaseAuthInvalidCredentialsException) {
          showNotValidPhoneNumberMessage();
        } else if (e instanceof FirebaseTooManyRequestsException) {
          showLimitExceededMessage();
        }
      }

      @Override public void onCodeSent(String verificationId,
          PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        super.onCodeSent(verificationId, forceResendingToken);

        Log.d(TAG, "onCodeSent:" + verificationId);

        getSupportActionBar().setTitle(R.string.verification);
        userRegistrationInfo.isCodeSent.set(true);

        mVerificationId = verificationId;
      }
    };
  }

  private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
    mFirebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              Log.d(TAG, "signInWithCredential:success");
              Intent intent = new Intent(UserRegistrationActivity.this, HomeActivity.class);
              startActivity(intent);
            } else {
              // Sign in failed, display a message and update the UI
              Log.w(TAG, "signInWithCredential:failure", task.getException());
              if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(UserRegistrationActivity.this, "Invalid verification code",
                    Toast.LENGTH_SHORT).show();
              }
            }
          }
        });
  }

  private void showLimitExceededMessage() {
    Toast.makeText(this, "Message Limit Exceeded!!", Toast.LENGTH_SHORT).show();
  }

  @Override public void signIn() {
    PhoneAuthCredential credential =
        PhoneAuthProvider.getCredential(mVerificationId, userRegistrationInfo.otp.get());
    signInWithPhoneAuthCredential(credential);
  }

  @Override public void verifyPhoneNumber() {
    // TODO -> Take country code
    if (!isVerificationInProgress) {
      PhoneAuthProvider.getInstance()
          .verifyPhoneNumber("+91" + userRegistrationInfo.phoneNumber.get(), 60, TimeUnit.SECONDS,
              this, mCallbacks);

      isVerificationInProgress = true;
    }
  }

  @Override public void showNotValidPhoneNumberMessage() {
    Toast.makeText(this, "No a valid phone number", Toast.LENGTH_SHORT).show();
  }
}
