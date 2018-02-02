package com.example.john.hbb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import com.example.john.hbb.configuration.Server_Service;
import com.example.john.hbb.configuration.SessionManager;
import com.example.john.hbb.db_operations.DBController;
import com.example.john.hbb.services.ProcessingService;
import com.example.john.hbb.simulation_mode.Start_Simulation;
import com.example.john.hbb.training_mode.Expandable_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.john.hbb.configuration.Constants.config.OPERATION_DISTRICT;
import static com.example.john.hbb.configuration.Constants.config.URL_GET_DISTRICT;

/**
 * Created by john on 7/5/17.
 * 2020202
 */

public class Menu_Dashboard extends AppCompatActivity {
    private AppCompatButton btn_training,btn_simulation;
    private FirebaseAuth auth;
    private Context context = this;
    Toolbar toolbar;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        toolbar = (Toolbar)findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);
        /// Setting the app icon
        auth = FirebaseAuth.getInstance();
        //final FirebaseUser user = auth.getCurrentUser();

        if (getSupportActionBar() != null){
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setIcon(R.drawable.baby);
        }

        btn_training = (AppCompatButton) findViewById(R.id.btn_training);
        btn_simulation = (AppCompatButton) findViewById(R.id.btn_simulation);

        btn_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Expandable_Activity.class);
                startActivity(intent);
            }
        });
        btn_simulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Start_Simulation.class);
                startActivity(intent);
            }
        });

        checkUserSessions();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intent);
        finish();
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
        email = user.get(SessionManager.KEY_EMAIL);
        String health = user.get(SessionManager.KEY_FACILITY);
        //toolbar.setTitle();

        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        TextView textView2 = (TextView) findViewById(R.id.toolbar_subtitle);
        textView.setText( health );
        textView2.setText("Welcome ("+fname + " " + lname+")");
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setSubtitle("Welcome ("+fname + " " + lname+")");
        }

        select();
    }

    public void select(){
         DBController.fetch(context,email,URL_GET_DISTRICT,OPERATION_DISTRICT);
        //// TODO: 10/23/17  
        startService(new Intent(context, ProcessingService.class));
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
                finish();
                return true;
            case R.id.action_settings:
                // todo: goto back activity from here
                SessionManager session = new SessionManager(getApplicationContext());
                session.logoutUser();
                if (stopService(new Intent(getBaseContext(), Server_Service.class)) == false) {
                    stopService(new Intent(getBaseContext(), Server_Service.class));
                }
                auth.signOut();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
