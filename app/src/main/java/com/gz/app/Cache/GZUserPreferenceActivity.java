package com.gz.app.Cache;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gz.android_utils.GZApplication;
import com.gz.android_utils.R;
import com.gz.android_utils.cache.GZBaseSharePreference;
import com.gz.android_utils.ui.GABaseActivity;

/**
 * created by Zhao Yue, at 10/10/16 - 11:31 AM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZUserPreferenceActivity extends GABaseActivity {

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

        updatePreferenceList();
    }

    private void updatePreferenceList() {
        preferenceDisplayer.setText(GZApplication.ApplicationUserIdentifier + "\n" + GZBaseSharePreference.sharedInstance().getDescription());
    }

    private View.OnClickListener onUIDChange = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GZBaseSharePreference.sharedInstance().reset();
            GZApplication.ApplicationUserIdentifier = userIDField.getText().toString();
            updatePreferenceList();
        }
    };

    private View.OnClickListener onPrefSave = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String contentKey = contentKeyField.getText().toString();
            String content = preferenceContentField.getText().toString();

            if (content != null && contentKey != null) {
                GZBaseSharePreference.sharedInstance()._setString(contentKey, content);
                updatePreferenceList();
                contentKeyField.getText().clear();
                preferenceContentField.getText().clear();
            }
        }
    };

    private View.OnClickListener onPrefRemove = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GZBaseSharePreference.sharedInstance()._clear();
            updatePreferenceList();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
