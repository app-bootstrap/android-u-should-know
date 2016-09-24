package com.gz.android_utils.misc.utils;

import android.os.SystemClock;

import com.gz.android_utils.misc.log.GZAppLogger;

/**
 * created by Zhao Yue, at 24/9/16 - 9:07 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZPerformanceTicker {

    private long m_startTick;
    private String mTickerName;
    private long mDuration = 0;

    public GZPerformanceTicker(String app) {
        start(app);
    }

    public void start(String tickerName) {
        mTickerName = tickerName;
        m_startTick = SystemClock.elapsedRealtime();
    }

    public void stop() {
        Long n = SystemClock.elapsedRealtime() - m_startTick;
        GZAppLogger.w("<%s> using:%s", mTickerName, n.toString());
        accumulate(n);
    }

    public void clean() {
        mDuration = 0;
    }

    public void accumulate(long delta) {
        mDuration = mDuration + delta;
    }

    public long read() {
        return mDuration;
    }
}
