package db;

import https.HTTPConstants;

import android.net.Uri;
import android.provider.BaseColumns;

public class CVDBase implements BaseColumns {

    public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";
    public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";
    public static final String MIME_ITEM = "vnd.ibme.gi.cvdrisk";
    public static final String MIME_TYPE_SINGLE = CVDBase.MIME_ITEM_PREFIX + "/" + CVDBase.MIME_ITEM;
    public static final String MIME_TYPE_MULTIPLE = CVDBase.MIME_DIR_PREFIX + "/" + CVDBase.MIME_ITEM;

    public static final String AUTHORITY = HTTPConstants.PACKAGE_NAME;
    public static final String PATH_CACHE_SINGLE = "cache/#";
    public static final String PATH_CACHE_MULTIPLE = "cache";
    public static final String PATH_RA_SINGLE = "assessments/#";
    public static final String PATH_RA_MULTIPLE = "assessments";
    public static final String PATH_CREDENTIALS_SINGLE = "credentials/#";
    public static final String PATH_CREDENTIALS_MULTIPLE = "credentials";
    public static final String PATH_FLAGGEDPATIENT_SINGLE = "flaggedpatients/#";
    public static final String PATH_FLAGGEDPATIENT_MULTIPLE = "flaggedpatients";
    public static final String PATH_BPMEASUREMENTS_SINGLE = "bpmeasurements/#";
    public static final String PATH_BPMEASUREMENTS_MULTIPLE = "bpmeasurements";
    public static final String PATH_BGMEASUREMENTS_SINGLE = "bgmeasurements/#";
    public static final String PATH_BGMEASUREMENTS_MULTIPLE = "bgmeasurements";
    public static final String PATH_QUESTIONNAIRE_SINGLE = "cvdquestionnaire/#";
    public static final String PATH_QUESTIONNAIRE_MULTIPLE = "cvdquestionnaires";

    public static final Uri CONTENT_URI_RA = Uri.parse("content://" + CVDBase.AUTHORITY + "/" + CVDBase.PATH_RA_MULTIPLE);
    public static final Uri CONTENT_URI_CREDENTIALS = Uri.parse("content://" + CVDBase.AUTHORITY + "/" + CVDBase.PATH_CREDENTIALS_MULTIPLE);
    public static final Uri CONTENT_URI_FLAGGEDPATIENTS = Uri.parse("content://" + CVDBase.AUTHORITY + "/" + CVDBase.PATH_FLAGGEDPATIENT_MULTIPLE);
    public static final Uri CONTENT_URI_BP = Uri.parse("content://" + CVDBase.AUTHORITY + "/" + CVDBase.PATH_BPMEASUREMENTS_MULTIPLE);
    public static final Uri CONTENT_URI_BG = Uri.parse("content://" + CVDBase.AUTHORITY + "/" + CVDBase.PATH_BGMEASUREMENTS_MULTIPLE);
    public static final Uri CONTENT_URI_QA = Uri.parse("content://" + CVDBase.AUTHORITY + "/" + CVDBase.PATH_QUESTIONNAIRE_MULTIPLE);
    public static final Uri CONTENT_URI_CACHE = Uri.parse("content://" + CVDBase.AUTHORITY + "/" + CVDBase.PATH_CACHE_MULTIPLE);
    public static final String DEFAULT_SORT_ORDER = null;//"updated DESC";
    
    //fields
    
    //1.Assessment - extended to the entire package
    //STEP1
    public static final String PID = "dbPatient_name";
    public static final String P_SURNAME = "dbsurname";
    public static final String P_GIVENNAME = "dbgivenname";
    public static final String P_CONSENT_NO = "dbPatient_consentNo";
    public static final String P_CONTACTNUMBER = "dbPatient_contaktnumber";
    public static final String P_CHKBOX_CONTACTPHONE_UNSURE = "IsyourphoneShared";
    public static final String P_ADDRESS = "dbPatient_address";
    public static final String P_DOB_CHKBOX_DDMM_UNSURE = "unsureofDob_MM_YY";
    public static final String P_DOB = "dbDateofBirth";
    public static final String P_DOA = "dbDateofAssessment";
    public static final String P_VILLAGE = "dbPatientVillage";
    public static final String P_AGE = "dbage";
    public static final String P_GENDER = "dbgender";
    //STEP 2
    public static final String P_PH_ANGINA = "pastHistory_Angina";
    public static final String P_PH_HRTATTACK = "pastHistory_HRTATTACK";
    public static final String P_PH_DM = "pastHistory_DM";
    public static final String P_PH_STROKE = "pastHistory_Stroke";
    public static final String P_PH_PVD = "pastHistory_PVD";
    
    public static final String P_FH_CVD = "dbpatientFH_CVD";
    public static final String P_FH_DM = "dbpatientFH_DM";
    
    public static final String P_RH_Q1 = "dbreferalques1";
    public static final String P_RH_Q2 = "dbreferalques2";
    
    public static final String P_TH_BP = "dbTreatmentHistoryTH_BP";
    public static final String P_TH_LLTT = "dbTreatmentHistoryTH_LLTT";
    public static final String P_TH_APTT = "dbTreatmentHistoryTH_APTT";
    
    public static final String P_SMOKER = "dbpatientSMOKER";
    public static final String P_SH_QUITSMO = "dbSmokingHistorySH_QUIT_WITHIN1YEAR";
        
    //STEP3
    public static final String P_SBPAV = "dbpatientSBPAV";
    public static final String P_DBPAV = "dbpatientDBPAV";
    public static final String P_PULSEAV = "dbpatientHRAV";
    public static final String P_SBP_1 = "dbpatientSBP_1";
    public static final String P_DBP_1 = "dbpatientDBP_1";
    public static final String P_PULSE_1 = "dbpatientHR_1";
    public static final String P_SBP_2 = "dbpatientSBP_2";
    public static final String P_DBP_2 = "dbpatientDBP_2";
    public static final String P_PULSE_2 = "dbpatientHR_2";
    public static final String P_SBP_3 = "dbpatientSBP_3";
    public static final String P_DBP_3 = "dbpatientDBP_3";
    public static final String P_PULSE_3 = "dbpatientHR_3";
    public static final String P_DM = "dbpatientDM";
    public static final String P_DM_FASTING_6HRS = "dbpatientDM_fasting6HrsChkBox";
    public static final String P_TC = "dbpatient_TC";
    public static final String P_HDL = "dbpatient_HDLc";
    public static final String P_LDL = "dbpatient_LDLc";
    public static final String P_TG = "dbpatient_TriGlycerides";
    public static final String P_CHOL_RESULTDATE = "dbpatient_CHOL_RESULTDATE";
    public static final String P_HT = "dbpatient_height";
    public static final String P_WT = "dbpatient_weight";
    
    //STEP 4
    public static final String P_ABRISK_RECOM = "dbpatient_abriskrecom";
    public static final String P_SMOKER_TFL = "dbpatient_smokertfl";
    public static final String P_SCREENING_DM = "dbpatient_screeningforDM";
    public static final String P_SCREENING_DM_TFL = "dbpatient_screeningforDM_TFL";
    public static final String P_SCREENING_ABRISK = "dbpatient_screeningforAbsoluteRisk";
    public static final String P_SCREENING_ABRISK_TFL = "dbpatient_screeningforAbsoluteRisk_TFL";
    public static final String P_DOCREF = "dbpatient_doctorReferral";
    public static final String P_DOCREF_TFL = "dbpatient_doctorReferral_TFL";
    
    public static final String P_BP_MED = "dbpatient_bplowering";
    public static final String P_LLTT_MED = "dbpatient_lipidloweringmed";
    public static final String P_APTT_MED = "dbpatient_apttlowering";
    
    public static final String P_TARGET_SBP = "dbpatient_targetSBP";
    public static final String P_TARGET_DBP = "dbpatient_targetDBP";
    
    public static final String P_BP_MED_TFL = "dbpatient_bplowering_TFL";
    public static final String P_LLTT_MED_TFL = "dbpatient_lipidloweringmed_TFL";
    public static final String P_APTT_MED_TFL = "dbpatient_apttlowering_TFL";
    
    public static final String P_TARGET_SBP_TFL = "dbpatient_targetSBP_TFL";
    public static final String P_TARGET_DBP_TFL = "dbpatient_targetDBP_TFL";
    
    public static final String P_CVD = "dbpatientCVD";
    public static final String P_CVDSCORE = "dbpatientCVDriskscore";
    
    //2.Credential
    public static final String USER = "dbUser";
    public static final String TYPE = "dbType";
    public static final String VILLAGE = "dbUserVillage";
    public static final String USER_ID = "dbUser_ID";
    public static final String USER_PASS = "dbUser_pass";
    public static final String USER_LOG_IN_TIME = "dbUser_login_time"; //timestamp@ login
    public static final String USER_LOG_OUT_TIME = "dbUser_log_out_time"; //timestamp@ 'Save&Upload' 
    //3.Flagged High Risk patients
    //same as 1.
    
    //4.BP measurement
    public static final String BP_DATE = "dbBPmeasure_date";
    public static final String BP_START_TIME = "dbBPstart_time";
    public static final String BP_END_TIME = "dbBPend_time";
    public static final String BP_SBP = "dbBPmeasure_SBP";
    public static final String BP_DBP = "dbBPmeasure_DBP";
    public static final String BP_HR = "dbBPmeasure_HR";
    
    //5.BG measurement
    public static final String BG_DATE = "dbBGmeasure_date";
    public static final String BG_START_TIME = "dbBGstart_time";
    public static final String BG_END_TIME = "dbBGend_time";
    public static final String BG_VALUE = "dbBGmeasure_value";
    public static final String BG_FASTING6HRS = "dbBGmeasure_fasting6hrs";
    
    //6.Questionnaire
    public static final String Q_1 = "answer_to_Question1";
    public static final String Q_2 = "answer_to_Question2";
    public static final String Q_3 = "answer_to_Question3";
    public static final String Q_4 = "answer_to_Question4";
    public static final String Q_5 = "answer_to_Question5";
    public static final String Q_6 = "answer_to_Question6";
    
    //7.Cache
    public static final String RA_PROCEDURE_COMPLETED = "WhetherRA_completed";
    public static final String BP_check1 = "WhetherBP_box1isFilled";
    public static final String BP_check2 = "WhetherBP_box2isFilled";
    public static final String BP_check3 = "WhetherBP_box3isFilled";
}