package com.example.john.hbb.configuration;
import android.content.Context;
import java.util.HashMap;
/**
 * Created by john on 3/16/18.
 */

public class UsersSession {
    private  Context context;
    public  String fname,lname,contact, email, health;
    public UsersSession(Context context){
        this.context = context;
        SessionManager session = new SessionManager(context);
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        fname = user.get(SessionManager.KEY_FNAME);
        lname = user.get(SessionManager.KEY_LNAME);
        contact = user.get(SessionManager.KEY_CONTACT);
        email = user.get(SessionManager.KEY_EMAIL);
        health = user.get(SessionManager.KEY_FACILITY);
    }
    public  String getFname(){
        return fname;
    }
    public  String getLname(){
        return lname;
    }
    public  String getContact(){
        return contact;
    }
    public  String getEmail(){
        return email;
    }
    public  String getHealth(){
        return health;
    }

}
