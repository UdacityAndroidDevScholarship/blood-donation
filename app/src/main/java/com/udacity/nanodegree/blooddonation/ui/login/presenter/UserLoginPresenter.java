package com.udacity.nanodegree.blooddonation.ui.login.presenter;

import android.os.CountDownTimer;
import android.text.TextUtils;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.constants.FireBaseConstants;
import com.udacity.nanodegree.blooddonation.constants.SharedPrefConstants;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.storage.SharedPreferenceManager;
import com.udacity.nanodegree.blooddonation.ui.login.UserLoginContract;
import com.udacity.nanodegree.blooddonation.util.Util;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

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

    @Override
    public void onCreate() {
        createCallBack();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if (countdownTimer != null) {
            countdownTimer.cancel();
            Timber.d("On Destroy -> Timer Cancelled");
        }
    }

    private void createCallBack() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                isVerificationInProgress = false;
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                isVerificationInProgress = false;
                mView.get().showHideLoader(false);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    mView.get().generalResponse(R.string.msg_invalid_phone);
                    mView.get().toggleVerificationRegistrationScreen(true);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    mView.get().generalResponse(R.string.msg_something_went_wrong);
                    mView.get().toggleVerificationRegistrationScreen(true);
                    Timber.e(e);
                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                Timber.d("CodeSent -> Yes");
                if (mResendToken != null) {
                    mView.get().toggleVerificationRegistrationScreen(false);
                }
                mView.get().setVerificationTitleBar();
                mView.get().setUserRegisInfoIsCodeFlag(true);
                mVerificationId = verificationId;
                mResendToken = token;
                mView.get().showHideLoader(false);

                startCountdown();
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                isVerificationInProgress = false;
            }
        };
    }

    private void startCountdown() {
        mView.get().setPhoneNumber(mPhoneNumber);
        mView.get().showCountdown(true);
        countdownTimer = new CountDownTimer(COUNT_DOWN * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mView.get().setTimerCount(millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                mView.get().showCountdown(false);
            }
        }.start();
    }

    private void fetchUser() {
        DatabaseReference userDetailDabaseReference = mFirebaseDatabase.getReference().child(FireBaseConstants.USERS).child(mFireBaseAuth.getUid());
        userDetailDabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mView.get().showHideLoader(false);
                User user = null;
                if (dataSnapshot.exists())
                    user = dataSnapshot.getValue(User.class);
                onSignInSuccess(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.get().showHideLoader(false);
                mView.get().generalResponse(R.string.msg_something_went_wrong);
                mView.get().toggleVerificationRegistrationScreen(true);
                Timber.e(databaseError.getMessage());
            }
        });
    }

    private void onSignInSuccess(User user) {

        if (Util.isValidUser(user) == 0) {
            mSharedPreferenceManager.put(SharedPrefConstants.IS_USER_DETAILS_ENTERED, true);
            mView.get().launchHomeScreen();
        } else
            mView.get().launchUserDetailsScreen();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mFireBaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        fetchUser();
                    } else {
                        mView.get().showHideLoader(false);
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            mView.get().generalResponse(R.string.msg_invalid_code);
                    }
                });
    }

    public void signIn(String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
        mView.get().showHideLoader(true);
        signInWithPhoneAuthCredential(credential);
    }

    public void verifyPhoneNumber(String phoneNumber) {
        if (!isVerificationInProgress) {
            mView.get().toggleVerificationRegistrationScreen(null);
            mView.get().showHideLoader(true);
            mPhoneNumber = phoneNumber;

            PhoneAuthProvider.getInstance()
                    .verifyPhoneNumber(phoneNumber, COUNT_DOWN, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,
                            mCallbacks, mResendToken);

            isVerificationInProgress = true;
        }
    }

    @Override
    public void onIamInButtonClick(String phoneNumber, String phoneCode) {
        if (Util.isValidPhoneNumber(phoneNumber) && !TextUtils.isEmpty(phoneCode)) {
            verifyPhoneNumber(Util.getPhoneNumberWithPlus(phoneNumber, phoneCode));
        } else {
            mView.get().generalResponse(R.string.msg_invalid_phone);
            mView.get().toggleVerificationRegistrationScreen(true);
        }
    }

    @Override
    public void onVerifyOtpButtonClick(String otp) {

        if (!TextUtils.isEmpty(otp)) {
            signIn(otp);
        } else mView.get().generalResponse(R.string.msg_invalid_otp);
    }

    @Override
    public void onResendCodeButtonClick() {
        verifyPhoneNumber(mPhoneNumber);
    }
}
