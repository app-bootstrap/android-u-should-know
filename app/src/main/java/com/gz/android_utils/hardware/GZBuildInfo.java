package com.gz.android_utils.hardware;

import android.os.Build;

import java.util.HashMap;
import java.util.Map;

/**
 * created by Zhao Yue, at 27/9/16 - 5:30 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZBuildInfo {
    private static Map<String ,String> buildInfo;

    public static Map<String, String> getBuildInfo() {
        if (buildInfo == null) {
            synchronized (GZBuildInfo.class) {
                buildInfo = new HashMap<>();

                buildInfo.put("Serial",Build.SERIAL);
                buildInfo.put("MODEL",Build.MODEL);
                buildInfo.put("Manufacuture",Build.MANUFACTURER);
                buildInfo.put("Brand",Build.BRAND);
                buildInfo.put("Type",Build.TYPE);
                buildInfo.put("User",Build.USER);
                buildInfo.put("Base", Build.VERSION_CODES.BASE+"");
                buildInfo.put("SDK",Build.VERSION.SDK+"");
                buildInfo.put("FingerPrint",Build.FINGERPRINT);
            }
        }

        return buildInfo;
    }

    public static String getBuildInfoDesc(boolean needLineEnd) {
        getBuildInfo();
        StringBuilder builder = new StringBuilder();
        for (String key : buildInfo.keySet()) {
            builder.append(key).append(":").append(buildInfo.get(key)).append(needLineEnd? "\n":",");
        }

        return builder.toString();
    }
}
