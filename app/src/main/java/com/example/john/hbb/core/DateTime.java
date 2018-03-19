package com.example.john.hbb.core;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by john on 7/9/17.
 */

public class DateTime {
    public static String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }
    public  static String getCurrentTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String strTime = sdf.format(c.getTime());
        return strTime;
    }
}
