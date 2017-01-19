package com.siddworks.assistantfortt2.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.siddworks.assistantfortt2.R;
import com.siddworks.assistantfortt2.StyleColorSelectionActivity;
import com.siddworks.assistantfortt2.util.CommonUtil;
import com.siddworks.assistantfortt2.util.Constants;
import com.siddworks.assistantfortt2.util.PrefUtils;

/**
 * Created by sidd on 13/11/16.
 */
public class ColorsGridViewAdapter extends BaseAdapter {
    private final StyleColorSelectionActivity activity;
    private final int[] colors;

    public ColorsGridViewAdapter(StyleColorSelectionActivity styleColorSelectionActivity) {
        this.activity = styleColorSelectionActivity;
        this.colors = new int[]{
                R.color.select_color_green,
                R.color.greenbody,
                R.color.tealbody,
                R.color.bluebody,
                R.color.bluebg,
                R.color.violetbody,
                R.color.select_color_violet,
                R.color.pink,
                R.color.yellowbody,
                R.color.peach,
                R.color.orangebody,
                R.color.redbody,
                R.color.select_color_red,
                R.color.brown,
                R.color.select_color_grey,
                R.color.select_color_dark_grey,
                R.color.black
        };
    }

    public int getCount() {
        return colors.length+1;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(activity);
            imageView.setLayoutParams(new GridView.LayoutParams(CommonUtil.dpToPx(48), CommonUtil.dpToPx(48)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        if(position == colors.length) {
            if(PrefUtils.getStyleColor().equals(Constants.STYLE_COLOR_DEFAULT)) {
                Bitmap topBmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_select_color_rainbow_selected);
                topBmp = Bitmap.createScaledBitmap(topBmp, CommonUtil.dpToPx(48), CommonUtil.dpToPx(48), false);
                imageView.setImageBitmap(topBmp);
            } else {
                Bitmap topBmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_select_color_rainbow);
                topBmp = Bitmap.createScaledBitmap(topBmp, CommonUtil.dpToPx(48), CommonUtil.dpToPx(48), false);
                imageView.setImageBitmap(topBmp);
            }
        } else {
            if(PrefUtils.getStyleColor().equals(Constants.STYLE_COLOR_CUSTOM)) {
                int color = PrefUtils.getStyleColorCustomValue();
                if(color == colors[position]) {
                    imageView.setImageResource(R.drawable.ic_check_circle_white_24dp);
                } else {
                    imageView.setImageResource(0);
                }
            } else {
                imageView.setImageResource(0);
            }
            imageView.setBackgroundColor(ContextCompat.getColor(activity, colors[position]));
        }
        return imageView;
    }

    public int[] getColors() {
        return colors;
    }
}
