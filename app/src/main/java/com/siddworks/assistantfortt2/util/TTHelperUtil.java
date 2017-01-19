package com.siddworks.assistantfortt2.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siddworks.assistantfortt2.BuildConfig;
import com.siddworks.assistantfortt2.HomeInfoFragment;
import com.siddworks.assistantfortt2.R;
import com.siddworks.assistantfortt2.SpellMasterFragment;
import com.siddworks.assistantfortt2.SwordMasterFragment;
import com.siddworks.assistantfortt2.TournamentFragment;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sidd on 15/1/17.
 */
public class TTHelperUtil {
    private static final String TAG = "TTHelperUtil";
    public static void logEvent(String screenPermissionDenied) {

    }

    public static void logError(Object o, String sp2, String gameNotInstalled) {
        android.util.Log.d(TAG, "logError() called with: o = [" + o + "], sp2 = [" + sp2 + "], gameNotInstalled = [" + gameNotInstalled + "]");

    }

    public static CardView getCardView(Activity activity, int color, int radius, int left, int top, int right, int bottom) {
        CardView cardview = new CardView(activity);
        cardview.setCardBackgroundColor(activity.getResources().getColor(color));
        cardview.setUseCompatPadding(true);
        cardview.setRadius(CommonUtil.dpToPx(radius));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(CommonUtil.dpToPx(left), CommonUtil.dpToPx(top),
                CommonUtil.dpToPx(right), CommonUtil.dpToPx(bottom));
        cardview.setLayoutParams(layoutParams);
        return cardview;
    }

    public static LinearLayout getLinearLayout(Context activity, int orientation, int left, int top, int right, int bottom) {
        LinearLayout rootLinearLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(CommonUtil.dpToPx(left), CommonUtil.dpToPx(top),
                CommonUtil.dpToPx(right), CommonUtil.dpToPx(bottom));
        rootLinearLayout.setLayoutParams(layoutParams2);
        rootLinearLayout.setOrientation(orientation);
        return rootLinearLayout;
    }

    public static TextView getHeaderTextView(Activity activity, String text, int height,
                                             int left, int top, int right, int bottom) {
        TextView headerView = new TextView(activity);
        headerView.setPadding(CommonUtil.dpToPx(left), CommonUtil.dpToPx(top),
                CommonUtil.dpToPx(right), CommonUtil.dpToPx(bottom));
        headerView.setTextColor(ContextCompat.getColor(activity, R.color.white));
        headerView.setTypeface(null, Typeface.BOLD);
        headerView.setText(text);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, CommonUtil.dpToPx(height));
        headerView.setLayoutParams(layoutParams3);
        headerView.setGravity(Gravity.CENTER);
        headerView.setMaxLines(2);
        return headerView;
    }

    public static TextView getKeyTextView(Activity activity, String text) {
        TextView key = getTextView(activity, text, R.color.white);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 0.49f;
        key.setLayoutParams(params);
        return key;
    }

    public static TextView getTextView(Activity activity, String text, int color) {
        TextView key = new TextView(activity);
        key.setTextColor(activity.getResources().getColor(color));
        key.setText(text);
        return key;
    }

    public static TextView getValueTextView(Activity activity, String text) {
        TextView value = new TextView(activity);
        value.setTextColor(ContextCompat.getColor(activity, R.color.white));
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT);
        params2.weight = 0.49f;
        params2.setMargins(CommonUtil.dpToPx(2), 0, 0, 0);
        value.setLayoutParams(params2);
        value.setText(text);
        return value;
    }

    public static TextView getSpacerView(Activity activity) {
        TextView spacer = new TextView(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0, CommonUtil.dpToPx(48));
        spacer.setLayoutParams(params);
        return spacer;
    }

    public static LinearLayout getKeyValueLayout(Activity activity, LinkedHashMap<String, String> keyValueMap) {
        String screenType = PrefUtils.getStyleContent();
        if(screenType.equals(Constants.STYLE_CONTENT_FIFTY_FIFTY)) {
            return getKeyValueSingleRow(activity, keyValueMap);
        } else if(screenType.equals(Constants.STYLE_CONTENT_DOUBLE_TREAT)) {
            return getKeyValueDoubleRows2(activity, keyValueMap);
        } else {
            return getKeyValueSingleRow(activity, keyValueMap);
        }
    }

    private static LinearLayout getKeyValueSingleRow(Activity activity, LinkedHashMap<String, String> map) {
        LinearLayout keyValueMain = TTHelperUtil.getLinearLayout(activity, LinearLayout.VERTICAL, 0, 0, 0, 4);

        Iterator attributeMapIterator = map.entrySet().iterator();
        for(int i=0; attributeMapIterator.hasNext(); i++) {
            String attributeKey = (String) ((Map.Entry) attributeMapIterator.next()).getKey();
            String attributeValue = map.get(attributeKey);
            Log.d(TAG, " attributeKey:" + attributeKey);
            Log.d(TAG, " attributeValue:" + attributeValue);

            LinearLayout keyValueParent = TTHelperUtil.getLinearLayout(activity, LinearLayout.HORIZONTAL, 8, 8, 8, 8);
            keyValueMain.addView(keyValueParent);

            TextView key = TTHelperUtil.getKeyTextView(activity, attributeKey);
            keyValueParent.addView(key);

            TextView value = TTHelperUtil.getValueTextView(activity, attributeValue);
            keyValueParent.addView(value);
        }

        return keyValueMain;
    }

    public static void removeChildren(LinearLayout mainLayout) {
        // Remove children if already added
        int childCount = mainLayout.getChildCount();
        Log.d(TAG, " childCount:" + childCount);

        if(childCount > 0) {
            mainLayout.removeAllViews();
        }
    }

    public static LinearLayout getPaperClipView(Activity activity, int currentColor, int left, int top, int right, int bottom) {

        LinearLayout rootLinearLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(CommonUtil.dpToPx(left), CommonUtil.dpToPx(top),
                CommonUtil.dpToPx(right), CommonUtil.dpToPx(bottom));
        rootLinearLayout.setLayoutParams(layoutParams2);
        rootLinearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout topClip = getLinearLayout(activity, LinearLayout.VERTICAL, 0, 0, 0, 0);
        Bitmap topBmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.triangle);
        topBmp = Bitmap.createScaledBitmap(topBmp, CommonUtil.dpToPx(13), CommonUtil.dpToPx(8), false);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(topBmp);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        bitmapDrawable.setColorFilter(new
                PorterDuffColorFilter(ContextCompat.getColor(activity, currentColor), PorterDuff.Mode.MULTIPLY));
        topClip.setBackgroundDrawable(bitmapDrawable);
        ViewGroup.LayoutParams topLayoutParams = topClip.getLayoutParams();
        topLayoutParams.height = CommonUtil.dpToPx(8);
        topClip.setLayoutParams(topLayoutParams);

        LinearLayout bottomClip = getLinearLayout(activity, LinearLayout.VERTICAL, 0, 0, 0, 0);
        Bitmap bottomBmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.triangle_inverted);
        bottomBmp = Bitmap.createScaledBitmap(bottomBmp, CommonUtil.dpToPx(13), CommonUtil.dpToPx(8), false);
        BitmapDrawable bottomBitmapDrawable = new BitmapDrawable(bottomBmp);
        bottomBitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        bottomBitmapDrawable.setColorFilter(new
                PorterDuffColorFilter(ContextCompat.getColor(activity, currentColor), PorterDuff.Mode.MULTIPLY));
        bottomClip.setBackgroundDrawable(bottomBitmapDrawable);
        ViewGroup.LayoutParams bottomLayoutParams = bottomClip.getLayoutParams();
        bottomLayoutParams.height = CommonUtil.dpToPx(16);
        bottomClip.setLayoutParams(bottomLayoutParams);

        rootLinearLayout.addView(topClip);
        rootLinearLayout.addView(bottomClip);

        return rootLinearLayout;
    }

    public static LinearLayout getWaterView(Activity activity, int currentColor, int left, int top, int right, int bottom) {

        LinearLayout rootLinearLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(CommonUtil.dpToPx(left), CommonUtil.dpToPx(top),
                CommonUtil.dpToPx(right), CommonUtil.dpToPx(bottom));
        rootLinearLayout.setLayoutParams(layoutParams2);
        rootLinearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout topClip = getLinearLayout(activity, LinearLayout.VERTICAL, 0, 0, 0, 0);
        Bitmap topBmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.dr_water);
        topBmp = Bitmap.createScaledBitmap(topBmp, CommonUtil.dpToPx(13), CommonUtil.dpToPx(8), false);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(topBmp);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        bitmapDrawable.setColorFilter(new
                PorterDuffColorFilter(ContextCompat.getColor(activity, currentColor), PorterDuff.Mode.MULTIPLY));
        topClip.setBackgroundDrawable(bitmapDrawable);
        ViewGroup.LayoutParams topLayoutParams = topClip.getLayoutParams();
        topLayoutParams.height = CommonUtil.dpToPx(8);
        topClip.setLayoutParams(topLayoutParams);

        LinearLayout bottomClip = getLinearLayout(activity, LinearLayout.VERTICAL, 0, 0, 0, 0);
        Bitmap bottomBmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.dr_water_inverted);
        bottomBmp = Bitmap.createScaledBitmap(bottomBmp, CommonUtil.dpToPx(13), CommonUtil.dpToPx(8), false);
        BitmapDrawable bottomBitmapDrawable = new BitmapDrawable(bottomBmp);
        bottomBitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        bottomBitmapDrawable.setColorFilter(new
                PorterDuffColorFilter(ContextCompat.getColor(activity, currentColor), PorterDuff.Mode.MULTIPLY));
        bottomClip.setBackgroundDrawable(bottomBitmapDrawable);
        ViewGroup.LayoutParams bottomLayoutParams = bottomClip.getLayoutParams();
        bottomLayoutParams.height = CommonUtil.dpToPx(16);
        bottomClip.setLayoutParams(bottomLayoutParams);

        rootLinearLayout.addView(topClip);
        rootLinearLayout.addView(bottomClip);

        return rootLinearLayout;
    }

    public static LinearLayout getKeyValueDoubleRows(Activity activity, LinkedHashMap<String, String> map) {
        Log.d(TAG, "getKeyValueDoubleRows() called with: " + "activity = [" + activity + "], map = [" + map + "]");

        final int TEXT_VIEW_HEADER_HEIGHT = 28;
        final int PADDING_TOP = 8;

        LinearLayout keyValueParent = TTHelperUtil.getLinearLayout(activity, LinearLayout.HORIZONTAL, 4, 4, 4, 8);

        LinearLayout column1LinearLayout = TTHelperUtil.getLinearLayout(activity, LinearLayout.VERTICAL, 0, 4, 0, 4);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 0.49f;
        column1LinearLayout.setLayoutParams(params);
        keyValueParent.addView(column1LinearLayout);

        LinearLayout column2LinearLayout = TTHelperUtil.getLinearLayout(activity, LinearLayout.VERTICAL, 0, 4, 0, 4);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT);
        params2.weight = 0.49f;
        column2LinearLayout.setLayoutParams(params2);
        keyValueParent.addView(column2LinearLayout);

        // Take iterator from constants map. This will ensure ordering
        Iterator attributeMapIterator = map.entrySet().iterator();
        for(int i=0; attributeMapIterator.hasNext(); i++) {
            String attributeKey = (String) ((Map.Entry)attributeMapIterator.next()).getKey();
            String attributeValue = map.get(attributeKey);
            Log.d(TAG, " attributeKey:" + attributeKey);
            Log.d(TAG, " attributeValue:" + attributeValue);

            // Add to left col
            if(i%2 ==0) {
                TextView keyHeader = TTHelperUtil.getHeaderTextView(activity, attributeKey,
                        TEXT_VIEW_HEADER_HEIGHT, 0, PADDING_TOP, 0, 0);
                keyHeader.setTextSize(14);
                column1LinearLayout.addView(keyHeader);

                TextView value = TTHelperUtil.getTextView(activity, attributeValue, R.color.white);
                value.setGravity(Gravity.CENTER);
                column1LinearLayout.addView(value);
            }
            // Add to right column
            else {
                TextView keyHeader = TTHelperUtil.getHeaderTextView(activity, attributeKey,
                        TEXT_VIEW_HEADER_HEIGHT, 0, PADDING_TOP, 0, 0);
                keyHeader.setTextSize(14);
                column2LinearLayout.addView(keyHeader);

                TextView value = TTHelperUtil.getTextView(activity, attributeValue, R.color.white);
                value.setGravity(Gravity.CENTER);
                column2LinearLayout.addView(value);
            }
        }

        return keyValueParent;
    }

    public static LinearLayout getKeyValueDoubleRows2(Activity activity, LinkedHashMap<String, String> map) {
        Log.d(TAG, "getKeyValueDoubleRows() called with: " + "activity = [" + activity + "], map = [" + map + "]");

        final int TEXT_VIEW_HEADER_HEIGHT = 28;
        final int PADDING_TOP = 10;

        LinearLayout keyValueParentMain = TTHelperUtil.getLinearLayout(activity, LinearLayout.VERTICAL, 4, 0, 4, 10);

        // Take iterator from constants map. This will ensure ordering
        Iterator attributeMapIterator = map.entrySet().iterator();
        for(int i=0; attributeMapIterator.hasNext(); i++) {
            LinearLayout keyValueParentHorizontal = TTHelperUtil.getLinearLayout(activity, LinearLayout.HORIZONTAL, 0, 0, 0, 0);
            keyValueParentMain.addView(keyValueParentHorizontal);

            String attributeKey = (String) ((Map.Entry)attributeMapIterator.next()).getKey();
            String attributeValue = map.get(attributeKey);
            Log.d(TAG, " attributeKey:" + attributeKey);
            Log.d(TAG, " attributeValue:" + attributeValue);

            LinearLayout cell1 = TTHelperUtil.getLinearLayout(activity, LinearLayout.VERTICAL, 0, 4, 0, 4);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 0.49f;
            cell1.setLayoutParams(params);

            TextView keyHeader = TTHelperUtil.getHeaderTextView(activity, attributeKey,
                    TEXT_VIEW_HEADER_HEIGHT, 0, PADDING_TOP, 0, 0);
            keyHeader.setTextSize(14);
            cell1.addView(keyHeader);

            TextView value = TTHelperUtil.getTextView(activity, attributeValue, R.color.white);
            value.setGravity(Gravity.CENTER);
            cell1.addView(value);

            keyValueParentHorizontal.addView(cell1);

            LinearLayout cell2 = TTHelperUtil.getLinearLayout(activity, LinearLayout.VERTICAL, 0, 4, 0, 4);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params2.weight = 0.49f;
            cell2.setLayoutParams(params2);
            keyValueParentHorizontal.addView(cell2);

            if(attributeMapIterator.hasNext()) {
                attributeKey = (String) ((Map.Entry)attributeMapIterator.next()).getKey();
                attributeValue = map.get(attributeKey);
                Log.d(TAG, " attributeKey:" + attributeKey);
                Log.d(TAG, " attributeValue:" + attributeValue);

                keyHeader = TTHelperUtil.getHeaderTextView(activity, attributeKey,
                        TEXT_VIEW_HEADER_HEIGHT, 0, PADDING_TOP, 0, 0);
                keyHeader.setTextSize(14);
                cell2.addView(keyHeader);

                value = TTHelperUtil.getTextView(activity, attributeValue, R.color.white);
                value.setGravity(Gravity.CENTER);
                cell2.addView(value);
            }
        }

        return keyValueParentMain;
    }

//    public static LinkedHashMap<String, String> addRelicPredictionMap(Player player, LinkedHashMap<String, String> existingMap,
//                                                                      String mapKey, String relicPredictionMapKey) {
//        LinkedHashMap<String, String> newMap = new LinkedHashMap<>();
//        newMap.put(relicPredictionMapKey, relicPredictionMapKey);
//        newMap.putAll(existingMap);
//
//        LinkedHashMap<String, HashMap<String, String>> propertiesMap = player.getPropertiesMap();
//        Log.d(TAG, " propertiesMap:" + propertiesMap);
//
//        HashMap<String, String> playerAttributesMap = propertiesMap.get(mapKey);
//        Log.d(TAG, " playerAttributesMap:" + playerAttributesMap);
//        if(playerAttributesMap == null) {
//            return existingMap;
//        }
//
//        LinkedHashMap<String, String> relicPredictionMap = new LinkedHashMap<>();
//
//        String currentStageValue = playerAttributesMap.get("Current Stage");
//        // Next relic increase stage
//        int currentStage = Integer.parseInt(currentStageValue);
//        int nextRelicStage = currentStage - (currentStage % 15) + 15;
//
////        Log.d(TAG, " currentStageValue:" + currentStageValue);
//
//        int world = 1;
//        if(mapKey.equals("worldOneMap")) {
//            world = 1;
//        } else if(mapKey.equals("worldTwoMap")) {
//            world = 2;
//        }
//        ArrayList<Long> relicsAtNextBreakpoint = Calculator.getRelicsAtNextBreakpoint(player, world, currentStage, nextRelicStage);
//        Log.d(TAG, " relicsAtNextBreakpoint:" + relicsAtNextBreakpoint);
//
//        relicPredictionMap.put("Current Stage", currentStageValue);
//        if (relicsAtNextBreakpoint != null && relicsAtNextBreakpoint.size() != 0 &&
//                relicsAtNextBreakpoint.get(0) != -1) {
//            relicPredictionMap.put("Relics at Current Stage", String.valueOf(relicsAtNextBreakpoint.get(0)));
//        }
//
//        relicPredictionMap.put("Next Relic Increase Stage", String.valueOf(nextRelicStage));
//        if (relicsAtNextBreakpoint != null && relicsAtNextBreakpoint.size() != 0 &&
//                relicsAtNextBreakpoint.get(1) != -1) {
//            relicPredictionMap.put("Relics at Next Breakpoint", String.valueOf(relicsAtNextBreakpoint.get(1)));
//        }
//
//        Log.d(TAG, " relicPredictionMap:" + relicPredictionMap);
//
//        propertiesMap.put(relicPredictionMapKey, relicPredictionMap);
//
//        return newMap;
//    }

    public static ViewGroup getHeroView(Activity activity, int color) {
        String screenType = PrefUtils.getStyleVisual();
        ViewGroup heroView = null;
        if(screenType.equals(Constants.STYLE_VISUAL_PAPER_CUT)) {
            heroView = TTHelperUtil.getPaperClipView(activity, color, 0, 0, 0, 16);
        } else if(screenType.equals(Constants.STYLE_VISUAL_CARDS)) {
            heroView = TTHelperUtil.getCardView(activity, color, 8, 8, 8, 8, 8);
        } else if(screenType.equals(Constants.STYLE_VISUAL_WATER)) {
            heroView = TTHelperUtil.getWaterView(activity, color, 0, 0, 0, 16);
        }
        // default
        else {
            heroView = TTHelperUtil.getPaperClipView(activity, color, 0, 0, 0, 16);
        }
        return heroView;
    }

//    public static int getCurrentScreenNo(String currentTab) {
//        if(currentTab.equals(Constants.SCREEN_SPELLMASTER)) {
//            return R.id.tab_spellmaster;
//        } else if(currentTab.equals(Constants.SCREEN_SWORDMASTER)) {
//            return R.id.tab_swordmaster;
//        } else if(currentTab.equals(Constants.SCREEN_HOME)) {
//            return R.id.tab_home;
//        } else if(currentTab.equals(Constants.SCREEN_TOURNAMENT)) {
//            return R.id.tab_tournament;
//        }
//        return 2;
//    }

    public static Fragment getFragment(String currentTab, SwordMasterFragment swordMasterFragment,
                                       SpellMasterFragment spellMasterFragment, HomeInfoFragment homeFragment,
                                       TournamentFragment tournamentFragment) {
        Log.d(TAG, "getFragment() called with: " + "currentTab = [" + currentTab + "], swordMasterFragment = [" + swordMasterFragment + "], spellMasterFragment = [" + spellMasterFragment + "], homeFragment = [" + homeFragment + "], tournamentFragment = [" + tournamentFragment + "]");
        if(currentTab.equals(Constants.SCREEN_SPELLMASTER)) {
            return spellMasterFragment;
        } else if(currentTab.equals(Constants.SCREEN_SWORDMASTER)) {
            return swordMasterFragment;
        } else if(currentTab.equals(Constants.SCREEN_HOME)) {
            return homeFragment;
        } else if(currentTab.equals(Constants.SCREEN_TOURNAMENT)) {
            return tournamentFragment;
        }
        return homeFragment;
    }

    public static void shareApp(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "I found this nice companion app for Tap Titans " +
                "https://play.google.com/store/apps/details?id=com.siddworks.android.taptitanssavefileviewer&hl=en" );
        intent.setType("text/plain");
        try {
            activity.startActivity(Intent.createChooser(intent, "Sharing Helper For Tap Titans:"));
        } catch (ActivityNotFoundException anfe) {
            // Hmm, market is not installed
//            Log.d(TAG, "Google Play is not installed; cannot install " +);
        }
    }

//    public static void sendGoogleFormsfeedback(Activity context) {
//        TTHelperUtil.logFeedbackClickedEvent(Constants.FEEDBACK_FORM_URL);
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                Uri.parse(Constants.FEEDBACK_FORM_URL));
//        try {
//            context.startActivity(browserIntent);
//        } catch (ActivityNotFoundException anfe) {
//            // Hmm, market is not installed
////            Log.d(TAG, "Google Play is not installed; cannot install " + packageName);
//        }
//    }

    public static void openPlayStore(Activity context) {
        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.siddworks.android.taptitanssavefileviewer&hl=en");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException anfe) {
            // Hmm, market is not installed
//            Log.d(TAG, "Google Play is not installed; cannot install " + packageName);
        }
    }

    public static void openUrl(Activity activity, String yattoUrl) {
        Log.d(TAG, " yattoUrl:" + yattoUrl);

        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(yattoUrl));
            activity.startActivity(browserIntent);
        } catch (Exception e) {
            e.printStackTrace();
            CommonUtil.showAlertOneButton(null, activity, "No Browser Installed", "Browser is required to open this URL. Please install browser app and try again");
        }
    }

    public static void showWhatsNewPopup(Activity activity) {
        String whatsNewContent = Constants.getWhatsNewContent(BuildConfig.VERSION_CODE);
        if (whatsNewContent != null) {
            CommonUtil.showAlertOneButton(null, activity, "What's New", whatsNewContent);
        }
    }

    public static void openBetaFeedback(Activity activity) {
        String whatsNewContent = Constants.getWhatsNewContent(BuildConfig.VERSION_CODE);
        if(whatsNewContent != null) {
            whatsNewContent = whatsNewContent.replaceAll("\n", "\nFeedback:\n\n");

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"siddworks.inc@gmail.com"});
            intent.putExtra(Intent.EXTRA_TEXT, whatsNewContent);
            activity.startActivity(Intent.createChooser(intent, "Send Email"));
        }
    }

    public static String getCurrentVersion() {
        return "v"+BuildConfig.VERSION_NAME;
    }

    public static void showContactDeveloper(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Please fix this:");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"siddworks.inc+tt2@gmail.com"});
        String emailContent = "Please send this email. It will help dev to the resolve problem.";

        try {
            File file = PrefUtils.getSaveFile();
            if (file.exists() && file.canRead()) {
                Uri uri = Uri.fromFile(file);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                emailContent += "\n\n~~ debug info " + file.getAbsolutePath() + " end ~~\n";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        emailContent += "\n\nYour Feedback:\n";

        intent.putExtra(Intent.EXTRA_TEXT, emailContent);
        activity.startActivity(Intent.createChooser(intent, "Send Email"));
    }

}
