package com.example.john.hbb.activities.home;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.john.hbb.R;
import com.example.john.hbb.core.Constants;
import com.example.john.hbb.core.Phone;
import com.example.john.hbb.core.Server_Service;
import com.example.john.hbb.core.SessionManager;
import com.example.john.hbb.core.UsersSession;
import com.example.john.hbb.db_operations.DBController;
import com.example.john.hbb.db_operations.User;
import com.example.john.hbb.services.ProcessingService;
import com.example.john.hbb.activities.simulation_mode.Start_Simulation;
import com.example.john.hbb.activities.training_mode.TrainingHomeActivity;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.john.hbb.core.Constants.config.OPERATION_DISTRICT;
import static com.example.john.hbb.core.Constants.config.URL_GET_DISTRICT;

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
    private TextView header_text;
    List<String> checklist = new ArrayList<>();
    List<Integer> checkid = new ArrayList<>();
    private static final String TAG = "Menu_Dashboard";
    String menu = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        toolbar = (Toolbar)findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);

        try{
            if (getIntent().getStringExtra("menu") != null){
                menu = getIntent().getStringExtra("menu");
                if (menu.equals("start_menu")){
                    openSelectDialog();
                }
            }else {
                openDialog();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        btn_training = (AppCompatButton) findViewById(R.id.btn_training);
        btn_simulation = (AppCompatButton) findViewById(R.id.btn_simulation);


        btn_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TrainingHomeActivity.class);
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

    private void openDialog(){
        final AlertDialog dialog;
        try{
            final AlertDialog.Builder alert = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.accept_dialog, null);
            // this is set the view from XML inside AlertDialog
            alert.setView(view);
            final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
            Button button = (Button) view.findViewById(R.id.continue_btn);
            final RadioButton alone_radio = (RadioButton) view.findViewById(R.id.alone_radio);
            final RadioButton team_radio = (RadioButton) view.findViewById(R.id.team_radio);
            header_text = (TextView) view. findViewById(R.id.header_text);
            header_text.setText("Welcome ("+new UsersSession(context).fname+" "+new UsersSession(context).lname+")");
            // disallow cancel of AlertDialog on click of back button and outside touch
            alert.setCancelable(false);
            alert.setIcon(getResources().getDrawable(android.R.drawable.checkbox_on_background));

            dialog = alert.create();
            dialog.show();


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int val = 0;
                    if (alone_radio.isChecked()){
                        val = 0;
                    }else if (team_radio.isChecked()){
                        val = 1;
                    }else {
                        val = -1;
                    }
                    if (val != -1){
                        dialog.dismiss();
                       if (val == 1){
                           openSelectDialog();
                       }
                    }else {
                        Toast.makeText(context,">>>Please make a selected..!<<<",Toast.LENGTH_SHORT).show();
                    }

                }

            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void openSelectDialog(){
        final AlertDialog dialog;
        try{
            final AlertDialog.Builder alert = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.select_dialog, null);
            LinearLayout layout_check = (LinearLayout) view.findViewById(R.id.layout_check);
            TextView no_text = (TextView) view.findViewById(R.id.no_text);
            TextView register_text = (TextView) view.findViewById(R.id.register_text);
            Button done_btn = (Button) view.findViewById(R.id.done_btn);
            Button back_btn = (Button) view.findViewById(R.id.back_btn);

            createCheckbox(layout_check,checklist,checkid,no_text);

            // this is set the view from XML inside AlertDialog
            alert.setView(view);
             // disallow cancel of AlertDialog on click of back button and outside touch
            alert.setCancelable(false);
            alert.setIcon(getResources().getDrawable(android.R.drawable.checkbox_on_background));

            dialog = alert.create();
            dialog.show();
            done_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checklist.size()>0){
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context,"Select Or Register atleast 1 member..!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    openDialog();
                }
            });

            register_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    startActivity(new Intent(context,RegisterActivity.class));
                    finish();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createCheckbox(LinearLayout layout, final List<String> checkedlist, final List<Integer> checkedid, TextView textView){
        final List<String> list = new ArrayList<>();
        final List<Integer> id = new ArrayList<>();
        try{
            Cursor cursor = new User(context).getAll(new UsersSession(context).health);
            int count = 0;
            if (cursor .moveToFirst()){

                do {
                    if(cursor.getString(cursor.getColumnIndex(Constants.config.FIRST_NAME)).equals(new UsersSession(context).fname) &&
                            cursor.getString(cursor.getColumnIndex(Constants.config.LAST_NAME)).equals(new UsersSession(context).lname)){

                    }else {
                        count ++;
                        list.add(cursor.getString(cursor.getColumnIndex(Constants.config.FIRST_NAME))+" "
                                +cursor.getString(cursor.getColumnIndex(Constants.config.LAST_NAME)));
                        id.add(cursor.getInt(cursor.getColumnIndex(Constants.config.USER_ID)));
                    }
                }while (cursor.moveToNext());
            }
            textView.setText(count+" Users Found..!");
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            final CheckBox[] cb = new CheckBox[list.size()];
            for (int i = 0; i < list.size(); i++) {
                cb[i] = new CheckBox(this);
                cb[i].setText(list.get(i));
                cb[i].setTextSize(27);
                cb[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                cb[i].setTypeface(Typeface.MONOSPACE);
                //cb.setButtonDrawable(getResources().android.R.drawable.checkboxselector);
                layout.addView(cb[i]);
                final int finalI = i;
                cb[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (cb[finalI].isChecked()) {
                                checkedlist.add(cb[finalI].getText().toString().trim());
                                String text = cb[finalI].getText().toString().trim();
                                int id2 = 0;
                                for (int j = 0; j < list.size(); j++) {
                                    if (text.equals(list.get(j))) {
                                        id2 = id.get(j);
                                    }
                                }
                                checkedid.add(id2);

                                Log.e(TAG,"Checkbox- Checkd");
                            } else {
                                if (checkedlist.contains(cb[finalI].getText().toString().trim())) {
                                    checkedlist.remove(cb[finalI].getText().toString().trim());
                                    checkedid.remove(checkedlist.indexOf(cb[finalI].getText().toString().trim()));
                                }
                                Log.e(TAG,"Checkbox- UNCheckd");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkUserSessions() {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        TextView textView2 = (TextView) findViewById(R.id.toolbar_subtitle);
        textView.setText( new UsersSession(context).getHealth() );
        textView2.setText("Welcome ("+new UsersSession(context).fname + " " + new UsersSession(context).lname+")");
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
                //auth.signOut();

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
