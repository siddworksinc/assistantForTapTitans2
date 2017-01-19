package com.siddworks.assistantfortt2;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siddworks.assistantfortt2.util.ColorUtils;
import com.siddworks.assistantfortt2.util.TTHelperUtil;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        int accentColor = ColorUtils.getAccentColor(this, R.color.bluebg);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setBackgroundColor(ColorUtils.getResolvedColor(this, accentColor));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ColorUtils.getResolvedColor(this, accentColor));
            }
        }
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }


        // Author Heart Image
        ImageView authorHeartImage = (ImageView) findViewById(R.id.about_author_image);
        authorHeartImage.setColorFilter(ContextCompat.getColor(this, R.color.redbody));
        authorHeartImage.setImageResource(R.drawable.dr_heart_white);

        // Twitter row
        LinearLayout authorTwitter = (LinearLayout) findViewById(R.id.about_author_root_twitter);
        authorTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TTHelperUtil.logEvent("ButtonAboutAuthorTwitter");
                TTHelperUtil.openUrl(AboutActivity.this, "https://twitter.com/sagarsiddhpura");
            }
        });

        // Reddit row
        LinearLayout authorReddit = (LinearLayout) findViewById(R.id.about_author_root_reddit);
        authorReddit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TTHelperUtil.logEvent("ButtonAboutAuthorReddit");
                TTHelperUtil.openUrl(AboutActivity.this, "https://www.reddit.com/u/sagarsiddhpura/");
            }
        });

        // App version textview
        TextView appVersion = (TextView) findViewById(R.id.about_app_version);
        appVersion.setText(TTHelperUtil.getCurrentVersion());

        // App play store
        LinearLayout appPlayStore = (LinearLayout) findViewById(R.id.about_app_root_playstore);
        appPlayStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TTHelperUtil.logEvent("ButtonAboutAppPlayStore");
                TTHelperUtil.openUrl(AboutActivity.this, "https://play.google.com/store/apps/details?id=com.siddworks.assistantfortt2");
            }
        });

        // App WhatsNew
        LinearLayout appWhatsNew = (LinearLayout) findViewById(R.id.about_app_root_whatsnew);
        appWhatsNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TTHelperUtil.logEvent("ButtonAboutAppWhatsNew");
                TTHelperUtil.showWhatsNewPopup(AboutActivity.this);
            }
        });

        // App PS Image
        ImageView appPSImage = (ImageView) findViewById(R.id.about_app_ps);
        appPSImage.setColorFilter(ContextCompat.getColor(this, accentColor));
        appPSImage.setImageResource(R.drawable.ic_about_store);

        // App whatsnew Image
        ImageView appWhatsNewImage = (ImageView) findViewById(R.id.about_app_whatsnew);
        appWhatsNewImage.setColorFilter(ContextCompat.getColor(this, accentColor));
        appWhatsNewImage.setImageResource(R.drawable.ic_about_new);

        // App curr ver Image
        ImageView appCurrVerImage = (ImageView) findViewById(R.id.about_app_current_ver_image);
        appCurrVerImage.setColorFilter(ContextCompat.getColor(this, accentColor));
        appCurrVerImage.setImageResource(R.drawable.ic_about_current_ver);

        // Feedback

        // Feedback Connect
        LinearLayout feedbackConnectRoot = (LinearLayout) findViewById(R.id.about_feedback_connect_root);
        feedbackConnectRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TTHelperUtil.logEvent("ButtonAboutFeedbackConnectWithDeveloper");
                TTHelperUtil.showContactDeveloper(AboutActivity.this);
            }
        });

        // Feedback Update
        LinearLayout feedbackUpdateRoot = (LinearLayout) findViewById(R.id.about_feedback_update_root);
        feedbackUpdateRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TTHelperUtil.logEvent("ButtonAboutFeedbackLatestUpdate");
                TTHelperUtil.openBetaFeedback(AboutActivity.this);
            }
        });

//        // Feedback Upcoming
//        LinearLayout feedbackUpcomingRoot = (LinearLayout) findViewById(R.id.about_feedback_upcoming_root);
//        feedbackUpcomingRoot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TTHelperUtil.logEvent("ButtonAboutFeedbackUpcomingFeatures");
//                TTHelperUtil.sendGoogleFormsfeedback(AboutActivity.this);
//            }
//        });

        // sendGoogleFormsfeedback connect Image
        ImageView feedbackConnect = (ImageView) findViewById(R.id.about_feedback_connect_image);
        feedbackConnect.setColorFilter(ContextCompat.getColor(this, accentColor));
        feedbackConnect.setImageResource(R.drawable.ic_about_mail);

        // sendGoogleFormsfeedback update Image
        ImageView feedbackUpdate = (ImageView) findViewById(R.id.about_feedback_update_image);
        feedbackUpdate.setColorFilter(ContextCompat.getColor(this, accentColor));
        feedbackUpdate.setImageResource(R.drawable.ic_about_latest);

        // sendGoogleFormsfeedback upcoming Image
        ImageView feedbackUpcoming = (ImageView) findViewById(R.id.about_feedback_upcoming_image);
        feedbackUpcoming.setColorFilter(ContextCompat.getColor(this, accentColor));
        feedbackUpcoming.setImageResource(R.drawable.ic_about_upcoming);

        // Credits Connect
        LinearLayout creditsColblitzRoot = (LinearLayout) findViewById(R.id.about_credits_colblitz_root);
        creditsColblitzRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TTHelperUtil.logEvent("ButtonAboutCreditsColblitz");
                TTHelperUtil.openUrl(AboutActivity.this, "https://www.reddit.com/u/colblitz/");
            }
        });

        // Credits Update
        LinearLayout creditsTitansmRoot = (LinearLayout) findViewById(R.id.about_credits_titansm_root);
        creditsTitansmRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TTHelperUtil.logEvent("ButtonAboutCreditsTitansM");
                TTHelperUtil.openUrl(AboutActivity.this, "https://www.reddit.com/u/Titansmasher_/");
            }
        });

        // Credits Upcoming
        LinearLayout creditsRedditRoot = (LinearLayout) findViewById(R.id.about_credits_reddit_root);
        creditsRedditRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TTHelperUtil.logEvent("ButtonAboutCreditsReddit");
                TTHelperUtil.openUrl(AboutActivity.this, "https://www.reddit.com/r/TapTitans2/");
            }
        });
    }
}
