package com.gz.android_utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        path = intent.getStringExtra("folder_path");

        GZCommonTaskLoop.getInstance().post(new Runnable() {
            @Override
            public void run() {
                fileUnits = new ArrayList<FileUnit>();

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
                    GZAppLogger.i("Check about file name :%s  with size %d",unit.name,unit.size);
                }
            }
        });
    }
}
