package com.example.john.hbb.training_mode;

/**
 * Created by john on 7/7/17.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.john.hbb.LoginActivity;
import com.example.john.hbb.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import java.util.HashMap;

import com.example.john.hbb.configuration.Server_Service;
import com.example.john.hbb.configuration.SessionManager;

public class Expandable_Activity extends AppCompatActivity {

    ExpandableRelativeLayout expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4, expandableLayout5;

    Toolbar toolbar;
    private TextView hand_washing,testing_equipments,p_combine_video,
                     drying_thouroughly,clamping_and_cutting_card,r_combine_video,
                     sunctioning,stimulation,without_combine_video
                     ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_expandable);

        toolbar = (Toolbar)findViewById(R.id.toolBar);
        //toolbar.setTitle(getResources().getString(R.string.app_name));
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        TextView textView2 = (TextView) findViewById(R.id.toolbar_subtitle);
        checkUserSessions();
        /// Setting the app icon
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        /// Preperation for birth
        hand_washing = (TextView) findViewById(R.id.hand_washing);
        testing_equipments = (TextView) findViewById(R.id.testing_equipments);
        p_combine_video = (TextView) findViewById(R.id.p_combine_video);


        /// Routine cares
        drying_thouroughly = (TextView) findViewById(R.id.drying_thouroughly);
        clamping_and_cutting_card = (TextView) findViewById(R.id.clamping_and_cutting_card);
        r_combine_video = (TextView) findViewById(R.id.r_combine_video);
        /// Golden minute without ventilation
        sunctioning = (TextView) findViewById(R.id.sunctioning);
        stimulation = (TextView) findViewById(R.id.stimulation);
        without_combine_video = (TextView) findViewById(R.id.without_combine_video);

        /// Preperation for birth
        hand_washing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Training_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.preperation_for_birth));
                intent.putExtra("title",getResources().getString(R.string.hand_washing));
                intent.putExtra("file","videos");
                startActivity(intent);
            }
        });
        testing_equipments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Training_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.preperation_for_birth));
                intent.putExtra("title",getResources().getString(R.string.testing_equipments));
                intent.putExtra("file","video2");
                startActivity(intent);
            }
        });
        p_combine_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Training_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.preperation_for_birth));
                intent.putExtra("title",getResources().getString(R.string.combine_video));
                intent.putExtra("file","video3");
                startActivity(intent);
            }
        });

        /// Routine cares
        drying_thouroughly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Training_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.routine_care));
                intent.putExtra("title",getResources().getString(R.string.drying_thouroughly));
                intent.putExtra("file","video4");
                startActivity(intent);
            }
        });
        clamping_and_cutting_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Training_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.routine_care));
                intent.putExtra("title",getResources().getString(R.string.clamping_and_cutting_card));
                intent.putExtra("file","video5");
                startActivity(intent);
            }
        });
        r_combine_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Training_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.routine_care));
                intent.putExtra("title",getResources().getString(R.string.combine_video));
                intent.putExtra("file","video6");
                startActivity(intent);
            }
        });

        /// Golden minute without ventilation

        sunctioning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Training_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.golden_minutes_without_ventilation));
                intent.putExtra("title",getResources().getString(R.string.sunctioning));
                intent.putExtra("file","video7");
                startActivity(intent);
            }
        });
        stimulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Training_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.golden_minutes_without_ventilation));
                intent.putExtra("title",getResources().getString(R.string.stimulation));
                intent.putExtra("file","video8");
                startActivity(intent);
            }
        });
        without_combine_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Training_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.golden_minutes_without_ventilation));
                intent.putExtra("title",getResources().getString(R.string.combine_video));
                intent.putExtra("file","video9");
                startActivity(intent);
            }
        });
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

        String fname = user.get(SessionManager.KEY_FNAME);
        String lname = user.get(SessionManager.KEY_LNAME);
        String contact = user.get(SessionManager.KEY_CONTACT);
        String email = user.get(SessionManager.KEY_EMAIL);
        String health = user.get(SessionManager.KEY_FACILITY);
        //toolbar.setTitle();

        
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        TextView textView2 = (TextView) findViewById(R.id.toolbar_subtitle);
        textView.setText( health );
        textView2.setText("("+fname + " " + lname+")");

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;
            case R.id.action_settings:
                // todo: Logout Session from here
                SessionManager session = new SessionManager(getApplicationContext());
                session.logoutUser();
                // todo: Should as well stop the running service
                if (stopService(new Intent(getBaseContext(), Server_Service.class)) == false) {
                    stopService(new Intent(getBaseContext(), Server_Service.class));
                }
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void expandableButton1(View view) {
        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);
        expandableLayout1.toggle(); // toggle expand and collapse
    }

    public void expandableButton2(View view) {
        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
        expandableLayout2.toggle(); // toggle expand and collapse
    }

    public void expandableButton3(View view) {
        expandableLayout3 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout3);
        expandableLayout3.toggle(); // toggle expand and collapse
    }

    public void expandableButton4(View view) {
        expandableLayout4 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout4);
        expandableLayout4.toggle(); // toggle expand and collapse
    }
    public void expandableButton5(View view) {
        expandableLayout5 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout5);
        expandableLayout5.toggle(); // toggle expand and collapse
    }
}