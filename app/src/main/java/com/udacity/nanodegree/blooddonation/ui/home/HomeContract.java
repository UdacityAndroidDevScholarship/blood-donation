package com.udacity.nanodegree.blooddonation.ui.home;

import com.udacity.nanodegree.blooddonation.base.BaseMvpPresenter;
import com.udacity.nanodegree.blooddonation.base.BaseView;

public class HomeContract {

    interface Presenter extends BaseMvpPresenter<View>{
        void checkView();
    }
    public interface  View extends BaseView{
        void checkData();
    }
}
