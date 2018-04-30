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
import static com.example.john.hbb.core.Constants.config.PREPARATIONID;
import static com.example.john.hbb.core.Constants.config.PREPARATION_ID;
import static com.example.john.hbb.core.Constants.config.PREP_AREA_DELIVERY;
import static com.example.john.hbb.core.Constants.config.PREP_ASSEMBLED;
import static com.example.john.hbb.core.Constants.config.PREP_DATE;
import static com.example.john.hbb.core.Constants.config.PREP_IDENTIFY_HELPER;
import static com.example.john.hbb.core.Constants.config.PREP_IMEI;
import static com.example.john.hbb.core.Constants.config.PREP_STATUS;
import static com.example.john.hbb.core.Constants.config.PREP_TEST_VENTILATION;
import static com.example.john.hbb.core.Constants.config.PREP_TIME;
import static com.example.john.hbb.core.Constants.config.PREP_TYPE;
import static com.example.john.hbb.core.Constants.config.PREP_UTEROTONIC;
import static com.example.john.hbb.core.Constants.config.PREP_WASHES_HANDS;
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
import static com.example.john.hbb.core.Constants.config.URL_SAVE_LOG;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_ROUTINE;
/**
 * Created by john on 3/20/18.
 */

public class Routine_Care {
    private Context context;
    private static final String TAG = "User";
    public Routine_Care(Context context){
        this.context = context;
    }

    public String save(int routine_id,String routine_dries_thoroughy, String routine_recognise_crying, String routine_checks_breathing, int routine_type, String routine_clamps, String routine_position,
                       String routine_continue, String routine_time,String routine_date, String routine_imei,int routine_status, int log_id){
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;
        try{
            database.beginTransactionNonExclusive();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ROUTINE_ID,routine_id);
            contentValues.put(ROUTINE_DRIES_THOROUGHY,routine_dries_thoroughy);
            contentValues.put(ROUTINE_RECOGNISE_CRYING,routine_recognise_crying);
            contentValues.put(ROUTINE_CHECKS_BREATHING,routine_checks_breathing);
            contentValues.put(ROUTINE_TYPE,routine_type);
            contentValues.put(ROUTINE_POSITION,routine_position);
            contentValues.put(ROUTINE_CONTINUE,routine_continue);
            contentValues.put(ROUTINE_TIME,routine_time);
            contentValues.put(ROUTINE_DATE,routine_date);
            contentValues.put(ROUTINE_IMEI,routine_imei);
            contentValues.put(ROUTINE_STATUS,routine_status);
            contentValues.put(ROUTINE_CLAMPS,routine_clamps);
            contentValues.put(LOG_ID,log_id);

            database.insert(Constants.config.TABLE_ROUTINE, null, contentValues);
            database.setTransactionSuccessful();
            message = "Details saved!";
        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
           database.endTransaction();
        }
        return message;
    }

    public void send(final String routine_dries_thoroughy, final String routine_recognise_crying, final String routine_checks_breathing, final int routine_type, final String routine_clamps, final String routine_position,
                     final String routine_continue, final String routine_time, final String routine_date, final String routine_imei, final int log_id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_ROUTINE,
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
                            String message = save(id,routine_dries_thoroughy,routine_recognise_crying,routine_checks_breathing,routine_type,routine_clamps,routine_position,routine_continue,routine_time,routine_date,routine_imei,status, log_id);
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
                params.put(ROUTINE_DRIES_THOROUGHY,routine_dries_thoroughy);
                params.put(ROUTINE_RECOGNISE_CRYING,routine_recognise_crying);
                params.put(ROUTINE_CHECKS_BREATHING,routine_checks_breathing);
                params.put(ROUTINE_TYPE, String.valueOf(routine_type));
                params.put(ROUTINE_POSITION,routine_position);
                params.put(ROUTINE_CONTINUE,routine_continue);
                params.put(ROUTINE_TIME,routine_time);
                params.put(ROUTINE_DATE,routine_date);
                params.put(ROUTINE_IMEI,routine_imei);
                params.put(ROUTINE_CLAMPS,routine_clamps);
                params.put(LOG_ID, String.valueOf(log_id));
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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_ROUTINE ;

        int status = 1;
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(ROUTINEID))));

                params.put(ROUTINE_DRIES_THOROUGHY,cursor.getString(cursor.getColumnIndex(ROUTINE_DRIES_THOROUGHY)));
                params.put(ROUTINE_RECOGNISE_CRYING,cursor.getString(cursor.getColumnIndex(ROUTINE_RECOGNISE_CRYING)));
                params.put(ROUTINE_CHECKS_BREATHING,cursor.getString(cursor.getColumnIndex(ROUTINE_CHECKS_BREATHING)));
                params.put(ROUTINE_TYPE, String.valueOf(cursor.getInt(cursor.getColumnIndex(ROUTINE_TYPE))));
                params.put(ROUTINE_POSITION,cursor.getString(cursor.getColumnIndex(ROUTINE_POSITION)));
                params.put(ROUTINE_CONTINUE,cursor.getString(cursor.getColumnIndex(ROUTINE_CONTINUE)));
                params.put(ROUTINE_TIME,cursor.getString(cursor.getColumnIndex(ROUTINE_TIME)));
                params.put(ROUTINE_DATE,cursor.getString(cursor.getColumnIndex(ROUTINE_DATE)));
                params.put(ROUTINE_IMEI,cursor.getString(cursor.getColumnIndex(ROUTINE_IMEI)));
                params.put(ROUTINE_CLAMPS,cursor.getString(cursor.getColumnIndex(ROUTINE_CLAMPS)));
                params.put(LOG_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(LOG_ID))));
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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_ROUTINE + " WHERE " + ROUTINE_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(ROUTINEID))));

                params.put(ROUTINE_DRIES_THOROUGHY,cursor.getString(cursor.getColumnIndex(ROUTINE_DRIES_THOROUGHY)));
                params.put(ROUTINE_RECOGNISE_CRYING,cursor.getString(cursor.getColumnIndex(ROUTINE_RECOGNISE_CRYING)));
                params.put(ROUTINE_CHECKS_BREATHING,cursor.getString(cursor.getColumnIndex(ROUTINE_CHECKS_BREATHING)));
                params.put(ROUTINE_TYPE, String.valueOf(cursor.getInt(cursor.getColumnIndex(ROUTINE_TYPE))));
                params.put(ROUTINE_POSITION,cursor.getString(cursor.getColumnIndex(ROUTINE_POSITION)));
                params.put(ROUTINE_CONTINUE,cursor.getString(cursor.getColumnIndex(ROUTINE_CONTINUE)));
                params.put(ROUTINE_TIME,cursor.getString(cursor.getColumnIndex(ROUTINE_TIME)));
                params.put(ROUTINE_DATE,cursor.getString(cursor.getColumnIndex(ROUTINE_DATE)));
                params.put(ROUTINE_IMEI,cursor.getString(cursor.getColumnIndex(ROUTINE_IMEI)));
                params.put(ROUTINE_CLAMPS,cursor.getString(cursor.getColumnIndex(ROUTINE_CLAMPS)));
                params.put(LOG_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(LOG_ID))));
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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_ROUTINE+ " WHERE " + ROUTINE_STATUS + " = '" + status + "' ";
            database = DBHelper.getHelper(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            count = cursor.getCount();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
               // database.close();
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
            String updateQuery = "UPDATE " + Constants.config.TABLE_ROUTINE + " SET " + ROUTINE_STATUS + " = '" + status + "', "+ROUTINE_ID+" = '"+routine_id+"' where " + ROUTINEID + "='" + id + "'  ";
            Log.d("query", updateQuery);
            database.execSQL(updateQuery);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
               // database.close();
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
            db.beginTransactionNonExclusive();
            String query = "SELECT "+Constants.config.ROUTINE_ID+" FROM" +
                    " "+ Constants.config.TABLE_ROUTINE+"  ORDER BY "+Constants.config.ROUTINEID+" DESC LIMIT 1 ";
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
            SQLiteDatabase db = DBHelper.getHelper(context).getWritableDB();
            try{
                db.beginTransactionNonExclusive();
                //String get_json = get
                //JSONArray jsonArray = new JSONArray(results);
                JSONArray jsonArray = jsonArrays[0];
                //db.execSQL("DELETE FROM " + Constants.config.TABLE_HEALTH+" WHERE "+HEALTH_STATUS+" = '"+status+"' ");
                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(ROUTINE_ID,jsonObject.getLong(Constants.config.ROUTINE_ID));
                    contentValues.put(ROUTINE_DRIES_THOROUGHY,jsonObject.getString(Constants.config.ROUTINE_DRIES_THOROUGHY));
                    contentValues.put(ROUTINE_RECOGNISE_CRYING,jsonObject.getString(Constants.config.ROUTINE_RECOGNISE_CRYING));
                    contentValues.put(ROUTINE_CHECKS_BREATHING,jsonObject.getString(Constants.config.ROUTINE_CHECKS_BREATHING));
                    contentValues.put(ROUTINE_TYPE,jsonObject.getLong(Constants.config.ROUTINE_TYPE));
                    contentValues.put(ROUTINE_POSITION,jsonObject.getString(Constants.config.ROUTINE_POSITION));
                    contentValues.put(ROUTINE_CONTINUE,jsonObject.getString(Constants.config.ROUTINE_CONTINUE));
                    contentValues.put(ROUTINE_TIME,jsonObject.getString(Constants.config.ROUTINE_TIME));
                    contentValues.put(ROUTINE_DATE,jsonObject.getString(Constants.config.ROUTINE_DATE));
                    contentValues.put(ROUTINE_IMEI,jsonObject.getString(Constants.config.ROUTINE_IMEI));
                    contentValues.put(LOG_ID,jsonObject.getLong(Constants.config.LOG_ID));
                    contentValues.put(ROUTINE_STATUS,status);
                    contentValues.put(ROUTINE_CLAMPS,jsonObject.getString(Constants.config.ROUTINE_CLAMPS));


                    db = DBHelper.getHelper(context).getReadableDB();
                    String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_ROUTINE+ " WHERE "+ROUTINE_IMEI+" = '"+jsonObject.getString(Constants.config.ROUTINE_IMEI)+"'" +
                            " AND "+ROUTINE_DATE+" = '"+jsonObject.getString(Constants.config.ROUTINE_DATE)+"' AND  " + ROUTINE_TIME + " = '" + jsonObject.getString(Constants.config.ROUTINE_TIME) + "' ";
                    db = new DBHelper(context).getReadableDatabase();
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()){
                        do {
                            int stat = cursor.getInt(cursor.getColumnIndex(Constants.config.ROUTINE_STATUS));
                            int id = cursor.getInt(cursor.getColumnIndex(Constants.config.ROUTINEID));
                            if (stat == 0){
                                db = DBHelper.getHelper(context).getWritableDB();
                                db.update(Constants.config.TABLE_ROUTINE,contentValues,ROUTINEID+"="+id, null);
                            }
                        }while (cursor.moveToNext());
                    }else {
                        db = DBHelper.getHelper(context).getWritableDB();
                        db.insert(Constants.config.TABLE_ROUTINE, null, contentValues);
                    }
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records , routine Table Updated successfully!";

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
