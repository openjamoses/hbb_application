package com.example.john.hbb.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.example.john.hbb.db_operations.DBController;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.john.hbb.configuration.Constants.config.OPERATION_DISTRICT;
import static com.example.john.hbb.configuration.Constants.config.OPERATION_TRAINING;
import static com.example.john.hbb.configuration.Constants.config.OPERATION_USER;
import static com.example.john.hbb.configuration.Constants.config.URL_SYNC_DISTRICT;
import static com.example.john.hbb.configuration.Constants.config.URL_SYNC_TRAINING;
import static com.example.john.hbb.configuration.Constants.config.URL_SYNC_USER;

/**
 * Created by john on 9/6/17.
 */

public class ProcessingService extends Service {
    private Timer timer = new Timer();
    private static final String TAG = "ProcessingService";

    private Context context = this;
    private static final Uri STATUS_URI = Uri.parse("content://sms");
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG,"Service ProcessingService started successfull!");

        //// TODO: 10/15/17  clear memory usage....
        super.onCreate();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    sendRequest();
                }
            }, 0, 10000);//1 minutes

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //shutdownService();
    }

    public void  sendRequest(){
        Log.e(TAG, "1 minutes has elapsed!");
        try {

            new DBController().syncCalls(URL_SYNC_DISTRICT,OPERATION_DISTRICT,"",context);
            new DBController().syncCalls(URL_SYNC_USER,OPERATION_USER,"",context);
            new DBController().syncCalls(URL_SYNC_TRAINING,OPERATION_TRAINING,"",context);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}