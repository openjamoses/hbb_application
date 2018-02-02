package com.example.john.hbb.db_operations;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.john.hbb.R;
import com.example.john.hbb.configuration.Constants;
import com.example.john.hbb.configuration.DBHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import static com.example.john.hbb.configuration.Constants.config.EMAIL;
import static com.example.john.hbb.configuration.Constants.config.HOST_URL;
import static com.example.john.hbb.configuration.Constants.config.OPERATION_DISTRICT;
import static com.example.john.hbb.configuration.Constants.config.OPERATION_TRAINING;
import static com.example.john.hbb.configuration.Constants.config.OPERATION_USER;
import static com.example.john.hbb.configuration.Constants.config.USER_STATUS;

/**
 * Created by john on 10/14/17.
 */
public class DBController {
    private static final String TAG = "DBController";
    Handler mainHandler = new Handler(Looper.getMainLooper());

    public void syncCalls(final String url, final String operations, String show, final Context context){
        Log.e(TAG,"******************************** "+url);
        Log.e(TAG,"Syncing started for: "+operations);
        new Task(context,operations,url).execute();
    }
    private class Task extends AsyncTask<String, Void,String>{

        String operations;
        String url;
        Context context;
        public Task(Context context,String operations, String url){
            this.context = context;
            this.operations = operations;
            this.url = url;
        }
        @Override
        protected String doInBackground(String... strings) {

            try {

                //final ProgressDialog prgDialog = new ProgressDialog(context);
                //prgDialog.setMessage("Synching SQLite Data with Remote Server. \n Please wait...");
                //prgDialog.setCancelable(false);
                //Create AsycHttpClient object

                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        //Code that uses AsyncHttpClient in your case ConsultaCaract()

                        AsyncHttpClient client = new AsyncHttpClient();
                        RequestParams params = new RequestParams();
                        int db_count = 0;
                        ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();
                        String json_data = "";
                        if (operations.equals(OPERATION_USER)) {
                            userList = new User(context).getAllUsers();
                            db_count = new User(context).dbSyncCount();
                            json_data = new User(context).composeJSONfromSQLite();
                        } else if (operations.equals(OPERATION_DISTRICT)) {
                            userList = new District(context).getAllUsers();
                            db_count = new District(context).dbSyncCount();
                            json_data = new District(context).composeJSONfromSQLite();
                        }else if (operations.equals(OPERATION_TRAINING)) {
                            userList = new Training_Mode(context).getAllUsers();
                            db_count = new Training_Mode(context).dbSyncCount();
                            json_data = new Training_Mode(context).composeJSONfromSQLite();
                        }
                        if (userList.size() != 0) {
                            if (db_count != 0) {

                                params.put("dataJSON", json_data);
                                client.post(HOST_URL + url, params, new AsyncHttpResponseHandler() {

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                        try {
                                            String response = new String(responseBody, "UTF-8");
                                            //prgDialog.hide();
                                            try {
                                                Log.e(TAG, response);
                                                JSONArray arr = new JSONArray(response);
                                                System.out.println(arr.length());
                                                for (int i = 0; i < arr.length(); i++) {
                                                    JSONObject obj = (JSONObject) arr.get(i);
                                                    System.out.println(obj.get("id"));

                                                    if (operations.equals(OPERATION_USER)) {
                                                        new User(context).updateSyncStatus(Integer.parseInt(obj.get("id").toString()), Integer.parseInt(obj.get("status").toString()));
                                                    } else if (operations.equals(OPERATION_DISTRICT)) {
                                                        new District(context).updateSyncStatus(Integer.parseInt(obj.get("id").toString()), Integer.parseInt(obj.get("status").toString()));
                                                    }else if (operations.equals(OPERATION_TRAINING)) {
                                                        new Training_Mode(context).updateSyncStatus(Integer.parseInt(obj.get("id").toString()), Integer.parseInt(obj.get("status").toString()));
                                                    }
                                                }
                                                //Toast.makeText(getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                //Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                                                e.printStackTrace();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        if (statusCode == 404) {
                                            Log.e("Error ", "Error code " + statusCode + " \t " + url);
                                            //Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                                        } else if (statusCode == 500) {
                                            Log.e("Error ", "Error code " + statusCode + " \t" + url);
                                            //Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                                        } else {
                                            Log.e("Error ", "Error code " + statusCode + " \t " + url);
                                        }
                                    }
                                });
                            } else {
                                Log.e("No Sync data", "Empty data to be sync " + url);
                            }
                        } else {
                            Log.e("Empty", "Empty data to be sync " + url);
                        }
                    }
                };
                mainHandler.post(myRunnable);



            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void fetch(final Context context, final String email, String url, final String operation){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            if(operation.equals(OPERATION_USER)){
                                new User(context).insert(jsonArray);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Log.e(TAG,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Log.e(TAG, volleyError.getMessage());
                        try {
                            Toast toast = Toast.makeText(context, "Connections ERROR!", Toast.LENGTH_LONG);
                            View view = toast.getView();
                            view.setBackgroundResource(R.drawable.round_conor);
                            TextView text = (TextView) view.findViewById(android.R.id.message);
                            toast.show();
                            //Log.e(TAG, volleyError.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put(EMAIL, email);
                //Adding parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }



}
