package com.co.nightowl.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.co.nightowl.R;

/**
 * Created by Rizvan Hawaldar on 06/12/16.
 */

public class DataStore {
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static DataStore instance = null;
    private static SharedPreferences dataInstance;

    private DataStore(){}

    public static synchronized DataStore getInstance(){
        if (instance == null){
            setPreference();
        }
        return instance;
    }

    private static void setPreference(){
        dataInstance = AppContext.getThis().getSharedPreferences(AppContext.getThis().getString(R.string.data_file), Context.MODE_PRIVATE);
    }

    private static synchronized SharedPreferences.Editor getData(){
        if (dataInstance==null) {
            dataInstance = AppContext.getThis().getSharedPreferences(AppContext.getThis().getString(R.string.data_file), Context.MODE_PRIVATE);
        }
        return dataInstance.edit();
    }

    public static void setAccessToken(final String accessToken){
        getData().putString(ACCESS_TOKEN,accessToken).commit();

    }

    public static String getAccessToken(){
        return getInstance().dataInstance.getString(ACCESS_TOKEN,"");
    }
}
