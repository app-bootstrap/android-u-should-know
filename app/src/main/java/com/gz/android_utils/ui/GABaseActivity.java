package com.gz.android_utils.ui;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * created by Zhao Yue, at 10/10/16 - 3:38 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */

// Base class for actionbar enabled activity
// Support runtime permission check
public class GABaseActivity extends AppCompatActivity{

    public static final String RunTimePerssionCheck = "CheckPermissions";
    public static final int RESULT_PERMISSION_NOT_GRANTED = -1;

    private String[] permissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Drawable drawable = getResources().getDrawable(android.R.drawable.ic_input_delete);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable);

        // Check permissions
        permissions = getIntent().getStringArrayExtra(RunTimePerssionCheck);

        if (permissions != null) {
            ActivityCompat.requestPermissions(this, permissions, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0) {
            boolean allGranted = true;
            for(int i:grantResults){
                if(i!= PackageManager.PERMISSION_GRANTED){
                    allGranted = false;
                }
            }

            if(allGranted){
                permissions = null;
            } else {
                setResult(RESULT_PERMISSION_NOT_GRANTED);
                finish();
            }
        }
    }
}
