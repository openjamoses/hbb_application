package com.example.john.hbb.activities.simulation_mode.rator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.john.hbb.activities.home.LoginActivity;
import com.example.john.hbb.R;
import com.example.john.hbb.activities.simulation_mode.Simulation_VideoPlayer;
import com.example.john.hbb.activities.simulation_mode.alone.Images_Simulations;
import com.example.john.hbb.core.Server_Service;
import com.example.john.hbb.core.SessionManager;
import com.example.john.hbb.core.UsersSession;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

/**
 * Created by john on 12/5/17.
 */

public class Rator_Home extends AppCompatActivity {

    ExpandableRelativeLayout expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4, expandableLayout5;

    Toolbar toolbar;
    private Context context = this;
    private TextView baby_being_born, crying_and_breathing, breathing_baby,
            drying_thouroughly, clamping_and_cutting_card, r_combine_video,
            sunctioning, stimulation, without_combine_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_expandable);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        //toolbar.setTitle(getResources().getString(R.string.app_name));
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        TextView textView2 = (TextView) findViewById(R.id.toolbar_subtitle);
        checkUserSessions();
        /// Setting the app icon
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        /// Routine care
        baby_being_born = (TextView) findViewById(R.id.baby_being_born);
        crying_and_breathing = (TextView) findViewById(R.id.crying_and_breathing);
        breathing_baby = (TextView) findViewById(R.id.breathing_baby);


        baby_being_born.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Simulation_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.routine_care));
                intent.putExtra("title",getResources().getString(R.string.baby_being_born));
                intent.putExtra("file","videos");
                startActivity(intent);
            }
        });

    }

    private void checkUserSessions() {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        TextView textView2 = (TextView) findViewById(R.id.toolbar_subtitle);
        textView.setText( new UsersSession(context).health );
        textView2.setText("("+new UsersSession(context).fname + " " + new UsersSession(context).lname+")");

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

    public void expandableButton0(View view){
        Intent intent = new Intent(getApplicationContext(), Images_Simulations.class);
        String[] title = {getResources().getString(R.string.baby_being_born),
                getResources().getString(R.string.crying_and_breathing),
                getResources().getString(R.string.breathing_baby)};
        intent.putExtra("mode","play");
        intent.putExtra("header",getResources().getString(R.string.preparation_for_birth));
        intent.putExtra("title",title);
        intent.putExtra("type","rator");
        intent.putExtra("file","images0");
        startActivity(intent);
    }

    public void expandableButton1(View view) {
        //expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);
        //expandableLayout1.toggle(); // toggle expand and collapse
        Intent intent = new Intent(getApplicationContext(), Images_Simulations.class);
        String[] title = {getResources().getString(R.string.baby_being_born),
                getResources().getString(R.string.crying_and_breathing),
                getResources().getString(R.string.breathing_baby)};
        intent.putExtra("mode","play");
        intent.putExtra("header",getResources().getString(R.string.routine_care));
        //intent.putExtra("title1",getResources().getString(R.string.baby_being_born));
        //intent.putExtra("title2",getResources().getString(R.string.crying_and_breathing));
        //intent.putExtra("title3",getResources().getString(R.string.breathing_baby));
        intent.putExtra("title",title);
        intent.putExtra("type","rator");
        intent.putExtra("file","images1");
        startActivity(intent);
    }

    public void expandableButton2(View view) {
        //expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
        //expandableLayout2.toggle(); // toggle expand and collapse
        String[] title = {getResources().getString(R.string.baby_is_born),
                getResources().getString(R.string.baby_not_crying),
                getResources().getString(R.string.baby_not_breathing),
                getResources().getString(R.string.breathing_baby)};


        Intent intent = new Intent(getApplicationContext(), Images_Simulations.class);
        intent.putExtra("mode","play");
        intent.putExtra("header",getResources().getString(R.string.golden_minutes_without_ventilation));
        intent.putExtra("title",title);
        intent.putExtra("type","rator");
        intent.putExtra("file","images2");
        startActivity(intent);
    }

    public void expandableButton3(View view) {
        // expandableLayout3 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout3);
        // expandableLayout3.toggle(); // toggle expand and collapse

        String[] title = {getResources().getString(R.string.baby_is_born),
                getResources().getString(R.string.baby_not_crying),
                getResources().getString(R.string.baby_not_breathing),
        };

        Intent intent = new Intent(getApplicationContext(), Images_Simulations.class);
        intent.putExtra("mode","play");
        intent.putExtra("header",getResources().getString(R.string.golden_minutes_with_ventilation));
        //intent.putExtra("title1",getResources().getString(R.string.baby_is_born));
        //intent.putExtra("title2",getResources().getString(R.string.baby_not_crying));
        //intent.putExtra("title3",getResources().getString(R.string.baby_not_breathing));
        intent.putExtra("title",title);
        intent.putExtra("type","rator");
        intent.putExtra("file","images3");
        startActivity(intent);
    }

    public void expandableButton4(View view) {
        // expandableLayout3 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout3);
        // expandableLayout3.toggle(); // toggle expand and collapse

        String[] title = {getResources().getString(R.string.baby_is_born),
                getResources().getString(R.string.baby_not_crying),
                getResources().getString(R.string.baby_not_breathing),
        };

        Intent intent = new Intent(getApplicationContext(), Images_Simulations.class);
        intent.putExtra("mode","play");
        intent.putExtra("header",getResources().getString(R.string.continued_ventilation_with_normal_or_slow_heart_rate));
        //intent.putExtra("title1",getResources().getString(R.string.baby_is_born));
        //intent.putExtra("title2",getResources().getString(R.string.baby_not_crying));
        //intent.putExtra("title3",getResources().getString(R.string.baby_not_breathing));
        intent.putExtra("title",title);
        intent.putExtra("type","rator");
        intent.putExtra("file","images4");
        startActivity(intent);
    }
}