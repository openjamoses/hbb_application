package com.example.john.hbb.core;

import android.content.Context;

import java.util.HashMap;
import static com.example.john.hbb.core.Constants.config.LOG_ID;
import static com.example.john.hbb.core.Constants.config.LOG_NAMES;

/**
 * Created by john on 3/24/18.
 */

public class LogSession {
    private Context context;
    public  String names;
    int log_id;
    public LogSession(Context context){
        this.context = context;
        SessionManager session = new SessionManager(context);
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getLog();
        names = user.get(LOG_NAMES);
        log_id = Integer.parseInt(user.get(LOG_ID));
    }
    public  String getNames(){
        return names;
    }
    public  int getID(){
        return log_id;
    }
}
