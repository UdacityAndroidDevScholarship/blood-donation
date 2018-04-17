package com.udacity.nanodegree.blooddonation.util.timber;

import android.util.Log;
import timber.log.Timber;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class ReleaseTree extends Timber.Tree {

  private static final int MAX_LOG_LENGTH = 4000;

  @Override protected boolean isLoggable(String tag, int priority) {
    if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) return false;

    // Only log war, error and wtf
    return true;
  }

  @Override protected void log(int priority, String tag, String message, Throwable t) {
    if (isLoggable(tag, priority)) {

      // Report caught exception to crashlytics library
      if (priority == Log.ERROR && t!=null){
        // Crashlytics.log(e)
      }

      // Message is short enough, does not need to be broken into chunks
      if (message.length() < MAX_LOG_LENGTH) {
        if (priority == Log.ASSERT) {
          Log.wtf(tag, message);
        } else {
          Log.println(priority, tag, message);
        }
        return;
      }

      // Split by line, and ensure each line fits into maximum length
      for (int i = 0, length = message.length(); i < length; i++) {
        int newline = message.indexOf('\n', i);
        newline = newline != -1 ? newline : length;
        do {
          int end = Math.min(newline, i + MAX_LOG_LENGTH);
          String part = message.substring(i, end);
          if (priority == Log.ASSERT) {
            Log.wtf(tag, part);
          } else {
            Log.println(priority, tag, part);
          }
          i = end;
        } while (i < newline);
      }
    }
  }
}
