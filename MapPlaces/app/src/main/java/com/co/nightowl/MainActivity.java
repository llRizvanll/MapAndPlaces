package com.co.nightowl;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.co.nightowl.Model.PlacesDataModel;
import com.co.nightowl.utils.AppToast;
import com.co.nightowl.utils.Connectivity;
import com.co.nightowl.utils.DataStore;
import com.co.nightowl.utils.NetworkThread;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN";
    private Activity activity;
    @Bind(R.id.input_email) EditText inputEmailView;
    @Bind(R.id.input_pwd) EditText inputPwdView;
    @Bind(R.id.btn_newuser) TextView newUserView;
    @Bind(R.id.progress_view) ProgressBar progressBar;

    @OnClick(R.id.btn_newuser)
    public void newUserScreen(){
        newUserView.setVisibility(View.GONE);
        AppToast.show(activity,"Please enter your email and password for first time registration");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        ButterKnife.bind(activity);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.GONE);

        //check accessToken available
        if (!DataStore.getAccessToken().isEmpty()){
            progressBar.setVisibility(View.VISIBLE);
            getPlacesListData();
        }
        else
            progressBar.setVisibility(View.GONE);

        //Enable new user view
        newUserView.setVisibility(View.VISIBLE);
    }

    @OnEditorAction(R.id.input_pwd)
    protected boolean onEditorAction(int key){
        if (key == EditorInfo.IME_ACTION_DONE){
            //Method call
            if (Connectivity.isAlive(activity)) {
                final String getEmail = inputEmailView.getText().toString();
                final String getPwd  = inputPwdView.getText().toString();

                if (getEmail!=null && getEmail.trim().toString().length() > 0) {
                    if (getPwd!=null && getPwd.trim().toString().length() > 0 ) {
                        try{
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("email",getEmail);
                            jsonObject.put("password",getPwd);
                            authenticateUser(jsonObject);
                        }
                        catch (Exception e){
                            e.getLocalizedMessage();
                        }
                    }
                    else{
                        AppToast.show(this,"Please enter valid/minimum password details");
                    }
                }
                else{
                    AppToast.show(this,"Please enter valid email id");
                }
            }
            else{
                AppToast.show(activity,"No Internet connection");
            }
        }
        return false;
    }

    private void authenticateUser(final JSONObject jsonObject){
        Log.d(TAG,"JSON "+jsonObject.toString());
        final NetworkThread networkThread = new NetworkThread();
        networkThread.postCredentials(jsonObject,listener);
    }

    NetworkThread.DataResponseListener listener = new NetworkThread.DataResponseListener() {
        @Override
        public void onResponse(JSONObject response) {
            Log.d(TAG,"RES : "+response.toString());
            try{
                if (response.optString("accessToken")!=null && response.optString("accessToken").trim().length()> 1) {
                    final String accessToken = response.getString("accessToken");
                    DataStore.setAccessToken(accessToken);
                    getPlacesListData();
                }
            }catch (Exception e){
                e.getLocalizedMessage();
            }
        }

        @Override
        public void onError(ANError anError) {
            Log.d(TAG,"ERR : "+anError.getLocalizedMessage());
            AppToast.show(activity,""+anError.getLocalizedMessage());
        }
    };

    /**
     * List All Places Detail Response
     */
    private void getPlacesListData(){
        if (Connectivity.isAlive(activity)){
            AppToast.show(activity,"Loading data please wait ...");
            progressBar.setVisibility(View.VISIBLE);
            final NetworkThread networkThread = new NetworkThread();
            networkThread.getPlacesList(dataResponseListener);
        }
        else{
            AppToast.show(activity,"No Internet connection");
        }
    }

    /**
     * Response listener for Places Apir
     */
    NetworkThread.DataResponseListener dataResponseListener = new NetworkThread.DataResponseListener() {
        @Override
        public void onResponse(JSONObject response) {
            PlacesDataModel dataModel = new Gson().fromJson(response.toString(),PlacesDataModel.class);
            Log.d(TAG,"DATA "+response.toString());
            nextPage(dataModel);
        }

        @Override
        public void onError(ANError anError) {

        }
    };

    /**
     * GO TO NEXT PAGE
     * @param placesDataModel
     */
    protected void nextPage(PlacesDataModel placesDataModel){
        progressBar.setVisibility(View.GONE);
        Bundle bundle = new Bundle();
        bundle.putSerializable("places_data",placesDataModel);
       Intent startNextPage = new Intent(activity,MapsActivity.class);
        startNextPage.putExtra("bundle_data",bundle);
        startActivity(startNextPage);
        finish();
    }
}
