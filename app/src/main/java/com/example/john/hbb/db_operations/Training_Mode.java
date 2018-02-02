package com.example.john.hbb.db_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.example.john.hbb.configuration.Constants.config.CONTACT;
import static com.example.john.hbb.configuration.Constants.config.DISTRICT_NAME;
import static com.example.john.hbb.configuration.Constants.config.EMAIL;
import static com.example.john.hbb.configuration.Constants.config.FACILITY_OWNER;
import static com.example.john.hbb.configuration.Constants.config.FACILITY_TYPE;
import static com.example.john.hbb.configuration.Constants.config.FIRST_NAME;
import static com.example.john.hbb.configuration.Constants.config.GENDER;
import static com.example.john.hbb.configuration.Constants.config.HEALTH_CADRE;
import static com.example.john.hbb.configuration.Constants.config.HEALTH_FACILITY;
import static com.example.john.hbb.configuration.Constants.config.HOST_URL;
import static com.example.john.hbb.configuration.Constants.config.LAST_NAME;
import static com.example.john.hbb.configuration.Constants.config.PASSWORD;
import static com.example.john.hbb.configuration.Constants.config.TRAINING_DATE;
import static com.example.john.hbb.configuration.Constants.config.TRAINING_FREQUENCY;
import static com.example.john.hbb.configuration.Constants.config.TRAINING_ID;
import static com.example.john.hbb.configuration.Constants.config.TRAINING_KEY_SKILL;
import static com.example.john.hbb.configuration.Constants.config.TRAINING_KEY_SUBSKILL;
import static com.example.john.hbb.configuration.Constants.config.TRAINING_NAME;
import static com.example.john.hbb.configuration.Constants.config.TRAINING_SYNC_STATUS;
import static com.example.john.hbb.configuration.Constants.config.TRAINING_TIME;
import static com.example.john.hbb.configuration.Constants.config.URL_SAVE_TRAINING;
import static com.example.john.hbb.configuration.Constants.config.URL_SAVE_USER;
import static com.example.john.hbb.configuration.Constants.config.USER_ID;
import static com.example.john.hbb.configuration.Constants.config.USER_STATUS;
import static com.example.john.hbb.configuration.Constants.config.VERIFIED_STATUS;

/**
 * Created by john on 10/20/17.
 */

public class Training_Mode {
    private Context context;
    private static final String TAG = "Training_Mode";
    public Training_Mode(Context context){
        this.context = context;
    }

    public String save(int userID, String name, String date, String time, int frequency, String key_skills,String key_subSkill, int sync_status){
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;
        try{
            database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(Constants.config.USER_ID,userID);
            contentValues.put(Constants.config.TRAINING_NAME,name);
            contentValues.put(Constants.config.TRAINING_DATE,date);
            contentValues.put(Constants.config.TRAINING_TIME,time);
            contentValues.put(Constants.config.TRAINING_FREQUENCY,frequency);
            contentValues.put(Constants.config.TRAINING_KEY_SKILL,key_skills);
            contentValues.put(Constants.config.TRAINING_KEY_SUBSKILL,key_subSkill);
            contentValues.put(Constants.config.TRAINING_SYNC_STATUS,sync_status);

            ///
            database.insert(Constants.config.TABLE_TRAINING_MODE, null, contentValues);
            database.setTransactionSuccessful();
            message = "Training Details saved!";
            Log.e("###","----------------------------------------");
            Log.e("User_ID",userID+"");
            Log.e("Training_Name",name);
            Log.e("Training_Date",date);
            Log.e("Training_Time",time);
            Log.e("Training_Frequency",frequency+"");
            Log.e("Training_keyskills",key_skills);
            Log.e("Training_Syncstatus",sync_status+"");
            Log.e("###","----------------------------------------");

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            database.endTransaction();
        }

        return message;
    } //////

    public void send(final int userID, final String name, final String date, final String time, final int frequency, final String key_skills, final String key_subSkill){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_TRAINING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            int status = 0;
                            if (response.equals("Success")){
                                status = 1;
                            }
                            String message = save(userID, name, date, time, frequency, key_skills, key_subSkill,status);
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
                int status = 1;
                //Adding parameters
                params.put("training_id", String.valueOf(status));
                params.put(USER_ID, String.valueOf(userID));
                params.put(TRAINING_NAME, name);
                params.put(TRAINING_DATE, date);
                params.put(TRAINING_TIME, time);
                params.put(TRAINING_FREQUENCY, String.valueOf(frequency));
                params.put(TRAINING_KEY_SKILL, key_skills);
                params.put(TRAINING_KEY_SUBSKILL, key_subSkill);


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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_TRAINING_MODE ;

        int status = 1;
        SQLiteDatabase database = new DBHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAINING_ID))));

                params.put("training_id", String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAINING_ID))));
                params.put(USER_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(USER_ID))));
                params.put(TRAINING_NAME, cursor.getString(cursor.getColumnIndex(TRAINING_NAME)));
                params.put(TRAINING_DATE, cursor.getString(cursor.getColumnIndex(TRAINING_DATE)));
                params.put(TRAINING_TIME, cursor.getString(cursor.getColumnIndex(TRAINING_TIME)));
                params.put(TRAINING_FREQUENCY, String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAINING_FREQUENCY))));
                params.put(TRAINING_KEY_SKILL, cursor.getString(cursor.getColumnIndex(TRAINING_KEY_SKILL)));
                params.put(TRAINING_KEY_SUBSKILL, cursor.getString(cursor.getColumnIndex(TRAINING_KEY_SUBSKILL)));

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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_TRAINING_MODE + " WHERE " + TRAINING_SYNC_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = new DBHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAINING_ID))));

                params.put("training_id", String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAINING_ID))));
                params.put(USER_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(USER_ID))));
                params.put(TRAINING_NAME, cursor.getString(cursor.getColumnIndex(TRAINING_NAME)));
                params.put(TRAINING_DATE, cursor.getString(cursor.getColumnIndex(TRAINING_DATE)));
                params.put(TRAINING_TIME, cursor.getString(cursor.getColumnIndex(TRAINING_TIME)));
                params.put(TRAINING_FREQUENCY, String.valueOf(cursor.getInt(cursor.getColumnIndex(TRAINING_FREQUENCY))));
                params.put(TRAINING_KEY_SKILL, cursor.getString(cursor.getColumnIndex(TRAINING_KEY_SKILL)));
                params.put(TRAINING_KEY_SUBSKILL, cursor.getString(cursor.getColumnIndex(TRAINING_KEY_SUBSKILL)));

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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_TRAINING_MODE+ " WHERE " + TRAINING_SYNC_STATUS + " = '" + status + "' ";
            database = new DBHelper(context).getReadableDatabase();
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
    public void updateSyncStatus(int id, int status){
        SQLiteDatabase database = null;
        try {
            database = new DBHelper(context).getWritableDatabase();
            String updateQuery = "UPDATE " + Constants.config.TABLE_TRAINING_MODE + " SET " + TRAINING_SYNC_STATUS + " = '" + status + "' where " + TRAINING_ID + "='" + id + "'  ";
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

}
