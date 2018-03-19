package com.example.john.hbb.activities.simulation_mode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.john.hbb.R;

import java.util.HashMap;

import com.example.john.hbb.activities.simulation_mode.alone.Alone_Home;
import com.example.john.hbb.activities.simulation_mode.rator.Rator_Home;
import com.example.john.hbb.core.SessionManager;
import com.example.john.hbb.core.UsersSession;

/**
 * Created by john on 7/11/17.
 */

public class Start_Simulation extends AppCompatActivity {
    Toolbar toolbar;
    private AppCompatRadioButton alone, rater;
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_simulation);

        toolbar = (Toolbar)findViewById(R.id.toolBar);
        //toolbar.setTitle(getResources().getString(R.string.app_name));
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText(getResources().getString(R.string.app_name));

        alone = (AppCompatRadioButton) findViewById(R.id.alone);
        rater = (AppCompatRadioButton) findViewById(R.id.rater);

        setSupportActionBar(toolbar);
        TextView textView2 = (TextView) findViewById(R.id.toolbar_subtitle);
        checkUserSessions();

        alone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Alone_Home.class);
                startActivity(intent);
            }
        });
        rater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Rator_Home.class);
                startActivity(intent);
            }
        });
    }
    private void checkUserSessions() {


        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        TextView textView2 = (TextView) findViewById(R.id.toolbar_subtitle);
        textView.setText(new UsersSession(context).health);
        textView2.setText("(" + new UsersSession(context).fname + " " + new UsersSession(context).lname + ")");
    }
}
