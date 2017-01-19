package com.siddworks.assistantfortt2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.siddworks.assistantfortt2.ui.ContributeCardData;
import com.siddworks.assistantfortt2.ui.ShadowTransformer;
import com.siddworks.assistantfortt2.ui.StyleSelectionCardAdapter;
import com.siddworks.assistantfortt2.util.Constants;
import com.siddworks.assistantfortt2.util.Log;
import com.siddworks.assistantfortt2.util.PrefUtils;

import java.util.ArrayList;

public class StyleSelectionActivity extends AppCompatActivity {

    private static final String TAG = "StyleSelectionActivity";
    private String stringExtra;
    private ArrayList<ContributeCardData> pagers;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_selection);

        String selected = null;

        Intent intent = getIntent();
        color = intent.getIntExtra(Constants.KEY_COLOR_CURRENT, R.color.bluebg);
        stringExtra = intent.getStringExtra(Constants.KEY);
        String title = intent.getStringExtra("Title");

        Log.d( TAG, " title:"+title);

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
            ab.setTitle(title);
        }

        ViewPager viewPagerMain = (ViewPager) findViewById(R.id.viewPager);

        if(stringExtra.equals(Constants.STYLE_CONTENT)) {
            pagers = getContentPagerObject();
            selected = PrefUtils.getStyleContent();
        } else if(stringExtra.equals(Constants.STYLE_VISUAL)) {
            pagers = getVisualPagerObject();
            selected = PrefUtils.getStyleVisual();
        }

        StyleSelectionCardAdapter cardAdapter = new StyleSelectionCardAdapter(this, pagers, selected);

        ShadowTransformer cardShadowTransformer = new ShadowTransformer(viewPagerMain, cardAdapter);

        viewPagerMain.setAdapter(cardAdapter);
        viewPagerMain.setPageTransformer(false, cardShadowTransformer);
        viewPagerMain.setOffscreenPageLimit(3);

        cardShadowTransformer.enableScaling(true);
    }

    private ArrayList<ContributeCardData> getContentPagerObject() {
        ArrayList<ContributeCardData> data = new ArrayList<>();

        data.add(new ContributeCardData(Constants.STYLE_CONTENT_DOUBLE_TREAT,
                "", R.drawable.style_doublerow, 0));
        data.add(new ContributeCardData(Constants.STYLE_CONTENT_FIFTY_FIFTY,
                "", R.drawable.style_papercut, 0));

        return data;
    }

    private ArrayList<ContributeCardData> getVisualPagerObject() {
        ArrayList<ContributeCardData> data = new ArrayList<>();

        data.add(new ContributeCardData(Constants.STYLE_VISUAL_CARDS,
                "",
                R.drawable.style_cards, 0));
        data.add(new ContributeCardData(Constants.STYLE_VISUAL_PAPER_CUT,
                "",
                R.drawable.style_papercut, 0));
        data.add(new ContributeCardData(Constants.STYLE_VISUAL_WATER,
                "",
                R.drawable.style_water, 0));

        return data;
    }

    public void itemSelected(int position) {
        Log.d( TAG, " position:" + position);

        if(stringExtra.equals(Constants.STYLE_CONTENT)) {
            PrefUtils.setStyleContent(pagers.get(position).getTitle());
        } else if(stringExtra.equals(Constants.STYLE_VISUAL)) {
            PrefUtils.setStyleVisual(pagers.get(position).getTitle());
        }
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
