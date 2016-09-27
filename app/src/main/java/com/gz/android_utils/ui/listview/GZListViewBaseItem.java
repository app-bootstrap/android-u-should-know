package com.gz.android_utils.ui.listview;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * created by Zhao Yue, at 22/9/16 - 6:52 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public abstract class GZListViewBaseItem {

    protected long getItemId() {
        return 0;
    }

    /* Called to generate the content view */
    protected abstract @NonNull View generateContentView(ViewGroup parent);

    /* Called to update the content view display information */
    protected abstract void onContentViewUpdate(View t);

    /* Perform on item click */
    protected abstract void onItemClicked();
}
