package com.example.john.hbb.activities.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.hbb.R;
import com.example.john.hbb.core.Constants;
import com.example.john.hbb.core.SessionManager;
import com.example.john.hbb.db_operations.District;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by john on 10/20/17.
 */

public class Signup_Next extends AppCompatActivity {
    private RadioButton radion_hospital,radio_centeriv,radio_centeriii,radio_dispensary,
            radio_government,radio_pnfp,radio_pfp,radio_other_type,
            nurse,clinical_officer,medical_officer,pediatricianradio_other_cadre;
    private EditText input_district;
    private Spinner spinner;
    private Context context = this;
    private List<String> list;
    private List<Integer> list_id;
    private AppCompatButton signup_btn;
    private RadioGroup group_type,group_ownership,group_cadre;
    /**private RadioButton radio_hospital,radio_centeriv,radio_centeriii,radio_dispensary,
            radio_government,radio_pnfp,radio_pfp,radio_other_type,
            nurse,clinical_officer,
     **/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_next);

        signup_btn = (AppCompatButton) findViewById(R.id.btn_signup);
        spinner = (Spinner) findViewById(R.id.district_spinner);
        input_district = (EditText) findViewById(R.id.input_district);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerValue();
        group_type = (RadioGroup) findViewById(R.id.group_type);
        group_ownership = (RadioGroup) findViewById(R.id.group_ownership);
        group_cadre = (RadioGroup) findViewById(R.id.group_cadre);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected_type = "",selected_ownership = "",selected_cadre = "";
                try {



                    int checkedRadioButtonId = group_type.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == -1) {
                        // No item selected
                        Toast toast = Toast.makeText(context, "No " + getResources().getString(R.string.type_of_facility) + " Selected !", Toast.LENGTH_SHORT);
                        View views = toast.getView();
                        views.setBackgroundResource(R.drawable.round_conor);
                        TextView text = (TextView) views.findViewById(android.R.id.message);
                        toast.show();
                    } else {

                        int radioButtonID = group_type.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) group_type.findViewById(radioButtonID);
                        selected_type = (String) radioButton.getText();
                    }
                    int checkedRadioButtonId2 = group_ownership.getCheckedRadioButtonId();
                    if (checkedRadioButtonId2 == -1) {
                        // No item selected
                        Toast toast = Toast.makeText(context, "No " + getResources().getString(R.string.facility_ownership) + " Selected !", Toast.LENGTH_SHORT);
                        View views = toast.getView();
                        views.setBackgroundResource(R.drawable.round_conor);
                        TextView text = (TextView) views.findViewById(android.R.id.message);
                        toast.show();
                    } else {

                        int radioButtonID = group_ownership.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) group_ownership.findViewById(radioButtonID);
                        selected_ownership = (String) radioButton.getText();
                    }

                    int checkedRadioButtonId3 = group_cadre.getCheckedRadioButtonId();
                    if (checkedRadioButtonId3 == -1) {
                        // No item selected
                        Toast toast = Toast.makeText(context, "No " + getResources().getString(R.string.health_cadre) + " Selected !", Toast.LENGTH_SHORT);
                        View views = toast.getView();
                        views.setBackgroundResource(R.drawable.round_conor);
                        TextView text = (TextView) views.findViewById(android.R.id.message);
                        toast.show();

                    } else {
                        int radioButtonID = group_cadre.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) group_cadre.findViewById(radioButtonID);
                        selected_cadre = (String) radioButton.getText();
                    }
                    int id = 0;
                    String district = spinner.getSelectedItem().toString().trim();
                    if (!district.equals("-- SELECT DISTRICT --")){

                    }else {
                        district = input_district.getText().toString().trim();
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    private void spinnerValue() {
        list = new ArrayList<>();
        list_id = new ArrayList<>();

        list.add("-- SELECT DISTRICT --");
        list_id.add(0);
        try{
            Cursor cursor = new District(context).selectDistrict();
            if (cursor.moveToFirst()){
                do {
                    list.add(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                    list_id.add(cursor.getInt(cursor.getColumnIndex(Constants.config.DISTRICT_ID)));
                }while (cursor.moveToNext());
            }

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter2);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showDiag(final String fname, final String lname, final String contact, final String gender, final String email, final String password, final String health, final String district2, final String selected_type, final String selected_ownership, final String selected_cadre){

       // new User(context).saveAll(fname,lname,contact,gender,email,password,health,district2,selected_type,selected_ownership,selected_cadre,0);

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setTitle("Signup Message Dialog");
        dialog.setMessage("Thank you for signing to HBB \n please login with your email "+email+" " );
        dialog.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Action for "Delete".
                 startActivity(new Intent(context,LoginActivity.class));
                finish();
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
