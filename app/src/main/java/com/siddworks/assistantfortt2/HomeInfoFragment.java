package com.siddworks.assistantfortt2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.HashMap;
import java.util.LinkedList;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeInfoFragment extends Fragment implements FragmentInterface {

    private static boolean isLoggingEnabled = false;
    private static final String TAG = "HomeInfoFragment";

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private LinearLayout mainLayout;

    public HomeInfoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HomeInfoFragment newInstance(int sectionNumber) {
//        Log.d(isLoggingEnabled, TAG, "newInstance() called with: " + "sectionNumber = [" + sectionNumber + "]");
        HomeInfoFragment fragment = new HomeInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Log.d(isLoggingEnabled, TAG, "HomeInfoFragment.onCreateView");
        long one = System.nanoTime();
//        View rootView = inflater.inflate(R.layout.fragment_hero, container, false);
        View rootView = inflater.inflate(R.layout.fragment_hero_rv, container, false);
        Log.d(TAG, "inflater:" + (System.nanoTime() - one));

        mainLayout = (LinearLayout) rootView.findViewById(R.id.root_LinearLayout);
        DashboardActivity activity = (DashboardActivity) getActivity();
        activity.renderUi();
        return rootView;
    }

    public ViewGroup getMainView() {
        return mainLayout;
    }

    public void buildUi(Player player, HashMap<String, String> map) {
        long one = System.nanoTime();
        Log.d(TAG, " one:" + one);

//        Log.d(isLoggingEnabled, TAG, "buildUi: player:" + player);

        LinkedList<Integer> colors = ColorUtils.getCardsColor(Constants.SCREEN_HOME);

//        TTHelperUtil.buildUI(getActivity(), mainLayout, player, map, colors);
        buildUiRv(map, player, getActivity(), colors);

//        TextView spacer = TTHelperUtil.getSpacerView(getActivity());
//        mainLayout.addView(spacer);

        Log.d(TAG, "build UI:" + (System.nanoTime() - one));
    }

    private void buildUiRv(HashMap<String, String> map, Player player, Activity activity, LinkedList<Integer> colors) {
        RecyclerView rv = (RecyclerView) mainLayout.findViewById(R.id.hero_fragment_RecyclerView);
//        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(new GridLayoutManager(activity, 1));
        rv.setAdapter(new SaveFileInfoAdapter(map, player, activity, colors));
    }

    @Override
    public LinearLayout getMainLayout() {
        return mainLayout;
    }
}
