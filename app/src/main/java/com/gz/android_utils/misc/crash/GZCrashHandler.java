package com.gz.android_utils.misc.crash;

import com.gz.android_utils.misc.log.GZAppLogger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * created by Zhao Yue, at 24/9/16 - 5:17 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZCrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler _defaultHandler;
    public static final String CRASH_FILE_NAME = "crash_log";
    public static final String EXCEPTION_FILE_NAME_PREFIX = "exception_log";
    private boolean _enabled = true;

    public void install() {
        _defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            if (!_enabled) {
                if (_defaultHandler != null) {
                    _defaultHandler.uncaughtException(thread, ex);
                } else {
                    GZAppLogger.e("uncaught exception:%s", ex.toString());
                }
                return;
            }
            GZAppLogger.e("Catch %s exception", ex.getClass().getSimpleName());

            // Generate and send crash report
            __handleException(ex);
        } catch (Throwable fatality) {
            if (_defaultHandler != null) {
                _defaultHandler.uncaughtException(thread, ex);
            }
        }
    }

    public void uninstall() {
        Thread.setDefaultUncaughtExceptionHandler(_defaultHandler);
    }

    public void enableHandler(boolean isEnabled) {
        _enabled = isEnabled;
    }

    private void __handleException(Throwable e) {
        if (e == null) {
            return;
        }
        try {
            String log = getStackTrace(e);
            String fileId = __generateFileId(log);
            writeToLog(fileId, log);
        } catch (Exception e1) {
            GZAppLogger.e(e1);
        }

        //don't restart the app to avoid duplicated crashes
        System.exit(2);
        //TODO: Application restart
//        BeeTalkApplication.restartApp(true);
    }

    protected static String getStackTrace(Throwable th) {

        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);

        Throwable cause = th;
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        final String stacktraceAsString = result.toString();
        printWriter.close();
        return stacktraceAsString;
    }

    // TODO Log writing
    protected static void writeToLog(String fileId, String log) {
        //ensure the crash folder exists
//        String path = BBPathManager.getInstance().getCrashReportPath(fileId);
//        BBCrashLogInfo logInfo = new BBCrashLogInfo();
//        logInfo.setUserInfo(BBMyInfoManager.getInstance().getName(), BBMyInfoManager.getInstance().getId());
//        logInfo.setCrashInfo(log);
//        logInfo.setDeviceInfo(BBBrandHack.getInstance().dumpThis());
//        final String sInfo = logInfo.toString();
//        byte[] data = BBConvertHelper.stringToUtf8Bytes(sInfo);
//        BBFileManager.getInstance().writeToDisk(path, data, data.length);
    }

    private static String __generateFileId(String log) {
        return CRASH_FILE_NAME;
    }


}
