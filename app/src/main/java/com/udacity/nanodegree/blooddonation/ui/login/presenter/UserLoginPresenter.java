package com.udacity.nanodegree.blooddonation.ui.login.presenter;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.constants.FireBaseConstants;
import com.udacity.nanodegree.blooddonation.constants.SharedPrefConstants;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.storage.SharedPreferenceManager;
import com.udacity.nanodegree.blooddonation.ui.login.UserLoginContract;
import com.udacity.nanodegree.blooddonation.util.NetworkUtil;
import com.udacity.nanodegree.blooddonation.util.Util;
import timber.log.Timber;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserLoginPresenter implements UserLoginContract.Presenter {

  private static final int COUNT_DOWN = 60;
  private FirebaseDatabase mFirebaseDatabase;
  private WeakReference<UserLoginContract.View> mView;
  private String mVerificationId;
  private PhoneAuthProvider.ForceResendingToken mResendToken;
  private String mPhoneNumber;
  private boolean isVerificationInProgress = false;
  private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
  private FirebaseAuth mFireBaseAuth;
  private SharedPreferenceManager mSharedPreferenceManager;
  private CountDownTimer countdownTimer;

  public UserLoginPresenter(FirebaseAuth firebaseAuth, FirebaseDatabase firebaseDatabase,
      SharedPreferenceManager sharedPreferenceManager, UserLoginContract.View view) {
    mFirebaseDatabase = firebaseDatabase;
    mView = new WeakReference<>(view);
    mFireBaseAuth = firebaseAuth;
    mSharedPreferenceManager = sharedPreferenceManager;
  }

  @Override public void onCreate() {
    createCallBack();
  }

  @Override
  public void onStart() {

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
        mView.get().setProceedProgressVisibility(false);

        if (e instanceof FirebaseAuthInvalidCredentialsException) {
          mView.get().generalResponse(R.string.msg_invalid_phone_number);
          mView.get().showPhoneNumberLayout();
          Timber.e(e);
        } else if (e instanceof FirebaseTooManyRequestsException) {
          mView.get().generalResponse(R.string.msg_sms_verification_limit_exceeded);
          mView.get().showPhoneNumberLayout();
          Timber.e(e);
        } else {
          mView.get().generalResponse(R.string.msg_encountered_an_unexpected_error);
          Timber.e(e);
        }
      }

      @Override
      public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
        Timber.d("CodeSent -> Yes");

        mVerificationId = verificationId;
        mResendToken = token;

        mView.get().setProceedProgressVisibility(false);
        mView.get().showVerifyOtpLayout();

        startCountdown();
      }

      @Override public void onCodeAutoRetrievalTimeOut(String s) {
        super.onCodeAutoRetrievalTimeOut(s);
        isVerificationInProgress = false;
      }
    };
  }

  private void startCountdown() {
    mView.get().setVerifyScreenPhoneNumber(mPhoneNumber);
    mView.get().setResendButtonEnabled(false);
    countdownTimer = new CountDownTimer(COUNT_DOWN * 1000, 1000) {
      @Override public void onTick(long millisUntilFinished) {
        mView.get().setResendButtonTimerCount(millisUntilFinished / 1000);
      }

      @Override public void onFinish() {
        mView.get().setResendButtonEnabled(true);
      }
    }.start();
  }

  private void fetchUser() {
    DatabaseReference userDetailDbReference = mFirebaseDatabase.getReference()
        .child(FireBaseConstants.USERS)
        .child(mFireBaseAuth.getUid());

    userDetailDbReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        mView.get().setVerifyProgressVisibility(false);
        User user = null;
        if (dataSnapshot.exists()) {
          user = dataSnapshot.getValue(User.class);
        }
        onSignInSuccess(user);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        mView.get().setVerifyProgressVisibility(false);
        mView.get().showVerifyOtpLayout();
        mView.get().generalResponse(R.string.msg_encountered_an_unexpected_error);

        Timber.e(databaseError.getMessage());
      }
    });
  }

  private void onSignInSuccess(User user) {

    if (Util.isValidUser(user) == 0) {
      mSharedPreferenceManager.put(SharedPrefConstants.IS_USER_DETAILS_ENTERED, true);
      mView.get().launchHomeScreen();
    } else {
      mView.get().launchUserDetailsScreen();
    }
  }

  private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
    mFireBaseAuth.signInWithCredential(credential)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              fetchUser();
            } else {
              mView.get().setVerifyProgressVisibility(false);
              if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                mView.get().generalResponse(R.string.msg_invalid_verification_code);
              } else {
                mView.get().generalResponse(R.string.msg_encountered_an_unexpected_error);
              }
            }
          }
        });
  }

  private void verifyPhoneNumberWithOTP(String otp) {
    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
    mView.get().setVerifyProgressVisibility(true);
    signInWithPhoneAuthCredential(credential);
  }

  private void verifyPhoneNumber(String phoneNumber) {
    if (!isVerificationInProgress) {

      mView.get().setProceedProgressVisibility(true);

      mPhoneNumber = phoneNumber;

      PhoneAuthProvider.getInstance()
          .verifyPhoneNumber(phoneNumber, COUNT_DOWN, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,
              mCallbacks, mResendToken);

      isVerificationInProgress = true;
    } else {
      mView.get().generalResponse(R.string.msg_verification_already_in_progress);
    }
  }

  @Override public void onProceedButtonClick(String phoneNumber, String phoneCode) {

    if (NetworkUtil.isOnline()) {
      if (Util.isValidPhoneNumber(phoneNumber) && !TextUtils.isEmpty(phoneCode)) {
        verifyPhoneNumber(Util.getPhoneNumberWithPlus(phoneNumber, phoneCode));
      } else {
        mView.get().generalResponse(R.string.msg_invalid_phone_number);
      }
    } else {
      mView.get().generalResponse(R.string.msg_no_internet_connection);
    }
  }

  @Override public void onVerifyOtpButtonClick(String otp) {
    if (!TextUtils.isEmpty(otp)) {
      verifyPhoneNumberWithOTP(otp);
    } else {
      mView.get().generalResponse(R.string.msg_invalid_otp);
    }
  }

  @Override public void onResendCodeButtonClick() {
    mView.get().generalResponse(R.string.msg_otp_has_been_sent);
    verifyPhoneNumber(mPhoneNumber);
  }

  @Override public void onEditPhoneNumberClick() {
    mView.get().showEditPhoneDialog();
  }

  @Override public void onEditPhoneNumberActionYes() {
    isVerificationInProgress = false;
    if (countdownTimer != null) {
      countdownTimer.cancel();
    }
    mView.get().showPhoneNumberLayout();
  }
}
