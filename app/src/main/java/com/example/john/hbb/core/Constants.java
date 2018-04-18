package com.example.john.hbb.core;

import android.content.Context;

import static com.example.john.hbb.core.Constants.config.IMEI;

/**
 * Created by john on 7/8/17.
 */

public class Constants {
    public abstract class config{

        public static final String URL_LOCALHOST = "http://127.0.0.1/hbb/";
        public static final String URL_PHONE = "http://192.168.43.18/hbb/";
        public static final String URL_CAMTECH = "http://192.168.1.104/hbb/";
        public static final String URL_STUDENT = "http://10.11.1.129/hbb/";
        public static final String URL_SERVER = "http://173.255.219.164/hbb_dashboard/";
        public static final String HOST_URL = URL_CAMTECH+"mobile_connections/";
        public static final String DATABASE_NAME = "hbb_db";
        public static final int DATABASE_VERSION = 1;
        public static final String TABLE_USERS = "users_tb";
        public static final String FETCH_STATUS = "fetch_status";
        public static final String IMEI = "imei";
        public static final String USERID = "userID";
        public static final String USER_ID = "user_id";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String CONTACT = "phone";
        public static final String FACILITY_NAME = "facility_name";
        public static final String GENDER = "gender";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String USER_STATUS = "User_Status";
        public static final String FACILITY_TYPE = "facility_type";
        public static final String HEALTH_FACILITY = "health_facility";

        public static final String FACILITY_OWNER = "facility_owner";
        public static final String HEALTH_CADRE = "health_cadre";
        public static final String VERIFIED_STATUS = "verified_status";
        public static final String TABLE_TRAINING = "training_tb";
        public static final String TRAINING_ID = "training_id";
        public static final String TRAININGID = "trainingID";
        public static final String TRAINING_DATE = "training_date";
        public static final String TRAINING_NAME = "training_name";
        public static final String TRAINING_TIME = "training_time";
        public static final String TRAINING_FREQUENCY = "training_frequency";
        public static final String TRAINING_KEY_SKILL = "training_key_skills";
        public static final String TRAINING_KEY_SUBSKILL = "training_key_subskills";
        public static final String TRAINING_SYNC_STATUS = "sync_status";
        public static final String VIDEO_COMPLETED = "video_completed";
        public static final String TRAINING_IMEI = "training_imei";
        public static final String DISTRICT_ID = "district_id";
        //TODO:: public static final String DISTRICTID = "districtID";
        public static final String DISTRICT_NAME = "district_name";
        public static final String TABLE_DISTRICT = "district_tb";
       //TODO:: public static final String DISTRICT_STATUS = "district_status";
        public static final String HEALTH_ID = "heath_id";
        public static final String HEALTH_NAME = "health_name";
        public static final String TABLE_HEALTH = "health_tb";
        public static final String HEALTH_STATUS = "health_status";
        public static final String HEALTHID = "healthID";
        ///// TODO: 10/20/17  sign up user..
        public static final String KEY_FNAME_TEMP = "fname_temp";
        public static final String KEY_LNAME_TEMP = "lname_temp";
        public static final String KEY_CONTACT_TEMP = "contact_temp";
        public static final String KEY_GENDER_TEMP = "gender_temp";
        public static final String KEY_FACILITY_TEMP = "facility_temp";
        // Email address (make variable public to access from outside)
        ///// >>>>>>>>>>>>>>>>> TODO: 10/20/17  SIMULATION MODE <<<<<<<<<<<<<<<<<<<<<<......
        public static final String TABLE_SIMULATION = "simulation_tb";
        public static final String SIMULATION_ID = "simulation_id";
        public static final String SIMULATIONID = "simulationID";
        public static final String SIMULATION_DATE = "simulation_date";
        public static final String SIMULATION_TIME = "simulation_time";
        public static final String SIMULATION_CHECHLIST = "simulation_checklists";
        public static final String SKILL_DONE = "simulation_skill_done";
        public static final String SIMULATION_STATUS = "simulation_status";
        public static final String SIMULATION_IMEI = "simulation_imei";
        ///// TODO: 10/20/17  Preparation for birth...
        public static final String TABLE_PREPARATION = "preparatin_tb";
        public static final String PREPARATION_ID = "preperation_id";
        public static final String PREPARATIONID = "preparationID";
        public static final String PREP_IDENTIFY_HELPER = "prep_identify_helper";
        public static final String PREP_AREA_DELIVERY = "prep_area_delivery";
        public static final String PREP_WASHES_HANDS = "prep_washes_hands";
        public static final String PREP_AREA_VENTILATION = "prep_area_ventilation";
        public static final String PREP_ASSEMBLED = "prep_assembled";
        public static final String PREP_TEST_VENTILATION = "prep_test_ventilation";
        public static final String PREP_UTEROTONIC = "prep_uterotonic";
        public static final String PREP_TIME = "prep_time";
        public static final String PREP_STATUS = "prep_status";
        public static final String PREP_DATE = "prep_date";
        public static final String PREP_IMEI = "prep_imei";
        public static final String PREP_TYPE = "prep_type";
        ///// TODO: 10/20/17  Routine Care...
        public static final String TABLE_ROUTINE = "routine_tb";
        public static final String ROUTINE_ID = "routine_id";
        public static final String ROUTINEID = "routineID";
        public static final String ROUTINE_DRIES_THOROUGHY = "routine_dries_thoroughy";
        public static final String ROUTINE_RECOGNISE_CRYING = "routine_recognise_crying";
        public static final String ROUTINE_CHECKS_BREATHING = "routine_checks_breathing";
        public static final String ROUTINE_CLAMPS = "routine_clamps";
        public static final String ROUTINE_POSITION = "routine_position";
        public static final String ROUTINE_CONTINUE = "routine_continue";
        public static final String ROUTINE_TIME = "routine_time";
        public static final String ROUTINE_STATUS = "routine_status";
        public static final String ROUTINE_DATE = "routine_date";
        public static final String ROUTINE_IMEI = "routine_imei";
        public static final String ROUTINE_TYPE = "routine_type";
        ///// TODO: 10/20/17  Golden Minute with ventilation...
        public static final String TABLE_GMV = "gmv_tb";
        public static final String GMV_ID = "gmv_id";
        public static final String GMVID = "gmvID";
        public static final String GMV_DRIES_THOROUGHY = "gmv_dries_thoroughy";
        public static final String GMV_RECOGNISE_NOTCRYING = "gmv_recognise_notcrying";
        public static final String GMV_KEEPS_WARM = "gmv_keeps_warm";
        public static final String GMV_STIMULATES_BREATHING = "gmv_stimulats_breathing";
        public static final String GMV_RECOGNISE_NOTBREATHING = "gmv_recognise_notbreathing";
        public static final String GMV_FOLLOW_ROUTINE = "gmv_follow_routine";
        public static final String GMV_MOVE_VENTILATION = "gmv_move_ventilation";
        public static final String GMV_VENTILATE = "gmv_ventilation";
        public static final String GMV_RECOGNISE_BREATHINGWELL = "gmv_recognise_breathing";
        public static final String GMV_RECOGNISE_CHESTMOVING = "gmv_recognise_chestmoving";
        public static final String GMV_MONITOR_WITHMOTHER = "gmv_monitor_with_mother";
        public static final String GMV_CONTINUE_ESSENTAIL = "gmv_continue_essential";
        public static final String GMV_TIME = "gmv_time";
        public static final String GMV_STATUS = "gmv_status";
        public static final String GMV_DATE = "gmv_date";
        public static final String GMV_IMEI = "gmv_imei";
        public static final String GMV_TYPE = "gmv_type";
        ///// TODO: 10/20/17  Golden Minute without ventilation...
        public static final String TABLE_GMWV = "gmwv_tb";
        public static final String GMWV_ID = "gmwv_id";
        public static final String GMWVID = "gmwvID";
        public static final String GMWV_RECOGNISE_NOTBREATHING_AND_CHEST = "gmwv_recognise_nothbeathing_and_chest";
        public static final String GMWV_CALLS_FORHELP = "gmwv_calls_forhelp";
        public static final String GMWV_CONTINUE_IMPROVE = "gmwv_continue_improve";
        public static final String GMWV_RECOGNISE_NOTBREATHING = "gmwv_recognise_notbreathing";
        public static final String GMWV_NORMAL = "gmwv_normal";
        public static final String GMWV_BREATHING = "gmwv_breathing";
        public static final String GMWV_IF_BREATHING = "gmwv_if_breathing";
        public static final String GMWV_IF_NOTBREATHING = "gmwv_if_nothbreathing";
        public static final String GMWV_COMMUNICATES_WITHMOTHER = "gmwv_communicates_withmother";
        public static final String GMWV_CONTINUE_ESSENTAIL = "gmwv_continue_essential";
        public static final String GMWV_DISINFECT_EQUIPMENT = "gmwv_disinfect_equipment";
        public static final String GMWV_TIME = "gmwv_time";
        public static final String GMWV_STATUS = "gmwv_status";
        public static final String GMWV_DATE = "gmwv_date";
        public static final String GMWV_IMEI = "gmwv_imei";
        public static final String GMWV_TYPE = "gmwv_type";
        // Email address (make variable public to access from outside)
        ///// TODO: 10/20/17  User LOG..
        public static final String TABLE_LOG = "log_tb";
        public static final String LOG_ID = "log_id";
        public static final String LOGID = "logID";
        public static final String LOG_DATE = "log_date";
        public static final String LOG_TIME = "log_time";
        public static final String LOG_TYPE = "log_type";
        public static final String LOGOUT_TIME = "logout_time";
        public static final String LOG_STATUS = "log_status";
        public static final String LOG_NAMES = "log_names";
        public static final String LOG_IMEI = "log_imei";
        public static final String GROUP_ID = "group_id";
        // Email address (make variable public to access from outside)
        public static final String KEY_EMAIL_TEMP = "email_temp";
        public static final String KEY_PASSWORD_TEMP = "password_temp";



        //TODO:: SYNCING>>>
        public static final String TABLE_SYNCING = "syncing_status";
        public static final String SYNC_ID = "sync_id";
        public static final String SYNC_IMEI = "imei";
        public static final String SYNC_DATE = "sync_date";
        public static final String SYNC_TIME = "last_sync_time";
        public static final String SYNC_STATUS = "sync_status";


        //// TODO: 10/23/17  URL
        public static final String SYNC_FOLDER = "syncing/";
        public static final String URL_SAVE_DISTRICT = SYNC_FOLDER+"save_district.php";
        public static final String URL_SAVE_TRAINING = SYNC_FOLDER+"save_training.php";
        public static final String URL_GET_DISTRICT = SYNC_FOLDER+"getDistrict.php";
        //// TODO: 10/23/17  URL
        public static final String SYNCING = "syncing/";
        public static final String URL_SAVE_HEALTH = SYNCING+"save_health.php";
        public static final String URL_USER = SYNCING+"save_users.php";
        public static final String URL_SAVE_LOG = SYNCING+"save_log.php";
        public static final String URL_SAVE_PREPARATION = SYNCING+"save_preparation.php";
        public static final String URL_SAVE_ROUTINE = SYNCING+"save_routine.php";
        public static final String URL_SAVE_GMV = SYNCING+"save_gmv.php";
        public static final String URL_SAVE_GMWV = SYNCING+"save_gmwv.php";
        public static final String URL_FETCH_JSON = SYNCING+"fetch_json.php";
        public static final String URL_QUERY = SYNCING+"query.php";
        public static final String URL_SAVE_STATUS = SYNCING+"sync_status.php";


        public static final String URL_GET_SINGLE_ENTRY = SYNCING+"get_single_entry.php";
        public static final String URL_GET_ALL_ENTRY = SYNCING+"get_all_entry.php";
        public static final String URL_SYNC_USER = SYNCING+"sync_users.php";
        public static final String URL_SYNC_HEALTH = SYNCING+"sync_health.php";
        public static final String URL_SYNC_PREPARATION = SYNCING+"sync_preparation.php";
        public static final String URL_SYNC_LOG = SYNCING+"sync_log.php";
        //// TODO: 10/23/17  OPERATIONS
        public static final String OPERATION_USER = "users";
        public static final String OPERATION_DISTRICT = "district";
        public static final String OPERATION_TRAINING = "training";
        public static final String OPERATION_HEALTH = "health";
        public static final String OPERATION_LOG = "log";
        public static final String OPERATION_PREPARATION = "preparation";
        public static final String OPERATION_LOGOUT = "logout";
        public static final String OPERATION_ROUTINE = "routine";
        ///TODO::: SERVER CALLS....>>>>>>>
        public static final String POST_TABLE = "table";
        public static final String POST_COLUMN = "column";
        //// TODO: 10/23/17
        public static final String SECRET_KEY = "super-secret-key-0123123451";
        public static final String SQL_QUERY = "query";
    }

    public static  String SQL_GET_CASES = "";
    public static  String SQL_GET_INSTALLATION = "";
    public static  String SQL_GET_ACTIVATION = "";
    public static  String SQL_GET_MANAGEMENT = "";
    public static  String SQL_GET_PAYMENT = "";
    public static  String SQL_GET_PAYPAL = "";
    public static  String SQL_GET_SUBSCRIPTION = "";
    public static  String SQL_GET_SUBSCRIPTION_FEE = "";
    public static  String SQL_GET_INSTALL = "";
    public Constants(Context context){
        //TODO??>>>>>> SQL QUERY.......
        SQL_GET_CASES = "SELECT * FROM cases  WHERE "+IMEI+" = '"+Phone.getIMEI(context)+"' ";
        SQL_GET_INSTALLATION = "SELECT * FROM installations  WHERE "+IMEI+" = '"+Phone.getIMEI(context)+"' ";
        SQL_GET_ACTIVATION = "SELECT * FROM activations  WHERE "+IMEI+" = '"+Phone.getIMEI(context)+"' ";
        SQL_GET_MANAGEMENT = "SELECT * FROM management  WHERE "+IMEI+" = '"+Phone.getIMEI(context)+"' ";
        SQL_GET_PAYMENT = "SELECT * FROM payments  WHERE "+IMEI+" = '"+Phone.getIMEI(context)+"' ";
        SQL_GET_PAYPAL = "SELECT * FROM installations  WHERE "+IMEI+" = '"+Phone.getIMEI(context)+"' ";
        SQL_GET_SUBSCRIPTION = "SELECT * FROM subscription_details  WHERE "+IMEI+" = '"+Phone.getIMEI(context)+"' ";
        SQL_GET_SUBSCRIPTION_FEE = " SELECT * FROM subscriptions ORDER BY Subscription_Id DESC LIMIT 1";
        //SQL_GET_INSTALLATION = "SELECT * FROM subscription_details  WHERE "+IMEI+" = '"+Phone.getIMEI(context)+"' ";
    }
}
