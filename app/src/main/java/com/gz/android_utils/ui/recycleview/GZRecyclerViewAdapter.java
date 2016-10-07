package com.gz.android_utils.ui.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Zhao Yue, at 7/10/16 - 12:05 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZRecyclerViewAdapter extends RecyclerView.Adapter<GZRecyclerViewAdapter.GZRecyclerViewHolder> {

    public static class GZRecyclerViewHolder extends RecyclerView.ViewHolder {
        public GZRecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static abstract class GZRecycleViewHolderBuilder {
        public abstract GZRecyclerViewAdapter.GZRecyclerViewHolder buildViewHolder(ViewGroup parent, int viewType);
    }

    public static abstract class GZRecyclerViewItem {

        protected long getItemId() {
            return 0;
        }

        protected abstract void onContentViewUpdate(GZRecyclerViewAdapter.GZRecyclerViewHolder t);

        protected abstract void onItemClicked();

        protected int getItemViewType() {
            return 0;
        }
    }

    private List<? extends GZRecyclerViewItem> items = new ArrayList<>();
    private GZRecycleViewHolderBuilder builder;

    public void updateRecyclerViewItems(List<? extends GZRecyclerViewItem> items) {
        if (items == null)  {
            return;
        }

        this.items = items;
        this.notifyDataSetChanged();
    }

    public void configForBuilder(GZRecycleViewHolderBuilder builder) {
        this.builder = builder;
    }

    @Override
    public GZRecyclerViewAdapter.GZRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (builder != null) {
            return builder.buildViewHolder(parent, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(GZRecyclerViewAdapter.GZRecyclerViewHolder holder, int position) {
        this.items.get(position).onContentViewUpdate(holder);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).getItemViewType();
    }

    @Override
    public long getItemId(int position) {
        return this.items.get(position).getItemId();
    }
}