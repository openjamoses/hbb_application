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

public class Quize_2 extends Fragment {
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
    public Quize_2() {

    }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Quize_2 newInstance(int sectionNumber) {
        Quize_2 fragment = new Quize_2();
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
        final View rootView = inflater.inflate(R.layout.quize_page_2, container, false);
        final RadioGroup quize_7 = (RadioGroup)rootView.findViewById(R.id.quize_7);
        final RadioGroup quize_8 = (RadioGroup)rootView.findViewById(R.id.quize_8);
        final RadioGroup quize_9 = (RadioGroup)rootView.findViewById(R.id.quize_9);
        final RadioGroup quize_10 = (RadioGroup)rootView.findViewById(R.id.quize_10);
        final RadioGroup quize_11 = (RadioGroup)rootView.findViewById(R.id.quize_11);

        quize_7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_7 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_8 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_9 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_10 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_11 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });

        return rootView;
    }
}