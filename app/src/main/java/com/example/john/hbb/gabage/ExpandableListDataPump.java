package com.example.john.hbb.gabage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by john on 7/7/17.
 */

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> preperation = new ArrayList<String>();
        preperation.add("Hand Washing");
        preperation.add("Testing Equipments");
        preperation.add("Combined Video");


        List<String> routine = new ArrayList<String>();
        routine.add("Drying Thouroughly");
        routine.add("Clamping and Cutting Card");
        routine.add("Combined Video");


        List<String> golden_without = new ArrayList<String>();
        golden_without.add("Sunctioning");
        golden_without.add("Stimulation");
        golden_without.add("Combined Video");


        List<String> golden_with = new ArrayList<String>();
        golden_with.add("Key Steps");


        List<String> ventilation = new ArrayList<String>();
        ventilation.add(" ");

        expandableListDetail.put("PREPERATION FOR BIRTH", preperation);
        expandableListDetail.put("ROUTINE CARE", routine);
        expandableListDetail.put("GOLDEN MINUTE WITHOUT VENTILATION", golden_without);
        expandableListDetail.put("GOLDEN MINUTE WITH VENTILATION", golden_with);
        expandableListDetail.put("VENTILATION WITH SLOW OR FAST HEART RATE", ventilation);
        return expandableListDetail;
    }
}