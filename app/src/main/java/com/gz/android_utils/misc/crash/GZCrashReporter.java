package com.gz.android_utils.misc.crash;

/**
 * created by Zhao Yue, at 24/9/16 - 5:55 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZCrashReporter {

    private static GZCrashReporter mInstance;

    public static GZCrashReporter sharedInstance() {
        if (mInstance == null) {
            mInstance = new GZCrashReporter();
        }
        return mInstance;
    }

    // Write down exception
    public void logException(Throwable e) {

    }

    // Upload exception
    public void reportException() {

    }
}