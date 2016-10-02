package com.gz.android_utils.misc.utils;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.WindowManager;
import com.gz.android_utils.GZApplication;
import com.gz.android_utils.misc.log.GZAppLogger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

/**
 * created by Zhao Yue, at 27/9/16 - 6:22 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZResourceManager {
    private static Resources mAppResource;

    private static void __checkResource() {
        if (mAppResource == null) {
            mAppResource = GZApplication.sharedInstance().getResources();
        }
    }

    public static int color(int colorId) {
        __checkResource();
        return mAppResource.getColor(colorId);
    }

    public static boolean bool(int rId) {
        __checkResource();
        return mAppResource.getBoolean(rId);
    }

    public static float dimension(int fontId) {
        __checkResource();
        return mAppResource.getDimension(fontId);
    }

    public static String string(int stringId) {
        __checkResource();
        return mAppResource.getString(stringId);
    }

    public static String string(int stringId, Object... formatArgs) {
        __checkResource();
        return mAppResource.getString(stringId, formatArgs);
    }

    public static Drawable drawable(int resourceId) {
        __checkResource();
        return mAppResource.getDrawable(resourceId);
    }

    public static Drawable drawable(String source) {
        __checkResource();
        int resId = mAppResource.getIdentifier(source, "drawable", GZApplication.sharedInstance().getPackageName());
        return drawable(resId);
    }


    public static int getDrawableId(String source) {
        __checkResource();
        return mAppResource.getIdentifier(source, "drawable", GZApplication.sharedInstance().getPackageName());
    }

    public static int getInt(int resourceId) {
        __checkResource();
        return mAppResource.getInteger(resourceId);
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) GZApplication.sharedInstance().getSystemService(Application.WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            return size.x;
        }

        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) GZApplication.sharedInstance().getSystemService(Application.WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            return size.y;
        }

        return wm.getDefaultDisplay().getHeight();
    }

    public static Locale getLanguageLocale() {
        __checkResource();
        android.content.res.Configuration conf = mAppResource.getConfiguration();
        return conf.locale;
    }

    public static String[] stringArray(int stringArrayId) {
        __checkResource();
        return mAppResource.getStringArray(stringArrayId);
    }

    public static int[] intArray(int colorResId) {
        __checkResource();
        return mAppResource.getIntArray(colorResId);
    }

    public static Bitmap bitmap(int resourceId) {
        __checkResource();
        return BitmapFactory.decodeResource(mAppResource, resourceId);
    }

    public static Drawable drawable(Bitmap bitmap) {
        __checkResource();
        return new BitmapDrawable(mAppResource, bitmap);
    }

    public static String loadRawString(int resId) {
        try {
            InputStream is = GZApplication.sharedInstance().getResources().openRawResource(resId);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                if (is != null)
                    is.close();
            }
            return writer.toString();
        } catch (IOException e) {
            GZAppLogger.e("error", e.getMessage());
        }
        return "";
    }

    public static Typeface getAppFonts() {
        Typeface typeFace=Typeface.createFromAsset(GZApplication.sharedInstance().getAssets(),"fonts/Gill_Sans.ttf");
        return typeFace;
    }
}