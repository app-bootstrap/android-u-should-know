package com.gz.android_utils.ui.recycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * created by Zhao Yue, at 7/10/16 - 11:11 AM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZRecycleView extends RecyclerView{

    // Default constructor method
    public GZRecycleView(Context context) {
        super(context);
    }

    public GZRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GZRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // Check about customizable options
    // 1. Recycler.Adapter          - > Effective Source Control
    // 2. Recycler.LayoutManager    - > Animation Optimization & Performance Imrpovements



}
