package com.example.john.hbb.configuration;

/**
 * Created by john on 7/8/17.
 */

public class Constants {
    public abstract class config{

        public static final String URL_PHONE = "http://192.168.43.18/hbb/";
        public static final String URL_CAMTECH = "http://137.63.161.57/hbb/";
        public static final String URL_SERVER = "http://173.255.219.164/hbb_dashboard/";
        public static final String HOST_URL = URL_SERVER+"mobile_connections/";

        public static final String DATABASE_NAME = "hbb_db";
        public static final int DATABASE_VERSION = 1;

        public static final String TABLE_TRAINING_MODE = "training_mode_tb";
        public static final String TABLE_USERS = "users_tb";

        public static final String FETCH_STATUS = "fetch_status";

        public static final String USER_ID = "user_id";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String CONTACT = "phone";
        public static final String HEALTH_FACILITY = "health_facility";
        public static final String GENDER = "gender";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String USER_STATUS = "User_Status";
        public static final String FACILITY_TYPE = "facility_type";
        public static final String FACILITY_OWNER = "facility_owner";
        public static final String HEALTH_CADRE = "health_cadre";
        public static final String VERIFIED_STATUS = "verified_status";

        public static final String TRAINING_ID = "training_ID";
        public static final String TRAINING_DATE = "training_date";
        public static final String TRAINING_NAME = "training_name";
        public static final String TRAINING_TIME = "training_time";
        public static final String TRAINING_FREQUENCY = "training_frequency";
        public static final String TRAINING_KEY_SKILL = "training_key_skills";
        public static final String TRAINING_KEY_SUBSKILL = "training_key_subskills";
        public static final String TRAINING_SYNC_STATUS = "sync_status";

        public static final String DISTRICT_ID = "district_id";
        public static final String DISTRICT_NAME = "district_name";
        public static final String TABLE_DISTRICT = "district";
        public static final String DISTRICT_DATE_TIME = "date_time";

        public static final String HEALTH_ID = "heath_id";
        public static final String HEALTH_NAME = "health_name";
        public static final String TABLE_HEALTH = "health_tb";
        public static final String TABLE_STATUS = "health_id";
        public static final String HEALTHID = "healthID";
        ///// TODO: 10/20/17  sign up user..
        public static final String KEY_FNAME_TEMP = "fname_temp";
        public static final String KEY_LNAME_TEMP = "lname_temp";
        public static final String KEY_CONTACT_TEMP = "contact_temp";
        public static final String KEY_GENDER_TEMP = "gender_temp";
        public static final String KEY_FACILITY_TEMP = "facility_temp";
        // Email address (make variable public to access from outside)
        public static final String KEY_EMAIL_TEMP = "email_temp";
        public static final String KEY_PASSWORD_TEMP = "password_temp";



        //// TODO: 10/23/17  URL
        public static final String SYNC_FOLDER = "sync_calls/";
        public static final String URL_SAVE_DISTRICT = SYNC_FOLDER+"save_district.php";
        public static final String URL_SAVE_USER = SYNC_FOLDER+"save_Users.php";
        public static final String URL_SAVE_TRAINING = SYNC_FOLDER+"save_training.php";
        public static final String URL_SYNC_DISTRICT = SYNC_FOLDER+"sync_districts.php";
        public static final String URL_SYNC_TRAINING = SYNC_FOLDER+"sync_Training.php";
        public static final String URL_SYNC_USER = SYNC_FOLDER+"sync_users.php";
        public static final String URL_GET_DISTRICT = SYNC_FOLDER+"getDistrict.php";
        public static final String URL_GET_USER = SYNC_FOLDER+"getUser.php";
        public static final String URL_GET_TRAINING = SYNC_FOLDER+"getTraining.php";

        //// TODO: 10/23/17  OPERATIONS
        public static final String OPERATION_USER = "users";
        public static final String OPERATION_DISTRICT = "district";
        public static final String OPERATION_TRAINING = "training";

        //// TODO: 10/23/17
        public static final String SECRET_KEY = "super-secret-key-0123123451";
    }
}
