package com.siddworks.assistantfortt2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nononsenseapps.filepicker.FilePickerActivity;
import com.siddworks.assistantfortt2.util.CommonUtil;
import com.siddworks.assistantfortt2.util.PrefUtils;
import com.siddworks.assistantfortt2.util.TTHelperUtil;

public class FileNotFoundActivity extends AppCompatActivity {

    private static final int FILE_CODE = 1331;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_not_found);

        TTHelperUtil.logEvent("ScreenFileNotFound");

        Button button = (Button) findViewById(R.id.browse_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker();
            }
        });
    }

    public void showPicker() {
        TTHelperUtil.logEvent("ButtonFileNotFoundPicker");
        // This always works
        Intent i = new Intent(this, FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(i, FILE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            // Do something with the URI
            String path = uri.getPath().toString();
            PrefUtils.setSaveFileLocation(path);
            CommonUtil.startActivity(this, WelcomeActivity.class);
            FileNotFoundActivity.this.finish();
        }
    }
}
