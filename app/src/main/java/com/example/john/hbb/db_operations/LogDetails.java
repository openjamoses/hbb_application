package com.example.john.hbb.db_operations;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.john.hbb.core.Constants;
import com.example.john.hbb.core.DBHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.example.john.hbb.core.Constants.config.DISTRICT_NAME;
import static com.example.john.hbb.core.Constants.config.FACILITY_OWNER;
import static com.example.john.hbb.core.Constants.config.FACILITY_TYPE;
import static com.example.john.hbb.core.Constants.config.GROUP_ID;
import static com.example.john.hbb.core.Constants.config.HEALTHID;
import static com.example.john.hbb.core.Constants.config.HEALTH_CADRE;
import static com.example.john.hbb.core.Constants.config.HEALTH_ID;
import static com.example.john.hbb.core.Constants.config.HEALTH_NAME;
import static com.example.john.hbb.core.Constants.config.HEALTH_STATUS;
import static com.example.john.hbb.core.Constants.config.HOST_URL;
import static com.example.john.hbb.core.Constants.config.LOGID;
import static com.example.john.hbb.core.Constants.config.LOG_DATE;
import static com.example.john.hbb.core.Constants.config.LOG_ID;
import static com.example.john.hbb.core.Constants.config.LOG_IMEI;
import static com.example.john.hbb.core.Constants.config.LOG_NAMES;
import static com.example.john.hbb.core.Constants.config.LOG_STATUS;
import static com.example.john.hbb.core.Constants.config.LOG_TIME;
import static com.example.john.hbb.core.Constants.config.LOG_TYPE;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_HEALTH;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_LOG;
import static com.example.john.hbb.core.Constants.config.USER_ID;

/**
 * Created by john on 3/19/18.
 */

public class LogDetails {
    private Context context;
    private static final String TAG = "User";
    public LogDetails(Context context){
        this.context = context;
    }

    public String save(int user_id,int id,String date, String time, String imei, int type, String names, String group_id, int status){
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;
        try{
            // database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_ID,user_id);
            contentValues.put(LOG_ID,id);
            contentValues.put(LOG_DATE,date);
            contentValues.put(LOG_TIME,time);
            contentValues.put(LOG_IMEI,imei);
            contentValues.put(LOG_TYPE,type);
            contentValues.put(LOG_NAMES,names);
            contentValues.put(GROUP_ID,group_id);
            contentValues.put(Constants.config.LOG_STATUS,status);

            database.insert(Constants.config.TABLE_LOG, null, contentValues);
            //database.setTransactionSuccessful();
            message = "Log Details saved!";
        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            database.close();
        }

        return message;
    }

    public void send(final int user_id, final String date, final String time, final String imei, final int type, final String names, final String group_id){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_LOG,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "Results: " + response);

                            String[] splits = response.split("/");
                            int status = 0, id = 0;

                            if (splits[0].equals("Success")) {
                                status = 1;
                                id = Integer.parseInt(splits[1]);

                            }
                            String message = save(user_id,id,date,time,imei,type,names,group_id,status);
                            //Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

                            if (message.equals("Log Details saved!")){
                                //alertDialog.dismiss();
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
                        try {
                            Log.e(TAG, ""+volleyError.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                int status = 1;
                Map<String, String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put(USER_ID, String.valueOf(user_id));
                params.put(LOG_DATE, date);
                params.put(LOG_TIME, time);
                params.put(LOG_IMEI, imei);
                params.put(LOG_TYPE, String.valueOf(type));
                params.put(LOG_NAMES, names);
                params.put(GROUP_ID, group_id);

                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    //// TODO: 10/15/17  Syncing
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_LOG ;

        int status = 1;
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(LOGID))));

                params.put(USER_ID,cursor.getString(cursor.getColumnIndex(USER_ID)));
                params.put(LOG_ID,cursor.getString(cursor.getColumnIndex(LOG_ID)));
                params.put(LOG_DATE,cursor.getString(cursor.getColumnIndex(LOG_DATE)));
                params.put(LOG_TIME,cursor.getString(cursor.getColumnIndex(LOG_TIME)));
                params.put(LOG_IMEI,cursor.getString(cursor.getColumnIndex(LOG_IMEI)));
                params.put(LOG_TYPE,cursor.getString(cursor.getColumnIndex(LOG_TYPE)));
                params.put(LOG_NAMES,cursor.getString(cursor.getColumnIndex(LOG_NAMES)));
                params.put(GROUP_ID,cursor.getString(cursor.getColumnIndex(GROUP_ID)));
                wordList.add(params);
            } while (cursor.moveToNext());
        }
        //database.close();
        return wordList;
    }
    public String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        int status = 0;
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_LOG + " WHERE " + LOG_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(LOGID))));

                params.put(HEALTH_NAME,cursor.getString(cursor.getColumnIndex(HEALTH_NAME)));
                params.put(DISTRICT_NAME,cursor.getString(cursor.getColumnIndex(DISTRICT_NAME)));
                params.put(HEALTH_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(HEALTH_ID))));
                params.put(FACILITY_OWNER,cursor.getString(cursor.getColumnIndex(FACILITY_OWNER)));
                params.put(FACILITY_TYPE,cursor.getString(cursor.getColumnIndex(FACILITY_TYPE)));
                params.put(HEALTH_CADRE,cursor.getString(cursor.getColumnIndex(HEALTH_CADRE)));

                wordList.add(params);
            } while (cursor.moveToNext());
        }
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }
    /**
     * Get Sync status of SQLite
     * @return
     */
    public String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        }else{
            msg = "DB Sync neededn";
        }
        return msg;
    }
    /**
     * Get SQLite records that are yet to be Synced
     * @return
     */
    public int dbSyncCount(){
        int count = 0;
        SQLiteDatabase database = null;
        try {
            int status = 0;
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_LOG+ " WHERE " + LOG_STATUS + " = '" + status + "' ";
            database = DBHelper.getHelper(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            count = cursor.getCount();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                database.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return count;
    }
    /***
     *
     * @param id
     * @param status
     */
    public void updateSyncStatus(int id, int log_id, int status){
        SQLiteDatabase database = null;
        try {
            database = DBHelper.getHelper(context).getWritableDatabase();
            String updateQuery = "UPDATE " + Constants.config.TABLE_LOG + " SET " + LOG_STATUS + " = '" + status + "', "+LOG_ID+" = '"+log_id+"' where " + LOGID + "='" + id + "'  ";
            Log.d("query", updateQuery);
            database.execSQL(updateQuery);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                database.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    ///// TODO: 10/23/17

    public void insert(JSONArray jsonArray){
        new InsertBackground(context).execute(jsonArray);
    }
    public class InsertBackground extends AsyncTask<JSONArray,Void,String> {
        Context context;
        InsertBackground(Context context){
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // startTime = System.nanoTime();
            // progressDialog.setTitle("Now Saving ! ...");
        }
        @Override
        protected String doInBackground(JSONArray... jsonArrays) {
            String message = null;
            int status = 1;
            SQLiteDatabase db = DBHelper.getHelper(context).getWritableDB();
            try{
                db.beginTransaction();
                //String get_json = get
                //JSONArray jsonArray = new JSONArray(results);
                JSONArray jsonArray = jsonArrays[0];
                //db.execSQL("DELETE FROM " + Constants.config.TABLE_HEALTH+" WHERE "+HEALTH_STATUS+" = '"+status+"' ");

                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(USER_ID,jsonObject.getLong(Constants.config.USER_ID));
                    contentValues.put(LOG_ID,jsonObject.getLong(Constants.config.LOG_ID));
                    contentValues.put(LOG_DATE,jsonObject.getString(Constants.config.LOG_DATE));
                    contentValues.put(LOG_TIME,jsonObject.getString(Constants.config.LOG_TIME));
                    contentValues.put(LOG_IMEI,jsonObject.getString(Constants.config.LOG_IMEI));
                    contentValues.put(LOG_TYPE,jsonObject.getLong(Constants.config.LOG_TYPE));
                    contentValues.put(LOG_NAMES,jsonObject.getString(Constants.config.LOG_NAMES));
                    contentValues.put(GROUP_ID,jsonObject.getString(Constants.config.GROUP_ID));
                    contentValues.put(Constants.config.LOG_STATUS,status);
                    db = DBHelper.getHelper(context).getReadableDB();
                    String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_LOG+ " WHERE "+LOG_IMEI+" = '"+jsonObject.getString(Constants.config.LOG_IMEI)+"'" +
                            " AND "+LOG_DATE+" = '"+jsonObject.getString(Constants.config.LOG_DATE)+"' AND  " + LOG_TIME + " = '" + jsonObject.getString(Constants.config.LOG_TIME) + "' ";
                    db = new DBHelper(context).getReadableDatabase();
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()){
                        do {
                            int stat = cursor.getInt(cursor.getColumnIndex(Constants.config.LOG_STATUS));
                            int id = cursor.getInt(cursor.getColumnIndex(Constants.config.LOGID));
                            if (stat == 0){
                                db = DBHelper.getHelper(context).getWritableDB();
                                db.update(Constants.config.TABLE_LOG,contentValues,LOGID+"="+id, null);
                            }
                        }while (cursor.moveToNext());
                    }else {
                        db = DBHelper.getHelper(context).getWritableDB();
                        db.insert(Constants.config.TABLE_LOG, null, contentValues);

                    }
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records , Log Table Updated successfully!";

            }catch (Exception e){
                e.printStackTrace();
                message = "Error: "+e;
                Log.e("Error: ",e.toString());
            }finally {
                db.endTransaction();
            }
            return  message;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Fetch results",s);

        }
    }
}
