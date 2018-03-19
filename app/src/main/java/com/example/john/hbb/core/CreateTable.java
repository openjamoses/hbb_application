package com.example.john.hbb.core;

/**
 * Created by john on 10/20/17.
 */

public class CreateTable {
    public abstract class config{
        public  static  final String CREATE_TRAINING_MODE =
                "CREATE TABLE "+ Constants.config.TABLE_TRAINING_MODE +" ("+ Constants.config.TRAININGID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.TRAINING_DATE+" TEXT,"+ Constants.config.TRAINING_TIME+" TEXT," +
                        " "+ Constants.config.TRAINING_FREQUENCY+" TEXT,"+ Constants.config.TRAINING_KEY_SKILL+" TEXT," +
                        " "+ Constants.config.TRAINING_SYNC_STATUS+" TEXT,"+ Constants.config.USER_ID+" INTEGER," +
                        " "+ Constants.config.TRAINING_NAME+" TEXT, "+ Constants.config.TRAINING_KEY_SUBSKILL+" TEXT," +
                        " "+Constants.config.TRAINING_ID+" INTEGER);";
        //Table for region
        public  static  final String CREATE_USER =
                "CREATE TABLE "+ Constants.config.TABLE_USERS +" ("+ Constants.config.USERID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.FIRST_NAME+" TEXT,"+ Constants.config.LAST_NAME+" TEXT, "+ Constants.config.CONTACT+" TEXT," +
                        " "+ Constants.config.HEALTH_ID+" INTEGER,"+ Constants.config.GENDER+" TEXT,"+Constants.config.FACILITY_NAME+" TEXT, " +
                        " "+ Constants.config.USERNAME+" TEXT,"+ Constants.config.PASSWORD+" TEXT, "+ Constants.config.USER_STATUS+" INTEGER," +
                        " "+ Constants.config.DISTRICT_NAME+" TEXT, "+Constants.config.HEALTH_FACILITY+" TEXT, "+ Constants.config.USER_ID+" INTEGER);";

        public  static  final String CREATE_HEALTH =
                "CREATE TABLE "+ Constants.config.TABLE_HEALTH +" ("+ Constants.config.HEALTHID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.HEALTH_ID+" INTEGER,"+ Constants.config.HEALTH_NAME+" TEXT, "+ Constants.config.HEALTH_CADRE+" TEXT," +
                        " "+ Constants.config.FACILITY_OWNER+" TEXT,"+ Constants.config.FACILITY_TYPE+" TEXT,"+Constants.config.DISTRICT_NAME+" TEXT, " +
                        " "+ Constants.config.HEALTH_STATUS+" INTEGER);";

        public  static  final String CREATE_SIMULATION =
                "CREATE TABLE "+ Constants.config.TABLE_SIMULATION +" ("+ Constants.config.SIMULATIONID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.SIMULATION_ID+" INTEGER,"+ Constants.config.SIMULATION_DATE+" TEXT, "+ Constants.config.SIMULATION_TIME+" TEXT," +
                        " "+ Constants.config.SIMULATION_CHECHLIST+" TEXT,"+ Constants.config.SKILL_DONE+" TEXT," +
                        " "+ Constants.config.SIMULATION_STATUS+" INTEGER);";

        public  static  final String CREATE_PREPARATION =
                "CREATE TABLE "+ Constants.config.TABLE_PREPARATION +" ("+ Constants.config.PREPARATIONID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.PREPARATION_ID+" INTEGER,"+ Constants.config.PREP_IDENTIFY_HELPER+" TEXT, "+ Constants.config.PREP_AREA_DELIVERY+" TEXT," +
                        " "+ Constants.config.PREP_WASHES_HANDS+" TEXT,"+ Constants.config.PREP_AREA_VENTILATION+" TEXT,"+ Constants.config.PREP_AREA_VENTILATION+" TEXT," +
                        " "+ Constants.config.PREP_ASSEMBLED+" TEXT,"+ Constants.config.PREP_TEST_VENTILATION+" TEXT,"+ Constants.config.PREP_UTEROTONIC+" TEXT," +
                        ""+ Constants.config.PREP_TIME+" TEXT,"+ Constants.config.LOG_ID+" TEXT," +
                        " "+ Constants.config.PREP_STATUS+" INTEGER);";


        public  static  final String CREATE_ROUTINE =
                "CREATE TABLE "+ Constants.config.TABLE_ROUTINE +" ("+ Constants.config.ROUTINEID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.ROUTINE_ID+" INTEGER,"+ Constants.config.ROUTINE_DRIES_THOROUGHY+" TEXT, "+ Constants.config.ROUTINE_RECOGNISE_CRYING+" TEXT," +
                        " "+ Constants.config.ROUTINE_CHECKS_BREATHING+" TEXT,"+ Constants.config.ROUTINE_CLAMPS+" TEXT,"+ Constants.config.ROUTINE_POSITION+" TEXT," +
                        " "+ Constants.config.ROUTINE_CONTINUE+" TEXT,"+ Constants.config.ROUTINE_TIME+" TEXT,"+Constants.config.ROUTINE_STATUS+" INTEGER);";


        public  static  final String CREATE_GMV =
                "CREATE TABLE "+ Constants.config.TABLE_GMV +" ("+ Constants.config.GMVID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.GMV_ID+" INTEGER,"+ Constants.config.GMV_DRIES_THOROUGHY+" TEXT, "+ Constants.config.GMV_RECOGNISE_NOTBREATHING+" TEXT," +
                        " "+ Constants.config.GMV_RECOGNISE_NOTCRYING+" TEXT,"+ Constants.config.GMV_KEEPS_WARM+" TEXT,"+ Constants.config.GMV_STIMULATES_BREATHING+" TEXT," +
                        " "+ Constants.config.GMV_FOLLOW_ROUTINE+" TEXT,"+ Constants.config.GMV_MOVE_VENTILATION+" TEXT,"+ Constants.config.GMV_VENTILATE+" TEXT," +
                        ""+ Constants.config.GMV_RECOGNISE_BREATHINGWELL+" TEXT,"+ Constants.config.LOG_ID+" INTEGER," +
                        " "+ Constants.config.PREP_STATUS+" INTEGER);";

    }
}
