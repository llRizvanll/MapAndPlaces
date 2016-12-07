package com.co.nightowl.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Rizvan Hawaldar on 06/12/16.
 */
public class Connectivity {
    private Context _context;
    public static String networkMsg = "Please enable internet connection!";

    public Connectivity(){}

    /**
     * Check for the Internet connection
     * @param context
     * @return true -> connected , false otherwise
     */
    public static boolean isAlive(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null ) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED && info[i].isConnectedOrConnecting())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
