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
import com.example.john.hbb.core.DateTime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.example.john.hbb.core.Constants.config.CONTACT;
import static com.example.john.hbb.core.Constants.config.DISTRICT_NAME;
import static com.example.john.hbb.core.Constants.config.FACILITY_OWNER;
import static com.example.john.hbb.core.Constants.config.FACILITY_TYPE;
import static com.example.john.hbb.core.Constants.config.FIRST_NAME;
import static com.example.john.hbb.core.Constants.config.GENDER;
import static com.example.john.hbb.core.Constants.config.HEALTHID;
import static com.example.john.hbb.core.Constants.config.HEALTH_CADRE;
import static com.example.john.hbb.core.Constants.config.HEALTH_ID;
import static com.example.john.hbb.core.Constants.config.HEALTH_NAME;
import static com.example.john.hbb.core.Constants.config.HEALTH_STATUS;
import static com.example.john.hbb.core.Constants.config.HOST_URL;
import static com.example.john.hbb.core.Constants.config.LAST_NAME;
import static com.example.john.hbb.core.Constants.config.PASSWORD;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_HEALTH;
import static com.example.john.hbb.core.Constants.config.USER_ID;
import static com.example.john.hbb.core.Constants.config.USER_STATUS;
import static com.example.john.hbb.core.Constants.config.VERIFIED_STATUS;

/**
 * Created by john on 3/18/18.
 */

public class Facility {

    private Context context;
    private static final String TAG = "User";
    public Facility(Context context){
        this.context = context;
    }

    public String save(String name,int facility_id, String cadre, String type, String owner, String district, int status){
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;
        try{
            database.beginTransactionNonExclusive();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constants.config.HEALTH_NAME,name);
            contentValues.put(Constants.config.DISTRICT_NAME,district);
            contentValues.put(HEALTH_ID,facility_id);
            contentValues.put(Constants.config.FACILITY_OWNER,owner);
            contentValues.put(Constants.config.FACILITY_TYPE,type);
            contentValues.put(Constants.config.HEALTH_CADRE,cadre);
            contentValues.put(Constants.config.HEALTH_STATUS,status);

            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_HEALTH+" WHERE "+Constants.config.HEALTH_NAME+" = '"+name+"' ";
            Cursor cursor = database.rawQuery(query,null);
            if (cursor.moveToFirst()){
                //database.insert(Constants.config.TABLE_HEALTH, null, contentValues);
                //database.setTransactionSuccessful();
                message = "Health FAcility already registered!";
            }else {
                database.insert(Constants.config.TABLE_HEALTH, null, contentValues);
                //database.setTransactionSuccessful();
                message = "Health Details saved!";
            }

            database.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            database.endTransaction();
        }

        return message;
    }

    public void send(final String name, final String cadre, final String type, final String owner, final String district, final ProgressDialog dialog, final AlertDialog alertDialog){

        try{
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_HEALTH,
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
                            String message = save(name,id,cadre,type,owner,district,status);
                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

                            if (message.equals("Health Details saved!")){
                                alertDialog.dismiss();
                            }

                            if (dialog.isShowing()){
                                dialog.dismiss();
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
                params.put(HEALTH_NAME, name);
                params.put(FACILITY_TYPE, type);
                params.put(FACILITY_OWNER, owner);
                params.put(HEALTH_CADRE, cadre);
                params.put(DISTRICT_NAME, district);
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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_HEALTH ;

        int status = 1;
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(HEALTHID))));
                params.put(HEALTH_NAME,cursor.getString(cursor.getColumnIndex(HEALTH_NAME)));
                params.put(DISTRICT_NAME,cursor.getString(cursor.getColumnIndex(DISTRICT_NAME)));
                params.put(HEALTH_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(HEALTH_ID))));
                params.put(FACILITY_OWNER,cursor.getString(cursor.getColumnIndex(FACILITY_OWNER)));
                params.put(FACILITY_TYPE,cursor.getString(cursor.getColumnIndex(FACILITY_TYPE)));
                params.put(HEALTH_CADRE,cursor.getString(cursor.getColumnIndex(HEALTH_CADRE)));
               // params.put(HEALTH_STATUS,status);
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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_HEALTH + " WHERE " + HEALTH_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(HEALTHID))));

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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_HEALTH+ " WHERE " + HEALTH_STATUS + " = '" + status + "' ";
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
    public void updateSyncStatus(int id,int health_id, int status){
        SQLiteDatabase database = null;
        try {
            database = DBHelper.getHelper(context).getWritableDatabase();
            String updateQuery = "UPDATE " + Constants.config.TABLE_HEALTH + " SET " + HEALTH_STATUS + " = '" + status + "', "+HEALTH_ID+" = '"+health_id+"' where " + HEALTHID + "='" + id + "'  ";
            Log.d("query", updateQuery);
            database.execSQL(updateQuery);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                //database.close();
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
            String query = "SELECT "+Constants.config.HEALTH_ID+" FROM" +
                    " "+ Constants.config.TABLE_HEALTH+"  ORDER BY "+Constants.config.HEALTHID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    last_id = cursor.getInt(cursor.getColumnIndex(Constants.config.HEALTH_ID));
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
            // startTime = System.nanoTime();
            // progressDialog.setTitle("Now Saving ! ...");
        }
        @Override
        protected String doInBackground(JSONArray... jsonArrays) {
            String message = null;
            int status = 1;
            SQLiteDatabase db = DBHelper.getHelper(context).getWritableDatabase();
            try{
                db.beginTransactionNonExclusive();
                //String get_json = get
                //JSONArray jsonArray = new JSONArray(results);
                JSONArray jsonArray = jsonArrays[0];
                db.execSQL("DELETE FROM " + Constants.config.TABLE_HEALTH+" WHERE "+HEALTH_STATUS+" = '"+status+"'");

                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                     contentValues.put(Constants.config.HEALTH_NAME,jsonObject.getString(Constants.config.HEALTH_NAME));
                    contentValues.put(Constants.config.DISTRICT_NAME,jsonObject.getString(Constants.config.DISTRICT_NAME));
                    contentValues.put(HEALTH_ID,jsonObject.getLong(HEALTH_ID));
                    contentValues.put(Constants.config.FACILITY_OWNER,jsonObject.getString(Constants.config.FACILITY_OWNER));
                    contentValues.put(Constants.config.FACILITY_TYPE,jsonObject.getString(Constants.config.FACILITY_TYPE));
                    contentValues.put(Constants.config.HEALTH_CADRE,jsonObject.getString(Constants.config.HEALTH_CADRE));
                    contentValues.put(Constants.config.HEALTH_STATUS,status);
                    db.insert(Constants.config.TABLE_HEALTH, null, contentValues);
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records ,"+Constants.config.TABLE_HEALTH+" Updated successfully!";

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
