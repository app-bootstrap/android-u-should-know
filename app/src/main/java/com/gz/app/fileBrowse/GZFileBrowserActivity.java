package com.gz.app.fileBrowse;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gz.android_utils.GZApplication;
import com.gz.android_utils.R;
import com.gz.android_utils.concurrency.loop.GZCommonTaskLoop;
import com.gz.android_utils.concurrency.loop.GZUILoop;
import com.gz.android_utils.demo.GZBrowserViewItem;
import com.gz.android_utils.misc.log.GZAppLogger;
import com.gz.android_utils.misc.utils.GZPathManager;
import com.gz.android_utils.ui.popup.GZPopup;
import com.gz.android_utils.ui.recycleview.GZRecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * created by Zhao Yue, at 4/10/16 - 8:32 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZFileBrowserActivity extends AppCompatActivity implements GZBrowserViewItem.onItemClickListener {

    private String path;
    private List<GZBrowserViewItem> fileUnits;
    private GZRecyclerViewAdapter adapter;
    private GZFileBrowserLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_file_dir_layout);
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setTitle("File browser");
        Drawable drawable = getResources().getDrawable(android.R.drawable.ic_input_delete);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        setupRecycleView();

        GZCommonTaskLoop.getInstance().post(new Runnable() {
            @Override
            public void run() {
                fileUnits = new ArrayList<>();

                Intent intent = getIntent();
                path = intent.getStringExtra("folder_path");

                if (path == null) {
                    path = Environment.getExternalStorageDirectory().getAbsolutePath();
                }

                final File[] files = GZPathManager.sharedInstance().browseFilesInDir(new File(path));

                for (File file : files) {
                    GZBrowserViewItem.FileUnit unit = new GZBrowserViewItem.FileUnit();
                    unit.isDir = file.isDirectory();
                    unit.filePath = file.getAbsolutePath();
                    unit.size = file.getTotalSpace();
                    unit.name = file.getName();
                    GZBrowserViewItem item = new GZBrowserViewItem(unit);
                    item.listener = GZFileBrowserActivity.this;
                    fileUnits.add(item);
                    GZAppLogger.i("Check about file name :%s  with size %d", unit.name, unit.size);
                }

                // After initialization, finish the current configuration with the corresponding recycler view
                GZUILoop.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = (TextView) findViewById(R.id.filePathIndicator);
                        textView.setText(path);
                        adapter.updateRecyclerViewItems(fileUnits);
                    }
                });
            }
        });
    }

    private void setupRecycleView() {

        GZFileBrowserRecyclerView recycleView = (GZFileBrowserRecyclerView) findViewById(R.id.file_dir_recycler_view);
        adapter = new GZRecyclerViewAdapter();
        adapter.configForBuilder(new GZRecyclerViewAdapter.GZRecycleViewHolderBuilder() {
            @Override
            public GZRecyclerViewAdapter.GZRecyclerViewHolder buildViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = (LayoutInflater) GZApplication.sharedInstance().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );

                if (viewType == 0) {
                    View view = inflater.inflate(R.layout.home_file_dir_folder_unit, parent, false);
                    GZBrowserViewItem.ViewHolder viewHolder = new GZBrowserViewItem.ViewHolder(view);
                    return viewHolder;
                } else {
                    View view = inflater.inflate(R.layout.home_file_dir_file_unit, parent, false);
                    GZBrowserViewItem.ViewHolder viewHolder = new GZBrowserViewItem.ViewHolder(view);
                    return viewHolder;
                }
            }
        });

        layoutManager = new GZFileBrowserLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(GZBrowserViewItem.FileUnit fileUnit) {
        if (!fileUnit.isDir) {
            return;
        }

        Intent intent = new Intent(GZFileBrowserActivity.this, GZFileBrowserActivity.class);
        intent.putExtra("folder_path",fileUnit.filePath);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(GZBrowserViewItem.FileUnit fileUnit) {

        if (fileUnit!= null && fileUnit.isDir) {
            View view = getLayoutInflater().inflate(R.layout.home_performance_pop, null);
            TextView content = (TextView) view.findViewById(R.id.performance_popup_content);
            content.setText(fileUnit.getDescription());
            GZPopup.show(view, GZFileBrowserActivity.this, "Dir Info");
            return true;
        }

        return false;
    }
}
