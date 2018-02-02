package com.example.john.hbb.configuration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;

import com.example.john.hbb.LoginActivity;
import com.example.john.hbb.encryptions.Encrypt_Decrypt;

import static com.example.john.hbb.configuration.Constants.config.SECRET_KEY;
import static com.example.john.hbb.configuration.CreateTable.config.CREATE_DISTRICT;
import static com.example.john.hbb.configuration.CreateTable.config.CREATE_TRAINING_MODE;
import static com.example.john.hbb.configuration.CreateTable.config.CREATE_USER;


public class DBHelper extends SQLiteOpenHelper {
    private final Handler handler;
    // queries to create the table
    public DBHelper(Context context) {
        super(context, Constants.config.DATABASE_NAME, null, Constants.config.DATABASE_VERSION);
        handler = new Handler(context.getMainLooper());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRAINING_MODE);
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_DISTRICT);
        Log.e("DATABASE OPERATION","Data base created / open successfully");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_TRAINING_MODE);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_DISTRICT);
        onCreate(db);
        Log.e("DATABASE OPERATION", "Table created / open successfully");

    }

    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }



    public String insertTraining(int userID, String name, String date, String time, int frequency, String key_skills,String key_subSkill, int sync_status){
        SQLiteDatabase database = this.getWritableDatabase();
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

    public String password(String email){
        Cursor cursor = null;
        String password = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String query = "SELECT "+Constants.config.PASSWORD+", "+Constants.config.USER_ID+" FROM " + Constants.config.TABLE_USERS + " " +
                    " WHERE  " +
                    " " + Constants.config.EMAIL + " = '" + email + "' ORDER BY "+Constants.config.USER_ID+" DESC LIMIT 1 ";

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

    public Cursor userLogin(String email, String password) {
        Log.e("####","-------------------------------------------------------------------");
        String password2 = password(email);


        String d_password = "";
        Log.e("DBHELPER","ENCRYPTED PASSWORD: "+password2);
        try{
            //d_password = Encrypt_Decrypt.decipher(SECRET_KEY,password2);
        }catch (Exception e){
            e.printStackTrace();
        }

        Cursor res = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            if (password2.equals(password)) {
                String query = "SELECT * FROM " + Constants.config.TABLE_USERS + " " +
                        " WHERE  " +
                        " " + Constants.config.EMAIL + " = '" + email + "' ";
                Log.e("Query: ", query);
                res = db.rawQuery(query, null);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        Log.e("####","-------------------------------------------------------------------");
        return res;
    }

    public Cursor fetchUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int status = 0;
        int new_status = 1;
        try {
            db.beginTransaction();
            String query = "SELECT * FROM  "+ Constants.config.TABLE_USERS +" WHERE " +
                    " "+ Constants.config.USER_STATUS+" = "+status;
            cursor = db.rawQuery(query, null);
            ContentValues values = new ContentValues();
            values.put(Constants.config.USER_STATUS,new_status);
            db = this.getWritableDatabase();
            if(cursor.moveToFirst()){
                do{
                    int id = cursor.getInt(cursor.getColumnIndex(Constants.config.USER_ID));
                    db.update(Constants.config.TABLE_USERS,values, Constants.config.USER_ID+" = "+id,null);
                    Log.e("Updated USER ID: ",id+"");
                }while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            //cursor.close();
        }
        return cursor;
    }

    public Cursor fetchTrainingMode(){
        SQLiteDatabase db = this.getReadableDatabase();
        int status = 0;
        int new_status = 1;
        Cursor cursor = null;
        try {
            db.beginTransaction();
            String query = "SELECT * FROM  "+ Constants.config.TABLE_TRAINING_MODE+" WHERE " +
                    " "+ Constants.config.TRAINING_SYNC_STATUS+" = "+status;
            cursor = db.rawQuery(query, null);
            ContentValues values = new ContentValues();
            values.put(Constants.config.TRAINING_SYNC_STATUS,new_status);
            db = this.getWritableDatabase();
            if(cursor.moveToFirst()){
                do{
                    int id = cursor.getInt(cursor.getColumnIndex(Constants.config.TRAINING_ID));
                    db.update(Constants.config.TABLE_TRAINING_MODE,values, Constants.config.TRAINING_ID+" = "+id,null);
                    Log.e("Updated TRAINING ID: ",id+"");
                }while (cursor.moveToNext());
            }

            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            //cursor.close();
        }
        return cursor;
    }

    public  SQLiteDatabase getWritableDB(){
        SQLiteDatabase database = this.getWritableDatabase();
        return database;
    }
    public SQLiteDatabase getReadableDB(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database;
    }

}