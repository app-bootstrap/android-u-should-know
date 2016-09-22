package com.gz.android_utils.concurrency.loop;

import android.os.Handler;
import android.os.Message;

/**
 * created by Zhao Yue, at 22/9/16 - 10:22 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZUILoop {
    private static GZUILoop mInstance;

    public static GZUILoop getInstance() {
        if (mInstance == null) {
            mInstance = new GZUILoop();
        }
        return mInstance;
    }

    private GZUILoop() {
    }

    private Handler m_uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Runnable info = (Runnable) msg.obj;
            if (info != null) {
                info.run();
            } else {
//                TODO: add logger event
//                BBAppLogger.e("UI callback is null");
            }
        }
    };

    public void init() {
//        BBAppLogger.i("BBUILoop init");
    }

    public void post(Runnable call) {
        m_uiHandler.post(call);
    }

    public void delayPost(Runnable r, int delayMillis) {
        m_uiHandler.postDelayed(r, delayMillis);
    }

    public void cancelPost(Runnable r) {
        m_uiHandler.removeCallbacks(r);
    }
}
