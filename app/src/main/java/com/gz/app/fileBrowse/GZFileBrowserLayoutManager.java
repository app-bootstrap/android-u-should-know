package com.gz.app.fileBrowse;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * created by Zhao Yue, at 7/10/16 - 1:49 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZFileBrowserLayoutManager extends StaggeredGridLayoutManager {

    public GZFileBrowserLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public GZFileBrowserLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }


}
