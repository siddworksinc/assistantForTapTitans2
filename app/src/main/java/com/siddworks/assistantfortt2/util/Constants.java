package com.siddworks.assistantfortt2.util;

/**
 * Created by sidd on 15/1/17.
 */
public class Constants {
    public static boolean isGlobalLoggingEnabled = true;
    public static boolean isBetaUser = true;

    // Screens
    public static final String DEFAULT_SCREEN = "defaultScreen";
    public static final String SCREEN_HOME = "Home";
    public static final String SCREEN_SWORDMASTER = "SwordMaster";
    public static final String SCREEN_SPELLMASTER = "SpellMaster";
    public static final String SCREEN_TOURNAMENT = "Tournament";

    // Styles
    public static final String STYLE_COLOR = "ColorStyle";
    public static final String STYLE_COLOR_DEFAULT = "Rainbow - Default";
    public static final String STYLE_COLOR_CUSTOM = "Custom Color";
    public static final String STYLE_COLOR_CUSTOM_VALUE = "ColorStyleValue";
    public static final String KEY_COLOR_CURRENT = "CurrentColor";

    public static final String STYLE_CONTENT = "contentStyle";
    public static final String STYLE_CONTENT_DOUBLE_TREAT = "Double Treat";
    public static final String STYLE_CONTENT_FIFTY_FIFTY = "Fifty Fifty";

    public static final String STYLE_VISUAL = "visualStyle";
    public static final String STYLE_VISUAL_CARDS = "Cards";
    public static final String STYLE_VISUAL_PAPER_CUT = "Paper Cut";
    public static final String STYLE_VISUAL_WATER = "Water";
    public static final String KEY = "key";

    public static String getWhatsNewContent(int versionCode) {
        if(versionCode == 29 || versionCode == 28 || versionCode == 30 || versionCode == 31) {
            String retVal = " - Added Artifact Calculator (Finally :p).\n\n" +
                    " - Added Launch Game Shortcut. Launch game from App itself.\n\n" +
                    " - Added Whats new section in Settings. Never miss out in newly added features.\n\n" +
                    " - Bug Fixes for \"Error Reading Data From Save File Popup\" Error";
            return retVal;
        }
        return null;
    }
}
