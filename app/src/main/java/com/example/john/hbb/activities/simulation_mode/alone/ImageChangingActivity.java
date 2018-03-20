package com.example.john.hbb.activities.simulation_mode.alone;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.hbb.R;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import com.example.john.hbb.core.DateTime;
import com.example.john.hbb.core.SessionManager;
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
    private boolean isComplete = false;
    private String type = "";

    Handler mainHandler = new Handler(Looper.getMainLooper());

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
        type = getIntent().getStringExtra("type");
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
                else {
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
                isComplete = true;

                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            exitVideo();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                mainHandler.post(myRunnable);
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

        if(file.equals("images0")){
            showDialog_prepare();
        }else if(file.equals("images1")){
            showDialog_routine();
        }else if(file.equals("images3")){
            showDialog_gmv();
        }else if (file.equals("images4")){
            showDialog_vnh();
        }else {
            showDialog();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exitVideo();
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
    private void showDialog_prepare(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(getResources().getDrawable(android.R.drawable.checkbox_on_background));
        alert.setTitle(getResources().getString(R.string.check_list));

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.check_list_preparation, null);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        final RadioGroup identifies_a_helper_group = (RadioGroup)alertLayout.findViewById(R.id.identifies_a_helper_group);
        final RadioGroup prepares_area_for_delivery_group = (RadioGroup)alertLayout.findViewById(R.id.prepares_area_for_delivery_group);
        final RadioGroup washe_hands_group = (RadioGroup)alertLayout.findViewById(R.id.washe_hands_group);
        final RadioGroup prepares_an_area_for_ventilation_group = (RadioGroup)alertLayout.findViewById(R.id.prepares_an_area_for_ventilation_group);
        final RadioGroup assembles_disinfected_group = (RadioGroup)alertLayout.findViewById(R.id.assembles_disinfected_group);
        final RadioGroup test_the_ventilation_group = (RadioGroup)alertLayout.findViewById(R.id.test_the_ventilation_group);
        final RadioGroup prepare_a_uterotonic_group = (RadioGroup)alertLayout.findViewById(R.id.prepare_a_uterotonic_group);
        //alert.setCancelable(false);
        alert.setCancelable(false);
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
                String identifies_a_helper_val, prepares_area_for_delivery_val,washe_hands_val,
                        prepares_an_area_for_ventilation_val, assembles_disinfected_val, test_the_ventilation_val,
                        prepare_a_uterotonic_val;

                if(identifies_a_helper_group.getCheckedRadioButtonId() != -1 && prepares_area_for_delivery_group.getCheckedRadioButtonId() != -1 &&
                        washe_hands_group.getCheckedRadioButtonId() != -1 && prepares_an_area_for_ventilation_group.getCheckedRadioButtonId() != -1 &&
                        assembles_disinfected_group.getCheckedRadioButtonId() != -1 && test_the_ventilation_group.getCheckedRadioButtonId() != -1 &&
                        prepare_a_uterotonic_group.getCheckedRadioButtonId() != -1 )
                {
                    int identifies_a_helperId = identifies_a_helper_group.getCheckedRadioButtonId();
                    int prepares_area_for_deliveryId = prepares_area_for_delivery_group.getCheckedRadioButtonId();
                    int washe_handsId = washe_hands_group.getCheckedRadioButtonId();
                    int prepares_an_area_for_ventilationId = prepares_an_area_for_ventilation_group.getCheckedRadioButtonId();
                    int assembles_disinfectedId = assembles_disinfected_group.getCheckedRadioButtonId();
                    int test_the_ventilationId = test_the_ventilation_group.getCheckedRadioButtonId();
                    int prepare_a_uterotonicId = prepare_a_uterotonic_group.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    identifies_a_helper_val = ((RadioButton)alertLayout.findViewById(identifies_a_helperId)).getText().toString();
                    prepares_area_for_delivery_val = ((RadioButton)alertLayout.findViewById(prepares_area_for_deliveryId)).getText().toString();
                    washe_hands_val = ((RadioButton)alertLayout.findViewById(washe_handsId)).getText().toString();
                    prepares_an_area_for_ventilation_val = ((RadioButton)alertLayout.findViewById(prepares_an_area_for_ventilationId)).getText().toString();
                    assembles_disinfected_val = ((RadioButton)alertLayout.findViewById(assembles_disinfectedId)).getText().toString();
                    test_the_ventilation_val = ((RadioButton)alertLayout.findViewById(test_the_ventilationId)).getText().toString();
                    prepare_a_uterotonic_val = ((RadioButton)alertLayout.findViewById(prepare_a_uterotonicId)).getText().toString();

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

    private void showDialog_routine(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(getResources().getDrawable(android.R.drawable.checkbox_on_background));
        alert.setTitle(getResources().getString(R.string.check_list));

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.check_list_routine, null);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch

        final RadioGroup dries_throughly_group = (RadioGroup)alertLayout.findViewById(R.id.dries_throughly_group);
        final RadioGroup recognises_crying_group = (RadioGroup)alertLayout.findViewById(R.id.recognises_crying_group);
        final RadioGroup keeps_warm_group = (RadioGroup)alertLayout.findViewById(R.id.keeps_warm_group);
        final RadioGroup checks_breathing_group = (RadioGroup)alertLayout.findViewById(R.id.checks_breathing_group);
        final RadioGroup clamps_or_ties_group = (RadioGroup)alertLayout.findViewById(R.id.clamps_or_ties_group);
        final RadioGroup position_on_mothers_group = (RadioGroup)alertLayout.findViewById(R.id.position_on_mothers_group);
        final RadioGroup continue_with_essential_group = (RadioGroup)alertLayout.findViewById(R.id.continue_with_essential_group);
        //alert.setCancelable(false);
        alert.setCancelable(false);
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
                String dries_throughly_val, recognises_crying_val,keeps_warm_val,
                        checks_breathing_val, clamps_or_ties_val, position_on_mothers_val,
                        continue_with_essential_val;

                if(dries_throughly_group.getCheckedRadioButtonId() != -1 && recognises_crying_group.getCheckedRadioButtonId() != -1 &&
                        keeps_warm_group.getCheckedRadioButtonId() != -1 && checks_breathing_group.getCheckedRadioButtonId() != -1 &&
                        clamps_or_ties_group.getCheckedRadioButtonId() != -1 && position_on_mothers_group.getCheckedRadioButtonId() != -1 &&
                        continue_with_essential_group.getCheckedRadioButtonId() != -1 )
                {
                    int dries_throughlyId = dries_throughly_group.getCheckedRadioButtonId();
                    int recognises_cryingId = recognises_crying_group.getCheckedRadioButtonId();
                    int keeps_warmId = keeps_warm_group.getCheckedRadioButtonId();
                    int checks_breathingId = checks_breathing_group.getCheckedRadioButtonId();
                    int clamps_or_tiesId = clamps_or_ties_group.getCheckedRadioButtonId();
                    int position_on_mothersId = position_on_mothers_group.getCheckedRadioButtonId();
                    int continue_with_essentialId = continue_with_essential_group.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    dries_throughly_val = ((RadioButton)alertLayout.findViewById(dries_throughlyId)).getText().toString();
                    recognises_crying_val = ((RadioButton)alertLayout.findViewById(recognises_cryingId)).getText().toString();
                    keeps_warm_val = ((RadioButton)alertLayout.findViewById(keeps_warmId)).getText().toString();
                    checks_breathing_val = ((RadioButton)alertLayout.findViewById(checks_breathingId)).getText().toString();
                    clamps_or_ties_val = ((RadioButton)alertLayout.findViewById(clamps_or_tiesId)).getText().toString();
                    position_on_mothers_val = ((RadioButton)alertLayout.findViewById(position_on_mothersId)).getText().toString();
                    continue_with_essential_val = ((RadioButton)alertLayout.findViewById(continue_with_essentialId)).getText().toString();
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

    private void showDialog_gmv(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(getResources().getDrawable(android.R.drawable.checkbox_on_background));
        alert.setTitle(getResources().getString(R.string.check_list));

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.check_list_gwv, null);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        alert.setCancelable(false);
        // disallow cancel of AlertDialog on click of back button and outside touch
        final RadioGroup dries_throughly_group = (RadioGroup)alertLayout.findViewById(R.id.dries_throughly_group);
        final RadioGroup recognises_not_crying_group = (RadioGroup)alertLayout.findViewById(R.id.recognises_not_crying_group);
        final RadioGroup keep_warm_clears_group = (RadioGroup)alertLayout.findViewById(R.id.keep_warm_clears_group);
        final RadioGroup stimulates_breathing_group = (RadioGroup)alertLayout.findViewById(R.id.stimulates_breathing_group);
        final RadioGroup not_breathing_group = (RadioGroup)alertLayout.findViewById(R.id.not_breathing_group);
        final RadioGroup follows_routine_group = (RadioGroup)alertLayout.findViewById(R.id.follows_routine_group);
        final RadioGroup move_to_area_for_ventilation_stand_group = (RadioGroup)alertLayout.findViewById(R.id.move_to_area_for_ventilation_stand_group);
        final RadioGroup ventilate_group = (RadioGroup)alertLayout.findViewById(R.id.ventilate_group);
        final RadioGroup recognises_chest_moving_group = (RadioGroup)alertLayout.findViewById(R.id.recognises_chest_moving_group);
        final RadioGroup recognises_breathing_well_group = (RadioGroup)alertLayout.findViewById(R.id.recognises_breathing_well_group);
        final RadioGroup monitors_with_mother_group = (RadioGroup)alertLayout.findViewById(R.id.monitors_with_mother_group);
        final RadioGroup continues_with_essential_newborn_group = (RadioGroup)alertLayout.findViewById(R.id.continues_with_essential_newborn_group);
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
                String dries_throughly_val, recognises_not_crying_val,keep_warm_clears_val,
                        stimulates_breathing_val, not_breathing_val, follows_routine_val,
                        move_to_area_for_ventilation_stand_val,ventilate_val,recognises_chest_moving_val,
                        recognises_breathing_well_val,monitors_with_mother_val,continues_with_essential_newborn_val;

                if(dries_throughly_group.getCheckedRadioButtonId() != -1 && recognises_not_crying_group.getCheckedRadioButtonId() != -1 &&
                        keep_warm_clears_group.getCheckedRadioButtonId() != -1 && stimulates_breathing_group.getCheckedRadioButtonId() != -1 &&
                        not_breathing_group.getCheckedRadioButtonId() != -1 && follows_routine_group.getCheckedRadioButtonId() != -1 &&
                        move_to_area_for_ventilation_stand_group.getCheckedRadioButtonId() != -1 && ventilate_group.getCheckedRadioButtonId() != -1 &&
                        recognises_chest_moving_group.getCheckedRadioButtonId() != -1 && recognises_breathing_well_group.getCheckedRadioButtonId() != -1 &&
                        monitors_with_mother_group.getCheckedRadioButtonId() != -1 && continues_with_essential_newborn_group.getCheckedRadioButtonId() != -1 )
                {
                    int dries_throughlyId = dries_throughly_group.getCheckedRadioButtonId();
                    int recognises_not_cryingId = recognises_not_crying_group.getCheckedRadioButtonId();
                    int keep_warm_clearsId = keep_warm_clears_group.getCheckedRadioButtonId();
                    int stimulates_breathingId = stimulates_breathing_group.getCheckedRadioButtonId();
                    int not_breathingId = not_breathing_group.getCheckedRadioButtonId();
                    int follows_routineId = follows_routine_group.getCheckedRadioButtonId();
                    int move_to_area_for_ventilation_standId = move_to_area_for_ventilation_stand_group.getCheckedRadioButtonId();
                    int ventilateId = ventilate_group.getCheckedRadioButtonId();

                    int recognises_chest_movingId = recognises_chest_moving_group.getCheckedRadioButtonId();
                    int recognises_breathing_wellId = recognises_breathing_well_group.getCheckedRadioButtonId();
                    int monitors_with_motherId = monitors_with_mother_group.getCheckedRadioButtonId();
                    int continues_with_essential_newbornId = continues_with_essential_newborn_group.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    dries_throughly_val = ((RadioButton)alertLayout.findViewById(dries_throughlyId)).getText().toString();
                    recognises_not_crying_val = ((RadioButton)alertLayout.findViewById(recognises_not_cryingId)).getText().toString();
                    keep_warm_clears_val = ((RadioButton)alertLayout.findViewById(keep_warm_clearsId)).getText().toString();
                    stimulates_breathing_val = ((RadioButton)alertLayout.findViewById(stimulates_breathingId)).getText().toString();
                    not_breathing_val = ((RadioButton)alertLayout.findViewById(not_breathingId)).getText().toString();
                    follows_routine_val = ((RadioButton)alertLayout.findViewById(follows_routineId)).getText().toString();
                    move_to_area_for_ventilation_stand_val = ((RadioButton)alertLayout.findViewById(move_to_area_for_ventilation_standId)).getText().toString();
                    ventilate_val = ((RadioButton)alertLayout.findViewById(ventilateId)).getText().toString();
                    recognises_chest_moving_val = ((RadioButton)alertLayout.findViewById(recognises_chest_movingId)).getText().toString();
                    recognises_breathing_well_val = ((RadioButton)alertLayout.findViewById(recognises_breathing_wellId)).getText().toString();
                    monitors_with_mother_val = ((RadioButton)alertLayout.findViewById(monitors_with_motherId)).getText().toString();
                    continues_with_essential_newborn_val = ((RadioButton)alertLayout.findViewById(continues_with_essential_newbornId)).getText().toString();

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


    private void showDialog_vnh(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(getResources().getDrawable(android.R.drawable.checkbox_on_background));
        alert.setTitle(getResources().getString(R.string.check_list));

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.check_list_vnh, null);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch

        final RadioGroup recognises_not_breathing_and_chest_group = (RadioGroup)alertLayout.findViewById(R.id.recognises_not_breathing_and_chest_group);
        final RadioGroup calls_for_help_group = (RadioGroup)alertLayout.findViewById(R.id.calls_for_help_group);
        final RadioGroup continues_and_improves_group = (RadioGroup)alertLayout.findViewById(R.id.continues_and_improves_group);
        final RadioGroup recognises_not_breathing_group = (RadioGroup)alertLayout.findViewById(R.id.recognises_not_breathing_group);
        final RadioGroup recognises_normal_or_slow_heart_group = (RadioGroup)alertLayout.findViewById(R.id.recognises_normal_or_slow_heart_group);
        final RadioGroup recognises_breathing_or_not_breathing_group = (RadioGroup)alertLayout.findViewById(R.id.recognises_breathing_or_not_breathing_group);
        final RadioGroup if_breathing_and_normal_heart_rate_group = (RadioGroup)alertLayout.findViewById(R.id.if_breathing_and_normal_heart_rate_group);
        final RadioGroup if_not_breathing_or_slow_heart_rate_group = (RadioGroup)alertLayout.findViewById(R.id.if_not_breathing_or_slow_heart_rate_group);
        final RadioGroup communicates_with_mothers_group = (RadioGroup)alertLayout.findViewById(R.id.communicates_with_mothers_group);
        final RadioGroup continues_with_essential_group = (RadioGroup)alertLayout.findViewById(R.id.continues_with_essential_group);
        final RadioGroup disinfect_equipment_group = (RadioGroup)alertLayout.findViewById(R.id.disinfect_equipment_group);
        //alert.setCancelable(false);
        alert.setCancelable(false);
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
                String recognises_not_breathing_and_chest_val, calls_for_help_val,continues_and_improves_val,
                        recognises_not_breathing_val, recognises_normal_or_slow_heart_val, recognises_breathing_or_not_breathing_val,
                        if_breathing_and_normal_heart_rate_val,if_not_breathing_or_slow_heart_rate_val,communicates_with_mothers_val,
                        continues_with_essential_val,disinfect_equipment_val;

                if(recognises_not_breathing_and_chest_group.getCheckedRadioButtonId() != -1 && calls_for_help_group.getCheckedRadioButtonId() != -1 &&
                        continues_and_improves_group.getCheckedRadioButtonId() != -1 && recognises_not_breathing_group.getCheckedRadioButtonId() != -1 &&
                        recognises_normal_or_slow_heart_group.getCheckedRadioButtonId() != -1 && recognises_breathing_or_not_breathing_group.getCheckedRadioButtonId() != -1 &&
                        if_breathing_and_normal_heart_rate_group.getCheckedRadioButtonId() != -1 && if_not_breathing_or_slow_heart_rate_group.getCheckedRadioButtonId() != -1 &&
                        communicates_with_mothers_group.getCheckedRadioButtonId() != -1 && continues_with_essential_group.getCheckedRadioButtonId() != -1 &&
                        disinfect_equipment_group.getCheckedRadioButtonId() != -1  )
                {
                    int recognises_not_breathing_and_chestId = recognises_not_breathing_and_chest_group.getCheckedRadioButtonId();
                    int calls_for_helpId = calls_for_help_group.getCheckedRadioButtonId();
                    int continues_and_improvesId = continues_and_improves_group.getCheckedRadioButtonId();
                    int recognises_not_breathingId = recognises_not_breathing_group.getCheckedRadioButtonId();
                    int recognises_normal_or_slow_heartId = recognises_normal_or_slow_heart_group.getCheckedRadioButtonId();
                    int recognises_breathing_or_not_breathingId = recognises_breathing_or_not_breathing_group.getCheckedRadioButtonId();
                    int if_breathing_and_normal_heart_rateId = if_breathing_and_normal_heart_rate_group.getCheckedRadioButtonId();
                    int if_not_breathing_or_slow_heart_rateId = if_not_breathing_or_slow_heart_rate_group.getCheckedRadioButtonId();
                    int communicates_with_mothersId = communicates_with_mothers_group.getCheckedRadioButtonId();
                    int continues_with_essentialId = continues_with_essential_group.getCheckedRadioButtonId();
                    int disinfect_equipmentId = disinfect_equipment_group.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    recognises_not_breathing_and_chest_val = ((RadioButton)alertLayout.findViewById(recognises_not_breathing_and_chestId)).getText().toString();
                    calls_for_help_val = ((RadioButton)alertLayout.findViewById(calls_for_helpId)).getText().toString();
                    continues_and_improves_val = ((RadioButton)alertLayout.findViewById(continues_and_improvesId)).getText().toString();
                    recognises_not_breathing_val = ((RadioButton)alertLayout.findViewById(recognises_not_breathingId)).getText().toString();
                    recognises_normal_or_slow_heart_val = ((RadioButton)alertLayout.findViewById(recognises_normal_or_slow_heartId)).getText().toString();
                    recognises_breathing_or_not_breathing_val = ((RadioButton)alertLayout.findViewById(recognises_breathing_or_not_breathingId)).getText().toString();
                    if_breathing_and_normal_heart_rate_val = ((RadioButton)alertLayout.findViewById(if_breathing_and_normal_heart_rateId)).getText().toString();
                    if_not_breathing_or_slow_heart_rate_val = ((RadioButton)alertLayout.findViewById(if_not_breathing_or_slow_heart_rateId)).getText().toString();
                    communicates_with_mothers_val = ((RadioButton)alertLayout.findViewById(communicates_with_mothersId)).getText().toString();
                    continues_with_essential_val = ((RadioButton)alertLayout.findViewById(continues_with_essentialId)).getText().toString();
                    disinfect_equipment_val = ((RadioButton)alertLayout.findViewById(disinfect_equipmentId)).getText().toString();

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

        String date = DateTime.getCurrentDate();
        String time = DateTime.getCurrentTime();
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