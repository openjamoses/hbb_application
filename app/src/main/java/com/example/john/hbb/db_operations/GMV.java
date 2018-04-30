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

import static com.example.john.hbb.core.Constants.config.GMVID;
import static com.example.john.hbb.core.Constants.config.GMV_CONTINUE_ESSENTAIL;
import static com.example.john.hbb.core.Constants.config.GMV_DATE;
import static com.example.john.hbb.core.Constants.config.GMV_DRIES_THOROUGHY;
import static com.example.john.hbb.core.Constants.config.GMV_FOLLOW_ROUTINE;
import static com.example.john.hbb.core.Constants.config.GMV_ID;
import static com.example.john.hbb.core.Constants.config.GMV_IMEI;
import static com.example.john.hbb.core.Constants.config.GMV_KEEPS_WARM;
import static com.example.john.hbb.core.Constants.config.GMV_MONITOR_WITHMOTHER;
import static com.example.john.hbb.core.Constants.config.GMV_MOVE_VENTILATION;
import static com.example.john.hbb.core.Constants.config.GMV_RECOGNISE_BREATHINGWELL;
import static com.example.john.hbb.core.Constants.config.GMV_RECOGNISE_CHESTMOVING;
import static com.example.john.hbb.core.Constants.config.GMV_RECOGNISE_NOTBREATHING;
import static com.example.john.hbb.core.Constants.config.GMV_RECOGNISE_NOTCRYING;
import static com.example.john.hbb.core.Constants.config.GMV_STATUS;
import static com.example.john.hbb.core.Constants.config.GMV_STIMULATES_BREATHING;
import static com.example.john.hbb.core.Constants.config.GMV_TIME;
import static com.example.john.hbb.core.Constants.config.GMV_TYPE;
import static com.example.john.hbb.core.Constants.config.GMV_VENTILATE;
import static com.example.john.hbb.core.Constants.config.HOST_URL;
import static com.example.john.hbb.core.Constants.config.LOG_ID;
import static com.example.john.hbb.core.Constants.config.TABLE_GMV;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_GMV;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_PREPARATION;

/**
 * Created by john on 3/22/18.
 */

public class GMV {
    private Context context;
    private static final String TAG = "User";
    public GMV(Context context){
        this.context = context;
    }

    public String save(int gmv_id,String gmv_dries_thoroughy, String gmv_recognise_notcrying, String gmv_keeps_warm,  String gmv_stimulats_breathing, String gmv_recognise_notbreathing,
                       String gmv_follow_routine, String gmv_move_ventilation,String gmv_ventilation, String gmv_recognise_breathing,String gmv_recognise_chestmoving,
            String gmv_monitor_with_mother,String gmv_continue_essential,String gmv_time, String gmv_date,String gmv_imei,int gmv_type ,int log_id,int gmv_status){
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;
        try{
            database.beginTransactionNonExclusive();
            ContentValues contentValues = new ContentValues();
            contentValues.put(GMV_ID,gmv_id);
            contentValues.put(GMV_DRIES_THOROUGHY,gmv_dries_thoroughy);
            contentValues.put(GMV_RECOGNISE_NOTCRYING,gmv_recognise_notcrying);
            contentValues.put(GMV_KEEPS_WARM,gmv_keeps_warm);
            contentValues.put(GMV_STIMULATES_BREATHING,gmv_stimulats_breathing);
            contentValues.put(GMV_FOLLOW_ROUTINE,gmv_follow_routine);
            contentValues.put(GMV_MOVE_VENTILATION,gmv_move_ventilation);
            contentValues.put(GMV_VENTILATE,gmv_ventilation);
            contentValues.put(GMV_RECOGNISE_NOTBREATHING,gmv_recognise_notbreathing);
            contentValues.put(GMV_RECOGNISE_BREATHINGWELL,gmv_recognise_breathing);
            contentValues.put(GMV_RECOGNISE_CHESTMOVING,gmv_recognise_chestmoving);
            contentValues.put(GMV_MONITOR_WITHMOTHER,gmv_monitor_with_mother);
            contentValues.put(GMV_CONTINUE_ESSENTAIL,gmv_continue_essential);
            contentValues.put(GMV_TIME,gmv_time);
            contentValues.put(GMV_DATE,gmv_date);
            contentValues.put(GMV_IMEI,gmv_imei);
            contentValues.put(GMV_TYPE,gmv_type);
            contentValues.put(GMV_STATUS,gmv_status);
            contentValues.put(LOG_ID,log_id);

            database.insert(Constants.config.TABLE_GMV, null, contentValues);
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

    public void send(final String gmv_dries_thoroughy, final String gmv_recognise_notcrying, final String gmv_keeps_warm, final String gmv_stimulats_breathing, final String gmv_recognise_notbreathing,
                     final String gmv_follow_routine, final String gmv_move_ventilation, final String gmv_ventilation, final String gmv_recognise_breathing, final String gmv_recognise_chestmoving,
                     final String gmv_monitor_with_mother, final String gmv_continue_essential, final String gmv_time, final String gmv_date, final String gmv_imei, final int gmv_type , final int log_id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_GMV,
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
                            String message = save(id,gmv_dries_thoroughy,gmv_recognise_notcrying,gmv_keeps_warm,gmv_stimulats_breathing,gmv_recognise_notbreathing,gmv_follow_routine,gmv_move_ventilation,gmv_ventilation,gmv_recognise_breathing,gmv_recognise_chestmoving,gmv_monitor_with_mother,gmv_continue_essential,gmv_time,gmv_date,gmv_imei,gmv_type,log_id,status);
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

                params.put(GMV_DRIES_THOROUGHY,gmv_dries_thoroughy);
                params.put(GMV_RECOGNISE_NOTCRYING,gmv_recognise_notcrying);
                params.put(GMV_KEEPS_WARM,gmv_keeps_warm);
                params.put(GMV_STIMULATES_BREATHING,gmv_stimulats_breathing);
                params.put(GMV_FOLLOW_ROUTINE,gmv_follow_routine);
                params.put(GMV_MOVE_VENTILATION,gmv_move_ventilation);
                params.put(GMV_VENTILATE,gmv_ventilation);
                params.put(GMV_RECOGNISE_NOTBREATHING,gmv_recognise_notbreathing);
                params.put(GMV_RECOGNISE_BREATHINGWELL,gmv_recognise_breathing);
                params.put(GMV_RECOGNISE_CHESTMOVING,gmv_recognise_chestmoving);
                params.put(GMV_MONITOR_WITHMOTHER,gmv_monitor_with_mother);
                params.put(GMV_CONTINUE_ESSENTAIL,gmv_continue_essential);
                params.put(GMV_TIME,gmv_time);
                params.put(GMV_DATE,gmv_date);
                params.put(GMV_IMEI,gmv_imei);
                params.put(GMV_TYPE, String.valueOf(gmv_type));
                params.put(LOG_ID, String.valueOf(log_id));
                //Adding parameters
                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public  int selectLast(){
        int last_id = 0;
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransactionNonExclusive();
            String query = "SELECT "+Constants.config.GMV_ID+" FROM" +
                    " "+ Constants.config.TABLE_GMV+"  ORDER BY "+Constants.config.GMVID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    last_id = cursor.getInt(cursor.getColumnIndex(Constants.config.GMV_ID));
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




    //// TODO: 10/15/17  Syncing
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_GMV ;

        int status = 1;
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(GMVID))));


                params.put(GMV_DRIES_THOROUGHY,cursor.getString(cursor.getColumnIndex(GMV_DRIES_THOROUGHY)));
                params.put(GMV_RECOGNISE_NOTCRYING,cursor.getString(cursor.getColumnIndex(GMV_RECOGNISE_NOTCRYING)));
                params.put(GMV_KEEPS_WARM,cursor.getString(cursor.getColumnIndex(GMV_KEEPS_WARM)));
                params.put(GMV_STIMULATES_BREATHING,cursor.getString(cursor.getColumnIndex(GMV_STIMULATES_BREATHING)));
                params.put(GMV_FOLLOW_ROUTINE,cursor.getString(cursor.getColumnIndex(GMV_FOLLOW_ROUTINE)));
                params.put(GMV_MOVE_VENTILATION,cursor.getString(cursor.getColumnIndex(GMV_MOVE_VENTILATION)));
                params.put(GMV_VENTILATE,cursor.getString(cursor.getColumnIndex(GMV_VENTILATE)));
                params.put(GMV_RECOGNISE_NOTBREATHING,cursor.getString(cursor.getColumnIndex(GMV_RECOGNISE_NOTBREATHING)));
                params.put(GMV_RECOGNISE_BREATHINGWELL,cursor.getString(cursor.getColumnIndex(GMV_RECOGNISE_BREATHINGWELL)));
                params.put(GMV_RECOGNISE_CHESTMOVING,cursor.getString(cursor.getColumnIndex(GMV_RECOGNISE_CHESTMOVING)));
                params.put(GMV_MONITOR_WITHMOTHER,cursor.getString(cursor.getColumnIndex(GMV_MONITOR_WITHMOTHER)));
                params.put(GMV_CONTINUE_ESSENTAIL,cursor.getString(cursor.getColumnIndex(GMV_CONTINUE_ESSENTAIL)));
                params.put(GMV_TIME,cursor.getString(cursor.getColumnIndex(GMV_TIME)));
                params.put(GMV_DATE,cursor.getString(cursor.getColumnIndex(GMV_DATE)));
                params.put(GMV_IMEI,cursor.getString(cursor.getColumnIndex(GMV_IMEI)));
                params.put(GMV_TYPE, String.valueOf(cursor.getInt(cursor.getColumnIndex(GMV_TYPE))));
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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_GMV + " WHERE " + GMV_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(GMVID))));


                params.put(GMV_DRIES_THOROUGHY,cursor.getString(cursor.getColumnIndex(GMV_DRIES_THOROUGHY)));
                params.put(GMV_RECOGNISE_NOTCRYING,cursor.getString(cursor.getColumnIndex(GMV_RECOGNISE_NOTCRYING)));
                params.put(GMV_KEEPS_WARM,cursor.getString(cursor.getColumnIndex(GMV_KEEPS_WARM)));
                params.put(GMV_STIMULATES_BREATHING,cursor.getString(cursor.getColumnIndex(GMV_STIMULATES_BREATHING)));
                params.put(GMV_FOLLOW_ROUTINE,cursor.getString(cursor.getColumnIndex(GMV_FOLLOW_ROUTINE)));
                params.put(GMV_MOVE_VENTILATION,cursor.getString(cursor.getColumnIndex(GMV_MOVE_VENTILATION)));
                params.put(GMV_VENTILATE,cursor.getString(cursor.getColumnIndex(GMV_VENTILATE)));
                params.put(GMV_RECOGNISE_NOTBREATHING,cursor.getString(cursor.getColumnIndex(GMV_RECOGNISE_NOTBREATHING)));
                params.put(GMV_RECOGNISE_BREATHINGWELL,cursor.getString(cursor.getColumnIndex(GMV_RECOGNISE_BREATHINGWELL)));
                params.put(GMV_RECOGNISE_CHESTMOVING,cursor.getString(cursor.getColumnIndex(GMV_RECOGNISE_CHESTMOVING)));
                params.put(GMV_MONITOR_WITHMOTHER,cursor.getString(cursor.getColumnIndex(GMV_MONITOR_WITHMOTHER)));
                params.put(GMV_CONTINUE_ESSENTAIL,cursor.getString(cursor.getColumnIndex(GMV_CONTINUE_ESSENTAIL)));
                params.put(GMV_TIME,cursor.getString(cursor.getColumnIndex(GMV_TIME)));
                params.put(GMV_DATE,cursor.getString(cursor.getColumnIndex(GMV_DATE)));
                params.put(GMV_IMEI,cursor.getString(cursor.getColumnIndex(GMV_IMEI)));
                params.put(GMV_TYPE, String.valueOf(cursor.getInt(cursor.getColumnIndex(GMV_TYPE))));
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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_GMV+ " WHERE " + GMV_STATUS + " = '" + status + "' ";
            database = DBHelper.getHelper(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            count = cursor.getCount();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
              //  database.close();
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
            String updateQuery = "UPDATE " + Constants.config.TABLE_GMV + " SET " + GMV_STATUS + " = '" + status + "', "+GMV_ID+" = '"+prep_id+"'  where " + GMVID + "='" + id + "'  ";
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
                //db.execSQL("DELETE FROM " + Constants.config.TABLE_HEALTH+" WHERE "+HEALTH_STATUS+" = '"+status+"' ");

                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(GMV_ID,jsonObject.getLong(Constants.config.GMV_ID));
                    contentValues.put(GMV_DRIES_THOROUGHY,jsonObject.getString(Constants.config.GMV_DRIES_THOROUGHY));
                    contentValues.put(GMV_RECOGNISE_NOTCRYING,jsonObject.getString(Constants.config.GMV_RECOGNISE_NOTCRYING));
                    contentValues.put(GMV_KEEPS_WARM,jsonObject.getString(Constants.config.GMV_KEEPS_WARM));
                    contentValues.put(GMV_STIMULATES_BREATHING,jsonObject.getString(Constants.config.GMV_STIMULATES_BREATHING));
                    contentValues.put(GMV_FOLLOW_ROUTINE,jsonObject.getString(Constants.config.GMV_FOLLOW_ROUTINE));
                    contentValues.put(GMV_MOVE_VENTILATION,jsonObject.getString(Constants.config.GMV_MOVE_VENTILATION));
                    contentValues.put(GMV_VENTILATE,jsonObject.getString(Constants.config.GMV_VENTILATE));
                    contentValues.put(GMV_RECOGNISE_NOTBREATHING,jsonObject.getString(Constants.config.GMV_RECOGNISE_NOTBREATHING));
                    contentValues.put(GMV_RECOGNISE_BREATHINGWELL,jsonObject.getString(Constants.config.GMV_RECOGNISE_BREATHINGWELL));
                    contentValues.put(GMV_RECOGNISE_CHESTMOVING,jsonObject.getString(Constants.config.GMV_RECOGNISE_CHESTMOVING));
                    contentValues.put(GMV_MONITOR_WITHMOTHER,jsonObject.getString(Constants.config.GMV_MONITOR_WITHMOTHER));
                    contentValues.put(GMV_CONTINUE_ESSENTAIL,jsonObject.getString(Constants.config.GMV_CONTINUE_ESSENTAIL));
                    contentValues.put(GMV_TIME,jsonObject.getString(Constants.config.GMV_TIME));
                    contentValues.put(GMV_DATE,jsonObject.getString(Constants.config.GMV_DATE));
                    contentValues.put(GMV_IMEI,jsonObject.getString(Constants.config.GMV_IMEI));
                    contentValues.put(GMV_TYPE,jsonObject.getLong(Constants.config.GMV_TYPE));
                    contentValues.put(GMV_STATUS,jsonObject.getLong(Constants.config.GMV_STATUS));
                    contentValues.put(LOG_ID,jsonObject.getLong(Constants.config.LOG_ID));

                    db = DBHelper.getHelper(context).getReadableDB();
                    String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_GMV+ " WHERE "+GMV_IMEI+" = '"+jsonObject.getString(Constants.config.GMV_IMEI)+"'" +
                            " AND "+GMV_DATE+" = '"+jsonObject.getString(Constants.config.GMV_DATE)+"' AND  " + GMV_TIME + " = '" + jsonObject.getString(Constants.config.GMV_TIME) + "' ";
                    db = new DBHelper(context).getReadableDatabase();
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()){
                        do {
                            int stat = cursor.getInt(cursor.getColumnIndex(Constants.config.GMV_STATUS));
                            int id = cursor.getInt(cursor.getColumnIndex(Constants.config.GMVID));
                            if (stat == 0){
                                db = DBHelper.getHelper(context).getWritableDB();
                                db.update(Constants.config.TABLE_GMV,contentValues,GMVID+"="+id, null);
                            }
                        }while (cursor.moveToNext());
                    }else {
                        db = DBHelper.getHelper(context).getWritableDB();
                        db.insert(Constants.config.TABLE_GMV, null, contentValues);

                    }
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records ,"+TABLE_GMV+"Table Updated successfully!";

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
