package com.udacity.nanodegree.blooddonation.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

/**
 * Created by BloodyBadboy on Jan, 2017.
 */

public class CircularMorphLayout extends FrameLayout implements Animatable {

  public static final Property<CircularMorphLayout, Float> CLIP_CIRCLE_RADIUS_PROGRESS =
      new Property<CircularMorphLayout, Float>(Float.class, "clipCircleRadius") {
        @Override
        public Float get(CircularMorphLayout view) {
          return view.getClipCircleRadius();
        }

        @Override
        public void set(CircularMorphLayout object, Float value) {
          object.setClipCircleRadius(value);
        }
      };
  private static final Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
  private static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

  private Path clipPath = new Path();

  private float centerX = 0;
  private float centerY = 0;
  private float startRadius = 0f;
  private float endRadius = 0f;

  private float clipCircleRadius = 0f;
  private long duration = 400;
  private boolean isRunning = false;
  private ObjectAnimator clipCircleRadiusAnimator;
  private CallBacks listener;
  private boolean isDirty = true;

  public CircularMorphLayout(Context context) {
    super(context);
  }

  public CircularMorphLayout(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public CircularMorphLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public CircularMorphLayout revealFrom(float centerX, float centerY, float startRadius,
      float endRadius) {
    this.centerX = centerX;
    this.centerY = centerY;
    this.startRadius = startRadius + 10; // to ensure the reverse animation fills the width
    this.endRadius = endRadius;

    clipCircleRadiusAnimator =
        ObjectAnimator.ofFloat(CircularMorphLayout.this, CLIP_CIRCLE_RADIUS_PROGRESS, startRadius,
            this.endRadius);

    isDirty = false;
    return this;
  }

  public CircularMorphLayout setListener(CallBacks listener) {
    this.listener = listener;
    return this;
  }

  public CircularMorphLayout setDuration(long duration) {
    this.duration = duration;
    return this;
  }

  @Override protected void dispatchDraw(Canvas canvas) {
    if (isDirty) {
      super.dispatchDraw(canvas);
      return;
    }
    canvas.save();

    clipPath.reset();
    clipPath.moveTo(centerX, centerY);
    clipPath.addCircle(centerX, centerY, clipCircleRadius, Path.Direction.CW);
    clipPath.close();

    canvas.clipPath(clipPath);

    super.dispatchDraw(canvas);

    canvas.restore();
  }

  public synchronized float getClipCircleRadius() {
    return clipCircleRadius;
  }

  public synchronized void setClipCircleRadius(float clipCircleRadius) {
    this.clipCircleRadius = clipCircleRadius;
    invalidate();
  }

  public void reverse() {

    if (isRunning()) {
      stop();
    }
    clipCircleRadiusAnimator = null;
    clipCircleRadiusAnimator =
        ObjectAnimator.ofFloat(CircularMorphLayout.this, CLIP_CIRCLE_RADIUS_PROGRESS, endRadius,
            startRadius);
    clipCircleRadiusAnimator.setInterpolator(DECELERATE_INTERPOLATOR);
    clipCircleRadiusAnimator.setDuration(duration);
    clipCircleRadiusAnimator.start();
  }

  @Override public void start() {
    if (isRunning()) {
      return;
    }
    isRunning = true;

    clipCircleRadiusAnimator.setInterpolator(ACCELERATE_INTERPOLATOR);
    clipCircleRadiusAnimator.setDuration(duration);
    clipCircleRadiusAnimator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        if (listener != null) {
          listener.onEnd();
        }
      }
    });
    clipCircleRadiusAnimator.start();
  }

  @Override public void stop() {
    if (!isRunning()) {
      return;
    }
    isRunning = false;
    clipCircleRadiusAnimator.cancel();
  }

  @Override public boolean isRunning() {
    return isRunning;
  }

  public interface CallBacks {
    void onEnd();
  }
}
