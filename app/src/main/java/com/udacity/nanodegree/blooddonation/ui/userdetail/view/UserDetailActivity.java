package com.udacity.nanodegree.blooddonation.ui.userdetail.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocomplete.IntentBuilder;
import com.udacity.nanodegree.blooddonation.R;
import com.udacity.nanodegree.blooddonation.base.BaseActivity;
import com.udacity.nanodegree.blooddonation.common.picker.DatePickerFragment;
import com.udacity.nanodegree.blooddonation.databinding.ActivityUserDetailsBinding;
import com.udacity.nanodegree.blooddonation.injection.Injection;
import com.udacity.nanodegree.blooddonation.ui.home.view.HomeActivity;
import com.udacity.nanodegree.blooddonation.ui.login.model.UserPhoneNumber;
import com.udacity.nanodegree.blooddonation.ui.userdetail.UserDetailContract;
import com.udacity.nanodegree.blooddonation.ui.userdetail.model.UserDetail;
import com.udacity.nanodegree.blooddonation.ui.userdetail.presenter.UserDetailPresenter;

import static com.udacity.nanodegree.blooddonation.constants.Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class UserDetailActivity extends BaseActivity implements UserDetailContract.View {

  private UserDetailContract.Presenter mPresenter;
  private ActivityUserDetailsBinding mActivityUserDetailsBinding;
  private UserDetail mUserDetail;
  private AutocompleteFilter.Builder autocompleteFilterBuilderCity;

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    mPresenter.handleActivityResult(this, requestCode, resultCode, data);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter =
        new UserDetailPresenter(this, Injection.provideFireBaseAuth(),
            Injection.getSharedPreference(),
            Injection.providesDataRepo());
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);
    mActivityUserDetailsBinding = (ActivityUserDetailsBinding) mBinding;
    mUserDetail = new UserDetail();

    mActivityUserDetailsBinding.setPresenter(mPresenter);
    mActivityUserDetailsBinding.setUserDetail(mUserDetail);

    autocompleteFilterBuilderCity =
        new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES);

    if (getIntent() != null && getIntent().hasExtra(UserPhoneNumber.EXTRA_USER_PHONE_NUMBER)) {
      UserPhoneNumber userPhoneNumber =
          getIntent().getParcelableExtra(UserPhoneNumber.EXTRA_USER_PHONE_NUMBER);
      mUserDetail.getPhoneNumber()
          .set(String.format("+%s-%s", userPhoneNumber.getPhoneCode(),
              userPhoneNumber.getPhoneNumber()));
      mUserDetail.getCountry().set(userPhoneNumber.getCountryName());
      mActivityUserDetailsBinding.tietUserDetailsCountry.setText(userPhoneNumber.getCountryName());

      autocompleteFilterBuilderCity.setCountry(userPhoneNumber.getIso());
    }

    mPresenter.onCreate();
  }

  @Override
  protected void onStart() {
    super.onStart();
    mPresenter.onStart();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mPresenter.onStop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }

  @Override public void showDatePickerDialog() {
    DatePickerFragment datePickerFragment =
        DatePickerFragment.newInstance(mUserDetail.getBirthdayInMillis());
    datePickerFragment.show(getSupportFragmentManager(), "datePickerFragment");
  }

  @Override public void startCityPickerActivity() {
    try {
      Intent placeAutoCompleteIntent = new IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
          .setFilter(autocompleteFilterBuilderCity.build())
          .build(this);

      startActivityForResult(placeAutoCompleteIntent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
      e.printStackTrace();
    }
  }

  @Override public void setUserDetailCityAndState(String city, String state) {
    mUserDetail.getCity().set(city);
    mUserDetail.getState().set(state);
  }

  @Override public void showTextInputError(int resId, int message) {

    TextInputLayout textInputLayout = findViewById(resId);

    textInputLayout.setErrorEnabled(true);
    textInputLayout.setError(getString(message));
  }

  @Override public void setCreateAccountProgressVisibility(boolean isVisible) {
    if (isVisible) {
      mActivityUserDetailsBinding.cmlCreateAccount.revealFrom(mActivityUserDetailsBinding.tvCreateAccount.getWidth() / 2f,
          mActivityUserDetailsBinding.tvCreateAccount.getHeight() / 2f,
          mActivityUserDetailsBinding.tvCreateAccount.getWidth() / 2f,
          mActivityUserDetailsBinding.tvCreateAccount.getHeight() / 2f).setListener(
          () -> {
            mActivityUserDetailsBinding.tvCreateAccount.setVisibility(View.GONE);
            mActivityUserDetailsBinding.pbCreateAccount.setVisibility(View.VISIBLE);
          }).start();
    } else {
      mActivityUserDetailsBinding.tvCreateAccount.setVisibility(View.VISIBLE);
      mActivityUserDetailsBinding.pbCreateAccount.setVisibility(View.GONE);
      mActivityUserDetailsBinding.cmlCreateAccount.reverse();
    }
  }

  @Override public void clearAllTextInputErrors() {
    mActivityUserDetailsBinding.tilUserDetailsFullName.setErrorEnabled(false);
    mActivityUserDetailsBinding.tilUserDetailsFullName.setError(null);

    mActivityUserDetailsBinding.tilUserDetailsBirthday.setErrorEnabled(false);
    mActivityUserDetailsBinding.tilUserDetailsBirthday.setError(null);

    mActivityUserDetailsBinding.tilUserDetailsEmail.setErrorEnabled(false);
    mActivityUserDetailsBinding.tilUserDetailsEmail.setError(null);

    mActivityUserDetailsBinding.tilUserDetailsAddress.setErrorEnabled(false);
    mActivityUserDetailsBinding.tilUserDetailsAddress.setError(null);

    mActivityUserDetailsBinding.tilUserDetailsCity.setErrorEnabled(false);
    mActivityUserDetailsBinding.tilUserDetailsCity.setError(null);

    mActivityUserDetailsBinding.tilUserDetailsState.setErrorEnabled(false);
    mActivityUserDetailsBinding.tilUserDetailsState.setError(null);
  }

  @Override
  public void launchHomeScreen() {
    Intent intent = new Intent(this, HomeActivity.class);
    finish();
    startActivity(intent);
  }

  @Override
  public void generalResponse(int responseId) {
    showSnackBar(getString(responseId), findViewById(R.id.coordinator_layout));
  }
}
