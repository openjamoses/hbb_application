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
import com.example.john.hbb.configuration.Constants;
import com.example.john.hbb.configuration.DBHelper;
import com.example.john.hbb.configuration.DateTime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

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
import static com.example.john.hbb.configuration.Constants.config.URL_SAVE_USER;
import static com.example.john.hbb.configuration.Constants.config.USER_ID;
import static com.example.john.hbb.configuration.Constants.config.USER_STATUS;
import static com.example.john.hbb.configuration.Constants.config.VERIFIED_STATUS;

/**
 * Created by john on 10/20/17.
 */

public class User {
    private Context context;
    private static final String TAG = "User";
    public User(Context context){
        this.context = context;
    }

    public String save(String fname, String lname, String email, String phone, String gender, String facility, String password){
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;
        int status = 0;
        try{
            // database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(Constants.config.FIRST_NAME,fname);
            contentValues.put(Constants.config.LAST_NAME,lname);
            contentValues.put(EMAIL,email);
            contentValues.put(Constants.config.CONTACT,phone);
            contentValues.put(Constants.config.GENDER,gender);
            contentValues.put(Constants.config.HEALTH_FACILITY,facility);
            contentValues.put(Constants.config.PASSWORD,password);
            contentValues.put(Constants.config.USER_STATUS,status);

            database.insert(Constants.config.TABLE_USERS, null, contentValues);
            //database.setTransactionSuccessful();
            message = "Registration Details saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            database.close();
        }

        return message;
    }

    public String saveAll(String fname, String lname, String contact, String gender, String email, String password, String health, String district2, String selected_type, String selected_ownership, String selected_cadre , int status) {
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        int id = new District(context).select(district2);
        if (id == 0){
            String message = new District(context).save(district2, DateTime.getCurrentDate()+" "+DateTime.getCurrentTime(),0);
        }
        String message = null;
        try{
            // database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(Constants.config.FIRST_NAME,fname);
            contentValues.put(Constants.config.LAST_NAME,lname);
            contentValues.put(EMAIL,email);
            contentValues.put(Constants.config.CONTACT,contact);
            contentValues.put(Constants.config.GENDER,gender);
            contentValues.put(Constants.config.HEALTH_FACILITY,health);
            contentValues.put(Constants.config.PASSWORD,password);
            contentValues.put(Constants.config.USER_STATUS,status);
            contentValues.put(Constants.config.FACILITY_TYPE,selected_type);
            contentValues.put(Constants.config.FACILITY_OWNER,selected_ownership);
            contentValues.put(Constants.config.HEALTH_CADRE,selected_cadre);
            contentValues.put(Constants.config.DISTRICT_NAME,district2);
            contentValues.put(VERIFIED_STATUS,status);
            database.insert(Constants.config.TABLE_USERS, null, contentValues);
            //database.setTransactionSuccessful();
            message = "Registration Details saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            database.close();
        }

        return message;
    }
    public void updateUserStatus(String email, int status){
        SQLiteDatabase database = null;
        try {
            database = new DBHelper(context).getWritableDatabase();
            String updateQuery = "UPDATE " + Constants.config.TABLE_USERS + " SET " + VERIFIED_STATUS + " = '" + status + "' where " + EMAIL + "='" + email + "'  ";
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

    public void updateUserPassword(String email, String password) {
        SQLiteDatabase database = null;
        try {
            database = new DBHelper(context).getWritableDatabase();
            String updateQuery = "UPDATE " + Constants.config.TABLE_USERS + " SET " + PASSWORD + " = '" + password + "' where " + EMAIL + "='" + email + "'  ";
            Log.d("query", updateQuery);
            database.execSQL(updateQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                database.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void send(final String fname, final String lname, final String contact, final String gender, final String email, final String password,
                     final String health, final String district, final String selected_type,
                     final String selected_ownership, final String selected_cadre){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            int status = 0;
                            if (response.equals("Success")){
                                status = 1;
                            }
                            String message = saveAll(fname, lname,  contact,  gender,  email,  password,  health,  district,  selected_type, selected_ownership,selected_cadre,status);

                            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                            View view = toast.getView();
                            view.setBackgroundResource(R.drawable.round_conor);
                            TextView text = (TextView) view.findViewById(android.R.id.message);
                        /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                            //toast.show();
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

                            Toast toast = Toast.makeText(context, "Check our internet Connections", Toast.LENGTH_LONG);
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
                params.put(FIRST_NAME, fname);
                params.put(LAST_NAME, lname);
                params.put(CONTACT, contact);
                params.put(HEALTH_FACILITY, health);
                params.put(GENDER, gender);
                params.put(EMAIL, email);
                params.put(PASSWORD, password);
                params.put(USER_STATUS, String.valueOf(status));
                params.put(FACILITY_TYPE,selected_type );
                params.put(DISTRICT_NAME,district);
                params.put(FACILITY_OWNER, selected_ownership);
                params.put(HEALTH_CADRE, selected_cadre);
                params.put(VERIFIED_STATUS, String.valueOf(status));

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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_USERS ;

        int status = 1;
        SQLiteDatabase database = new DBHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(USER_ID))));

                params.put(FIRST_NAME, cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
                params.put(LAST_NAME, cursor.getString(cursor.getColumnIndex(LAST_NAME)));
                params.put(CONTACT, cursor.getString(cursor.getColumnIndex(CONTACT)));
                params.put(HEALTH_FACILITY, cursor.getString(cursor.getColumnIndex(HEALTH_FACILITY)));
                params.put(GENDER, cursor.getString(cursor.getColumnIndex(GENDER)));
                params.put(EMAIL, cursor.getString(cursor.getColumnIndex(EMAIL)));
                params.put(PASSWORD, cursor.getString(cursor.getColumnIndex(PASSWORD)));
                params.put(USER_STATUS, String.valueOf(status));
                params.put(FACILITY_TYPE,cursor.getString(cursor.getColumnIndex(FACILITY_TYPE)) );
                params.put(DISTRICT_NAME,cursor.getString(cursor.getColumnIndex(DISTRICT_NAME)) );
                params.put(FACILITY_OWNER, cursor.getString(cursor.getColumnIndex(FACILITY_OWNER)));
                params.put(HEALTH_CADRE, cursor.getString(cursor.getColumnIndex(HEALTH_CADRE)));
                params.put(VERIFIED_STATUS, String.valueOf(cursor.getInt(cursor.getColumnIndex(VERIFIED_STATUS))));

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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_USERS + " WHERE " + USER_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = new DBHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(USER_ID))));

                params.put(FIRST_NAME, cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
                params.put(LAST_NAME, cursor.getString(cursor.getColumnIndex(LAST_NAME)));
                params.put(CONTACT, cursor.getString(cursor.getColumnIndex(CONTACT)));
                params.put(HEALTH_FACILITY, cursor.getString(cursor.getColumnIndex(HEALTH_FACILITY)));
                params.put(GENDER, cursor.getString(cursor.getColumnIndex(GENDER)));
                params.put(EMAIL, cursor.getString(cursor.getColumnIndex(EMAIL)));
                params.put(PASSWORD, cursor.getString(cursor.getColumnIndex(PASSWORD)));
                params.put(USER_STATUS, String.valueOf(status));
                params.put(FACILITY_TYPE,cursor.getString(cursor.getColumnIndex(FACILITY_TYPE)) );
                params.put(DISTRICT_NAME,cursor.getString(cursor.getColumnIndex(DISTRICT_NAME)) );
                params.put(FACILITY_OWNER, cursor.getString(cursor.getColumnIndex(FACILITY_OWNER)));
                params.put(HEALTH_CADRE, cursor.getString(cursor.getColumnIndex(HEALTH_CADRE)));
                params.put(VERIFIED_STATUS, String.valueOf(cursor.getInt(cursor.getColumnIndex(VERIFIED_STATUS))));

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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_USERS+ " WHERE " + USER_STATUS + " = '" + status + "' ";
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
            String updateQuery = "UPDATE " + Constants.config.TABLE_USERS + " SET " + USER_STATUS + " = '" + status + "' where " + USER_ID + "='" + id + "'  ";
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
            SQLiteDatabase db = new DBHelper(context).getWritableDB();
            try{

                db.beginTransaction();
                //String get_json = get
                //JSONArray jsonArray = new JSONArray(results);
                JSONArray jsonArray = jsonArrays[0];
                db.execSQL("DELETE FROM " + Constants.config.TABLE_USERS+" WHERE "+USER_STATUS+" = '"+status+"' ");

                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(Constants.config.FIRST_NAME,jsonObject.getString(Constants.config.FIRST_NAME));
                    contentValues.put(Constants.config.LAST_NAME,jsonObject.getString(Constants.config.LAST_NAME));
                    contentValues.put(EMAIL,jsonObject.getString(Constants.config.EMAIL));
                    contentValues.put(Constants.config.CONTACT,jsonObject.getString(Constants.config.CONTACT));
                    contentValues.put(Constants.config.GENDER,jsonObject.getString(Constants.config.GENDER));
                    contentValues.put(Constants.config.HEALTH_FACILITY,jsonObject.getString(Constants.config.HEALTH_FACILITY));
                    contentValues.put(Constants.config.PASSWORD,jsonObject.getString(Constants.config.PASSWORD));
                    contentValues.put(Constants.config.FACILITY_TYPE,jsonObject.getString(Constants.config.FACILITY_TYPE));
                    contentValues.put(Constants.config.FACILITY_OWNER,jsonObject.getString(Constants.config.FACILITY_OWNER));
                    contentValues.put(Constants.config.HEALTH_CADRE,jsonObject.getString(Constants.config.HEALTH_CADRE));
                    contentValues.put(Constants.config.DISTRICT_NAME,jsonObject.getString(Constants.config.DISTRICT_NAME));
                    contentValues.put(Constants.config.USER_STATUS,status);
                    contentValues.put(VERIFIED_STATUS,status);

                    db.insert(Constants.config.TABLE_USERS, null, contentValues);
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records , Activation Table Updated successfully!";

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

