package com.udacity.nanodegree.blooddonation.base;

/**
 * Created by kautilya on 11-04-2018.
 */

/**
 * Each Presenter must implement this as this mostly contains the connection
 *
 * @param <V> View for the presenter
 */
public interface BaseMvpPresenter<V extends BaseView> {
    /**
     * called when view is attached to presenter
     * @param view
     */
    void attach(V view);

    /**
     * called when view is detached from presenter
     */
    void detach();

    /**
     * method to check if the view is attached or not
     * @return true if attached
     */
    boolean isAttached();

    /**
     * Used to generate any data before the presenter is called
     */
    void init();
}