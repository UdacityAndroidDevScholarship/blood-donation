package com.udacity.nanodegree.blooddonation.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by riteshksingh on Apr, 2018
 */
public abstract class BaseActivity<T extends BaseMvpPresenter, K extends ViewDataBinding> extends AppCompatActivity implements BaseView {


    T mPresenter;
    K mDataBinder;
    private Unbinder binder;


    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = PresenterFactory.getPresenter(this);
        mPresenter.attach(this);
        mPresenter.init();
        beforeView(savedInstanceState);
        mDataBinder = DataBindingUtil.setContentView(this, getContentResource());
        binder = ButterKnife.bind(this);

        init(savedInstanceState);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        //mPresenter.detach();
    }

    public T getPresenter() {
        return mPresenter;
    }

    public K getDataBinder() {
        return mDataBinder;
    }


    /**
     * Layout resource to inflate
     *
     * @return layout resource
     */
    @LayoutRes
    protected abstract int getContentResource();

    protected abstract void init(@Nullable Bundle savedInstanceState);


    protected abstract void beforeView(@Nullable Bundle savedInstanceState);

    protected class SubView {
        public SubView(@LayoutRes int view, Context context) {
            ButterKnife.bind(this, LayoutInflater.from(context).inflate(view, null));
        }
    }
}