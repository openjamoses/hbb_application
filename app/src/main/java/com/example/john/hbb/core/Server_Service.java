package com.example.john.hbb.core;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by john on 7/9/17.
 */

public class Server_Service extends Service {
    private Context context = this;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkInternetConenction();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Dis-abled!", Toast.LENGTH_LONG).show();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            Log.e("Intenet Status:","You have Intenet Connection!" );
            //// Fetch data wenever there is internet connection....
            uploadUsersData();
            uploadTrainingData();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            //Toast.makeText(this, "No Intenet Connection!", Toast.LENGTH_LONG).show();
            Log.e("Intenet Status:","No Intenet Connection!" );
            ///
            return false;
        }
        return false;
    }


    public void uploadUsersData(){
        try{
            DBHelper dbHelper = new DBHelper(context);
            Cursor cursor = dbHelper.fetchUsers();
            if(cursor != null) {
                if(cursor.moveToFirst()){
                    int count = 0;
                    do{
                        count ++;
                        upload_users_Datas(count,
                                           cursor.getInt(cursor.getColumnIndex(Constants.config.USER_ID)),
                                           cursor.getString(cursor.getColumnIndex(Constants.config.FIRST_NAME)),
                                           cursor.getString(cursor.getColumnIndex(Constants.config.LAST_NAME)),
                                           cursor.getString(cursor.getColumnIndex(Constants.config.USERNAME)),
                                           cursor.getString(cursor.getColumnIndex(Constants.config.CONTACT)),
                                           cursor.getString(cursor.getColumnIndex(Constants.config.HEALTH_FACILITY)),
                                           cursor.getString(cursor.getColumnIndex(Constants.config.GENDER)),
                                           cursor.getString(cursor.getColumnIndex(Constants.config.PASSWORD))
                        );

                    }while (cursor.moveToNext());
                }
            }else {
                Log.e("Users_Table","No users data found");
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.e("Error:", e+"");
        }
    }
    public void uploadTrainingData(){
       try{
           DBHelper dbHelper = new DBHelper(context);

           Cursor cursor = dbHelper.fetchTrainingMode();
           if(cursor != null) {
               if(cursor.moveToFirst()){
                   int count = 0;
                   do{
                       count ++;
                       upload_TrainingMode_Datas( count,
                                                cursor.getInt(cursor.getColumnIndex(Constants.config.TRAINING_ID)),
                                                cursor.getInt(cursor.getColumnIndex(Constants.config.USER_ID)),
                                                cursor.getString(cursor.getColumnIndex(Constants.config.TRAINING_NAME)),
                                                cursor.getString(cursor.getColumnIndex(Constants.config.TRAINING_DATE)),
                                                cursor.getString(cursor.getColumnIndex(Constants.config.TRAINING_TIME)),
                                                String.valueOf(cursor.getInt(cursor.getColumnIndex(Constants.config.TRAINING_FREQUENCY))),
                                                cursor.getString(cursor.getColumnIndex(Constants.config.TRAINING_KEY_SKILL)),
                                                cursor.getString(cursor.getColumnIndex(Constants.config.TRAINING_KEY_SUBSKILL)),
                                                cursor.getInt(cursor.getColumnIndex(Constants.config.TRAINING_SYNC_STATUS))
                       );

                   }while (cursor.moveToNext());
               }
           }else {
               Log.e("Training_Table", "No training datas found!");
           }

       }catch (Exception e){
           e.printStackTrace();
           Log.e("Error:", e+"");
       }
    }

    private void upload_users_Datas(final int count, final int user_id, final String first_name, final String last_name, final String email,
                                           final String phone, final String health_facility, final String gender, final String password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.config.HOST_URL+"save_users.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                        Log.e("Users Data:",count+": \t"+ response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id", String.valueOf(user_id));
                params.put("first_name", first_name);
                params.put("last_name",last_name);
                params.put("email",email);
                params.put("phone", phone);
                params.put("health_facility",health_facility);
                params.put("gender",gender);
                params.put("password", password);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void upload_TrainingMode_Datas(final int count, final int training_id, final int user_id, final String training_name, final String training_date, final String training_time,
                              final String training_frequency, final String training_key, final String training_subKey, final int status){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.config.HOST_URL+"save_training.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                        Log.e("Traing Data:",count+": \t"+ response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("training_id", String.valueOf(training_id));
                params.put("user_id", String.valueOf(user_id));
                params.put("training_name", training_name);
                params.put("training_date",training_date);
                params.put("training_time",training_time);
                params.put("training_frequency", training_frequency);
                params.put("training_key_skills",training_key);
                params.put("training_key_subskills",training_subKey);
                params.put("sync_status", String.valueOf(status));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    // this is a inner class starts here....................

    class Users_Task   extends AsyncTask<Cursor,Void,String> {

        long s_time;
        long e_time;
        long d_time;
        Context ctx2;
        public Users_Task(Context ctx2) {
            this. ctx2 = ctx2;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            s_time = System.nanoTime();
        }
        @Override
        protected String doInBackground(Cursor... params) {
            final String url = Constants.config.HOST_URL+"get-all-streets.php";
            final StringBuilder sb = new StringBuilder();
            final Cursor cursor = params[0];
            final int[] count = {0};

            if(cursor != null) {
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OutputStream os = null;
                            InputStream is = null;
                            HttpURLConnection conn = null;



                            try {
                                //constants
                                URL url = new URL(Constants.config.HOST_URL+"save_users_details.php");
                                conn = (HttpURLConnection) url.openConnection();
                                conn.setReadTimeout(10000 /*milliseconds*/);
                                conn.setConnectTimeout(15000 /* milliseconds */);
                                conn.setRequestMethod("POST");
                                conn.setDoInput(true);
                                conn.setDoOutput(true);

                                if (cursor.moveToFirst()) {
                                    do {
                                        count[0]++;
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("user_id", cursor.getInt(cursor.getColumnIndex(Constants.config.USER_ID)));
                                        jsonObject.put("first_name", cursor.getString(cursor.getColumnIndex(Constants.config.FIRST_NAME)));
                                        jsonObject.put("last_name", cursor.getString(cursor.getColumnIndex(Constants.config.LAST_NAME)));
                                        jsonObject.put("username", cursor.getString(cursor.getColumnIndex(Constants.config.USERNAME)));
                                        jsonObject.put("phone", cursor.getString(cursor.getColumnIndex(Constants.config.CONTACT)));
                                        jsonObject.put("gender", cursor.getString(cursor.getColumnIndex(Constants.config.GENDER)));
                                        jsonObject.put("health_facility", cursor.getString(cursor.getColumnIndex(Constants.config.HEALTH_FACILITY)));
                                        jsonObject.put("password", cursor.getString(cursor.getColumnIndex(Constants.config.PASSWORD)));
                                        String message = jsonObject.toString();
                                        conn.setFixedLengthStreamingMode(message.getBytes().length);
                                        //make some HTTP header nicety
                                        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                                        conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                                        //open
                                        conn.connect();

                                        //setup send
                                        os = new BufferedOutputStream(conn.getOutputStream());
                                        os.write(message.getBytes());
                                        //clean up
                                        os.flush();

                                    } while (cursor.moveToFirst());
                                }


                                //do somehting with response
                                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String line = null;
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                    break;
                                }
                                //String contentAsString = readIt(is,len);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("error", e.toString());
                            } finally {
                                //clean up

                            }
                        }
                    }).start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                return "No new Data found!";
            }

            return count[0]+" records "+sb.toString();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            e_time = System.nanoTime();;
            d_time = (s_time - e_time)/1000000000;
            try {
                long startTime = System.nanoTime();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime)/1000000000;
               // Toast.makeText(context,result+" , Time Taken: "+duration+" sec.",Toast.LENGTH_SHORT).show();

            }catch (Exception  e){
                e.printStackTrace();
                ///Toast.makeText(context,"Error: "+e,Toast.LENGTH_SHORT).show();
            }
        }

    }
    //// inner class ends here.........






    // this is a inner class starts here....................

    class TrainingMode_Task   extends AsyncTask<Cursor,Void,String> {

        long s_time;
        long e_time;
        long d_time;
        Context ctx2;
        public TrainingMode_Task(Context ctx2) {
            this. ctx2 = ctx2;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            s_time = System.nanoTime();
        }
        @Override
        protected String doInBackground(Cursor... params) {
            final StringBuilder sb = new StringBuilder();
            final Cursor cursor = params[0];
            final int[] count = {0};

            if(cursor != null) {
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OutputStream os = null;
                            InputStream is = null;
                            HttpURLConnection conn = null;



                            try {
                                //constants
                                URL url = new URL(Constants.config.HOST_URL+"save_training_details.php");
                                conn = (HttpURLConnection) url.openConnection();
                                conn.setReadTimeout(10000 /*milliseconds*/);
                                conn.setConnectTimeout(15000 /* milliseconds */);
                                conn.setRequestMethod("POST");
                                conn.setDoInput(true);
                                conn.setDoOutput(true);

                                if (cursor.moveToFirst()) {
                                    do {
                                        count[0]++;
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("training_id", cursor.getInt(cursor.getColumnIndex(Constants.config.TRAINING_ID)));
                                        jsonObject.put("user_id", cursor.getString(cursor.getColumnIndex(Constants.config.USER_ID)));
                                        jsonObject.put("training_name", cursor.getString(cursor.getColumnIndex(Constants.config.TRAINING_NAME)));
                                        jsonObject.put("training_date", cursor.getString(cursor.getColumnIndex(Constants.config.TRAINING_DATE)));
                                        jsonObject.put("training_time", cursor.getString(cursor.getColumnIndex(Constants.config.TRAINING_TIME)));
                                        jsonObject.put("training_frequency", cursor.getString(cursor.getColumnIndex(Constants.config.TRAINING_FREQUENCY)));
                                        jsonObject.put("training_key_skills", cursor.getString(cursor.getColumnIndex(Constants.config.TRAINING_KEY_SKILL)));
                                        jsonObject.put("training_key_subskills", cursor.getString(cursor.getColumnIndex(Constants.config.TRAINING_KEY_SUBSKILL)));
                                        jsonObject.put("sync_status", cursor.getString(cursor.getColumnIndex(Constants.config.TRAINING_SYNC_STATUS)));

                                        String message = jsonObject.toString();
                                        conn.setFixedLengthStreamingMode(message.getBytes().length);
                                        //make some HTTP header nicety
                                        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                                        conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                                        //open
                                        conn.connect();

                                        //setup send
                                        os = new BufferedOutputStream(conn.getOutputStream());
                                        os.write(message.getBytes());
                                        //clean up
                                        os.flush();

                                    } while (cursor.moveToFirst());
                                }


                                //do somehting with response
                                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String line = null;
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                    break;
                                }
                                //String contentAsString = readIt(is,len);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("error", e.toString());
                            } finally {

                            }
                        }
                    }).start();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Error: ","connectivity error, "+e);
                }
            }else {
                Log.e("No data:", " No New Training data found!");
                return "No new Data found!";
            }
            return count[0]+" records "+sb.toString();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            e_time = System.nanoTime();;
            d_time = (s_time - e_time)/1000000000;
            try {
                long startTime = System.nanoTime();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime)/1000000000;
                //Toast.makeText(context,result+" , Time Taken: "+duration+" sec.",Toast.LENGTH_SHORT).show();
                Log.e("Traing Records:", result+" , Time Taken: "+duration+" sec.");

            }catch (Exception  e){
                e.printStackTrace();
                //oast.makeText(context,"Error: "+e,Toast.LENGTH_SHORT).show();
                Log.e("Error: ","Error in connectivity, "+e);
            }
        }

    }
    //// inner class ends here.........




}
