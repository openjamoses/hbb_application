package com.example.john.hbb.core;

/**
 * Created by john on 10/20/17.
 */

public class CreateTable {
    public abstract class config{
        public  static  final String CREATE_TRAINING_MODE =
                "CREATE TABLE "+ Constants.config.TABLE_TRAINING +" ("+ Constants.config.TRAININGID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.TRAINING_DATE+" TEXT,"+ Constants.config.TRAINING_TIME+" TEXT, "+Constants.config.TRAINING_NAME+" TEXT, " +
                        " "+ Constants.config.TRAINING_FREQUENCY+" TEXT,"+ Constants.config.TRAINING_KEY_SKILL+" INTEGER," +
                        " "+ Constants.config.TRAINING_SYNC_STATUS+" TEXT,"+ Constants.config.USER_ID+" INTEGER," +
                        " "+ Constants.config.TRAINING_KEY_SUBSKILL+" INTEGER,"+Constants.config.VIDEO_COMPLETED+" INTEGER, " +
                        " "+Constants.config.TRAINING_ID+" INTEGER, "+Constants.config.TRAINING_IMEI+" TEXT, "+Constants.config.LOG_ID+" INTEGER," +
                        " "+Constants.config.LOG_TYPE+" INTEGER);";
        //Table for region
        public  static  final String CREATE_USER =
                "CREATE TABLE "+ Constants.config.TABLE_USERS +" ("+ Constants.config.USERID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.FIRST_NAME+" TEXT,"+ Constants.config.LAST_NAME+" TEXT, "+ Constants.config.CONTACT+" TEXT," +
                        " "+ Constants.config.HEALTH_ID+" INTEGER,"+ Constants.config.GENDER+" TEXT,"+Constants.config.FACILITY_NAME+" TEXT, " +
                        " "+ Constants.config.USERNAME+" TEXT,"+ Constants.config.PASSWORD+" TEXT, "+ Constants.config.USER_STATUS+" INTEGER," +
                        " "+ Constants.config.DISTRICT_NAME+" TEXT, "+Constants.config.HEALTH_FACILITY+" TEXT, "+ Constants.config.USER_ID+" INTEGER," +
                        " "+Constants.config.IMEI+" TEXT);";

        public  static  final String CREATE_HEALTH =
                "CREATE TABLE "+ Constants.config.TABLE_HEALTH +" ("+ Constants.config.HEALTHID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.HEALTH_ID+" INTEGER,"+ Constants.config.HEALTH_NAME+" TEXT, "+ Constants.config.HEALTH_CADRE+" TEXT," +
                        " "+ Constants.config.FACILITY_OWNER+" TEXT,"+ Constants.config.FACILITY_TYPE+" TEXT,"+Constants.config.DISTRICT_NAME+" TEXT, " +
                        " "+ Constants.config.HEALTH_STATUS+" INTEGER);";

        public  static  final String CREATE_LOGIN =
                "CREATE TABLE "+ Constants.config.TABLE_LOG +" ("+ Constants.config.LOGID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.LOG_ID+" INTEGER,"+ Constants.config.LOG_DATE+" TEXT, "+ Constants.config.LOG_TIME+" TEXT," +
                        " "+ Constants.config.LOG_TYPE+" INTEGER,"+ Constants.config.LOGOUT_TIME+" TEXT,"+Constants.config.GROUP_ID+" TEXT, " +
                        " "+ Constants.config.LOG_STATUS+" INTEGER,"+Constants.config.LOG_NAMES+" TEXT,"+Constants.config.LOG_IMEI+" TEXT," +
                        " "+Constants.config.USER_ID+" INTEGER);";


        public  static  final String CREATE_SIMULATION =
                "CREATE TABLE "+ Constants.config.TABLE_SIMULATION +" ("+ Constants.config.SIMULATIONID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.SIMULATION_ID+" INTEGER,"+ Constants.config.SIMULATION_DATE+" TEXT, "+ Constants.config.SIMULATION_TIME+" TEXT," +
                        " "+ Constants.config.SIMULATION_CHECHLIST+" TEXT,"+ Constants.config.SKILL_DONE+" TEXT," +
                        " "+ Constants.config.SIMULATION_STATUS+" INTEGER, "+Constants.config.SIMULATION_IMEI+" TEXT);";

        public  static  final String CREATE_PREPARATION =
                "CREATE TABLE "+ Constants.config.TABLE_PREPARATION +" ("+ Constants.config.PREPARATIONID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.PREPARATION_ID+" INTEGER,"+ Constants.config.PREP_IDENTIFY_HELPER+" TEXT, "+ Constants.config.PREP_AREA_DELIVERY+" TEXT," +
                        " "+ Constants.config.PREP_WASHES_HANDS+" TEXT,"+ Constants.config.PREP_AREA_VENTILATION+" TEXT,"+Constants.config.PREP_TEST_VENTILATION+" TEXT, " +
                        " "+ Constants.config.PREP_ASSEMBLED+" TEXT,"+ Constants.config.PREP_UTEROTONIC+" TEXT," +
                        ""+ Constants.config.PREP_TIME+" TEXT,"+ Constants.config.LOG_ID+" INTEGER," +
                        " "+ Constants.config.PREP_STATUS+" INTEGER, "+Constants.config.PREP_DATE+" TEXT, "+Constants.config.PREP_IMEI+", "+Constants.config.PREP_TYPE+" INTEGER);";


        public  static  final String CREATE_ROUTINE =
                "CREATE TABLE "+ Constants.config.TABLE_ROUTINE +" ("+ Constants.config.ROUTINEID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.ROUTINE_ID+" INTEGER,"+ Constants.config.ROUTINE_DRIES_THOROUGHY+" TEXT, "+ Constants.config.ROUTINE_RECOGNISE_CRYING+" TEXT," +
                        " "+ Constants.config.ROUTINE_CHECKS_BREATHING+" TEXT,"+ Constants.config.ROUTINE_CLAMPS+" TEXT,"+ Constants.config.ROUTINE_POSITION+" TEXT," +
                        " "+ Constants.config.ROUTINE_CONTINUE+" TEXT,"+ Constants.config.ROUTINE_TIME+" TEXT,"+Constants.config.ROUTINE_STATUS+" INTEGER," +
                        " "+Constants.config.ROUTINE_DATE+" TEXT, "+Constants.config.ROUTINE_IMEI+", "+Constants.config.ROUTINE_TYPE+" INTEGER, "+Constants.config.LOG_ID+" INTEGER);";


        public  static  final String CREATE_GMV =
                "CREATE TABLE "+ Constants.config.TABLE_GMV +" ("+ Constants.config.GMVID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.GMV_ID+" INTEGER,"+ Constants.config.GMV_DRIES_THOROUGHY+" TEXT, "+ Constants.config.GMV_RECOGNISE_NOTBREATHING+" TEXT," +
                        " "+ Constants.config.GMV_RECOGNISE_NOTCRYING+" TEXT,"+ Constants.config.GMV_KEEPS_WARM+" TEXT,"+ Constants.config.GMV_STIMULATES_BREATHING+" TEXT," +
                        " "+ Constants.config.GMV_FOLLOW_ROUTINE+" TEXT,"+ Constants.config.GMV_MOVE_VENTILATION+" TEXT,"+ Constants.config.GMV_VENTILATE+" TEXT," +
                        ""+ Constants.config.GMV_RECOGNISE_BREATHINGWELL+" TEXT,"+ Constants.config.LOG_ID+" INTEGER, "+Constants.config.GMV_RECOGNISE_CHESTMOVING+" TEXT," +
                        " "+Constants.config.GMV_MONITOR_WITHMOTHER+" TEXT,"+Constants.config.GMV_CONTINUE_ESSENTAIL+" TEXT, "+Constants.config.GMV_TIME+" TEXT, " +
                        " "+ Constants.config.GMV_STATUS+" INTEGER,"+Constants.config.GMV_DATE+" TEXT, "+Constants.config.GMV_IMEI+", "+Constants.config.GMV_TYPE+" INTEGER);";

        public  static  final String CREATE_GMWV =
                "CREATE TABLE "+ Constants.config.TABLE_GMWV +" ("+ Constants.config.GMWVID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.GMWV_ID+" INTEGER,"+ Constants.config.GMWV_RECOGNISE_NOTBREATHING_AND_CHEST+" TEXT, "+ Constants.config.GMWV_CALLS_FORHELP+" TEXT," +
                        " "+ Constants.config.GMWV_CONTINUE_IMPROVE+" TEXT,"+ Constants.config.GMWV_RECOGNISE_NOTBREATHING+" TEXT,"+ Constants.config.GMWV_NORMAL+" TEXT," +
                        " "+ Constants.config.GMWV_BREATHING+" TEXT,"+ Constants.config.GMWV_IF_BREATHING+" TEXT,"+ Constants.config.GMWV_IF_NOTBREATHING+" TEXT," +
                        ""+ Constants.config.GMWV_COMMUNICATES_WITHMOTHER+" TEXT,"+ Constants.config.LOG_ID+" INTEGER, "+Constants.config.GMWV_CONTINUE_ESSENTAIL+" TEXT," +
                        " "+Constants.config.GMWV_DISINFECT_EQUIPMENT+" TEXT,"+Constants.config.GMWV_TIME+" TEXT, "+Constants.config.GMWV_DATE+" TEXT, " +
                        " "+ Constants.config.GMWV_STATUS+" INTEGER,"+Constants.config.GMWV_IMEI+" TEXT,"+Constants.config.GMWV_TYPE+" INTEGER);";

        public  static  final String CREATE_QUIZE1 =
                "CREATE TABLE "+ Constants.config.TABLE_QUIZ1 +" ("+ Constants.config.QUIZ1ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+ Constants.config.QUIZ1_ID+" INTEGER,"+ Constants.config.QUIZ1_USER_ID+" INTEGER, "+ Constants.config.QUIZ1_NAMES+" TEXT," +
                        " "+ Constants.config.QUIZ1_DATE+" TEXT,"+ Constants.config.QUIZ1_TIME+" TEXT,"+ Constants.config.QUIZ1_TYPE+" INTEGER," +
                        " "+ Constants.config.QUIZ1_STATUS+" INTEGER,"+ Constants.config.QUIZ1_IMEI+" TEXT,"+ Constants.config.QUIZ1_Q1+" INTEGER," +
                        ""+ Constants.config.QUIZ1_Q2+" INTEGER,"+ Constants.config.QUIZ1_Q3+" INTEGER, "+Constants.config.QUIZ1_Q4+" INTEGER," +
                        " "+Constants.config.QUIZ1_Q5+" INTEGER,"+Constants.config.QUIZ1_Q6+" INTEGER, "+Constants.config.QUIZ1_Q7+" INTEGER, " +
                        " "+ Constants.config.QUIZ1_Q8+" INTEGER,"+Constants.config.QUIZ1_Q9+" INTEGER ,"+ Constants.config.QUIZ1_Q10+" INTEGER," +
                        " "+ Constants.config.QUIZ1_Q11+" INTEGER,"+ Constants.config.QUIZ1_Q12+" INTEGER,"+ Constants.config.QUIZ1_Q13+" INTEGER," +
                        ""+ Constants.config.QUIZ1_Q14+" INTEGER,"+ Constants.config.QUIZ1_Q15+" INTEGER, "+Constants.config.QUIZ1_Q16+" INTEGER," +
                        " "+Constants.config.QUIZ1_Q17+" INTEGER,"+Constants.config.QUIZ1_Q18+" INTEGER, "+Constants.config.QUIZ1_Q19+" INTEGER, " +
                        " "+ Constants.config.QUIZ1_Q20+" INTEGER,"+Constants.config.QUIZ1_Q21+" INTEGER,"+Constants.config.QUIZ1_Q22+" INTEGER ," +
                        " "+Constants.config.QUIZ1_Q23+" INTEGER,"+Constants.config.QUIZ1_Q24+" INTEGER, "+Constants.config.QUIZ1_Q25+" INTEGER, " +
                        " "+ Constants.config.QUIZ1_Q26+" INTEGER,"+Constants.config.LOG_ID+" INTEGER);";


    }
}
