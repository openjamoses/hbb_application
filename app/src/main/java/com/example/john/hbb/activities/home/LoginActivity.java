package com.example.john.hbb.activities.home;

/**
 * Created by john on 7/5/17.
 */
import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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


import com.example.john.hbb.R;
import com.example.john.hbb.core.BaseApplication;
import com.example.john.hbb.core.Constants;
import com.example.john.hbb.core.DBHelper;
import com.example.john.hbb.core.Phone;
import com.example.john.hbb.core.Server_Service;
import com.example.john.hbb.core.SessionManager;
import com.example.john.hbb.db_operations.DBController;
import com.example.john.hbb.db_operations.User;
import com.example.john.hbb.firebase.ResetPasswordActivity;
import com.example.john.hbb.services.ProcessingService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_PHONE_STATE;
import static com.example.john.hbb.core.Constants.config.CONTACT;
import static com.example.john.hbb.core.Constants.config.FIRST_NAME;
import static com.example.john.hbb.core.Constants.config.HEALTH_FACILITY;
import static com.example.john.hbb.core.Constants.config.LAST_NAME;
import static com.example.john.hbb.core.Constants.config.OPERATION_HEALTH;
import static com.example.john.hbb.core.Constants.config.OPERATION_LOG;
import static com.example.john.hbb.core.Constants.config.OPERATION_USER;
import static com.example.john.hbb.core.Constants.config.URL_FETCH_JSON;
import static com.example.john.hbb.core.Constants.config.URL_GET_SINGLE_ENTRY;
import static com.example.john.hbb.core.Constants.config.USERNAME;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private Context context = this;

    private EditText _usernameText,_passwordText;
    private Button _loginButton;
    private TextView _signupLink;

    private TransparentProgressDialog pd;
    private Handler h;
    private Runnable r;
    // Session Manager Class
    SessionManager session;
    private FirebaseAuth auth;
    //private TextView forgot_text;
    String email;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static int SPLASH_TIME_OUT = 2000;
    public static final int RequestPermissionCode = 1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.john.hbb.R.layout.login_activity);

        _usernameText = (EditText) findViewById(R.id.input_username) ;
        _passwordText = (EditText) findViewById(R.id.input_password) ;
        _loginButton = (Button) findViewById(R.id.btn_login) ;
        _signupLink = (TextView) findViewById(R.id.link_signup) ;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try{
            if (new SessionManager(context).isLoggedIn() == true){
                new SessionManager(context).logoutUser();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // Session Manager
        session = new SessionManager(getApplicationContext());
        TextView textView2 = (TextView) findViewById(R.id.toolbar_subtitle);
        textView2.setText("Helping Babies Breath");

        //forgot_text = (TextView) findViewById(R.id.forgot_txt);

        //// TODO: 10/23/17  Checking permsions...!! 
        checkAndRequestPermissions();

        if (checkAndRequestPermissions() == true){
            startService(new Intent(context, ProcessingService.class));
            BaseApplication.deleteCache(context);
            String user_query = "SELECT * FROM user_tb";
            String health_query = "SELECT * FROM health_tb";
            String log_query = "SELECT * FROM log_tb";
            DBController.fetchJSON(context,user_query,URL_FETCH_JSON,OPERATION_USER);
            DBController.fetchJSON(context,health_query,URL_FETCH_JSON,OPERATION_HEALTH);
            DBController.fetchJSON(context,log_query,URL_FETCH_JSON,OPERATION_LOG);

        }
        h = new Handler();
        pd = new TransparentProgressDialog(this, R.drawable.dialog_image);
        r =new Runnable() {
            @Override
            public void run() {
                if (pd.isShowing()) {
                    pd.dismiss();
                       final String username_ = _usernameText.getText().toString();
                       final String password = _passwordText.getText().toString();
                       String phone = username_;
                       //if (username_.length() >=10){
                         //  phone = username_.substring(username_.length()-9,username_.length());
                      // }

                    try {
                            int useID = 0;
                            int verified = 0;
                            String message = null,fname = null,lname = null,username = null,contact = null,facility = null, gend = null, pass = null;
                            int fac_id = 0;
                            Cursor cursor = new User(context).userLogin(username_, password);
                        if (cursor != null) {
                            if (cursor.moveToFirst()) {
                                do {
                                    message = "Login Successfully!";
                                    useID = cursor.getInt(cursor.getColumnIndex(Constants.config.USER_ID));
                                    fname = cursor.getString(cursor.getColumnIndex(Constants.config.FIRST_NAME));
                                    lname = cursor.getString(cursor.getColumnIndex(Constants.config.LAST_NAME));
                                    username = cursor.getString(cursor.getColumnIndex(Constants.config.USERNAME));
                                    contact = cursor.getString(cursor.getColumnIndex(Constants.config.CONTACT));
                                    facility = cursor.getString(cursor.getColumnIndex(Constants.config.HEALTH_FACILITY));
                                    gend = cursor.getString(cursor.getColumnIndex(Constants.config.HEALTH_FACILITY));
                                    pass = cursor.getString(cursor.getColumnIndex(Constants.config.HEALTH_FACILITY));
                                    fac_id = cursor.getInt(cursor.getColumnIndex(Constants.config.HEALTH_ID));
                                } while (cursor.moveToNext());
                                Log.e("Cursor","Cursor found");

                            } else {
                                message = "Login Failed!";
                                useID = 0;
                                fname = "fname";
                                lname = "lname";
                                email = "email";
                                contact = "contact";
                                facility = "facility";
                                gend = "facility";
                                pass = "facility";
                                Log.e("Cursor","Cursor Not null but empty");
                            }
                        }else {
                            message = "Login Failed!";
                            useID = 0;
                            fname = "fname";
                            lname = "lname";
                            email = "email";
                            contact = "contact";
                            facility = "facility";
                            gend = "gender";
                            pass = "password";

                            Log.e("Cursor","Cursor Null");
                        }
                            onLoginSuccess(message, useID, fname, lname, username, contact, facility,gend,pass,fac_id);

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
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_Dialog_Alert);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                //progressDialog.show();
        pd.show();
        h.postDelayed(r,1000);
        // TODO: Implement your own authentication logic here.
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
        super.onBackPressed();
        finish();
    }

    public void onLoginSuccess(String message, int useID, String fname, String lname, String username, String contact, String facility, String gender, String password, int fac_id) {
        //_loginButton.setEnabled(true);
        if(message.equals("Login Successfully!")) {
            session.createLoginSession(useID, fname, lname, contact, username, facility, gender,password,fac_id);
            // Staring MainActivity
            Intent i = new Intent(getApplicationContext(), Menu_Dashboard.class);
            startActivity(i);
            /// ***************************************************************************
            finish();
        }else {
            onLoginFailed();
        }
    }
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
       // _loginButton.setEnabled(true);
    }

    ///// TODO: 10/13/17   permission requests...
    private  boolean checkAndRequestPermissions() {
        int writepermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readpermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionNetworkstate = ContextCompat.checkSelfPermission(this, ACCESS_NETWORK_STATE);
        int permissionLocation = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        int permissionInternet = ContextCompat.checkSelfPermission(this, INTERNET);
        int permissionPhone = ContextCompat.checkSelfPermission(this, READ_PHONE_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionNetworkstate != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(ACCESS_NETWORK_STATE);
        }
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (readpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionInternet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (permissionPhone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
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
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if ( perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
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
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
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