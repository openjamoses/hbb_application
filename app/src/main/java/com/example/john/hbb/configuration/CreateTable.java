package com.example.john.hbb.configuration;

/**
 * Created by john on 10/20/17.
 */

public class CreateTable {
    public abstract class config{
        public  static  final String CREATE_TRAINING_MODE =
                "CREATE TABLE "+ Constants.config.TABLE_TRAINING_MODE +" ("+ Constants.config.TRAINING_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.TRAINING_DATE+" TEXT,"+ Constants.config.TRAINING_TIME+" TEXT," +
                        " "+ Constants.config.TRAINING_FREQUENCY+" TEXT,"+ Constants.config.TRAINING_KEY_SKILL+" TEXT," +
                        " "+ Constants.config.TRAINING_SYNC_STATUS+" TEXT,"+ Constants.config.USER_ID+" INTEGER," +
                        " "+ Constants.config.TRAINING_NAME+" TEXT, "+ Constants.config.TRAINING_KEY_SUBSKILL+" TEXT);";
        //Table for region
        public  static  final String CREATE_USER =
                "CREATE TABLE "+ Constants.config.TABLE_USERS +" ("+ Constants.config.USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.FIRST_NAME+" TEXT,"+ Constants.config.LAST_NAME+" TEXT, "+ Constants.config.CONTACT+" TEXT," +
                        " "+ Constants.config.HEALTH_FACILITY+" TEXT,"+ Constants.config.GENDER+" TEXT," +
                        " "+ Constants.config.EMAIL+" TEXT,"+ Constants.config.PASSWORD+" TEXT, "+ Constants.config.USER_STATUS+" INTEGER," +
                        " "+ Constants.config.DISTRICT_NAME+" TEXT, "+Constants.config.FACILITY_TYPE+" TEXT," +
                        " "+Constants.config.FACILITY_OWNER+" TEXT, "+Constants.config.HEALTH_CADRE+" TEXT," +
                        " "+Constants.config.VERIFIED_STATUS+" INTEGER );";

        public  static  final String CREATE_DISTRICT =
                "CREATE TABLE "+ Constants.config.TABLE_DISTRICT +" ("+ Constants.config.DISTRICT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.DISTRICT_NAME+" TEXT, "+Constants.config.DISTRICT_DATE_TIME+" TEXT, "+Constants.config.FETCH_STATUS+" INTEGER );";

    }
}
