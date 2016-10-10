package com.gz.app.Cache;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gz.android_utils.R;

/**
 * created by Zhao Yue, at 10/10/16 - 11:31 AM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZUserPreferenceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_user_pref_demo);
        getSupportActionBar().setTitle("User Pref");
        Drawable drawable = getResources().getDrawable(android.R.drawable.ic_input_delete);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
    }
}
