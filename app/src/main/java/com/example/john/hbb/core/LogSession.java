package com.example.john.hbb.core;

import android.content.Context;

import java.util.HashMap;
import static com.example.john.hbb.core.Constants.config.LOG_ID;
import static com.example.john.hbb.core.Constants.config.LOG_NAMES;
import static com.example.john.hbb.core.Constants.config.LOG_TYPE;

/**
 * Created by john on 3/24/18.
 */

public class LogSession {
    private Context context;
    public  String names = "";
    int log_id = 0, log_type = 0;
    public LogSession(Context context){
        this.context = context;
        SessionManager session = new SessionManager(context);
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getLog();
        try {
            if (user.get(LOG_NAMES) != null){
                names = user.get(LOG_NAMES);
            }
            if (user.get(LOG_ID) != null){
                log_id = Integer.parseInt(user.get(LOG_ID));
            }
            if (user.get(LOG_TYPE) != null){
                log_type = Integer.parseInt(user.get(LOG_TYPE));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  String getNames(){
        return names;
    }
    public  int getID(){
        return log_id;
    }
    public  int getLog_type(){
        return log_type;
    }
}
