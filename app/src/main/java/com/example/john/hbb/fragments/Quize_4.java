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

public class Quize_4 extends Fragment {
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
    public Quize_4() {

    }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Quize_4 newInstance(int sectionNumber) {
        Quize_4 fragment = new Quize_4();
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
        final View rootView = inflater.inflate(R.layout.quize_page_4, container, false);
        final RadioGroup quize_17 = (RadioGroup)rootView.findViewById(R.id.quize_17);
        final RadioGroup quize_18 = (RadioGroup)rootView.findViewById(R.id.quize_18);
        final RadioGroup quize_19 = (RadioGroup)rootView.findViewById(R.id.quize_19);
        final RadioGroup quize_20 = (RadioGroup)rootView.findViewById(R.id.quize_20);
        final RadioGroup quize_21 = (RadioGroup)rootView.findViewById(R.id.quize_21);

        quize_17.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_17 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_18.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_18 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_19.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_19 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_20.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_20 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_21.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_21 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });

        return rootView;
    }
}