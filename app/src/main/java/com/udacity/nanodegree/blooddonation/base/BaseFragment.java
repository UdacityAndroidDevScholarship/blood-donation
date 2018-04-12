package com.udacity.nanodegree.blooddonation.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by kautilya on 11-04-2018.
 */

public abstract class BaseFragment<T extends BaseMvpPresenter, K extends ViewDataBinding> extends Fragment implements BaseView {


    T mPresenter;
    K mDataBinder;
    private Unbinder binder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(getContentResource(), container, false);
        mDataBinder = DataBindingUtil.bind(view);
        binder = ButterKnife.bind(this, view);
        return view;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = injectPresenter();
        mPresenter.attach(this);
        init(view, savedInstanceState);
    }

    public T getPresenter() {
        return mPresenter;
    }

    public K getDataBinder() {
        return mDataBinder;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }

    /**
     * Layout resource to inflate
     *
     * @return layout resource
     */
    @LayoutRes
    protected abstract int getContentResource();


    protected abstract void init(View view, @Nullable Bundle savedInstanceState);


    protected abstract T injectPresenter();

    void displayText(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}