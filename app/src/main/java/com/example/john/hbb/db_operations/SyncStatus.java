package com.example.john.hbb.db_operations;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.john.hbb.core.BaseApplication;

import java.util.Hashtable;
import java.util.Map;

import static com.example.john.hbb.core.Constants.config.HOST_URL;
import static com.example.john.hbb.core.Constants.config.SYNC_DATE;
import static com.example.john.hbb.core.Constants.config.SYNC_IMEI;
import static com.example.john.hbb.core.Constants.config.SYNC_STATUS;
import static com.example.john.hbb.core.Constants.config.SYNC_TIME;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_STATUS;

/**
 * Created by john on 4/17/18.
 */

public class SyncStatus {
    private static final String TAG = "SyncStatus";
    public static void send(Context context,final String imei, final String sync_date, final String last_sync_time, final int sync_status){
        BaseApplication.deleteCache(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_STATUS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "Results: " + response);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        Log.e(TAG,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        try {
                            Log.e(TAG, ""+volleyError.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put(SYNC_IMEI,imei);
                params.put(SYNC_DATE,sync_date);
                params.put(SYNC_TIME,last_sync_time);
                params.put(SYNC_STATUS, String.valueOf(sync_status));
                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

}
