package com.siddworks.assistantfortt2;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by sidd on 15/1/17.
 */
public class BaseActivity extends AppCompatActivity {
    boolean isLoggingEnabled = true;

    void init() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }
}
