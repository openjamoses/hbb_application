package com.example.john.hbb.db_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import com.example.john.hbb.core.Constants;
import com.example.john.hbb.core.DBHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import static com.example.john.hbb.core.Constants.config.CONTACT;
import static com.example.john.hbb.core.Constants.config.DISTRICT_NAME;
import static com.example.john.hbb.core.Constants.config.FACILITY_OWNER;
import static com.example.john.hbb.core.Constants.config.FIRST_NAME;
import static com.example.john.hbb.core.Constants.config.GENDER;
import static com.example.john.hbb.core.Constants.config.HEALTH_FACILITY;
import static com.example.john.hbb.core.Constants.config.HEALTH_ID;
import static com.example.john.hbb.core.Constants.config.HEALTH_NAME;
import static com.example.john.hbb.core.Constants.config.LAST_NAME;
import static com.example.john.hbb.core.Constants.config.PASSWORD;
import static com.example.john.hbb.core.Constants.config.USERID;
import static com.example.john.hbb.core.Constants.config.USERNAME;
import static com.example.john.hbb.core.Constants.config.USER_STATUS;

/**
 * Created by john on 10/20/17.
 */

public class User {
    private Context context;
    private static final String TAG = "User";
    public User(Context context){
        this.context = context;
    }

    public String save(String fname, String lname, String username, String phone, String gender, String facility,int facililty_id, String password, int status){
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;
        try{
            // database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(Constants.config.FIRST_NAME,fname);
            contentValues.put(Constants.config.LAST_NAME,lname);
            contentValues.put(Constants.config.USERNAME,username);
            contentValues.put(Constants.config.CONTACT,phone);
            contentValues.put(Constants.config.GENDER,gender);
            contentValues.put(Constants.config.HEALTH_FACILITY,facility);
            contentValues.put(Constants.config.PASSWORD,password);
            contentValues.put(HEALTH_ID,facililty_id);
            contentValues.put(Constants.config.USER_STATUS,status);
            database.insert(Constants.config.TABLE_USERS, null, contentValues);

            //database.setTransactionSuccessful();
            message = "User Details saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            database.close();
        }

        return message;
    }

    //// TODO: 10/15/17  Syncing
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_USERS ;

        int status = 1;
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(USERID))));

                params.put(FIRST_NAME,cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
                params.put(LAST_NAME,cursor.getString(cursor.getColumnIndex(LAST_NAME)));
                params.put(USERNAME,cursor.getString(cursor.getColumnIndex(USERNAME)));
                params.put(CONTACT,cursor.getString(cursor.getColumnIndex(CONTACT)));
                params.put(GENDER,cursor.getString(cursor.getColumnIndex(GENDER)));
                params.put(HEALTH_FACILITY,cursor.getString(cursor.getColumnIndex(HEALTH_FACILITY)));
                params.put(PASSWORD,cursor.getString(cursor.getColumnIndex(PASSWORD)));
                params.put(HEALTH_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(HEALTH_ID))));

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
        SQLiteDatabase database = DBHelper.getHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(USERID))));

                params.put(FIRST_NAME,cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
                params.put(LAST_NAME,cursor.getString(cursor.getColumnIndex(LAST_NAME)));
                params.put(USERNAME,cursor.getString(cursor.getColumnIndex(USERNAME)));
                params.put(CONTACT,cursor.getString(cursor.getColumnIndex(CONTACT)));
                params.put(GENDER,cursor.getString(cursor.getColumnIndex(GENDER)));
                params.put(HEALTH_FACILITY,cursor.getString(cursor.getColumnIndex(HEALTH_FACILITY)));
                params.put(PASSWORD,cursor.getString(cursor.getColumnIndex(PASSWORD)));
                params.put(HEALTH_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(HEALTH_ID))));

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
            database = DBHelper.getHelper(context).getWritableDatabase();
            String updateQuery = "UPDATE " + Constants.config.TABLE_USERS + " SET " + USER_STATUS + " = '" + status + "' where " + USERID + "='" + id + "'  ";
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

    public void insert(JSONArray response){
        new InsertBackground(context).execute(response);
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
        protected String doInBackground(JSONArray... params) {
            String message = null;
            int status = 1;
            SQLiteDatabase db = DBHelper.getHelper(context).getWritableDB();
            try{

                db.beginTransaction();

                JSONArray jsonArray = params[0];
                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(Constants.config.FIRST_NAME,jsonObject.getString(Constants.config.FIRST_NAME));
                    contentValues.put(Constants.config.LAST_NAME,jsonObject.getString(Constants.config.LAST_NAME));
                    contentValues.put(Constants.config.USERNAME,jsonObject.getString(Constants.config.USERNAME));
                    contentValues.put(Constants.config.CONTACT,jsonObject.getString(Constants.config.CONTACT));
                    contentValues.put(Constants.config.GENDER,jsonObject.getString(Constants.config.GENDER));
                    contentValues.put(Constants.config.HEALTH_FACILITY,jsonObject.getString(Constants.config.HEALTH_FACILITY));
                    contentValues.put(Constants.config.PASSWORD,jsonObject.getString(Constants.config.PASSWORD));
                    contentValues.put(HEALTH_ID,jsonObject.getLong(HEALTH_ID));
                    contentValues.put(Constants.config.USER_STATUS,1);

                    db = DBHelper.getHelper(context).getReadableDB();
                    String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_USERS+ " WHERE "+PASSWORD+" = '"+jsonObject.getString(Constants.config.PASSWORD)+"'" +
                            " AND  " + USERNAME + " = '" + jsonObject.getString(Constants.config.USERNAME) + "' ";
                    db = new DBHelper(context).getReadableDatabase();
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()){
                        do {
                            int stat = cursor.getInt(cursor.getColumnIndex(Constants.config.USER_STATUS));
                            int id = cursor.getInt(cursor.getColumnIndex(USERID));
                            if (stat == 0){
                                db = DBHelper.getHelper(context).getWritableDB();
                                db.update(Constants.config.TABLE_USERS,contentValues,USERID+"="+id, null);
                            }
                        }while (cursor.moveToNext());
                    }else {
                        db = DBHelper.getHelper(context).getWritableDB();
                        db.insert(Constants.config.TABLE_USERS, null, contentValues);

                    }
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records ,User Table Updated successfully!";

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


    public String password(String username){
        Cursor cursor = null;
        String password = "";
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDatabase();
        try{
            String query = "SELECT "+Constants.config.PASSWORD+", "+Constants.config.USER_ID+" FROM " + Constants.config.TABLE_USERS + " " +
                    " WHERE  " +
                    " " + Constants.config.USERNAME + " = '" + username + "' ORDER BY "+Constants.config.USER_ID+" DESC LIMIT 1 ";

            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do {
                    password = cursor.getString(cursor.getColumnIndex(Constants.config.PASSWORD));
                }while (cursor.moveToNext());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return password;
    }


    public Cursor userLogin(String username, String phone, String password) {
        Log.e("####","-------------------------------------------------------------------");
        Cursor res = null;
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDatabase();
        try {
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_USERS+ " WHERE " +
                    " ("+USERNAME+" = '"+username+"' OR "+CONTACT+" = '"+phone+"') AND  " + PASSWORD + " = '" + password + "' ORDER BY "+USERID+" DESC LIMIT 1 ";
            res  = db.rawQuery(selectQuery, null);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.e("####","-------------------------------------------------------------------");
        return res;
    }


    public Cursor getAll(String health) {
        //Log.e("####","-------------------------------------------------------------------");
        Cursor res = null;
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDatabase();
        try {
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_USERS+ " WHERE" +
                    " "+Constants.config.HEALTH_FACILITY+" = '"+health+"' ORDER BY "+FIRST_NAME+", "+LAST_NAME+" ";
            res  = db.rawQuery(selectQuery, null);
        }catch (Exception e){
            e.printStackTrace();
        }
       // Log.e("####","-------------------------------------------------------------------");
        return res;
    }

}

