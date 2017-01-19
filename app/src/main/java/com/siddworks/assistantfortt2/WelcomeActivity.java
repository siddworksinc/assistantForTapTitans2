package com.siddworks.assistantfortt2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.siddworks.assistantfortt2.bean.Player;
import com.siddworks.assistantfortt2.util.ColorUtils;
import com.siddworks.assistantfortt2.util.CommonUtil;
import com.siddworks.assistantfortt2.util.Constants;
import com.siddworks.assistantfortt2.util.Log;
import com.siddworks.assistantfortt2.util.PrefUtils;
import com.siddworks.assistantfortt2.util.SaveFileJsonParser;
import com.siddworks.assistantfortt2.util.TTHelperUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeActivity";

    private String playerInfo;
    private Player player;
    private int accentColor;
    private String playerInfoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();

        final View saveFile = findViewById(R.id.wc_root_save_file);
        setOnTouchListener(saveFile, "ButtonScreenSaveFileInfo", DashboardActivity.class);

        final View settings = findViewById(R.id.wc_root_settings);
        settings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    scaleView(settings, 1, 0.95f);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    scaleView(settings, 0.95f, 1f);
                    TTHelperUtil.logEvent("ButtonScreenSettings");
                    Intent intent = new Intent(WelcomeActivity.this, SettingsActivity.class);
                    intent.putExtra(Constants.KEY_COLOR_CURRENT, ContextCompat.getColor(WelcomeActivity.this, R.color.bluebg));
                    startActivity(intent);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    scaleView(settings, 0.95f, 1f);
                    return true;
                }
                return true;
            }
        });

        View about = findViewById(R.id.wc_root_about);
        setOnTouchListener(about, "ButtonScreenAbout", AboutActivity.class);

        TextView version = (TextView) findViewById(R.id.wc_version_info);
        version.setText("v"+BuildConfig.VERSION_NAME);
        version.setBackgroundColor(ColorUtils.getResolvedColor(this, accentColor));

        TextView title = (TextView) findViewById(R.id.wc_title);
        title.setBackgroundColor(ColorUtils.getResolvedColor(this, accentColor));

        final View shortcutsGame = findViewById(R.id.wc_shortcuts_game);
        shortcutsGame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    scaleView(shortcutsGame, 1, 0.95f);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    scaleView(shortcutsGame, 0.95f, 1f);
                    TTHelperUtil.logEvent("ButtonLaunchGame");
                    try {
                        Intent intent = new Intent();
                        intent.setClassName(
                                "com.gamehivecorp.taptitans2",
                                "com.unity3d.player.UnityPlayerActivity");
                        startActivity(intent);
                    } catch (Exception e) {
                        CommonUtil.showAlertOneButton(null, WelcomeActivity.this,
                                "Error", "Couldn't launch game Tap Titans2. Looks like its not installed");
                    }
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    scaleView(shortcutsGame, 0.95f, 1f);
                    return true;
                }
                return true;
            }
        });

        final View shortcutsReddit = findViewById(R.id.wc_shortcuts_reddit);
        shortcutsReddit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    scaleView(shortcutsReddit, 1, 0.95f);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    scaleView(shortcutsReddit, 0.95f, 1f);
                    TTHelperUtil.logEvent("ButtonWelcomeReddit");
                    TTHelperUtil.openUrl(WelcomeActivity.this, "https://www.reddit.com/r/TapTitans2/");
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    scaleView(shortcutsReddit, 0.95f, 1f);
                    return true;
                }
                return true;
            }
        });

        final View shortcutsDownload = findViewById(R.id.wc_shortcuts_download);
        shortcutsDownload.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    scaleView(shortcutsDownload, 1, 0.95f);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    scaleView(shortcutsDownload, 0.95f, 1f);
                    TTHelperUtil.logEvent("ButtonWelcomeDownloadSaveFile");
                    downloadSaveFile();
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    scaleView(shortcutsDownload, 0.95f, 1f);
                    return true;
                }
                return true;
            }
        });
    }

    private void downloadSaveFile() {
        CommonUtil.showSnackbar(findViewById(R.id.main_content), "Writing SaveFile to Internal Storage...");
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(playerInfoString);
            String prettyJsonString = gson.toJson(je);

            File file = new File(Environment.getExternalStorageDirectory(), "TT2SaveFile.json");
            FileWriter out = new FileWriter(file, true);
            out.write(prettyJsonString+"\n\n");
            out.close();
            CommonUtil.showSnackbar(findViewById(R.id.main_content), "Decrypted SaveFile saved at InternalStorage/TT2SaveFile.json");
        } catch (Exception e) {
            Log.e(TAG, e);
            e.printStackTrace();
            showErrorPopup("Error writing SaveFile Data", "Would you please send your save file so that this can be fixed in next version?");
        }
    }

    private void setOnTouchListener(final View view, final String action, final Class dashboardActivityClass) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    scaleView(view, 1, 0.95f);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    TTHelperUtil.logEvent(action);
                    CommonUtil.startActivity(WelcomeActivity.this, dashboardActivityClass);
                    scaleView(view, 0.95f, 1f);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    scaleView(view, 0.95f, 1f);
                    return true;
                }
                return true;
            }
        });
    }

    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                startScale, endScale, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(500);
        v.startAnimation(anim);
    }

    private void init() {
        Log.d(TAG, " init:");

        accentColor = ColorUtils.getAccentColor(this, R.color.bluebg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ColorUtils.getResolvedColor(this, accentColor));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setBackgroundColor(ColorUtils.getResolvedColor(this, accentColor));
        }

        Nammu.init(getApplicationContext());

        boolean shouldShowWhatsNewPopup = PrefUtils.setCurrentVersion();
        Log.d(TAG, " shouldShowWhatsNewPopup:" + shouldShowWhatsNewPopup);
        if(shouldShowWhatsNewPopup) {
            TTHelperUtil.showWhatsNewPopup(this);
        }
    }

    private void checkPermissionAndReadSaveFile() {
//        Log.d(TAG, "checkPermissionAndReadSaveFile() called with: " + "");
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
                CommonUtil.startActivity(WelcomeActivity.this, PermissionDeniedActivity.class);
                WelcomeActivity.this.finish();
            }
        };

        Nammu.askForPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE" , permissionContactsCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume() called");

        checkPermissionAndReadSaveFile();
        refreshToolbar();
    }

    private void refreshToolbar() {
        accentColor = ColorUtils.getAccentColor(this, R.color.bluebg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ColorUtils.getResolvedColor(this, accentColor));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setBackgroundColor(ColorUtils.getResolvedColor(this, accentColor));
        }

        TextView version = (TextView) findViewById(R.id.wc_version_info);
        version.setBackgroundColor(ColorUtils.getResolvedColor(this, accentColor));

        TextView title = (TextView) findViewById(R.id.wc_title);
        title.setBackgroundColor(ColorUtils.getResolvedColor(this, accentColor));
    }

    private void showSaveFileNotFoundActivity() {
        CommonUtil.startActivity(this, FileNotFoundActivity.class);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void readSaveFile(final File saveFile) {
        Log.d(TAG, "readSaveFile. saveFile:" + saveFile);
        final String[] readTextFile = {null};

        try {
            if(saveFile == null || !saveFile.exists()) {
                TTHelperUtil.logError(null, "WA1", "SaveFileDoesNotExist");
                showSaveFileNotFoundActivity();
                return;
            }
            if(!saveFile.canRead()) {
                TTHelperUtil.logError(null, "WA2", "CannotReadSaveFile");
                showSaveFileNotFoundActivity();
                return;
            }

            long startTime = System.nanoTime();
            readTextFile[0] = SaveFileJsonParser.readSaveFile(saveFile);
            long endTime = System.nanoTime();

            int beginIndex = readTextFile[0].indexOf("saveString") - 2;
            int lastIndex = readTextFile[0].lastIndexOf("}");
            String jsonS = readTextFile[0].substring(beginIndex, lastIndex + 1);

            JSONObject jb = new JSONObject(jsonS);
            playerInfoString = jb.getString("saveString");

//                    Log.d(isLoggingEnabled, TAG, "readSaveFile.playerInfoString:"+(playerInfoString == null ? "null" : playerInfoString.substring(0, 20)));

            player = SaveFileJsonParser.parse(playerInfoString);
            TTHelperApplication.setPlayer(player);
//                    TTHelperUtil.logSaveFileReadTimeEvent(endTime - startTime);
        } catch (Exception e) {
            Log.e(TAG, e);
            TTHelperUtil.logError(e, "WA3", "FailedToCreateUI");
//
//                    try {
//                        String compress = LZString.compress(readTextFile[0]);
//                        Answers.getInstance().logCustom(new CustomEvent("ErrorSaveFileContent")
//                                .putCustomAttribute("Content", readTextFile[0])
//                                .putCustomAttribute("CompressedContent", compress)
//                                .putCustomAttribute("Error", e.getMessage()));
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("Content", readTextFile[0]);
//                        params.put("CompressedContent", compress);
//                        params.put("Error", e.getMessage());
//                        FlurryAgent.logEvent("ErrorSaveFileContent", params);
//                    } catch (Exception e1) {
//                        TTHelperUtil.logError(e, "WA4", "FailedToSendFile");
//                    }
//
            if(!WelcomeActivity.this.isFinishing()) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        showErrorPopup("Error Reading Save File Data", "Would you please send your save file so that this can be fixed in next version?");
                    }
                });
            }
        }
    }

    private void showErrorPopup(String title, String message) {
        new MaterialDialog.Builder(WelcomeActivity.this)
                .title(title)
                .content(message)
                .positiveText("Sure!")
                .negativeText("Close")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        TTHelperUtil.showContactDeveloper(WelcomeActivity.this);
                    }
                })
                .positiveColor(ContextCompat.getColor(WelcomeActivity.this, R.color.colorPrimary))
                .negativeColor(ContextCompat.getColor(WelcomeActivity.this, R.color.colorPrimary))
                .show();
    }

}
