package com.siddworks.assistantfortt2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.siddworks.assistantfortt2.ui.SettingsListViewAdapter;
import com.siddworks.assistantfortt2.util.CommonUtil;
import com.siddworks.assistantfortt2.util.Constants;
import com.siddworks.assistantfortt2.util.Log;
import com.siddworks.assistantfortt2.util.PrefUtils;
import com.siddworks.assistantfortt2.util.TTHelperUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends BaseActivity {

    private static final String TAG = "SettingsActivity";

    ListView listview;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        super.init();

        Intent intent = getIntent();
        if(PrefUtils.getStyleColor().equals(Constants.STYLE_COLOR_CUSTOM)) {
            color = PrefUtils.getStyleColorCustomValue();
            color = ContextCompat.getColor(this, color);
        } else {
            color = intent.getIntExtra(Constants.KEY_COLOR_CURRENT,  ContextCompat.getColor(this, R.color.bluebg));
            Log.d( TAG, "color: "+color);
        }
        TTHelperUtil.logEvent("ScreenSettings");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setBackgroundColor(color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(color);
            }
        }
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        listview = (ListView) findViewById(R.id.listview);
        setAdapter();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(SettingsActivity.this, StyleSelectionActivity.class);
                if(position == 0) {
                    intent.putExtra(Constants.KEY, Constants.STYLE_CONTENT);
                    intent.putExtra(Constants.KEY_COLOR_CURRENT, color);
                    intent.putExtra("Title", "Select Content Style");
                    startActivity(intent);
                } else if(position == 1) {
                    intent.putExtra(Constants.KEY, Constants.STYLE_VISUAL);
                    intent.putExtra(Constants.KEY_COLOR_CURRENT, color);
                    intent.putExtra("Title", "Select Visual Style");
                    startActivity(intent);
                }  else if(position == 2) {
                    CommonUtil.startActivity(SettingsActivity.this, StyleColorSelectionActivity.class);
                } else if(position == 3) {
//                    showDefaultScreenPopup();
                } else if(position == 4) {
                    TTHelperUtil.showWhatsNewPopup(SettingsActivity.this);
                } else if(position == 5) {
//                    TTHelperUtil.showAboutDialog(SettingsActivity.this);
                }
            }
        });
    }

//    private void showDefaultScreenPopup() {
//        final ListView listView = new ListView(this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        listView.setLayoutParams(params);
//
//        final ArrayList<String> screens = new ArrayList<>();
//        screens.add(Constants.SCREEN_SWORDMASTER);
//        screens.add(Constants.SCREEN_SPELLMASTER);
//        screens.add(Constants.SCREEN_HOME);
//        screens.add(Constants.SCREEN_TOURNAMENT);
//
//        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, screens));
//
//        final MaterialDialog close = CommonUtil.getDialogBuilder(null, this)
//                .customView(listView, false)
//                .positiveColor(ContextCompat.getColor(this, R.color.colorPrimary))
//                .title("Default Screen")
//                // Set the action buttons
//                .positiveText("Close")
//                .show();
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int which, long id) {
//                Log.d( TAG, "onItemClick() called with: " + "parent = [" + parent + "], view = [" + view + "], which = [" + which + "], id = [" + id + "]");
//                PrefUtils.setDefaultScreen(screens.get(which));
//                close.hide();
//                setAdapter();
//            }
//        });
//
//    }

    private void setAdapter() {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> item = new HashMap<>();
        item.put("header", "Content Style");
        item.put("text", PrefUtils.getStyleContent());
        item.put("resid", PrefUtils.getStyleContentDrawable());
        list.add(item);

        item = new HashMap<>();
        item.put("header", "Visual Style");
        item.put("text", PrefUtils.getStyleVisual());
        item.put("resid", PrefUtils.getStyleVisualDrawable());
        list.add(item);

        item = new HashMap<>();
        item.put("header", "Color Style");
        item.put("text", PrefUtils.getStyleColor());
        item.put("resid", PrefUtils.getStyleColorDrawable());
        list.add(item);

//        item = new HashMap<>();
//        item.put("header", "Default Tab");
//        item.put("text", PrefUtils.getDefaultScreen());
//        item.put("resid", PrefUtils.getDefaultScreenDrawable());
//        list.add(item);

        SettingsListViewAdapter adapter = new SettingsListViewAdapter(this, list, color);
        listview.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            Intent intent = getIntent();
            if(PrefUtils.getStyleColor().equals(Constants.STYLE_COLOR_CUSTOM)) {
                color = PrefUtils.getStyleColorCustomValue();
                color = ContextCompat.getColor(this, color);
            } else {
                color = intent.getIntExtra(Constants.KEY_COLOR_CURRENT, R.color.bluebg);
            }

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                toolbar.setBackgroundColor(color);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(color);
                }
            }
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setHomeButtonEnabled(true);
                ab.setDisplayHomeAsUpEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setAdapter();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
