package com.udacity.nanodegree.blooddonation.ui.home;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/04/2018.
 */
public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }


    @Override
    public void onAddClicked() {
        view.generalInfo(0);
    }

    @Override
    public void onCurrentLocationClicked() {
        view.updateCamera(null);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
