package com.udacity.nanodegree.blooddonation.base;

import android.support.annotation.StringRes;

public interface BaseView {

    /**
     * Method to display general response.
     *
     * @param responseId: String resource id of the message.
     */
    void generalResponse(@StringRes int responseId);
}
