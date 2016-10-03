package com.gz.android_utils.misc.utils;

import android.widget.Toast;

import com.gz.android_utils.GZApplication;
import com.gz.android_utils.concurrency.loop.GZUILoop;

/**
 * created by Zhao Yue, at 3/10/16 - 10:18 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZToastManager {

    public static void show(final String msg) {
        GZUILoop.getInstance().post(new Runnable() {
            @Override
            public void run() {
                Toast newToast = Toast.makeText(GZApplication.sharedInstance(), msg, Toast.LENGTH_LONG);
                newToast.show();
            }
        });
    }

    public static void showShort(final String msg) {
        GZUILoop.getInstance().post(new Runnable() {
            @Override
            public void run() {
                Toast newToast = Toast.makeText(GZApplication.sharedInstance(), msg, Toast.LENGTH_SHORT);
                newToast.show();
            }
        });
    }

    public static void showShort(int resId) {
        showShort(GZResourceManager.string(resId));
    }

    public static void show(int resId) {
        show(GZResourceManager.string(resId));
    }
}
