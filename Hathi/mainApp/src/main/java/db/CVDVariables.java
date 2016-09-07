package db;

/* class: CVDVariables. java
 * This is the base class comprising all the variables involved.
 * 
 * Copyright (C) 2011 Arvind Raghu, IBME, University of Oxford, UK
 * Last Revised: 27th Sep, 2011
 */
/*
 * This program is licensed under the GNU General Public License, version 3 and is distributed in the hope that it will be useful, but WITHOUT ANY
 WARRANTY. You may contact the author by e-mail (airwind266@gmail.com). 
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//import org.ibme.gi.algorithm.patientTemplate;
//import org.ibme.gi.csvparsing.CSVReader;
//import org.ibme.gi.csvparsing.DatabaseHelper;
import db.DatabaseConnecter;
import db.DatabaseVariables;
import db.utils.PatientModel;
import utils.PersistanceData;
import db.utils.UserModel;
//import org.ibme.gi.mainRCT_healthtrackercvd.jsonparsing.JsonParsing;
//import org.ibme.gi.mainRCT_healthtrackercvd.logger.CVDappLogger;
import https.HTTPConstants;
import org.pnuemoniaproject.hathi.R;
//import org.ibme.gi.service.HttpAsyncServiceHelper;
//import org.ibme.gi.service.RequestFactory;
//import org.ibme.gi.service.Response;
//import org.ibme.gi.service.ServiceListener;
import utils.AppUtil;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CVDVariables extends Activity {

	//John Variables
	// @ww
	public static String J_VARIABLES_ROW_POINTER = "";
	public static String J_VARIABLES_FIRST_NAME = "";
	public static String J_VARIABLES_LAST_NAME = "";
	public static String J_VARIABLES_P_ID = "";



	//

	public static PatientModel selectedPatientRecord;
	static PatientModel selectedPatient;
	static db.utils.UserModel usermodel;
	public static UserModel selectedUser;

	public static final String DATE_FORMAT = "dd-MM-yyyy";

	public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

	public static final String DATE_FORMAT_DELIMITTER = "-";

	public static final String CVD_VARIABLE_CURRENT_USER_LOGIN = "current_user_login";
	public static final String CVD_VARIABLE_CURRENT_USER_ROLE = "current_user_role";

	public static final String STRING_YES = "Yes";
	public static final String STRING_NO = "No";

	public static final int YES = 1;
	public static final int NO = 0;
	public static final int DK = 2;
	public static final int REFERALH_Q2__IFYES_OP1 = 1;
	public static final int REFERALH_Q2__IFYES_OP2 = 2;
	public static final int REFERALH_Q2__IFYES_OP3 = 3;
	public static final int REFERALH_Q2__IFNO_OP1 = 1;
	public static final int REFERALH_Q2__IFNO_OP2 = 2;

	public static final int REFERALH_Q2__IFNO_OP3 = 3;

	public static final int REFERALH_Q2__IFNO_OP4 = 4;

	public static boolean isFromNewPatient = true;

	public static boolean quick_or_fullreview = false;

	public static boolean buttonclick = true;

	public static final String VARIABLE_MISSING = "-1";

	public SharedPreferences prefs;

	public static boolean fillavgvalues = false;

	public static boolean fillavgvalues_quick = false;

	public static boolean quickreview = false;;

	public SharedPreferences.Editor editorShared;
	public static final int NavStep1 = 1;
	public static final int NavStep2 = 2;
	public static final int NavStep3 = 3;
	public static final int NavStep4 = 4;
	public static final int NavStep5 = 5;
	public static final int Nav_disable = -1;

	public static String appLoggerFileName = "HTpilot_CVDappLogger";
	// shared variables for Settings and User handling
	public static final String COMMON = "common_prefs";
	public static final String COMMON_USER_TYPE = "ashaORdoctor";
	public static final String COMMON_USER_ID = "userid";
	public static final String COMMON_USER_PHC = "userunderwhichPHC";
	public static final String COMMON_USER_VILLAGE = "usersVillage";
	public static final String COMMON_USER_NAME = "ashadocadmin";
	public static final String COMMON_RA_START_TIME = "startofNewRiskAssessment";
	public ProgressBar progressBar_Step;
	//public patientTemplate currentPatient;
	// shared variables for CVD risk factors
	public static final String CVD_VARIABLES = "cvd_variables";
	public static final String CVD_VARIABLES_PID = "_id";
	public static final String KEY_PATIENT_ID = "patient_id";

	public static final String CVD_VARIABLES_NA = "NA";

	public static final String CVD_VARIABLES_P_SURNAME = "sur_name";
	public static final String CVD_VARIABLES_P_FIRSTNAME = "first_name";
	public static final String CVD_VARIABLES_P_DOB = "dob";
	public static final String CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE = "dob_unsure";
	public static final String CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE = "shared_phone";
	public static final String CVD_VARIABLES_P_DOA = "doa";
	public static final String CVD_VARIABLES_P_GENDER = "gender";

	// @ww
	public static final String CVD_VARIABLES_P_CNUMBER = "consent_num";
	public static final String CVD_VARIABLES_P_ADDRESS = "address";
	public static final String CVD_VARIABLES_P_VILLAGE = "vill_name";
	public static final String CVD_VARIABLES_P_LOCALITY = "locality";

	public static final String CVD_VARIABLES_P_CONTACTNUM = "contact_num";
	public static final String CVD_VARIABLES_P_AGE = "age";

	public static final String CVD_VARIABLES_PH_ANGINA = "ph_angina";
	public static final String CVD_VARIABLES_PH_HRTATTACK = "ph_hrtattack";
	public static final String CVD_VARIABLES_PH_PVD = "ph_pvd";
	public static final String CVD_VARIABLES_PH_DM = "ph_diab";
	public static final String CVD_VARIABLES_PH_STROKE = "ph_stroke";
	public static final String CVD_VARIABLES_FH_HRATTACK = "fh_hrtattack";
	public static final String CVD_VARIABLES_FH_STROKE = "fh_stroke";

	public static final String CVD_VARIABLES_PH_BP = "ph_bp";

	public static final String CVD_VARIABLES_PH_BP_SINCE = "ph_bp_since";

	public static final String CVD_VARIABLES_PH_BP_MEDICATION = "ph_medication";

	public static final String CVD_VARIABLES_FH_CVD = "fh_cardiovasculardisease";
	public static final String CVD_VARIABLES_FH_DM = "fh_diab";

	 public static final String CVD_VARIABLES_DOC_REF = "ref_doc";

	public static final String CVD_VARIABLES_RH_Q1 = "rh_q1";
	public static final String CVD_VARIABLES_RH_Q2 = "rh_q2";
	public static final String CVD_VARIABLES_REFERALH_Q2_IF_YES = "rh_q2_yes";
	public static final String CVD_VARIABLES_REFERALH_Q2_IF_NO_REASON = "rh_q2_no";

	public static final String CVD_VARIABLES_REFERALH_Q2_IF_OTHERS_REASON = "rh_ques2_if_other_reason";

	public static final String CVD_VARIABLES_TH_BP = "th_bp";
	public static final String CVD_VARIABLES_TH_APTT = "th_aptt";
	public static final String CVD_VARIABLES_TH_LLTT = "th_lltt";

	public static final String CVD_VARIABLES_TH_DM = "th_diab";

	public static final String KEY_P_REFERALCARD_NUM = "referalcard_num";

	public static final String KEY_P_LAST_ENCOUNTER = "last_encounter";

	public static final String KEY_AADHAR_NO = "aadhar_num";

	// ......................................................................

	public static final String CVD_VARIABLES_BP_PERDAY = "bp_perday";
	public static final String CVD_VARIABLES_BP_PERWEEK = "bp_perweek";
	public static final String CVD_VARIABLES_BP_PERLASTWEEK = "bp_perlastweek";
	public static final String CVD_VARIABLES_BP_PERYESTERDAY = "bp_peryesterday";

	public static final String CVD_VARIABLES_LP_PERDAY = "lltt_perday";
	public static final String CVD_VARIABLES_LP_PERWEEK = "lltt_perweek";
	public static final String CVD_VARIABLES_LP_PERLASTWEEK = "lltt_perlastweek";
	public static final String CVD_VARIABLES_LP_PERYESTERDAY = "lltt_peryesterday";

	public static final String CVD_VARIABLES_AT_PERDAY = "aptt_perday";
	public static final String CVD_VARIABLES_AT_PERWEEK = "aptt_perweek";
	public static final String CVD_VARIABLES_AT_PERLASTWEEK = "aptt_perlastweek";
	public static final String CVD_VARIABLES_AT_PERYESTERDAY = "aptt_peryesterday";

	// -------------------------------------------------------------------------------------------

	public static final String CVD_VARIABLES_SH_CURRSMO = "sh_current";
	public static final String CVD_VARIABLES_SH_QUITSMO = "sh_quit";

	public static final String CVD_VARIABLES_SBP1 = "sbp1";
	public static final String CVD_VARIABLES_DBP1 = "dbp1";
	public static final String CVD_VARIABLES_SBP2 = "sbp2";
	public static final String CVD_VARIABLES_DBP2 = "dbp2";
	public static final String CVD_VARIABLES_SBP3 = "sbp3";
	public static final String CVD_VARIABLES_DBP3 = "dbp3";
	public static final String CVD_VARIABLES_HR1 = "hr1";
	public static final String CVD_VARIABLES_HR2 = "hr2";
	public static final String CVD_VARIABLES_HR3 = "hr3";
	public static final String CVD_VARIABLES_BPcheck1 = "isBP1filled";
	public static final String CVD_VARIABLES_BPcheck2 = "isBP2filled";
	public static final String CVD_VARIABLES_BPcheck3 = "isBP3filled";

	public static final String CVD_VARIABLES_PULSE = "pulse";
	public static final String CVD_VARIABLES_FASTING6HRS = "bg_fasting_calc";
	public static final String CVD_VARIABLES_BG_MGDL = "bg_value";
	public static final String CVD_VARIABLES_TC = "tc";
	public static final String CVD_VARIABLES_LDL = "ldl";
	public static final String CVD_VARIABLES_HDL = "hdl";
	public static final String CVD_VARIABLES_TG = "tg";
	public static final String CVD_VARIABLES_CHOL_RESULTDATE = "chol_resultdate";
	public static final String CVD_VARIABLES_HT = "ht";
	public static final String CVD_VARIABLES_WT = "wt";
	public static final String CVD_VARIABLES_REFCODE = "ref_card_no";
	public static final String KEY_INTERVIEW_STATUS = "interview_status";

	public static final String CVD_VARIABLE_UPDATED_DATE = "update_date";
	public static final String CVD_VARIABLE_CREATED_DATE = "created_date";
	public static final String CVD_VARIABLE_LAST_ENCOUNTER = "last_encounter";
	public static final String CVD_VARIABLES_P_DOA_MONTH = "doa_mo";
	public static final String CVD_VARIABLES_P_DOA_YEAR = "doa_yr";
	public static final String CVD_VARIABLES_P_DOA_DAY = "doa_day";

	public static final String CVD_VARIABLES_P_DOB_MONTH = "dob_mo";
	public static final String CVD_VARIABLES_P_DOB_YEAR = "dob_yr";
	public static final String CVD_VARIABLES_P_DOB_DAY = "dob_day";

	public static final String CVD_VARIABLE_CURRENT_ENCOUNTER = "current_encounter";

	public static final String CVD_VARIABLES_RISKSCORE = "cvd_risk";

	public static final String CVD_VARIABLES_BP_DRUGLIST = "bp_druglist";

	public static final String CVD_VARIABLES_STATIN_DRUGLIST = "lltt_druglist";

	public static final String CVD_VARIABLES_ASPIRIN_DRUGLIST = "aptt_druglist";

	public static final String CVD_VARIABLES_BP = "med_bp";

	public static final String CVD_VARIABLES_STATIN = "med_statin";

	public static final String CVD_VARIABLES_ASPIRIN = "med_aspirin";

	public static final String CVD_VARIABLES_BP_REASON = "med_bp_reason";

	public static final String CVD_VARIABLES_STATIN_REASON = "med_lltt_reason";

	public static final String CVD_VARIABLES_ASPIRIN_REASON = "med_aptt_reason";

	public static final String CVD_VARIABLES_ACQUISITION_TIME = "bg_rectime";

	public static final String CVD_CALCULATION_MODE = "lit";

	public static String DEFAULT_VALUE = "defaultValue";
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;

	public static final int MEDICINE_HISTORY_INFO_AVAILABLE = 1;
	public static final int MEDICINE_HISTORY_INFO_NOT_AVAILABLE = 2;
	public static final int MEDICINE_HISTORY_INFO_REFUSED = 3;

	public static final int PHY_ACT_INFO_AVAILABLE = 1;
	public static final int PHY_ACT_NO_ACT = 2;
	public static final int PHY_ACT_NOT_SURE = 3;
	public static final int PHY_ACT_REFUSED = 4;

	public static final int SS_SMOKER = 1;
	public static final int SS_CHEWER = 2;
	public static final int SS_BOTH = 3;
	public static final int SS_NONE = 4;

	public static final int QA_OPT_ONE = 1;
	public static final int QA_OPT_TWO = 2;
	public static final int QA_OPT_THREE = 3;

	// further questions variables

	public static final int FQ_OPT1 = 1;
	public static final int FQ_OPT2 = 2;
	public static final int FQ_OPT3 = 3;
	public static final int FQ_OPT4 = 4;
	public static final int FQ_OPT5 = 5;
	public static final int FQ_OPT6 = 6;

	static boolean shouldReloadData = false;
	static ImageView selectedPatientIndicator;

	private static Context mContext;

	public boolean bpflagforsettingvalues = false;

	public boolean ltflagforsettingvalues = false;

	public boolean atflagforsettingvalues = false;

	public boolean quickbpflagforsettingvalues = false;

	public boolean quickltflagforsettingvalues = false;

	public boolean quickatflagforsettingvalues = false;

	public static Map<String, String> storeBPvaluesBackPressed;

	public static boolean storeBPvaluesBackPressedflag = false;

	String patient_Id = "";

	//JsonParsing jsonParsing;

	DatabaseConnecter connector;

	String url = "";

	public static Map<String, String> bpAverageValues;

	/*
	 * public String getDataTime() { Date cDate = new Date(); // get Present
	 * date String fDateNtime = new SimpleDateFormat("dd-MM-yyyy HH:mm")
	 * .format(cDate);
	 * 
	 * return fDateNtime; }
	 */

	// shared variables for Settings and User handling

	public static final String PATIENT_ID = "patient_id";
	public static final String KEY_PATIENT_FNAME = "first_name";
	public static final String KEY_PATIENT_LNAME = "last_name";
	public static final String KEY_PATIENT_FNAME_TE = "first_name_te";
	public static final String KEY_PATIENT_LNAME_TE = "last_name_te";

	public static final String KEY_AGE = "age";

	public static final String KEY_CONTACT_NO_ISAVAILABLE = "contact_no_isavailable";

	public static final String KEY_CONTACT_NO = "contact_number";

	public static final String KEY_GENDER = "gender";

	public static final String KEY_P_DOB = "dob";

	public static final String KEY_P_AGE = "age";

	public static final String KEY_DOB_UNSURED = "dob_unsured";

	public static final String KEY_CONSENT_NUM = "consent_number";
	public static final String KEY_AGE_CHECKED = "dob_unsured"; // modified

	public static final String KEY_CONTACT_NUMBER = "contact_number";

	public static final String KEY_IS_CONTACT_NUMBER_SHARED = "is_contact_no_shared";

	public static final String KEY_ADDRESS = "address";

	public static final String CVD_VARIABLES_P_LISTED_AGE = "ListedAge";

	// @ww
	protected static final String NOT_AVAILABLE = "NA";

	public static final String CVD_VARIABLES_PH_HRTATTACK_SINCE = "ph_hrtattack_since";
	public static final String CVD_VARIABLES_PH_PVD_SINCE = "ph_pvd_since";
	public static final String CVD_VARIABLES_PH_DM_SINCE = "ph_diab_since";
	public static final String CVD_VARIABLES_PH_STROKE_SINCE = "ph_stroke_since";

	public static final String CVD_VARIABLES_TH_OWN = "fh_owm";

	public static final String CVD_VARIABLES_SH_SMOKING_QUESTION = "tobacco_ques";
	public static final String CVD_VARIABLES_SH_AGEATSMO = "sh_agestarted";
	public static final String CVD_VARIABLES_SH_CURR_CHEW = "ch_current";
	public static final String CVD_VARIABLES_SH_QUIT_CHEW = "ch_quit";
	public static final String CVD_VARIABLES_SH_AGEAT_CHEW = "ch_agestarted";

	public static final String CVD_VARIABLES_BPcuffSize = "bp_cuffsize";

	public static final String CVD_VARIABLES_SBP_AVG = "sbp_avg";
	public static final String CVD_VARIABLES_DBP_AVG = "dbp_avg";
	public static final String CVD_VARIABLES_HR_AVG = "hr_avg";

	public static final String CVD_VARIABLES_BG_EATorDRINK = "bg_lasteat";//
	public static final String CVD_VARIABLES_BG_SPEC_AQUI_TIME = "bg_bloodspecimen_aquisitionTime";

	public static final String CVD_VARIABLES_BH_TAKING_MEDI = "Bh_Takingmedication";
	public static final String CVD_VARIABLES_BH_FRAC_IN_PAST = "Bh_Fracture_In_History";
	public static final String CVD_VARIABLES_BH_FRAC_SITE = "Bh_Fracture_Location";

	public static final String CVD_VARIABLES_P_HEALTH_STATUS = "patient_health_status";

	public static final String CVD_VARIABLES_P_CBOX_CONTA_NOTAVIALBLE = "contactnum_notavailble";
	// public static final String CVD_VARIABLES_P_CHKBOX_SHAREDPHONE =
	// "shared_phone";

	public static final String CVD_VARIABLES_P_AADHAR_BINARY = "aadhar_yesorno";
	public static final String CVD_VARIABLES_P_AADHAR_CARDNUM = "aadhar_cardNum";
	public static final String CVD_VARIABLES_BPLT = "bp_lowering_treatment";
	public static final String CVD_VARIABLES_LLTT = "lipidlowering_treatment";
	public static final String CVD_VARIABLES_AT = "antiplatelet_therapy";

	// new variables for posting open MRS

	public static final String CVD_VARIABLES_NEXT_VISIT_1MONTH = "next_visit_1month";

	public static final String CVD_VARIABLES_TT_PRES = "tt_pres";

	public static final String CVD_VARIABLES_TT_ADHER = "tt_adher";

	public static final String CVD_VARIABLES_MED_RECEIVED = "med_received";

	public static final String CVD_VARIABLES_SMOKER = "smoker_calc";

	// @ww new variables 17/10/2014

	public static final String CVD_VARIABLES_ENC_DATE = "enc_date";
	public static final String CVD_VARIABLES_ENC_type = "enc_type";

	public static final String CVD_VARIABLES_PHC_NAME = "phc_name";
	public static final String CVD_VARIABLES_ASHA_ASSIGNED = "asha_assigned";

	public static final String CVD_VARIABLES_DIAB_CALC = "diab_calc";
	public static final String CVD_VARIABLES_SMOKER_TL = "smoker_tl";

	public static final String CVD_VARIABLES_PH_CVD_CALC = "ph_cvd_calc";

	public static final String CVD_VARIABLES_HIGH_RISK_CALC = "high_risk_calc";

	public static final String CVD_VARIABLES_AR_RECOM = "ar_recom";

	public static final String CVD_VARIABLES_NV_AR = "nv_ar";
	public static final String CVD_VARIABLES_NV_AR_TL = "nv_ar_tl";

	public static final String CVD_VARIABLES_NV_DIAB = "nv_diab";
	public static final String CVD_VARIABLES_NV_DIAB_TL = "nv_diab_tl";
	public static final String CVD_VARIABLES_REF_DOC_TL = "ref_doc_tl";

	public static final String CVD_VARIABLES_TARGET_SBP = "target_sbp";

	public static final String CVD_VARIABLES_TARGET_SBP_TL = "target_sbp_tl";
	public static final String CVD_VARIABLES_TARGET_DBP = "target_dbp";
	public static final String CVD_VARIABLES_TARGET_DBP_TL = "target_dbp_tl";

	// public static final String CVD_VARIABLES_WT = "cvd_WT";

	private PersistanceData persistanceData = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		try {

			mContext = this;

			String code = null;

			registerReceiver(broadcastReceiver, new IntentFilter(
					"cvdLocaleChanged"));
			String langs = Locale.getDefault().getLanguage();

			if (langs != null && langs.equalsIgnoreCase("te")) {
				code = "telugu";
			} else if (langs != null && langs.equalsIgnoreCase("en")) {

				code = "english";
			}

			persistanceData = new PersistanceData(this);

			persistanceData.setLanguage(code);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			try {
				restartActivity();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v("BASELINE destroy", "BASELINE destroy");

		if (broadcastReceiver != null) {
			try {
				unregisterReceiver(broadcastReceiver);
				broadcastReceiver = null;
			} catch (final Exception e) {
			}
		}

	}

	protected void restartActivity() throws ClassNotFoundException {

		String code = "";

		PersistanceData persistanceData = new PersistanceData(this);

		try {

			String langs = Locale.getDefault().getLanguage();

			if (langs.equalsIgnoreCase("te")) {
				code = "telugu";
			} else if (langs.equalsIgnoreCase("en")) {

				code = "english";
			}

			persistanceData.setLanguage(code);

			ActivityManager am = (ActivityManager) this
					.getSystemService(ACTIVITY_SERVICE);

			List<RunningTaskInfo> taskInfo = am.getRunningTasks(2);

			for (RunningTaskInfo runningTaskInfo : taskInfo) {

				ComponentName componentInfo = runningTaskInfo.topActivity;

				if (componentInfo.getPackageName().equalsIgnoreCase(
						HTTPConstants.PACKAGE_NAME)) {
					String lang = Locale.getDefault().getLanguage();

					if (lang != null && lang.equalsIgnoreCase("te")) {

						code = "telugu";
					} else if (lang != null && lang.equalsIgnoreCase("en")) {

						code = "english";
					}

					Locale myLocale = new Locale(lang);
					Resources res = getResources();
					DisplayMetrics dm = res.getDisplayMetrics();
					Configuration conf = res.getConfiguration();
					conf.locale = myLocale;
					res.updateConfiguration(conf, dm);

					Class<?> current = Class
							.forName(runningTaskInfo.topActivity.getClassName());

					Intent intent = new Intent(this, current);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

					persistanceData.setLanguage(code);

					startActivity(intent);
					finish();

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public String theText(EditText etext) {

		return etext.getText().toString();

	}

	void hideVirtualKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}

	public static void makeToastinCenter(String message, Context ctx) {

		LinearLayout layout = new LinearLayout(ctx);

		TextView tv = new TextView(ctx);
		// set the TextView properties like color, size etc
		tv.setTextColor(Color.RED);
		tv.setTextSize(15);

		tv.setGravity(Gravity.CENTER_HORIZONTAL);

		// set the text you want to show in Toast
		tv.setText(message);

		layout.addView(tv);

		Toast toast = new Toast(ctx); // context is object of Context
										// write "this" if you are an
										// Activity
		// Set The layout as Toast View
		toast.setView(layout);

		// Position you toast here toast position is 50 dp from bottom you
		// can give any integral value
		toast.setGravity(Gravity.CENTER, 0, 50);
		toast.show();

	}

	protected void makeToastwithText(String toast_text) {
		Context context = getApplicationContext();
		CharSequence text = toast_text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	protected static String getAppLoggerFileName() {
		Calendar cal = Calendar.getInstance();
		String line = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "-"
				+ String.valueOf(cal.get(Calendar.MONTH)) + "-"
				+ String.valueOf(cal.get(Calendar.YEAR));
		String myString = appLoggerFileName + "_" + line;
		return myString;
	}

	/*protected String getAnswerforChoice(RadioGroup rgroup) {

		int selectedId = rgroup.getCheckedRadioButtonId();

		if (selectedId == R.id.radio_yes) {
			return String.valueOf(YES);
		} else if (selectedId == R.id.radio_no) {
			return String.valueOf(NO);
		} else if (selectedId == R.id.radio_dk) {
			return String.valueOf(DK);
		}

		else
			return VARIABLE_MISSING;
	}

	protected String getAnswerforChoiceRefNo(RadioGroup rgroup) {

		int selectedId = rgroup.getCheckedRadioButtonId();

		if (selectedId == R.id.radio_option1) {
			return String.valueOf(REFERALH_Q2__IFNO_OP1);
		} else if (selectedId == R.id.radio_option2) {
			return String.valueOf(REFERALH_Q2__IFNO_OP2);
		} else if (selectedId == R.id.radio_option3) {
			return String.valueOf(REFERALH_Q2__IFNO_OP3);
		} else if (selectedId == R.id.radio_option4) {
			return String.valueOf(REFERALH_Q2__IFNO_OP4);
		} else
			return VARIABLE_MISSING;
	}

	protected void setReferalRadioGroupForValueYes(String value,
			int rGrounpValue) {

		String result = value;
		if (null != result && !result.isEmpty()) {

			if (!(result.equalsIgnoreCase(DEFAULT_VALUE))) {
				RadioGroup rGroup = (RadioGroup) findViewById(rGrounpValue);

				if (result.equalsIgnoreCase(String
						.valueOf(REFERALH_Q2__IFYES_OP1))) {

					((RadioButton) rGroup.findViewById(R.id.radio_yes))
							.setChecked(true);
				} else if (result.equalsIgnoreCase(String
						.valueOf(REFERALH_Q2__IFYES_OP2)))

				{
					// ((RadioButton) findViewById(noId)).setChecked(true);
					((RadioButton) rGroup.findViewById(R.id.radio_no))
							.setChecked(true);
				} else if (result.equalsIgnoreCase(String
						.valueOf(REFERALH_Q2__IFYES_OP3)))

				{
					// ((RadioButton) findViewById(noId)).setChecked(true);
					((RadioButton) rGroup.findViewById(R.id.radio_dk))
							.setChecked(true);
				}
			}
		}
	}

	protected void setReferalRadioGroupForValueNo(String value, int rGrounpValue) {

		String result = value;
		if (null != result && !result.isEmpty()) {

			if (!(result.equalsIgnoreCase(DEFAULT_VALUE))) {
				RadioGroup rGroup = (RadioGroup) findViewById(rGrounpValue);

				if (result.equalsIgnoreCase(String
						.valueOf(REFERALH_Q2__IFNO_OP1))) {

					((RadioButton) rGroup.findViewById(R.id.radio_option1))
							.setChecked(true);
				} else if (result.equalsIgnoreCase(String
						.valueOf(REFERALH_Q2__IFNO_OP2)))

				{
					// ((RadioButton) findViewById(noId)).setChecked(true);
					((RadioButton) rGroup.findViewById(R.id.radio_option2))
							.setChecked(true);
				} else if (result.equalsIgnoreCase(String
						.valueOf(REFERALH_Q2__IFNO_OP3)))

				{
					// ((RadioButton) findViewById(noId)).setChecked(true);
					((RadioButton) rGroup.findViewById(R.id.radio_option3))
							.setChecked(true);
				} else if (result.equalsIgnoreCase(String
						.valueOf(REFERALH_Q2__IFNO_OP4)))

				{
					// ((RadioButton) findViewById(noId)).setChecked(true);
					((RadioButton) rGroup.findViewById(R.id.radio_option4))
							.setChecked(true);
				}
			}
		}
	}*/

	public void setRadioGroupFromShrPref(String constant, int rGrounpValue) {

		if (prefs.getString(constant, DEFAULT_VALUE) != null) {
			String result = prefs.getString(constant, DEFAULT_VALUE);
			if (!(result.equalsIgnoreCase(DEFAULT_VALUE))) {
				RadioGroup rGroup = (RadioGroup) findViewById(rGrounpValue);

				if (result.equalsIgnoreCase(String.valueOf(YES))) {

					((RadioButton) rGroup.findViewById(R.id.radio_yes))
							.setChecked(true);
				} else if (result.equalsIgnoreCase(String.valueOf(NO)))

				{
					// ((RadioButton) findViewById(noId)).setChecked(true);
					((RadioButton) rGroup.findViewById(R.id.radio_no))
							.setChecked(true);
				}
			}
		}

	}

	public void setRadioGroupForValuebP(String result, int rGrounpValue) {

		RadioGroup rGroup = (RadioGroup) findViewById(rGrounpValue);

		if (result.equalsIgnoreCase(String.valueOf(YES))) {

			((RadioButton) rGroup.findViewById(R.id.radio_yes))
					.setChecked(true);
		} else if (result.equalsIgnoreCase(String.valueOf(NO)))

		{
			((RadioButton) rGroup.findViewById(R.id.radio_no)).setChecked(true);
		}

	}

	public boolean LoadFromTempAddToDatabase(String table_name) {

		DatabaseConnecter dbConnecter = new DatabaseConnecter(this);

		HashMap<String, String> map = dbConnecter.getRAPatient(
				selectedPatientRecord.getPatient_ID(), table_name);

		if (map.isEmpty()) {

			return false;

		} else {

			Log.v("DATA  TO DB", map.toString());

			ContentValues values = new ContentValues();

			values.put(PATIENT_ID, map.get(PATIENT_ID));

			values.put(CVD_VARIABLES_P_CNUMBER,
					map.get(CVD_VARIABLES_P_CNUMBER));

			values.put(CVD_VARIABLES_P_SURNAME,
					map.get(CVD_VARIABLES_P_SURNAME));
			values.put(CVD_VARIABLES_P_FIRSTNAME,
					map.get(CVD_VARIABLES_P_FIRSTNAME));
			values.put(CVD_VARIABLES_P_CONTACTNUM,
					map.get(CVD_VARIABLES_P_CONTACTNUM));
			values.put(CVD_VARIABLES_P_GENDER, map.get(CVD_VARIABLES_P_GENDER));

			values.put(CVD_VARIABLES_P_DOB, map.get(CVD_VARIABLES_P_DOB));
			values.put(CVD_VARIABLES_P_DOA, map.get(CVD_VARIABLES_P_DOA));

			values.put(CVD_VARIABLES_P_ADDRESS,
					map.get(CVD_VARIABLES_P_ADDRESS));
			values.put(CVD_VARIABLES_P_VILLAGE,
					map.get(CVD_VARIABLES_P_VILLAGE));
			values.put(CVD_VARIABLES_P_LOCALITY,
					map.get(CVD_VARIABLES_P_LOCALITY));

			values.put(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE,
					map.get(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE));

			values.put(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE,
					map.get(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE));

			values.put(CVD_VARIABLES_P_AGE, map.get(CVD_VARIABLES_P_AGE));

			values.put(KEY_AADHAR_NO, map.get(KEY_AADHAR_NO));

			values.put(CVD_VARIABLES_PH_HRTATTACK,
					map.get(CVD_VARIABLES_PH_HRTATTACK));
			values.put(CVD_VARIABLES_PH_STROKE,
					map.get(CVD_VARIABLES_PH_STROKE));
			values.put(CVD_VARIABLES_PH_PVD, map.get(CVD_VARIABLES_PH_PVD));
			values.put(CVD_VARIABLES_PH_DM, map.get(CVD_VARIABLES_PH_DM));

			values.put(CVD_VARIABLES_PH_BP, map.get(CVD_VARIABLES_PH_BP));

			values.put(CVD_VARIABLES_PH_BP_SINCE,
					map.get(CVD_VARIABLES_PH_BP_SINCE));

			values.put(CVD_VARIABLES_PH_BP_MEDICATION,
					map.get(CVD_VARIABLES_PH_BP_MEDICATION));

			values.put(CVD_VARIABLES_PH_DM, map.get(CVD_VARIABLES_PH_DM));

			values.put(CVD_VARIABLES_PH_HRTATTACK_SINCE,
					map.get(CVD_VARIABLES_PH_HRTATTACK_SINCE));

			values.put(CVD_VARIABLES_PH_STROKE_SINCE,
					map.get(CVD_VARIABLES_PH_STROKE_SINCE));

			values.put(CVD_VARIABLES_PH_DM_SINCE,
					map.get(CVD_VARIABLES_PH_DM_SINCE));
			values.put(CVD_VARIABLES_PH_PVD_SINCE,
					map.get(CVD_VARIABLES_PH_PVD_SINCE));
			values.put(CVD_VARIABLES_FH_HRATTACK,
					map.get(CVD_VARIABLES_FH_HRATTACK));
			values.put(CVD_VARIABLES_FH_STROKE,
					map.get(CVD_VARIABLES_FH_STROKE));
			values.put(CVD_VARIABLES_FH_DM, map.get(CVD_VARIABLES_FH_DM));
			// @treatment history
			values.put(CVD_VARIABLES_TH_BP, map.get(CVD_VARIABLES_TH_BP));
			values.put(CVD_VARIABLES_TH_LLTT, map.get(CVD_VARIABLES_TH_LLTT));
			values.put(CVD_VARIABLES_TH_APTT, map.get(CVD_VARIABLES_TH_APTT));

			values.put(CVD_VARIABLES_TT_PRES, map.get(CVD_VARIABLES_TT_PRES));

			values.put(CVD_VARIABLES_SMOKER, map.get(CVD_VARIABLES_SMOKER));

			values.put(CVD_VARIABLES_BP_PERDAY,
					map.get(CVD_VARIABLES_BP_PERDAY));
			// @treatment history
			values.put(CVD_VARIABLES_BP_PERWEEK,
					map.get(CVD_VARIABLES_BP_PERWEEK));
			values.put(CVD_VARIABLES_BP_PERLASTWEEK,
					map.get(CVD_VARIABLES_BP_PERLASTWEEK));
			values.put(CVD_VARIABLES_BP_PERYESTERDAY,
					map.get(CVD_VARIABLES_BP_PERYESTERDAY));
			values.put(CVD_VARIABLES_LP_PERDAY,
					map.get(CVD_VARIABLES_LP_PERDAY));
			// @treatment history
			values.put(CVD_VARIABLES_LP_PERWEEK,
					map.get(CVD_VARIABLES_LP_PERWEEK));
			values.put(CVD_VARIABLES_LP_PERLASTWEEK,
					map.get(CVD_VARIABLES_LP_PERLASTWEEK));
			values.put(CVD_VARIABLES_LP_PERYESTERDAY,
					map.get(CVD_VARIABLES_LP_PERYESTERDAY));
			values.put(CVD_VARIABLES_AT_PERDAY,
					map.get(CVD_VARIABLES_AT_PERDAY));
			// @treatment history
			values.put(CVD_VARIABLES_AT_PERWEEK,
					map.get(CVD_VARIABLES_AT_PERWEEK));
			values.put(CVD_VARIABLES_AT_PERLASTWEEK,
					map.get(CVD_VARIABLES_AT_PERLASTWEEK));
			values.put(CVD_VARIABLES_AT_PERYESTERDAY,
					map.get(CVD_VARIABLES_AT_PERYESTERDAY));
			values.put(CVD_VARIABLES_SH_SMOKING_QUESTION,
					map.get(CVD_VARIABLES_SH_SMOKING_QUESTION));
			values.put(CVD_VARIABLES_SH_CURRSMO,
					map.get(CVD_VARIABLES_SH_CURRSMO));
			values.put(CVD_VARIABLES_SH_AGEATSMO,
					map.get(CVD_VARIABLES_SH_AGEATSMO));
			values.put(CVD_VARIABLES_SH_QUITSMO,
					map.get(CVD_VARIABLES_SH_QUITSMO));
			values.put(CVD_VARIABLES_SH_CURR_CHEW,
					map.get(CVD_VARIABLES_SH_CURR_CHEW));
			values.put(CVD_VARIABLES_SH_AGEAT_CHEW,
					map.get(CVD_VARIABLES_SH_AGEAT_CHEW));
			values.put(CVD_VARIABLES_SH_QUIT_CHEW,
					map.get(CVD_VARIABLES_SH_QUIT_CHEW));

			values.put(CVD_VARIABLES_RH_Q1, map.get(CVD_VARIABLES_RH_Q1));
			values.put(CVD_VARIABLES_RH_Q2, map.get(CVD_VARIABLES_RH_Q2));

			values.put(CVD_VARIABLES_REFERALH_Q2_IF_YES,
					map.get(CVD_VARIABLES_REFERALH_Q2_IF_YES));

			values.put(CVD_VARIABLES_REFERALH_Q2_IF_NO_REASON,
					map.get(CVD_VARIABLES_REFERALH_Q2_IF_NO_REASON));

			// @treatment history

			values.put(CVD_VARIABLES_TH_BP, map.get(CVD_VARIABLES_TH_BP));
			values.put(CVD_VARIABLES_TH_LLTT, map.get(CVD_VARIABLES_TH_LLTT));
			values.put(CVD_VARIABLES_TH_APTT, map.get(CVD_VARIABLES_TH_APTT));
			values.put(CVD_VARIABLES_TH_DM, map.get(CVD_VARIABLES_TH_DM));
			values.put(CVD_VARIABLES_SBP1, map.get(CVD_VARIABLES_SBP1));
			values.put(CVD_VARIABLES_DBP1, map.get(CVD_VARIABLES_DBP1));
			values.put(CVD_VARIABLES_SBP2, map.get(CVD_VARIABLES_SBP2));

			values.put(CVD_VARIABLES_HR1, map.get(CVD_VARIABLES_HR1));
			values.put(CVD_VARIABLES_DBP2, map.get(CVD_VARIABLES_DBP2));
			values.put(CVD_VARIABLES_HR2, map.get(CVD_VARIABLES_HR2));
			values.put(CVD_VARIABLES_SBP3, map.get(CVD_VARIABLES_SBP3));
			values.put(CVD_VARIABLES_DBP3, map.get(CVD_VARIABLES_DBP3));
			values.put(CVD_VARIABLES_HR3, map.get(CVD_VARIABLES_HR3));
			values.put(CVD_VARIABLES_PULSE, map.get(CVD_VARIABLES_PULSE));

			values.put(CVD_VARIABLES_SBP_AVG, map.get(CVD_VARIABLES_SBP_AVG));

			values.put(CVD_VARIABLES_DBP_AVG, map.get(CVD_VARIABLES_DBP_AVG));

			values.put(CVD_VARIABLES_HR_AVG, map.get(CVD_VARIABLES_HR_AVG));

			values.put(CVD_VARIABLES_ACQUISITION_TIME,
					map.get(CVD_VARIABLES_ACQUISITION_TIME));

			values.put(CVD_VARIABLES_BPcuffSize,
					map.get(CVD_VARIABLES_BPcuffSize));

			values.put(CVD_VARIABLES_BG_MGDL, map.get(CVD_VARIABLES_BG_MGDL));
			values.put(CVD_VARIABLES_FASTING6HRS,
					map.get(CVD_VARIABLES_FASTING6HRS));

			values.put(CVD_VARIABLES_BG_EATorDRINK,
					map.get(CVD_VARIABLES_BG_EATorDRINK));

			try {
				values.put(CVD_VARIABLES_RISKSCORE,
						map.get(CVD_VARIABLES_RISKSCORE));

				values.put(CVD_VARIABLES_BP_DRUGLIST,
						map.get(CVD_VARIABLES_BP_DRUGLIST));

				values.put(CVD_VARIABLES_STATIN_DRUGLIST,
						map.get(CVD_VARIABLES_STATIN_DRUGLIST));

				values.put(CVD_VARIABLES_ASPIRIN_DRUGLIST,
						map.get(CVD_VARIABLES_ASPIRIN_DRUGLIST));

				values.put(CVD_VARIABLES_BP, map.get(CVD_VARIABLES_BP));

				values.put(CVD_VARIABLES_STATIN, map.get(CVD_VARIABLES_STATIN));

				values.put(CVD_VARIABLES_ASPIRIN,
						map.get(CVD_VARIABLES_ASPIRIN));

				values.put(CVD_VARIABLES_BP_REASON,
						map.get(CVD_VARIABLES_BP_REASON));

				values.put(CVD_VARIABLES_STATIN_REASON,
						map.get(CVD_VARIABLES_STATIN_REASON));

				values.put(CVD_VARIABLES_ASPIRIN_REASON,
						map.get(CVD_VARIABLES_ASPIRIN_REASON));

				values.put(CVD_VARIABLES_NEXT_VISIT_1MONTH,
						map.get(CVD_VARIABLES_NEXT_VISIT_1MONTH));

				values.put(CVD_VARIABLES_TT_ADHER,
						map.get(CVD_VARIABLES_TT_ADHER));

				values.put(CVD_VARIABLES_MED_RECEIVED,
						map.get(CVD_VARIABLES_MED_RECEIVED));

			} catch (Exception e) {
				// TODO: handle exception

				e.printStackTrace();
			}// for cholesterol

			values.put(CVD_VARIABLES_TC, map.get(CVD_VARIABLES_TC));
			values.put(CVD_VARIABLES_LDL, map.get(CVD_VARIABLES_LDL));
			values.put(CVD_VARIABLES_HDL, map.get(CVD_VARIABLES_HDL));
			values.put(CVD_VARIABLES_TG, map.get(CVD_VARIABLES_TG));

			values.put(CVD_VARIABLES_CHOL_RESULTDATE,
					map.get(CVD_VARIABLES_CHOL_RESULTDATE));

			values.put(CVD_VARIABLES_HT, map.get(CVD_VARIABLES_HT));
			values.put(CVD_VARIABLES_WT, map.get(CVD_VARIABLES_WT));

			values.put(CVD_VARIABLES_REFCODE, map.get(CVD_VARIABLES_REFCODE));
			values.put(CVD_VARIABLE_CURRENT_USER_LOGIN,
					map.get(CVD_VARIABLE_CURRENT_USER_LOGIN));
			values.put(CVD_VARIABLE_CURRENT_USER_ROLE,
					map.get(CVD_VARIABLE_CURRENT_USER_ROLE));

			values.put(CVD_VARIABLE_CURRENT_ENCOUNTER,
					map.get(CVD_VARIABLE_CURRENT_ENCOUNTER));
			values.put(CVD_VARIABLE_LAST_ENCOUNTER,
					map.get(CVD_VARIABLE_LAST_ENCOUNTER));

			values.put(CVD_VARIABLE_UPDATED_DATE,
					map.get(CVD_VARIABLE_UPDATED_DATE));
			values.put(CVD_VARIABLE_CREATED_DATE,
					map.get(CVD_VARIABLE_CREATED_DATE));

			values.put(CVD_VARIABLES_DIAB_CALC,
					map.get(CVD_VARIABLES_DIAB_CALC));

			values.put(CVD_VARIABLES_SMOKER_TL,
					map.get(CVD_VARIABLES_SMOKER_TL));

			/*
			 * values.put(CVD_VARIABLES_BG_FASTING_CALC,
			 * map.get(CVD_VARIABLES_BG_FASTING_CALC));
			 */
			values.put(CVD_VARIABLES_AR_RECOM, map.get(CVD_VARIABLES_AR_RECOM));

			values.put(CVD_VARIABLES_NV_AR, map.get(CVD_VARIABLES_NV_AR));

			values.put(CVD_VARIABLES_NV_AR_TL, map.get(CVD_VARIABLES_NV_AR_TL));

			values.put(CVD_VARIABLES_NV_DIAB, map.get(CVD_VARIABLES_NV_DIAB));

			values.put(CVD_VARIABLES_NV_DIAB_TL,
					map.get(CVD_VARIABLES_NV_DIAB_TL));

			values.put(CVD_VARIABLES_REF_DOC_TL,
					map.get(CVD_VARIABLES_REF_DOC_TL));

			values.put(CVD_VARIABLES_TARGET_SBP,
					map.get(CVD_VARIABLES_TARGET_SBP));

			values.put(CVD_VARIABLES_TARGET_SBP_TL,
					map.get(CVD_VARIABLES_TARGET_SBP_TL));

			values.put(CVD_VARIABLES_TARGET_DBP,
					map.get(CVD_VARIABLES_TARGET_DBP));

			values.put(CVD_VARIABLES_TARGET_DBP_TL,
					map.get(CVD_VARIABLES_TARGET_DBP_TL));

			values.put(CVD_VARIABLES_PHC_NAME, map.get(CVD_VARIABLES_PHC_NAME));

			values.put(CVD_VARIABLES_ASHA_ASSIGNED,
					map.get(CVD_VARIABLES_ASHA_ASSIGNED));

			values.put(CVD_VARIABLES_ENC_DATE, map.get(CVD_VARIABLES_ENC_DATE));

			values.put(CVD_VARIABLES_ENC_type, map.get(CVD_VARIABLES_ENC_type));

			values.put(CVD_VARIABLES_DOC_REF, map.get(CVD_VARIABLES_DOC_REF));

			values.put(CVD_VARIABLES_PH_CVD_CALC,
					map.get(CVD_VARIABLES_PH_CVD_CALC));

			values.put(CVD_VARIABLES_HIGH_RISK_CALC,
					map.get(CVD_VARIABLES_HIGH_RISK_CALC));

			if (table_name
					.equalsIgnoreCase(DatabaseVariables.TABLE_MAINLINE_CACHE_HT)) {

				dbConnecter.mainlineData(values,
						DatabaseVariables.TABLE_MAINLINE_RISKASSES_HT);

				// Storing json request data into database.

				//storeRequestData(map, selectedPatientRecord.getPatient_ID());

			} else if (table_name
					.equalsIgnoreCase(DatabaseVariables.TABLE_MAINLINE_RISKASSES_HT)) {

				dbConnecter.mainlineData(values,
						DatabaseVariables.TABLE_MAINLINE_CACHE_HT);

			}

			return true;

		}
	}

/*	private void storeRequestData(HashMap<String, String> requestMap,
			long patientID) {
		// TODO Auto-generated method stub

		if (!requestMap.isEmpty()) {

			new DatabaseConnecter(this).insertRequestData(
					RequestFactory.buildRequestData(requestMap, this),
					String.valueOf(patientID));

		}

	}*/

	/*public void setRadioGroupForValue(String value, int rGrounpValue) {

		if (null != value && !value.isEmpty()) {
			String result = value;
			if (!(result.equalsIgnoreCase(DEFAULT_VALUE))) {
				RadioGroup rGroup = (RadioGroup) findViewById(rGrounpValue);

				if (result.equalsIgnoreCase(String.valueOf(YES))) {

					((RadioButton) rGroup.findViewById(R.id.radio_yes))
							.setChecked(true);
				} else if (result.equalsIgnoreCase(String.valueOf(NO)))

				{
					((RadioButton) rGroup.findViewById(R.id.radio_no))
							.setChecked(true);
				}

				else if (!isFromNewPatient
						&& result.equalsIgnoreCase(String.valueOf(DK)))

				{
					// ((RadioButton) findViewById(noId)).setChecked(true);
					((RadioButton) rGroup.findViewById(R.id.radio_dk))
							.setChecked(true);
				} else if (quickreview
						&& result.equalsIgnoreCase(String.valueOf(DK))) {

					((RadioButton) rGroup.findViewById(R.id.radio_dk))
							.setChecked(true);
				}
			}
		}

	}*/

	public void showAlert(String str) {
		AlertDialog.Builder builder = new AlertDialog.Builder(CVDVariables.this);

		builder.setIcon(R.drawable.alert);

		builder.setMessage(str);

		// Setting OK Button
		builder.setPositiveButton(getResources().getString(R.string.string_ok),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						// {
						// alertDialog.dismiss();
						// }
						// wantoExit = true;

						dialog.dismiss();
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	/*public void showAlertForSync(String str, final long pid) {
		AlertDialog.Builder builder = new AlertDialog.Builder(CVDVariables.this);

		builder.setIcon(R.drawable.alert);

		builder.setMessage(str);

		patient_Id = String.valueOf(pid);

		// Setting OK Button
		builder.setPositiveButton(getResources().getString(R.string.string_yes),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						new PatientDeatils().execute();

						dialog.dismiss();
					}
				});

		builder.setNegativeButton(getResources().getString(R.string.string_no),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();
	}*/

	/*protected void getPatientDetails(String pid) {
		// TODO Auto-generated method stub

		url = HTTPConstants.OPENMRS_URL + "/encounter/?q=" + pid
				+ "&v=custom:(uuid,encounterDatetime,encounterType:(name))";

		patient_Id = pid;

		HttpAsyncServiceHelper serviceHelper = new HttpAsyncServiceHelper(
				patientAllEncountersServiceListener);

		serviceHelper.performAsyncGet(url, AppUtil.getDefaultUsername(this),
				AppUtil.getDefaultPassword(this), null);
	}

	private ServiceListener patientAllEncountersServiceListener = new ServiceListener() {

		@Override
		public void onServiceSuccess(Response response_msg) {
			// TODO Auto-generated method stub

			jsonParsing = new JsonParsing(CVDVariables.this);

			jsonParsing.storeAllEncounterData(
					response_msg.getResponse_content(), patient_Id);// connector.countPatients()

			loadObservations(patient_Id);

		}

		@Override
		public void onServiceError(Response error_response) {
			// TODO Auto-generated method stub

		}
	};

	protected void loadObservations(String patientId) {
		// TODO Auto-generated method stub

		updateObservations(patientId);

	}

	protected void updateObservations(String patientId) {
		// TODO Auto-generated method stub

		connector = new DatabaseConnecter(this);

		patient_Id = patientId;

		String lastEncounterUUID = connector.getLastEncounterUUID(patientId);

		if (!lastEncounterUUID.equalsIgnoreCase("-1")) {

			url = HTTPConstants.OPENMRS_URL + "/encounter/" + lastEncounterUUID
					+ "/?v=custom:(obs:(value,concept:(names:(name)))";

			getObservationsforLastEncounterUUID();

		}

	}*/

	/*private void getObservationsforLastEncounterUUID() {
		// TODO Auto-generated method stub

		HttpAsyncServiceHelper serviceHelper = new HttpAsyncServiceHelper(
				patientlastEncounterServiceListener);

		serviceHelper.performAsyncGet(url, AppUtil.getDefaultUsername(this),
				AppUtil.getDefaultPassword(this), null);

	}

	private ServiceListener patientlastEncounterServiceListener = new ServiceListener() {

		@Override
		public void onServiceSuccess(Response response_msg) {
			// TODO Auto-generated method stub

			jsonParsing.parseLastEncounterObservationsData(
					response_msg.getResponse_content(), patient_Id);

		}

		@Override
		public void onServiceError(Response error_response) {
			// TODO Auto-generated method stub

		}
	};*/

	/*public static void displayVideo(String resourcename) throws Exception {

		Boolean isSDPresent = Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED);

		if (isSDPresent) {

			File extStore = Environment.getExternalStorageDirectory();

			File myPath = new File(extStore.getAbsolutePath() + "/.Mainrct");
			myPath.mkdir();
			String file_only_name = resourcename.substring(0,
					resourcename.indexOf("."));
			InputStream in = mContext.getResources().openRawResource(
					mContext.getResources().getIdentifier(file_only_name,
							"raw", mContext.getPackageName()));

			String file_extension = resourcename.substring(
					resourcename.indexOf(".") + 1, resourcename.length());

			FileOutputStream out = new FileOutputStream(myPath + "/"
					+ file_only_name + "." + file_extension);

			byte[] buff = new byte[3144900];
			int read = 0;

			try {
				while ((read = in.read(buff)) > 0) {
					out.write(buff, 0, read);
				}
			} finally {
				in.close();

				out.close();

			}

			PackageManager packageManager = mContext.getApplicationContext()
					.getPackageManager();

			Intent testIntent = new Intent(Intent.ACTION_VIEW);
			testIntent.setType("video*//*");
			if (packageManager.queryIntentActivities(testIntent,
					PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {

				File file = new File(myPath + "/" + file_only_name + "."
						+ file_extension);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file), "video*//*");
				mContext.startActivity(intent);

			} else {

				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

				builder.setIcon(R.drawable.alert);

				builder.setMessage(mContext.getResources().getString(
						R.string.videomsg));

				// Setting OK Button

				builder.setPositiveButton(
						mContext.getResources().getString(R.string.ok),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								final String appPackageName = "com.mxtech.videoplayer.ad"; // getPackageName()
																							// from
																							// Context
																							// or
																							// Activity
																							// object
								try {
									mContext.startActivity(new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("market://details?id="
													+ appPackageName)));
								} catch (android.content.ActivityNotFoundException anfe) {

									mContext.startActivity(new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("http://play.google.com/store/apps/details?id="
													+ appPackageName)));

								}

								dialog.dismiss();
							}
						});
				builder.setNegativeButton(
						mContext.getResources().getString(R.string.cancel),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// Write your code here to execute after
								// dialog
								// closed
								dialog.cancel();
							}
						});
				AlertDialog dialog = builder.create();
				dialog.show();

			}
		}
	}*/

/*	protected class PatientDeatils extends AsyncTask<Void, Void, Boolean> {

		private ProgressDialog dialog = new ProgressDialog(CVDVariables.this);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			dialog.setMessage(CVDVariables.this.getResources().getString(
					R.string.search_querying));
			dialog.setCancelable(false);
			dialog.show();

		}

		@Override
		protected Boolean doInBackground(Void... arg0) {

			getPatientDetails(patient_Id);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			Toast.makeText(CVDVariables.this,
					"Patient Details Imported Successfully.", Toast.LENGTH_LONG)
					.show();
		}

	}*/

	public void showPopup(View anchorView, String infoText) {

		View popUpView = getLayoutInflater().inflate(R.layout.info_popup, null);

		TextView tv_info = (TextView) popUpView.findViewById(R.id.tv_info);

		tv_info.setText(infoText);

		PopupWindow mpopup = new PopupWindow(popUpView, 200, 200, true);

		mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
		mpopup.setBackgroundDrawable(new BitmapDrawable());
		mpopup.setOutsideTouchable(true);

		int OFFSET_X = -5;
		int OFFSET_Y = 20;

		int[] location = new int[2];

		anchorView.getLocationOnScreen(location);
		Point p = new Point(location[0], location[1]);

		mpopup.setBackgroundDrawable(new BitmapDrawable());

		mpopup.showAtLocation(popUpView, Gravity.NO_GRAVITY, p.x + OFFSET_X,
				p.y + OFFSET_Y);

	}

	public String calculateTT_ADHER(String tablets_take_last_week,
			String tablets_week) {

		float take_last_week = 0, take_in_week = 0;

		if (tablets_take_last_week != null
				&& !tablets_take_last_week.equalsIgnoreCase(CVD_VARIABLES_NA)
				&& tablets_week != null
				&& !tablets_week.equalsIgnoreCase(CVD_VARIABLES_NA)) {

			take_last_week = Float.parseFloat(tablets_take_last_week);

			take_in_week = Float.parseFloat(tablets_week);

			float tablets_percentage = (take_last_week / take_in_week) * 100;

			if (tablets_percentage >= 75) {

				return String.valueOf(YES);
			} else {

				return String.valueOf(NO);
			}
		} else {

			return CVD_VARIABLES_NA;
		}

	}

	public String calculate_Med_Received(String calculated_tt_adher) {

		if (calculated_tt_adher != null) {

			if (calculated_tt_adher.equalsIgnoreCase(String.valueOf(YES))
					|| calculated_tt_adher.equalsIgnoreCase(String.valueOf(NO))) {

				return String.valueOf(YES);

			} else {

				return CVD_VARIABLES_NA;
			}

		} else {

			return CVD_VARIABLES_NA;
		}

	}

	public static String getCurrentDate() {

		String current_date = new SimpleDateFormat(DATE_FORMAT)
				.format(new Date());

		return current_date;

	}

	public static String getCurrentDateTime() {

		String current_date = new SimpleDateFormat(DATE_TIME_FORMAT)
				.format(new Date());

		return current_date;

	}

	public String getEmptyStringIfNA(String strToValidate) {

		if (strToValidate != null) {
			if (strToValidate.equalsIgnoreCase("NA")) {
				return "";
			} else {
				return strToValidate;
			}
		}

		return "";
	}

	/*public void copyDatabase() {

		Boolean isSDPresent = Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED);

		if (isSDPresent) {

			File extStore = Environment.getExternalStorageDirectory();

			File myPath = new File(extStore.getAbsolutePath() + File.separator
					+ HTTPConstants.DB_MAIN_RCT);

			myPath.mkdir();

			File myFile = new File(myPath + "/mainrct_listing_info");

			if (!myFile.exists()) {

				InputStream in = getResources().openRawResource(
						R.raw.mainrct_listing_info);

				FileOutputStream out = null;
				try {
					out = new FileOutputStream(myPath + "/mainrct_listing_info");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				byte[] buff = new byte[3144900];
				int read = 0;

				try {
					try {
						while ((read = in.read(buff)) > 0) {
							out.write(buff, 0, read);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		}

	}*/

	protected void downloadFileFromServer() {
		// TODO Auto-generated method stub

		if (AppUtil.isInternetAvailable(CVDVariables.this)) {

			new DownloadFileAsync()
					.execute(HTTPConstants.FILE_DOWNLOAD_SERVER_URL);

		} else {

			showAlert(getString(R.string.enable_internet_msg));
		}

	}

	class DownloadFileAsync extends AsyncTask<String, String, String> {

		ProgressDialog mProgressDialog = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			mProgressDialog = new ProgressDialog(CVDVariables.this);

			mProgressDialog.setMessage("Please wait....");

			mProgressDialog.setCancelable(false);

			mProgressDialog.show();

		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			try {

				URL url = new URL(aurl[0]);

				URLConnection conexion = url.openConnection();

				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();

				Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

				InputStream input = new BufferedInputStream(url.openStream());

				File myPath = new File(HTTPConstants.FILE_DOWNLOADED_PATH);

				if (!myPath.exists()) {

					myPath.mkdir();

				}

				OutputStream output = new FileOutputStream(
						HTTPConstants.FILE_DOWNLOADED_PATH + File.separator
								+ HTTPConstants.USERS_FILE_NAME);

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {

					total += count;

					publishProgress("" + (int) ((total * 100) / lenghtOfFile));

					output.write(data, 0, count);
				}

				output.flush();

				output.close();

				input.close();

				// /

				/*parseUserData(HTTPConstants.FILE_DOWNLOADED_PATH
						+ File.separator + HTTPConstants.USERS_FILE_NAME);
*/
			} catch (Exception e) {

				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(String unused) {

			mProgressDialog.dismiss();

		}
	}

	/*public void parseUserData(String path) {
		// TODO Auto-generated method stub

		InputStream is = null;
		try {

			is = new FileInputStream(path);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CSVReader reader = new CSVReader(new InputStreamReader(is), ',', '"', 1);

		String[] nextLine;

		DatabaseHelper databaseadapter = new DatabaseHelper(CVDVariables.this);

		try {

			databaseadapter.getWritableDB();

			while ((nextLine = reader.readNext()) != null) {
				if (nextLine != null) {
					// do your work

					UserModel usermodel = new UserModel();

					usermodel.setFname(nextLine[1]);
					usermodel.setLname(nextLine[2]);
					usermodel.setUserid(nextLine[3]);
					usermodel.setUsername(nextLine[4]);
					usermodel.setPassword(nextLine[5]);
					usermodel.setRole(nextLine[6]);
					usermodel.setVillage(nextLine[7]);
					usermodel.setPhc(nextLine[8]);
					usermodel.setContact_no(nextLine[9]);

					usermodel.setGender(nextLine[10]);

					databaseadapter.insertUser(usermodel);

				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			databaseadapter.closeDB();
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}*/

}
