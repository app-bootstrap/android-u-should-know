package com.gz.android_utils.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gz.android_utils.GZApplication;
import com.gz.android_utils.R;
import com.gz.android_utils.ui.listview.GZListViewBaseItem;

/**
 * created by Zhao Yue, at 23/9/16 - 4:02 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZDemiListViewItem extends GZListViewBaseItem {

    /*GZDemoListView item Data structure provided in the structure */
    public static class GZDemoListViewItemData {
        private String title;
        private String desc;
        private Bitmap bitmap;
    }

    /*Provide simple optimization on holder pattern */
    private static class GZDemoListViewItemHolder {
        private TextView titleView;
        private TextView descriptionView;
        private ImageView rowIcon;
    }

    public GZDemoListViewItemData data;

    @Override
    protected long getItemId() {
        return super.getItemId();
    }

    @NonNull
    @Override
    protected View generateContentView() {
        // Inflater logic
        LayoutInflater inflater = (LayoutInflater) GZApplication.sharedInstance().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );;
        View view =  inflater.inflate(R.layout.home_demo_list_row, null);

        // View holder design pattern
        GZDemoListViewItemHolder holder = new GZDemoListViewItemHolder();
        holder.titleView = (TextView) view.findViewById(R.id.demo_list_view_row_title);
        holder.descriptionView = (TextView) view.findViewById(R.id.demo_list_view_row_desc);
        view.setTag(holder);

        return view;
    }

    @Override
    protected void onContentViewUpdate(View t) {
        GZDemoListViewItemHolder holder = (GZDemoListViewItemHolder) t.getTag();
        holder.titleView.setText(data.title);
        holder.descriptionView.setText(data.desc);
        //TODO provide bitmap processor & cache
    }
}
