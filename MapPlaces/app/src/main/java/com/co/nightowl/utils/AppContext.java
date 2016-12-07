package com.co.nightowl.utils;

import android.app.Application;

/**
 * Created by Rizvan Hawaldar on 06/12/16.
 */

public class AppContext extends Application {

    private static AppContext instance ;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized AppContext getThis(){
        return instance;
    }
}
