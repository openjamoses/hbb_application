package com.example.john.hbb.activities.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.hbb.R;
import com.example.john.hbb.configuration.SessionManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import net.rimoto.intlphoneinput.IntlPhoneInput;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private EditText _fnameText,_lnameText,_hfacilityText,_emailText,_passwordText;
    private RadioGroup _genderText;
    private Context context = this;
    private FirebaseAuth auth;
    private Button _signupButton;
    IntlPhoneInput phoneInputView;
    private TextView _loginLink;
    private AppCompatRadioButton input_female,input_male;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        _fnameText = (EditText) findViewById(R.id.input_fname);
        _lnameText = (EditText) findViewById(R.id.input_lname);
        phoneInputView = (IntlPhoneInput) findViewById(R.id.my_phone_input);
        _hfacilityText = (EditText) findViewById(R.id.input_hfacility);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        auth = FirebaseAuth.getInstance();

        _genderText = (RadioGroup)  findViewById(R.id.input_gender);
        input_female = (AppCompatRadioButton) findViewById(R.id.input_female);
        input_male = (AppCompatRadioButton) findViewById(R.id.input_male);

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
            FirebaseApp.initializeApp(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    public void signup() {
        Log.d(TAG, "Signup");
        if (!validate()) {
            onSignupFailed();
            return;
        }
        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");


        final String fname = _fnameText.getText().toString();
        final String lname = _lnameText.getText().toString();
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
            final String contact = num_mob;
            final String facility = _hfacilityText.getText().toString();
            final String email = _emailText.getText().toString();
            final String password = _passwordText.getText().toString();
            final String gend = gender;

            String password_encrypt = password;
            try {
                //password_encrypt = Encrypt_Decrypt.cipher(SECRET_KEY,password);
            } catch (Exception e) {
                e.printStackTrace();
            }

            new SessionManager(context).createTemp(fname, lname, contact, gend, email, password_encrypt, facility);
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            Intent intent = new Intent(context, Signup_Next.class);
                            startActivity(intent);
                        }
                    }, 1000);
        }else {
            Toast.makeText(context, " Invalid Phone Number!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
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
        String facility = _hfacilityText.getText().toString();
        String email = _emailText.getText().toString();
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
        if (facility.isEmpty() || facility.length() < 3) {
            _hfacilityText.setError("at least 3 characters");
            valid = false;
        } else {
            _hfacilityText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10 ) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
