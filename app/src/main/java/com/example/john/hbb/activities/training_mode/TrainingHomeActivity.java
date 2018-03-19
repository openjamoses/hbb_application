package com.example.john.hbb.activities.training_mode;

/**
 * Created by john on 7/7/17.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.john.hbb.activities.home.LoginActivity;
import com.example.john.hbb.R;
import com.example.john.hbb.core.UsersSession;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import java.util.HashMap;

import com.example.john.hbb.core.Server_Service;
import com.example.john.hbb.core.SessionManager;

public class TrainingHomeActivity extends AppCompatActivity {

    ExpandableRelativeLayout expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4;
    private Button expandableButton5, expandableButton6;

    Toolbar toolbar;
    private TextView hand_washing,testing_equipments,p_combine_video,
                     drying_thouroughly,clamping_and_cutting_card,r_combine_video,
                     sunctioning,stimulation,without_combine_video,bag_and_mask_ventilation_video,improving_ventilation_video
                     ;
    private Context context = this;

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


        expandableButton5 = (Button) findViewById(R.id.expandableButton5) ;
        expandableButton6 = (Button) findViewById(R.id.expandableButton6) ;
        /// Routine cares
        drying_thouroughly = (TextView) findViewById(R.id.drying_thouroughly);
        clamping_and_cutting_card = (TextView) findViewById(R.id.clamping_and_cutting_card);
        r_combine_video = (TextView) findViewById(R.id.r_combine_video);
        /// Golden minute without ventilation
        sunctioning = (TextView) findViewById(R.id.sunctioning);
        stimulation = (TextView) findViewById(R.id.stimulation);
        without_combine_video = (TextView) findViewById(R.id.without_combine_video);
        bag_and_mask_ventilation_video = (TextView) findViewById(R.id.bag_and_mask_ventilation_video);
        improving_ventilation_video = (TextView) findViewById(R.id.improving_ventilation_video);

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
        expandableButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Training_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.ventilation_with_slow_or_fast_heart_rate));
                intent.putExtra("title",getResources().getString(R.string.ventilation_with_slow_or_fast_heart_rate));
                intent.putExtra("file","video9");
                startActivity(intent);
            }
        });

        expandableButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Training_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.disinfecting_and_testing_equipment_after_every_use));
                intent.putExtra("title",getResources().getString(R.string.disinfecting_and_testing_equipment_after_every_use));
                intent.putExtra("file","video9");
                startActivity(intent);
            }
        });

        bag_and_mask_ventilation_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Training_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.golden_minutes_with_ventilation));
                intent.putExtra("title",getResources().getString(R.string.bag_and_mask_ventilation));
                intent.putExtra("file","video9");
                startActivity(intent);
            }
        });

        improving_ventilation_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Training_VideoPlayer.class);
                intent.putExtra("mode","play");
                intent.putExtra("header",getResources().getString(R.string.golden_minutes_with_ventilation));
                intent.putExtra("title",getResources().getString(R.string.improving_ventilation));
                intent.putExtra("file","video9");
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

    public void expandableButton1(View view) {
        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);
        expandableLayout1.toggle(); // toggle expand and collapse
        try {
            if (expandableLayout2 != null) {
                if (expandableLayout2.isExpanded()) {
                    expandableLayout2.collapse();
                }
            }

            if (expandableLayout3 != null) {
                if (expandableLayout3.isExpanded()) {
                    expandableLayout3.collapse();
                }
            }

            if (expandableLayout4 != null) {
                if (expandableLayout4.isExpanded()) {
                    expandableLayout4.collapse();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void expandableButton2(View view) {
        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
        expandableLayout2.toggle(); // toggle expand and collapse

        try {
            if (expandableLayout1 != null) {
                if (expandableLayout1.isExpanded()) {
                    expandableLayout1.collapse();
                }
            }

            if (expandableLayout3 != null) {
                if (expandableLayout3.isExpanded()) {
                    expandableLayout3.collapse();
                }
            }

            if (expandableLayout4 != null) {
                if (expandableLayout4.isExpanded()) {
                    expandableLayout4.collapse();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void expandableButton3(View view) {
        expandableLayout3 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout3);
        expandableLayout3.toggle(); // toggle expand and collapse

        try {
            if (expandableLayout1 != null) {
                if (expandableLayout1.isExpanded()) {
                    expandableLayout1.collapse();
                }
            }

            if (expandableLayout2 != null) {
                if (expandableLayout2.isExpanded()) {
                    expandableLayout2.collapse();
                }
            }

            if (expandableLayout4 != null) {
                if (expandableLayout4.isExpanded()) {
                    expandableLayout4.collapse();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void expandableButton4(View view) {
        expandableLayout4 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout4);
        expandableLayout4.toggle(); // toggle expand and collapse

        try {
            if (expandableLayout2 != null) {
                if (expandableLayout2.isExpanded()) {
                    expandableLayout2.collapse();
                }
            }

            if (expandableLayout3 != null) {
                if (expandableLayout3.isExpanded()) {
                    expandableLayout3.collapse();
                }
            }

            if (expandableLayout1 != null) {
                if (expandableLayout1.isExpanded()) {
                    expandableLayout1.collapse();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}