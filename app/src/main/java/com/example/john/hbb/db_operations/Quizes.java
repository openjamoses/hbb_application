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
import static com.example.john.hbb.core.Constants.config.QUIZ1ID;
import static com.example.john.hbb.core.Constants.config.QUIZ1_DATE;
import static com.example.john.hbb.core.Constants.config.QUIZ1_ID;
import static com.example.john.hbb.core.Constants.config.QUIZ1_IMEI;
import static com.example.john.hbb.core.Constants.config.QUIZ1_NAMES;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q1;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q10;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q11;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q12;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q13;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q14;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q15;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q16;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q17;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q18;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q19;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q2;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q20;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q21;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q22;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q23;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q24;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q25;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q26;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q3;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q4;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q5;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q6;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q7;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q8;
import static com.example.john.hbb.core.Constants.config.QUIZ1_Q9;
import static com.example.john.hbb.core.Constants.config.QUIZ1_STATUS;
import static com.example.john.hbb.core.Constants.config.QUIZ1_TIME;
import static com.example.john.hbb.core.Constants.config.QUIZ1_TYPE;
import static com.example.john.hbb.core.Constants.config.QUIZ1_USER_ID;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_PREPARATION;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_QUIZ1;

/**
 * Created by john on 4/26/18.
 */

public class Quizes {
    private Context context;
    private static final String TAG = "User";
    public Quizes(Context context){
        this.context = context;
    }

    public String save(int quiz1_id, String quiz1_date, String quiz1_time, int quiz1_user_id, String quiz1_names, String quiz1_imei,int quiz1_status,
                       int quiz1_q1, int quiz1_q2, int quiz1_q3, int quiz1_q4, int quiz1_q5, int quiz1_q6, int quiz1_q7, int quiz1_q8, int quiz1_q9,
                       int quiz1_q10, int quiz1_q11, int quiz1_q12, int quiz1_q13, int quiz1_q14, int quiz1_q15, int quiz1_q16, int quiz1_q17, int quiz1_q18,
                       int quiz1_q19, int quiz1_q20, int quiz1_q21, int quiz1_q22, int quiz1_q23, int quiz1_q24, int quiz1_q25, int quiz1_q26,  int log_id,int quiz1_type){
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;
        try{
            database.beginTransactionNonExclusive();
            ContentValues contentValues = new ContentValues();
            contentValues.put(QUIZ1_ID,quiz1_id);
            contentValues.put(QUIZ1_DATE,quiz1_date);
            contentValues.put(QUIZ1_TIME,quiz1_time);
            contentValues.put(QUIZ1_USER_ID,quiz1_user_id);
            contentValues.put(QUIZ1_NAMES,quiz1_names);
            contentValues.put(QUIZ1_TYPE,quiz1_type);
            contentValues.put(QUIZ1_IMEI,quiz1_imei);
            contentValues.put(QUIZ1_STATUS,quiz1_status);
            contentValues.put(QUIZ1_Q1,quiz1_q1);
            contentValues.put(QUIZ1_Q2,quiz1_q2);
            contentValues.put(QUIZ1_Q3,quiz1_q3);
            contentValues.put(QUIZ1_Q4,quiz1_q4);
            contentValues.put(QUIZ1_Q5,quiz1_q5);
            contentValues.put(QUIZ1_Q6,quiz1_q6);
            contentValues.put(QUIZ1_Q7,quiz1_q7);
            contentValues.put(QUIZ1_Q8,quiz1_q8);
            contentValues.put(QUIZ1_Q9,quiz1_q9);
            contentValues.put(QUIZ1_Q10,quiz1_q10);
            contentValues.put(QUIZ1_Q11,quiz1_q11);
            contentValues.put(QUIZ1_Q12,quiz1_q12);
            contentValues.put(QUIZ1_Q13,quiz1_q13);
            contentValues.put(QUIZ1_Q14,quiz1_q14);
            contentValues.put(QUIZ1_Q15,quiz1_q15);
            contentValues.put(QUIZ1_Q16,quiz1_q16);
            contentValues.put(QUIZ1_Q17,quiz1_q17);
            contentValues.put(QUIZ1_Q18,quiz1_q18);
            contentValues.put(QUIZ1_Q19,quiz1_q19);
            contentValues.put(QUIZ1_Q20,quiz1_q20);
            contentValues.put(QUIZ1_Q21,quiz1_q21);
            contentValues.put(QUIZ1_Q22,quiz1_q22);
            contentValues.put(QUIZ1_Q23,quiz1_q23);
            contentValues.put(QUIZ1_Q24,quiz1_q24);
            contentValues.put(QUIZ1_Q25,quiz1_q25);
            contentValues.put(QUIZ1_Q26,quiz1_q26);
            contentValues.put(LOG_ID,log_id);
            database.insert(Constants.config.TABLE_QUIZ1, null, contentValues);
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

    public void send(final String quiz1_date, final String quiz1_time, final int quiz1_user_id, final String quiz1_names, final String quiz1_imei, final int quiz1_type,
                     final int quiz1_q1, final int quiz1_q2, final int quiz1_q3, final int quiz1_q4, final int quiz1_q5, final int quiz1_q6, final int quiz1_q7, final int quiz1_q8, final int quiz1_q9,
                     final int quiz1_q10, final int quiz1_q11, final int quiz1_q12, final int quiz1_q13, final int quiz1_q14, final int quiz1_q15, final int quiz1_q16, final int quiz1_q17, final int quiz1_q18,
                     final int quiz1_q19, final int quiz1_q20, final int quiz1_q21, final int quiz1_q22, final int quiz1_q23, final int quiz1_q24, final int quiz1_q25, final int quiz1_q26, final int log_id){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_QUIZ1,
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
                            String message = save(id,quiz1_date,quiz1_time,quiz1_user_id,quiz1_names,quiz1_imei,status,quiz1_q1,quiz1_q2,quiz1_q3,quiz1_q4,quiz1_q5,quiz1_q6,
                                    quiz1_q7,quiz1_q8,quiz1_q9,quiz1_q10,quiz1_q11,quiz1_q12,quiz1_q13,quiz1_q14,quiz1_q15,quiz1_q16,quiz1_q17,quiz1_q18,quiz1_q19,quiz1_q20,
                                    quiz1_q21,quiz1_q22,quiz1_q23,quiz1_q24,quiz1_q25,quiz1_q26,log_id,quiz1_type);


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
                params.put(QUIZ1_DATE,quiz1_date);
                params.put(QUIZ1_TIME,quiz1_time);
                params.put(QUIZ1_USER_ID, String.valueOf(quiz1_user_id));
                params.put(QUIZ1_NAMES,quiz1_names);
                params.put(QUIZ1_IMEI,quiz1_imei);
                params.put(QUIZ1_TYPE, String.valueOf(quiz1_type));
                params.put(QUIZ1_Q1, String.valueOf(quiz1_q1));
                params.put(QUIZ1_Q2, String.valueOf(quiz1_q2));
                params.put(QUIZ1_Q3, String.valueOf(quiz1_q3));
                params.put(QUIZ1_Q4, String.valueOf(quiz1_q4));
                params.put(QUIZ1_Q5, String.valueOf(quiz1_q5));
                params.put(QUIZ1_Q6, String.valueOf(quiz1_q6));
                params.put(QUIZ1_Q7, String.valueOf(quiz1_q7));
                params.put(QUIZ1_Q8, String.valueOf(quiz1_q8));
                params.put(QUIZ1_Q9, String.valueOf(quiz1_q9));
                params.put(QUIZ1_Q10, String.valueOf(quiz1_q10));
                params.put(QUIZ1_Q11, String.valueOf(quiz1_q11));
                params.put(QUIZ1_Q12, String.valueOf(quiz1_q12));
                params.put(QUIZ1_Q13, String.valueOf(quiz1_q13));
                params.put(QUIZ1_Q14, String.valueOf(quiz1_q14));
                params.put(QUIZ1_Q15, String.valueOf(quiz1_q15));
                params.put(QUIZ1_Q16, String.valueOf(quiz1_q16));
                params.put(QUIZ1_Q17, String.valueOf(quiz1_q17));
                params.put(QUIZ1_Q18, String.valueOf(quiz1_q18));
                params.put(QUIZ1_Q19, String.valueOf(quiz1_q19));
                params.put(QUIZ1_Q20, String.valueOf(quiz1_q20));
                params.put(QUIZ1_Q21, String.valueOf(quiz1_q21));
                params.put(QUIZ1_Q22, String.valueOf(quiz1_q22));
                params.put(QUIZ1_Q23, String.valueOf(quiz1_q23));
                params.put(QUIZ1_Q24, String.valueOf(quiz1_q24));
                params.put(QUIZ1_Q25, String.valueOf(quiz1_q25));
                params.put(QUIZ1_Q26, String.valueOf(quiz1_q26));
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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_QUIZ1 ;

        int status = 1;
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1ID))));

                params.put(QUIZ1_DATE,cursor.getString(cursor.getColumnIndex(QUIZ1_DATE)));
                params.put(QUIZ1_TIME,cursor.getString(cursor.getColumnIndex(QUIZ1_TIME)));
                params.put(QUIZ1_USER_ID,String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_USER_ID))));
                params.put(QUIZ1_NAMES,cursor.getString(cursor.getColumnIndex(QUIZ1_NAMES)));
                params.put(QUIZ1_IMEI,cursor.getString(cursor.getColumnIndex(QUIZ1_IMEI)));
                params.put(QUIZ1_TYPE, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_TYPE))));
                params.put(QUIZ1_Q1,String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q1))));
                params.put(QUIZ1_Q2,String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q2))));
                params.put(QUIZ1_Q3,String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q3))));
                params.put(QUIZ1_Q4,String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q4))));
                params.put(QUIZ1_Q5, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q5))));
                params.put(QUIZ1_Q6, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q6))));
                params.put(QUIZ1_Q7, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q7))));
                params.put(QUIZ1_Q8, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q8))));
                params.put(QUIZ1_Q9, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q9))));
                params.put(QUIZ1_Q10, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q10))));
                params.put(QUIZ1_Q11, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q11))));
                params.put(QUIZ1_Q12, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q12))));
                params.put(QUIZ1_Q13, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q13))));
                params.put(QUIZ1_Q14, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q14))));
                params.put(QUIZ1_Q15, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q15))));
                params.put(QUIZ1_Q16, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q16))));
                params.put(QUIZ1_Q17, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q17))));
                params.put(QUIZ1_Q18, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q18))));
                params.put(QUIZ1_Q19, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q19))));
                params.put(QUIZ1_Q20, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q20))));
                params.put(QUIZ1_Q21, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q21))));
                params.put(QUIZ1_Q22, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q22))));
                params.put(QUIZ1_Q23, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q23))));
                params.put(QUIZ1_Q24, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q24))));
                params.put(QUIZ1_Q25, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q25))));
                params.put(QUIZ1_Q26, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q26))));
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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_QUIZ1 + " WHERE " + QUIZ1_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1ID))));

                params.put(QUIZ1_DATE,cursor.getString(cursor.getColumnIndex(QUIZ1_DATE)));
                params.put(QUIZ1_TIME,cursor.getString(cursor.getColumnIndex(QUIZ1_TIME)));
                params.put(QUIZ1_USER_ID,String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_USER_ID))));
                params.put(QUIZ1_NAMES,cursor.getString(cursor.getColumnIndex(QUIZ1_NAMES)));
                params.put(QUIZ1_IMEI,cursor.getString(cursor.getColumnIndex(QUIZ1_IMEI)));
                params.put(QUIZ1_TYPE, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_TYPE))));
                params.put(QUIZ1_Q1,String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q1))));
                params.put(QUIZ1_Q2,String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q2))));
                params.put(QUIZ1_Q3,String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q3))));
                params.put(QUIZ1_Q4,String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q4))));
                params.put(QUIZ1_Q5, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q5))));
                params.put(QUIZ1_Q6, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q6))));
                params.put(QUIZ1_Q7, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q7))));
                params.put(QUIZ1_Q8, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q8))));
                params.put(QUIZ1_Q9, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q9))));
                params.put(QUIZ1_Q10, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q10))));
                params.put(QUIZ1_Q11, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q11))));
                params.put(QUIZ1_Q12, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q12))));
                params.put(QUIZ1_Q13, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q13))));
                params.put(QUIZ1_Q14, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q14))));
                params.put(QUIZ1_Q15, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q15))));
                params.put(QUIZ1_Q16, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q16))));
                params.put(QUIZ1_Q17, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q17))));
                params.put(QUIZ1_Q18, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q18))));
                params.put(QUIZ1_Q19, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q19))));
                params.put(QUIZ1_Q20, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q20))));
                params.put(QUIZ1_Q21, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q21))));
                params.put(QUIZ1_Q22, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q22))));
                params.put(QUIZ1_Q23, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q23))));
                params.put(QUIZ1_Q24, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q24))));
                params.put(QUIZ1_Q25, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q25))));
                params.put(QUIZ1_Q26, String.valueOf(cursor.getInt(cursor.getColumnIndex(QUIZ1_Q26))));
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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_QUIZ1+ " WHERE " + QUIZ1_STATUS + " = '" + status + "' ";
            database = DBHelper.getHelper(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            count = cursor.getCount();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                //database.close();
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
    public void updateSyncStatus(int id, int prep_id, int status){
        SQLiteDatabase database = null;
        try {
            database = DBHelper.getHelper(context).getWritableDatabase();
            String updateQuery = "UPDATE " + Constants.config.TABLE_QUIZ1 + " SET " + QUIZ1_STATUS + " = '" + status + "', "+QUIZ1_ID+" = '"+prep_id+"'  where " + QUIZ1ID + "='" + id + "'  ";
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

    public  int selectLast(){
        int last_id = 0;
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransactionNonExclusive();
            String query = "SELECT "+Constants.config.QUIZ1_ID+" FROM" +
                    " "+ Constants.config.TABLE_QUIZ1+"  ORDER BY "+Constants.config.QUIZ1_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    last_id = cursor.getInt(cursor.getColumnIndex(Constants.config.QUIZ1_ID));
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
            SQLiteDatabase db = DBHelper.getHelper(context).getWritableDB();
            try{
                db.beginTransactionNonExclusive();
                //String get_json = get
                //JSONArray jsonArray = new JSONArray(results);
                JSONArray jsonArray = jsonArrays[0];
                db.execSQL("DELETE FROM " + Constants.config.TABLE_QUIZ1+" WHERE "+QUIZ1_STATUS+" = '"+status+"' ");

                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(QUIZ1_ID,jsonObject.getLong(Constants.config.QUIZ1_ID));
                    contentValues.put(QUIZ1_DATE,jsonObject.getString(Constants.config.QUIZ1_DATE));
                    contentValues.put(QUIZ1_TIME,jsonObject.getString(Constants.config.QUIZ1_TIME));
                    contentValues.put(QUIZ1_USER_ID,jsonObject.getLong(Constants.config.QUIZ1_USER_ID));
                    contentValues.put(QUIZ1_NAMES,jsonObject.getString(Constants.config.QUIZ1_NAMES));
                    contentValues.put(QUIZ1_TYPE,jsonObject.getLong(Constants.config.QUIZ1_TYPE));
                    contentValues.put(QUIZ1_IMEI,jsonObject.getString(Constants.config.QUIZ1_IMEI));
                    contentValues.put(QUIZ1_STATUS,jsonObject.getLong(Constants.config.QUIZ1_STATUS));
                    contentValues.put(QUIZ1_Q1,jsonObject.getLong(Constants.config.QUIZ1_Q1));
                    contentValues.put(QUIZ1_Q2,jsonObject.getLong(Constants.config.QUIZ1_Q2));
                    contentValues.put(QUIZ1_Q3,jsonObject.getLong(Constants.config.QUIZ1_Q3));
                    contentValues.put(QUIZ1_Q4,jsonObject.getLong(Constants.config.QUIZ1_Q4));
                    contentValues.put(QUIZ1_Q5,jsonObject.getLong(Constants.config.QUIZ1_Q5));
                    contentValues.put(QUIZ1_Q6,jsonObject.getLong(Constants.config.QUIZ1_Q6));
                    contentValues.put(QUIZ1_Q7,jsonObject.getLong(Constants.config.QUIZ1_Q7));
                    contentValues.put(QUIZ1_Q8,jsonObject.getLong(Constants.config.QUIZ1_Q8));
                    contentValues.put(QUIZ1_Q9,jsonObject.getLong(Constants.config.QUIZ1_Q9));
                    contentValues.put(QUIZ1_Q10,jsonObject.getLong(Constants.config.QUIZ1_Q10));
                    contentValues.put(QUIZ1_Q11,jsonObject.getLong(Constants.config.QUIZ1_Q11));
                    contentValues.put(QUIZ1_Q12,jsonObject.getLong(Constants.config.QUIZ1_Q12));
                    contentValues.put(QUIZ1_Q13,jsonObject.getLong(Constants.config.QUIZ1_Q13));
                    contentValues.put(QUIZ1_Q14,jsonObject.getLong(Constants.config.QUIZ1_Q14));
                    contentValues.put(QUIZ1_Q15,jsonObject.getLong(Constants.config.QUIZ1_Q15));
                    contentValues.put(QUIZ1_Q16,jsonObject.getLong(Constants.config.QUIZ1_Q16));
                    contentValues.put(QUIZ1_Q17,jsonObject.getLong(Constants.config.QUIZ1_Q17));
                    contentValues.put(QUIZ1_Q18,jsonObject.getLong(Constants.config.QUIZ1_Q18));
                    contentValues.put(QUIZ1_Q19,jsonObject.getLong(Constants.config.QUIZ1_Q19));
                    contentValues.put(QUIZ1_Q20,jsonObject.getLong(Constants.config.QUIZ1_Q20));
                    contentValues.put(QUIZ1_Q21,jsonObject.getLong(Constants.config.QUIZ1_Q21));
                    contentValues.put(QUIZ1_Q22,jsonObject.getLong(Constants.config.QUIZ1_Q22));
                    contentValues.put(QUIZ1_Q23,jsonObject.getLong(Constants.config.QUIZ1_Q23));
                    contentValues.put(QUIZ1_Q24,jsonObject.getLong(Constants.config.QUIZ1_Q24));
                    contentValues.put(QUIZ1_Q25,jsonObject.getLong(Constants.config.QUIZ1_Q25));
                    contentValues.put(QUIZ1_Q26,jsonObject.getLong(Constants.config.QUIZ1_Q26));
                    contentValues.put(LOG_ID,jsonObject.getLong(Constants.config.LOG_ID));

                    db = DBHelper.getHelper(context).getReadableDB();
                    String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_QUIZ1+ " WHERE "+QUIZ1_IMEI+" = '"+jsonObject.getString(Constants.config.QUIZ1_IMEI)+"'" +
                            " AND "+QUIZ1_DATE+" = '"+jsonObject.getString(Constants.config.QUIZ1_DATE)+"' AND  " + QUIZ1_TIME + " = '" + jsonObject.getString(Constants.config.QUIZ1_TIME) + "' ";
                    db = new DBHelper(context).getReadableDatabase();
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()){
                        do {
                            int stat = cursor.getInt(cursor.getColumnIndex(Constants.config.QUIZ1_TYPE));
                            int id = cursor.getInt(cursor.getColumnIndex(Constants.config.QUIZ1ID));
                            if (stat == 0){
                                db = DBHelper.getHelper(context).getWritableDB();
                                db.update(Constants.config.TABLE_PREPARATION,contentValues,QUIZ1ID+"="+id, null);
                            }
                        }while (cursor.moveToNext());
                    }else {
                        db = DBHelper.getHelper(context).getWritableDB();
                        db.insert(Constants.config.TABLE_QUIZ1, null, contentValues);
                    }
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records , "+Constants.config.TABLE_QUIZ1+" Updated successfully!";

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
