package com.udacity.nanodegree.blooddonation.util;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

@SuppressWarnings("unused") public class ViewMoveUpBehavior extends CoordinatorLayout.Behavior<View> {

  public ViewMoveUpBehavior(Context context, AttributeSet attrs) {
  }

  @Override
  public boolean layoutDependsOn(CoordinatorLayout parent, View child,
      View dependency) {

    return dependency instanceof Snackbar.SnackbarLayout;
  }

  @Override
  public boolean onDependentViewChanged(CoordinatorLayout parent, View child,
      View dependency) {

    float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
    child.setTranslationY(translationY);
    return true;
  }

  @Override
  public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
    ViewCompat.animate(child).translationY(0).start();
  }
}