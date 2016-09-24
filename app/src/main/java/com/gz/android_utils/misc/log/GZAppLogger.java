package com.gz.android_utils.misc.log;

import android.util.Log;

import com.gz.android_utils.misc.utils.GZThreadHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * created by Zhao Yue, at 24/9/16 - 3:33 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZAppLogger {

    private static String APP_LOG_FLAG = "";

    public static void e(String format, Object... args) {
        if (!GZAppLoggerConfig.NO_LOG) {
            Log.e(APP_LOG_FLAG, __generateLineInfo() + GZThreadHelper.getThreadInfo() + String
                    .format(format, args));
        }
        String ss = String.format(format, args);
        if (ss.contains("UnknownFormatConversionException")) {
            GZAppLogger.i("OK");
        }
    }

    public static void w(String format, Object... args) {
        if (!GZAppLoggerConfig.NO_LOG) {
            Log.w(APP_LOG_FLAG, __generateLineInfo() + GZThreadHelper.getThreadInfo() + String
                    .format(format, args));
        }
        String ss = String.format(format, args);
        if (ss.contains("UnknownFormatConversionException")) {
            GZAppLogger.i("OK");
        }
    }

    public static void d(String format, Object... args) {
        if (!GZAppLoggerConfig.NO_LOG) {
            Log.d(APP_LOG_FLAG, __generateLineInfo() + GZThreadHelper.getThreadInfo() + String
                    .format(format, args));
        }
        String ss = String.format(format, args);
        if (ss.contains("UnknownFormatConversionException")) {
            GZAppLogger.i("OK");
        }
    }

    public static void display(String format, Object... args) {
        if (!GZAppLoggerConfig.NO_LOG) {
            Log.w(APP_LOG_FLAG, GZThreadHelper.getThreadInfo() + String.format(format, args));
        }
    }

    public static void i(String format, Object... args) {
        if (GZAppLoggerConfig.NO_LOG) {
            return;
        }
        Log.i(APP_LOG_FLAG, GZAppLoggerConfig.RELEASE_VERSION ? "" : __generateLineInfo() + String.format(format, args));
    }

    public static void e(Throwable e) {

        // If there is a exception and the config is open, write down the exception stack track and upload
        if (GZAppLoggerConfig.DEBUG_SHOW) {
            //TODO exception reporting logic
            return;
        }

        if (GZAppLoggerConfig.NO_LOG) {
            return;
        }

        StackTraceElement[] stackTraceElement = e.getStackTrace();
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (stackTraceElement[i].getMethodName().compareTo("e") == 0) {
                currentIndex = i + 1;
                break;
            }
        }

        if (currentIndex >= 0) {
            String fullClassName = stackTraceElement[currentIndex].getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = stackTraceElement[currentIndex].getMethodName();
            String lineNumber = String.valueOf(stackTraceElement[currentIndex].getLineNumber());
            Log.e(APP_LOG_FLAG + " position",
                    "at " + fullClassName + "." + methodName + "(" + className + ".java:" + lineNumber + ")");
        } else {
            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);
            e.printStackTrace(printWriter);
            Log.e(APP_LOG_FLAG, result.toString());
        }
    }

    private static String __generateLineInfo() {
        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (!stackTraceElement[i].getClassName().equals(GZAppLogger.class.getName())) {
                continue;
            }

            String method = stackTraceElement[i].getMethodName();

            if (method.equals("e") || method.equals("w") || method.equals("i") || method.equals("d")) {
                currentIndex = i + 1;
                break;
            }
        }
        if (currentIndex == -1) {
            Log.i(APP_LOG_FLAG, "CANNOT GENERATE DEBUG");
            return "";
        }
        StackTraceElement traceElement = stackTraceElement[currentIndex];
        String fullClassName = traceElement.getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        return traceElement.getMethodName() + "(" + className + ".java:" + traceElement
                .getLineNumber() + "): ";
    }
}
