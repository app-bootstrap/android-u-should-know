package com.gz.app.Cache;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gz.android_utils.R;

/**
 * created by Zhao Yue, at 10/10/16 - 11:31 AM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZUserPreferenceActivity extends AppCompatActivity {

    private Button setUidButton;
    private Button saveButton;
    private Button clearContents;

    private EditText userIDField;
    private EditText contentKeyField;
    private EditText preferenceContentField;

    private TextView preferenceDisplayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_user_pref_demo);
        getSupportActionBar().setTitle("User Pref");
        Drawable drawable = getResources().getDrawable(android.R.drawable.ic_input_delete);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable);

        setUidButton = (Button) findViewById(R.id.set_id_button);
        saveButton = (Button) findViewById(R.id.set_content_button);
        clearContents = (Button) findViewById(R.id.remove_content_button);

        setUidButton.setOnClickListener(onUIDChange);
        saveButton.setOnClickListener(onPrefSave);
        clearContents.setOnClickListener(onPrefRemove);

        preferenceDisplayer = (TextView) findViewById(R.id.logFile);

        userIDField = (EditText) findViewById(R.id.user_id_field);
        contentKeyField = (EditText) findViewById(R.id.content_header_field);
        preferenceContentField = (EditText) findViewById(R.id.content_field);
    }

    private void updatePreferenceList () {

    }

    private View.OnClickListener onUIDChange = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener onPrefSave = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener onPrefRemove = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

}
