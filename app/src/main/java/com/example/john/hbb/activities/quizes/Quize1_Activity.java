package com.example.john.hbb.activities.quizes;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.john.hbb.R;
import com.example.john.hbb.core.DateTime;
import com.example.john.hbb.core.LogSession;
import com.example.john.hbb.core.Phone;
import com.example.john.hbb.core.RadioModel;
import com.example.john.hbb.core.UsersSession;
import com.example.john.hbb.db_operations.Quizes;
import com.example.john.hbb.fragments.Quize_1;
import com.example.john.hbb.fragments.Quize_2;
import com.example.john.hbb.fragments.Quize_3;
import com.example.john.hbb.fragments.Quize_4;
import com.example.john.hbb.fragments.Quize_5;
import com.example.john.hbb.utils.Utils;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by john on 2/28/18.
 */

public class Quize1_Activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    private Context context = this;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    ImageButton mNextBtn;
    Button mSkipBtn, mFinishBtn;

    ImageView zero,one,two,three,four;
    ImageView[] indicators;
    int lastLeftValue = 0;
    private static final String TAG = "Quize1_Activity";

    CoordinatorLayout mCoordinator;

    int page = 0;   //  to track page position

    //TODO LIST::
    List<String> q1_list = new ArrayList<>();
    List<String> q2_list = new ArrayList<>();
    List<String> q3_list = new ArrayList<>();
    List<String> q4_list = new ArrayList<>();
    List<String> q5_list = new ArrayList<>();
    List<String> q6_list = new ArrayList<>();
    List<String> q7_list = new ArrayList<>();
    List<String> q8_list = new ArrayList<>();
    List<String> q9_list = new ArrayList<>();
    List<String> q10_list = new ArrayList<>();
    List<String> q11_list = new ArrayList<>();
    List<String> q12_list = new ArrayList<>();
    List<String> q13_list = new ArrayList<>();
    List<String> q14_list = new ArrayList<>();
    List<String> q15_list = new ArrayList<>();
    List<String> q16_list = new ArrayList<>();
    List<String> q17_list = new ArrayList<>();
    List<String> q18_list = new ArrayList<>();
    List<String> q19_list = new ArrayList<>();
    List<String> q20_list = new ArrayList<>();
    List<String> q21_list = new ArrayList<>();
    List<String> q22_list = new ArrayList<>();
    List<String> q23_list = new ArrayList<>();
    List<String> q24_list = new ArrayList<>();
    List<String> q25_list = new ArrayList<>();
    List<String> q26_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       //TODO::: To be removed...!!!!!!
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        setContentView(R.layout.quize1_home);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mNextBtn = (ImageButton) findViewById(R.id.intro_btn_next);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
            mNextBtn.setImageDrawable(
                    Utils.tintMyDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_right_24dp), Color.WHITE)
            );

        mSkipBtn = (Button) findViewById(R.id.intro_btn_skip);
        mFinishBtn = (Button) findViewById(R.id.intro_btn_finish);

        //zero = (ImageView) findViewById(R.id.intro_indicator_0);
        zero = (ImageView) findViewById(R.id.intro_indicator_1);
        one = (ImageView) findViewById(R.id.intro_indicator_2);
        two = (ImageView) findViewById(R.id.intro_indicator_3);
        three = (ImageView) findViewById(R.id.intro_indicator_4);
        four = (ImageView) findViewById(R.id.intro_indicator_5);

        mCoordinator = (CoordinatorLayout) findViewById(R.id.main_content);


        indicators = new ImageView[]{zero,one,two,three,four};

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        mViewPager.setCurrentItem(page);
        updateIndicators(page);

        //final int color1 = ContextCompat.getColor(this, R.color.cyan);
        final int color1 = ContextCompat.getColor(this, R.color.gray_btn_bg_color);
        final int color2 = ContextCompat.getColor(this, R.color.gray_btn_bg_color);
        final int color3 = ContextCompat.getColor(this, R.color.gray_btn_bg_color);
        final int color4 = ContextCompat.getColor(this, R.color.gray_btn_bg_color);
        final int color5 = ContextCompat.getColor(this, R.color.gray_btn_bg_color);

        final int[] colorList = new int[]{color1,color2,color3,color4,color5};

        final ArgbEvaluator evaluator = new ArgbEvaluator();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                /*
                color update
                 */
                try {
                    int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 5 ? position : position + 1]);
                    mViewPager.setBackgroundColor(colorUpdate);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageSelected(int position) {

                page = position;

                updateIndicators(page);

                switch (position) {
                    case 0:
                        mViewPager.setBackgroundColor(color1);
                        break;
                    case 1:
                        mViewPager.setBackgroundColor(color2);
                        break;
                    case 2:
                        mViewPager.setBackgroundColor(color3);
                        break;
                    case 3:
                        mViewPager.setBackgroundColor(color4);
                        break;
                    case 4:
                        mViewPager.setBackgroundColor(color5);
                        break;
                }
                mNextBtn.setVisibility(position == 4 ? View.GONE : View.VISIBLE);
                mFinishBtn.setVisibility(position == 4 ? View.VISIBLE : View.GONE);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page += 1;
                mViewPager.setCurrentItem(page, true);
                mSkipBtn.setText("<< Back");
                //Toast.makeText(context,"Q1="+ RadioModel.QUIZ_1+"\tQ2="+RadioModel.QUIZ_2+"\tQ3="+RadioModel.QUIZ_3+"\tQ4="+RadioModel.QUIZ_4+"\tQ5="+RadioModel.QUIZ_5+"\tQ6="+RadioModel.QUIZ_6, Toast.LENGTH_SHORT).show();
                //Log.e(TAG,"Q1="+ RadioModel.QUIZ_1+"\tQ2="+RadioModel.QUIZ_2+"\tQ3="+RadioModel.QUIZ_3+"\tQ4="+RadioModel.QUIZ_4+"\tQ5="+RadioModel.QUIZ_5+"\tQ6="+RadioModel.QUIZ_6);
                //Log.e(TAG,"Q7="+ RadioModel.QUIZ_7+"\tQ8="+RadioModel.QUIZ_8+"\tQ9="+RadioModel.QUIZ_9+"\tQ10="+RadioModel.QUIZ_10+"\tQ11="+RadioModel.QUIZ_11);
                //Log.e(TAG,"Q12="+ RadioModel.QUIZ_12+"\tQ23="+RadioModel.QUIZ_13+"\tQ14="+RadioModel.QUIZ_14+"\tQ15="+RadioModel.QUIZ_15+"\tQ16="+RadioModel.QUIZ_16);
                //Log.e(TAG,"Q17="+ RadioModel.QUIZ_17+"\tQ18="+RadioModel.QUIZ_18+"\tQ19="+RadioModel.QUIZ_19+"\tQ20="+RadioModel.QUIZ_20+"\tQ21="+RadioModel.QUIZ_21);
                //Log.e(TAG,"Q22="+ RadioModel.QUIZ_22+"\tQ23="+RadioModel.QUIZ_23+"\tQ24="+RadioModel.QUIZ_24+"\tQ25="+RadioModel.QUIZ_25+"\tQ26="+RadioModel.QUIZ_26);
            }
        });

        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (page > 0){
                    page -= 1;
                    mViewPager.setCurrentItem(page, true);
                    if (page == 0){
                        mSkipBtn.setText("Cancel");
                    }
                }else {

                    show();
                    //finish();
                }

            }
        });

        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                Log.e(TAG,"Q1="+ RadioModel.QUIZ_1+"\tQ2="+RadioModel.QUIZ_2+"\tQ3="+RadioModel.QUIZ_3+"\tQ4="+RadioModel.QUIZ_4+"\tQ5="+RadioModel.QUIZ_5+"\tQ6="+RadioModel.QUIZ_6);
                Log.e(TAG,"Q7="+ RadioModel.QUIZ_7+"\tQ8="+RadioModel.QUIZ_8+"\tQ9="+RadioModel.QUIZ_9+"\tQ10="+RadioModel.QUIZ_10+"\tQ11="+RadioModel.QUIZ_11);
                Log.e(TAG,"Q12="+ RadioModel.QUIZ_12+"\tQ23="+RadioModel.QUIZ_13+"\tQ14="+RadioModel.QUIZ_14+"\tQ15="+RadioModel.QUIZ_15+"\tQ16="+RadioModel.QUIZ_16);
                Log.e(TAG,"Q17="+ RadioModel.QUIZ_17+"\tQ18="+RadioModel.QUIZ_18+"\tQ19="+RadioModel.QUIZ_19+"\tQ20="+RadioModel.QUIZ_20+"\tQ21="+RadioModel.QUIZ_21);
                Log.e(TAG,"Q22="+ RadioModel.QUIZ_22+"\tQ23="+RadioModel.QUIZ_23+"\tQ24="+RadioModel.QUIZ_24+"\tQ25="+RadioModel.QUIZ_25+"\tQ26="+RadioModel.QUIZ_26);
                 **/

                if (!RadioModel.QUIZ_1.equals("") && !RadioModel.QUIZ_2.equals("") && !RadioModel.QUIZ_3.equals("") && !RadioModel.QUIZ_4.equals("") && !RadioModel.QUIZ_5.equals("") && !RadioModel.QUIZ_6.equals("")
                        && !RadioModel.QUIZ_7.equals("") && !RadioModel.QUIZ_8.equals("") && !RadioModel.QUIZ_9.equals("") && !RadioModel.QUIZ_10.equals("") && !RadioModel.QUIZ_11.equals("")
                        && !RadioModel.QUIZ_12.equals("") && !RadioModel.QUIZ_13.equals("") && !RadioModel.QUIZ_14.equals("") && !RadioModel.QUIZ_15.equals("") && !RadioModel.QUIZ_16.equals("")
                        && !RadioModel.QUIZ_17.equals("") && !RadioModel.QUIZ_18.equals("") && !RadioModel.QUIZ_19.equals("") && !RadioModel.QUIZ_20.equals("") && !RadioModel.QUIZ_21.equals("")
                        && !RadioModel.QUIZ_22.equals("") && !RadioModel.QUIZ_23.equals("") && !RadioModel.QUIZ_24.equals("") && !RadioModel.QUIZ_25.equals("") && !RadioModel.QUIZ_26.equals("") ){

                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Well done..!")
                            .setContentText("You have completed all the quizes. Chose submit,or Back")
                            .setConfirmText("Yes,Submit!")
                            .setCancelText("BACK")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    new Quizes(context).send(DateTime.getCurrentDate(),DateTime.getCurrentTime(),new UsersSession(context).getUserID(),new UsersSession(context).getFname()+" "+new UsersSession(context).getLname()
                                    ,Phone.getIMEI(context), new LogSession(context).getLog_type(),q1_list.indexOf(RadioModel.QUIZ_1),q2_list.indexOf(RadioModel.QUIZ_2),q3_list.indexOf(RadioModel.QUIZ_3),q4_list.indexOf(RadioModel.QUIZ_4)
                                    ,q5_list.indexOf(RadioModel.QUIZ_5),q6_list.indexOf(RadioModel.QUIZ_6),q7_list.indexOf(RadioModel.QUIZ_7),q8_list.indexOf(RadioModel.QUIZ_8),q9_list.indexOf(RadioModel.QUIZ_9)
                                    ,q10_list.indexOf(RadioModel.QUIZ_10),q11_list.indexOf(RadioModel.QUIZ_11),q12_list.indexOf(RadioModel.QUIZ_12),q13_list.indexOf(RadioModel.QUIZ_13),q14_list.indexOf(RadioModel.QUIZ_14)
                                    ,q15_list.indexOf(RadioModel.QUIZ_15),q16_list.indexOf(RadioModel.QUIZ_16),q17_list.indexOf(RadioModel.QUIZ_17),q18_list.indexOf(RadioModel.QUIZ_18)
                                    ,q19_list.indexOf(RadioModel.QUIZ_19),q20_list.indexOf(RadioModel.QUIZ_20),q21_list.indexOf(RadioModel.QUIZ_21),q22_list.indexOf(RadioModel.QUIZ_22)
                                    ,q23_list.indexOf(RadioModel.QUIZ_23),q24_list.indexOf(RadioModel.QUIZ_24),q25_list.indexOf(RadioModel.QUIZ_25),q26_list.indexOf(RadioModel.QUIZ_26),new LogSession(context).getID());
                                    sDialog.dismissWithAnimation();
                                    finish();
                                    RadioModel.QUIZ_1 = "";
                                    RadioModel.QUIZ_2 = "";
                                    RadioModel.QUIZ_3 = "";
                                    RadioModel.QUIZ_4 = "";
                                    RadioModel.QUIZ_5 = "";
                                    RadioModel.QUIZ_6 = "";
                                    RadioModel.QUIZ_7 = "";
                                    RadioModel.QUIZ_8 = "";
                                    RadioModel.QUIZ_9 = "";
                                    RadioModel.QUIZ_10 = "";
                                    RadioModel.QUIZ_11 = "";
                                    RadioModel.QUIZ_12 = "";
                                    RadioModel.QUIZ_13 = "";
                                    RadioModel.QUIZ_14 = "";
                                    RadioModel.QUIZ_15 = "";
                                    RadioModel.QUIZ_16 = "";
                                    RadioModel.QUIZ_17 = "";
                                    RadioModel.QUIZ_18 = "";
                                    RadioModel.QUIZ_19 = "";
                                    RadioModel.QUIZ_20 = "";
                                    RadioModel.QUIZ_21 = "";
                                    RadioModel.QUIZ_22 = "";
                                    RadioModel.QUIZ_23 = "";
                                    RadioModel.QUIZ_24 = "";
                                    RadioModel.QUIZ_25 = "";
                                    RadioModel.QUIZ_26 = "";
                                }
                            })

                            .show();


                }else {
                    String QUESTIONS = "";
                    int count = 0;
                    if (RadioModel.QUIZ_1.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.1 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_2.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.2 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_3.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.3 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_4.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.4 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_5.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.5 , ");
                        count ++;
                    }if (RadioModel.QUIZ_6.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.6 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_7.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.7 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_8.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.8 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_9.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.9 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_10.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.10 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_11.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.11 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_12.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.12 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_13.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.13 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_14.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.14 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_15.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.15 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_16.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.16 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_17.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.17 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_18.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.18 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_19.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.19 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_20.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.20 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_21.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.21 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_22.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.22 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_23.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.23 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_24.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.24 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_25.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.25 , ");
                        count ++;
                    }
                    if (RadioModel.QUIZ_26.equals("")){
                        QUESTIONS = QUESTIONS.concat("Qn.26 , ");
                        count ++;
                    }

                    String icomplete = "";
                    if (count == 1){
                        icomplete = count+" Question";
                    }else {
                        icomplete = count+" Questions";
                    }
                    final String finalQUESTIONS = QUESTIONS;
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(""+icomplete+" not attempted!")
                            .setContentText("Sory, We detected "+icomplete+" not attempted!. Click YES, To View Unanswered quiz numbers..")
                            .setConfirmText("YES,VIEW")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog
                                            .setTitleText("Unattempted!")
                                            .setContentText(finalQUESTIONS)
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                }
                            })
                            .show();

                }


                //  update 1st time pref
                // Utils.saveSharedSetting(ApplyActivity_DEl.this, US_MainActivity.PREF_USER_FIRST_TIME, "false");

            }
        });
        //TODO::::
        setList();
    }

    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a SelectFragment (defined as a static inner class below).
            if (position == 0){
                return Quize_1.newInstance(position + 1);
            }else if (position == 1){
                return Quize_2.newInstance(position + 1);
            }else if (position == 2){
                return Quize_3.newInstance(position + 1);
            }else if (position == 3){
                return Quize_4.newInstance(position + 1);
            }else if (position == 4){
                return Quize_5.newInstance(position + 1);
            }else {
                return Quize_1.newInstance(position + 1);
            }


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
                case 4:
                    return "SECTION 5";
            }
            return null;
        }
    }

    private void show(){
        String QUESTIONS = "";
        int count = 0;
        if (RadioModel.QUIZ_1.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.1 , ");
            count ++;
        }
        if (RadioModel.QUIZ_2.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.2 , ");
            count ++;
        }
        if (RadioModel.QUIZ_3.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.3 , ");
            count ++;
        }
        if (RadioModel.QUIZ_4.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.4 , ");
            count ++;
        }
        if (RadioModel.QUIZ_5.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.5 , ");
            count ++;
        }if (RadioModel.QUIZ_6.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.6 , ");
            count ++;
        }
        if (RadioModel.QUIZ_7.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.7 , ");
            count ++;
        }
        if (RadioModel.QUIZ_8.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.8 , ");
            count ++;
        }
        if (RadioModel.QUIZ_9.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.9 , ");
            count ++;
        }
        if (RadioModel.QUIZ_10.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.10 , ");
            count ++;
        }
        if (RadioModel.QUIZ_11.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.11 , ");
            count ++;
        }
        if (RadioModel.QUIZ_12.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.12 , ");
            count ++;
        }
        if (RadioModel.QUIZ_13.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.13 , ");
            count ++;
        }
        if (RadioModel.QUIZ_14.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.14 , ");
            count ++;
        }
        if (RadioModel.QUIZ_15.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.15 , ");
            count ++;
        }
        if (RadioModel.QUIZ_16.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.16 , ");
            count ++;
        }
        if (RadioModel.QUIZ_17.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.17 , ");
            count ++;
        }
        if (RadioModel.QUIZ_18.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.18 , ");
            count ++;
        }
        if (RadioModel.QUIZ_19.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.19 , ");
            count ++;
        }
        if (RadioModel.QUIZ_20.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.20 , ");
            count ++;
        }
        if (RadioModel.QUIZ_21.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.21 , ");
            count ++;
        }
        if (RadioModel.QUIZ_22.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.22 , ");
            count ++;
        }
        if (RadioModel.QUIZ_23.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.23 , ");
            count ++;
        }
        if (RadioModel.QUIZ_24.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.24 , ");
            count ++;
        }
        if (RadioModel.QUIZ_25.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.25 , ");
            count ++;
        }
        if (RadioModel.QUIZ_26.equals("")){
            QUESTIONS = QUESTIONS.concat("Qn.26 , ");
            count ++;
        }

        String icomplete = "";
        if (count == 1){
            icomplete = count+" Question";
        }else {
            icomplete = count+" Questions";
        }

        if (count < 26) {
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Discard all?")
                    .setContentText("Click Back to return to the quizes..")
                    .setConfirmText("YES,Discard")
                    .setCancelText("BACK")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })
                    //.setCanceledOnTouchOutside(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            RadioModel.QUIZ_1 = "";
                            RadioModel.QUIZ_2 = "";
                            RadioModel.QUIZ_3 = "";
                            RadioModel.QUIZ_4 = "";
                            RadioModel.QUIZ_5 = "";
                            RadioModel.QUIZ_6 = "";
                            RadioModel.QUIZ_7 = "";
                            RadioModel.QUIZ_8 = "";
                            RadioModel.QUIZ_9 = "";
                            RadioModel.QUIZ_10 = "";
                            RadioModel.QUIZ_11 = "";
                            RadioModel.QUIZ_12 = "";
                            RadioModel.QUIZ_13 = "";
                            RadioModel.QUIZ_14 = "";
                            RadioModel.QUIZ_15 = "";
                            RadioModel.QUIZ_16 = "";
                            RadioModel.QUIZ_17 = "";
                            RadioModel.QUIZ_18 = "";
                            RadioModel.QUIZ_19 = "";
                            RadioModel.QUIZ_20 = "";
                            RadioModel.QUIZ_21 = "";
                            RadioModel.QUIZ_22 = "";
                            RadioModel.QUIZ_23 = "";
                            RadioModel.QUIZ_24 = "";
                            RadioModel.QUIZ_25 = "";
                            RadioModel.QUIZ_26 = "";
                            finish();
                        }
                    })
                    .show();
        }else {
            finish();
        }
    }

    private void setList(){
        q1_list = new ArrayList<>();
        q1_list.add(context.getResources().getString(R.string.quiz_1_a));
        q1_list.add(context.getResources().getString(R.string.quiz_1_b));
        q1_list.add(context.getResources().getString(R.string.quiz_1_c));
        q1_list.add(context.getResources().getString(R.string.quiz_1_d));

        q2_list = new ArrayList<>();
        q2_list.add(context.getResources().getString(R.string.quiz_2_a));
        q2_list.add(context.getResources().getString(R.string.quiz_2_b));
        q2_list.add(context.getResources().getString(R.string.quiz_2_c));
        q2_list.add(context.getResources().getString(R.string.quiz_2_d));

        q3_list = new ArrayList<>();
        q3_list.add(context.getResources().getString(R.string.quiz_3_a));
        q3_list.add(context.getResources().getString(R.string.quiz_3_b));
        q3_list.add(context.getResources().getString(R.string.quiz_3_c));
        q3_list.add(context.getResources().getString(R.string.quiz_3_d));

        q4_list = new ArrayList<>();
        q4_list.add(context.getResources().getString(R.string.quiz_4_a));
        q4_list.add(context.getResources().getString(R.string.quiz_4_b));
        q4_list.add(context.getResources().getString(R.string.quiz_4_c));
        q4_list.add(context.getResources().getString(R.string.quiz_4_d));

        q5_list = new ArrayList<>();
        q5_list.add(context.getResources().getString(R.string.quiz_5_a));
        q5_list.add(context.getResources().getString(R.string.quiz_5_b));
        q5_list.add(context.getResources().getString(R.string.quiz_5_c));
        q5_list.add(context.getResources().getString(R.string.quiz_5_d));

        q6_list = new ArrayList<>();
        q6_list.add(context.getResources().getString(R.string.quiz_6_a));
        q6_list.add(context.getResources().getString(R.string.quiz_6_b));
        q6_list.add(context.getResources().getString(R.string.quiz_6_c));
        q6_list.add(context.getResources().getString(R.string.quiz_6_d));

        q7_list = new ArrayList<>();
        q7_list.add(context.getResources().getString(R.string.quiz_7_a));
        q7_list.add(context.getResources().getString(R.string.quiz_7_b));
        q7_list.add(context.getResources().getString(R.string.quiz_7_c));
        q7_list.add(context.getResources().getString(R.string.quiz_7_d));

        q8_list = new ArrayList<>();
        q8_list.add(context.getResources().getString(R.string.quiz_8_a));
        q8_list.add(context.getResources().getString(R.string.quiz_8_b));
        q8_list.add(context.getResources().getString(R.string.quiz_8_c));
        q8_list.add(context.getResources().getString(R.string.quiz_8_d));

        q9_list = new ArrayList<>();
        q9_list.add(context.getResources().getString(R.string.quiz_9_a));
        q9_list.add(context.getResources().getString(R.string.quiz_9_b));
        q9_list.add(context.getResources().getString(R.string.quiz_9_c));
        q9_list.add(context.getResources().getString(R.string.quiz_9_d));

        q10_list = new ArrayList<>();
        q10_list.add(context.getResources().getString(R.string.quiz_10_a));
        q10_list.add(context.getResources().getString(R.string.quiz_10_b));
        q10_list.add(context.getResources().getString(R.string.quiz_10_c));
        q10_list.add(context.getResources().getString(R.string.quiz_10_d));

        q11_list = new ArrayList<>();
        q11_list.add(context.getResources().getString(R.string.quiz_11_a));
        q11_list.add(context.getResources().getString(R.string.quiz_11_b));
        q11_list.add(context.getResources().getString(R.string.quiz_11_c));
        q11_list.add(context.getResources().getString(R.string.quiz_11_d));

        q12_list = new ArrayList<>();
        q12_list.add(context.getResources().getString(R.string.quiz_12_a));
        q12_list.add(context.getResources().getString(R.string.quiz_12_b));
        q12_list.add(context.getResources().getString(R.string.quiz_12_c));
        q12_list.add(context.getResources().getString(R.string.quiz_12_d));

        q13_list = new ArrayList<>();
        q13_list.add(context.getResources().getString(R.string.quiz_13_a));
        q13_list.add(context.getResources().getString(R.string.quiz_13_b));
        q13_list.add(context.getResources().getString(R.string.quiz_13_c));
        q13_list.add(context.getResources().getString(R.string.quiz_13_d));

        q14_list = new ArrayList<>();
        q14_list.add(context.getResources().getString(R.string.quiz_14_a));
        q14_list.add(context.getResources().getString(R.string.quiz_14_b));
        q14_list.add(context.getResources().getString(R.string.quiz_14_c));
        q14_list.add(context.getResources().getString(R.string.quiz_14_d));

        q15_list = new ArrayList<>();
        q15_list.add(context.getResources().getString(R.string.quiz_15_a));
        q15_list.add(context.getResources().getString(R.string.quiz_15_b));
        q15_list.add(context.getResources().getString(R.string.quiz_15_c));
        q15_list.add(context.getResources().getString(R.string.quiz_15_d));

        q16_list = new ArrayList<>();
        q16_list.add(context.getResources().getString(R.string.quiz_16_a));
        q16_list.add(context.getResources().getString(R.string.quiz_16_b));
        q16_list.add(context.getResources().getString(R.string.quiz_16_c));
        q16_list.add(context.getResources().getString(R.string.quiz_16_d));

        q17_list = new ArrayList<>();
        q17_list.add(context.getResources().getString(R.string.quiz_17_a));
        q17_list.add(context.getResources().getString(R.string.quiz_17_b));
        q17_list.add(context.getResources().getString(R.string.quiz_17_c));
        q17_list.add(context.getResources().getString(R.string.quiz_17_d));

        q18_list = new ArrayList<>();
        q18_list.add(context.getResources().getString(R.string.quiz_18_a));
        q18_list.add(context.getResources().getString(R.string.quiz_18_b));
        q18_list.add(context.getResources().getString(R.string.quiz_18_c));
        q18_list.add(context.getResources().getString(R.string.quiz_18_d));

        q19_list = new ArrayList<>();
        q19_list.add(context.getResources().getString(R.string.quiz_19_a));
        q19_list.add(context.getResources().getString(R.string.quiz_19_b));
        q19_list.add(context.getResources().getString(R.string.quiz_19_c));
        q19_list.add(context.getResources().getString(R.string.quiz_19_d));

        q20_list = new ArrayList<>();
        q20_list.add(context.getResources().getString(R.string.quiz_20_a));
        q20_list.add(context.getResources().getString(R.string.quiz_20_b));
        q20_list.add(context.getResources().getString(R.string.quiz_20_c));
        q20_list.add(context.getResources().getString(R.string.quiz_20_d));

        q21_list = new ArrayList<>();
        q21_list.add(context.getResources().getString(R.string.quiz_21_a));
        q21_list.add(context.getResources().getString(R.string.quiz_21_b));
        q21_list.add(context.getResources().getString(R.string.quiz_21_c));
        q21_list.add(context.getResources().getString(R.string.quiz_21_d));

        q22_list = new ArrayList<>();
        q22_list.add(context.getResources().getString(R.string.quiz_22_a));
        q22_list.add(context.getResources().getString(R.string.quiz_22_b));
        q22_list.add(context.getResources().getString(R.string.quiz_22_c));
        q22_list.add(context.getResources().getString(R.string.quiz_22_d));

        q23_list = new ArrayList<>();
        q23_list.add(context.getResources().getString(R.string.quiz_23_a));
        q23_list.add(context.getResources().getString(R.string.quiz_23_b));
        q23_list.add(context.getResources().getString(R.string.quiz_23_c));
        q23_list.add(context.getResources().getString(R.string.quiz_23_d));

        q24_list = new ArrayList<>();
        q24_list.add(context.getResources().getString(R.string.quiz_24_a));
        q24_list.add(context.getResources().getString(R.string.quiz_24_b));
        q24_list.add(context.getResources().getString(R.string.quiz_24_c));
        q24_list.add(context.getResources().getString(R.string.quiz_24_d));

        q25_list = new ArrayList<>();
        q25_list.add(context.getResources().getString(R.string.quiz_25_a));
        q25_list.add(context.getResources().getString(R.string.quiz_25_b));
        q25_list.add(context.getResources().getString(R.string.quiz_25_c));
        q25_list.add(context.getResources().getString(R.string.quiz_25_d));

        q26_list = new ArrayList<>();
        q26_list.add(context.getResources().getString(R.string.quiz_26_a));
        q26_list.add(context.getResources().getString(R.string.quiz_26_b));
        q26_list.add(context.getResources().getString(R.string.quiz_26_c));
        q26_list.add(context.getResources().getString(R.string.quiz_26_d));
    }
}
