package com.gz.android_utils.concurrency.loop;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * created by Zhao Yue, at 22/9/16 - 8:54 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZBaseLoop {
    private ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();

    public void post(Runnable runnable) {
        mExecutor.execute(runnable);
    }

    public void cancel(Future future) {
        if (future != null && !future.isCancelled() && !future.isDone()) {
            future.cancel(true);
        }
    }

    public Future delayPost(Runnable call, int nMillSec) {
        return mExecutor.schedule(call, nMillSec, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        mExecutor.shutdown();
    }
}
