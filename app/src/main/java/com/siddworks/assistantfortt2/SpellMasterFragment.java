package com.siddworks.assistantfortt2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siddworks.assistantfortt2.bean.Player;
import com.siddworks.assistantfortt2.ui.SaveFileInfoAdapter;
import com.siddworks.assistantfortt2.util.ColorUtils;
import com.siddworks.assistantfortt2.util.CommonUtil;
import com.siddworks.assistantfortt2.util.Constants;
import com.siddworks.assistantfortt2.util.Log;
import com.siddworks.assistantfortt2.util.TTHelperUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SpellMasterFragment extends Fragment implements FragmentInterface {

    private boolean isLoggingEnabled = true;
    private static final String TAG = "HomeInfoFragment";

    private static final int TEXT_VIEW_HEADER_HEIGHT = 36;
    private static final int PADDING_TOP = 12;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private LinearLayout mainLayout;

    public SpellMasterFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SpellMasterFragment newInstance(int sectionNumber) {
        SpellMasterFragment fragment = new SpellMasterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hero_rv, container, false);
        mainLayout = (LinearLayout) rootView.findViewById(R.id.root_LinearLayout);
        DashboardActivity activity = (DashboardActivity) getActivity();
        Log.d(TAG, " onCreateView");
        activity.renderUi();
        return rootView;
    }

    public ViewGroup getMainView() {
        return mainLayout;
    }

    public void buildUi(Player player, LinkedHashMap<String, String> map ) {
        Log.d(TAG, "buildUi");
        long start = System.nanoTime();

        LinkedHashMap<String, HashMap<String, String>> propertiesMap = player.getPropertiesMap();
//        Log.d(TAG, " propertiesMap:" + propertiesMap);

        HashMap<String, String> playerAttributesMap = propertiesMap.get("worldTwoMap");
        Log.d(TAG, " playerAttributesMap:" + playerAttributesMap);
        if(playerAttributesMap == null) {
            TTHelperUtil.removeChildren(mainLayout);
            addNoWorldTwoMessage(mainLayout);
            return;
        }

//        LinkedHashMap<String, String> newMap = TTHelperUtil.addRelicPredictionMap(player, map, "worldTwoMap", "relicPredictionMapSpellMaster");
        long relicMap = System.nanoTime();
        Log.d(TAG, " relicMap:" + (relicMap - start));

        LinkedList<Integer> colors = ColorUtils.getCardsColor(Constants.SCREEN_SPELLMASTER);

        buildUiRv(map, player, getActivity(), colors);

        long end = System.nanoTime();
        Log.d(TAG, " build UI:" + (end - start));
    }

    private void buildUiRv(LinkedHashMap<String, String> newMap, Player player, FragmentActivity activity, LinkedList<Integer> colors) {
        RecyclerView rv = (RecyclerView) mainLayout.findViewById(R.id.hero_fragment_RecyclerView);
        rv.setLayoutManager(new GridLayoutManager(activity, 1));
        rv.setAdapter(new SaveFileInfoAdapter(newMap, player, activity, colors));
    }

    private void addNoWorldTwoMessage(LinearLayout mainLayout) {

        // Add header
        TextView mainHeaderTextView = TTHelperUtil.getHeaderTextView(getActivity(), "Spell Master", 48, 0, 0, 0, 0);
        mainHeaderTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.peach));
        mainLayout.addView(mainHeaderTextView);

        // Add hero view
        ViewGroup paperView = TTHelperUtil.getHeroView(getActivity(), R.color.peach);
        mainLayout.addView(paperView);

        // child main linear layout
        LinearLayout rootHeroLinearLayout = TTHelperUtil.getLinearLayout(getActivity(), LinearLayout.VERTICAL,
                0, 0, 0, 0);
        rootHeroLinearLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.peach));
        rootHeroLinearLayout.setPadding(0, CommonUtil.dpToPx(2), 0, CommonUtil.dpToPx(6));
        paperView.addView(rootHeroLinearLayout, 1);

        Bitmap detectiveBmp = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.no_data);
        detectiveBmp = Bitmap.createScaledBitmap(detectiveBmp, CommonUtil.dpToPx(200), CommonUtil.dpToPx(200), false);
        BitmapDrawable bitmapDrawableScaled = new BitmapDrawable(detectiveBmp);
        ImageView image = new ImageView(getActivity());
        image.setImageBitmap(bitmapDrawableScaled.getBitmap());
        image.setPadding(CommonUtil.dpToPx(8), CommonUtil.dpToPx(8),
                CommonUtil.dpToPx(8), CommonUtil.dpToPx(8));

        TextView headerTextView = TTHelperUtil.getHeaderTextView(getActivity(), "No Spell Master Info",
                TEXT_VIEW_HEADER_HEIGHT, 0, PADDING_TOP, 0, 4);
        TextView noInfoText = TTHelperUtil.getTextView(getActivity(), "Looks like this save file does not have any Spell Master" +
                " info (before world two was introduced). " +
                        "Please visit this section again after updating game or unlocking world two.",
                R.color.white);
        noInfoText.setPadding(CommonUtil.dpToPx(8), CommonUtil.dpToPx(8),
                CommonUtil.dpToPx(8), CommonUtil.dpToPx(8));

        noInfoText.setTextSize(14);
        noInfoText.setSingleLine(false);
        rootHeroLinearLayout.addView(image);
        rootHeroLinearLayout.addView(headerTextView);
        rootHeroLinearLayout.addView(noInfoText);
    }

    @Override
    public LinearLayout getMainLayout() {
        return mainLayout;
    }
}
