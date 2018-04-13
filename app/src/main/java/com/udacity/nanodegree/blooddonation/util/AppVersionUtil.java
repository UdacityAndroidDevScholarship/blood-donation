package com.udacity.nanodegree.blooddonation.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.Locale;

public final class AppVersionUtil {

    private AppVersionUtil() {
        throw new AssertionError();
    }

    public static String getAppVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "N/A";
        int versionCode = 0;
        try {
            packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return String.format(Locale.US, "Version %s(%d)", versionName, versionCode);
    }
}