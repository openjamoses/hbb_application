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
import static com.example.john.hbb.core.Constants.config.PREP_AREA_VENTILATION;
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
import static com.example.john.hbb.core.Constants.config.URL_SAVE_LOG;
import static com.example.john.hbb.core.Constants.config.URL_SAVE_PREPARATION;
import static com.example.john.hbb.core.Constants.config.USER_ID;

/**
 * Created by john on 3/20/18.
 */

public class Preparation {
    private Context context;
    private static final String TAG = "User";
    public Preparation(Context context){
        this.context = context;
    }

    public String save(int preperation_id, String date, String time, String imei, int type, String prep_identify_helper, String prep_area_delivery,String prepare_an_area_ventilation,
                       String prep_washes_hands, String prep_assembled, String prep_test_ventilation, String prep_uterotonic, int prep_status, int log_id){
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;
        try{
            // database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PREPARATION_ID,preperation_id);
            contentValues.put(PREP_DATE,date);
            contentValues.put(PREP_TIME,time);
            contentValues.put(PREP_IMEI,imei);
            contentValues.put(PREP_TYPE,type);
            contentValues.put(PREP_IDENTIFY_HELPER,prep_identify_helper);
            contentValues.put(PREP_AREA_DELIVERY,prep_area_delivery);
            contentValues.put(PREP_AREA_VENTILATION,prepare_an_area_ventilation);
            contentValues.put(PREP_WASHES_HANDS,prep_washes_hands);
            contentValues.put(PREP_ASSEMBLED,prep_assembled);
            contentValues.put(PREP_TEST_VENTILATION,prep_test_ventilation);
            contentValues.put(PREP_UTEROTONIC,prep_uterotonic);
            contentValues.put(PREP_STATUS,prep_status);
            contentValues.put(LOG_ID,log_id);

            database.insert(Constants.config.TABLE_PREPARATION, null, contentValues);
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

    public void send(final String date, final String time, final String imei, final int type, final String prep_identify_helper, final String prep_area_delivery, final String prepare_an_area_ventilation,
                     final String prep_washes_hands, final String prep_assembled, final String prep_test_ventilation, final String prep_uterotonic, final int log_id){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_PREPARATION,
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
                            String message = save(id,date,time,imei,type,prep_identify_helper,prep_area_delivery,prepare_an_area_ventilation,prep_washes_hands,prep_assembled,
                                    prep_test_ventilation,prep_uterotonic,status,log_id);
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
                int status = 1;
                Map<String, String> params = new Hashtable<String, String>();
                params.put(PREP_DATE,date);
                params.put(PREP_TIME,time);
                params.put(PREP_IMEI,imei);
                params.put(PREP_TYPE, String.valueOf(type));
                params.put(LOG_ID, String.valueOf(log_id));
                params.put(PREP_IDENTIFY_HELPER,prep_identify_helper);
                params.put(PREP_AREA_DELIVERY,prep_area_delivery);
                params.put(PREP_AREA_VENTILATION,prepare_an_area_ventilation);
                params.put(PREP_WASHES_HANDS,prep_washes_hands);
                params.put(PREP_ASSEMBLED,prep_assembled);
                params.put(PREP_TEST_VENTILATION,prep_test_ventilation);
                params.put(PREP_UTEROTONIC,prep_uterotonic);
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


    //// TODO: 10/15/17  Syncing
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_PREPARATION ;

        int status = 1;
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(PREPARATIONID))));


                params.put(PREP_DATE,cursor.getString(cursor.getColumnIndex(PREP_DATE)));
                params.put(PREP_TIME,cursor.getString(cursor.getColumnIndex(PREP_TIME)));
                params.put(PREP_IMEI,cursor.getString(cursor.getColumnIndex(PREP_IMEI)));
                params.put(PREP_TYPE,cursor.getString(cursor.getColumnIndex(PREP_TYPE)));
                params.put(PREP_IDENTIFY_HELPER,cursor.getString(cursor.getColumnIndex(PREP_IDENTIFY_HELPER)));
                params.put(PREP_AREA_DELIVERY,cursor.getString(cursor.getColumnIndex(PREP_AREA_DELIVERY)));
                params.put(PREP_AREA_VENTILATION,cursor.getString(cursor.getColumnIndex(PREP_AREA_VENTILATION)));
                params.put(PREP_WASHES_HANDS,cursor.getString(cursor.getColumnIndex(PREP_WASHES_HANDS)));
                params.put(PREP_ASSEMBLED,cursor.getString(cursor.getColumnIndex(PREP_ASSEMBLED)));
                params.put(PREP_TEST_VENTILATION,cursor.getString(cursor.getColumnIndex(PREP_TEST_VENTILATION)));
                params.put(PREP_UTEROTONIC,cursor.getString(cursor.getColumnIndex(PREP_UTEROTONIC)));
                params.put(PREP_STATUS,cursor.getString(cursor.getColumnIndex(PREP_STATUS)));
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
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_PREPARATION + " WHERE " + PREP_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(PREPARATIONID))));


                params.put(PREP_DATE,cursor.getString(cursor.getColumnIndex(PREP_DATE)));
                params.put(PREP_TIME,cursor.getString(cursor.getColumnIndex(PREP_TIME)));
                params.put(PREP_IMEI,cursor.getString(cursor.getColumnIndex(PREP_IMEI)));
                params.put(PREP_TYPE,cursor.getString(cursor.getColumnIndex(PREP_TYPE)));
                params.put(PREP_IDENTIFY_HELPER,cursor.getString(cursor.getColumnIndex(PREP_IDENTIFY_HELPER)));
                params.put(PREP_AREA_DELIVERY,cursor.getString(cursor.getColumnIndex(PREP_AREA_DELIVERY)));
                params.put(PREP_AREA_VENTILATION,cursor.getString(cursor.getColumnIndex(PREP_AREA_VENTILATION)));
                params.put(PREP_WASHES_HANDS,cursor.getString(cursor.getColumnIndex(PREP_WASHES_HANDS)));
                params.put(PREP_ASSEMBLED,cursor.getString(cursor.getColumnIndex(PREP_ASSEMBLED)));
                params.put(PREP_TEST_VENTILATION,cursor.getString(cursor.getColumnIndex(PREP_TEST_VENTILATION)));
                params.put(PREP_UTEROTONIC,cursor.getString(cursor.getColumnIndex(PREP_UTEROTONIC)));
                params.put(PREP_STATUS,cursor.getString(cursor.getColumnIndex(PREP_STATUS)));
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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_PREPARATION+ " WHERE " + PREP_STATUS + " = '" + status + "' ";
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
            String updateQuery = "UPDATE " + Constants.config.TABLE_PREPARATION + " SET " + PREP_STATUS + " = '" + status + "', "+PREPARATION_ID+" = '"+prep_id+"'  where " + PREPARATIONID + "='" + id + "'  ";
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
            String query = "SELECT "+Constants.config.PREPARATION_ID+" FROM" +
                    " "+ Constants.config.TABLE_PREPARATION+"  ORDER BY "+Constants.config.PREPARATIONID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    last_id = cursor.getInt(cursor.getColumnIndex(Constants.config.PREPARATION_ID));
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
                db.beginTransaction();
                //String get_json = get
                //JSONArray jsonArray = new JSONArray(results);
                JSONArray jsonArray = jsonArrays[0];
                //db.execSQL("DELETE FROM " + Constants.config.TABLE_HEALTH+" WHERE "+HEALTH_STATUS+" = '"+status+"' ");

                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(PREPARATION_ID,jsonObject.getLong(Constants.config.PREPARATION_ID));
                    contentValues.put(PREP_DATE,jsonObject.getString(Constants.config.PREP_DATE));
                    contentValues.put(PREP_TIME,jsonObject.getString(Constants.config.PREP_TIME));
                    contentValues.put(PREP_IMEI,jsonObject.getString(Constants.config.PREP_IMEI));
                    contentValues.put(PREP_TYPE,jsonObject.getLong(Constants.config.PREP_TYPE));
                    contentValues.put(PREP_IDENTIFY_HELPER,jsonObject.getString(Constants.config.PREP_IDENTIFY_HELPER));
                    contentValues.put(PREP_AREA_DELIVERY,jsonObject.getString(Constants.config.PREP_AREA_DELIVERY));
                    contentValues.put(PREP_WASHES_HANDS,jsonObject.getString(Constants.config.PREP_WASHES_HANDS));
                    contentValues.put(PREP_AREA_VENTILATION,jsonObject.getString(Constants.config.PREP_AREA_VENTILATION));
                    contentValues.put(PREP_ASSEMBLED,jsonObject.getString(Constants.config.PREP_ASSEMBLED));
                    contentValues.put(PREP_TEST_VENTILATION,jsonObject.getString(Constants.config.PREP_TEST_VENTILATION));
                    contentValues.put(PREP_UTEROTONIC,jsonObject.getString(Constants.config.PREP_UTEROTONIC));
                    contentValues.put(LOG_ID,jsonObject.getLong(Constants.config.LOG_ID));
                    contentValues.put(PREP_STATUS,status);


                    db = DBHelper.getHelper(context).getReadableDB();
                    String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_PREPARATION+ " WHERE "+PREP_IMEI+" = '"+jsonObject.getString(Constants.config.PREP_IMEI)+"'" +
                            " AND "+PREP_DATE+" = '"+jsonObject.getString(Constants.config.PREP_DATE)+"' AND  " + PREP_TIME + " = '" + jsonObject.getString(Constants.config.PREP_TIME) + "' ";
                    db = new DBHelper(context).getReadableDatabase();
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()){
                        do {
                            int stat = cursor.getInt(cursor.getColumnIndex(Constants.config.PREP_STATUS));
                            int id = cursor.getInt(cursor.getColumnIndex(Constants.config.PREPARATIONID));
                            if (stat == 0){
                                db = DBHelper.getHelper(context).getWritableDB();
                                db.update(Constants.config.TABLE_PREPARATION,contentValues,PREPARATIONID+"="+id, null);
                            }
                        }while (cursor.moveToNext());
                    }else {
                        db = DBHelper.getHelper(context).getWritableDB();
                        db.insert(Constants.config.TABLE_PREPARATION, null, contentValues);

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
