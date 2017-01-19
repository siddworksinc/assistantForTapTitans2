package com.siddworks.assistantfortt2;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.siddworks.assistantfortt2.bean.Player;
import com.siddworks.assistantfortt2.ui.ColorsGridViewAdapter;
import com.siddworks.assistantfortt2.ui.SaveFileInfoAdapter;
import com.siddworks.assistantfortt2.util.ColorUtils;
import com.siddworks.assistantfortt2.util.Constants;
import com.siddworks.assistantfortt2.util.JsonUtil;
import com.siddworks.assistantfortt2.util.PrefUtils;
import com.siddworks.assistantfortt2.util.SaveFileJsonParser;

import java.util.LinkedList;

public class StyleColorSelectionActivity extends BaseActivity {

    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_color_selection);

        super.init();

        if(PrefUtils.getStyleColor().equals(Constants.STYLE_COLOR_CUSTOM)) {
            color = PrefUtils.getStyleColorCustomValue();
            color = ContextCompat.getColor(this, color);
        } else {
            color = R.color.bluebg;
            color = ContextCompat.getColor(this, color);
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        String json = JsonUtil.parseBootStrapData(this);
        final Player player = SaveFileJsonParser.parse(json);
        LinkedList<Integer> colors = ColorUtils.getCardsColor(Constants.SCREEN_HOME);

        final RecyclerView rv = (RecyclerView) findViewById(R.id.select_color_content_RecyclerView);
        rv.setLayoutManager(new GridLayoutManager(this, 1));
        rv.setAdapter(new SaveFileInfoAdapter(SaveFileJsonParser.homeMetaMap, player, this, colors));

        GridView rvColors = (GridView) findViewById(R.id.select_color_colors_GridView);
        final ColorsGridViewAdapter colorsGridViewAdapter = new ColorsGridViewAdapter(this);
        rvColors.setAdapter(colorsGridViewAdapter);
        rvColors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int[] colors = colorsGridViewAdapter.getColors();
                if(position == colors.length) {
                    PrefUtils.setStyleColor(Constants.STYLE_COLOR_DEFAULT);
                    if (toolbar != null) {
                        toolbar.setBackgroundColor(ColorUtils.getResolvedColor(StyleColorSelectionActivity.this, R.color.bluebg));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ColorUtils.getResolvedColor(StyleColorSelectionActivity.this, R.color.bluebg));
                        }
                    }
                } else {
                    int currentColor = colors[position];
                    PrefUtils.setStyleColor(Constants.STYLE_COLOR_CUSTOM);
                    PrefUtils.setStyleCustomValue(currentColor);

                    currentColor = ColorUtils.getResolvedColor(StyleColorSelectionActivity.this, currentColor);
                    if (toolbar != null) {
                        toolbar.setBackgroundColor(currentColor);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(currentColor);
                        }
                    }
                }
                colorsGridViewAdapter.notifyDataSetChanged();

                LinkedList<Integer> colorsNew = ColorUtils.getCardsColor(Constants.SCREEN_HOME);
                rv.setAdapter(new SaveFileInfoAdapter(SaveFileJsonParser.homeMetaMap, player, StyleColorSelectionActivity.this, colorsNew));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
