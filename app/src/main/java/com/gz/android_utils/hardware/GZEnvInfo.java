package com.gz.android_utils.hardware;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.gz.android_utils.GZApplication;

/**
 * created by Zhao Yue, at 24/9/16 - 9:22 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZEnvInfo {

    public final static String GOOGLE_MAP_APP = "com.google.android.apps.maps";

    public final static String GOOGLE_PLAY_SERVICE = "com.google.vending";

    public final static String GOOGLE_PLAY_SERVICE_LEGACY = "com.google.market";

    public final static String GOOGLE_ACCOUNT_TYPE = "com.google";

    public static boolean isGoogleMapsInstalled() {
        try {
            GZApplication.sharedInstance().getPackageManager().getPackageInfo(GOOGLE_MAP_APP, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isGooglePlayServiceInstalled() {
        int statusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(GZApplication.sharedInstance());
        return statusCode == ConnectionResult.SUCCESS || statusCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED;
    }

    public static boolean isGoogleGMSAvaliable() {
        int statusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(GZApplication.sharedInstance());
        if (ConnectionResult.SUCCESS == statusCode || ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED == statusCode) {
            AccountManager manager = (AccountManager) GZApplication.sharedInstance().getSystemService(Context.ACCOUNT_SERVICE);
            if (manager != null) {
                Account[] list = manager.getAccountsByType(GOOGLE_ACCOUNT_TYPE);
                if (list != null && list.length > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isPackageAvailable(String packageName) {
        PackageInfo info = null;
        try {
            info = GZApplication.sharedInstance().getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return info != null;
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    //API LEVEL 11
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    //API LEVEL 12
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasIcecreamSandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean hasIcecreamSandwichMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    //API LEVEL 18
    public static boolean hasJellyBean2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    //API LEVEL 19
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    //API LEVEL 20
    public static boolean hasKitKatWatch() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;
    }

    //API LEVEL 21
    public static boolean hasLOLLIPOP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    //API LEVEL 22
    public static boolean hasLOLLIPOP_SugarCoating() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    //API LEVEL 23
    public static boolean hasM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
