package com.udacity.nanodegree.blooddonation.ui.login.presenter;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

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
import com.udacity.nanodegree.blooddonation.constants.SharedPrefConstants;
import com.udacity.nanodegree.blooddonation.storage.SharedPreferenceManager;
import com.udacity.nanodegree.blooddonation.ui.login.UserLoginContract;
import com.udacity.nanodegree.blooddonation.util.Util;

import java.util.concurrent.TimeUnit;
import timber.log.Timber;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserLoginPresenter implements UserLoginContract.Presenter {

  private UserLoginContract.View mView;
  private String mVerificationId;
  private PhoneAuthProvider.ForceResendingToken mResendToken;
  private String mPhoneNumber;

  private boolean isVerificationInProgress = false;
  private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

  private FirebaseAuth mFireBaseAuth;

  private SharedPreferenceManager mSharedPreferenceManager;
  private CountDownTimer countdownTimer;

  private static final int COUNT_DOWN = 60;

  public UserLoginPresenter(FirebaseAuth firebaseAuth,
      SharedPreferenceManager sharedPreferenceManager, UserLoginContract.View view) {
    mView = view;
    mFireBaseAuth = firebaseAuth;
    mSharedPreferenceManager = sharedPreferenceManager;
  }

  @Override public void onCreate() {
    createCallBack();
  }

  @Override public void onStart() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {
    if (countdownTimer != null) {
      countdownTimer.cancel();
      Timber.d("On Destroy -> Timer Cancelled");
    }
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

      @Override
      public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
        Timber.d("CodeSent -> Yes");
        if (mResendToken != null) {
          mView.showVerificationScreen(true);
        }
        mView.setVerificationTitleBar();
        mView.setUserRegisInfoIsCodeFlag(true);
        mVerificationId = verificationId;
        mResendToken = token;
        mView.showHideLoader(false);

        startCountdown();
      }

      @Override public void onCodeAutoRetrievalTimeOut(String s) {
        super.onCodeAutoRetrievalTimeOut(s);
        isVerificationInProgress = false;
      }
    };
  }

  private void startCountdown() {
    mView.setPhoneNumber(mPhoneNumber);
    mView.showCountdown(true);
    countdownTimer = new CountDownTimer(COUNT_DOWN * 1000, 1000) {
      @Override public void onTick(long millisUntilFinished) {
        mView.setTimerCount(millisUntilFinished / 1000);
      }

      @Override public void onFinish() {
        mView.showCountdown(false);
      }
    }.start();
  }

  private void onSignInSuccess() {
    if (mSharedPreferenceManager.getBoolean(SharedPrefConstants.IS_USER_DETAILS_ENTERED)) {
      mView.launchHomeScreen();
    } else {
      mView.launchUserDetailsScreen();
    }
  }

  private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
    mFireBaseAuth.signInWithCredential(credential)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              mView.showHideLoader(false);
              onSignInSuccess();
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
      mView.hidePhoneNumberScreen();
      mView.showHideLoader(true);
      mPhoneNumber = phoneNumber;

      PhoneAuthProvider.getInstance()
          .verifyPhoneNumber(phoneNumber, COUNT_DOWN, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,
              mCallbacks, mResendToken);

      isVerificationInProgress = true;
    }
  }

  @Override public void onIamInButtonClick(String phoneNumber, String phoneCode) {
    if (Util.isValidPhoneNumber(phoneNumber) && !TextUtils.isEmpty(phoneCode)) {
      verifyPhoneNumber(Util.getPhoneNumberWithPlus(phoneNumber, phoneCode));
    } else {
      mView.showNotValidPhoneNumberMessage();
    }
  }

  @Override public void onVerifyOtpButtonClick(String otp) {
    if (!TextUtils.isEmpty(otp)) {
      signIn(otp);
    }
  }

  @Override public void onResendCodeButtonClick() {
    verifyPhoneNumber(mPhoneNumber);
  }
}
