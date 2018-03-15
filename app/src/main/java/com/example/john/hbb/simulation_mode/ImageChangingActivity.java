package com.example.john.hbb.simulation_mode;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.hbb.R;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import com.example.john.hbb.configuration.DateTime;
import com.example.john.hbb.configuration.SessionManager;
import com.example.john.hbb.utils.CompressBitmap;

/**
 * Created by john on 7/12/17.
 */

public class ImageChangingActivity extends AppCompatActivity {

    private Integer images[] = {R.drawable.pics1, R.drawable.pics2, R.drawable.pics3};
    private int currImage = 0;
    private String header,title1,title2,title3,file;
    private AppCompatButton exit_button;
    public int counter = 0;
    private TextView textView3;
    private String[] title = null;
    TextView textView2;
    private String[] golden_without_checklist = null;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_switcher);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar);

        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText(getResources().getString(R.string.app_name));
        textView2 = (TextView) findViewById(R.id.toolbar_subtitle);
        ImageView imageView = (ImageView) findViewById(R.id.toolbar_image);
        imageView.setVisibility(View.GONE);
        textView3 = (TextView) findViewById(R.id.textView3);
        exit_button = (AppCompatButton) findViewById(R.id.exit_button);



        header = getIntent().getStringExtra("header");
        title = getIntent().getStringArrayExtra("title");
        file = getIntent().getStringExtra("file");
        textView.setText(header);

        Log.e("Titles:", title.toString());

        textView2.setSelected(true);
        textView2.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView2.setSingleLine(true);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setSubtitle(title1);

        }
        coutDown();

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitVideo();
            }
        });
        checkUserSessions();

    }
    private void coutDown(){
        final int[] c = {0};
        final int total = title.length * 2;
        new CountDownTimer(total*10000, 1000){
            public void onTick(long millisUntilFinished){


                if(file.equals("images1")){
                    images = new Integer[]{R.drawable.pics1, R.drawable.pics2, R.drawable.pics3,  R.drawable.pics4, R.drawable.pics5};

                }else if(file.equals("images2")){
                    images = new Integer[]{ R.drawable.pics4, R.drawable.pics5,  R.drawable.pics3};

                }else if(file.equals("images3")){
                    images = new Integer[]{R.drawable.pics1, R.drawable.pics2, R.drawable.pics3,  R.drawable.pics4, R.drawable.pics5};

                }
                  counter++;
                //textView3.setVisibility(View.GONE);
                textView3.setText(counter+"");
                if((counter == 20) ){
                    c[0]++;
                }
                else if((counter == 45) ){
                    c[0]++;
                }else  if((counter == 65) ){
                    c[0]++;
                }

                Log.e("####:","---------------------------------------------------------");

                Log.e("Title:",title[c[0]]);
                Log.e("C Number:",c[0]+"");

                if(c[0] < images.length && c[0] < title.length) {
                    setCurrentImage(images[c[0]]);
                    textView2.setText(title[c[0]]);
                }
                Log.e("####:","---------------------------------------------------------");

            }
            public  void onFinish(){
               // setCurrentImage(images[images.length-1]);
            }
        }.start();
    }



    private void maximiseImageView(final ImageView imageView){
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context, R.style.CustomDialog);
                if ((context.getResources().getConfiguration().screenLayout &
                        Configuration.SCREENLAYOUT_SIZE_MASK) ==
                        Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                    //TODO on a large screen device ...
                    dialog.setContentView(R.layout.image_dialog);
                } else dialog.setContentView(R.layout.image_dialog);

                Bitmap bitmap = getByteArrayFromImageView(imageView);

                ImageView im = (ImageView) dialog.findViewById(R.id.imageView);
                im.setImageBitmap(CompressBitmap.compress(bitmap));
                im.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    public static Bitmap getByteArrayFromImageView(ImageView imageView)
    {
        BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView.getDrawable());
        Bitmap bitmap;
        if(bitmapDrawable==null){
            imageView.buildDrawingCache();
            bitmap = imageView.getDrawingCache();
            imageView.buildDrawingCache(false);
        }else
        {
            bitmap = bitmapDrawable .getBitmap();
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        //return stream.toByteArray();
        return bitmap;
    }

    private void exitVideo() {

        if(file.equals("images1")){
            showDialog();
        }else if(file.equals("images2")){
            showDialog_Golden_Without();
        }else if(file.equals("images3")){
            showDialog_Golden_Without();
        }else {
            showDialog();
        }

    }

    private void showDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(getResources().getString(R.string.check_list));
        alert.setIcon(getResources().getDrawable(android.R.drawable.checkbox_on_background));

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        final RadioGroup dry_group = (RadioGroup) alertLayout.findViewById(R.id.dry_group);

        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setPositiveButton(getResources().getString(R.string.submit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        final AlertDialog dialog = alert.create();
        dialog.show();

        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RadioButton dry_radio;

                if(dry_group.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(getApplicationContext(), "Please select all!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // get selected radio button from radioGroup
                    int selectedId = dry_group.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    dry_radio = (RadioButton)alertLayout.findViewById(selectedId);
                    Toast.makeText(getApplicationContext(), dry_radio.getText().toString()+" is selected", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                }
            }
        });

    }

    private void showDialog_Golden_Without(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(getResources().getDrawable(android.R.drawable.checkbox_on_background));
        alert.setTitle(getResources().getString(R.string.check_list));

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.golden_without_checklist, null);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch

        final RadioGroup dries_group = (RadioGroup)alertLayout.findViewById(R.id.dries_group);
        final RadioGroup remove_group = (RadioGroup)alertLayout.findViewById(R.id.remove_group);
        final RadioGroup recognise_group = (RadioGroup)alertLayout.findViewById(R.id.recognise_group);
        final RadioGroup simulate_group = (RadioGroup)alertLayout.findViewById(R.id.simulate_group);
        final RadioGroup not_breathing_group = (RadioGroup)alertLayout.findViewById(R.id.not_breathing_group);
        final RadioGroup cuts_cord_group = (RadioGroup)alertLayout.findViewById(R.id.cuts_cord_group);
        final RadioGroup ventilation_with_bag_group = (RadioGroup)alertLayout.findViewById(R.id.cuts_cord_group);
        final RadioGroup calls_group = (RadioGroup)alertLayout.findViewById(R.id.calls_group);
        final RadioGroup continues_group = (RadioGroup)alertLayout.findViewById(R.id.continues_group);

        //alert.setCancelable(false);
        alert.setPositiveButton(getResources().getString(R.string.submit), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();

            }
        });
        final AlertDialog dialog = alert.create();
        dialog.show();


        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String dries_val, remove_val,recognise_val,
                        simulate_val, not_breathing_val, cuts_cord_val,
                        ventilation_with_bag_val,calls_val,continues_val;

                if(dries_group.getCheckedRadioButtonId() != -1 && remove_group.getCheckedRadioButtonId() != -1 &&
                        recognise_group.getCheckedRadioButtonId() != -1 && simulate_group.getCheckedRadioButtonId() != -1 &&
                        not_breathing_group.getCheckedRadioButtonId() != -1 && cuts_cord_group.getCheckedRadioButtonId() != -1 &&
                        ventilation_with_bag_group.getCheckedRadioButtonId() != -1 && calls_group.getCheckedRadioButtonId() != -1 &&
                        continues_group.getCheckedRadioButtonId() != -1)
                {
                    int driesId = dries_group.getCheckedRadioButtonId();
                    int removeId = remove_group.getCheckedRadioButtonId();
                    int recogniseId = recognise_group.getCheckedRadioButtonId();
                    int simulateId = simulate_group.getCheckedRadioButtonId();
                    int not_breathingId = not_breathing_group.getCheckedRadioButtonId();
                    int cuts_cordId = cuts_cord_group.getCheckedRadioButtonId();
                    int ventilation_with_bagId = ventilation_with_bag_group.getCheckedRadioButtonId();
                    int callsId = calls_group.getCheckedRadioButtonId();
                    int continuesId = continues_group.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    dries_val = ((RadioButton)alertLayout.findViewById(driesId)).getText().toString();
                    remove_val = ((RadioButton)alertLayout.findViewById(removeId)).getText().toString();
                    recognise_val = ((RadioButton)alertLayout.findViewById(recogniseId)).getText().toString();
                    simulate_val = ((RadioButton)alertLayout.findViewById(simulateId)).getText().toString();
                    not_breathing_val = ((RadioButton)alertLayout.findViewById(not_breathingId)).getText().toString();
                    cuts_cord_val = ((RadioButton)alertLayout.findViewById(cuts_cordId)).getText().toString();
                    ventilation_with_bag_val = ((RadioButton)alertLayout.findViewById(ventilation_with_bagId)).getText().toString();
                    calls_val = ((RadioButton)alertLayout.findViewById(callsId)).getText().toString();
                    continues_val = ((RadioButton)alertLayout.findViewById(continuesId)).getText().toString();

                    dialog.dismiss();
                    finish();
                }
                else
                {
                    // get selected radio button from radioGroup
                    Toast.makeText(getApplicationContext(), "Please select all!", Toast.LENGTH_SHORT).show();

                }
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

        String userID = user.get(SessionManager.KEY_USERID);
        String fname = user.get(SessionManager.KEY_FNAME);
        String lname = user.get(SessionManager.KEY_LNAME);
        String contact = user.get(SessionManager.KEY_CONTACT);
        String email = user.get(SessionManager.KEY_EMAIL);
        String health = user.get(SessionManager.KEY_FACILITY);

        String date = new DateTime().getCurrentDate();
        String time = new DateTime().getCurrentTime();
        int frequency = 1;
        int status = 0;

        //if(count == 1) {
        //DBHelper dbHelper = new DBHelper(context);
        //dbHelper.insertTraining(Integer.parseInt(userID), fname + " " + lname, date, time, frequency, header, title, status);
        // }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                exitVideo();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

   /** private void setInitialImage() {
        setCurrentImage();
    }**/

    private void setCurrentImage(Integer image) {

        final ImageView imageView = (ImageView) findViewById(R.id.imageDisplay);
        imageView.setImageResource(image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maximiseImageView(imageView);
            }
        });

    }
}