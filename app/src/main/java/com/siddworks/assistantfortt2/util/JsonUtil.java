package com.siddworks.assistantfortt2.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.siddworks.assistantfortt2.R;
import com.siddworks.assistantfortt2.bean.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by SIDD on 7/18/2015.
 */
public class JsonUtil {

    private static final String TAG = "JsonUtil";

    public static String parseBootStrapData(Context mContext) {
        String bootstrapJson = null;
        try {
            // Load data from bootstrap raw resource
            bootstrapJson = parseResource(mContext, R.raw.new_player);
        } catch (IOException ex) {
            Log.e(TAG, "parseBootStrapData ex:"+ex);
            ex.printStackTrace();
        }
        return bootstrapJson;
    }

    public static Player makePlayerFromJson(String templateJson) {
        Player templates = null;

        Gson gson = new Gson();
        templates = gson.fromJson(templateJson, Player.class);

        return templates;
    }

    public static String parseResource(Context context, int resource) throws IOException {
        InputStream is = context.getResources().openRawResource(resource);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        return writer.toString();
    }

    public static String makeJson(Object obj) {
        Gson gson = new Gson();
        String jsonObj = gson.toJson(obj);
        return jsonObj;
    }

    public static String makeJson(Object obj, boolean shouldPrettyPrint) {
        Gson gson = null;
        if (shouldPrettyPrint) {
            gson = new GsonBuilder().setPrettyPrinting().create();
        } else {
            gson = new Gson();
        }
        String jsonObj = gson.toJson(obj);
        return jsonObj;
    }

    public static Object makeObject(String json, Class objClass) {
        Gson gson = new Gson();
        Object reports = gson.fromJson(json, objClass);
        return reports;
    }
}
