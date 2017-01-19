package com.siddworks.assistantfortt2.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.siddworks.assistantfortt2.BuildConfig;
import com.siddworks.assistantfortt2.R;
import com.siddworks.assistantfortt2.TTHelperApplication;

import java.io.File;

/**
 * Created by sidd on 15/1/17.
 */
public class PrefUtils {
    public static File getSaveFile() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                TTHelperApplication.getInstance());
        String saveFileLocation = prefs.getString("SaveFileLocation", null);
        if(saveFileLocation != null) {
            return new File(saveFileLocation);
        } else {
            return SaveFileJsonParser.getDefaultSaveFile();
        }
    }

    public static String getDefaultScreen() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                TTHelperApplication.getInstance());
        String contentType = prefs.getString(Constants.DEFAULT_SCREEN, Constants.SCREEN_HOME);
        return contentType;
    }

    public static String getStyleColor() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                TTHelperApplication.getInstance());
        String retval = prefs.getString(Constants.STYLE_COLOR, Constants.STYLE_COLOR_DEFAULT);
        return retval;
    }

    public static int getStyleColorCustomValue() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                TTHelperApplication.getInstance());
        int retval = prefs.getInt(Constants.STYLE_COLOR_CUSTOM_VALUE, R.color.redbody);
        return retval;
    }

    public static String getStyleVisual() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                TTHelperApplication.getInstance());
        String visualStyle = prefs.getString(Constants.STYLE_VISUAL, Constants.STYLE_VISUAL_PAPER_CUT);
        return visualStyle;
    }

    public static String getStyleContent() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                TTHelperApplication.getInstance());
        String contentType = prefs.getString(Constants.STYLE_CONTENT, Constants.STYLE_CONTENT_DOUBLE_TREAT);
        return contentType;
    }

//    public static Object getDefaultScreenDrawable() {
//        String defaultScreen = getDefaultScreen();
//        if(defaultScreen.equals(Constants.SCREEN_HOME)) {
//            return R.drawable.ic_home_white_24dp;
//        } else if(defaultScreen.equals(Constants.SCREEN_SWORDMASTER)) {
//            return R.drawable.ic_swordmaster;
//        } else if(defaultScreen.equals(Constants.SCREEN_SPELLMASTER)) {
//            return R.drawable.ic_spellmaster;
//        } else if(defaultScreen.equals(Constants.SCREEN_TOURNAMENT)) {
//            return R.drawable.ic_assessment_white_24dp;
//        }
//        else {
//            return R.drawable.ic_home_white_24dp;
//        }
//    }

    public static Object getStyleColorDrawable() {
        String colorStyle = getStyleColor();
        if(colorStyle.equals(Constants.STYLE_COLOR_DEFAULT)) {
            return R.drawable.dr_color_palette_rainbow;
        } else if(colorStyle.equals(Constants.STYLE_COLOR_CUSTOM)) {
            return R.drawable.dr_color_palette;
        }
        else {
            return R.drawable.dr_color_palette_rainbow;
        }
    }

    public static Object getStyleContentDrawable() {
        String contentStyle = getStyleContent();
        if(contentStyle.equals(Constants.STYLE_CONTENT_DOUBLE_TREAT)) {
            return R.drawable.settings_double_treat;
        } else if(contentStyle.equals(Constants.STYLE_CONTENT_FIFTY_FIFTY)) {
            return R.drawable.settings_fifty_fifty;
        }
        else {
            return R.drawable.settings_double_treat;
        }
    }

    public static Object getStyleVisualDrawable() {
        String visualStyle = getStyleVisual();
        if(visualStyle.equals(Constants.STYLE_VISUAL_CARDS)) {
            return R.drawable.settings_card;
        } else if(visualStyle.equals(Constants.STYLE_VISUAL_PAPER_CUT)) {
            return R.drawable.settings_papercut;
        } else if(visualStyle.equals(Constants.STYLE_VISUAL_WATER)) {
            return R.drawable.settings_water;
        }
        else {
            return R.drawable.settings_papercut;
        }
    }

    public static void setStyleContent(String contentStyle) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                TTHelperApplication.getInstance());
        prefs.edit().putString(Constants.STYLE_CONTENT, contentStyle).apply();
    }

    public static void setStyleVisual(String visualStyle) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                TTHelperApplication.getInstance());
        prefs.edit().putString(Constants.STYLE_VISUAL, visualStyle).apply();
    }

    public static void setStyleColor(String styleColor) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(TTHelperApplication.getInstance());
        sp.edit().putString(Constants.STYLE_COLOR, styleColor).apply();
    }

    public static void setStyleCustomValue(int color) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(TTHelperApplication.getInstance());
        sp.edit().putInt(Constants.STYLE_COLOR_CUSTOM_VALUE, color).apply();
    }

    public static boolean setCurrentVersion() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                TTHelperApplication.getInstance());
        int currentVersion = prefs.getInt("CurrentVersionCode", -1);
        if (currentVersion == -1) {
            currentVersion = BuildConfig.VERSION_CODE;
            prefs.edit().putInt("CurrentVersionCode", currentVersion).apply();
        } else {
            if(currentVersion != BuildConfig.VERSION_CODE) {
                setLaunchCountSinceUpdate(0);
                setPromptForBetaFeedback(true);
                prefs.edit().putInt("PreviousVersionCode", currentVersion).apply();
                currentVersion = BuildConfig.VERSION_CODE;
                prefs.edit().putInt("CurrentVersionCode", currentVersion).apply();
                return true;
            }
        }
        return false;
    }

    private static void setLaunchCountSinceUpdate(int i) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(TTHelperApplication.getInstance());
        sp.edit().putInt("LaunchCountSinceUpdate", i).apply();
    }

    public static void setPromptForBetaFeedback(boolean b) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(TTHelperApplication.getInstance());
        sp.edit().putBoolean("ShowPromptForBetaFeedback", b).apply();
    }

    public static void setSaveFileLocation(String saveFileLocation) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                TTHelperApplication.getInstance());
        prefs.edit().putString("SaveFileLocation", saveFileLocation).apply();
    }

}
