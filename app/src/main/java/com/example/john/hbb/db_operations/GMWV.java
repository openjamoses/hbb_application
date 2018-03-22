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
import static com.example.john.hbb.core.Constants.config.GMWVID;
import static com.example.john.hbb.core.Constants.config.GMWV_BREATHING;
import static com.example.john.hbb.core.Constants.config.GMWV_CALLS_FORHELP;
import static com.example.john.hbb.core.Constants.config.GMWV_COMMUNICATES_WITHMOTHER;
import static com.example.john.hbb.core.Constants.config.GMWV_CONTINUE_ESSENTAIL;
import static com.example.john.hbb.core.Constants.config.GMWV_CONTINUE_IMPROVE;
import static com.example.john.hbb.core.Constants.config.GMWV_DATE;
import static com.example.john.hbb.core.Constants.config.GMWV_DISINFECT_EQUIPMENT;
import static com.example.john.hbb.core.Constants.config.GMWV_ID;
import static com.example.john.hbb.core.Constants.config.GMWV_IF_BREATHING;
import static com.example.john.hbb.core.Constants.config.GMWV_IF_NOTBREATHING;
import static com.example.john.hbb.core.Constants.config.GMWV_IMEI;
import static com.example.john.hbb.core.Constants.config.GMWV_NORMAL;
import static com.example.john.hbb.core.Constants.config.GMWV_RECOGNISE_NOTBREATHING;
import static com.example.john.hbb.core.Constants.config.GMWV_RECOGNISE_NOTBREATHING_AND_CHEST;
import static com.example.john.hbb.core.Constants.config.GMWV_STATUS;
import static com.example.john.hbb.core.Constants.config.GMWV_TIME;
import static com.example.john.hbb.core.Constants.config.GMWV_TYPE;
import static com.example.john.hbb.core.Constants.config.HOST_URL;
import static com.example.john.hbb.core.Constants.config.LOG_ID;
import static com.example.john.hbb.core.Constants.config.TABLE_GMWV;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_GMWV;

/**
 * Created by john on 3/22/18.
 */

public class GMWV {

    private Context context;
    private static final String TAG = "User";
    public GMWV(Context context){
        this.context = context;
    }

    public String save(int gmwv_id,String gmwv_recognise_nothbeathing_and_chest, String gmwv_calls_forhelp, String gmwv_continue_improve,  String gmwv_recognise_notbreathing, String gmwv_normal,
                       String gmwv_breathing, String gmwv_if_breathing,String gmwv_if_nothbreathing, String gmwv_communicates_withmother,String gmwv_continue_essential,
                       String gmwv_disinfect_equipment,String gmwv_time,String gmwv_date, String gmwv_imei,int gmwv_type ,int log_id,int gmwv_status){
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;
        try{
            // database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(GMWV_ID,gmwv_id);
            contentValues.put(GMWV_RECOGNISE_NOTBREATHING_AND_CHEST,gmwv_recognise_nothbeathing_and_chest);
            contentValues.put(GMWV_CALLS_FORHELP,gmwv_calls_forhelp);
            contentValues.put(GMWV_CONTINUE_IMPROVE,gmwv_continue_improve);
            contentValues.put(GMWV_RECOGNISE_NOTBREATHING,gmwv_recognise_notbreathing);
            contentValues.put(GMWV_NORMAL,gmwv_normal);
            contentValues.put(GMWV_BREATHING,gmwv_breathing);
            contentValues.put(GMWV_IF_BREATHING,gmwv_if_breathing);
            contentValues.put(GMWV_IF_NOTBREATHING,gmwv_if_nothbreathing);
            contentValues.put(GMWV_COMMUNICATES_WITHMOTHER,gmwv_communicates_withmother);
            contentValues.put(GMWV_CONTINUE_ESSENTAIL,gmwv_continue_essential);
            contentValues.put(GMWV_DISINFECT_EQUIPMENT,gmwv_disinfect_equipment);
            contentValues.put(GMWV_TIME,gmwv_time);
            contentValues.put(GMWV_DATE,gmwv_date);
            contentValues.put(GMWV_IMEI,gmwv_imei);
            contentValues.put(GMWV_TYPE,gmwv_type);
            contentValues.put(GMWV_STATUS,gmwv_status);
            contentValues.put(LOG_ID,log_id);

            database.insert(Constants.config.TABLE_GMWV, null, contentValues);
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

    public void send(final String gmwv_recognise_nothbeathing_and_chest, final String gmwv_calls_forhelp, final String gmwv_continue_improve, final String gmwv_recognise_notbreathing, final String gmwv_normal,
                     final String gmwv_breathing, final String gmwv_if_breathing, final String gmwv_if_nothbreathing, final String gmwv_communicates_withmother, final String gmwv_continue_essential,
                     final String gmwv_disinfect_equipment, final String gmwv_time, final String gmwv_date, final String gmwv_imei, final int gmwv_type , final int log_id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_GMWV,
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
                            String message = save(id,gmwv_recognise_nothbeathing_and_chest,gmwv_calls_forhelp,gmwv_continue_improve,gmwv_recognise_notbreathing,gmwv_normal,gmwv_breathing,gmwv_if_breathing,gmwv_if_nothbreathing,gmwv_communicates_withmother,gmwv_continue_essential,gmwv_disinfect_equipment,gmwv_time,gmwv_date,gmwv_imei,gmwv_type,log_id,status);
                            //Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

                            if (message.equals("Details saved!")){
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
                Map<String, String> params = new Hashtable<String, String>();

                params.put(GMWV_RECOGNISE_NOTBREATHING_AND_CHEST,gmwv_recognise_nothbeathing_and_chest);
                params.put(GMWV_CALLS_FORHELP,gmwv_calls_forhelp);
                params.put(GMWV_CONTINUE_IMPROVE,gmwv_continue_improve);
                params.put(GMWV_RECOGNISE_NOTBREATHING,gmwv_recognise_notbreathing);
                params.put(GMWV_NORMAL,gmwv_normal);
                params.put(GMWV_BREATHING,gmwv_breathing);
                params.put(GMWV_IF_BREATHING,gmwv_if_breathing);
                params.put(GMWV_IF_NOTBREATHING,gmwv_if_nothbreathing);
                params.put(GMWV_COMMUNICATES_WITHMOTHER,gmwv_communicates_withmother);
                params.put(GMWV_CONTINUE_ESSENTAIL,gmwv_continue_essential);
                params.put(GMWV_DISINFECT_EQUIPMENT,gmwv_disinfect_equipment);
                params.put(GMWV_TIME,gmwv_time);
                params.put(GMWV_DATE,gmwv_date);
                params.put(GMWV_IMEI,gmwv_imei);
                params.put(GMWV_TYPE, String.valueOf(gmwv_type));
                params.put(LOG_ID, String.valueOf(log_id));
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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_GMWV ;

        int status = 1;
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(GMWVID))));

                params.put(GMWV_RECOGNISE_NOTBREATHING_AND_CHEST,cursor.getString(cursor.getColumnIndex(GMWV_RECOGNISE_NOTBREATHING_AND_CHEST)));
                params.put(GMWV_CALLS_FORHELP,cursor.getString(cursor.getColumnIndex(GMWV_CALLS_FORHELP)));
                params.put(GMWV_CONTINUE_IMPROVE,cursor.getString(cursor.getColumnIndex(GMWV_CONTINUE_IMPROVE)));
                params.put(GMWV_RECOGNISE_NOTBREATHING,cursor.getString(cursor.getColumnIndex(GMWV_RECOGNISE_NOTBREATHING)));
                params.put(GMWV_NORMAL,cursor.getString(cursor.getColumnIndex(GMWV_NORMAL)));
                params.put(GMWV_BREATHING,cursor.getString(cursor.getColumnIndex(GMWV_BREATHING)));
                params.put(GMWV_IF_BREATHING,cursor.getString(cursor.getColumnIndex(GMWV_IF_BREATHING)));
                params.put(GMWV_IF_NOTBREATHING,cursor.getString(cursor.getColumnIndex(GMWV_IF_NOTBREATHING)));
                params.put(GMWV_COMMUNICATES_WITHMOTHER,cursor.getString(cursor.getColumnIndex(GMWV_COMMUNICATES_WITHMOTHER)));
                params.put(GMWV_CONTINUE_ESSENTAIL,cursor.getString(cursor.getColumnIndex(GMWV_CONTINUE_ESSENTAIL)));
                params.put(GMWV_DISINFECT_EQUIPMENT,cursor.getString(cursor.getColumnIndex(GMWV_DISINFECT_EQUIPMENT)));
                params.put(GMWV_TIME,cursor.getString(cursor.getColumnIndex(GMWV_TIME)));
                params.put(GMWV_DATE,cursor.getString(cursor.getColumnIndex(GMWV_DATE)));
                params.put(GMWV_IMEI,cursor.getString(cursor.getColumnIndex(GMWV_IMEI)));
                params.put(GMWV_TYPE, String.valueOf(cursor.getInt(cursor.getColumnIndex(GMWV_TYPE))));
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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_GMWV + " WHERE " + GMWV_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(GMWVID))));

                params.put(GMWV_RECOGNISE_NOTBREATHING_AND_CHEST,cursor.getString(cursor.getColumnIndex(GMWV_RECOGNISE_NOTBREATHING_AND_CHEST)));
                params.put(GMWV_CALLS_FORHELP,cursor.getString(cursor.getColumnIndex(GMWV_CALLS_FORHELP)));
                params.put(GMWV_CONTINUE_IMPROVE,cursor.getString(cursor.getColumnIndex(GMWV_CONTINUE_IMPROVE)));
                params.put(GMWV_RECOGNISE_NOTBREATHING,cursor.getString(cursor.getColumnIndex(GMWV_RECOGNISE_NOTBREATHING)));
                params.put(GMWV_NORMAL,cursor.getString(cursor.getColumnIndex(GMWV_NORMAL)));
                params.put(GMWV_BREATHING,cursor.getString(cursor.getColumnIndex(GMWV_BREATHING)));
                params.put(GMWV_IF_BREATHING,cursor.getString(cursor.getColumnIndex(GMWV_IF_BREATHING)));
                params.put(GMWV_IF_NOTBREATHING,cursor.getString(cursor.getColumnIndex(GMWV_IF_NOTBREATHING)));
                params.put(GMWV_COMMUNICATES_WITHMOTHER,cursor.getString(cursor.getColumnIndex(GMWV_COMMUNICATES_WITHMOTHER)));
                params.put(GMWV_CONTINUE_ESSENTAIL,cursor.getString(cursor.getColumnIndex(GMWV_CONTINUE_ESSENTAIL)));
                params.put(GMWV_DISINFECT_EQUIPMENT,cursor.getString(cursor.getColumnIndex(GMWV_DISINFECT_EQUIPMENT)));
                params.put(GMWV_TIME,cursor.getString(cursor.getColumnIndex(GMWV_TIME)));
                params.put(GMWV_DATE,cursor.getString(cursor.getColumnIndex(GMWV_DATE)));
                params.put(GMWV_IMEI,cursor.getString(cursor.getColumnIndex(GMWV_IMEI)));
                params.put(GMWV_TYPE, String.valueOf(cursor.getInt(cursor.getColumnIndex(GMWV_TYPE))));
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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_GMWV+ " WHERE " + GMWV_STATUS + " = '" + status + "' ";
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
    public void updateSyncStatus(int id, int prep_id, int status){
        SQLiteDatabase database = null;
        try {
            database = DBHelper.getHelper(context).getWritableDatabase();
            String updateQuery = "UPDATE " + Constants.config.TABLE_GMWV + " SET " + GMWV_STATUS + " = '" + status + "', "+GMWV_ID+" = '"+prep_id+"'  where " + GMWVID + "='" + id + "'  ";
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

                    contentValues.put(GMWV_ID,jsonObject.getLong(Constants.config.GMWV_ID));
                    contentValues.put(GMWV_RECOGNISE_NOTBREATHING_AND_CHEST,jsonObject.getString(Constants.config.GMWV_RECOGNISE_NOTBREATHING_AND_CHEST));
                    contentValues.put(GMWV_CALLS_FORHELP,jsonObject.getString(Constants.config.GMWV_CALLS_FORHELP));
                    contentValues.put(GMWV_CONTINUE_IMPROVE,jsonObject.getString(Constants.config.GMWV_CONTINUE_IMPROVE));
                    contentValues.put(GMWV_RECOGNISE_NOTBREATHING,jsonObject.getString(Constants.config.GMWV_RECOGNISE_NOTBREATHING));
                    contentValues.put(GMWV_NORMAL,jsonObject.getString(Constants.config.GMWV_NORMAL));
                    contentValues.put(GMWV_BREATHING,jsonObject.getString(Constants.config.GMWV_BREATHING));
                    contentValues.put(GMWV_IF_BREATHING,jsonObject.getString(Constants.config.GMWV_IF_BREATHING));
                    contentValues.put(GMWV_IF_NOTBREATHING,jsonObject.getString(Constants.config.GMWV_IF_NOTBREATHING));
                    contentValues.put(GMWV_COMMUNICATES_WITHMOTHER,jsonObject.getString(Constants.config.GMWV_COMMUNICATES_WITHMOTHER));
                    contentValues.put(GMWV_CONTINUE_ESSENTAIL,jsonObject.getString(Constants.config.GMWV_CONTINUE_ESSENTAIL));
                    contentValues.put(GMWV_DISINFECT_EQUIPMENT,jsonObject.getString(Constants.config.GMWV_DISINFECT_EQUIPMENT));
                    contentValues.put(GMWV_TIME,jsonObject.getString(Constants.config.GMWV_TIME));
                    contentValues.put(GMWV_DATE,jsonObject.getString(Constants.config.GMWV_DATE));
                    contentValues.put(GMWV_IMEI,jsonObject.getString(Constants.config.GMWV_IMEI));
                    contentValues.put(GMWV_TYPE,jsonObject.getLong(Constants.config.GMWV_TYPE));
                    contentValues.put(GMWV_STATUS,jsonObject.getLong(Constants.config.GMWV_STATUS));
                    contentValues.put(LOG_ID,jsonObject.getLong(Constants.config.LOG_ID));

                    db = DBHelper.getHelper(context).getReadableDB();
                    String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_GMWV+ " WHERE "+GMWV_IMEI+" = '"+jsonObject.getString(Constants.config.GMWV_IMEI)+"'" +
                            " AND "+GMWV_DATE+" = '"+jsonObject.getString(Constants.config.GMWV_DATE)+"' AND  " + GMWV_TIME + " = '" + jsonObject.getString(Constants.config.GMWV_TIME) + "' ";
                    db = new DBHelper(context).getReadableDatabase();
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()){
                        do {
                            int stat = cursor.getInt(cursor.getColumnIndex(Constants.config.GMWV_STATUS));
                            int id = cursor.getInt(cursor.getColumnIndex(Constants.config.GMWVID));
                            if (stat == 0){
                                db = DBHelper.getHelper(context).getWritableDB();
                                db.update(Constants.config.TABLE_GMWV,contentValues,GMWVID+"="+id, null);
                            }
                        }while (cursor.moveToNext());
                    }else {
                        db = DBHelper.getHelper(context).getWritableDB();
                        db.insert(Constants.config.TABLE_GMWV, null, contentValues);

                    }
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records ,"+TABLE_GMWV+"Table Updated successfully!";

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
