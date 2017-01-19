package com.siddworks.assistantfortt2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.siddworks.assistantfortt2.util.CommonUtil;
import com.siddworks.assistantfortt2.util.Log;
import com.siddworks.assistantfortt2.util.TTHelperUtil;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;
import pl.tajchert.nammu.PermissionListener;

public class PermissionDeniedActivity extends BaseActivity {

    private static final String TAG = "PermissionDeniedActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_denied);

        Nammu.init(getApplicationContext());
        TTHelperUtil.logEvent("ScreenPermissionDenied");

        Button button = (Button) findViewById(R.id.open_settings_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });
        checkPermissionAndReadSaveFile();
    }

    private void openSettings() {
        TTHelperUtil.logEvent("ButtonPermissionDeniedPicker");

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");

        Nammu.permissionCompare(new PermissionListener() {
            @Override
            public void permissionsChanged(String permissionRevoke) {
                //Toast is not needed as always either permissionsGranted() or permissionsRemoved() will be called
                //Toast.makeText(MainActivity.this, "Access revoked = " + permissionRevoke, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void permissionsGranted(String permissionGranted) {
                if(permissionGranted != null && permissionGranted.equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                    Log.d(TAG, "PermissionsManager.onGranted" );
                    CommonUtil.startActivity(PermissionDeniedActivity.this, WelcomeActivity.class);
                    PermissionDeniedActivity.this.finish();
                }
            }

            @Override
            public void permissionsRemoved(String permissionRemoved) {
            }
        });
    }

    private void checkPermissionAndReadSaveFile() {
        PermissionCallback permissionContactsCallback = new PermissionCallback() {
            @Override
            public void permissionGranted() {
                Log.d(TAG, "PermissionsManager.onGranted" );
                CommonUtil.startActivity(PermissionDeniedActivity.this, WelcomeActivity.class);
                PermissionDeniedActivity.this.finish();
            }

            @Override
            public void permissionRefused() {
                Log.d(TAG, "PermissionsManager.onDenied permission:" );
            }
        };

        Nammu.askForPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE" , permissionContactsCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
