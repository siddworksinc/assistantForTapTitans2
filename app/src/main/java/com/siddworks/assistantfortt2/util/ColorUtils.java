package com.siddworks.assistantfortt2.util;

import android.app.Activity;
import android.support.v4.content.ContextCompat;

import com.siddworks.assistantfortt2.DashboardActivity;
import com.siddworks.assistantfortt2.R;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by SIDD on 7/27/2015.
 */
public class ColorUtils {
    private static final String TAG = "ColorUtils";
    private static boolean isLoggingEnabled = true;


    public static LinkedList<Integer> getCardsColor(String fragment) {
        if(fragment != null) {
            // Default Style
            if(PrefUtils.getStyleColor().equals(Constants.STYLE_COLOR_DEFAULT)) {
                if(fragment.equals(Constants.SCREEN_SWORDMASTER)) {
                    LinkedList<Integer> colors = new LinkedList<>();
                    colors.add(R.color.tealbody);
                    colors.add(R.color.tealbody);
                    return colors;
                } else if(fragment.equals(Constants.SCREEN_SPELLMASTER)) {
                    LinkedList<Integer> colors = new LinkedList<>();
                    colors.add(R.color.peach);
                    colors.add(R.color.peach);
                    return colors;
                } else if(fragment.equals(Constants.SCREEN_HOME)) {
                    LinkedList<Integer> colors = new LinkedList<>();
                    colors.add(R.color.greenbody);
                    colors.add(R.color.orangebody);
                    colors.add(R.color.violetbody);
                    colors.add(R.color.redbody);
                    colors.add(R.color.bluebody);
                    colors.add(R.color.yellowbody);
                    colors.add(R.color.tealbody);
                    colors.add(R.color.peach);
                    colors.add(R.color.greenbody);
                    colors.add(R.color.orangebody);
                    colors.add(R.color.violetbody);
                    colors.add(R.color.redbody);
                    colors.add(R.color.bluebody);
                    colors.add(R.color.yellowbody);
                    colors.add(R.color.tealbody);
                    colors.add(R.color.peach);
                    return colors;
                } else if(fragment.equals(Constants.SCREEN_TOURNAMENT)) {
                    LinkedList<Integer> colors = new LinkedList<>();
                    colors.add(R.color.redbody);
                    colors.add(R.color.redbody);
                    return colors;
                }
            }
            // If user has set custom color
            else if(PrefUtils.getStyleColor().equals(Constants.STYLE_COLOR_CUSTOM)) {
                int color = PrefUtils.getStyleColorCustomValue();
                LinkedList<Integer> colors = new LinkedList<>();
                colors.add(color);
                colors.add(color);
                colors.add(color);
                colors.add(color);
                colors.add(color);
                colors.add(color);
                colors.add(color);
                colors.add(color);
                colors.add(color);
                colors.add(color);
                colors.add(color);
                colors.add(color);
                colors.add(color);
                colors.add(color);
                colors.add(color);
                colors.add(color);
                return colors;
            }
        }

        return null;
    }

    public static int getColorForTab(DashboardActivity dashboardActivity, String currentTab) {
        Log.d(TAG, "getColorForTab() called with: dashboardActivity = [" + dashboardActivity + "], currentTab = [" + currentTab + "]");
        HashMap<String, Integer> colors = new HashMap<>();
        colors.put(Constants.SCREEN_SPELLMASTER,  R.color.violetHeader);
        colors.put(Constants.SCREEN_SWORDMASTER,  R.color.brown);
        colors.put(Constants.SCREEN_HOME,  R.color.pink);
        colors.put(Constants.SCREEN_TOURNAMENT,  R.color.blueHeader);

//        colors.put(Constants.SCREEN_SPELLMASTER,  R.color.black);
//        colors.put(Constants.SCREEN_SWORDMASTER,  R.color.black);
//        colors.put(Constants.SCREEN_HOME,  R.color.black);
//        colors.put(Constants.SCREEN_TOURNAMENT,  R.color.black);

//        if(currentTab.equals(Constants.SCREEN_SPELLMASTER)) {
//            return ContextCompat.getColor(dashboardActivity, R.color.violetHeader);
//        } else if(currentTab.equals(Constants.SCREEN_SWORDMASTER)) {
//            return ContextCompat.getColor(dashboardActivity, R.color.brown);
//        } else if(currentTab.equals(Constants.SCREEN_HOME)) {
//            return ContextCompat.getColor(dashboardActivity, R.color.pink);
//        } else if(currentTab.equals(Constants.SCREEN_TOURNAMENT)) {
//            return ContextCompat.getColor(dashboardActivity, R.color.blueHeader);
//        }
//        return ContextCompat.getColor(dashboardActivity, R.color.violetHeader);
        Integer integer = colors.get(currentTab);
        if (integer != null) {
            return ContextCompat.getColor(dashboardActivity, integer);
        }
        return ContextCompat.getColor(dashboardActivity, R.color.pink);
    }

    public static int getHeaderColor(int color) {
        int colorHeader = R.color.greenHeader;
        int colorContent = R.color.greenbody;
        switch (color) {
            case R.color.greenbody:
                colorHeader = R.color.greenHeader;
                colorContent = R.color.greenbody;
                break;
            case R.color.orangebody:
                colorHeader = R.color.orangeHeader;
                colorContent = R.color.orangebody;
                break;
            case R.color.violetbody:
                colorHeader = R.color.violetHeader;
                colorContent = R.color.violetbody;
                break;
            case R.color.redbody:
                colorHeader = R.color.redHeader;
                colorContent = R.color.redbody;
                break;
            case R.color.bluebody:
                colorHeader = R.color.blueHeader;
                colorContent = R.color.bluebody;
                break;
            case R.color.yellowbody:
                colorHeader = R.color.yellowHeader;
                colorContent = R.color.yellowbody;
                break;
            case R.color.tealbody:
                colorHeader = R.color.tealHeader;
                colorContent = R.color.tealbody;
                break;
            case R.color.peach:
                colorHeader = R.color.peach;
                colorContent = R.color.peach;
                break;

            default:
                colorHeader = color;
        }
        return colorHeader;
    }

    public static int getResolvedColor(Activity activity, int color) {
        return ContextCompat.getColor(activity, color);
    }

    public static int getAccentColor(Activity activity, int defaultColor) {
        int color;
        if(PrefUtils.getStyleColor().equals(Constants.STYLE_COLOR_CUSTOM)) {
            color = PrefUtils.getStyleColorCustomValue();
        } else {
            color = defaultColor;
        }
        return color;
    }

//    public static int getNextColor(int count, String type) {
//        int colorHeader = R.color.greenHeader;
//        int colorContent = R.color.greenbody;
//        switch (count % 8) {
//            case 0:
//                colorHeader = R.color.greenHeader;
//                colorContent = R.color.greenbody;
//                break;
//            case 1:
//                colorHeader = R.color.orangeHeader;
//                colorContent = R.color.orangebody;
//                break;
//            case 2:
//                colorHeader = R.color.violetHeader;
//                colorContent = R.color.violetbody;
//                break;
//            case 3:
//                colorHeader = R.color.redHeader;
//                colorContent = R.color.redbody;
//                break;
//            case 4:
//                colorHeader = R.color.blueHeader;
//                colorContent = R.color.bluebody;
//                break;
//            case 5:
//                colorHeader = R.color.yellowHeader;
//                colorContent = R.color.yellowbody;
//                break;
//            case 6:
//                colorHeader = R.color.tealHeader;
//                colorContent = R.color.tealbody;
//                break;
//            case 7:
//                colorHeader = R.color.peach;
//                colorContent = R.color.peach;
//                break;
//
//            default:
//                break;
//        }
//        if(type.equals("header"))
//            return colorHeader;
//        else
//            return colorContent;
//    }

//    public static int getNextColor2(int count, String type) {
//        int colorHeader = R.color.greenHeader;
//        int colorContent = R.color.greenbody;
//        switch (count % 2) {
//            case 0:
//                colorHeader = R.color.greenHeader;
//                colorContent = R.color.greenbody;
//                break;
//            case 1:
//                colorHeader = R.color.orangeHeader;
//                colorContent = R.color.orangebody;
//                break;
//            default:
//                break;
//        }
//        if(type.equals("header"))
//            return colorHeader;
//        else
//            return colorContent;
//    }


}



