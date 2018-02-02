package com.example.john.hbb;

/**
 * Created by john on 7/5/17.
 */
import android.*;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.john.hbb.configuration.Constants;
import com.example.john.hbb.configuration.DBHelper;
import com.example.john.hbb.configuration.Server_Service;
import com.example.john.hbb.configuration.SessionManager;
import com.example.john.hbb.db_operations.DBController;
import com.example.john.hbb.db_operations.User;
import com.example.john.hbb.encryptions.Encrypt_Decrypt;
import com.example.john.hbb.firebase.ResetPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.john.hbb.configuration.Constants.config.OPERATION_DISTRICT;
import static com.example.john.hbb.configuration.Constants.config.OPERATION_USER;
import static com.example.john.hbb.configuration.Constants.config.URL_GET_DISTRICT;
import static com.example.john.hbb.configuration.Constants.config.URL_GET_USER;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private Context context = this;

    private EditText _emailText,_passwordText;
    private Button _loginButton;
    private TextView _signupLink;

    private TransparentProgressDialog pd;
    private Handler h;
    private Runnable r;
    // Session Manager Class
    SessionManager session;
    private FirebaseAuth auth;
    private TextView forgot_text;
    String email;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static int SPLASH_TIME_OUT = 2000;
    public static final int RequestPermissionCode = 1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        _emailText = (EditText) findViewById(R.id.input_email) ;
        _passwordText = (EditText) findViewById(R.id.input_password) ;
        _loginButton = (Button) findViewById(R.id.btn_login) ;
        _signupLink = (TextView) findViewById(R.id.link_signup) ;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Session Manager
        session = new SessionManager(getApplicationContext());
        TextView textView2 = (TextView) findViewById(R.id.toolbar_subtitle);
        textView2.setText("Helping Babies Breath");

        forgot_text = (TextView) findViewById(R.id.forgot_txt);
        forgot_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ResetPasswordActivity.class));
            }
        });


        //// TODO: 10/23/17  Checking permsions...!! 
        checkAndRequestPermissions();
        try {
            //FirebaseApp.initializeApp(this);
            auth = FirebaseAuth.getInstance();
            final FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                if (user.isEmailVerified()) {
                    //Toast.makeText(LoginActivity.this,"You are in =)",Toast.LENGTH_LONG).show();
                    String email = user.getEmail();
                    if (email != null){
                        new User(context).updateUserStatus(email,1);
                    }
                }else {

                    final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setCancelable(false);
                    dialog.setTitle("Signup Message Dialog");
                    dialog.setMessage("Confirmation Message has been sent to \n your email address\n Please login to your email to confirm !" );
                    dialog.setPositiveButton("Resend", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //Action for "Delete".
                            user.sendEmailVerification();
                            dialog.dismiss();
                        }
                    });
                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    final AlertDialog alert = dialog.create();
                    alert.show();

                    //Toast.makeText(LoginActivity.this,"Check your email first...",Toast.LENGTH_LONG).show();

                }
            }else {
                //Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                //startActivityForResult(intent, REQUEST_SIGNUP);
                //finish();
            }

            if (auth.getCurrentUser() != null) {
                //startActivity(new Intent(context, MainActivity.class));
                //finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        h = new Handler();
        pd = new TransparentProgressDialog(this, R.drawable.dialog_image);
        r =new Runnable() {
            @Override
            public void run() {
                if (pd.isShowing()) {
                    pd.dismiss();

                       final String email_ = _emailText.getText().toString();
                        final String password = _passwordText.getText().toString();
                        //String password_encrypt = Encrypt_Decrypt.decrypt(password);

                    try {

                            int useID = 0;
                            int verified = 0;
                            String message = null,fname = null,lname = null,email = null,contact = null,facility = null;

                            //start the profile activity
                            DBHelper dbHelper = new DBHelper(context);
                            Cursor cursor = dbHelper.userLogin(email_, password);
                        if (cursor != null) {
                            if (cursor.moveToFirst()) {
                                do {
                                    message = "Login Successfully!";
                                    useID = cursor.getInt(cursor.getColumnIndex(Constants.config.USER_ID));
                                    fname = cursor.getString(cursor.getColumnIndex(Constants.config.FIRST_NAME));
                                    lname = cursor.getString(cursor.getColumnIndex(Constants.config.LAST_NAME));
                                    email = cursor.getString(cursor.getColumnIndex(Constants.config.EMAIL));
                                    contact = cursor.getString(cursor.getColumnIndex(Constants.config.CONTACT));
                                    facility = cursor.getString(cursor.getColumnIndex(Constants.config.HEALTH_FACILITY));
                                    verified = cursor.getInt(cursor.getColumnIndex(Constants.config.VERIFIED_STATUS));
                                } while (cursor.moveToNext());

                            } else {
                                select(email_);

                                message = "Login Failed!";
                                useID = 0;
                                fname = "fname";
                                lname = "lname";
                                email = "email";
                                contact = "contact";
                                facility = "facility";
                            }

                        }else {
                            select(email_);
                            message = "Login Failed!";
                            useID = 0;
                            fname = "fname";
                            lname = "lname";
                            email = "email";
                            contact = "contact";
                            facility = "facility";
                        }
                            onLoginSuccess(message, useID, fname, lname, email, contact, facility);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                }
            }
        };
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        if (session.isLoggedIn()){
            if (pd != null){
                pd.show();
            }
            startService(new Intent(getBaseContext(), Server_Service.class));


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // new CallingNumber(c).clearCalls();
                    if ( pd != null){
                        if (pd.isShowing()){
                            pd.dismiss();
                        }
                    }
                    checkUserSessions();
                }},3000);
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

        String fname = user.get(SessionManager.KEY_FNAME);
        String lname = user.get(SessionManager.KEY_LNAME);
        String contact = user.get(SessionManager.KEY_CONTACT);
        email = user.get(SessionManager.KEY_EMAIL);
        String health = user.get(SessionManager.KEY_FACILITY);
        //toolbar.setTitle();

        if (fname != null && lname != null && contact != null && email != null && health != null){
            Intent i = new Intent(getApplicationContext(), Menu_Dashboard.class);
            startActivity(i);
            finish();
        }

    }

    public void select(String email){
        DBController.fetch(context,email,URL_GET_USER,OPERATION_USER);
    }

    @Override
    protected void onDestroy() {
        h.removeCallbacks(r);
        if (pd.isShowing() ) {
            pd.dismiss();
        }
        super.onDestroy();
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_Dialog_Alert);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                //progressDialog.show();

        pd.show();
        ///TODO: Start your service here..
        //Intent intent = new Intent(getBaseContext(),Server_Service.class);
        //intent.putExtra("progress",pd);
        startService(new Intent(getBaseContext(), Server_Service.class));


        h.postDelayed(r,3000);


        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                    }
                }, 3000);
    }

    private class TransparentProgressDialog extends Dialog {

        private ImageView iv;
        public TransparentProgressDialog(Context context, int resourceIdOfImage) {
            super(context, R.style.TransparentProgressDialog);
            WindowManager.LayoutParams wlmp = getWindow().getAttributes();
            wlmp.gravity = Gravity.CENTER_HORIZONTAL;
            getWindow().setAttributes(wlmp);
            setTitle(null);
            setCancelable(false);
            setOnCancelListener(null);
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            iv = new ImageView(context);
            iv.setImageResource(resourceIdOfImage);
            layout.addView(iv, params);
            addContentView(layout, params);
        }

        @Override
        public void show() {
            super.show();
            RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(3000);
            iv.setAnimation(anim);
            iv.startAnimation(anim);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
               // this.finish();
                // Start the Signup activity
               // Intent intent = new Intent(getApplicationContext(), Menu_Dashboard.class);
                //startActivityForResult(intent, REQUEST_SIGNUP);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
    public void onLoginSuccess(String message, int useID, String fname, String lname, String email, String contact,String facility) {
        _loginButton.setEnabled(true);
        //finish();
        //Intent intent = new Intent(getApplicationContext(),Menu_Dashboard.class);
        //startActivity(intent);
        // Creating user login session
        // For testing i am stroing name, email as follow
        // Use user real data
        if(message.equals("Login Successfully!")) {
            session.createLoginSession(useID, fname, lname, contact, email, facility);
            // Staring MainActivity
            Intent i = new Intent(getApplicationContext(), Menu_Dashboard.class);
            startActivity(i);
            //if (stopService(new Intent(getBaseContext(), Server_Service.class)) == true) {
               // startService(new Intent(getBaseContext(), Server_Service.class));
           // }
            /// ***************************************************************************
            finish();
        }else {
            onLoginFailed();
        }
    }
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }









    ///// TODO: 10/13/17   permission requests...
    private  boolean checkAndRequestPermissions() {
       
        int writepermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readpermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionNetworkstate = ContextCompat.checkSelfPermission(this, ACCESS_NETWORK_STATE);
        int permissionLocation = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        
        List<String> listPermissionsNeeded = new ArrayList<>();
        
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionNetworkstate != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(ACCESS_NETWORK_STATE);
        }
        if (readpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (readpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if ( perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
                        Log.d(TAG, "All permissions services permission granted");
                        // process the normal flow
                        //Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
                        //startActivity(i);
                        //finish();
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if ( ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_NETWORK_STATE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                            showDialogOK("Service Permissions are required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    finish();
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?");
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
    private void explain(String msg){
        final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        //  permissionsclass.requestPermission(type,code);
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.exampledemo.parsaniahardik.marshmallowpermission")));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.setCancelable(false);
        dialog.show();

    }
    
}