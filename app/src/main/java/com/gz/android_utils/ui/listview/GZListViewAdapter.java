package com.gz.android_utils.ui.listview;

import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gz.android_utils.concurrency.loop.GZUILoop;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Zhao Yue, at 22/9/16 - 6:40 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZListViewAdapter extends BaseAdapter {

    private List<GZListViewBaseItem> baseItems;

    /* Adapter control*/
    @Override
    public int getCount() {
        return baseItems.size();
    }

    @Override
    public Object getItem(int position) {
        return baseItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return baseItems.get(position).getItemId();
    }

    @Override
    public int getItemViewType(int position) {
        return baseItems.get(position).getItemViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (baseItems != null && baseItems.size() > 0) {

            GZListViewBaseItem item = baseItems.get(position);

            /* Generate content view */
            if (convertView == null) {
                convertView = item.generateContentView(parent);
            }

            /* Update view content */
            item.onContentViewUpdate(convertView);
        }

        return convertView;
    }

    /* Check adapter data management */
    public synchronized void updateData(List<? extends GZListViewBaseItem> items) {
        if (items != null) {
            baseItems = new ArrayList<>();
            for (GZListViewBaseItem item : items) {
                baseItems.add(item);
            }
        }

        notifyDataSetChanged();
    }

    public void clearData() {
        updateData(new ArrayList<GZListViewBaseItem>());
    }

    @Override
    public void notifyDataSetChanged() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            GZUILoop.getInstance().post(new Runnable() {
                @Override
                public void run() {
                    GZListViewAdapter.super.notifyDataSetChanged();
                }
            });
        } else {
            super.notifyDataSetChanged();
        }
    }
}
