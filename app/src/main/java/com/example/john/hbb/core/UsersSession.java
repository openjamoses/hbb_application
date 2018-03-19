package com.example.john.hbb.core;
import android.content.Context;
import java.util.HashMap;

import static com.example.john.hbb.core.Constants.config.CONTACT;
import static com.example.john.hbb.core.Constants.config.FIRST_NAME;
import static com.example.john.hbb.core.Constants.config.HEALTH_FACILITY;
import static com.example.john.hbb.core.Constants.config.HEALTH_ID;
import static com.example.john.hbb.core.Constants.config.LAST_NAME;
import static com.example.john.hbb.core.Constants.config.USERNAME;
import static com.example.john.hbb.core.Constants.config.USER_ID;

/**
 * Created by john on 3/16/18.
 */

public class UsersSession {
    private  Context context;
    public  String fname,lname,contact, username, health, gender,password;
    int id,user_id;
    public UsersSession(Context context){
        this.context = context;
        SessionManager session = new SessionManager(context);
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        fname = user.get(FIRST_NAME);
        lname = user.get(LAST_NAME);
        contact = user.get(CONTACT);
        username = user.get(USERNAME);
        health = user.get(HEALTH_FACILITY);

        gender = user.get(CONTACT);
        password = user.get(USERNAME);
        user_id = Integer.parseInt(user.get(USER_ID));
        id = Integer.parseInt(user.get(HEALTH_ID));
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
    public  String getUsername(){
        return username;
    }
    public  String getHealth(){
        return health;
    }

    public  String getGender(){
        return gender;
    }
    public  String getPassword(){
        return password;
    }
    public  int getUserID(){
        return user_id;
    }
    public  int getHealthID(){
        return id;
    }

}
