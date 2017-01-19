package com.siddworks.assistantfortt2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.nononsenseapps.filepicker.FilePickerActivity;
import com.siddworks.assistantfortt2.bean.Player;
import com.siddworks.assistantfortt2.util.ColorUtils;
import com.siddworks.assistantfortt2.util.CommonUtil;
import com.siddworks.assistantfortt2.util.Constants;
import com.siddworks.assistantfortt2.util.Log;
import com.siddworks.assistantfortt2.util.PrefUtils;
import com.siddworks.assistantfortt2.util.SaveFileJsonParser;
import com.siddworks.assistantfortt2.util.TTHelperUtil;

import java.io.File;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

import static com.siddworks.assistantfortt2.util.SaveFileJsonParser.readSaveFile;

public class DashboardActivity extends BaseActivity {
    private static final String TAG = "DashboardActivity";
    private Player player;
    private HomeInfoFragment homeFragment;
    private String currentTab;
    private static final int FILE_CODE = 1331;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        super.init();
        init();
        player = TTHelperApplication.getPlayer();

        currentTab = PrefUtils.getDefaultScreen();
        int color = ColorUtils.getColorForTab(DashboardActivity.this, currentTab);
        int currentTabNo = 0;
        currentTab = Constants.SCREEN_HOME;

        homeFragment = HomeInfoFragment.newInstance(0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, (Fragment) homeFragment);
        transaction.commit();

        renderUi();
    }

    public void renderUi() {
//        Log.d(isLoggingEnabled, TAG, "renderUi() called");
        runOnUiThread(new Runnable() {
            public void run() {
                try {
//                    Log.d(isLoggingEnabled, TAG, " player:" + player + ", currentTab:" + currentTab
//                    +", homeFragment:" + homeFragment);

                    if(currentTab.equals("Home") && player != null && homeFragment != null &&
                            homeFragment.getMainView() != null) {
                        homeFragment.buildUi(player, SaveFileJsonParser.homeMetaMap);
                    }
//                    if(currentTab.equals("SwordMaster") && player != null && swordMasterFragment.getMainView() != null) {
//                        swordMasterFragment.buildUi(player, SaveFileJsonParser.swordMasterNameMap);
//                    }
//                    if(currentTab.equals("SpellMaster") && player != null && spellMasterFragment.getMainView() != null) {
//                        spellMasterFragment.buildUi(player, SaveFileJsonParser.spellMasterNameMap);
//                    }
//                    if(currentTab.equals("Tournament") && player != null && tournamentFragment.getMainView() != null) {
//                        tournamentFragment.buildUi(player, SaveFileJsonParser.tournamentNameMap);
//                    }

                } catch (Exception e) {
                    Log.e(TAG, e);
                    TTHelperUtil.logError(e, "DA4", "FailedToRenderUi");
                    CommonUtil.showAlertOneButton(null, DashboardActivity.this, "Error", "Error reading data from save file.");
                }
            }
        });
    }

    private void checkPermissionAndReadSaveFile() {
//        Log.d(isLoggingEnabled, TAG, "checkPermissionAndReadSaveFile() called with: " + "");
        // android.permission.WRITE_EXTERNAL_STORAGE

        PermissionCallback permissionContactsCallback = new PermissionCallback() {
            @Override
            public void permissionGranted() {
                Log.d(TAG, "PermissionsManager.onGranted" );
                File saveFile = PrefUtils.getSaveFile();
                readSaveFile(saveFile);
            }

            @Override
            public void permissionRefused() {
                Log.d(TAG, "PermissionsManager.onDenied permission:" );
                CommonUtil.startActivity(DashboardActivity.this, PermissionDeniedActivity.class);
                DashboardActivity.this.finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);

        if(!Constants.isBetaUser) {
            MenuItem markNonExistentMenuItem = menu.findItem(R.id.action_select_save_file);
            markNonExistentMenuItem.setVisible(false);
//            MenuItem betaFeedbackItem = menu.findItem(R.id.action_beta_feedback);
//            betaFeedbackItem.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        if(id == R.id.action_launch_game) {
//            try {
//                Intent intent = new Intent();
//                intent.setClassName(
//                        "com.gamehivecorp.taptitans",
//                        "com.gamehivecorp.ghplugin.ImmersivePlayerNativeActivity");
//                startActivity(intent);
//            } catch (Exception e) {
//                CommonUtil.showAlertOneButton(null, this, "Error", "Couldn't launch game. Looks like its not installed");
//            }
//            return true;
//        }
//        else if(id == R.id.action_settings) {
//            TTHelperUtil.logEvent("MenuSettings");
//            int color = ColorUtils.getColorForTab(DashboardActivity.this, currentTab);
//            Intent intent = new Intent(this, SettingsActivity.class);
//            intent.putExtra(Constants.KEY_COLOR_CURRENT, color);
//            startActivity(intent);
//            return true;
//        }
//        else
        if(id == R.id.action_contact) {
            TTHelperUtil.logEvent("MenuContactDeveloper");
            TTHelperUtil.showContactDeveloper(DashboardActivity.this);
            return true;
        }
//        else if(id == R.id.action_beta_feedback) {
//            TTHelperUtil.openBetaFeedback(this);
//            return true;
//        }
        else if(id == R.id.action_select_save_file) {
            TTHelperUtil.logEvent("MenuSelectSaveFile");
            showPicker();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showPicker() {
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

}
