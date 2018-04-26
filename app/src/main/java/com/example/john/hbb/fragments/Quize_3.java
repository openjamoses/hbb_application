package com.example.john.hbb.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.john.hbb.R;
import com.example.john.hbb.core.RadioModel;

/**
 * Created by john on 4/25/18.
 */

public class Quize_3 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    Activity activity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;

    }
    public Quize_3() {

    }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Quize_3 newInstance(int sectionNumber) {
        Quize_3 fragment = new Quize_3();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.quize_page_3, container, false);
        final RadioGroup quize_12 = (RadioGroup)rootView.findViewById(R.id.quize_12);
        final RadioGroup quize_13 = (RadioGroup)rootView.findViewById(R.id.quize_13);
        final RadioGroup quize_14 = (RadioGroup)rootView.findViewById(R.id.quize_14);
        final RadioGroup quize_15 = (RadioGroup)rootView.findViewById(R.id.quize_15);
        final RadioGroup quize_16 = (RadioGroup)rootView.findViewById(R.id.quize_16);

        quize_12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_12 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_13.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_13 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_14.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_14 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_15.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_15 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_16.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_16 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });

        return rootView;
    }
}