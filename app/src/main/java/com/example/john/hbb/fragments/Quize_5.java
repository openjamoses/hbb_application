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

public class Quize_5 extends Fragment {
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
    public Quize_5() {

    }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Quize_5 newInstance(int sectionNumber) {
        Quize_5 fragment = new Quize_5();
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
        final View rootView = inflater.inflate(R.layout.quize_page_5, container, false);
        final RadioGroup quize_22 = (RadioGroup)rootView.findViewById(R.id.quize_22);
        final RadioGroup quize_23 = (RadioGroup)rootView.findViewById(R.id.quize_23);
        final RadioGroup quize_24 = (RadioGroup)rootView.findViewById(R.id.quize_24);
        final RadioGroup quize_25 = (RadioGroup)rootView.findViewById(R.id.quize_25);
        final RadioGroup quize_26 = (RadioGroup)rootView.findViewById(R.id.quize_26);

        quize_22.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_22 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_23.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_23 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_24.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_24 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_25.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_25 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_26.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_26 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });

        return rootView;
    }
}