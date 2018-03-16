package com.example.john.hbb.activities.simulation_mode;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.john.hbb.R;

import java.util.HashMap;

import com.example.john.hbb.configuration.DateTime;
import com.example.john.hbb.configuration.SessionManager;

/**
 * Created by john on 7/11/17.
 */

public class Simulation_VideoPlayer  extends AppCompatActivity implements SurfaceHolder.Callback{

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

    private AppCompatButton exit_button;



    /** Called when the activity is first created. */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_video_layout);

        count ++;
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar);

        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText(getResources().getString(R.string.app_name));
        TextView textView2 = (TextView) findViewById(R.id.toolbar_subtitle);
        ImageView imageView = (ImageView) findViewById(R.id.toolbar_image);
        exit_button = (AppCompatButton) findViewById(R.id.exit_button);

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

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitVideo();
            }
        });
        checkUserSessions();

        getWindow().setFormat(PixelFormat.UNKNOWN);

        if(file.equals("videos")){
            loopingVideos();
        }

    }
    private void loopingVideos(){
       /** String[] paths = {"android.resource://"+getPackageName()+"/"+R.raw.r_combined,
                          "android.resource://"+getPackageName()+"/"+R.raw.p_combined,
                           "android.resource://"+getPackageName()+"/"+R.raw.g_ombined
                         }; ***/
        String[] paths = {"android.resource://"+getPackageName()+"/"+R.raw.hands_washing,
                "android.resource://"+getPackageName()+"/"+R.raw.hands_washing,
                "android.resource://"+getPackageName()+"/"+R.raw.hands_washing
        };
        for(int i=0; i<3; i++){
            if(i < 2){
                //TODO: Play for 2 min..
                scheduleVideos(paths[i],i);
            }else {
                //TODO: Continue playing the third one
                continuePlaying(paths[i]);
            }
        }
    }

    private void exitVideo() {
        showDialog();
    }

    private void showDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getResources().getString(R.string.check_list));

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        final RadioGroup dry_group = (RadioGroup) alertLayout.findViewById(R.id.dry_group);




        alert.setCancelable(false);
        AlertDialog dialog = alert.create();
        dialog.show();

        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RadioButton dry_radio;

                if(dry_group.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(getApplicationContext(), "Please select all!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // get selected radio button from radioGroup
                    int selectedId = dry_group.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    dry_radio = (RadioButton)findViewById(selectedId);
                    Toast.makeText(getApplicationContext(), dry_radio.getText().toString()+" is selected", Toast.LENGTH_SHORT).show();
                    //finish();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exitVideo();
    }


    private void scheduleVideos(final String url_videoPath,final int i){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 20 seconds

                Log.e("Playing:",i+":\t"+ url_videoPath);

                //TODO: Playing the video from here

                //Displays a video file.
                final VideoView mVideoView = (VideoView)findViewById(R.id.videoview);

                if (mediaControls == null) {
                    // create an object of media controller class
                    mediaControls = new MediaController(context);
                    mediaControls.setAnchorView(mVideoView);
                }
                ////Picking now the right video....




                Uri uri = Uri.parse(url_videoPath);
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

                ///TODO: End of playing video



            }
        }, 30000);  //the time is in miliseconds


    }

    private void continuePlaying(String path){
        //Displays a video file.
        final VideoView mVideoView = (VideoView)findViewById(R.id.videoview);

        Log.e("Playing: Last, ",path);

        if (mediaControls == null) {
            // create an object of media controller class
            mediaControls = new MediaController(context);
            mediaControls.setAnchorView(mVideoView);
        }
        ////Picking now the right video....




        Uri uri = Uri.parse(path);
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

        ///TODO: End of playing video

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                exitVideo();
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
        //DBHelper dbHelper = new DBHelper(context);
        //dbHelper.insertTraining(Integer.parseInt(userID), fname + " " + lname, date, time, frequency, header, title, status);
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