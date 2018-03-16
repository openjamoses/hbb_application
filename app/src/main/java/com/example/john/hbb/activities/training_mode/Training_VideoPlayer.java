package com.example.john.hbb.activities.training_mode;

/**
 * Created by john on 7/7/17.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.john.hbb.R;

import java.util.HashMap;

import com.example.john.hbb.configuration.DBHelper;
import com.example.john.hbb.configuration.DateTime;
import com.example.john.hbb.configuration.SessionManager;

//Implement SurfaceHolder interface to Play video
//Implement this interface to receive information about changes to the surface
public class Training_VideoPlayer extends AppCompatActivity implements SurfaceHolder.Callback{

    MediaPlayer mediaPlayer;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean pausing = false;;

    MediaController mediaControls;
    private Context context = this;
    private int position = 0;
    private String mode,file,title,header;
    private String uriPath;
    private int count = 0;


    /** Called when the activity is first created. */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);

        count ++;
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar);

        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText(getResources().getString(R.string.app_name));
        TextView textView2 = (TextView) findViewById(R.id.toolbar_subtitle);
        ImageView imageView = (ImageView) findViewById(R.id.toolbar_image);
        imageView.setVisibility(View.GONE);
        ///// Getting the intent values from the TrainingHomeActivity activity....
        mode = getIntent().getStringExtra("mode");
        file = getIntent().getStringExtra("file");
        title = getIntent().getStringExtra("title");
        header = getIntent().getStringExtra("header");
        //// setting up the toolbar....
        textView.setText(header);
        textView2.setText(title);
        textView2.setSelected(true);
        textView2.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView2.setSingleLine(true);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setSubtitle(title);

        }

        checkUserSessions();

        getWindow().setFormat(PixelFormat.UNKNOWN);
        //Displays a video file.
        final VideoView mVideoView = (VideoView)findViewById(R.id.videoview);

        if (mediaControls == null) {
            // create an object of media controller class
            mediaControls = new MediaController(context);
            mediaControls.setAnchorView(mVideoView);
        }
        ////Picking now the right video....

        /**
        if(file.equals("videos")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.hands_washing;/// hand washing video
        }else if(file.equals("video2")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.testing_equipment;/// Prepare quipment video
        }else if(file.equals("video3")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.p_combined;/// Combined video for preperation...
        }else if(file.equals("video4")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.drying_throughly;/// Combined video for preperation...
        }else if(file.equals("video5")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.clamping_cord;/// Combined video for preperation...
        }else if(file.equals("video6")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.r_combined;/// Combined video for preperation...
        }else if(file.equals("video7")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.sunctioning;/// Combined video for preperation...
        }else if(file.equals("video8")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.stimulation;/// Combined video for preperation...
        }else if(file.equals("video9")) {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.g_ombined; //// neither of those... incase any!
        }else {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.g_ombined; //// neither of those... incase any!
        }

         ***/

        if(file.equals("videos")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.hands_washing;/// hand washing video
        }else if(file.equals("video2")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.hands_washing;/// Prepare quipment video
        }else if(file.equals("video3")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.hands_washing;/// Combined video for preperation...
        }else if(file.equals("video4")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.hands_washing;/// Combined video for preperation...
        }else if(file.equals("video5")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.hands_washing;/// Combined video for preperation...
        }else if(file.equals("video6")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.hands_washing;/// Combined video for preperation...
        }else if(file.equals("video7")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.hands_washing;/// Combined video for preperation...
        }else if(file.equals("video8")){
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.hands_washing;/// Combined video for preperation...
        }else if(file.equals("video9")) {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.hands_washing; //// neither of those... incase any!
        }else {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.hands_washing; //// neither of those... incase any!
        }

        Uri uri = Uri.parse(uriPath);
        mVideoView.setVideoURI(uri);
        mVideoView.setMediaController(mediaControls);
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public MediaPlayer mediaPlayer;

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // if we have a position on savedInstanceState, the video
                // playback should start from here
                mVideoView.seekTo(position);

                System.out.println("vidio is ready for playing");

                if (position == 0)
                {
                    //this.mediaPlayer = mediaPlayer;
                    if(mode.equals("play")) {
                        mVideoView.start();
                    }
                } else
                {
                    // if we come from a resumed activity, video playback will
                    // be paused
                    mVideoView.pause();
                }
            }
        });

        // implement on completion listener on video view
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(), "Thank You...!!!", Toast.LENGTH_LONG).show(); // display a toast when an video is completed
            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(getApplicationContext(), "Oops An Error Occur While Playing Video...!!!", Toast.LENGTH_LONG).show(); // display a toast when an error is occured while playing an video
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                Intent intent = new Intent(getApplicationContext(), TrainingHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void checkUserSessions() {
        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        // Session class instance
        SessionManager session = new SessionManager(getApplicationContext());
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        String userID = user.get(SessionManager.KEY_USERID);
        String fname = user.get(SessionManager.KEY_FNAME);
        String lname = user.get(SessionManager.KEY_LNAME);
        String contact = user.get(SessionManager.KEY_CONTACT);
        String email = user.get(SessionManager.KEY_EMAIL);
        String health = user.get(SessionManager.KEY_FACILITY);

        String date = new DateTime().getCurrentDate();
        String time = new DateTime().getCurrentTime();
        int frequency = 1;
        int status = 0;

        //if(count == 1) {
            DBHelper dbHelper = new DBHelper(context);
            dbHelper.insertTraining(Integer.parseInt(userID), fname + " " + lname, date, time, frequency, header, title, status);
       // }

    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }
}