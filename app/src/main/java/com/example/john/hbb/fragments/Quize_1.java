package com.example.john.hbb.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

public class Quize_1 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    Activity activity;
    private OnQuiz1Listener listener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        try {
            if (activity instanceof OnQuiz1Listener) {
                listener = (OnQuiz1Listener) activity;
            } else {
                throw new RuntimeException(activity.toString() + "" +
                        " Must implement OnFragmentInteractionListener");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public Quize_1() {

    }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Quize_1 newInstance(int sectionNumber) {
        Quize_1 fragment = new Quize_1();
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
        final View rootView = inflater.inflate(R.layout.quize_page_1, container, false);
        final RadioGroup quize_1 = (RadioGroup)rootView.findViewById(R.id.quize_1);
        final RadioGroup quize_2 = (RadioGroup)rootView.findViewById(R.id.quize_2);
        final RadioGroup quize_3 = (RadioGroup)rootView.findViewById(R.id.quize_3);
        final RadioGroup quize_4 = (RadioGroup)rootView.findViewById(R.id.quize_4);
        final RadioGroup quize_5 = (RadioGroup)rootView.findViewById(R.id.quize_5);
        final RadioGroup quize_6 = (RadioGroup)rootView.findViewById(R.id.quize_6);
        quize_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_1 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_2 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_3 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_4 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        quize_5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_5 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });

        quize_6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioModel.QUIZ_6 = ((RadioButton) rootView.findViewById(i)).getText().toString().trim();
            }
        });
        if (listener != null){
            listener.OnQuiz1Listener("","Desc Forms");
        }else {
            Log.e("TAG","Interface is Null");
        }
        return rootView;
    }

    public interface OnQuiz1Listener{
        void OnQuiz1Listener(String name, String desc);
    }
}