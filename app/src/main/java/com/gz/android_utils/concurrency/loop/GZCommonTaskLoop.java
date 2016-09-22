package com.gz.android_utils.concurrency.loop;

/**
 * created by Zhao Yue, at 22/9/16 - 9:04 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZCommonTaskLoop {

    private static GZCommonTaskLoop mInstance = new GZCommonTaskLoop();

    public static GZCommonTaskLoop getInstance() {
        return mInstance;
    }

    private GZBaseLoop mLoop;

    private GZCommonTaskLoop() {
        mLoop = new GZBaseLoop();
    }

    public void post(Runnable runnable) {
        GZThreadRunnable threadRunnable = new GZThreadRunnable(runnable);
        mLoop.post(threadRunnable);
    }

    public void delayPost(Runnable runnable, int nMillSec) {
        GZThreadRunnable threadRunnable = new GZThreadRunnable(runnable);
        mLoop.delayPost(threadRunnable, nMillSec);
    }
}
