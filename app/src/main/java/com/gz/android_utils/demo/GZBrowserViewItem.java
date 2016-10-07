package com.gz.android_utils.demo;

import android.view.View;
import android.widget.TextView;

import com.gz.android_utils.R;
import com.gz.android_utils.misc.log.GZAppLogger;
import com.gz.android_utils.ui.recycleview.GZRecyclerViewAdapter;

/**
 * created by Zhao Yue, at 7/10/16 - 2:01 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZBrowserViewItem extends GZRecyclerViewAdapter.GZRecyclerViewItem {

    public interface onItemClickListener {
        public abstract void onItemClick(FileUnit fileUnit);
    }

    public static class FileUnit {
        public String filePath;
        public boolean isDir;
        public long size;
        public String name;
    }

    public static class ViewHolder extends GZRecyclerViewAdapter.GZRecyclerViewHolder {

        public TextView fileName;
        public TextView fileSize;
        public View container;

        public ViewHolder(View itemView) {
            super(itemView);

            fileName = (TextView) itemView.findViewById(R.id.folder_name);
            fileSize = (TextView) itemView.findViewById(R.id.folder_size);
            container = itemView;

            if (fileName == null) {
                fileName = (TextView) itemView.findViewById(R.id.file_name);
                fileSize = (TextView) itemView.findViewById(R.id.file_size);
            }
        }
    }

    public FileUnit fileUnit;
    public onItemClickListener listener;

    public GZBrowserViewItem(FileUnit fileUnit) {
        super();
        this.fileUnit = fileUnit;
    }

    @Override
    protected void onContentViewUpdate(GZRecyclerViewAdapter.GZRecyclerViewHolder t) {
        ViewHolder holder = (ViewHolder)t;
        holder.fileName.setText(fileUnit.name);
        holder.fileSize.setText(fileUnit.size + "Byte");
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(fileUnit);
                }
            }
        });
    }

    @Override
    protected void onItemClicked() {
        GZAppLogger.d("Logging event here");
    }

    @Override
    protected int getItemViewType() {
        return fileUnit.isDir?0:1;
    }
}
