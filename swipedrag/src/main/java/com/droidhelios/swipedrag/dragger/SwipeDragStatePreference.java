package com.droidhelios.swipedrag.dragger;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SwipeDragStatePreference {

    private static final String HOME_PAGE_LIST = "home_page_list";

    private final Context context;

    public SwipeDragStatePreference(Context context) {
        this.context = context;
    }

    /**
     * @param typeCast : new TypeToken<List<ModelName>>() {}
     */
    public <T> List<T> getRankList(TypeToken<List<T>> typeCast) {
        return parseJson(getData(context, HOME_PAGE_LIST), typeCast);
    }

    /**
     * @param typeCast : new TypeToken<List<ModelName>>() {}
     */
    public <T> void saveRankList(final Context context, final List<T> jsonArray, TypeToken<List<T>> typeCast) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        String finalList = gson.toJson(jsonArray, typeCast.getType());
        setData(context, HOME_PAGE_LIST, finalList);
    }

    public String getSavedListJsonData(Context context) {
        return getData(context, HOME_PAGE_LIST);
    }

    public void setSavedListJsonData(Context context, String value) {
        setData(context, HOME_PAGE_LIST, value);
    }

    private <T> List<T> parseJson(String jsonContent, TypeToken<List<T>> typeCast) {
        try {
            return new Gson().fromJson(jsonContent, typeCast.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String getData(Context context, String key) {
        if (context != null) {
            return getDefaultSharedPref(context).getString(key, "");
        } else {
            return "";
        }
    }

    private void setData(Context context, String key, String value) {
        if (context != null && value != null) {
            final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    private SharedPreferences getDefaultSharedPref(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }
}
