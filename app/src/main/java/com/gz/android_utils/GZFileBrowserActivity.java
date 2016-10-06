package com.gz.android_utils;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gz.android_utils.concurrency.loop.GZCommonTaskLoop;
import com.gz.android_utils.misc.log.GZAppLogger;
import com.gz.android_utils.misc.utils.GZPathManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Zhao Yue, at 4/10/16 - 8:32 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZFileBrowserActivity extends AppCompatActivity {

    private String path;
    private List<FileUnit> fileUnits;

    private static class FileUnit {
        private String filePath;
        private boolean isDir;
        private long size;
        private String name;
    }

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

        GZCommonTaskLoop.getInstance().post(new Runnable() {
            @Override
            public void run() {
                fileUnits = new ArrayList<>();

                Intent intent = getIntent();
                path = intent.getStringExtra("folder_path");

                if (path == null) {
                    path = Environment.getExternalStorageDirectory().getAbsolutePath();
                }

                File[] files = GZPathManager.sharedInstance().browseFilesInDir(new File(path));

                for (File file : files) {
                    FileUnit unit = new FileUnit();
                    unit.isDir = file.isDirectory();
                    unit.filePath = file.getAbsolutePath();
                    unit.size = file.getTotalSpace();
                    unit.name = file.getName();

                    fileUnits.add(unit);
                    GZAppLogger.i("Check about file name :%s  with size %d", unit.name, unit.size);
                }

                // After initialization, finish the current configuration with the corresponding recycler view

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
