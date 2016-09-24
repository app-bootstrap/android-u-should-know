package com.gz.android_utils.misc.utils;

import java.util.Locale;

/**
 * created by Zhao Yue, at 24/9/16 - 3:49 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZThreadHelper {
    public static String getThreadInfo () {
        return String.format(Locale.getDefault(),"[thread_id:%d name=%s]", Thread.currentThread().getId(), Thread.currentThread().getName());
    }
}
