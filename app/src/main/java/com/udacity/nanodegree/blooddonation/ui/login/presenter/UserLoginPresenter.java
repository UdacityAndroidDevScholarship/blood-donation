package com.udacity.nanodegree.blooddonation.ui.login.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.udacity.nanodegree.blooddonation.ui.login.UserLoginContract;
import com.udacity.nanodegree.blooddonation.util.Util;
import java.util.concurrent.TimeUnit;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserLoginPresenter implements UserLoginContract.Presenter {

  private UserLoginContract.View mView;
  private String mVerificationId;

  private boolean isVerificationInProgress = false;
  private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

  private FirebaseAuth mFireBaseAuth;

  public UserLoginPresenter(FirebaseAuth firebaseAuth, UserLoginContract.View view) {
    mView = view;
    mFireBaseAuth = firebaseAuth;
  }

  @Override public void onCreate() {
    createCallBack();
  }

  @Override public void onStart() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {

  }

  private void createCallBack() {
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
      @Override public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        isVerificationInProgress = false;
        signInWithPhoneAuthCredential(phoneAuthCredential);
      }

      @Override public void onVerificationFailed(FirebaseException e) {
        isVerificationInProgress = false;
        mView.showHideLoader(false);
        if (e instanceof FirebaseAuthInvalidCredentialsException) {
          mView.showNotValidPhoneNumberMessage();
        } else if (e instanceof FirebaseTooManyRequestsException) {
          mView.showLimitExceededMessage();
        }
      }

      @Override public void onCodeSent(String verificationId,
          PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        super.onCodeSent(verificationId, forceResendingToken);
        mView.setVerificationTitleBar();
        mView.setUserRegisInfoIsCodeFlag(true);
        mVerificationId = verificationId;
        mView.showHideLoader(false);
      }
    };
  }

  private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
    mFireBaseAuth.signInWithCredential(credential)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              mView.showHideLoader(false);
              mView.onSignInSuccess();
            } else {
              mView.showHideLoader(false);
              if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                mView.showInvalidVerificationCodeMessage();
              }
            }
          }
        });
  }

  public void signIn(String otp) {
    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
    mView.showHideLoader(true);
    signInWithPhoneAuthCredential(credential);
  }

  public void verifyPhoneNumber(String phoneNumber) {
    if (!isVerificationInProgress) {
      mView.showHideLoader(true);
      PhoneAuthProvider.getInstance()
          .verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,
              mCallbacks);

      isVerificationInProgress = true;
    }
  }

  @Override public void onIamInButtonClick(String phoneNumber, String phoneCode) {
    if (Util.isValidPhoneNumber(phoneNumber) && !TextUtils.isEmpty(phoneCode)) {
      verifyPhoneNumber(Util.getPhoneNumberWithPlus(phoneNumber,phoneCode));
    } else {
      mView.showNotValidPhoneNumberMessage();
    }
  }

  @Override public void onVerifyOtpButtonClick(String otp) {
    if (!TextUtils.isEmpty(otp)) {
      signIn(otp);
    }
  }
}
