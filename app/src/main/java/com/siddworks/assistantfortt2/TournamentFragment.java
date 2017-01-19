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
import com.siddworks.assistantfortt2.util.ColorUtils;
import com.siddworks.assistantfortt2.util.Constants;
import com.siddworks.assistantfortt2.util.Log;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TournamentFragment extends Fragment implements FragmentInterface {

    private boolean isLoggingEnabled = false;
    private static final String TAG = "TournamentFragment";

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private LinearLayout mainLayout;

    public TournamentFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TournamentFragment newInstance(int sectionNumber) {
        TournamentFragment fragment = new TournamentFragment();
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
        Log.d( TAG, " onCreateView");
        activity.renderUi();
        return rootView;
    }

    public ViewGroup getMainView() {
        return mainLayout;
    }

    public void buildUi(Player player, HashMap<String, String> map ) {
        Log.d( TAG, "buildUi: player:" + player);

        LinkedList<Integer> colors = ColorUtils.getCardsColor(Constants.SCREEN_TOURNAMENT);

        buildUiRv(player, getActivity(), colors);
    }

    private void buildUiRv(Player player, FragmentActivity activity, LinkedList<Integer> colors) {
        RecyclerView rv = (RecyclerView) mainLayout.findViewById(R.id.hero_fragment_RecyclerView);
        rv.setLayoutManager(new GridLayoutManager(activity, 1));
//        rv.setAdapter(new TournamentInfoAdapter(player, activity, colors));
    }


    @Override
    public LinearLayout getMainLayout() {
        return mainLayout;
    }
}
