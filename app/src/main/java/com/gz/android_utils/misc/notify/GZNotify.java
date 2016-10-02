package com.gz.android_utils.misc.notify;

import com.gz.android_utils.misc.log.GZAppLogger;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * created by Zhao Yue, at 2/10/16 - 6:29 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZNotify {

    private static class GZNotifyItemInfo {
        WeakReference<GZNotifyItem> notifyItem;
        Object itemKey;

        public GZNotifyItemInfo(GZNotifyItem item) {
            notifyItem = new WeakReference<>(item);
            itemKey = null;
        }

        public GZNotifyItemInfo(GZNotifyItem item, Object key) {
            notifyItem = new WeakReference<>(item);
            itemKey = key;
        }

        public GZNotifyItem get() {
            return notifyItem.get();
        }
    }

    private List<GZNotifyItemInfo> m_notifyList;

    private String m_from;

    public GZNotify(String name) {
        m_from = name;
    }

    public void fire(Object param) {
        GZAppLogger.i("Fire the notification:%s", m_from);

        if (m_notifyList == null) {
            return;
        }

        Iterator<GZNotifyItemInfo> iterator = m_notifyList.iterator();
        while (iterator.hasNext()) {
            GZNotifyItemInfo itemInfo = iterator.next();
            GZNotifyItem item = itemInfo.get();
            if (item == null) {
                iterator.remove();
            } else {
                item.fire(param);
            }
        }
    }

    public void add(GZNotifyItem item) {
        GZNotifyItemInfo itemInfo = new GZNotifyItemInfo(item);
        if (m_notifyList == null) {
            m_notifyList = new ArrayList<>();
        }

        m_notifyList.add(itemInfo);
    }

    public boolean addOnce(GZNotifyItem item, Object key) {
        if (m_notifyList == null) {
            m_notifyList = new ArrayList<>();
        }

        boolean bExist = __isExists(key);
        if (bExist) {
            return false;
        }

        GZNotifyItemInfo nitem = new GZNotifyItemInfo(item, key);
        m_notifyList.add(nitem);
        return true;
    }

    public void remove(GZNotifyItem item) {
        if (m_notifyList == null || item == null) {
            return;
        }
        Iterator<GZNotifyItemInfo> iterator = m_notifyList.iterator();
        while (iterator.hasNext()) {
            GZNotifyItemInfo itemInfo = iterator.next();
            if (itemInfo.get() == null) {
                iterator.remove();
            } else {
                if (item == itemInfo.get()) {
                    GZAppLogger.i("notification removed:%s", m_from);
                    iterator.remove();
                    return;
                }
            }
        }
    }

    public boolean removeByKey(Object key) {
        Iterator<GZNotifyItemInfo> iterator = m_notifyList.iterator();
        while (iterator.hasNext()) {
            GZNotifyItemInfo item0 = iterator.next();
            if (item0.get() == null) {
                iterator.remove();
            } else {
                if (key.equals(item0.itemKey)) {
                    iterator.remove();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean __isExists(Object key) {
        Iterator<GZNotifyItemInfo> iterator = m_notifyList.iterator();
        while (iterator.hasNext()) {
            GZNotifyItemInfo item0 = iterator.next();
            if (item0.get() == null) {
                iterator.remove();
            } else {
                if (key.equals(item0.itemKey)) {
                    return true;
                }
            }
        }
        return false;
    }

}
