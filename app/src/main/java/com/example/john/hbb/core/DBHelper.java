package com.example.john.hbb.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;

import static com.example.john.hbb.core.CreateTable.config.CREATE_GMV;
import static com.example.john.hbb.core.CreateTable.config.CREATE_GMWV;
import static com.example.john.hbb.core.CreateTable.config.CREATE_HEALTH;
import static com.example.john.hbb.core.CreateTable.config.CREATE_LOGIN;
import static com.example.john.hbb.core.CreateTable.config.CREATE_PREPARATION;
import static com.example.john.hbb.core.CreateTable.config.CREATE_QUIZE1;
import static com.example.john.hbb.core.CreateTable.config.CREATE_ROUTINE;
import static com.example.john.hbb.core.CreateTable.config.CREATE_SIMULATION;
import static com.example.john.hbb.core.CreateTable.config.CREATE_TRAINING_MODE;
import static com.example.john.hbb.core.CreateTable.config.CREATE_USER;

public class DBHelper extends SQLiteOpenHelper {
    private final Handler handler;
    // queries to create the table
    private static DBHelper instance;
    public static synchronized DBHelper getHelper(Context context)
    {
        if (instance == null)
            instance = new DBHelper(context);
        return instance;
    }
    public DBHelper(Context context) {
        super(context, Constants.config.DATABASE_NAME, null, Constants.config.DATABASE_VERSION);
        handler = new Handler(context.getMainLooper());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRAINING_MODE);
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_HEALTH);
        db.execSQL(CREATE_LOGIN);
        db.execSQL(CREATE_PREPARATION);
        db.execSQL(CREATE_ROUTINE);
        db.execSQL(CREATE_GMV);
        db.execSQL(CREATE_GMWV);
        db.execSQL(CREATE_SIMULATION);
        db.execSQL(CREATE_QUIZE1);

        Log.e("DATABASE OPERATION","Data base created / open successfully");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_TRAINING);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_HEALTH);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_LOG);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_PREPARATION);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_ROUTINE);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_GMV);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_GMWV);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_SIMULATION);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_QUIZ1);
        onCreate(db);
        Log.e("DATABASE OPERATION", "Table created / open successfully");

    }

    private void runOnUiThread(Runnable r) {
        handler.post(r);
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

    public  SQLiteDatabase getWritableDB(){
        SQLiteDatabase database = this.getWritableDatabase();
        return database;
    }
    public SQLiteDatabase getReadableDB(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database;
    }
}