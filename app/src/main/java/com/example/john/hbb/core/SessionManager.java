package com.example.john.hbb.core;

/**
 * Created by john on 7/8/17.
 */

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.example.john.hbb.activities.home.LoginActivity;
import com.example.john.hbb.db_operations.LogDetails;
import static com.example.john.hbb.core.Constants.config.CONTACT;
import static com.example.john.hbb.core.Constants.config.FIRST_NAME;
import static com.example.john.hbb.core.Constants.config.GENDER;
import static com.example.john.hbb.core.Constants.config.HEALTH_FACILITY;
import static com.example.john.hbb.core.Constants.config.HEALTH_ID;
import static com.example.john.hbb.core.Constants.config.LAST_NAME;
import static com.example.john.hbb.core.Constants.config.LOG_ID;
import static com.example.john.hbb.core.Constants.config.LOG_NAMES;
import static com.example.john.hbb.core.Constants.config.LOG_TYPE;
import static com.example.john.hbb.core.Constants.config.OPERATION_LOGOUT;
import static com.example.john.hbb.core.Constants.config.PASSWORD;
import static com.example.john.hbb.core.Constants.config.URL_QUERY;
import static com.example.john.hbb.core.Constants.config.USERNAME;
import static com.example.john.hbb.core.Constants.config.USER_ID;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "HBB_USER_Pref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // User name (make variable public to access from outside)
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(int userID, String fname, String lname, String contact, String email,String facility,String gender, String password, int fac_id){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing users Details  in pref
        editor.putString(USER_ID, String.valueOf(userID));
        editor.putString(FIRST_NAME, fname);
        editor.putString(LAST_NAME, lname);
        editor.putString(CONTACT, contact);
        editor.putString(USERNAME, email);
        editor.putString(HEALTH_FACILITY, facility);
        editor.putString(GENDER, gender);
        editor.putString(PASSWORD, password);
        editor.putString(HEALTH_ID, String.valueOf(fac_id));
        // commit changes
        editor.commit();
    }

    public void createLog(int log_id, String log_name, int type){
        // Storing login value as TRUE
         // Storing users Details  in pref
        editor.putString(LOG_ID, String.valueOf(log_id));
        editor.putString(LOG_NAMES, log_name);
        editor.putString(LOG_TYPE, String.valueOf(type));

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(FIRST_NAME, pref.getString(FIRST_NAME, null));
        user.put(LAST_NAME, pref.getString(LAST_NAME, null));
        user.put(CONTACT, pref.getString(CONTACT, null));
        user.put(USER_ID, pref.getString(USER_ID, null));
        user.put(GENDER, pref.getString(GENDER, null));
        user.put(PASSWORD, pref.getString(PASSWORD, null));
        user.put(HEALTH_ID, pref.getString(HEALTH_ID, null));
        user.put(USERNAME, pref.getString(USERNAME, null));
        user.put(HEALTH_FACILITY, pref.getString(HEALTH_FACILITY, null));
        // return user
        return user;
    }

    public HashMap<String, String> getLog(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(LOG_ID, pref.getString(LOG_ID, null));
        user.put(LOG_NAMES, pref.getString(LOG_NAMES, null));
        user.put(LOG_TYPE, pref.getString(LOG_TYPE, null));
        // return user
        return user;
    }
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        try {
            int login_id = new LogSession(_context).getID();
            if (login_id != 0) {
                String updateQery = "UPDATE log_tb SET logout_time = '" + DateTime.getCurrentTime() + "' WHERE log_id = '" + login_id + "'";
                new LogDetails(_context).logout(_context, updateQery, URL_QUERY, login_id, DateTime.getCurrentTime(), OPERATION_LOGOUT);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        editor.clear();
        editor.commit();


        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
