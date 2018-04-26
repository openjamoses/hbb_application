package com.example.john.hbb.activities.quizes;

import android.animation.ArgbEvaluator;
import android.content.Context;
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
import com.example.john.hbb.core.RadioModel;
import com.example.john.hbb.fragments.Quize_1;
import com.example.john.hbb.fragments.Quize_2;
import com.example.john.hbb.fragments.Quize_3;
import com.example.john.hbb.fragments.Quize_4;
import com.example.john.hbb.fragments.Quize_5;
import com.example.john.hbb.utils.Utils;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;


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
                Toast.makeText(context,"Q1="+ RadioModel.QUIZ_1+"\tQ2="+RadioModel.QUIZ_2+"\tQ3="+RadioModel.QUIZ_3+"\tQ4="+RadioModel.QUIZ_4+"\tQ5="+RadioModel.QUIZ_5+"\tQ6="+RadioModel.QUIZ_6, Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Q1="+ RadioModel.QUIZ_1+"\tQ2="+RadioModel.QUIZ_2+"\tQ3="+RadioModel.QUIZ_3+"\tQ4="+RadioModel.QUIZ_4+"\tQ5="+RadioModel.QUIZ_5+"\tQ6="+RadioModel.QUIZ_6);
                Log.e(TAG,"Q7="+ RadioModel.QUIZ_7+"\tQ8="+RadioModel.QUIZ_8+"\tQ9="+RadioModel.QUIZ_9+"\tQ10="+RadioModel.QUIZ_10+"\tQ11="+RadioModel.QUIZ_11);
                Log.e(TAG,"Q12="+ RadioModel.QUIZ_12+"\tQ23="+RadioModel.QUIZ_13+"\tQ14="+RadioModel.QUIZ_14+"\tQ15="+RadioModel.QUIZ_15+"\tQ16="+RadioModel.QUIZ_16);
                Log.e(TAG,"Q17="+ RadioModel.QUIZ_17+"\tQ18="+RadioModel.QUIZ_18+"\tQ19="+RadioModel.QUIZ_19+"\tQ20="+RadioModel.QUIZ_20+"\tQ21="+RadioModel.QUIZ_21);
                Log.e(TAG,"Q22="+ RadioModel.QUIZ_22+"\tQ23="+RadioModel.QUIZ_23+"\tQ24="+RadioModel.QUIZ_24+"\tQ25="+RadioModel.QUIZ_25+"\tQ26="+RadioModel.QUIZ_26);
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
                Log.e(TAG,"Q1="+ RadioModel.QUIZ_1+"\tQ2="+RadioModel.QUIZ_2+"\tQ3="+RadioModel.QUIZ_3+"\tQ4="+RadioModel.QUIZ_4+"\tQ5="+RadioModel.QUIZ_5+"\tQ6="+RadioModel.QUIZ_6);
                Log.e(TAG,"Q7="+ RadioModel.QUIZ_7+"\tQ8="+RadioModel.QUIZ_8+"\tQ9="+RadioModel.QUIZ_9+"\tQ10="+RadioModel.QUIZ_10+"\tQ11="+RadioModel.QUIZ_11);
                Log.e(TAG,"Q12="+ RadioModel.QUIZ_12+"\tQ23="+RadioModel.QUIZ_13+"\tQ14="+RadioModel.QUIZ_14+"\tQ15="+RadioModel.QUIZ_15+"\tQ16="+RadioModel.QUIZ_16);
                Log.e(TAG,"Q17="+ RadioModel.QUIZ_17+"\tQ18="+RadioModel.QUIZ_18+"\tQ19="+RadioModel.QUIZ_19+"\tQ20="+RadioModel.QUIZ_20+"\tQ21="+RadioModel.QUIZ_21);
                Log.e(TAG,"Q22="+ RadioModel.QUIZ_22+"\tQ23="+RadioModel.QUIZ_23+"\tQ24="+RadioModel.QUIZ_24+"\tQ25="+RadioModel.QUIZ_25+"\tQ26="+RadioModel.QUIZ_26);

                finish();
                //  update 1st time pref
                // Utils.saveSharedSetting(ApplyActivity_DEl.this, US_MainActivity.PREF_USER_FIRST_TIME, "false");

            }
        });

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
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("please wait..");
        pDialog.setCancelable(false);
        //pDialog.show();
        finish();
        /**
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
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
