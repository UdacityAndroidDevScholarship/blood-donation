package com.udacity.nanodegree.blooddonation.ui.login

import android.os.Bundle
import com.udacity.nanodegree.blooddonation.R
import com.udacity.nanodegree.blooddonation.base.BaseActivity
import com.udacity.nanodegree.blooddonation.ui.login.presenter.LoginActivityPresenter

/**
 * Created by riteshksingh on Apr, 2018
 */
class LoginActivity : BaseActivity() {

    private lateinit var loginActivityPresenter: LoginActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginActivityPresenter = LoginActivityPresenter()
    }
}