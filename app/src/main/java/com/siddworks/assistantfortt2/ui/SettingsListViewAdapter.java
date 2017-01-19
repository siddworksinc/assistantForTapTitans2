package com.siddworks.assistantfortt2.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.siddworks.assistantfortt2.R;
import com.siddworks.assistantfortt2.util.CommonUtil;
import com.siddworks.assistantfortt2.util.PrefUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by SIDD on 31-Oct-15.
 */
public class SettingsListViewAdapter extends ArrayAdapter<Map<String, Object>> {

    private boolean isLoggingEnabled = true;
    private static final String TAG = "SettingsListViewAdapter";

    private final Context context;
    private final ArrayList<Map<String, Object>> values;
    private final int color;

    public SettingsListViewAdapter(Context context, ArrayList<Map<String, Object>> values, int color_) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.color = color_;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_settings, parent, false);

        Map<String, Object> currentValue = values.get(position);

        String titleValue = (String) currentValue.get("header");
        TextView title = (TextView) rowView.findViewById(R.id.title);
        title.setText(titleValue);

        String footerValue = (String) currentValue.get("text");
        TextView value = (TextView) rowView.findViewById(R.id.summary);
        value.setText(footerValue);

        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);
        Bitmap topBmp = BitmapFactory.decodeResource(context.getResources(), (int) currentValue.get("resid"));
        topBmp = Bitmap.createScaledBitmap(topBmp, CommonUtil.dpToPx(48), CommonUtil.dpToPx(48), false);

//        Log.d(isLoggingEnabled, TAG, " title:" + titleValue);
//        Log.d(isLoggingEnabled, TAG, " value:" + footerValue);

        if(titleValue.equals("Color Style")) {
            if(footerValue.equals("Custom Color")) {
                int color = ContextCompat.getColor(context, PrefUtils.getStyleColorCustomValue());
                icon.setColorFilter(color);
            }
        } else {
            icon.setColorFilter(color);
        }
        icon.setImageBitmap(topBmp);

        return rowView;
    }

    @Override
    public int getCount() {
        if (values != null) {
            return values.size();
        }
        return 0;
    }
}
