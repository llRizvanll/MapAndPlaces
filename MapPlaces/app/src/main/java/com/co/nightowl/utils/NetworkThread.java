package com.co.nightowl.utils;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

/**
 * Created by Rizvan Hawaldar on 06/12/16.
 */

public class NetworkThread {

    //Tag
    private final static String NT_TAG = "NETWORK_THREAD";

    /**
     * Only for emulator i.e 10.0.2.2
     * others : localhost:8080 or 127.0.0.1:8080 or domain path
     */
    private final String DOMAIN = "http://10.0.2.2:8080/api/v1";
    //private final String DOMAIN = "http://localhost:8080/api/v1";

    public NetworkThread(){}

    public void postCredentials(final JSONObject jsonObject, final DataResponseListener listener){
        final String path = DOMAIN+"/register/";
        AndroidNetworking.post(path).addJSONObjectBody(jsonObject).setTag(NT_TAG).setPriority(Priority.HIGH).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }

            @Override
            public void onError(ANError anError) {
                listener.onError(anError);
            }
        });
    }

    public void getPlacesList(final DataResponseListener listener){
        final String path = DOMAIN+"/places/";
        Log.d("TOKEN ",DataStore.getAccessToken());
        AndroidNetworking.get(path).addHeaders("Authorization",DataStore.getAccessToken()).setTag(NT_TAG).setPriority(Priority.HIGH).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }

            @Override
            public void onError(ANError anError) {
                listener.onError(anError);
            }
        });
    }

    public void postRentals(final JSONObject jsonObject, final DataResponseListener listener){
        final String path = DOMAIN+"/rent/";
        AndroidNetworking.post(path).addHeaders("Authorization",DataStore.getAccessToken()).addJSONObjectBody(jsonObject).setTag(NT_TAG).setPriority(Priority.HIGH).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }

            @Override
            public void onError(ANError anError) {
                listener.onError(anError);
            }
        });
    }

    public interface DataResponseListener{
        void onResponse(JSONObject response);
        void onError(ANError anError);
    }
}
