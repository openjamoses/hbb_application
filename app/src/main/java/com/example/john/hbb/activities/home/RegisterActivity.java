package com.example.john.hbb.activities.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.john.hbb.R;
import com.example.john.hbb.core.Constants;
import com.example.john.hbb.core.Phone;
import com.example.john.hbb.core.UsersSession;
import com.example.john.hbb.db_operations.DBController;
import com.example.john.hbb.db_operations.District;
import com.example.john.hbb.db_operations.Facility;
import com.example.john.hbb.db_operations.User;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static com.example.john.hbb.core.Constants.config.CONTACT;
import static com.example.john.hbb.core.Constants.config.FIRST_NAME;
import static com.example.john.hbb.core.Constants.config.GENDER;
import static com.example.john.hbb.core.Constants.config.HEALTH_FACILITY;
import static com.example.john.hbb.core.Constants.config.HEALTH_ID;
import static com.example.john.hbb.core.Constants.config.HOST_URL;
import static com.example.john.hbb.core.Constants.config.IMEI;
import static com.example.john.hbb.core.Constants.config.LAST_NAME;
import static com.example.john.hbb.core.Constants.config.OPERATION_HEALTH;
import static com.example.john.hbb.core.Constants.config.PASSWORD;
import static com.example.john.hbb.core.Constants.config.URL_SYNC_HEALTH;
import static com.example.john.hbb.core.Constants.config.URL_USER;
import static com.example.john.hbb.core.Constants.config.USERNAME;

/**
 * Created by john on 3/19/18.
 */

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private EditText _fnameText,_lnameText,_passwordText,_usernameText;
    private RadioGroup _genderText;
    private Context context = this;
    //private FirebaseAuth auth;
    private Button _signupButton;
    private Spinner spinner_health;
    IntlPhoneInput phoneInputView;
    private TextView _loginLink;
    private AppCompatRadioButton input_female,input_male;

    private EditText input_district;
    private Spinner spinner;
    private List<String> list;
    private List<Integer> list_id;
    private List<String> health_list = new ArrayList<>();
    private List<Integer> health_id = new ArrayList<>();
    private RadioGroup group_type,group_ownership,group_cadre;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        _fnameText = (EditText) findViewById(R.id.input_fname);
        _lnameText = (EditText) findViewById(R.id.input_lname);
        _usernameText = (EditText) findViewById(R.id.input_username);
        phoneInputView = (IntlPhoneInput) findViewById(R.id.my_phone_input);
        spinner_health = (Spinner) findViewById(R.id.spinner_health);
        //_emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        auth = FirebaseAuth.getInstance();

        _genderText = (RadioGroup)  findViewById(R.id.input_gender);
        input_female = (AppCompatRadioButton) findViewById(R.id.input_female);
        input_male = (AppCompatRadioButton) findViewById(R.id.input_male);
        Button add_btn = (Button) findViewById(R.id.add_btn);
        add_btn.setVisibility(View.GONE);
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
        try{
            phoneInputView.setDefault();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            //FirebaseApp.initializeApp(this);
            spinnerHealth();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        try{
            new DBController().syncCalls(URL_SYNC_HEALTH,OPERATION_HEALTH,"",context);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void signup() {
        Log.d(TAG, "Signup");
        if (!validate()) {
            onSignupFailed();
            return;
        }
       // _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");


        final String fname = _fnameText.getText().toString();
        final String lname = _lnameText.getText().toString();
        String health = spinner_health.getSelectedItem().toString().trim();
        String gender = null;
        if(input_female.isChecked()){
            gender = input_female.getText().toString();
        }else {
            gender = input_male.getText().toString();
        }
        String num_mob = phoneInputView.getNumber();
        //Toast.makeText(context,num_mob,Toast.LENGTH_LONG).show();

        if(phoneInputView.isValid()) {
            progressDialog.show();
            String contact = num_mob;
            final String facility =  "";
            final String username = _usernameText.getText().toString().trim();
            final String password = _passwordText.getText().toString();
            final String gend = gender;

            //String password_encrypt = password;
            try {
                //password_encrypt = Encrypt_Decrypt.cipher(SECRET_KEY,password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String imei = Phone.getIMEI(context);

            if (!health.equals("-- SELECT HEALTH FACILITY --")){
                int id = 0;
                for (int i=0; i<health_list.size(); i++){
                    if (health_list.get(i).equals(health)){
                        id = health_id.get(i);
                    }
                }
                ProgressDialog dialog = new ProgressDialog(context);
                dialog.setMessage("Please wait...");
                String phoneF = contact;
                if (contact.length() >= 10){
                    contact = contact.substring(contact.length()-9,contact.length());
                }
                send(fname,lname,contact,username,gender,imei,password,health,id,progressDialog);
            }else {
                Toast.makeText(context,"Select health facility or add new",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context, " Invalid Phone Number!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, Menu_Dashboard.class);
        intent.putExtra("menu", "start_menu");
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(context, Menu_Dashboard.class);
            intent.putExtra("menu", "start_menu");
            startActivity(intent);
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    public void onSignupSuccess() {
       // _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
       // _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String fname = _fnameText.getText().toString();
        String lname = _lnameText.getText().toString();
        String gender = null;
        //// Getting the checked gender....
        if(input_female.isChecked()){
            gender = input_female.getText().toString();
        }else {
            gender = input_male.getText().toString();
        }
        //String facility = _hfacilityText.getText().toString();
        String username = _usernameText.getText().toString().trim();
        String password = _passwordText.getText().toString();

        if (fname.isEmpty() || fname.length() < 3) {
            _fnameText.setError("at least 3 characters");
            valid = false;
        } else {
            _fnameText.setError(null);
        }

        if (lname.isEmpty() || lname.length() < 3) {
            _lnameText.setError("at least 3 characters");
            valid = false;
        } else {
            _lnameText.setError(null);
        }
        if (username.isEmpty() || username.length() < 3) {
            _usernameText.setError("at least 3 characters");
            valid = false;
        } else {
            _usernameText.setError(null);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10 ) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


    public void send(final String fname, final String lname, final String phone, final String username, final String gender, final String imei, final String password, final String health, final int heath_id, final ProgressDialog dialog){
        try{
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_USER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG,"Results: "+response);

                        String[] splits = response.split("/");
                        int status = 0, id = 0;

                        if (splits[0].equals("Already_Registered username and Phone Number")){
                            Toast.makeText(context,splits[0],Toast.LENGTH_SHORT).show();
                        }else {
                            if (splits[0].equals("Success")){
                                status = 1;
                                id = Integer.parseInt(splits[1]);
                            }

                            String message = new User(context).save(fname,lname,username,phone,gender,health,id,password,status);
                            if (message.equals("User Details saved!")){
                                showDiag();
                            }
                        }

                        try{
                            dialog.dismiss();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        Log.e(TAG,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        try {
                            Log.e(TAG, volleyError.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                int status = 1;
                Map<String, String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put(FIRST_NAME, fname);
                params.put(LAST_NAME, lname);
                params.put(CONTACT, phone);
                params.put(USERNAME, username);
                params.put(GENDER, gender);
                params.put(PASSWORD, password);
                params.put(IMEI, imei);
                params.put(HEALTH_ID, String.valueOf(heath_id));
                params.put(HEALTH_FACILITY, health);
                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    private void spinnerHealth() {
        health_list = new ArrayList<>();
        health_id = new ArrayList<>();
        health_list.add(new UsersSession(context).health);
        health_id.add(new UsersSession(context).getHealthID());
        try{
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, health_list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_health.setAdapter(dataAdapter2);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showDiag(){
        Intent intent = new Intent(context, Menu_Dashboard.class);
        intent.putExtra("menu", "start_menu");
        startActivity(intent);
        finish();
    }

}
