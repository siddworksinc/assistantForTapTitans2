package com.siddworks.assistantfortt2.ui;

import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.siddworks.assistantfortt2.R;
import com.siddworks.assistantfortt2.StyleSelectionActivity;
import com.siddworks.assistantfortt2.util.Log;

import java.util.ArrayList;
import java.util.List;

public class StyleSelectionCardAdapter extends PagerAdapter implements CardAdapter {

    private boolean isLoggingEnabled = true;
    private static final String TAG = "StyleSelectionCardAdapt";

    private final String selected;
    private final StyleSelectionActivity context;
    private List<CardView> views;
    private ArrayList<ContributeCardData> data;
    private float baseElevation;

    public StyleSelectionCardAdapter(StyleSelectionActivity context_, ArrayList<ContributeCardData> data_, String selected_) {

        this.data = data_;
        this.context = context_;
        this.selected = selected_;

        views = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            views.add(null);
        }
    }

    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        ContributeCardData contributeCardData = data.get(position);

        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.row_select_style, container, false);
        container.addView(view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (baseElevation == 0) {
            baseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation( baseElevation * MAX_ELEVATION_FACTOR );
        views.set(position, cardView);
        boolean isSelected = false;

        TextView titleTV = (TextView) view.findViewById(R.id.contribute_title);
        String title = contributeCardData.getTitle();
        if(title.equals(selected)) {
            isSelected = true;
        }
        Log.d(TAG, " titleTV:" + title);
        titleTV.setText(title);

        if(isSelected) {
            Drawable myDrawable =  view.getContext().getResources().getDrawable(R.drawable.dr_checked);
            titleTV.setCompoundDrawablesWithIntrinsicBounds(null, null, myDrawable, null);
        } else {
            titleTV.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }

        ImageView icon = (ImageView) view.findViewById(R.id.contribute_icon);
        icon.setImageResource(contributeCardData.getIcon());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.itemSelected(position);
            }
        });

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        views.set(position, null);
    }

}

