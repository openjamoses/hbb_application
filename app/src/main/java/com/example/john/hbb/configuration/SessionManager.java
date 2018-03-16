package com.example.john.hbb.configuration;

/**
 * Created by john on 7/8/17.
 */

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.john.hbb.activities.home.LoginActivity;

import static com.example.john.hbb.configuration.Constants.config.KEY_CONTACT_TEMP;
import static com.example.john.hbb.configuration.Constants.config.KEY_EMAIL_TEMP;
import static com.example.john.hbb.configuration.Constants.config.KEY_FACILITY_TEMP;
import static com.example.john.hbb.configuration.Constants.config.KEY_FNAME_TEMP;
import static com.example.john.hbb.configuration.Constants.config.KEY_GENDER_TEMP;
import static com.example.john.hbb.configuration.Constants.config.KEY_LNAME_TEMP;
import static com.example.john.hbb.configuration.Constants.config.KEY_PASSWORD_TEMP;

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
    public static final String KEY_FNAME = Constants.config.FIRST_NAME;
    public static final String KEY_LNAME = Constants.config.LAST_NAME;
    public static final String KEY_CONTACT = Constants.config.CONTACT;
    public static final String KEY_USERID = Constants.config.USER_ID;
    public static final String KEY_FACILITY = Constants.config.HEALTH_FACILITY;
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = Constants.config.EMAIL;

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(int userID, String fname, String lname, String contact, String email,String facility){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing users Details  in pref
        editor.putString(KEY_USERID, String.valueOf(userID));
        editor.putString(KEY_FNAME, fname);
        editor.putString(KEY_LNAME, lname);
        editor.putString(KEY_CONTACT, contact);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_FACILITY, facility);

        // commit changes
        editor.commit();
    }

    public void createTemp(String fname, String lname, String contact,String gender, String email,String password, String facility){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, false);
        // Storing users Details  in pref
        editor.putString(KEY_FNAME_TEMP, fname);
        editor.putString(KEY_LNAME_TEMP, lname);
        editor.putString(KEY_GENDER_TEMP, gender);
        editor.putString(KEY_CONTACT_TEMP, contact);
        editor.putString(KEY_EMAIL_TEMP, email);
        editor.putString(KEY_PASSWORD_TEMP, password);
        editor.putString(KEY_FACILITY_TEMP, facility);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getTemp(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_FNAME_TEMP, pref.getString(KEY_FNAME_TEMP, null));
        user.put(KEY_LNAME_TEMP, pref.getString(KEY_LNAME_TEMP, null));
        user.put(KEY_CONTACT_TEMP, pref.getString(KEY_CONTACT_TEMP, null));
        user.put(KEY_GENDER_TEMP, pref.getString(KEY_GENDER_TEMP, null));
        user.put(KEY_EMAIL_TEMP, pref.getString(KEY_EMAIL_TEMP, null));
        user.put(KEY_PASSWORD_TEMP, pref.getString(KEY_PASSWORD_TEMP, null));
        user.put(KEY_FACILITY_TEMP, pref.getString(KEY_FACILITY_TEMP, null));

        // return user
        return user;
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
        user.put(KEY_FNAME, pref.getString(KEY_FNAME, null));
        user.put(KEY_LNAME, pref.getString(KEY_LNAME, null));
        user.put(KEY_CONTACT, pref.getString(KEY_CONTACT, null));
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_FACILITY, pref.getString(KEY_FACILITY, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
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
