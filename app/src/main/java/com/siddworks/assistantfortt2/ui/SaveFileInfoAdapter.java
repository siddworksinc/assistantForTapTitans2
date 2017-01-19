package com.siddworks.assistantfortt2.ui;import android.app.Activity;import android.support.v4.content.ContextCompat;import android.support.v7.widget.RecyclerView;import android.text.TextUtils;import android.view.ViewGroup;import android.widget.LinearLayout;import android.widget.TextView;import com.siddworks.assistantfortt2.bean.Player;import com.siddworks.assistantfortt2.util.ColorUtils;import com.siddworks.assistantfortt2.util.CommonUtil;import com.siddworks.assistantfortt2.util.Log;import com.siddworks.assistantfortt2.util.SaveFileJsonParser;import com.siddworks.assistantfortt2.util.TTHelperUtil;import java.util.HashMap;import java.util.Iterator;import java.util.LinkedHashMap;import java.util.LinkedList;import java.util.Map;/** * Created by SIDD on 7/15/2015. */public class SaveFileInfoAdapter extends RecyclerView.Adapter<SaveFileInfoAdapter.ViewHolderTI> {    private boolean isLoggingEnabled = false;    private static final String TAG = "SaveFileInfoAdapter";    private final Player player;    private final Activity activity;    private final LinkedList<Integer> colors;    private HashMap<String, String> dataset;    private final HashMap<Integer, String> countMap;    public class ViewHolderTI extends RecyclerView.ViewHolder {        public LinearLayout rootView;        public ViewHolderTI(LinearLayout v) {            super(v);            rootView = v;        }    }    public SaveFileInfoAdapter(HashMap<String, String> dataset_, Player player_, Activity activity_, LinkedList<Integer> colors_) {        countMap = new HashMap<>();        activity = activity_;        player = player_;        colors = colors_;        Iterator headerMapIterator = dataset_.entrySet().iterator();        LinkedHashMap<String, HashMap<String, String>> playerPropertiesMap = player_.getPropertiesMap();        int count =0;        while(headerMapIterator.hasNext()) {            String headerMapKey = (String) ((Map.Entry) headerMapIterator.next()).getKey();//            Log.d(isLoggingEnabled, TAG, " headerMapKey:" + headerMapKey);            HashMap<String, String> attributesMap = playerPropertiesMap.get(headerMapKey);//            Log.d(isLoggingEnabled, TAG, " attributesMap:" + attributesMap);            if (attributesMap != null) {                countMap.put(count, headerMapKey);                count++;            }            dataset = dataset_;        }    }    // Create new views (invoked by the layout manager)    @Override    public ViewHolderTI onCreateViewHolder(ViewGroup parent, int viewType) {        Log.d(TAG, "onCreateViewHolder" );//        // create a new view        LinearLayout linearLayout = TTHelperUtil.getLinearLayout(parent.getContext(), LinearLayout.VERTICAL, 0, 0, 0, 0);        ViewHolderTI vh = new ViewHolderTI(linearLayout);        return vh;    }    // Replace the contents of a view (invoked by the layout manager)    @Override    public void onBindViewHolder(ViewHolderTI holder, final int position) {        Log.d(TAG, "onBindViewHolder() called with: position = [" + position + "]");        LinearLayout mainLayout = holder.rootView;        int currentColor = colors.get(position);        int headerColor = ColorUtils.getHeaderColor(currentColor);        String headerMapKey = countMap.get(position);        LinkedHashMap<String, HashMap<String, String>> playerPropertiesMap = player.getPropertiesMap();        HashMap<String, String> attributesMap = playerPropertiesMap.get(headerMapKey);        TTHelperUtil.removeChildren(mainLayout);//			Log.d(isLoggingEnabled, TAG, " attributesMap:" + attributesMap);        if (attributesMap != null) {            TextView headerView = TTHelperUtil.getHeaderTextView(activity,                    SaveFileJsonParser.homeNameMap.get(headerMapKey), 48, 8, 0, 8, 0);            headerView.setTextColor(ContextCompat.getColor(activity, headerColor));            headerView.setTextSize(16);            mainLayout.addView(headerView);            // Add hero view            ViewGroup paperView = TTHelperUtil.getHeroView(activity, currentColor);            mainLayout.addView(paperView);            // child main linear layout            LinearLayout rootLinearLayout = TTHelperUtil.getLinearLayout(activity, LinearLayout.VERTICAL,                    0, 0, 0, 0);            rootLinearLayout.setBackgroundColor(ContextCompat.getColor(activity, currentColor));            rootLinearLayout.setPadding(0, CommonUtil.dpToPx(2), 0, CommonUtil.dpToPx(6));            if(paperView.getChildCount() > 1) {                paperView.addView(rootLinearLayout, 1);            } else {                paperView.addView(rootLinearLayout);            }            LinkedHashMap<String, String> keyValueMap = new LinkedHashMap<>();            // Take iterator from constants map. This will ensure ordering            HashMap<String, String> globalAttributesMap = SaveFileJsonParser.propertiesMetaMap.get(headerMapKey);            Iterator attributeMapIterator = globalAttributesMap.entrySet().iterator();            for(int i=1; attributeMapIterator.hasNext(); i++) {                String attributeKey = (String) ((Map.Entry)attributeMapIterator.next()).getKey();                Log.d(TAG, " attributeKey:" + attributeKey);                String attributeKeyTranslated = globalAttributesMap.get(attributeKey);                String attributeValue = attributesMap.get(attributeKeyTranslated);                Log.d(TAG, " attributeValue:" + attributeValue);                if(!TextUtils.isEmpty(attributeKey) && !TextUtils.isEmpty(attributeValue)) {                    keyValueMap.put(attributeKeyTranslated, attributeValue);                }            }//				Log.logToFile();            LinearLayout keyValueLayout = TTHelperUtil.getKeyValueLayout(activity, keyValueMap);            rootLinearLayout.addView(keyValueLayout);            if(position == countMap.size() -1) {                TextView spacerView = TTHelperUtil.getSpacerView(activity);                mainLayout.addView(spacerView);                TextView spacerView2 = TTHelperUtil.getSpacerView(activity);                mainLayout.addView(spacerView2);            }        }        long end = System.nanoTime();        Log.d(TAG, " end:" + end);    }   // Return the size of your dataset (invoked by the layout manager)    @Override    public int getItemCount() {        if(countMap == null) {//            Log.d(isLoggingEnabled, TAG, "getItemCount ret 0:");            return 0;        }//        Log.d(isLoggingEnabled, TAG, "getItemCount ret:"+countMap.size());        return countMap.size();    }}