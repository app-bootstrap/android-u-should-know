package com.gz.android_utils.hardware;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.gz.android_utils.GZApplication;

/**
 * created by Zhao Yue, at 2/10/16 - 9:28 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZBatteryManager {

    /**
    *Intent - is a message passing mechanism between components of android, except for Content Provider. You can use intent to start any component.

     Sticky Intent - Sticks with android, for future broad cast listeners. For example if BATTERY_LOW event occurs then that intent will be stick with android so that if any future user requested for BATTER_LOW, it will be fired;

     Pending Intent - If you want some one to perform any Intent operation at future point of time on behalf of you, then we will use Pending Intent.
    * */

    private Intent m_intent;

    private static GZBatteryManager m_instance;

    public static GZBatteryManager sharedInstance() {
        if (m_instance == null) {
            m_instance = new GZBatteryManager();
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            m_instance.m_intent = GZApplication.sharedInstance().registerReceiver(null, ifilter);
        }

        return m_instance;
    }

    public boolean isCharging() {
        int status = m_intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        return isCharging;
    }

    public boolean isUsbCharging() {
        int chargePlug = m_intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        return usbCharge;
    }

    public boolean isAcCharging() {
        int chargePlug = m_intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        return acCharge;
    }

    public float getBatteryLevel() {
        int level = m_intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = m_intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;
        return batteryPct;
    }

    public String getBatteryInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("AC Charging : " + this.isAcCharging() + "\n").
                append("USB Charging: " + this.isUsbCharging() + "\n").
                append(("Charging: " + this.isCharging() + "\n")).
                append("Battery Level: " + this.getBatteryLevel());

        return builder.toString();
    }
}
