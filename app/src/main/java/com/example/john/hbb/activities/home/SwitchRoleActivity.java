package com.example.john.hbb.activities.home;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.john.hbb.R;
import com.example.john.hbb.core.SessionManager;



/**
 * Created by john on 3/26/18.
 */

public class SwitchRoleActivity extends Activity{
    private Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);

        showDialog();
    }

    private void showDialog(){
        final AlertDialog dialog;
        try{

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setIcon(getResources().getDrawable(android.R.drawable.stat_sys_warning));
            alert.setTitle("DO YOU WANT TO..");

            LayoutInflater inflater = getLayoutInflater();
            final View alertLayout = inflater.inflate(R.layout.switch_dialog, null);
            // this is set the view from XML inside AlertDialog
            alert.setView(alertLayout);
            // this is set the view from XML inside AlertDialog
            //alert.setMessage("  Switch role?");
            // disallow cancel of AlertDialog on click of back button and outside touch
            alert.setCancelable(false);
            //alert.setIcon(getResources().getDrawable(android.R.drawable.checkbox_on_background));

            alert.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            alert.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });
            dialog = alert.create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    startActivity(new Intent(context,Menu_Dashboard.class));
                }
            });
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    finish();
                    new SessionManager(context).logoutUser();
                    startActivity(new Intent(context,LoginActivity.class));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void show(){
        /**
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog
                                .setTitleText("Deleted!")
                                .setContentText("Your imaginary file has been deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();

         **/
    }
}
