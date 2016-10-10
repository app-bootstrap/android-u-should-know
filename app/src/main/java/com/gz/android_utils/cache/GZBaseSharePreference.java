package com.gz.android_utils.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.gz.android_utils.GZApplication;

import java.util.Map;

/**
 * created by Zhao Yue, at 2/10/16 - 9:06 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZBaseSharePreference {
    protected SharedPreferences m_settings;

    private static GZBaseSharePreference m_instance;
    public static GZBaseSharePreference sharedInstance() {
        if (m_instance == null) {
            synchronized (GZBaseSharePreference.class) {
                m_instance = new GZBaseSharePreference();
                m_instance.check();
            }
        }
        return m_instance;
    }

    public void check() {
        if (m_settings == null) {
            Context context = GZApplication.sharedInstance().getApplicationContext();
            m_settings = context.getSharedPreferences(GZApplication.ApplicationUserIdentifier, Context.MODE_PRIVATE);
        }
    }

    public void reset(){
        m_settings = null;
        check();
    }

    public void _unset(String tag) {
        SharedPreferences.Editor editor = m_settings.edit();
        editor.remove(tag);
        editor.apply();
    }

    public boolean _getBoolean(String tag, boolean b) {
        return m_settings.getBoolean(tag, b);
    }

    public void _setBoolean(String tag, boolean b) {
        SharedPreferences.Editor editor = m_settings.edit();
        editor.putBoolean(tag, b);
        editor.apply();
    }

    public float _getFloat(String tag, float def) {
        return m_settings.getFloat(tag, def);
    }

    public void _setFloat(String tag, float f) {
        SharedPreferences.Editor editor = m_settings.edit();
        editor.putFloat(tag, f);
        editor.apply();
    }

    public String _getString(String tag, String defaultStr) {
        return m_settings.getString(tag, defaultStr);
    }

    public void _setString(String tag, String valueStr) {
        SharedPreferences.Editor editor = m_settings.edit();
        editor.putString(tag, valueStr);
        editor.apply();
    }

    public int _getInt(String tag, int defaultVal) {
        return m_settings.getInt(tag, defaultVal);
    }


    public void _setInt(String tag, int val) {
        SharedPreferences.Editor editor = m_settings.edit();
        editor.putInt(tag, val);
        editor.apply();
    }

    public long _getLong(String tag, long defaultVal) {
        return m_settings.getLong(tag, defaultVal);
    }

    public void _setLong(String tag, long val) {
        SharedPreferences.Editor editor = m_settings.edit();
        editor.putLong(tag, val);
        editor.apply();
    }

    public void _remove(String tag) {
        SharedPreferences.Editor editor = m_settings.edit();
        editor.remove(tag);
        editor.apply();
    }

    public void _clear() {
        SharedPreferences.Editor editor = m_settings.edit();
        editor.clear();
        editor.apply();
    }

    public String getDescription() {
        StringBuilder builder = new StringBuilder();
        Map<String, ?> allEntries = m_settings.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            builder.append(entry.getKey()).append(":").append(entry.getValue().toString()).append("\n");
        }

        return builder.toString();
    }
}
