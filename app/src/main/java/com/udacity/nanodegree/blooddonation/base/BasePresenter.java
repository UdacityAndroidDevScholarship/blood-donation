package com.udacity.nanodegree.blooddonation.base;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class BasePresenter<V extends BaseView> implements BaseMvpPresenter<V> {

    private V view;


    @Override
    public void attach(V view) {
        this.view = view;
    }

    @Override
    public void detach() {
        view = null;
    }

    @Override
    public boolean isAttached() {
        return view != null;
    }

    @Override
    public void init() {
    }

    public V getView() {
        return view;
    }
}