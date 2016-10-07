package com.gz.app.fileBrowse;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.gz.android_utils.ui.recycleview.GZRecycleView;

/**
 * created by Zhao Yue, at 7/10/16 - 1:42 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZFileBrowserRecyclerView extends GZRecycleView{
    public GZFileBrowserRecyclerView(Context context) {
        super(context);
    }

    public GZFileBrowserRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GZFileBrowserRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
