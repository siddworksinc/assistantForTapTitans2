package com.siddworks.assistantfortt2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.siddworks.assistantfortt2.bean.Player;
import com.siddworks.assistantfortt2.ui.SaveFileInfoAdapter;
import com.siddworks.assistantfortt2.util.ColorUtils;
import com.siddworks.assistantfortt2.util.Constants;
import com.siddworks.assistantfortt2.util.Log;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SwordMasterFragment extends Fragment implements FragmentInterface{

    private boolean isLoggingEnabled = true;
    private static final String TAG = "HomeInfoFragment";

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private LinearLayout mainLayout;

    public SwordMasterFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SwordMasterFragment newInstance(int sectionNumber) {
        SwordMasterFragment fragment = new SwordMasterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        long one = System.nanoTime();
        View rootView = inflater.inflate(R.layout.fragment_hero_rv, container, false);
        long two = System.nanoTime();
        Log.d( TAG, " inflate:" + (two - one));

        mainLayout = (LinearLayout) rootView.findViewById(R.id.root_LinearLayout);
        DashboardActivity activity = (DashboardActivity) getActivity();
        Log.d( TAG, " onCreateView");
        activity.renderUi();
        return rootView;
    }

    public ViewGroup getMainView() {
        return mainLayout;
    }

    public void buildUi(Player player, LinkedHashMap<String, String> map ) {
        long start = System.nanoTime();
        Log.d( TAG, " one:" + start);
//        Log.d( TAG, "buildUi: player:" + player);

//        LinkedHashMap<String, String> newMap = TTHelperUtil.addRelicPredictionMap(player, map, "worldOneMap", "relicPredictionMapSwordMaster");
        long relicMap = System.nanoTime();
        Log.d( TAG, " relicMap:" + (relicMap - start));

        LinkedList<Integer> colors = ColorUtils.getCardsColor(Constants.SCREEN_SWORDMASTER);

        buildUiRv(map, player, getActivity(), colors);

        long end = System.nanoTime();
        Log.d( TAG, " build UI:" + (end - start));
    }

    private void buildUiRv(LinkedHashMap<String, String> map, Player player, FragmentActivity activity, LinkedList<Integer> colors) {
        RecyclerView rv = (RecyclerView) mainLayout.findViewById(R.id.hero_fragment_RecyclerView);
        rv.setLayoutManager(new GridLayoutManager(activity, 1));
        rv.setAdapter(new SaveFileInfoAdapter(map, player, activity, colors));
    }

    @Override
    public LinearLayout getMainLayout() {
        return mainLayout;
    }
}
