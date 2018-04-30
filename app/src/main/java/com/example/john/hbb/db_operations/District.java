package com.example.john.hbb.db_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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

import static com.example.john.hbb.core.Constants.config.DISTRICT_ID;
import static com.example.john.hbb.core.Constants.config.DISTRICT_NAME;
import static com.example.john.hbb.core.Constants.config.FETCH_STATUS;
import static com.example.john.hbb.core.Constants.config.HOST_URL;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_DISTRICT;

/**
 * Created by john on 10/18/17.
 */

public class District {
    Context context;
    private static final String TAG = "District";
    public District(Context context){
        this.context = context;
    }

    public String save(String name,String date_time, int fetch_status) {
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;
        try{
            int status = 0;
            database.beginTransactionNonExclusive();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DISTRICT_NAME,name);
            //contentValues.put(DISTRICT_DATE_TIME,date_time);
            contentValues.put(FETCH_STATUS,fetch_status);
            database.insert(Constants.config.TABLE_DISTRICT, null, contentValues);
            database.setTransactionSuccessful();
            message = "District cases saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            database.endTransaction();
        }
        return message;
    }

    ///// TODO: 10/13/17  select here!
    public Cursor selectDistrict(){
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransactionNonExclusive();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_HEALTH+"  ORDER BY "+Constants.config.DISTRICT_NAME+" ASC ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }

    public  int selectLast(){
        int last_id = 0;
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransactionNonExclusive();
            String query = "SELECT "+Constants.config.DISTRICT_ID+" FROM" +
                    " "+ Constants.config.TABLE_DISTRICT+"  ORDER BY "+Constants.config.DISTRICT_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    last_id = cursor.getInt(cursor.getColumnIndex(Constants.config.DISTRICT_ID));
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

    public  int select(String name){
        int last_id = 0;
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransactionNonExclusive();
            String query = "SELECT "+Constants.config.DISTRICT_ID+" FROM" +
                    " "+ Constants.config.TABLE_DISTRICT+" WHERE "+Constants.config.DISTRICT_NAME+" = '"+name+"'  ORDER BY "+Constants.config.DISTRICT_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    last_id = cursor.getInt(cursor.getColumnIndex(Constants.config.DISTRICT_ID));
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
    public void send(final String name, final String date_time){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_DISTRICT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            int status = 0;
                            if (response.equals("Success")){
                                status = 1;
                            }
                            String message = save(name,date_time,status);
                            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                            View view = toast.getView();
                            view.setBackgroundResource(R.drawable.round_conor);
                            TextView text = (TextView) view.findViewById(android.R.id.message);
                        /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                            toast.show();
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
                            int status = 0;
                            //String message = saves(hospital_name,s_name,s_number,email,imei,country,other,city,status,date);

                            Toast toast = Toast.makeText(context, "Connection error! ", Toast.LENGTH_LONG);
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
                //Adding parameters
                params.put(DISTRICT_NAME, name);
               // params.put(DISTRICT_DATE_TIME, date_time);

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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_DISTRICT ;

        int status = 1;
        SQLiteDatabase database = new DBHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(DISTRICT_ID))));

                params.put(DISTRICT_NAME, cursor.getString(cursor.getColumnIndex(DISTRICT_NAME)));
                //params.put(DISTRICT_DATE_TIME, cursor.getString(cursor.getColumnIndex(DISTRICT_DATE_TIME)));

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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_DISTRICT + " WHERE " + FETCH_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = new DBHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(DISTRICT_ID))));

                params.put(DISTRICT_NAME, cursor.getString(cursor.getColumnIndex(DISTRICT_NAME)));
               // params.put(DISTRICT_DATE_TIME, cursor.getString(cursor.getColumnIndex(DISTRICT_DATE_TIME)));

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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_DISTRICT+ " WHERE " + FETCH_STATUS + " = '" + status + "' ";
            database = new DBHelper(context).getReadableDatabase();
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
    public void updateSyncStatus(int id, int status){
        SQLiteDatabase database = null;
        try {
            database = new DBHelper(context).getWritableDatabase();
            String updateQuery = "UPDATE " + Constants.config.TABLE_DISTRICT + " SET " + FETCH_STATUS + " = '" + status + "' where " + DISTRICT_ID + "='" + id + "'  ";
            Log.d("query", updateQuery);
            database.execSQL(updateQuery);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
              //  database.close();
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
            SQLiteDatabase db = new DBHelper(context).getWritableDB();
            try{

                db.beginTransactionNonExclusive();
                //String get_json = get
                //JSONArray jsonArray = new JSONArray(results);
                JSONArray jsonArray = jsonArrays[0];
                db.execSQL("DELETE FROM " + Constants.config.TABLE_DISTRICT+" WHERE "+FETCH_STATUS+" = '"+status+"' ");

                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(Constants.config.DISTRICT_NAME,jsonObject.getString(Constants.config.DISTRICT_NAME));
                    //contentValues.put(Constants.config.DISTRICT_DATE_TIME,jsonObject.getString(Constants.config.DISTRICT_DATE_TIME));
                      contentValues.put(FETCH_STATUS,status);

                    db.insert(Constants.config.TABLE_USERS, null, contentValues);
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records , district Table Updated successfully!";

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
