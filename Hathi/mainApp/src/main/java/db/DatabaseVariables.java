package db;

import db.CVDVariables;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class DatabaseVariables extends CVDVariables {

	/* PHC */

	public static final String KEY_PHC_CODE = "phc_code";

	public static final String KEY_PHC_NAME = "phc_name";

	/* VILLAGE */

	// public static final String KEY_PHC_CODE = "phc_code";

	/* LOCALITY */

	public static final String KEY_P_DOB = "dob";

	public static final String KEY_P_AGE = "age";

	public static final String KEY_AGE_CHECKED = "dob_unsured";

	public static final String KEY_LOCALITY_NAME = "locality_name";

	// public static final String KEY_VILLAGE_ID = "village_id";

	/* HOUSEHOLD */

	// public static final String KEY_HH_ID = "hh_id";
	public static final String KEY_DOOR_NO = "door_no";

	// public static final String KEY_LOCALITY_ID = "locality_id";
	// public static final String KEY_VILLAGE_ID= "village_id";

	public static final String KEY_TOTAL_HH_SIZE = "total_hh_size";

	public static final String KEY_TOTAL_HH_ELIGIBLE = "total_hh_eligible";

	public static final String KEY_HH_HEAD_FNAME = "hh_head_fname";

	public static final String KEY_HH_HEAD_LNAME = "hh_head_lname";

	/* PHC_VILLAGE_MAP */

	// public static final String KEY_PHC_CODE = "phc_code";
	public static final String KEY_VILLAGE_ID = "village_id";

	public static final String KEY_LOCALITY_ID = "locality_id";

	/* PATIENT */

	public static final String TABLE_RISK_ASSESS = "riskassessment";

	public static final String TABLE_OPEN_MRS = "openmrsupload";

	public static final String KEY_ID = "_id";
	
	public static final String KEY_NA = "NA";

	public static final String KEY_PATIENT_ID = "patient_id";

	public static final String KEY_CNUMBER = "consent_number";

	public static final String KEY_FNAME = "first_name";

	public static final String KEY_LNAME = "last_name";

	public static final String KEY_CONTACT_NUMBER = "contact_number";

	public static final String KEY_IS_CONTACT_NUMBER_SHARED = "is_contact_no_shared";

	public static final String KEY_ADDRESS = "address";

	public static final String KEY_VILLAGE_NAME = "village_name";

	public static final String KEY_GENDER = "gender";

	public static final String KEY_P_LOCALITY_ID = "locality_id";

	public static final String KEY_P_VILLAGE_ID = "p_village_id";

	public static final String KEY_AGE = "age";

	public static final String KEY_IS_HEAD_OF_HH = "is_head_of_hh";

	public static final String KEY_HH_ID = "hh_id";

	public static final String KEY_HDC = "hdc";

	public static final String KEY_RDC = "rdc";

	public static final String KEY_INTERVIEW_STATUS = "interview_status";

	public static final String KEY_PATIENT_ID_COUNTER = "patient_id_counter";

	/* Input medi */

	// public static final String KEY_PATIENT_ID = "patient_id";
	public static final String KEY_MEDI_NAME = "name";

	public static final String KEY_MEDI_PILLS = "pills_day";

	public static final String KEY_MEDI_STRENGTH = "strength";

	/* TABLES */

	public static final String TABLE_USER = "user";

	public static final String TABLE_PHC = "phc";

	public static final String TABLE_VILLAGE = "village";

	public static final String TABLE_LOCALITY = "locality";

	public static final String TABLE_HOUSEHOLD = "household";

	public static final String TABLE_PHC_VILLAGE_MAP = "phc_village_map";

	public static final String TABLE_PATIENT = "patient";

	public static final String TABLE_UPLOAD_STATUS = "uploadStatus";

	public static final String TABLE_UPLOAD_PATIENT_STATUS = "uploadPatientStatus";

	public static final String TABLE_PATIENTIDENTIFIERS = "patientidentifiers";

	public static final String TABLE_ENCOUNTER = "encounter";

	public static final String KEY_UPLOAD_TABLE = "tablename";

	public static final String KEY_UPLOAD_TABLE_ROW_ID = "table_row_id";

	public static final String KEY_IS_UPLOADED = "is_uploaded";

	public static final String KEY_DATE_MODIFIED = "date_modified";

	public static final String KEY_DATE_UPLOAD = "date_uploaded";

	public static final String KEY_PATIENTIDENTIFIER = "patient_identifier";

	public static final String KEY_UUID = "uuid";

	public static final String KEY_ENCOUNTERDATE_TIME = "encounterDatetime";

	public static final String KEY_ENCOUNTERTYPE = "encounterType";

	public static final String KEY_UPLOAD_STATUS = "upload_status";

	public static final String KEY_OBS_UPDATE_STATUS = "obs_update_status";

	public static final String KEY_ENCOUNTER_STATUS = "encounter_status";

	public static final String KEY_REQUEST_DATA = "requestdata";

	public static final String KEY_QUICK_REVIEW_FLAG = "quick_review_flag";

	public static final String KEY_ALARM_UPDATED_TIME = "updatedtime";

	public static final String KEY_PL_SCORE = "pl_score";

	public static final String KEY_LAST_PL_SCORE = "last_pl_score";

	public static final String KEY_APP_FIRST_INSTAL_FLAG = "app_first_install";

	public static final String KEY_PENDING = "pending";

	public static final String KEY_RETRY_COUNT = "retry_count";
	
	public static final String KEY_ATTEMPT= "attempt";

	public static final String KEY_PROC_GUID = "proc_guid";

	public static final String KEY_DEVICE_IMEI = "device_imei";

	public static final String TABLE_UPLOAD_PATIENT_STATUS_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_UPLOAD_PATIENT_STATUS
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_PATIENT_ID
			+ " TEXT,"
			+ KEY_REQUEST_DATA
			+ " TEXT,"
			+ CVD_VARIABLE_CREATED_DATE
			+ " TEXT,"
			+ KEY_UPLOAD_STATUS
			+ " TEXT,"
			+ KEY_PROC_GUID
			+ " TEXT,"
			+ KEY_RETRY_COUNT
			+ " INTEGER,"
			+ KEY_ATTEMPT + " INTEGER" + ")";

	public static final String TABLE_ENCOUNTER_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_ENCOUNTER
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_PATIENT_ID
			+ " TEXT,"
			+ KEY_UUID
			+ " TEXT,"
			+ KEY_ENCOUNTERDATE_TIME
			+ " TEXT,"
			+ KEY_ENCOUNTERTYPE
			+ " TEXT,"
			+ KEY_DATE_UPLOAD
			+ " TEXT,"
			+ KEY_ENCOUNTER_STATUS
			+ " INTEGER"
			+ ")";

	public static final String TABLE_PATIENTIDENTIFIERS_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_PATIENTIDENTIFIERS
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_PATIENTIDENTIFIER
			+ " TEXT,"
			+ KEY_PL_SCORE
			+ " TEXT,"
			+ KEY_LAST_PL_SCORE
			+ " TEXT,"
			+ KEY_UPLOAD_STATUS
			+ " TEXT,"
			+ KEY_OBS_UPDATE_STATUS + " TEXT" + ")";

	public static final String TABLE_UPLOAD_STATUS_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_UPLOAD_STATUS
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_UPLOAD_TABLE
			+ " TEXT,"
			+ KEY_UPLOAD_TABLE_ROW_ID
			+ " TEXT,"
			+ KEY_DATE_MODIFIED
			+ " TEXT,"
			+ KEY_DATE_UPLOAD
			+ " TEXT,"
			+ KEY_IS_UPLOADED + " INTEGER" + ")";

	// private static final int DATABASE_VERSION = 1;

	public static final String TABLE_PHC_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_PHC + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PHC_CODE
			+ " INTEGER," + KEY_PHC_NAME + " TEXT" + " )";

	public static final String TABLE_VILLAGE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_VILLAGE
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_VILLAGE_NAME + " TEXT," + KEY_PHC_CODE + " INTEGER" + " )";

	public static final String TABLE_LOCALITY_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_LOCALITY
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_LOCALITY_NAME
			+ " TEXT,"
			+ KEY_VILLAGE_ID
			+ " INTEGER"
			+ " ) ";

	public static final String TABLE_HOUSEHOLD_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_HOUSEHOLD
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_HH_ID
			+ " TEXT,"
			+ KEY_DOOR_NO
			+ " TEXT,"
			+ KEY_LOCALITY_ID
			+ " INTEGER,"
			+ KEY_VILLAGE_ID
			+ " INTEGER,"
			+ KEY_TOTAL_HH_SIZE
			+ " INTEGER,"
			+ KEY_TOTAL_HH_ELIGIBLE
			+ " INTEGER,"
			+ KEY_HH_HEAD_FNAME
			+ " TEXT,"
			+ KEY_HH_HEAD_LNAME
			+ " TEXT,"
			+ KEY_PATIENT_ID_COUNTER + " INTEGER" + ") ";

	public static final String TABLE_PHC_VILLAGE_MAP_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_PHC_VILLAGE_MAP
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_PHC_CODE
			+ " INTEGER,"
			+ KEY_VILLAGE_ID
			+ " INTEGER,"
			+ KEY_LOCALITY_ID + " INTEGER" + ")";

	/*public static final String TABLE_PATIENT_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_PATIENT
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_PATIENT_FNAME
			+ " TEXT,"
			+ KEY_PATIENT_FNAME_TE
			+ " TEXT,"
			+ KEY_PATIENT_LNAME
			+ " TEXT,"
			+ KEY_PATIENT_LNAME_TE
			+ " TEXT,"
			+ KEY_AGE
			+ " INTEGER,"
			+ KEY_GENDER
			+ " TEXT,"
			+ KEY_P_LAST_ENCOUNTER
			+ " TEXT,"
			+ KEY_IS_HEAD_OF_HH
			+ " BOOLEAN,"
			+ KEY_HH_ID
			+ " INTEGER,"
			+ KEY_HDC
			+ " INTEGER,"
			+ KEY_RDC
			+ " INTEGER,"
			+ KEY_INTERVIEW_STATUS
			+ " INTEGER,"
			+ KEY_PATIENT_ID
			+ " INTEGER" + ")";*/

	public static final String TABLE_PATIENT_RISK_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_PATIENT
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_PATIENT_FNAME
			+ " TEXT,"
			+ KEY_PATIENT_LNAME
			+ " TEXT,"
			+ KEY_CONTACT_NUMBER
			+ " TEXT,"
			+ KEY_IS_CONTACT_NUMBER_SHARED
			+ " TEXT,"
			+ KEY_ADDRESS
			+ " TEXT,"
			+ KEY_VILLAGE_ID
			+ " TEXT,"
			+ KEY_GENDER
			+ " TEXT,"
			+ KEY_P_DOB
			+ " TEXT,"
			+ KEY_AGE
			+ " TEXT,"
			+ KEY_AGE_CHECKED
			+ " TEXT,"
			+ KEY_LOCALITY_ID
			+ " TEXT,"
			+ KEY_P_REFERALCARD_NUM
			+ " INTEGER,"
			+ KEY_P_LAST_ENCOUNTER
			+ " TEXT" + " ) ";
	
	
	
	
     public static final String TABLE_TELUGU_NAMES = "telugu_names";
	
	public static final String KEY_NAME_EN = "name_en";
	public static final String KEY_NAME_TE = "name_te";
	public static final String KEY_NAME_TYPE = "name_type";
	
	private static final String TABLE_TELUGU_NAMES_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_TELUGU_NAMES
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_NAME_EN
			+ " TEXT,"
			+ KEY_NAME_TE
			+ " TEXT,"
			+ KEY_NAME_TYPE
			+ " TEXT"
			+ " ) ";
  
	
	public static final String TABLE_PATIENT_TELUGU_NAMES = "patient_telugu_names";
	
	
	
	
	private static final String TABLE_PATIENT_TELUGU_NAMES_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_PATIENT_TELUGU_NAMES
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_PATIENT_ID
			+ " TEXT,"
			+ KEY_PATIENT_FNAME_TE
			+ " TEXT,"
			+ KEY_PATIENT_LNAME_TE
			+ " TEXT"
			+ " ) ";
	

	/* Risk Assemnt Related Contsants */

	// RISK ASSESSMENT ADDITIONAL INFO----------------------------------
	public static final String KEY_CONTACT_NO_ISAVAILABLE = "contact_no_isavailable";

	public static final String KEY_CONTACT_NO = "contact_number";

	public static final String KEY_QUESTION_PHONE = "question_phone";

	public static final String KEY_ISHAVING_AADHAR = "ishaving_aadhar";

	public static final String KEY_BP_TREATMENT = "bp_treatment";

	public static final String KEY_LIPID_TREATMENT = "lipid_treatment";

	public static final String KEY_ANTI_THERAPY = "anti_therapy";

	public static final String KEY_WESTERN_MEDICINE = "western_medicine";

	public static final String KEY_RECORD_MEDICATION = "record_medication";

	public static final String KEY_AYUSH_MEDICINE = "ayush_medicine";

	public static final String KEY_PA_QUES1 = "pa_ques1";

	public static final String KEY_PA_QUES1_NO_DAYS = "pa_ques1_no_days";

	public static final String KEY_PA_QUES1_HRS = "pa_ques1_hrs";

	public static final String KEY_PA_QUES1_MIN = "pa_ques1_min";

	public static final String KEY_PA_QUES2 = "pa_ques2";

	public static final String KEY_PA_QUES2_NDPW = "pa_ques2_ndpw";

	public static final String KEY_PA_QUES2_HRS = "pa_ques2_hrs";

	public static final String KEY_PA_QUES2_MIN = "pa_ques2_min";

	public static final String KEY_PA_QUES3 = "pa_ques3";

	public static final String KEY_PA_QUES3_NDPW = "pa_ques3_ndpw";

	public static final String KEY_PA_QUES3_HRS = "pa_ques3_hrs";

	public static final String KEY_PA_QUES3_NDPW_MIN = "pa_ques3_min";

	public static final String KEY_QLIFE_MOBILITY = "qlife_mobility";

	public static final String KEY_QLIFE_SELFCARE = "qlife_selfcare";

	public static final String KEY_QLIFE_UACTIVITIES = "qlife_uactivities";

	public static final String KEY_QLIFE_PAIN = "qlife_pain";

	public static final String KEY_QLIFE_ANXITY = "qlife_anxity";

	public static final String KEY_QLIFE_HOW_GOOD_OR_BAD = "qlife_how_good_or_bad";

	public static final String KEY_ASHA_ASSIGNED = "asha_assigned";

	// further question variables

	public static final String KEY_FURTHER_CHEERFUL_GUD = "further_cheerful_gud";

	public static final String KEY_FURTHER_CALM_RELAX = "further_calm_relax";

	public static final String KEY_FURTHER_ACTIVE_VIGOUROUS = "further_active_vigourous";

	public static final String KEY_FURTHER_FRESH_RESTED = "further_fresh_rested";

	public static final String KEY_FURTHER_FILLED_THINGS = "further_filled_things";

	public static final String KEY_FURTHER_HEIGHT = "further_height";

	public static final String KEY_FURTHER_WEIGHT = "further_weight";

	// table names

	public static final String TABLE_MAINLINE_CACHE_HT = "mainline_riskassess_cache";

	public static final String TABLE_MAINLINE_RISKASSES_HT = "mainline_riskassess";

	public static final String KEY_USERID = "user_id";

	public static final String KEY_USERNAME = "username";

	public static final String KEY_PASSWORD = "password";

	public static final String KEY_USERNAME_UPLOAD = "username_upload";

	
	public static final String KEY_LANGUAGE = "language";
	
	
	
	public static final String KEY_TELUGU_FONT_MAP = "telugu_font_map";
	
	
	public static final String KEY_PASSWORD_UPLOAD = "password_upload";

	public static final String KEY_ROLE = "role";

	public static final String KEY_VILLAGE = "village";

	public static final String TABLE_PRIORITY_LIST = "prioritylist";

	public static final String KEY_PRIORITY = "priority";

	// @@ww

	public static final String TABLE_PRIORITY_LIST_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_PRIORITY_LIST
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_PATIENT_ID
			+ " INTEGER,"
			+ KEY_FNAME
			+ " TEXT,"
			+ KEY_LNAME
			+ " TEXT,"
			+ KEY_LOCALITY_NAME
			+ " TEXT,"
			+ KEY_PRIORITY
			+ " TEXT"
			+ " ) ";

	public static final String TABLE_USER_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_USER
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_FNAME
			+ " TEXT,"
			+ KEY_LNAME
			+ " TEXT,"
			+ KEY_USERID
			+ " TEXT,"
			+ KEY_USERNAME
			+ " TEXT,"
			+ KEY_PASSWORD
			+ " TEXT,"
			+ KEY_ROLE
			+ " TEXT,"
			+ KEY_VILLAGE
			+ " TEXT,"
			+ KEY_PHC_CODE
			+ " TEXT"
			+ " ) ";

	// temp PatientTable for MainLine

	/*
	 * public static final String TABLE_MAINLINE_HT_CREATE =
	 * "CREATE TABLE IF NOT EXISTS " + TABLE_MAINLINE_CACHE_HT + "(" + KEY_ID +
	 * " INTEGER PRIMARY KEY," + KEY_PATIENT_ID + " INTEGER," +
	 * CVD_VARIABLES_P_CNUMBER + " TEXT," + CVD_VARIABLES_P_FIRSTNAME + " TEXT,"
	 * + CVD_VARIABLES_P_SURNAME + " TEXT," + CVD_VARIABLES_P_CONTACTNUM +
	 * " TEXT," + CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE + " TEXT," +
	 * CVD_VARIABLES_P_DOB + " TEXT," + CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE +
	 * " TEXT," + CVD_VARIABLES_P_ADDRESS + " TEXT," + CVD_VARIABLES_P_VILLAGE +
	 * " TEXT, " + CVD_VARIABLES_P_LOCALITY + " TEXT," + CVD_VARIABLES_P_GENDER
	 * + " TEXT," + CVD_VARIABLES_P_DOA + " TEXT," + CVD_VARIABLES_P_AGE +
	 * " TEXT," + CVD_VARIABLES_PH_HRTATTACK + " TEXT," + CVD_VARIABLES_PH_BP +
	 * " TEXT," + CVD_VARIABLES_PH_BP_SINCE + " TEXT," +
	 * CVD_VARIABLES_PH_BP_MEDICATION + " TEXT," + CVD_VARIABLES_PH_STROKE +
	 * " TEXT," + CVD_VARIABLES_PH_PVD + " TEXT," + CVD_VARIABLES_PH_PVD_SINCE +
	 * " TEXT," + CVD_VARIABLES_PH_DM + " TEXT," +
	 * CVD_VARIABLES_PH_HRTATTACK_SINCE + " TEXT," +
	 * CVD_VARIABLES_PH_STROKE_SINCE + " TEXT," + CVD_VARIABLES_PH_DM_SINCE +
	 * " TEXT," + CVD_VARIABLES_FH_HRATTACK + " TEXT," + CVD_VARIABLES_FH_STROKE
	 * + " TEXT," + CVD_VARIABLES_FH_DM + " TEXT," + CVD_VARIABLES_RH_Q1 +
	 * " TEXT," + CVD_VARIABLES_RH_Q2 + " TEXT," +
	 * CVD_VARIABLES_REFERALH_Q2_IF_YES + " TEXT," +
	 * CVD_VARIABLES_REFERALH_Q2_IF_NO_REASON + " TEXT," +
	 * CVD_VARIABLES_REFERALH_Q2_IF_OTHERS_REASON + " TEXT," +
	 * CVD_VARIABLES_TH_BP + " TEXT," + CVD_VARIABLES_TH_APTT + " TEXT," +
	 * CVD_VARIABLES_TH_LLTT + " TEXT," + CVD_VARIABLES_TH_DM + " TEXT," +
	 * CVD_VARIABLES_SH_SMOKING_QUESTION + " TEXT," + CVD_VARIABLES_SH_CURRSMO +
	 * " TEXT," + CVD_VARIABLES_SH_AGEATSMO + " TEXT," +
	 * CVD_VARIABLES_SH_QUITSMO + " TEXT," + CVD_VARIABLES_SH_CURR_CHEW +
	 * " TEXT," + CVD_VARIABLES_SH_AGEAT_CHEW + " TEXT," +
	 * CVD_VARIABLES_SH_QUIT_CHEW + " TEXT," + CVD_VARIABLES_SBP1 + " TEXT," +
	 * CVD_VARIABLES_DBP1 + " TEXT," + CVD_VARIABLES_SBP2 + " TEXT," +
	 * CVD_VARIABLES_DBP2 + " TEXT," + CVD_VARIABLES_SBP3 + " TEXT," +
	 * CVD_VARIABLES_DBP3 + " TEXT," + CVD_VARIABLES_HR1 + " TEXT," +
	 * CVD_VARIABLES_HR2 + " TEXT ," + CVD_VARIABLES_HR3 + " TEXT ," +
	 * CVD_VARIABLES_SBP_AVG + " TEXT ," + CVD_VARIABLES_DBP_AVG + " TEXT ," +
	 * CVD_VARIABLES_HR_AVG + " TEXT ," + CVD_VARIABLES_BPcuffSize + " TEXT," +
	 * CVD_VARIABLES_PULSE + " TEXT ," + CVD_VARIABLES_FASTING6HRS + " TEXT ," +
	 * CVD_VARIABLES_ACQUISITION_TIME + " TEXT ," + CVD_VARIABLES_BG_MGDL +
	 * " TEXT ," + CVD_VARIABLES_TC + " TEXT ," + CVD_VARIABLES_LDL + " TEXT ,"
	 * + CVD_VARIABLES_HDL + " TEXT ," + CVD_VARIABLES_TG + " TEXT ," +
	 * CVD_VARIABLES_CHOL_RESULTDATE + " TEXT ," + CVD_VARIABLES_HT + " TEXT ,"
	 * + CVD_VARIABLES_WT + " TEXT ," + CVD_VARIABLES_BG_EATorDRINK + " TEXT ,"
	 * + CVD_VARIABLES_BP_PERDAY + " TEXT ," + CVD_VARIABLES_BP_PERWEEK +
	 * " TEXT ," + CVD_VARIABLES_BP_PERLASTWEEK + " TEXT ," +
	 * CVD_VARIABLES_BP_PERYESTERDAY + " TEXT ," + CVD_VARIABLES_LP_PERDAY +
	 * " TEXT ," + CVD_VARIABLES_LP_PERWEEK + " TEXT ," +
	 * CVD_VARIABLES_LP_PERLASTWEEK + " TEXT ," + CVD_VARIABLES_LP_PERYESTERDAY
	 * + " TEXT ," + CVD_VARIABLES_AT_PERDAY + " TEXT ," +
	 * CVD_VARIABLES_AT_PERWEEK + " TEXT ," + CVD_VARIABLES_AT_PERLASTWEEK +
	 * " TEXT ," + CVD_VARIABLES_AT_PERYESTERDAY + " TEXT ," +
	 * CVD_VARIABLES_BP_DRUGLIST + " TEXT ," + CVD_VARIABLES_STATIN_DRUGLIST +
	 * " TEXT ," + CVD_VARIABLES_ASPIRIN_DRUGLIST + " TEXT ," + CVD_VARIABLES_BP
	 * + " TEXT ," + CVD_VARIABLES_STATIN + " TEXT ," + CVD_VARIABLES_ASPIRIN +
	 * " TEXT ," + CVD_VARIABLES_BP_REASON + " TEXT ," +
	 * CVD_VARIABLES_STATIN_REASON + " TEXT ," + CVD_VARIABLES_ASPIRIN_REASON +
	 * " TEXT ," + CVD_VARIABLES_TT_PRES + " TEXT ," + CVD_VARIABLES_REFCODE +
	 * " TEXT ," + CVD_VARIABLES_RISKSCORE + " TEXT ," + CVD_VARIABLES_SMOKER +
	 * " TEXT ," + CVD_VARIABLES_DOC_REF + " TEXT ," +
	 * CVD_VARIABLES_NEXT_VISIT_1MONTH + " TEXT ," + CVD_VARIABLES_TT_ADHER +
	 * " TEXT ," + CVD_VARIABLES_MED_RECEIVED + " TEXT ," +
	 * CVD_VARIABLE_UPDATED_DATE + " TEXT ," + CVD_VARIABLE_CREATED_DATE +
	 * " TEXT ," + CVD_VARIABLE_CURRENT_USER_LOGIN + " TEXT ," +
	 * CVD_VARIABLE_CURRENT_USER_ROLE + " TEXT ," + CVD_VARIABLE_LAST_ENCOUNTER
	 * + " TEXT ," + CVD_VARIABLE_CURRENT_ENCOUNTER + " TEXT, " +
	 * CVD_VARIABLES_ENC_DATE + " TEXT ," + CVD_VARIABLES_ENC_type + " TEXT ," +
	 * CVD_VARIABLES_PHC_NAME + " TEXT ," + CVD_VARIABLES_ASHA_ASSIGNED +
	 * " TEXT ," + CVD_VARIABLES_DIAB_CALC + " TEXT ," + CVD_VARIABLES_SMOKER_TL
	 * + " TEXT ," + CVD_VARIABLES_AR_RECOM + " TEXT ," +
	 * CVD_VARIABLES_PH_CVD_CALC + " TEXT ," + CVD_VARIABLES_HIGH_RISK_CALC +
	 * " TEXT ," + CVD_VARIABLES_NV_AR + " TEXT ," + CVD_VARIABLES_NV_AR_TL +
	 * " TEXT ," + CVD_VARIABLES_NV_DIAB + " TEXT ," + CVD_VARIABLES_NV_DIAB_TL
	 * + " TEXT ," + CVD_VARIABLES_REF_DOC_TL + " TEXT ," +
	 * CVD_VARIABLES_TARGET_SBP + " TEXT ," + CVD_VARIABLES_TARGET_SBP_TL +
	 * " TEXT ," + CVD_VARIABLES_TARGET_DBP + " TEXT ," +
	 * CVD_VARIABLES_TARGET_DBP_TL + " TEXT " + " ) ";
	 */

	public static final String TABLE_MAINRISK_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_MAINLINE_RISKASSES_HT
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_PATIENT_ID
			+ " INTEGER,"
			+ CVD_VARIABLES_P_CNUMBER
			+ " TEXT,"
			+ CVD_VARIABLES_P_FIRSTNAME
			+ " TEXT,"
			+ CVD_VARIABLES_P_SURNAME
			+ " TEXT,"
			+ CVD_VARIABLES_P_CONTACTNUM
			+ " TEXT,"
			+ CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE
			+ " TEXT,"
			+ CVD_VARIABLES_P_DOB
			+ " TEXT,"
			+ CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE
			+ " TEXT,"
			+ CVD_VARIABLES_P_ADDRESS
			+ " TEXT,"
			+ CVD_VARIABLES_P_VILLAGE
			+ " TEXT, "
			+ CVD_VARIABLES_P_LOCALITY
			+ " TEXT,"
			+ CVD_VARIABLES_P_GENDER
			+ " TEXT,"
			+ CVD_VARIABLES_P_DOA
			+ " TEXT,"
			+ CVD_VARIABLES_P_AGE
			+ " TEXT,"
			+ KEY_AADHAR_NO
			+ " TEXT,"
			+ CVD_VARIABLES_PH_HRTATTACK
			+ " TEXT,"
			+ CVD_VARIABLES_PH_BP
			+ " TEXT,"
			+ CVD_VARIABLES_PH_BP_SINCE
			+ " TEXT,"
			+ CVD_VARIABLES_PH_BP_MEDICATION
			+ " TEXT,"
			+ CVD_VARIABLES_PH_STROKE
			+ " TEXT,"
			+ CVD_VARIABLES_PH_PVD
			+ " TEXT,"
			+ CVD_VARIABLES_PH_PVD_SINCE
			+ " TEXT,"
			+ CVD_VARIABLES_PH_DM
			+ " TEXT,"
			+ CVD_VARIABLES_PH_HRTATTACK_SINCE
			+ " TEXT,"
			+ CVD_VARIABLES_PH_STROKE_SINCE
			+ " TEXT,"
			+ CVD_VARIABLES_PH_DM_SINCE
			+ " TEXT,"
			+ CVD_VARIABLES_FH_HRATTACK
			+ " TEXT,"
			+ CVD_VARIABLES_FH_STROKE
			+ " TEXT,"
			+ CVD_VARIABLES_FH_DM
			+ " TEXT,"
			+ CVD_VARIABLES_RH_Q1
			+ " TEXT,"
			+ CVD_VARIABLES_RH_Q2
			+ " TEXT,"
			+ CVD_VARIABLES_REFERALH_Q2_IF_YES
			+ " TEXT,"
			+ CVD_VARIABLES_REFERALH_Q2_IF_NO_REASON
			+ " TEXT,"
			+ CVD_VARIABLES_REFERALH_Q2_IF_OTHERS_REASON
			+ " TEXT,"
			+ CVD_VARIABLES_TH_BP
			+ " TEXT,"
			+ CVD_VARIABLES_TH_APTT
			+ " TEXT,"
			+ CVD_VARIABLES_TH_LLTT
			+ " TEXT,"
			+ CVD_VARIABLES_TH_DM
			+ " TEXT,"
			+ CVD_VARIABLES_SH_SMOKING_QUESTION
			+ " TEXT,"
			+ CVD_VARIABLES_SH_CURRSMO
			+ " TEXT,"
			+ CVD_VARIABLES_SH_AGEATSMO
			+ " TEXT,"
			+ CVD_VARIABLES_SH_QUITSMO
			+ " TEXT,"
			+ CVD_VARIABLES_SH_CURR_CHEW
			+ " TEXT,"
			+ CVD_VARIABLES_SH_AGEAT_CHEW
			+ " TEXT,"
			+ CVD_VARIABLES_SH_QUIT_CHEW
			+ " TEXT,"
			+ CVD_VARIABLES_SBP1
			+ " TEXT,"
			+ CVD_VARIABLES_DBP1
			+ " TEXT,"
			+ CVD_VARIABLES_SBP2
			+ " TEXT,"
			+ CVD_VARIABLES_DBP2
			+ " TEXT,"
			+ CVD_VARIABLES_SBP3
			+ " TEXT,"
			+ CVD_VARIABLES_DBP3
			+ " TEXT,"
			+ CVD_VARIABLES_HR1
			+ " TEXT,"
			+ CVD_VARIABLES_HR2
			+ " TEXT ,"
			+ CVD_VARIABLES_HR3
			+ " TEXT ,"
			+ CVD_VARIABLES_SBP_AVG
			+ " TEXT ,"
			+ CVD_VARIABLES_DBP_AVG
			+ " TEXT ,"
			+ CVD_VARIABLES_HR_AVG
			+ " TEXT ,"
			+ CVD_VARIABLES_BPcuffSize
			+ " TEXT,"
			+ CVD_VARIABLES_PULSE
			+ " TEXT ,"
			+ CVD_VARIABLES_FASTING6HRS
			+ " TEXT ,"
			+ CVD_VARIABLES_ACQUISITION_TIME
			+ " TEXT ,"
			+ CVD_VARIABLES_BG_MGDL
			+ " TEXT ,"
			+ CVD_VARIABLES_TC
			+ " TEXT ,"
			+ CVD_VARIABLES_LDL
			+ " TEXT ,"
			+ CVD_VARIABLES_HDL
			+ " TEXT ,"
			+ CVD_VARIABLES_TG
			+ " TEXT ,"
			+ CVD_VARIABLES_CHOL_RESULTDATE
			+ " TEXT ,"
			+ CVD_VARIABLES_HT
			+ " TEXT ,"
			+ CVD_VARIABLES_WT
			+ " TEXT ,"
			+ CVD_VARIABLES_BG_EATorDRINK
			+ " TEXT ,"
			+ CVD_VARIABLES_BP_PERDAY
			+ " TEXT ,"
			+ CVD_VARIABLES_BP_PERWEEK
			+ " TEXT ,"
			+ CVD_VARIABLES_BP_PERLASTWEEK
			+ " TEXT ,"
			+ CVD_VARIABLES_BP_PERYESTERDAY
			+ " TEXT ,"
			+ CVD_VARIABLES_LP_PERDAY
			+ " TEXT ,"
			+ CVD_VARIABLES_LP_PERWEEK
			+ " TEXT ,"
			+ CVD_VARIABLES_LP_PERLASTWEEK
			+ " TEXT ,"
			+ CVD_VARIABLES_LP_PERYESTERDAY
			+ " TEXT ,"
			+ CVD_VARIABLES_AT_PERDAY
			+ " TEXT ,"
			+ CVD_VARIABLES_AT_PERWEEK
			+ " TEXT ,"
			+ CVD_VARIABLES_AT_PERLASTWEEK
			+ " TEXT ,"
			+ CVD_VARIABLES_AT_PERYESTERDAY
			+ " TEXT ,"
			+ CVD_VARIABLES_BP_DRUGLIST
			+ " TEXT ,"
			+ CVD_VARIABLES_STATIN_DRUGLIST
			+ " TEXT ,"
			+ CVD_VARIABLES_ASPIRIN_DRUGLIST
			+ " TEXT ,"
			+ CVD_VARIABLES_BP
			+ " TEXT ,"
			+ CVD_VARIABLES_STATIN
			+ " TEXT ,"
			+ CVD_VARIABLES_ASPIRIN
			+ " TEXT ,"
			+ CVD_VARIABLES_BP_REASON
			+ " TEXT ,"
			+ CVD_VARIABLES_STATIN_REASON
			+ " TEXT ,"
			+ CVD_VARIABLES_ASPIRIN_REASON
			+ " TEXT ,"
			+ CVD_VARIABLES_TT_PRES
			+ " TEXT ,"
			+ CVD_VARIABLES_REFCODE
			+ " TEXT ,"
			+ CVD_VARIABLES_RISKSCORE
			+ " TEXT ,"
			+ CVD_VARIABLES_SMOKER
			+ " TEXT ,"
			+ CVD_VARIABLES_DOC_REF
			+ " TEXT ,"
			+ CVD_VARIABLES_NEXT_VISIT_1MONTH
			+ " TEXT ,"
			+ CVD_VARIABLES_TT_ADHER
			+ " TEXT ,"
			+ CVD_VARIABLES_MED_RECEIVED
			+ " TEXT ,"
			+ CVD_VARIABLE_UPDATED_DATE
			+ " TEXT ,"
			+ CVD_VARIABLE_CREATED_DATE
			+ " TEXT ,"
			+ CVD_VARIABLE_CURRENT_USER_LOGIN
			+ " TEXT ,"
			+ CVD_VARIABLE_CURRENT_USER_ROLE
			+ " TEXT ,"
			+ CVD_VARIABLE_LAST_ENCOUNTER
			+ " TEXT ,"
			+ CVD_VARIABLE_CURRENT_ENCOUNTER
			+ " TEXT, "
			+ CVD_VARIABLES_ENC_DATE
			+ " TEXT ,"
			+ CVD_VARIABLES_ENC_type
			+ " TEXT ,"
			+ CVD_VARIABLES_PHC_NAME
			+ " TEXT ,"
			+ CVD_VARIABLES_ASHA_ASSIGNED
			+ " TEXT ,"
			+ CVD_VARIABLES_DIAB_CALC
			+ " TEXT ,"
			+ CVD_VARIABLES_SMOKER_TL
			+ " TEXT ,"
			+ CVD_VARIABLES_AR_RECOM
			+ " TEXT ,"
			+ CVD_VARIABLES_NV_AR
			+ " TEXT ,"
			+ CVD_VARIABLES_NV_AR_TL
			+ " TEXT ,"
			+ CVD_VARIABLES_PH_CVD_CALC
			+ " TEXT ,"
			+ CVD_VARIABLES_HIGH_RISK_CALC
			+ " TEXT ,"
			+ CVD_VARIABLES_NV_DIAB
			+ " TEXT ,"
			+ CVD_VARIABLES_NV_DIAB_TL
			+ " TEXT ,"
			+ CVD_VARIABLES_REF_DOC_TL
			+ " TEXT ,"
			+ CVD_VARIABLES_TARGET_SBP
			+ " TEXT ,"
			+ CVD_VARIABLES_TARGET_SBP_TL
			+ " TEXT ,"
			+ CVD_VARIABLES_TARGET_DBP
			+ " TEXT ,"
			+ CVD_VARIABLES_TARGET_DBP_TL + " TEXT " + " ) ";

	// -----------------------------------

	public static final String TABLE_MAINLINE_HT_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_MAINLINE_CACHE_HT
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY,"
			+ KEY_PATIENT_ID
			+ " INTEGER,"
			+ CVD_VARIABLES_P_CNUMBER
			+ " TEXT,"
			+ CVD_VARIABLES_P_FIRSTNAME
			+ " TEXT,"
			+ CVD_VARIABLES_P_SURNAME
			+ " TEXT,"
			+ CVD_VARIABLES_P_CONTACTNUM
			+ " TEXT,"
			+ CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE
			+ " TEXT,"
			+ CVD_VARIABLES_P_DOB
			+ " TEXT,"
			+ CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE
			+ " TEXT,"
			+ CVD_VARIABLES_P_ADDRESS
			+ " TEXT,"
			+ CVD_VARIABLES_P_VILLAGE
			+ " TEXT, "
			+ CVD_VARIABLES_P_LOCALITY
			+ " TEXT,"
			+ CVD_VARIABLES_P_GENDER
			+ " TEXT,"
			+ CVD_VARIABLES_P_DOA
			+ " TEXT,"
			+ CVD_VARIABLES_P_AGE
			+ " TEXT,"
			+ KEY_AADHAR_NO
			+ " TEXT,"
			+ CVD_VARIABLES_PH_HRTATTACK
			+ " TEXT,"
			+ CVD_VARIABLES_PH_BP
			+ " TEXT,"
			+ CVD_VARIABLES_PH_BP_SINCE
			+ " TEXT,"
			+ CVD_VARIABLES_PH_BP_MEDICATION
			+ " TEXT,"
			+ CVD_VARIABLES_PH_STROKE
			+ " TEXT,"
			+ CVD_VARIABLES_PH_PVD
			+ " TEXT,"
			+ CVD_VARIABLES_PH_PVD_SINCE
			+ " TEXT,"
			+ CVD_VARIABLES_PH_DM
			+ " TEXT,"
			+ CVD_VARIABLES_PH_HRTATTACK_SINCE
			+ " TEXT,"
			+ CVD_VARIABLES_PH_STROKE_SINCE
			+ " TEXT,"
			+ CVD_VARIABLES_PH_DM_SINCE
			+ " TEXT,"
			+ CVD_VARIABLES_FH_HRATTACK
			+ " TEXT,"
			+ CVD_VARIABLES_FH_STROKE
			+ " TEXT,"
			+ CVD_VARIABLES_FH_DM
			+ " TEXT,"
			+ CVD_VARIABLES_RH_Q1
			+ " TEXT,"
			+ CVD_VARIABLES_RH_Q2
			+ " TEXT,"
			+ CVD_VARIABLES_REFERALH_Q2_IF_YES
			+ " TEXT,"
			+ CVD_VARIABLES_REFERALH_Q2_IF_NO_REASON
			+ " TEXT,"
			+ CVD_VARIABLES_REFERALH_Q2_IF_OTHERS_REASON
			+ " TEXT,"
			+ CVD_VARIABLES_TH_BP
			+ " TEXT,"
			+ CVD_VARIABLES_TH_APTT
			+ " TEXT,"
			+ CVD_VARIABLES_TH_LLTT
			+ " TEXT,"
			+ CVD_VARIABLES_TH_DM
			+ " TEXT,"
			+ CVD_VARIABLES_SH_SMOKING_QUESTION
			+ " TEXT,"
			+ CVD_VARIABLES_SH_CURRSMO
			+ " TEXT,"
			+ CVD_VARIABLES_SH_AGEATSMO
			+ " TEXT,"
			+ CVD_VARIABLES_SH_QUITSMO
			+ " TEXT,"
			+ CVD_VARIABLES_SH_CURR_CHEW
			+ " TEXT,"
			+ CVD_VARIABLES_SH_AGEAT_CHEW
			+ " TEXT,"
			+ CVD_VARIABLES_SH_QUIT_CHEW
			+ " TEXT,"
			+ CVD_VARIABLES_SBP1
			+ " TEXT,"
			+ CVD_VARIABLES_DBP1
			+ " TEXT,"
			+ CVD_VARIABLES_SBP2
			+ " TEXT,"
			+ CVD_VARIABLES_DBP2
			+ " TEXT,"
			+ CVD_VARIABLES_SBP3
			+ " TEXT,"
			+ CVD_VARIABLES_DBP3
			+ " TEXT,"
			+ CVD_VARIABLES_HR1
			+ " TEXT,"
			+ CVD_VARIABLES_HR2
			+ " TEXT ,"
			+ CVD_VARIABLES_HR3
			+ " TEXT ,"
			+ CVD_VARIABLES_SBP_AVG
			+ " TEXT ,"
			+ CVD_VARIABLES_DBP_AVG
			+ " TEXT ,"
			+ CVD_VARIABLES_HR_AVG
			+ " TEXT ,"
			+ CVD_VARIABLES_BPcuffSize
			+ " TEXT,"
			+ CVD_VARIABLES_PULSE
			+ " TEXT ,"
			+ CVD_VARIABLES_FASTING6HRS
			+ " TEXT ,"
			+ CVD_VARIABLES_ACQUISITION_TIME
			+ " TEXT ,"
			+ CVD_VARIABLES_BG_MGDL
			+ " TEXT ,"
			+ CVD_VARIABLES_TC
			+ " TEXT ,"
			+ CVD_VARIABLES_LDL
			+ " TEXT ,"
			+ CVD_VARIABLES_HDL
			+ " TEXT ,"
			+ CVD_VARIABLES_TG
			+ " TEXT ,"
			+ CVD_VARIABLES_CHOL_RESULTDATE
			+ " TEXT ,"
			+ CVD_VARIABLES_HT
			+ " TEXT ,"
			+ CVD_VARIABLES_WT
			+ " TEXT ,"
			+ CVD_VARIABLES_BG_EATorDRINK
			+ " TEXT ,"
			+ CVD_VARIABLES_BP_PERDAY
			+ " TEXT ,"
			+ CVD_VARIABLES_BP_PERWEEK
			+ " TEXT ,"
			+ CVD_VARIABLES_BP_PERLASTWEEK
			+ " TEXT ,"
			+ CVD_VARIABLES_BP_PERYESTERDAY
			+ " TEXT ,"
			+ CVD_VARIABLES_LP_PERDAY
			+ " TEXT ,"
			+ CVD_VARIABLES_LP_PERWEEK
			+ " TEXT ,"
			+ CVD_VARIABLES_LP_PERLASTWEEK
			+ " TEXT ,"
			+ CVD_VARIABLES_LP_PERYESTERDAY
			+ " TEXT ,"
			+ CVD_VARIABLES_AT_PERDAY
			+ " TEXT ,"
			+ CVD_VARIABLES_AT_PERWEEK
			+ " TEXT ,"
			+ CVD_VARIABLES_AT_PERLASTWEEK
			+ " TEXT ,"
			+ CVD_VARIABLES_AT_PERYESTERDAY
			+ " TEXT ,"
			+ CVD_VARIABLES_BP_DRUGLIST
			+ " TEXT ,"
			+ CVD_VARIABLES_STATIN_DRUGLIST
			+ " TEXT ,"
			+ CVD_VARIABLES_ASPIRIN_DRUGLIST
			+ " TEXT ,"
			+ CVD_VARIABLES_BP
			+ " TEXT ,"
			+ CVD_VARIABLES_STATIN
			+ " TEXT ,"
			+ CVD_VARIABLES_ASPIRIN
			+ " TEXT ,"
			+ CVD_VARIABLES_BP_REASON
			+ " TEXT ,"
			+ CVD_VARIABLES_STATIN_REASON
			+ " TEXT ,"
			+ CVD_VARIABLES_ASPIRIN_REASON
			+ " TEXT ,"
			+ CVD_VARIABLES_TT_PRES
			+ " TEXT ,"
			+ CVD_VARIABLES_REFCODE
			+ " TEXT ,"
			+ CVD_VARIABLES_RISKSCORE
			+ " TEXT ,"
			+ CVD_VARIABLES_SMOKER
			+ " TEXT ,"
			+ CVD_VARIABLES_DOC_REF
			+ " TEXT ,"
			+ CVD_VARIABLES_NEXT_VISIT_1MONTH
			+ " TEXT ,"
			+ CVD_VARIABLES_TT_ADHER
			+ " TEXT ,"
			+ CVD_VARIABLES_MED_RECEIVED
			+ " TEXT ,"
			+ CVD_VARIABLE_UPDATED_DATE
			+ " TEXT ,"
			+ CVD_VARIABLE_CREATED_DATE
			+ " TEXT ,"
			+ CVD_VARIABLE_CURRENT_USER_LOGIN
			+ " TEXT ,"
			+ CVD_VARIABLE_CURRENT_USER_ROLE
			+ " TEXT ,"
			+ CVD_VARIABLE_LAST_ENCOUNTER
			+ " TEXT ,"
			+ CVD_VARIABLE_CURRENT_ENCOUNTER
			+ " TEXT, "
			+ CVD_VARIABLES_ENC_DATE
			+ " TEXT ,"
			+ CVD_VARIABLES_ENC_type
			+ " TEXT ,"
			+ CVD_VARIABLES_PHC_NAME
			+ " TEXT ,"
			+ CVD_VARIABLES_ASHA_ASSIGNED
			+ " TEXT ,"
			+ CVD_VARIABLES_DIAB_CALC
			+ " TEXT ,"
			+ CVD_VARIABLES_SMOKER_TL
			+ " TEXT ,"
			+ CVD_VARIABLES_AR_RECOM
			+ " TEXT ,"
			+ CVD_VARIABLES_NV_AR
			+ " TEXT ,"
			+ CVD_VARIABLES_NV_AR_TL
			+ " TEXT ,"
			+ CVD_VARIABLES_PH_CVD_CALC
			+ " TEXT ,"
			+ CVD_VARIABLES_HIGH_RISK_CALC
			+ " TEXT ,"
			+ CVD_VARIABLES_NV_DIAB
			+ " TEXT ,"
			+ CVD_VARIABLES_NV_DIAB_TL
			+ " TEXT ,"
			+ CVD_VARIABLES_REF_DOC_TL
			+ " TEXT ,"
			+ CVD_VARIABLES_TARGET_SBP
			+ " TEXT ,"
			+ CVD_VARIABLES_TARGET_SBP_TL
			+ " TEXT ,"
			+ CVD_VARIABLES_TARGET_DBP
			+ " TEXT ,"
			+ CVD_VARIABLES_TARGET_DBP_TL + " TEXT " + " ) ";

	/*
	 * public static final String TABLE_MAINRISK_CREATE =
	 * "CREATE TABLE IF NOT EXISTS " + TABLE_P_M_HT + "(" + KEY_ID +
	 * " INTEGER PRIMARY KEY," + KEY_PATIENT_ID + " INTEGER," +
	 * CVD_VARIABLES_P_CNUMBER + " TEXT," + CVD_VARIABLES_P_FIRSTNAME + " TEXT,"
	 * + CVD_VARIABLES_P_SURNAME + " TEXT," + CVD_VARIABLES_P_CONTACTNUM +
	 * " TEXT," + CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE + " TEXT," +
	 * CVD_VARIABLES_P_DOB + " TEXT," + CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE +
	 * " TEXT," + CVD_VARIABLES_P_ADDRESS + " TEXT," + CVD_VARIABLES_P_VILLAGE +
	 * " TEXT," + CVD_VARIABLES_P_GENDER + " TEXT," + CVD_VARIABLES_P_DOA +
	 * " TEXT," + CVD_VARIABLES_P_AGE + " TEXT," + CVD_VARIABLES_PH_HRTATTACK +
	 * " TEXT," + CVD_VARIABLES_PH_STROKE + " TEXT," + CVD_VARIABLES_PH_PVD +
	 * " TEXT," + CVD_VARIABLES_PH_DM + " TEXT," + CVD_VARIABLES_FH_HRATTACK +
	 * " TEXT," + CVD_VARIABLES_FH_STROKE + " TEXT," + CVD_VARIABLES_FH_DM +
	 * " TEXT," + CVD_VARIABLES_RH_Q1 + " TEXT," + CVD_VARIABLES_RH_Q2 +
	 * " TEXT," + CVD_VARIABLES_REFERALH_Q2_IF_YES + " TEXT," +
	 * CVD_VARIABLES_REFERALH_Q2_IF_NO_REASON + " TEXT," + CVD_VARIABLES_TH_BP +
	 * " TEXT," + CVD_VARIABLES_TH_APTT + " TEXT," + CVD_VARIABLES_TH_LLTT +
	 * " TEXT," + CVD_VARIABLES_TH_NOD_BP + " TEXT," + CVD_VARIABLES_TH_NOD_LLTT
	 * + " TEXT," + CVD_VARIABLES_TH_NOD_APTT + " TEXT," +
	 * CVD_VARIABLES_TH_TOLAST_BP + " TEXT," + CVD_VARIABLES_TH_TOLAST_LLTT +
	 * " TEXT," + CVD_VARIABLES_TH_TOLAST_APTT + " TEXT," +
	 * CVD_VARIABLES_TH_LWEEK_BP + " TEXT," + CVD_VARIABLES_TH_LWEEK_LLTT +
	 * " TEXT," + CVD_VARIABLES_TH_LWEEK_APTT + " TEXT," +
	 * CVD_VARIABLES_TH_STERDAY_BP + " TEXT," + CVD_VARIABLES_TH_STERDAY_LLTT +
	 * " TEXT," + CVD_VARIABLES_TH_STERDAY_APTT + " TEXT," +
	 * CVD_VARIABLES_SH_SMOKING_QUESTION + " TEXT," + CVD_VARIABLES_SH_CURRSMO +
	 * " TEXT," + CVD_VARIABLES_SH_AGEATSMO + " TEXT," +
	 * CVD_VARIABLES_SH_QUITSMO + " TEXT," + CVD_VARIABLES_SH_CURR_CHEW +
	 * " TEXT," + CVD_VARIABLES_SH_AGEAT_CHEW + " TEXT," +
	 * CVD_VARIABLES_SH_QUIT_CHEW + " TEXT," + CVD_VARIABLES_SBP1 + " TEXT," +
	 * CVD_VARIABLES_DBP1 + " TEXT," + CVD_VARIABLES_SBP2 + " TEXT," +
	 * CVD_VARIABLES_DBP2 + " TEXT," + CVD_VARIABLES_SBP3 + " TEXT," +
	 * CVD_VARIABLES_DBP3 + " TEXT," + CVD_VARIABLES_HR1 + " TEXT," +
	 * CVD_VARIABLES_HR2 + " TEXT ," + CVD_VARIABLES_HR3 + " TEXT ," +
	 * CVD_VARIABLES_SBP_AVG + " TEXT ," + CVD_VARIABLES_DBP_AVG + " TEXT ," +
	 * CVD_VARIABLES_HR_AVG + " TEXT ," + CVD_VARIABLES_BPcuffSize + " TEXT," +
	 * CVD_VARIABLES_PULSE + " TEXT ," + CVD_VARIABLES_FASTING6HRS + " TEXT ," +
	 * CVD_VARIABLES_BG_MGDL + " TEXT ," + CVD_VARIABLES_TC + " TEXT ," +
	 * CVD_VARIABLES_LDL + " TEXT ," + CVD_VARIABLES_HDL + " TEXT ," +
	 * CVD_VARIABLES_TG + " TEXT ," + CVD_VARIABLES_CHOL_RESULTDATE + " TEXT ,"
	 * + CVD_VARIABLES_HT + " TEXT ," + CVD_VARIABLES_WT + " TEXT ," +
	 * CVD_CALCULATION_MODE + " TEXT ," + CVD_VARIABLES_QRCODE + " TEXT ," +
	 * CVD_VARIABLE_UPDATED_DATE + " TEXT ," + CVD_VARIABLE_CREATED_DATE +
	 * " TEXT ," + CVD_VARIABLE_CURRENT_USER_LOGIN + " TEXT ," +
	 * CVD_VARIABLE_CURRENT_USER_ROLE + " TEXT ," + CVD_VARIABLE_LAST_ENCOUNTER
	 * + " INTEGER ," + CVD_VARIABLE_CURRENT_ENCOUNTER + " INTEGER " + " ) ";
	 */

	protected void hideVirtualKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}

}
