package com.example.john.hbb.db_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

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

import static com.example.john.hbb.core.Constants.config.HOST_URL;
import static com.example.john.hbb.core.Constants.config.LOG_ID;
import static com.example.john.hbb.core.Constants.config.LOG_TYPE;
import static com.example.john.hbb.core.Constants.config.ROUTINEID;
import static com.example.john.hbb.core.Constants.config.ROUTINE_CHECKS_BREATHING;
import static com.example.john.hbb.core.Constants.config.ROUTINE_CLAMPS;
import static com.example.john.hbb.core.Constants.config.ROUTINE_CONTINUE;
import static com.example.john.hbb.core.Constants.config.ROUTINE_DATE;
import static com.example.john.hbb.core.Constants.config.ROUTINE_DRIES_THOROUGHY;
import static com.example.john.hbb.core.Constants.config.ROUTINE_ID;
import static com.example.john.hbb.core.Constants.config.ROUTINE_IMEI;
import static com.example.john.hbb.core.Constants.config.ROUTINE_POSITION;
import static com.example.john.hbb.core.Constants.config.ROUTINE_RECOGNISE_CRYING;
import static com.example.john.hbb.core.Constants.config.ROUTINE_STATUS;
import static com.example.john.hbb.core.Constants.config.ROUTINE_TIME;
import static com.example.john.hbb.core.Constants.config.ROUTINE_TYPE;
import static com.example.john.hbb.core.Constants.config.TRAININGID;
import static com.example.john.hbb.core.Constants.config.TRAINING_DATE;
import static com.example.john.hbb.core.Constants.config.TRAINING_FREQUENCY;
import static com.example.john.hbb.core.Constants.config.TRAINING_ID;
import static com.example.john.hbb.core.Constants.config.TRAINING_IMEI;
import static com.example.john.hbb.core.Constants.config.TRAINING_KEY_SKILL;
import static com.example.john.hbb.core.Constants.config.TRAINING_KEY_SUBSKILL;
import static com.example.john.hbb.core.Constants.config.TRAINING_NAME;
import static com.example.john.hbb.core.Constants.config.TRAINING_SYNC_STATUS;
import static com.example.john.hbb.core.Constants.config.TRAINING_TIME;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_ROUTINE;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_TRAINING;
import static com.example.john.hbb.core.Constants.config.VIDEO_COMPLETED;

/**
 * Created by john on 4/17/18.
 */

public class TrainingDetails {
    private Context context;
    private static final String TAG = "User";
    public TrainingDetails(Context context){
        this.context = context;
    }

    public String save(int training_id,String training_date, String training_name, String training_time, int training_frequency, int training_key_skills, int training_key_subskills,
                       int log_id, int log_type,int video_completed, String training_imei,int sync_status){
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;
        try{
            // database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TRAINING_ID,training_id);
            contentValues.put(TRAINING_DATE,training_date);
            contentValues.put(TRAINING_NAME,training_name);
            contentValues.put(TRAINING_TIME,training_time);
            contentValues.put(TRAINING_FREQUENCY,training_frequency);
            contentValues.put(TRAINING_KEY_SKILL,training_key_skills);
            contentValues.put(TRAINING_KEY_SUBSKILL,training_key_subskills);
            contentValues.put(LOG_ID,log_id);
            contentValues.put(LOG_TYPE,log_type);
            contentValues.put(VIDEO_COMPLETED,video_completed);
            contentValues.put(TRAINING_IMEI,training_imei);
            contentValues.put(TRAINING_SYNC_STATUS,sync_status);

            database.insert(Constants.config.TABLE_TRAINING, null, contentValues);
            //database.setTransactionSuccessful();
            message = "Details saved!";
        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            database.close();
        }
        return message;
    }

    public void send(final String training_date, final String training_name, final String training_time, final int training_frequency, final int training_key_skills, final int training_key_subskills,
                     final int log_id, final int log_type, final int video_completed, final String training_imei){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_TRAINING,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "Results: " + response);
                            String[] splits = response.split("/");
                            int status = 0, id = selectLast()+1;
                            if (splits[0].equals("Success")) {
                                status = 1;
                                id = Integer.parseInt(splits[1]);
                            }
                            String message = save(id,training_date,training_name,training_time,training_frequency,training_key_skills,training_key_subskills,log_id,log_type,video_completed,training_imei,status);
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
                params.put(TRAINING_DATE,training_date);
                params.put(TRAINING_NAME,training_name);
                params.put(TRAINING_TIME,training_time);
                params.put(TRAINING_FREQUENCY, String.valueOf(training_frequency));
                params.put(TRAINING_KEY_SKILL, String.valueOf(training_key_skills));
                params.put(TRAINING_KEY_SUBSKILL, String.valueOf(training_key_subskills));
                params.put(LOG_ID, String.valueOf(log_id));
                params.put(LOG_TYPE, String.valueOf(log_type));
                params.put(VIDEO_COMPLETED, String.valueOf(video_completed));
                params.put(TRAINING_IMEI,training_imei);

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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_TRAINING ;

        int status = 1;
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAININGID))));

                params.put(TRAINING_DATE,cursor.getString(cursor.getColumnIndex(TRAINING_DATE)));
                params.put(TRAINING_NAME,cursor.getString(cursor.getColumnIndex(TRAINING_NAME)));
                params.put(TRAINING_TIME,cursor.getString(cursor.getColumnIndex(TRAINING_TIME)));
                params.put(TRAINING_FREQUENCY, String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAINING_FREQUENCY))));
                params.put(TRAINING_KEY_SKILL, String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAINING_KEY_SKILL))));
                params.put(TRAINING_KEY_SUBSKILL, String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAINING_KEY_SUBSKILL))));
                params.put(LOG_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(LOG_ID))));
                params.put(LOG_TYPE, String.valueOf(cursor.getInt(cursor.getColumnIndex(LOG_TYPE))));
                params.put(VIDEO_COMPLETED, String.valueOf(cursor.getInt(cursor.getColumnIndex(VIDEO_COMPLETED))));
                params.put(TRAINING_IMEI,cursor.getString(cursor.getColumnIndex(TRAINING_IMEI)));

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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_TRAINING + " WHERE " + TRAINING_SYNC_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAININGID))));

                params.put(TRAINING_DATE,cursor.getString(cursor.getColumnIndex(TRAINING_DATE)));
                params.put(TRAINING_NAME,cursor.getString(cursor.getColumnIndex(TRAINING_NAME)));
                params.put(TRAINING_TIME,cursor.getString(cursor.getColumnIndex(TRAINING_TIME)));
                params.put(TRAINING_FREQUENCY, String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAINING_FREQUENCY))));
                params.put(TRAINING_KEY_SKILL, String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAINING_KEY_SKILL))));
                params.put(TRAINING_KEY_SUBSKILL, String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAINING_KEY_SUBSKILL))));
                params.put(LOG_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(LOG_ID))));
                params.put(LOG_TYPE, String.valueOf(cursor.getInt(cursor.getColumnIndex(LOG_TYPE))));
                params.put(VIDEO_COMPLETED, String.valueOf(cursor.getInt(cursor.getColumnIndex(VIDEO_COMPLETED))));
                params.put(TRAINING_IMEI,cursor.getString(cursor.getColumnIndex(TRAINING_IMEI)));
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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_TRAINING+ " WHERE " + TRAINING_SYNC_STATUS + " = '" + status + "' ";
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
    public void updateSyncStatus(int id,int routine_id, int status){
        SQLiteDatabase database = null;
        try {
            database = DBHelper.getHelper(context).getWritableDatabase();
            String updateQuery = "UPDATE " + Constants.config.TABLE_TRAINING + " SET " + TRAINING_SYNC_STATUS + " = '" + status + "', "+TRAINING_ID+" = '"+routine_id+"' where " + ROUTINEID + "='" + id + "'  ";
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

    public  int selectLast(){
        int last_id = 0;
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT "+Constants.config.TRAINING_ID+" FROM" +
                    " "+ Constants.config.TABLE_TRAINING+"  ORDER BY "+Constants.config.TRAINING_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    last_id = cursor.getInt(cursor.getColumnIndex(Constants.config.ROUTINE_ID));
                }while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  last_id;
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
        }
        @Override
        protected String doInBackground(JSONArray... jsonArrays) {
            String message = null;
            int status = 1;
            SQLiteDatabase db = DBHelper.getHelper(context).getWritableDatabase();
            try{
                db.beginTransaction();
                //String get_json = get
                //JSONArray jsonArray = new JSONArray(results);
                JSONArray jsonArray = jsonArrays[0];
                db.execSQL("DELETE FROM " + Constants.config.TABLE_TRAINING+" WHERE "+TRAINING_SYNC_STATUS+" = '"+status+"' ");
                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(TRAINING_ID, jsonObject.getLong(Constants.config.TRAINING_ID));
                    contentValues.put(TRAINING_DATE, jsonObject.getString(Constants.config.TRAINING_DATE));
                    contentValues.put(TRAINING_NAME, jsonObject.getString(Constants.config.TRAINING_NAME));
                    contentValues.put(TRAINING_TIME, jsonObject.getString(Constants.config.TRAINING_TIME));
                    contentValues.put(TRAINING_FREQUENCY, jsonObject.getLong(Constants.config.TRAINING_FREQUENCY));
                    contentValues.put(TRAINING_KEY_SKILL, jsonObject.getLong(Constants.config.TRAINING_KEY_SKILL));
                    contentValues.put(TRAINING_KEY_SUBSKILL, jsonObject.getLong(Constants.config.TRAINING_KEY_SUBSKILL));
                    contentValues.put(LOG_ID, jsonObject.getLong(Constants.config.LOG_ID));
                    contentValues.put(LOG_TYPE, jsonObject.getLong(Constants.config.LOG_TYPE));
                    contentValues.put(VIDEO_COMPLETED, jsonObject.getLong(Constants.config.VIDEO_COMPLETED));
                    contentValues.put(TRAINING_IMEI, jsonObject.getString(Constants.config.TRAINING_IMEI));
                    contentValues.put(TRAINING_SYNC_STATUS, status);
                    db.insert(Constants.config.TABLE_TRAINING, null, contentValues);
                }
                db.setTransactionSuccessful();
                message = total+" records , "+Constants.config.TABLE_TRAINING+" Updated successfully!";

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
