package db;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import org.ibme.gi.mainRCT_healthtrackercvd.db.utils.LocalityModel;
import db.utils.PatientModel;
import https.HTTPConstants;
import utils.PersistanceData;
//import org.ibme.gi.mainRCT_healthtrackercvd.db.utils.PhcModel;
//import utils.UploadDataModel;
import db.utils.UserModel;
//import org.ibme.gi.mainRCT_healthtrackercvd.db.utils.VillageModel;
//import org.ibme.gi.mainRCT_healthtrackercvd.upload.https.HTTPConstants;
//import org.ibme.gi.model.Encounter;
//import org.ibme.gi.model.Obs;
//import org.ibme.gi.service.RequestModel;
//import org.ibme.gi.utils.AppUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DatabaseConnecter extends DatabaseVariables {

	public static Map<String, String> telugunames = new HashMap<String, String>();

	public static Map<String, String> loctelugunames = new HashMap<String, String>();

	// from
	private static String DB_PATH = Environment.getExternalStorageDirectory()
			+ File.separator + HTTPConstants.DB_MAIN_RCT + File.separator;

	public static final String DB_NAME_RISK_ASSESSMENT = "mainrct_listing_info";

	public static final int DB_VERSION_RISK_ASSESSMENT = 27;

	private static RiskAssessmentDBHelper ra_dbHelper;

	private static SQLiteDatabase ra_db;

	private static final String DB_NAME_MAINLINE_HT = "mainrct_riskasses";

	private static final int DB_VERSION_MAINLINE_HT = 76;

	private static MainlineDBHelper ml_dbHelper;

	private PersistanceData persistenceData;

	private static SQLiteDatabase ml_db;

	Context ctx;

	public DatabaseConnecter(Context mainContext) {

		ra_dbHelper = new RiskAssessmentDBHelper(mainContext);

		ml_dbHelper = new MainlineDBHelper(mainContext);

		ctx = mainContext;

		persistenceData = new PersistanceData(mainContext);
	}

	public void openmainlineWritableDatabase() {

		ml_db = ml_dbHelper.getWritableDatabase();

	}

	/*
	 * private void openmainlineReadbleDatabase() {
	 * 
	 * ml_db = ml_dbHelper.getReadableDatabase();
	 * 
	 * }
	 */

	public void closemainlineDatabase() {

		if (ml_db.isOpen()) {

			ml_db.close();
		}
	}

	private class MainlineDBHelper extends SQLiteOpenHelper {

		MainlineDBHelper(Context context) {

			super(context, DB_PATH + DB_NAME_MAINLINE_HT, null,
					DB_VERSION_MAINLINE_HT);

		}

		MainlineDBHelper(Context context, String dbPath, String dbName) {

			super(context, dbPath + dbName, null, DB_VERSION_MAINLINE_HT);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(TABLE_MAINLINE_HT_CREATE);
			db.execSQL(TABLE_MAINRISK_CREATE);
			db.execSQL(TABLE_UPLOAD_STATUS_CREATE);
			db.execSQL(TABLE_PATIENTIDENTIFIERS_CREATE);
			db.execSQL(TABLE_ENCOUNTER_CREATE);
			db.execSQL(TABLE_UPLOAD_PATIENT_STATUS_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAINLINE_CACHE_HT);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAINLINE_RISKASSES_HT);
			db.execSQL("DROP TABLE IF EXISTS  " + TABLE_UPLOAD_STATUS);
			db.execSQL("DROP TABLE IF EXISTS  " + TABLE_PATIENTIDENTIFIERS);
			db.execSQL("DROP TABLE IF EXISTS  " + TABLE_ENCOUNTER);
			db.execSQL("DROP TABLE IF EXISTS  " + TABLE_UPLOAD_PATIENT_STATUS);

			onCreate(db);

		}
	}

	private void openRiskAssesmentWritableDatabase() {

		ra_db = ra_dbHelper.getWritableDatabase();

	}

	private void openRiskAssesmentReadbleDatabase() {

		ra_db = ra_dbHelper.getReadableDatabase();

	}

	private void closeRiskAssesmentDatabase() {

		if (ra_db.isOpen()) {

			ra_db.close();
		}
	}

	public String getRiskAssessRowIdForPatientId(long patient_id) {

		String ra_RowId = "-1";

		String selectQuery = "SELECT  * FROM " + TABLE_MAINLINE_CACHE_HT
				+ "  WHERE  " + KEY_PATIENT_ID + "='" + patient_id + "'";

		try {

			openmainlineWritableDatabase();

			Cursor c = ml_db.rawQuery(selectQuery, null);

			if (c != null && c.getCount() > 0) {

				c.moveToFirst();

				ra_RowId = c.getString(c.getColumnIndex(KEY_ID));

			}

		} catch (SQLiteException k) {

			k.printStackTrace();
		} finally {

			closemainlineDatabase();
		}

		return ra_RowId;

	}

	public boolean doesPatientAlreadyExistsInMT(String tableName, long patient) {

		boolean result = false;

		if (patient == -1)
			return false;
		openmainlineWritableDatabase();

		Cursor mCursor = ml_db.rawQuery("SELECT COUNT(*) FROM " + tableName
				+ " WHERE " + KEY_PATIENT_ID + "=" + patient, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
			if (mCursor.getInt(0) > 0) {
				result = true;

			}
			mCursor.close();
		}

		closemainlineDatabase();

		return result;
	}

	public boolean doesPatientExits(String tableName, long patient) {

		boolean result = false;

		if (patient == -1)
			return false;
		openRiskAssesmentWritableDatabase();

		Cursor mCursor = ra_db.rawQuery("SELECT COUNT(*) FROM " + tableName
				+ " WHERE " + KEY_PATIENT_ID + "=" + patient, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
			if (mCursor.getInt(0) > 0) {
				result = true;

			}
			mCursor.close();
		}

		closeRiskAssesmentDatabase();
		return result;
	}

	public boolean doesUserExits(long user_id) {

		boolean result = false;

		openRiskAssesmentWritableDatabase();

		Cursor mCursor = ra_db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USER
				+ " WHERE " + KEY_USERID + "=" + user_id, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
			if (mCursor.getInt(0) > 0) {
				result = true;

			}
			mCursor.close();
		}

		closeRiskAssesmentDatabase();
		return result;
	}

	public boolean doesRecordExistWithThisRefval(String refVal) {

		boolean result = false;

		openRiskAssesmentWritableDatabase();

		Cursor mCursor = ra_db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PATIENT
				+ " WHERE " + KEY_P_REFERALCARD_NUM + "=" + refVal, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
			if (mCursor.getInt(0) > 0) {
				result = true;

			}
			mCursor.close();
		}

		closeRiskAssesmentDatabase();
		return result;
	}

	public boolean userAuth(String tableName, String uname, String password) {

		boolean result = false;

		if (uname == null || password == null)
			return false;
		openRiskAssesmentWritableDatabase();

		Cursor mCursor = ra_db.rawQuery("SELECT COUNT(*) FROM " + tableName
				+ " WHERE " + KEY_USERNAME + "=" + "'" + uname + "'" + "AND "
				+ KEY_PASSWORD + "=" + "'" + password + "'", null);
		if (mCursor != null) {
			mCursor.moveToFirst();
			if (mCursor.getInt(0) > 0) {

				result = true;

			}
			mCursor.close();
		}

		closeRiskAssesmentDatabase();
		return result;
	}

	public long newPatientDataforHT(ContentValues initialValues) {

		openmainlineWritableDatabase();

		long rowId = ml_db.insert(TABLE_MAINLINE_CACHE_HT, null, initialValues);

		closemainlineDatabase();
		return rowId;

	}

	/*
	 * public long insertpatientData(ContentValues initialValues) {
	 * 
	 * openmainlineWritableDatabase();
	 * 
	 * long rowId = ml_db.insert(TABLE_MAINLINE_RISKASSES_HT, null,
	 * initialValues);
	 * 
	 * closemainlineDatabase(); return rowId;
	 * 
	 * }
	 */
	public long newUser(ContentValues initialValues) {

		openRiskAssesmentWritableDatabase();
		long rowId = ra_db.insert(TABLE_USER, null, initialValues);

		closeRiskAssesmentDatabase();
		return rowId;

	}

	public long mainlineData(ContentValues initialValues, String table_name) {

		openmainlineWritableDatabase();

		long rowId = ml_db.insert(table_name, null, initialValues);

		closemainlineDatabase();
		return rowId;

	}

	public void updatePatientData(ContentValues initialValues, long id) {

		openRiskAssesmentWritableDatabase();

		// updating row
		ra_db.update(TABLE_PATIENT, initialValues, KEY_PATIENT_ID + " = ?",
				new String[] { String.valueOf(id) });

		closeRiskAssesmentDatabase();
	}

	public void updatePatientHT(ContentValues initialValues, long id) {

		openmainlineWritableDatabase();

		// updating row
		ml_db.update(TABLE_MAINLINE_CACHE_HT, initialValues, KEY_PATIENT_ID
				+ " = ?", new String[] { String.valueOf(id) });

		closemainlineDatabase();
	}

	public void updateUserData(ContentValues initialValues, long id) {

		openRiskAssesmentWritableDatabase();
		// updating row
		ra_db.update(TABLE_USER, initialValues, KEY_USERID + " = ?",
				new String[] { String.valueOf(id) });

		closeRiskAssesmentDatabase();
	}

	public void updateRole(String role, String userName, long id) {

		openmainlineWritableDatabase();

		ContentValues val = new ContentValues();
		val.put(CVD_VARIABLE_CURRENT_USER_ROLE, role);
		val.put(CVD_VARIABLE_CURRENT_USER_LOGIN, userName);
		val.put(CVD_VARIABLE_CREATED_DATE, getCurrentDateTime());
		val.put(CVD_VARIABLE_UPDATED_DATE, getCurrentDateTime());

		// updating row
		ml_db.update(TABLE_MAINLINE_CACHE_HT, val, KEY_PATIENT_ID + " = ?",
				new String[] { String.valueOf(id) });

		closemainlineDatabase();
	}

	public void deleterow(long patientid) {

		openmainlineWritableDatabase();

		ml_db.delete(TABLE_MAINLINE_CACHE_HT, KEY_PATIENT_ID + "=" + patientid,
				null);
		closemainlineDatabase();
	}

	public void deleteTableRecordsByTableName(String tablename) {
		// TODO Auto-generated method stub
		openmainlineWritableDatabase();

		ml_db.delete(tablename, null, null);
		closemainlineDatabase();

	}

	/*public ArrayList<LocalityModel> getLocalityListForVillage(int villageid) {

		ArrayList<LocalityModel> localityList = new ArrayList<LocalityModel>();

		LocalityModel loc = new LocalityModel("Select locality");
		localityList.add(loc);

		String selectQuery = "SELECT  " + KEY_ID + "," + KEY_LOCALITY_NAME
				+ " FROM " + TABLE_LOCALITY + "  WHERE  " + KEY_VILLAGE_ID
				+ "=" + villageid;

		openRiskAssesmentWritableDatabase();

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();
			LocalityModel locality;

			do {
				locality = new LocalityModel();
				locality.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
				locality.setLocality_name(c.getString(c
						.getColumnIndex(KEY_LOCALITY_NAME)));
				locality.setVillage_id(villageid);
				localityList.add(locality);
			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return localityList;

	}

	public ArrayList<VillageModel> getVillageListForPhc(int phc_id) {

		ArrayList<VillageModel> villageList = new ArrayList<VillageModel>();

		String selectQuery = "SELECT  " + KEY_ID + "," + KEY_VILLAGE_NAME
				+ " FROM " + TABLE_VILLAGE + "  WHERE  " + KEY_PHC_CODE + "="
				+ phc_id;

		openRiskAssesmentWritableDatabase();

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();
			VillageModel villages;

			do {
				villages = new VillageModel();
				villages.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
				villages.setVillage_name(c.getString(c
						.getColumnIndex(KEY_VILLAGE_NAME)));
				villageList.add(villages);
			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return villageList;

	}

	public ArrayList<PhcModel> getPhcData() {

		ArrayList<PhcModel> phcList = new ArrayList<PhcModel>();

		String selectQuery = "SELECT  " + KEY_PHC_CODE + "," + KEY_PHC_NAME
				+ " FROM " + TABLE_PHC;

		openRiskAssesmentWritableDatabase();

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();
			PhcModel phcs;

			do {
				phcs = new PhcModel();
				phcs.setPhc_code(c.getInt(c.getColumnIndex(KEY_PHC_CODE)));
				phcs.setPhc_name(c.getString(c.getColumnIndex(KEY_PHC_NAME)));

				phcList.add(phcs);
			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return phcList;

	}*/

	public List<Map<String, String>> getXDateandRoleValuesForGraph(
			long patient_id) {

		List<Map<String, String>> xvalues = new ArrayList<Map<String, String>>();

		String selectQuery = "SELECT  " + CVD_VARIABLE_CREATED_DATE + ","
				+ CVD_VARIABLE_CURRENT_USER_ROLE + "," + CVD_VARIABLES_SBP_AVG
				+ "," + CVD_VARIABLES_DBP_AVG + " FROM "
				+ TABLE_MAINLINE_RISKASSES_HT + " " + " WHERE  "
				+ KEY_PATIENT_ID + "=" + patient_id + " ORDER BY "
				+ CVD_VARIABLE_CREATED_DATE + " DESC";

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		Map<String, String> date_role;

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				date_role = new HashMap<String, String>();

				date_role.put(CVD_VARIABLE_CREATED_DATE, c.getString(c
						.getColumnIndex(CVD_VARIABLE_CREATED_DATE)));

				date_role.put(CVD_VARIABLE_CURRENT_USER_ROLE, c.getString(c
						.getColumnIndex(CVD_VARIABLE_CURRENT_USER_ROLE)));

				date_role.put(CVD_VARIABLES_SBP_AVG,
						c.getString(c.getColumnIndex(CVD_VARIABLES_SBP_AVG)));

				date_role.put(CVD_VARIABLES_DBP_AVG,
						c.getString(c.getColumnIndex(CVD_VARIABLES_DBP_AVG)));

				xvalues.add(date_role);

			} while (c.moveToNext());

		}

		closemainlineDatabase();

		return xvalues;

	}

	public ArrayList<String> getSBPValuesForGraph(long patient_id) {

		ArrayList<String> xvalues = new ArrayList<String>();

		String selectQuery = "SELECT  " + CVD_VARIABLES_SBP_AVG + " FROM "
				+ TABLE_MAINLINE_RISKASSES_HT + " " + " WHERE  "
				+ KEY_PATIENT_ID + "=" + patient_id;

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {
				xvalues.add(c.getString(c.getColumnIndex(CVD_VARIABLES_SBP_AVG)));
			} while (c.moveToNext());

		}

		closemainlineDatabase();

		return xvalues;

	}

	public ArrayList<String> getDBPValuesForGraph(long patient_id) {

		ArrayList<String> xvalues = new ArrayList<String>();

		String selectQuery = "SELECT  " + CVD_VARIABLES_DBP_AVG + " FROM "
				+ TABLE_MAINLINE_RISKASSES_HT + " " + " WHERE  "
				+ KEY_PATIENT_ID + "=" + patient_id;

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {
				xvalues.add(c.getString(c.getColumnIndex(CVD_VARIABLES_DBP_AVG)));
			} while (c.moveToNext());

		}

		closemainlineDatabase();

		return xvalues;

	}

	public ArrayList<String> getHRValuesForGraph(long patient_id) {

		ArrayList<String> xvalues = new ArrayList<String>();

		String selectQuery = "SELECT  " + CVD_VARIABLES_HR_AVG + " FROM "
				+ TABLE_MAINLINE_RISKASSES_HT + " " + " WHERE  "
				+ KEY_PATIENT_ID + "=" + patient_id;

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {
				xvalues.add(c.getString(c.getColumnIndex(CVD_VARIABLES_HR_AVG)));
			} while (c.moveToNext());

		}

		closemainlineDatabase();

		return xvalues;

	}

	public ArrayList<String> getWeightValues(long patient_id) {

		ArrayList<String> xvalues = new ArrayList<String>();

		String selectQuery = "SELECT  " + CVD_VARIABLES_WT + " FROM "
				+ TABLE_MAINLINE_RISKASSES_HT + " " + " WHERE  "
				+ KEY_PATIENT_ID + "=" + patient_id;

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {
				xvalues.add(c.getString(c.getColumnIndex(CVD_VARIABLES_WT)));
			} while (c.moveToNext());

		}

		closemainlineDatabase();

		return xvalues;

	}

	public void updatepatientEncoutersInPatienttable(long patient_id) {

		String encounter = "";
		String ref_code = "";
		String selectQuery = "SELECT  " + CVD_VARIABLE_CURRENT_ENCOUNTER + ","
				+ CVD_VARIABLES_REFCODE + "  FROM "
				+ TABLE_MAINLINE_RISKASSES_HT + " WHERE " + KEY_PATIENT_ID
				+ " = " + patient_id;

		try {

			openmainlineWritableDatabase();

			Cursor c = ml_db.rawQuery(selectQuery, null);

			if (c != null && c.getCount() > 0) {

				c.moveToFirst();

				do {

					encounter = c.getString(c
							.getColumnIndex(CVD_VARIABLE_CURRENT_ENCOUNTER));
					ref_code = c.getString(c
							.getColumnIndex(CVD_VARIABLES_REFCODE));

				} while (c.moveToNext());

			}

		} catch (SQLiteException e) {

			e.printStackTrace();

		} finally {

			closemainlineDatabase();

		}

		updatePatientEncounter(patient_id, encounter, ref_code);

	}

	public int getVillageid(String villagename) {

		String selectQuery = "SELECT  " + KEY_ID + " FROM " + TABLE_VILLAGE
				+ "  WHERE  " + KEY_VILLAGE_NAME + "=" + "'" + villagename
				+ "'";
		int villageid = 0;

		try {

			openRiskAssesmentWritableDatabase();

			Cursor c = ra_db.rawQuery(selectQuery, null);

			if (c != null && c.getCount() > 0) {

				c.moveToFirst();

				do {

					villageid = c.getInt(c.getColumnIndex(KEY_ID));

				} while (c.moveToNext());

			}

		} catch (SQLiteException e) {

			e.printStackTrace();

		} finally {

			closeRiskAssesmentDatabase();

		}
		return villageid;

	}

	public long getPatientid(String refnum) {

		String selectQuery = "SELECT  " + KEY_PATIENT_ID + " FROM "
				+ TABLE_PATIENT + "  WHERE  " + KEY_P_REFERALCARD_NUM + "='"
				+ refnum + "'";

		openRiskAssesmentWritableDatabase();
		long patientid = -1;

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				patientid = c.getLong(c.getColumnIndex(KEY_PATIENT_ID));

			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return patientid;

	}

	public String getPhcName(String phccode) {

		String selectQuery = "SELECT  " + KEY_PHC_NAME + " FROM " + TABLE_PHC
				+ "  WHERE  " + KEY_PHC_CODE + "='" + phccode + "'";

		openRiskAssesmentWritableDatabase();
		String phc = "";
		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				phc = c.getString(c.getColumnIndex(KEY_PHC_NAME));

			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return phc;

	}

	public String getPhcNameForDoctorreview(long patientId) {

		String selectQuery = "SELECT  " + KEY_PHC_NAME + " FROM "
				+ TABLE_PATIENT + "  WHERE  " + KEY_PATIENT_ID + "='"
				+ patientId + "'";

		openRiskAssesmentWritableDatabase();
		String phc = "";
		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				phc = c.getString(c.getColumnIndex(KEY_PHC_NAME));

			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return phc;

	}

	public long getlastCounterValue(long patientid, String role) {

		String selectQuery = "SELECT  " + CVD_VARIABLE_LAST_ENCOUNTER
				+ " FROM " + TABLE_MAINLINE_RISKASSES_HT + "  WHERE  "
				+ KEY_PATIENT_ID + "=" + patientid + " AND "
				+ CVD_VARIABLE_CURRENT_USER_ROLE + "='" + role + "'"
				+ " ORDER BY " + CVD_VARIABLE_CURRENT_ENCOUNTER + " DESC";

		openmainlineWritableDatabase();

		long counter = 0;

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				counter = c.getLong(c
						.getColumnIndex(CVD_VARIABLE_LAST_ENCOUNTER));

			} while (c.moveToNext());

		}

		closemainlineDatabase();

		return counter;

	}

	public String getCounterValue(long patientid, String role) {

		String selectQuery = "SELECT  " + CVD_VARIABLE_CURRENT_ENCOUNTER
				+ " FROM " + TABLE_MAINLINE_RISKASSES_HT + "  WHERE  "
				+ KEY_PATIENT_ID + "=" + patientid + " AND "
				+ CVD_VARIABLE_CURRENT_USER_ROLE + "='" + role + "'"
				+ " ORDER BY " + CVD_VARIABLE_UPDATED_DATE + " DESC LIMIT 1";

		openmainlineWritableDatabase();

		String counter = "";

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				counter = c.getString(c
						.getColumnIndex(CVD_VARIABLE_CURRENT_ENCOUNTER));

			} while (c.moveToNext());

		}

		closemainlineDatabase();

		return counter;

	}

	/*
	 * public String getDoctorEncounter(long patientid, String role) {
	 * 
	 * String selectQuery = "SELECT  " + CVD_VARIABLE_CURRENT_ENCOUNTER +
	 * " FROM " + TABLE_P_MAINLINE_HT + "  WHERE  " + KEY_PATIENT_ID + "=" +
	 * patientid + " AND " + CVD_VARIABLE_CURRENT_USER_ROLE + "='" + role + "'"
	 * ;
	 * 
	 * openmainlineWritableDatabase();
	 * 
	 * String counter = "";
	 * 
	 * Cursor c = ml_db.rawQuery(selectQuery, null);
	 * 
	 * if (c != null && c.getCount() > 0) {
	 * 
	 * c.moveToFirst();
	 * 
	 * do {
	 * 
	 * counter = c.getString(c .getColumnIndex(CVD_VARIABLE_CURRENT_ENCOUNTER));
	 * 
	 * } while (c.moveToNext());
	 * 
	 * }
	 * 
	 * closemainlineDatabase();
	 * 
	 * return counter;
	 * 
	 * }
	 */

	public String getDoctorEncounter(long patientid) {

		String selectQuery = "SELECT  " + CVD_VARIABLE_CURRENT_USER_ROLE
				+ " FROM " + TABLE_MAINLINE_RISKASSES_HT + "  WHERE  "
				+ KEY_PATIENT_ID + "=" + patientid;

		openmainlineWritableDatabase();

		String counter = "";

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				counter = c.getString(c
						.getColumnIndex(CVD_VARIABLE_CURRENT_USER_ROLE));

			} while (c.moveToNext());

		}

		closemainlineDatabase();

		return counter;

	}

	public String getReferalcardval(long patientid) {

		String selectQuery = "SELECT  " + CVD_VARIABLES_REFCODE + " FROM "
				+ TABLE_MAINLINE_RISKASSES_HT + "  WHERE  " + KEY_PATIENT_ID
				+ "=" + patientid;
		String counter = "";
		try {

			openmainlineWritableDatabase();

			Cursor c = ml_db.rawQuery(selectQuery, null);

			if (c != null && c.getCount() > 0) {

				c.moveToFirst();

				do {

					counter = c.getString(c
							.getColumnIndex(CVD_VARIABLE_CURRENT_USER_ROLE));

				} while (c.moveToNext());

			}

			closemainlineDatabase();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return counter;

	}

	public HashMap<String, String> getPatientPriority(long patient_id) {

		HashMap<String, String> patientMap = new HashMap<String, String>();

		String selectQuery = "SELECT  " + CVD_VARIABLES_SH_SMOKING_QUESTION
				+ "," + CVD_VARIABLE_CURRENT_USER_ROLE + " FROM "
				+ TABLE_MAINLINE_CACHE_HT + "  WHERE  " + CVD_VARIABLES_REFCODE
				+ "='" + patient_id + "'";

		openRiskAssesmentWritableDatabase();

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			patientMap.put(PATIENT_ID,
					c.getString(c.getColumnIndex(PATIENT_ID)));

		}
		closeRiskAssesmentDatabase();

		return patientMap;

	}

	public String getSmokingStatus(long patientid) {

		String selectQuery = "SELECT  " + CVD_VARIABLES_SH_SMOKING_QUESTION
				+ " FROM " + TABLE_MAINLINE_RISKASSES_HT + "  WHERE  "
				+ KEY_PATIENT_ID + "=" + patientid;

		openmainlineWritableDatabase();

		String counter = "-1";

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				counter = c.getString(c
						.getColumnIndex(CVD_VARIABLES_SH_SMOKING_QUESTION));

			} while (c.moveToNext());

		}

		closemainlineDatabase();

		return counter;

	}

	public ArrayList<PatientModel> getPatientDetails(int village_id,
			int locality_id, int gender) {

		PatientModel patient = null;

		ArrayList<PatientModel> plist = new ArrayList<PatientModel>();

		String selectQuery = "SELECT * FROM " + TABLE_PATIENT + "  WHERE "
				+ KEY_VILLAGE_ID + "=" + village_id + " AND "
				+ KEY_P_LAST_ENCOUNTER + " BETWEEN -1 AND 1";

		if (locality_id > 0) {

			selectQuery = selectQuery + " AND " + KEY_LOCALITY_ID + "='"
					+ locality_id + "'";
		}

		if (gender > 0) {

			if (gender == 1) {

				selectQuery = selectQuery + " AND " + KEY_GENDER + "='" + "M"
						+ "'";

			} else {

				selectQuery = selectQuery + " AND " + KEY_GENDER + "='" + "F"
						+ "'";

			}
		}

		openRiskAssesmentWritableDatabase();

		try {

			Cursor c = ra_db.rawQuery(selectQuery, null);

			if (c != null && c.getCount() > 0) {

				c.moveToFirst();

				do {

					patient = new PatientModel();

					patient.set_id(c.getLong(c.getColumnIndex(KEY_ID)));
					patient.setPatient_ID(c.getLong(c
							.getColumnIndex(KEY_PATIENT_ID)));

					patient.setPatient_fname(c.getString(c
							.getColumnIndex(KEY_PATIENT_FNAME)));
					patient.setPatient_lname(c.getString(c
							.getColumnIndex(KEY_PATIENT_LNAME)));

					patient.setAge(c.getInt(c.getColumnIndex(KEY_AGE)));

					plist.add(patient);
				} while (c.moveToNext());

			}

			closeRiskAssesmentDatabase();

		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}

		return plist;

	}

	public void loadTeluguFontFromDB(long village_id) {

		String selectQuery = "SELECT * FROM " + TABLE_PATIENT + "  WHERE "
				+ KEY_VILLAGE_ID + "=" + village_id;

		openRiskAssesmentWritableDatabase();

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				telugunames.put(c.getString(c.getColumnIndex(KEY_PATIENT_ID)),
						getPatientName(c.getString(c
								.getColumnIndex(KEY_PATIENT_ID))));

			} while (c.moveToNext());

			persistenceData.setTeluguFontMap(telugunames,
					String.valueOf(village_id));

		}

		closeRiskAssesmentDatabase();

		loadLocTeluguFontFromDB(village_id);

	}

	private void loadLocTeluguFontFromDB(long village_id) {
		// TODO Auto-generated method stub

		String selectlocQuery = "SELECT * FROM " + TABLE_LOCALITY + "  WHERE "
				+ KEY_VILLAGE_ID + "=" + village_id;

		openRiskAssesmentWritableDatabase();

		Cursor localitycur = ra_db.rawQuery(selectlocQuery, null);

		List<String> localityList = new ArrayList<String>();

		if (localitycur != null && localitycur.getCount() > 0) {

			localitycur.moveToFirst();

			do {

				localityList.add(localitycur.getString(localitycur
						.getColumnIndex(KEY_LOCALITY_NAME)));

			} while (localitycur.moveToNext());

		}

		closeRiskAssesmentDatabase();

		getLocalityTeluguFont(String.valueOf(village_id), localityList);
	}

	private void getLocalityTeluguFont(String vill_id, List<String> localityList) {
		// TODO Auto-generated method stub

		for (String loc : localityList) {

			loctelugunames.put(loc, getTeluguName(loc));

		}

		persistenceData.setLocTeluguFontMap(loctelugunames,
				String.valueOf(vill_id));

	}

	public ArrayList<PatientModel> getPatientDetails_Village(long village_id,
			String language) {

		PatientModel patient = null;

		ArrayList<PatientModel> plist = new ArrayList<PatientModel>();

		String selectQuery = "SELECT * FROM " + TABLE_PATIENT + "  WHERE "
				+ KEY_VILLAGE_ID + "=" + village_id + " AND "
				+ KEY_P_LAST_ENCOUNTER + " BETWEEN -1 AND 1";

		openRiskAssesmentWritableDatabase();

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				patient = new PatientModel();

				patient.set_id(c.getLong(c.getColumnIndex(KEY_ID)));

				patient.setPatient_ID(c.getLong(c
						.getColumnIndex(KEY_PATIENT_ID)));

				patient.setPatient_fname(c.getString(c
						.getColumnIndex(KEY_PATIENT_FNAME)));

				patient.setPatient_lname(c.getString(c
						.getColumnIndex(KEY_PATIENT_LNAME)));

				patient.setAge(c.getInt(c.getColumnIndex(KEY_AGE)));

				/*
				 * telugunames.put(
				 * c.getString(c.getColumnIndex(KEY_PATIENT_ID)),
				 * c.getString(c.getColumnIndex(KEY_PATIENT_FNAME_TE)) + " " +
				 * c.getString(c .getColumnIndex(KEY_PATIENT_LNAME_TE)));
				 */

				// patient.setTelugu_name(getPatientName(c.getString(c.getColumnIndex(KEY_PATIENT_ID))));

				plist.add(patient);

			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return plist;

	}

	public ArrayList<UserModel> getUserDetails() {// '''''''''''''''''''

		UserModel user = null;

		ArrayList<UserModel> list = new ArrayList<UserModel>();

		// String selectQuery = "SELECT * FROM " + TABLE_MAINLINE_HT +
		// "  WHERE " + KEY_INTERVIEW_STATUS +"="+"'"+status+"'";

		String selectQuery = "SELECT * FROM " + TABLE_USER;

		openRiskAssesmentWritableDatabase();
		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				user = new UserModel();

				user.setUserid(c.getString(c.getColumnIndex(KEY_USERID)));

				user.setFname(c.getString(c.getColumnIndex(KEY_FNAME)));
				user.setLname(c.getString(c.getColumnIndex(KEY_LNAME)));

				user.setRole(c.getString(c.getColumnIndex(KEY_ROLE)));

				list.add(user);
			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return list;

	}

	public ArrayList<PatientModel> getReviewPatientDetails_Village(
			long village_id, String asha_assigned) {

		PatientModel patient = null;

		ArrayList<PatientModel> plist = new ArrayList<PatientModel>();

		String selectQuery = "SELECT * FROM " + TABLE_PATIENT + "  WHERE "
				+ KEY_VILLAGE_ID + "=" + village_id + " AND "
				+ KEY_P_LAST_ENCOUNTER + " NOT BETWEEN -1 AND 1 AND "
				+ KEY_ASHA_ASSIGNED + "='" + asha_assigned + "'";

		openRiskAssesmentWritableDatabase();
		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				patient = new PatientModel();

				patient.set_id(c.getLong(c.getColumnIndex(KEY_ID)));
				patient.setPatient_ID(c.getLong(c
						.getColumnIndex(KEY_PATIENT_ID)));

				patient.setPatient_fname(c.getString(c
						.getColumnIndex(KEY_PATIENT_FNAME)));
				patient.setPatient_lname(c.getString(c
						.getColumnIndex(KEY_PATIENT_LNAME)));
				patient.setAge(c.getInt(c.getColumnIndex(KEY_AGE)));

				patient.setLast_encounter(c.getString(c
						.getColumnIndex(KEY_P_LAST_ENCOUNTER)));

				plist.add(patient);

			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return plist;

	}

	public ArrayList<PatientModel> getPatientDetailByLocalityId(
			long locality_id, int villageid) {// '''''''''''''''''''

		PatientModel patient = null;

		ArrayList<PatientModel> plist = new ArrayList<PatientModel>();

		// String selectQuery = "SELECT * FROM " + TABLE_MAINLINE_HT +
		// "  WHERE " + KEY_INTERVIEW_STATUS +"="+"'"+status+"'";
		String selectQuery = "SELECT * FROM " + TABLE_PATIENT + "  WHERE "
				+ KEY_LOCALITY_ID + "=" + locality_id + " AND  "
				+ KEY_VILLAGE_ID + "=" + villageid;

		openRiskAssesmentWritableDatabase();
		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				patient = new PatientModel();

				patient.set_id(c.getLong(c.getColumnIndex(KEY_ID)));
				patient.setPatient_ID(c.getLong(c
						.getColumnIndex(KEY_PATIENT_ID)));

				patient.setPatient_fname(c.getString(c
						.getColumnIndex(KEY_PATIENT_FNAME)));
				patient.setPatient_lname(c.getString(c
						.getColumnIndex(KEY_PATIENT_LNAME)));

				patient.setAge(c.getInt(c.getColumnIndex(KEY_AGE)));

				plist.add(patient);
			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return plist;

	}

	public ArrayList<PatientModel> getPatientDetailByGender(String gender,
			int villageid) {// '''''''''''''''''''

		PatientModel patient = null;

		ArrayList<PatientModel> plist = new ArrayList<PatientModel>();

		// String selectQuery = "SELECT * FROM " + TABLE_MAINLINE_HT +
		// "  WHERE " + KEY_INTERVIEW_STATUS +"="+"'"+status+"'";
		String selectQuery = "SELECT * FROM " + TABLE_PATIENT + "  WHERE "
				+ KEY_GENDER + "=" + "'" + gender + "'" + " AND  "
				+ KEY_VILLAGE_ID + "=" + villageid;

		openRiskAssesmentWritableDatabase();
		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				patient = new PatientModel();

				patient.set_id(c.getLong(c.getColumnIndex(KEY_ID)));
				patient.setPatient_ID(c.getLong(c
						.getColumnIndex(KEY_PATIENT_ID)));
				patient.setPatient_fname(c.getString(c
						.getColumnIndex(KEY_PATIENT_FNAME)));
				patient.setPatient_lname(c.getString(c
						.getColumnIndex(KEY_PATIENT_LNAME)));

				patient.setAge(c.getInt(c.getColumnIndex(KEY_AGE)));

				plist.add(patient);
			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return plist;

	}

	public ArrayList<PatientModel> getReviewPatientData(int village_id,
			long locality_id, int gender) {

		PatientModel patient = null;

		ArrayList<PatientModel> plist = new ArrayList<PatientModel>();

		ArrayList<PatientModel> doc_plist = new ArrayList<PatientModel>();

		// String selectQuery = "SELECT * FROM " + TABLE_MAINLINE_HT +
		// "  WHERE " + KEY_INTERVIEW_STATUS +"="+"'"+status+"'";

		/*
		 * String selectQuery1 = "SELECT * FROM " + TABLE_PATIENT + "  WHERE " +
		 * KEY_LOCALITY_ID + "=" + locality_id + " AND " + KEY_GENDER + "=" +
		 * "'" + gender + "'" + " AND " + KEY_P_LAST_ENCOUNTER +
		 * " NOT BETWEEN -1 AND 1";
		 */

		String selectQuery = "SELECT * FROM " + TABLE_PATIENT + "  WHERE "
				+ KEY_VILLAGE_ID + "=" + village_id + " AND "
				+ KEY_P_LAST_ENCOUNTER + "  NOT BETWEEN -1 AND 1";

		if (locality_id > 0) {

			selectQuery = selectQuery + " AND " + KEY_LOCALITY_ID + "='"
					+ locality_id + "'";
		}

		if (gender > 0) {

			if (gender == 1) {

				selectQuery = selectQuery + " AND " + KEY_GENDER + "='" + "M"
						+ "'";

			} else {

				selectQuery = selectQuery + " AND " + KEY_GENDER + "='" + "F"
						+ "'";
			}

		}

		openRiskAssesmentWritableDatabase();

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				patient = new PatientModel();

				patient.set_id(c.getLong(c.getColumnIndex(KEY_ID)));
				patient.setPatient_ID(c.getLong(c
						.getColumnIndex(KEY_PATIENT_ID)));

				patient.setPatient_fname(c.getString(c
						.getColumnIndex(KEY_PATIENT_FNAME)));
				patient.setPatient_lname(c.getString(c
						.getColumnIndex(KEY_PATIENT_LNAME)));

				patient.setAge(c.getInt(c.getColumnIndex(KEY_AGE)));

				plist.add(patient);
			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return plist;

	}

	public List<PatientModel> getHighRiskPatientsforPriorityListing() {

		List<PatientModel> highriskpatients = null;

		List<PatientModel> resulthighriskpatients = null;

		try {

			highriskpatients = new ArrayList<PatientModel>();

			resulthighriskpatients = new ArrayList<PatientModel>();

			Log.v("village", "village" + persistenceData.getVillage());

			highriskpatients = getPriorityList(persistenceData.getVillage(),
					persistenceData.getUserName());
			
			Log.v("list", "userslist" + highriskpatients.size());

			for (PatientModel patient_obj : highriskpatients) {

				if (patientIsHighRiskInLastVisit(patient_obj.getPatient_ID(),
						DatabaseVariables.TABLE_MAINLINE_RISKASSES_HT)) {

					resulthighriskpatients.add(patient_obj);

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return resulthighriskpatients;

	}

	public ArrayList<PatientModel> getPriorityList(String village,
			String asha_assigned) {

		ArrayList<PatientModel> plist = new ArrayList<PatientModel>();

		int villageid = getVillageid(village);

		PatientModel patientModel = null;

		String selectQuery = "SELECT * FROM " + TABLE_PATIENT + "  WHERE "
				+ KEY_VILLAGE_ID + "='" + villageid + "'  AND "
				+ KEY_P_LAST_ENCOUNTER + " NOT BETWEEN -1 AND 1 AND "
				+ KEY_ASHA_ASSIGNED + "='" + asha_assigned + "'" + " ORDER BY "
				+ KEY_PL_SCORE + " DESC";

		openRiskAssesmentReadbleDatabase();

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				patientModel = new PatientModel();

				patientModel.setPatient_ID(c.getLong(c
						.getColumnIndex(KEY_PATIENT_ID)));

				patientModel.setPatient_fname(c.getString(c
						.getColumnIndex(KEY_FNAME)));
				patientModel.setPatient_lname(c.getString(c
						.getColumnIndex(KEY_LNAME)));

				patientModel.setLocalityname(getLocalityName(Integer.parseInt(c
						.getString(c.getColumnIndex(KEY_LOCALITY_ID)))));

				plist.add(patientModel);
			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return plist;

	}

	private String getLocalityName(int localityid) {
		// TODO Auto-generated method stub

		String result = "-1";

		String selectQuery = "SELECT " + KEY_LOCALITY_NAME + " FROM "
				+ TABLE_LOCALITY + "  WHERE " + KEY_ID + "=" + localityid;

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			if (c.getString(0) != null) {
				result = c.getString(0);

			}
			c.close();

		}

		return result;

	}

	public String getLocalityName(String locality_id) {
		// TODO Auto-generated method stub

		String result = "-1";
		openRiskAssesmentWritableDatabase();

		String selectQuery = "SELECT " + KEY_LOCALITY_NAME + " FROM "
				+ TABLE_LOCALITY + "  WHERE " + KEY_ID + "='" + locality_id
				+ "'";

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			if (c.getString(0) != null) {
				result = c.getString(0);

			}

		}
		closeRiskAssesmentDatabase();
		return result;

	}

	public String getLocalityID(long patient_id) {
		// TODO Auto-generated method stub

		String result = "-1";
		openRiskAssesmentWritableDatabase();

		String selectQuery = "SELECT " + KEY_LOCALITY_ID + " FROM "
				+ TABLE_PATIENT + "  WHERE " + KEY_PATIENT_ID + "="
				+ patient_id;

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			if (c.getString(0) != null) {
				result = c.getString(c.getColumnIndex(KEY_LOCALITY_ID));

			}

		}
		closeRiskAssesmentDatabase();
		return result;

	}

	public ArrayList<PatientModel> getPatient_LocalityDataByGender(
			long locality_id, String gender, int villageid) {

		PatientModel patient = null;

		ArrayList<PatientModel> plist = new ArrayList<PatientModel>();

		String selectQuery = "SELECT * FROM " + TABLE_PATIENT + "  WHERE "
				+ KEY_LOCALITY_ID + "=" + locality_id + " AND " + KEY_GENDER
				+ "=" + "'" + gender + "'" + " AND " + KEY_VILLAGE_ID + " ="
				+ villageid;

		openRiskAssesmentReadbleDatabase();
		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				patient = new PatientModel();

				patient.set_id(c.getLong(c.getColumnIndex(KEY_ID)));
				patient.setPatient_ID(c.getLong(c
						.getColumnIndex(KEY_PATIENT_ID)));

				patient.setPatient_fname(c.getString(c
						.getColumnIndex(KEY_PATIENT_FNAME)));
				patient.setPatient_lname(c.getString(c
						.getColumnIndex(KEY_PATIENT_LNAME)));

				patient.setAge(c.getInt(c.getColumnIndex(KEY_AGE)));

				plist.add(patient);
			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return plist;

	}

	public ArrayList<PatientModel> getPatientLocalityDatabyGender(
			long locality_id, String gender, int villageid) {

		PatientModel patient = null;

		ArrayList<PatientModel> plist = new ArrayList<PatientModel>();

		String selectQuery = "SELECT * FROM " + TABLE_PATIENT + "  WHERE "
				+ KEY_LOCALITY_ID + "=" + locality_id + " AND " + KEY_GENDER
				+ "=" + "'" + gender + "'" + " AND " + KEY_P_LAST_ENCOUNTER
				+ " IN " + " (-1,1)";

		openRiskAssesmentReadbleDatabase();
		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			do {

				patient = new PatientModel();

				patient.set_id(c.getLong(c.getColumnIndex(KEY_ID)));
				patient.setPatient_ID(c.getLong(c
						.getColumnIndex(KEY_PATIENT_ID)));

				patient.setPatient_fname(c.getString(c
						.getColumnIndex(KEY_PATIENT_FNAME)));
				patient.setPatient_lname(c.getString(c
						.getColumnIndex(KEY_PATIENT_LNAME)));

				patient.setAge(c.getInt(c.getColumnIndex(KEY_AGE)));

				plist.add(patient);
			} while (c.moveToNext());

		}

		closeRiskAssesmentDatabase();

		return plist;

	}

	public UserModel getUserData(String uname, String password) {

		UserModel user = new UserModel();
		String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
				+ KEY_USERNAME + "=" + "'" + uname + "'" + "AND "
				+ KEY_PASSWORD + "=" + "'" + password + "'";
		openRiskAssesmentWritableDatabase();

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			user = new UserModel();
			c.moveToFirst();
			user.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
			user.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
			user.setPhc(c.getString(c.getColumnIndex(KEY_PHC_CODE)));
			user.setRole(c.getString(c.getColumnIndex(KEY_ROLE)));
			user.setUserid(c.getString(c.getColumnIndex(KEY_USERID)));
			user.setUsername(c.getString(c.getColumnIndex(KEY_USERNAME)));
			user.setVillage(c.getString(c.getColumnIndex(KEY_VILLAGE)));

			user.setFname(c.getString(c.getColumnIndex(KEY_FNAME)));
			user.setLname(c.getString(c.getColumnIndex(KEY_LNAME)));

		}

		closeRiskAssesmentDatabase();

		return user;

	}

	public long insertPatientHT(PatientModel patient) {

		openRiskAssesmentWritableDatabase();

		ContentValues initialValues = new ContentValues();

		initialValues.put(KEY_PATIENT_FNAME, patient.getPatient_fname());

		initialValues.put(KEY_PATIENT_LNAME, patient.getPatient_lname());
		initialValues.put(KEY_AGE, patient.getAge());

		initialValues.put(KEY_GENDER, patient.getGender());

		long rowId = ra_db.insert(TABLE_MAINLINE_CACHE_HT, null, initialValues);
		closeRiskAssesmentDatabase();

		return rowId;

	}

	/*
	 * public long insertPatient(PatientModel patient) {
	 * 
	 * openListingInfoWritableDatabase();
	 * 
	 * ContentValues initialValues = new ContentValues();
	 * 
	 * initialValues.put(KEY_PATIENT_FNAME, patient.getPatient_fname());
	 * 
	 * initialValues.put(KEY_PATIENT_LNAME, patient.getPatient_lname());
	 * initialValues.put(KEY_AGE, patient.getAge());
	 * 
	 * initialValues.put(KEY_GENDER, patient.getGender());
	 * initialValues.put(KEY_IS_HEAD_OF_HH, patient.getIs_head_of_hh());
	 * initialValues.put(KEY_HH_ID, patient.getHh_id());
	 * initialValues.put(KEY_HDC, patient.getHdc()); initialValues.put(KEY_RDC,
	 * patient.getRdc()); initialValues.put(KEY_INTERVIEW_STATUS,
	 * patient.getInterview_status()); initialValues.put(KEY_PATIENT_ID,
	 * patient.getPatient_ID()); initialValues.put(KEY_LOCALITY_ID,
	 * patient.getLocality_id());
	 * 
	 * long rowId = li_db.insert(TABLE_PATIENT, null, initialValues);
	 * closeListingInfoDatabase();
	 * 
	 * return rowId;
	 * 
	 * }
	 */

	public long insertPatientForMT(PatientModel patient) {

		openRiskAssesmentWritableDatabase();

		ContentValues initialValues = new ContentValues();

		initialValues.put(KEY_PATIENT_FNAME, patient.getPatient_fname());

		initialValues.put(KEY_PATIENT_LNAME, patient.getPatient_lname());
		initialValues.put(KEY_AGE, patient.getAge());

		initialValues.put(KEY_GENDER, patient.getGender());
		initialValues.put(KEY_PATIENT_ID, patient.getPatient_ID());
		long rowId = ra_db.insert(TABLE_MAINLINE_CACHE_HT, null, initialValues);
		closeRiskAssesmentDatabase();

		return rowId;

	}

	public void updatePatientInterViewStatus(long patientId,
			int encounter_status) {

		openRiskAssesmentWritableDatabase();

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_P_LAST_ENCOUNTER, encounter_status);

		ra_db.update(TABLE_PATIENT, initialValues, KEY_PATIENT_ID + " = ?",
				new String[] { String.valueOf(patientId) });

		closeRiskAssesmentDatabase();

	}

	public void updaterefval(long patientId, long qrcode) {

		openRiskAssesmentWritableDatabase();

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_P_REFERALCARD_NUM, qrcode);
		// initialValues.put(CVD_VARIABLE_UPDATED_DATE, fDateNtime);

		ra_db.update(TABLE_PATIENT, initialValues, KEY_PATIENT_ID + " = ?",
				new String[] { String.valueOf(patientId) });

		closeRiskAssesmentDatabase();

	}

	public void updateCounterval(long patientId, int counter, long lastencounter) {

		openRiskAssesmentWritableDatabase();

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_P_LAST_ENCOUNTER, counter);

		// currentEncounter

		// initialValues.put(CVD_VARIABLE_UPDATED_DATE, fDateNtime);

		ra_db.update(TABLE_PATIENT, initialValues, KEY_PATIENT_ID + " = ?",
				new String[] { String.valueOf(patientId) });

		closeRiskAssesmentDatabase();

		openmainlineWritableDatabase();

		ContentValues initialValues1 = new ContentValues();
		initialValues1.put(CVD_VARIABLE_CURRENT_ENCOUNTER, counter);
		initialValues1.put(CVD_VARIABLE_LAST_ENCOUNTER, lastencounter);
		initialValues1.put(CVD_VARIABLES_ENC_type, counter);

		// initialValues.put(CVD_VARIABLE_UPDATED_DATE, fDateNtime);

		ml_db.update(TABLE_MAINLINE_CACHE_HT, initialValues1, KEY_PATIENT_ID
				+ " = ?", new String[] { String.valueOf(patientId) });

		closemainlineDatabase();

	}

	public void updateCount(long patientId, String counter, String lastencounter) {

		openRiskAssesmentWritableDatabase();

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_P_LAST_ENCOUNTER, counter);

		// currentEncounter

		// initialValues.put(CVD_VARIABLE_UPDATED_DATE, fDateNtime);

		ra_db.update(TABLE_PATIENT, initialValues, KEY_PATIENT_ID + " = ?",
				new String[] { String.valueOf(patientId) });

		closeRiskAssesmentDatabase();

		openmainlineWritableDatabase();

		ContentValues initialValues1 = new ContentValues();
		initialValues1.put(CVD_VARIABLE_CURRENT_ENCOUNTER, counter);
		initialValues1.put(CVD_VARIABLE_LAST_ENCOUNTER, lastencounter);

		// initialValues.put(CVD_VARIABLE_UPDATED_DATE, fDateNtime);

		ml_db.update(TABLE_MAINLINE_CACHE_HT, initialValues1, KEY_PATIENT_ID
				+ " = ?", new String[] { String.valueOf(patientId) });

		closemainlineDatabase();

	}

	public void updatePatientEncounter(long patientId, String counter,
			String ref_code) {

		openRiskAssesmentWritableDatabase();

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_P_LAST_ENCOUNTER, counter);
		initialValues.put(KEY_P_REFERALCARD_NUM, ref_code);

		ra_db.update(TABLE_PATIENT, initialValues, KEY_PATIENT_ID + " = ?",
				new String[] { String.valueOf(patientId) });

		closeRiskAssesmentDatabase();

	}

	public void updateAsha(long patientId, String asha_assigned) {

		openRiskAssesmentWritableDatabase();

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ASHA_ASSIGNED, asha_assigned);

		ra_db.update(TABLE_PATIENT, initialValues, KEY_PATIENT_ID + " = ?",
				new String[] { String.valueOf(patientId) });

		closeRiskAssesmentDatabase();
	}

	public void updaterefvalToML(long patientId, long qrcode) {

		openmainlineWritableDatabase();

		ContentValues initialValues = new ContentValues();
		initialValues.put(CVD_VARIABLES_REFCODE, qrcode);
		// initialValues.put(CVD_VARIABLE_UPDATED_DATE, fDateNtime);

		ml_db.update(TABLE_MAINLINE_CACHE_HT, initialValues, KEY_PATIENT_ID
				+ " = ?", new String[] { String.valueOf(patientId) });

		closemainlineDatabase();

	}

	/*
	 * public void updatePatientID(long _Id, long patient_ID) {
	 * 
	 * openListingInfoWritableDatabase();
	 * 
	 * ContentValues initialValues = new ContentValues();
	 * initialValues.put(KEY_PATIENT_ID, patient_ID);
	 * 
	 * li_db.update(TABLE_PATIENT, initialValues, KEY_ID + " = ?", new String[]
	 * { String.valueOf(_Id) });
	 * 
	 * closeListingInfoDatabase();
	 * 
	 * }
	 */

	public void updatePatientIDMT(long _Id, long patient_ID) {

		openRiskAssesmentWritableDatabase();
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_PATIENT_ID, patient_ID);

		ra_db.update(TABLE_MAINLINE_CACHE_HT, initialValues, KEY_ID + " = ?",
				new String[] { String.valueOf(_Id) });

		closeRiskAssesmentDatabase();
	}

	public boolean doesPatientAlreadyExists(String tableName, long patient) {

		boolean result = false;

		if (patient == -1)
			return false;
		openRiskAssesmentWritableDatabase();

		Cursor mCursor = ra_db.rawQuery("SELECT COUNT(*) FROM " + tableName
				+ " WHERE " + KEY_PATIENT_ID + "=" + patient, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
			if (mCursor.getInt(0) > 0) {
				result = true;

			}
			mCursor.close();
		}

		closeRiskAssesmentDatabase();
		return result;
	}

	public boolean doesDoctorReviewCompletes(String role, long patient) {

		boolean result = false;

		if (patient == -1)
			return false;
		openmainlineWritableDatabase();
		String query = "SELECT COUNT(*) FROM " + TABLE_MAINLINE_RISKASSES_HT
				+ " WHERE " + KEY_PATIENT_ID + "=" + patient + " AND "
				+ CVD_VARIABLE_CURRENT_USER_ROLE + "='" + role + "'";

		Cursor mCursor = ml_db.rawQuery(query, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
			if (mCursor.getInt(0) > 0) {
				result = true;

			}
			mCursor.close();
		}

		closemainlineDatabase();
		return result;
	}

	public HashMap<String, String> getPatientIDbyQRcode(String qrcode) {

		HashMap<String, String> patientMap = new HashMap<String, String>();

		String selectQuery = "SELECT  " + PATIENT_ID + " FROM "
				+ TABLE_MAINLINE_CACHE_HT + "  WHERE  " + CVD_VARIABLES_REFCODE
				+ "='" + qrcode + "'";

		openRiskAssesmentWritableDatabase();

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			patientMap.put(PATIENT_ID,
					c.getString(c.getColumnIndex(PATIENT_ID)));

		}
		closeRiskAssesmentDatabase();

		return patientMap;

	}

	public HashMap<String, String> getPatientbyQRcode(long pid) {

		HashMap<String, String> patientMap = new HashMap<String, String>();

		String selectQuery = "SELECT  * FROM " + TABLE_MAINLINE_CACHE_HT
				+ "  WHERE  " + PATIENT_ID + "=" + pid;

		openRiskAssesmentWritableDatabase();

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			patientMap.put(PATIENT_ID,
					c.getString(c.getColumnIndex(PATIENT_ID)));

			patientMap.put(CVD_VARIABLES_P_CNUMBER,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_CNUMBER)));

			patientMap.put(CVD_VARIABLES_P_FIRSTNAME,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_FIRSTNAME)));

			patientMap.put(CVD_VARIABLES_P_SURNAME,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_SURNAME)));

			patientMap.put(CVD_VARIABLES_P_CONTACTNUM,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_CONTACTNUM)));

			patientMap.put(CVD_VARIABLES_P_AGE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_AGE)));
			patientMap.put(CVD_VARIABLES_P_GENDER,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_GENDER)));

			patientMap.put(CVD_VARIABLES_P_ADDRESS,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_ADDRESS)));

			patientMap.put(CVD_VARIABLES_P_VILLAGE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_VILLAGE)));
			/*
			 * patientMap.put(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE,
			 * c.getString
			 * (c.getColumnIndex(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE)));
			 */

		}
		closeRiskAssesmentDatabase();

		return patientMap;

	}

	public HashMap<String, String> getPatientData(long patient_id) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			String selectQuery = "SELECT  * FROM " + TABLE_PATIENT
					+ "  WHERE  " + KEY_PATIENT_ID + "=" + patient_id;

			openRiskAssesmentWritableDatabase();

			Cursor c = ra_db.rawQuery(selectQuery, null);

			if (c != null && c.getCount() > 0) {

				c.moveToFirst();

				map.put(KEY_PATIENT_ID,
						c.getString(c.getColumnIndex(KEY_PATIENT_ID)));
				map.put(KEY_CNUMBER, c.getString(c.getColumnIndex(KEY_CNUMBER)));
				map.put(KEY_PATIENT_FNAME,
						c.getString(c.getColumnIndex(KEY_PATIENT_FNAME)));
				map.put(KEY_PATIENT_LNAME,
						c.getString(c.getColumnIndex(KEY_PATIENT_LNAME)));

				map.put(KEY_P_DOB, c.getString(c.getColumnIndex(KEY_P_DOB)));

				map.put(KEY_AGE_CHECKED,
						c.getString(c.getColumnIndex(KEY_AGE_CHECKED)));

				map.put(KEY_GENDER, c.getString(c.getColumnIndex(KEY_GENDER)));

				map.put(KEY_CONTACT_NUMBER,
						c.getString(c.getColumnIndex(KEY_CONTACT_NUMBER)));
				map.put(KEY_IS_CONTACT_NUMBER_SHARED, c.getString(c
						.getColumnIndex(KEY_IS_CONTACT_NUMBER_SHARED)));

				map.put(KEY_ADDRESS, c.getString(c.getColumnIndex(KEY_ADDRESS)));

				map.put(KEY_AGE, c.getString(c.getColumnIndex(KEY_AGE)));

			}
			closeRiskAssesmentDatabase();

		} catch (Exception k) {

			k.printStackTrace();
		}

		return map;

	}

	public HashMap<String, String> getPatientDataFormMainTable(long patient_id) {

		HashMap<String, String> map = new HashMap<String, String>();

		String selectQuery = "SELECT  * FROM " + TABLE_MAINLINE_CACHE_HT
				+ "  WHERE  " + PATIENT_ID + "=" + patient_id;

		openmainlineWritableDatabase();
		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			map.put(PATIENT_ID, c.getString(c.getColumnIndex(PATIENT_ID)));
			map.put(CVD_VARIABLES_P_CNUMBER,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_CNUMBER)));
			map.put(CVD_VARIABLES_P_FIRSTNAME,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_FIRSTNAME)));
			map.put(CVD_VARIABLES_P_SURNAME,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_SURNAME)));

			map.put(CVD_VARIABLES_P_DOB,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_DOB)));

			/*
			 * map.put(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE, c.getString(c
			 * .getColumnIndex(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE)));
			 */

			map.put(CVD_VARIABLES_P_GENDER,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_GENDER)));

			map.put(CVD_VARIABLES_P_CONTACTNUM,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_CONTACTNUM)));
			map.put(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE, c.getString(c
					.getColumnIndex(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE)));

			map.put(CVD_VARIABLES_P_ADDRESS,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_ADDRESS)));

			map.put(CVD_VARIABLES_P_AGE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_AGE)));

			map.put(KEY_AADHAR_NO, c.getString(c.getColumnIndex(KEY_AADHAR_NO)));

		}
		closemainlineDatabase();
		return map;

	}

	public HashMap<String, String> getUserData(String user_id) {

		HashMap<String, String> map = new HashMap<String, String>();

		String selectQuery = "SELECT  * FROM " + TABLE_USER + "  WHERE  "
				+ KEY_USERID + "=" + "'" + user_id + "'";

		openRiskAssesmentWritableDatabase();
		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			map.put(KEY_USERID, c.getString(c.getColumnIndex(KEY_USERID)));
			map.put(KEY_FNAME, c.getString(c.getColumnIndex(KEY_FNAME)));
			map.put(KEY_LNAME, c.getString(c.getColumnIndex(KEY_LNAME)));

			map.put(KEY_GENDER, c.getString(c.getColumnIndex(KEY_GENDER)));

			/*
			 * 
			 * 
			 * map.put(KEY_CONTACT_NO,
			 * c.getString(c.getColumnIndex(KEY_CONTACT_NO)));
			 */

			map.put(KEY_USERNAME, c.getString(c.getColumnIndex(KEY_USERNAME)));
			map.put(KEY_PASSWORD, c.getString(c.getColumnIndex(KEY_PASSWORD)));
			map.put(KEY_PHC_CODE, c.getString(c.getColumnIndex(KEY_PHC_CODE)));
			map.put(KEY_ROLE, c.getString(c.getColumnIndex(KEY_ROLE)));

			map.put(KEY_CONTACT_NO,
					c.getString(c.getColumnIndex(KEY_CONTACT_NO)));

			/*
			 * map.put(KEY_VILLAGE_NAME,
			 * c.getString(c.getColumnIndex(KEY_VILLAGE_NAME)));
			 */

		}
		closeRiskAssesmentDatabase();
		return map;

	}

	public HashMap<String, String> getRAPatient(long patient_id,
			String table_name) {

		HashMap<String, String> map = new HashMap<String, String>();

		String selectQuery = "SELECT  * FROM " + table_name + "  WHERE  "
				+ KEY_PATIENT_ID + "=" + patient_id + " ORDER BY "
				+ CVD_VARIABLE_UPDATED_DATE + " DESC LIMIT 1";

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();
			try {
				map.put(KEY_PATIENT_ID,
						c.getString(c.getColumnIndex(KEY_PATIENT_ID)));
				map.put(CVD_VARIABLES_P_CNUMBER,
						c.getString(c.getColumnIndex(CVD_VARIABLES_P_CNUMBER)));
				map.put(CVD_VARIABLES_P_FIRSTNAME, c.getString(c
						.getColumnIndex(CVD_VARIABLES_P_FIRSTNAME)));
				map.put(CVD_VARIABLES_P_SURNAME,
						c.getString(c.getColumnIndex(CVD_VARIABLES_P_SURNAME)));

				map.put(CVD_VARIABLES_P_DOB,
						c.getString(c.getColumnIndex(CVD_VARIABLES_P_DOB)));
				map.put(CVD_VARIABLES_P_DOA,
						c.getString(c.getColumnIndex(CVD_VARIABLES_P_DOA)));
				map.put(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE,
						c.getString(c
								.getColumnIndex(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE)));
				map.put(CVD_VARIABLES_P_GENDER,
						c.getString(c.getColumnIndex(CVD_VARIABLES_P_GENDER)));

				map.put(CVD_VARIABLES_P_CONTACTNUM, c.getString(c
						.getColumnIndex(CVD_VARIABLES_P_CONTACTNUM)));
				map.put(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE,
						c.getString(c
								.getColumnIndex(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE)));

				map.put(CVD_VARIABLES_P_ADDRESS,
						c.getString(c.getColumnIndex(CVD_VARIABLES_P_ADDRESS)));
				map.put(CVD_VARIABLES_P_VILLAGE,
						c.getString(c.getColumnIndex(CVD_VARIABLES_P_VILLAGE)));
				map.put(CVD_VARIABLES_P_LOCALITY,
						c.getString(c.getColumnIndex(CVD_VARIABLES_P_LOCALITY)));

				map.put(CVD_VARIABLES_P_AGE,
						c.getString(c.getColumnIndex(CVD_VARIABLES_P_AGE)));

				map.put(KEY_AADHAR_NO,
						c.getString(c.getColumnIndex(KEY_AADHAR_NO)));

				map.put(CVD_VARIABLES_PH_HRTATTACK, c.getString(c
						.getColumnIndex(CVD_VARIABLES_PH_HRTATTACK)));

				map.put(CVD_VARIABLES_PH_STROKE,
						c.getString(c.getColumnIndex(CVD_VARIABLES_PH_STROKE)));

				map.put(CVD_VARIABLES_PH_BP,
						c.getString(c.getColumnIndex(CVD_VARIABLES_PH_BP)));

				map.put(CVD_VARIABLES_PH_BP_SINCE, c.getString(c
						.getColumnIndex(CVD_VARIABLES_PH_BP_SINCE)));

				map.put(CVD_VARIABLES_PH_PVD,
						c.getString(c.getColumnIndex(CVD_VARIABLES_PH_PVD)));
				map.put(CVD_VARIABLES_PH_PVD_SINCE, c.getString(c
						.getColumnIndex(CVD_VARIABLES_PH_PVD_SINCE)));

				map.put(CVD_VARIABLES_PH_DM,
						c.getString(c.getColumnIndex(CVD_VARIABLES_PH_DM)));

				map.put(CVD_VARIABLES_PH_BP,
						c.getString(c.getColumnIndex(CVD_VARIABLES_PH_BP)));

				map.put(CVD_VARIABLES_PH_BP_MEDICATION, c.getString(c
						.getColumnIndex(CVD_VARIABLES_PH_BP_MEDICATION)));

				map.put(CVD_VARIABLES_PH_DM,
						c.getString(c.getColumnIndex(CVD_VARIABLES_PH_DM)));

				map.put(CVD_VARIABLES_PH_HRTATTACK_SINCE, c.getString(c
						.getColumnIndex(CVD_VARIABLES_PH_HRTATTACK_SINCE)));

				map.put(CVD_VARIABLES_PH_STROKE_SINCE, c.getString(c
						.getColumnIndex(CVD_VARIABLES_PH_STROKE_SINCE)));

				map.put(CVD_VARIABLES_PH_DM_SINCE, c.getString(c
						.getColumnIndex(CVD_VARIABLES_PH_DM_SINCE)));

				map.put(CVD_VARIABLES_FH_HRATTACK, c.getString(c
						.getColumnIndex(CVD_VARIABLES_FH_HRATTACK)));
				map.put(CVD_VARIABLES_FH_STROKE,
						c.getString(c.getColumnIndex(CVD_VARIABLES_FH_STROKE)));
				map.put(CVD_VARIABLES_FH_DM,
						c.getString(c.getColumnIndex(CVD_VARIABLES_FH_DM)));

				map.put(CVD_VARIABLES_TH_BP,
						c.getString(c.getColumnIndex(CVD_VARIABLES_TH_BP)));
				map.put(CVD_VARIABLES_TH_LLTT,
						c.getString(c.getColumnIndex(CVD_VARIABLES_TH_LLTT)));
				map.put(CVD_VARIABLES_TH_APTT,
						c.getString(c.getColumnIndex(CVD_VARIABLES_TH_APTT)));
				map.put(CVD_VARIABLES_TH_DM,
						c.getString(c.getColumnIndex(CVD_VARIABLES_TH_DM)));

				map.put(CVD_VARIABLES_SH_SMOKING_QUESTION, c.getString(c
						.getColumnIndex(CVD_VARIABLES_SH_SMOKING_QUESTION)));
				map.put(CVD_VARIABLES_SH_CURRSMO,
						c.getString(c.getColumnIndex(CVD_VARIABLES_SH_CURRSMO)));
				map.put(CVD_VARIABLES_SH_AGEATSMO, c.getString(c
						.getColumnIndex(CVD_VARIABLES_SH_AGEATSMO)));
				map.put(CVD_VARIABLES_SH_QUITSMO,
						c.getString(c.getColumnIndex(CVD_VARIABLES_SH_QUITSMO)));
				map.put(CVD_VARIABLES_SH_CURR_CHEW, c.getString(c
						.getColumnIndex(CVD_VARIABLES_SH_CURR_CHEW)));
				map.put(CVD_VARIABLES_SH_AGEAT_CHEW, c.getString(c
						.getColumnIndex(CVD_VARIABLES_SH_AGEAT_CHEW)));

				map.put(CVD_VARIABLES_SH_QUIT_CHEW, c.getString(c
						.getColumnIndex(CVD_VARIABLES_SH_QUIT_CHEW)));

				// @treatment history

				map.put(CVD_VARIABLES_RH_Q1,
						c.getString(c.getColumnIndex(CVD_VARIABLES_RH_Q1)));
				map.put(CVD_VARIABLES_RH_Q2,
						c.getString(c.getColumnIndex(CVD_VARIABLES_RH_Q2)));
				map.put(CVD_VARIABLES_REFERALH_Q2_IF_YES, c.getString(c
						.getColumnIndex(CVD_VARIABLES_REFERALH_Q2_IF_YES)));
				map.put(CVD_VARIABLES_REFERALH_Q2_IF_NO_REASON,
						c.getString(c
								.getColumnIndex(CVD_VARIABLES_REFERALH_Q2_IF_NO_REASON)));
				map.put(CVD_VARIABLES_TH_BP,
						c.getString(c.getColumnIndex(CVD_VARIABLES_TH_BP)));
				map.put(CVD_VARIABLES_TH_LLTT,
						c.getString(c.getColumnIndex(CVD_VARIABLES_TH_LLTT)));
				map.put(CVD_VARIABLES_TH_APTT,
						c.getString(c.getColumnIndex(CVD_VARIABLES_TH_APTT)));

				map.put(CVD_VARIABLES_BP_PERDAY,
						c.getString(c.getColumnIndex(CVD_VARIABLES_BP_PERDAY)));
				map.put(CVD_VARIABLES_BP_PERWEEK,
						c.getString(c.getColumnIndex(CVD_VARIABLES_BP_PERWEEK)));
				map.put(CVD_VARIABLES_BP_PERLASTWEEK, c.getString(c
						.getColumnIndex(CVD_VARIABLES_BP_PERLASTWEEK)));
				map.put(CVD_VARIABLES_BP_PERYESTERDAY, c.getString(c
						.getColumnIndex(CVD_VARIABLES_BP_PERYESTERDAY)));
				map.put(CVD_VARIABLES_LP_PERDAY,
						c.getString(c.getColumnIndex(CVD_VARIABLES_LP_PERDAY)));
				map.put(CVD_VARIABLES_LP_PERWEEK,
						c.getString(c.getColumnIndex(CVD_VARIABLES_LP_PERWEEK)));
				map.put(CVD_VARIABLES_LP_PERLASTWEEK, c.getString(c
						.getColumnIndex(CVD_VARIABLES_LP_PERLASTWEEK)));
				map.put(CVD_VARIABLES_LP_PERYESTERDAY, c.getString(c
						.getColumnIndex(CVD_VARIABLES_LP_PERYESTERDAY)));

				map.put(CVD_VARIABLES_TT_PRES,
						c.getString(c.getColumnIndex(CVD_VARIABLES_TT_PRES)));

				map.put(CVD_VARIABLES_SMOKER,
						c.getString(c.getColumnIndex(CVD_VARIABLES_SMOKER)));

				map.put(CVD_VARIABLES_AT_PERDAY,
						c.getString(c.getColumnIndex(CVD_VARIABLES_AT_PERDAY)));
				map.put(CVD_VARIABLES_AT_PERWEEK,
						c.getString(c.getColumnIndex(CVD_VARIABLES_AT_PERWEEK)));
				map.put(CVD_VARIABLES_AT_PERLASTWEEK, c.getString(c
						.getColumnIndex(CVD_VARIABLES_AT_PERLASTWEEK)));
				map.put(CVD_VARIABLES_AT_PERYESTERDAY, c.getString(c
						.getColumnIndex(CVD_VARIABLES_AT_PERYESTERDAY)));

				map.put(CVD_VARIABLES_SBP1,
						c.getString(c.getColumnIndex(CVD_VARIABLES_SBP1)));
				map.put(CVD_VARIABLES_DBP1,
						c.getString(c.getColumnIndex(CVD_VARIABLES_DBP1)));
				map.put(CVD_VARIABLES_HR1,
						c.getString(c.getColumnIndex(CVD_VARIABLES_HR1)));
				map.put(CVD_VARIABLES_SBP2,
						c.getString(c.getColumnIndex(CVD_VARIABLES_SBP2)));
				map.put(CVD_VARIABLES_DBP2,
						c.getString(c.getColumnIndex(CVD_VARIABLES_DBP2)));
				map.put(CVD_VARIABLES_HR2,
						c.getString(c.getColumnIndex(CVD_VARIABLES_HR2)));
				map.put(CVD_VARIABLES_SBP3,
						c.getString(c.getColumnIndex(CVD_VARIABLES_SBP3)));
				map.put(CVD_VARIABLES_DBP3,
						c.getString(c.getColumnIndex(CVD_VARIABLES_DBP3)));
				map.put(CVD_VARIABLES_HR3,
						c.getString(c.getColumnIndex(CVD_VARIABLES_HR3)));

				map.put(CVD_VARIABLES_HR_AVG,
						c.getString(c.getColumnIndex(CVD_VARIABLES_HR_AVG)));

				map.put(CVD_VARIABLES_SBP_AVG,
						c.getString(c.getColumnIndex(CVD_VARIABLES_SBP_AVG)));

				map.put(CVD_VARIABLES_DBP_AVG,
						c.getString(c.getColumnIndex(CVD_VARIABLES_DBP_AVG)));

				map.put(CVD_VARIABLES_PULSE,
						c.getString(c.getColumnIndex(CVD_VARIABLES_PULSE)));

				map.put(CVD_VARIABLES_BPcuffSize,
						c.getString(c.getColumnIndex(CVD_VARIABLES_BPcuffSize)));
				map.put(CVD_VARIABLES_FASTING6HRS, c.getString(c
						.getColumnIndex(CVD_VARIABLES_FASTING6HRS)));

				map.put(CVD_VARIABLES_ACQUISITION_TIME, c.getString(c
						.getColumnIndex(CVD_VARIABLES_ACQUISITION_TIME)));

				map.put(CVD_VARIABLES_BG_EATorDRINK, c.getString(c
						.getColumnIndex(CVD_VARIABLES_BG_EATorDRINK)));

				map.put(CVD_VARIABLES_BG_MGDL,
						c.getString(c.getColumnIndex(CVD_VARIABLES_BG_MGDL)));
				map.put(CVD_VARIABLES_CHOL_RESULTDATE, c.getString(c
						.getColumnIndex(CVD_VARIABLES_CHOL_RESULTDATE)));

				map.put(CVD_VARIABLES_TC,
						c.getString(c.getColumnIndex(CVD_VARIABLES_TC)));
				map.put(CVD_VARIABLES_LDL,
						c.getString(c.getColumnIndex(CVD_VARIABLES_LDL)));
				map.put(CVD_VARIABLES_HDL,
						c.getString(c.getColumnIndex(CVD_VARIABLES_HDL)));
				map.put(CVD_VARIABLES_TG,
						c.getString(c.getColumnIndex(CVD_VARIABLES_TG)));
				map.put(CVD_VARIABLES_HT,
						c.getString(c.getColumnIndex(CVD_VARIABLES_HT)));

				map.put(CVD_VARIABLES_WT,
						c.getString(c.getColumnIndex(CVD_VARIABLES_WT)));

				try {
					map.put(CVD_VARIABLES_RISKSCORE, c.getString(c
							.getColumnIndex(CVD_VARIABLES_RISKSCORE)));

					map.put(CVD_VARIABLES_BP_DRUGLIST,

					c.getString(c.getColumnIndex(CVD_VARIABLES_BP_DRUGLIST)));

					map.put(CVD_VARIABLES_STATIN_DRUGLIST, c.getString(c
							.getColumnIndex(CVD_VARIABLES_STATIN_DRUGLIST)));
					map.put(CVD_VARIABLES_ASPIRIN_DRUGLIST, c.getString(c
							.getColumnIndex(CVD_VARIABLES_ASPIRIN_DRUGLIST)));

					map.put(CVD_VARIABLES_BP,
							c.getString(c.getColumnIndex(CVD_VARIABLES_BP)));
					map.put(CVD_VARIABLES_STATIN,
							c.getString(c.getColumnIndex(CVD_VARIABLES_STATIN)));
					map.put(CVD_VARIABLES_ASPIRIN, c.getString(c
							.getColumnIndex(CVD_VARIABLES_ASPIRIN)));
					map.put(CVD_VARIABLES_BP_REASON, c.getString(c
							.getColumnIndex(CVD_VARIABLES_BP_REASON)));
					map.put(CVD_VARIABLES_STATIN_REASON, c.getString(c
							.getColumnIndex(CVD_VARIABLES_STATIN_REASON)));

					map.put(CVD_VARIABLES_ASPIRIN_REASON, c.getString(c
							.getColumnIndex(CVD_VARIABLES_ASPIRIN_REASON)));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				map.put(CVD_VARIABLES_REFCODE,
						c.getString(c.getColumnIndex(CVD_VARIABLES_REFCODE)));

				map.put(CVD_VARIABLE_CURRENT_USER_LOGIN, c.getString(c
						.getColumnIndex(CVD_VARIABLE_CURRENT_USER_LOGIN)));
				map.put(CVD_VARIABLE_CURRENT_USER_ROLE, c.getString(c
						.getColumnIndex(CVD_VARIABLE_CURRENT_USER_ROLE)));

				map.put(CVD_VARIABLE_CREATED_DATE, c.getString(c
						.getColumnIndex(CVD_VARIABLE_CREATED_DATE)));

				map.put(CVD_VARIABLE_UPDATED_DATE, c.getString(c
						.getColumnIndex(CVD_VARIABLE_UPDATED_DATE)));

				map.put(CVD_VARIABLE_CURRENT_ENCOUNTER, c.getString(c
						.getColumnIndex(CVD_VARIABLE_CURRENT_ENCOUNTER)));

				map.put(CVD_VARIABLE_LAST_ENCOUNTER, c.getString(c
						.getColumnIndex(CVD_VARIABLE_LAST_ENCOUNTER)));

				map.put(CVD_VARIABLES_BP_DRUGLIST, c.getString(c
						.getColumnIndex(CVD_VARIABLES_BP_DRUGLIST)));

				map.put(CVD_VARIABLES_STATIN_DRUGLIST, c.getString(c
						.getColumnIndex(CVD_VARIABLES_STATIN_DRUGLIST)));

				map.put(CVD_VARIABLES_ASPIRIN_DRUGLIST, c.getString(c
						.getColumnIndex(CVD_VARIABLES_ASPIRIN_DRUGLIST)));

				map.put(CVD_VARIABLES_NEXT_VISIT_1MONTH, c.getString(c
						.getColumnIndex(CVD_VARIABLES_NEXT_VISIT_1MONTH)));

				map.put(CVD_VARIABLES_TT_ADHER,
						c.getString(c.getColumnIndex(CVD_VARIABLES_TT_ADHER)));

				map.put(CVD_VARIABLES_MED_RECEIVED, c.getString(c
						.getColumnIndex(CVD_VARIABLES_MED_RECEIVED)));

				map.put(CVD_VARIABLES_ASHA_ASSIGNED, c.getString(c
						.getColumnIndex(CVD_VARIABLES_ASHA_ASSIGNED)));

				map.put(CVD_VARIABLES_PHC_NAME,
						c.getString(c.getColumnIndex(CVD_VARIABLES_PHC_NAME)));

				map.put(CVD_VARIABLES_ENC_DATE,
						c.getString(c.getColumnIndex(CVD_VARIABLES_ENC_DATE)));

				map.put(CVD_VARIABLES_ENC_type,
						c.getString(c.getColumnIndex(CVD_VARIABLES_ENC_type)));

				// /////////////////////////////////

				map.put(CVD_VARIABLES_DIAB_CALC,
						c.getString(c.getColumnIndex(CVD_VARIABLES_DIAB_CALC)));

				map.put(CVD_VARIABLES_SMOKER_TL,
						c.getString(c.getColumnIndex(CVD_VARIABLES_SMOKER_TL)));

				/*
				 * map.put(CVD_VARIABLES_BG_FASTING_CALC, c.getString(c
				 * .getColumnIndex(CVD_VARIABLES_BG_FASTING_CALC)));
				 */
				map.put(CVD_VARIABLES_AR_RECOM,
						c.getString(c.getColumnIndex(CVD_VARIABLES_AR_RECOM)));

				map.put(CVD_VARIABLES_NV_AR,
						c.getString(c.getColumnIndex(CVD_VARIABLES_NV_AR)));

				map.put(CVD_VARIABLES_NV_AR_TL,
						c.getString(c.getColumnIndex(CVD_VARIABLES_NV_AR_TL)));

				map.put(CVD_VARIABLES_NV_DIAB,
						c.getString(c.getColumnIndex(CVD_VARIABLES_NV_DIAB)));

				map.put(CVD_VARIABLES_NV_DIAB_TL,
						c.getString(c.getColumnIndex(CVD_VARIABLES_NV_DIAB_TL)));

				map.put(CVD_VARIABLES_REF_DOC_TL,
						c.getString(c.getColumnIndex(CVD_VARIABLES_REF_DOC_TL)));

				map.put(CVD_VARIABLES_TARGET_SBP,
						c.getString(c.getColumnIndex(CVD_VARIABLES_TARGET_SBP)));

				map.put(CVD_VARIABLES_TARGET_SBP_TL, c.getString(c
						.getColumnIndex(CVD_VARIABLES_TARGET_SBP_TL)));

				map.put(CVD_VARIABLES_TARGET_DBP,
						c.getString(c.getColumnIndex(CVD_VARIABLES_TARGET_DBP)));

				map.put(CVD_VARIABLES_TARGET_DBP_TL, c.getString(c
						.getColumnIndex(CVD_VARIABLES_TARGET_DBP_TL)));

				map.put(CVD_VARIABLES_TARGET_DBP_TL, c.getString(c
						.getColumnIndex(CVD_VARIABLES_TARGET_DBP_TL)));

				map.put(CVD_VARIABLES_TARGET_DBP_TL, c.getString(c
						.getColumnIndex(CVD_VARIABLES_TARGET_DBP_TL)));

				map.put(CVD_VARIABLES_TARGET_DBP_TL, c.getString(c
						.getColumnIndex(CVD_VARIABLES_TARGET_DBP_TL)));

				map.put(CVD_VARIABLES_DOC_REF,
						c.getString(c.getColumnIndex(CVD_VARIABLES_DOC_REF)));

				map.put(CVD_VARIABLES_PH_CVD_CALC, c.getString(c
						.getColumnIndex(CVD_VARIABLES_PH_CVD_CALC)));

				map.put(CVD_VARIABLES_HIGH_RISK_CALC, c.getString(c
						.getColumnIndex(CVD_VARIABLES_HIGH_RISK_CALC)));

			} catch (Exception e) {
				// TODO: handle exception

				e.printStackTrace();
			}
		}
		closemainlineDatabase();

		return map;

	}

	public HashMap<String, String> getPatientDataFomTemp(long patient_id) {

		HashMap<String, String> patientMap = new HashMap<String, String>();

		String selectQuery = "SELECT  * FROM " + TABLE_MAINLINE_CACHE_HT
				+ "  WHERE  " + KEY_PATIENT_ID + "=" + patient_id;

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			patientMap.put(KEY_PATIENT_ID,
					c.getString(c.getColumnIndex(KEY_PATIENT_ID)));
			patientMap.put(CVD_VARIABLES_P_CNUMBER,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_CNUMBER)));
			patientMap.put(CVD_VARIABLES_P_FIRSTNAME,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_FIRSTNAME)));
			patientMap.put(CVD_VARIABLES_P_SURNAME,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_SURNAME)));

			patientMap.put(CVD_VARIABLES_P_DOB,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_DOB)));
			patientMap
					.put(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE,
							c.getString(c
									.getColumnIndex(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE)));
			patientMap.put(CVD_VARIABLES_P_AGE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_AGE)));

			patientMap.put(CVD_VARIABLES_P_CONTACTNUM,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_CONTACTNUM)));
			patientMap
					.put(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE,
							c.getString(c
									.getColumnIndex(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE)));

			patientMap.put(CVD_VARIABLES_P_ADDRESS,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_ADDRESS)));

			patientMap.put(CVD_VARIABLES_P_AGE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_AGE)));

		}
		closemainlineDatabase();

		return patientMap;

	}

	public boolean patientReferedToDoctorInLastVisit(long patient_id,
			String table_name) {

		String ref_doc_calc = "NA";

		String selectQuery = "SELECT  " + CVD_VARIABLES_DOC_REF + " FROM "
				+ table_name + "  WHERE  " + KEY_PATIENT_ID + "=" + patient_id
				+ " ORDER BY " + CVD_VARIABLE_UPDATED_DATE + " DESC LIMIT 1";

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			try {

				ref_doc_calc = c.getString(c
						.getColumnIndex(CVD_VARIABLES_DOC_REF));

			} catch (Exception e) {
				// TODO: handle exception

				e.printStackTrace();
			}
		}
		closemainlineDatabase();

		if (null != ref_doc_calc && !ref_doc_calc.isEmpty()) {

			if (ref_doc_calc.equalsIgnoreCase("1")) {

				return true;
			}
		}

		return false;

	}

	public boolean patientIsHighRiskInLastVisit(long patient_id,
			String table_name) {

		String high_risk_calc = "NA";

		String selectQuery = "SELECT  " + CVD_VARIABLES_HIGH_RISK_CALC
				+ " FROM " + table_name + "  WHERE  " + KEY_PATIENT_ID + "="
				+ patient_id + " ORDER BY " + CVD_VARIABLE_UPDATED_DATE
				+ " DESC LIMIT 1";

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			try {

				high_risk_calc = c.getString(c
						.getColumnIndex(CVD_VARIABLES_HIGH_RISK_CALC));

			} catch (Exception e) {
				// TODO: handle exception

				e.printStackTrace();
			}
		}
		closemainlineDatabase();

		if (null != high_risk_calc && !high_risk_calc.isEmpty()) {

			if (high_risk_calc.equalsIgnoreCase("1")) {

				return true;
			}
		}

		return false;

	}

	public HashMap<String, String> getPatientsHistory(long patient_id) {

		HashMap<String, String> patientMap = new HashMap<String, String>();

		String selectQuery = "SELECT  * FROM " + TABLE_MAINLINE_CACHE_HT
				+ "  WHERE  " + KEY_PATIENT_ID + "=" + patient_id;

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			patientMap.put(KEY_ID, c.getString(c.getColumnIndex(KEY_ID)));
			patientMap.put(KEY_PATIENT_ID,
					c.getString(c.getColumnIndex(KEY_PATIENT_ID)));
			patientMap.put(CVD_VARIABLES_PH_HRTATTACK,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_HRTATTACK)));
			patientMap.put(CVD_VARIABLES_PH_HRTATTACK_SINCE, c.getString(c
					.getColumnIndex(CVD_VARIABLES_PH_HRTATTACK_SINCE)));
			patientMap.put(CVD_VARIABLES_PH_STROKE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_STROKE)));
			patientMap.put(CVD_VARIABLES_PH_STROKE_SINCE, c.getString(c
					.getColumnIndex(CVD_VARIABLES_PH_STROKE_SINCE)));
			patientMap.put(CVD_VARIABLES_PH_PVD,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_PVD)));
			patientMap.put(CVD_VARIABLES_PH_PVD_SINCE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_PVD_SINCE)));
			patientMap.put(CVD_VARIABLES_PH_DM,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_DM)));
			patientMap.put(CVD_VARIABLES_PH_DM_SINCE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_DM_SINCE)));
			patientMap.put(CVD_VARIABLES_FH_HRATTACK,
					c.getString(c.getColumnIndex(CVD_VARIABLES_FH_HRATTACK)));
			patientMap.put(CVD_VARIABLES_FH_STROKE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_FH_STROKE)));
			patientMap.put(CVD_VARIABLES_FH_DM,
					c.getString(c.getColumnIndex(CVD_VARIABLES_FH_DM)));

			patientMap.put(CVD_VARIABLES_SH_SMOKING_QUESTION, c.getString(c
					.getColumnIndex(CVD_VARIABLES_SH_SMOKING_QUESTION)));
			patientMap.put(CVD_VARIABLES_SH_CURRSMO,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_CURRSMO)));
			patientMap.put(CVD_VARIABLES_SH_AGEATSMO,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_AGEATSMO)));
			patientMap.put(CVD_VARIABLES_SH_QUITSMO,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_QUITSMO)));
			patientMap.put(CVD_VARIABLES_SH_CURR_CHEW,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_CURR_CHEW)));
			patientMap.put(CVD_VARIABLES_SH_AGEAT_CHEW,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_AGEAT_CHEW)));

			patientMap.put(CVD_VARIABLES_SH_QUIT_CHEW,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_QUIT_CHEW)));

		}
		closemainlineDatabase();

		return patientMap;

	}

	public HashMap<String, String> getRiskHistory(long patient_id,
			String table_name) {

		HashMap<String, String> patientMap = new HashMap<String, String>();

		String selectQuery = "SELECT  * FROM " + table_name + "  WHERE  "
				+ KEY_PATIENT_ID + "='" + patient_id + "'";

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			patientMap.put(CVD_VARIABLES_RH_Q1,
					c.getString(c.getColumnIndex(CVD_VARIABLES_RH_Q1)));
			patientMap.put(CVD_VARIABLES_RH_Q2,
					c.getString(c.getColumnIndex(CVD_VARIABLES_RH_Q2)));
			patientMap.put(CVD_VARIABLES_REFERALH_Q2_IF_YES, c.getString(c
					.getColumnIndex(CVD_VARIABLES_REFERALH_Q2_IF_YES)));

			patientMap
					.put(CVD_VARIABLES_REFERALH_Q2_IF_OTHERS_REASON,
							c.getString(c
									.getColumnIndex(CVD_VARIABLES_REFERALH_Q2_IF_OTHERS_REASON)));

			patientMap
					.put(CVD_VARIABLES_REFERALH_Q2_IF_NO_REASON,
							c.getString(c
									.getColumnIndex(CVD_VARIABLES_REFERALH_Q2_IF_NO_REASON)));
			patientMap.put(CVD_VARIABLES_TH_BP,
					c.getString(c.getColumnIndex(CVD_VARIABLES_TH_BP)));

			patientMap.put(CVD_VARIABLES_TH_LLTT,
					c.getString(c.getColumnIndex(CVD_VARIABLES_TH_LLTT)));
			patientMap.put(CVD_VARIABLES_TH_APTT,
					c.getString(c.getColumnIndex(CVD_VARIABLES_TH_APTT)));

			patientMap.put(CVD_VARIABLES_BP_PERDAY,
					c.getString(c.getColumnIndex(CVD_VARIABLES_BP_PERDAY)));

			patientMap.put(CVD_VARIABLES_BP_PERWEEK,
					c.getString(c.getColumnIndex(CVD_VARIABLES_BP_PERWEEK)));

			patientMap
					.put(CVD_VARIABLES_BP_PERLASTWEEK, c.getString(c
							.getColumnIndex(CVD_VARIABLES_BP_PERLASTWEEK)));

			patientMap.put(CVD_VARIABLES_BP_PERYESTERDAY, c.getString(c
					.getColumnIndex(CVD_VARIABLES_BP_PERYESTERDAY)));

			patientMap.put(CVD_VARIABLES_LP_PERDAY,
					c.getString(c.getColumnIndex(CVD_VARIABLES_LP_PERDAY)));

			patientMap.put(CVD_VARIABLES_LP_PERWEEK,
					c.getString(c.getColumnIndex(CVD_VARIABLES_LP_PERWEEK)));

			patientMap
					.put(CVD_VARIABLES_LP_PERLASTWEEK, c.getString(c
							.getColumnIndex(CVD_VARIABLES_LP_PERLASTWEEK)));

			patientMap.put(CVD_VARIABLES_LP_PERYESTERDAY, c.getString(c
					.getColumnIndex(CVD_VARIABLES_LP_PERYESTERDAY)));

			patientMap.put(CVD_VARIABLES_AT_PERDAY,
					c.getString(c.getColumnIndex(CVD_VARIABLES_AT_PERDAY)));

			patientMap.put(CVD_VARIABLES_AT_PERWEEK,
					c.getString(c.getColumnIndex(CVD_VARIABLES_AT_PERWEEK)));

			patientMap
					.put(CVD_VARIABLES_AT_PERLASTWEEK, c.getString(c
							.getColumnIndex(CVD_VARIABLES_AT_PERLASTWEEK)));

			patientMap.put(CVD_VARIABLES_AT_PERYESTERDAY, c.getString(c
					.getColumnIndex(CVD_VARIABLES_AT_PERYESTERDAY)));

		}
		closemainlineDatabase();

		return patientMap;

	}

	public HashMap<String, String> getRefferalCard(long patient_id) {

		HashMap<String, String> patientMap = new HashMap<String, String>();

		String selectQuery = "SELECT  * FROM " + TABLE_MAINLINE_CACHE_HT
				+ "  WHERE  " + KEY_PATIENT_ID + "=" + patient_id;

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			patientMap.put(KEY_ID, c.getString(c.getColumnIndex(KEY_ID)));
			patientMap.put(KEY_PATIENT_ID,
					c.getString(c.getColumnIndex(KEY_PATIENT_ID)));

			patientMap.put(CVD_VARIABLES_P_FIRSTNAME,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_FIRSTNAME)));
			patientMap.put(CVD_VARIABLES_P_SURNAME,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_SURNAME)));
			patientMap.put(CVD_VARIABLES_P_GENDER,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_GENDER)));

			patientMap.put(CVD_VARIABLES_P_AGE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_AGE)));
			patientMap.put(CVD_VARIABLES_P_CONTACTNUM,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_CONTACTNUM)));

			patientMap.put(CVD_VARIABLES_P_VILLAGE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_VILLAGE)));

			patientMap.put(CVD_VARIABLES_SBP_AVG,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SBP_AVG)));

			patientMap.put(CVD_VARIABLES_DBP_AVG,
					c.getString(c.getColumnIndex(CVD_VARIABLES_DBP_AVG)));

		}
		closemainlineDatabase();
		return patientMap;

	}

	public HashMap<String, String> getPatientsHistoryMT(long patient_id,
			String table_name) {

		HashMap<String, String> patientMap = new HashMap<String, String>();

		String selectQuery = "SELECT  * FROM " + table_name + "  WHERE  "
				+ KEY_PATIENT_ID + "='" + patient_id + "'";

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			patientMap.put(CVD_VARIABLES_P_FIRSTNAME,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_FIRSTNAME)));

			patientMap.put(CVD_VARIABLES_P_SURNAME,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_SURNAME)));

			patientMap.put(CVD_VARIABLES_P_CONTACTNUM,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_CONTACTNUM)));

			patientMap
					.put(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE,
							c.getString(c
									.getColumnIndex(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE)));

			patientMap.put(CVD_VARIABLES_P_DOB,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_DOB)));

			patientMap.put(CVD_VARIABLES_P_GENDER,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_GENDER)));

			patientMap.put(CVD_VARIABLES_P_ADDRESS,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_ADDRESS)));

			patientMap.put(CVD_VARIABLES_P_AGE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_P_AGE)));

			patientMap
					.put(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE,
							c.getString(c
									.getColumnIndex(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE)));

			patientMap.put(KEY_ID, c.getString(c.getColumnIndex(KEY_ID)));
			patientMap.put(KEY_PATIENT_ID,
					c.getString(c.getColumnIndex(KEY_PATIENT_ID)));
			patientMap.put(CVD_VARIABLES_PH_HRTATTACK,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_HRTATTACK)));
			patientMap.put(CVD_VARIABLES_PH_STROKE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_STROKE)));

			patientMap.put(CVD_VARIABLES_PH_PVD,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_PVD)));

			patientMap.put(CVD_VARIABLES_PH_BP,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_BP)));

			patientMap.put(CVD_VARIABLES_PH_BP_SINCE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_BP_SINCE)));

			patientMap.put(CVD_VARIABLES_PH_PVD_SINCE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_PVD_SINCE)));

			patientMap.put(CVD_VARIABLES_PH_BP,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_BP)));

			patientMap.put(CVD_VARIABLES_PH_BP_MEDICATION, c.getString(c
					.getColumnIndex(CVD_VARIABLES_PH_BP_MEDICATION)));

			patientMap.put(CVD_VARIABLES_PH_DM,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_DM)));

			patientMap.put(CVD_VARIABLES_PH_HRTATTACK_SINCE, c.getString(c
					.getColumnIndex(CVD_VARIABLES_PH_HRTATTACK_SINCE)));

			patientMap.put(CVD_VARIABLES_PH_STROKE_SINCE, c.getString(c
					.getColumnIndex(CVD_VARIABLES_PH_STROKE_SINCE)));

			patientMap.put(CVD_VARIABLES_PH_DM_SINCE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_PH_DM_SINCE)));

			patientMap.put(CVD_VARIABLES_FH_HRATTACK,
					c.getString(c.getColumnIndex(CVD_VARIABLES_FH_HRATTACK)));
			patientMap.put(CVD_VARIABLES_FH_STROKE,
					c.getString(c.getColumnIndex(CVD_VARIABLES_FH_STROKE)));
			patientMap.put(CVD_VARIABLES_FH_DM,
					c.getString(c.getColumnIndex(CVD_VARIABLES_FH_DM)));

			patientMap.put(CVD_VARIABLES_TH_BP,
					c.getString(c.getColumnIndex(CVD_VARIABLES_TH_BP)));

			patientMap.put(CVD_VARIABLES_TH_LLTT,
					c.getString(c.getColumnIndex(CVD_VARIABLES_TH_LLTT)));
			patientMap.put(CVD_VARIABLES_TH_APTT,
					c.getString(c.getColumnIndex(CVD_VARIABLES_TH_APTT)));

			patientMap.put(CVD_VARIABLES_TH_DM,
					c.getString(c.getColumnIndex(CVD_VARIABLES_TH_DM)));

			patientMap.put(CVD_VARIABLES_SH_SMOKING_QUESTION, c.getString(c
					.getColumnIndex(CVD_VARIABLES_SH_SMOKING_QUESTION)));
			patientMap.put(CVD_VARIABLES_SH_CURRSMO,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_CURRSMO)));
			patientMap.put(CVD_VARIABLES_SH_AGEATSMO,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_AGEATSMO)));
			patientMap.put(CVD_VARIABLES_SH_QUITSMO,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_QUITSMO)));
			patientMap.put(CVD_VARIABLES_SH_CURR_CHEW,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_CURR_CHEW)));
			patientMap.put(CVD_VARIABLES_SH_AGEAT_CHEW,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_AGEAT_CHEW)));

			patientMap.put(CVD_VARIABLES_SH_QUIT_CHEW,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_QUIT_CHEW)));

		}
		closemainlineDatabase();

		return patientMap;

	}

	public HashMap<String, String> getPatientsRiskfactorML(long patient_id,
			String table_name) {

		HashMap<String, String> patientMap = new HashMap<String, String>();

		String selectQuery = "SELECT  * FROM " + table_name + "  WHERE  "
				+ KEY_PATIENT_ID + "='" + patient_id + "'";

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			patientMap.put(KEY_ID, c.getString(c.getColumnIndex(KEY_ID)));
			patientMap.put(KEY_PATIENT_ID,
					c.getString(c.getColumnIndex(KEY_PATIENT_ID)));
			patientMap.put(CVD_VARIABLES_SBP1,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SBP1)));
			patientMap.put(CVD_VARIABLES_DBP1,
					c.getString(c.getColumnIndex(CVD_VARIABLES_DBP1)));
			patientMap.put(CVD_VARIABLES_HR1,
					c.getString(c.getColumnIndex(CVD_VARIABLES_HR1)));
			patientMap.put(CVD_VARIABLES_SBP2,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SBP2)));
			patientMap.put(CVD_VARIABLES_DBP2,
					c.getString(c.getColumnIndex(CVD_VARIABLES_DBP2)));
			patientMap.put(CVD_VARIABLES_HR2,
					c.getString(c.getColumnIndex(CVD_VARIABLES_HR2)));
			patientMap.put(CVD_VARIABLES_SBP3,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SBP3)));
			patientMap.put(CVD_VARIABLES_DBP3,
					c.getString(c.getColumnIndex(CVD_VARIABLES_DBP3)));
			patientMap.put(CVD_VARIABLES_HR3,
					c.getString(c.getColumnIndex(CVD_VARIABLES_HR3)));

			patientMap.put(CVD_VARIABLES_BG_MGDL,
					c.getString(c.getColumnIndex(CVD_VARIABLES_BG_MGDL)));

			patientMap.put(CVD_VARIABLES_BG_EATorDRINK,
					c.getString(c.getColumnIndex(CVD_VARIABLES_BG_EATorDRINK)));

			patientMap.put(CVD_VARIABLES_FASTING6HRS,
					c.getString(c.getColumnIndex(CVD_VARIABLES_FASTING6HRS)));

			patientMap.put(CVD_VARIABLES_ACQUISITION_TIME, c.getString(c
					.getColumnIndex(CVD_VARIABLES_ACQUISITION_TIME)));

			patientMap.put(CVD_VARIABLES_BPcuffSize,
					c.getString(c.getColumnIndex(CVD_VARIABLES_BPcuffSize)));
			patientMap.put(CVD_VARIABLES_CHOL_RESULTDATE, c.getString(c
					.getColumnIndex(CVD_VARIABLES_CHOL_RESULTDATE)));

			patientMap.put(CVD_VARIABLES_TC,
					c.getString(c.getColumnIndex(CVD_VARIABLES_TC)));
			patientMap.put(CVD_VARIABLES_LDL,
					c.getString(c.getColumnIndex(CVD_VARIABLES_LDL)));
			patientMap.put(CVD_VARIABLES_HDL,
					c.getString(c.getColumnIndex(CVD_VARIABLES_HDL)));
			patientMap.put(CVD_VARIABLES_TG,
					c.getString(c.getColumnIndex(CVD_VARIABLES_TG)));
			patientMap.put(CVD_VARIABLES_HT,
					c.getString(c.getColumnIndex(CVD_VARIABLES_HT)));

			patientMap.put(CVD_VARIABLES_WT,
					c.getString(c.getColumnIndex(CVD_VARIABLES_WT)));

			patientMap.put(CVD_VARIABLES_BP,
					c.getString(c.getColumnIndex(CVD_VARIABLES_BP)));

			patientMap.put(CVD_VARIABLES_STATIN,
					c.getString(c.getColumnIndex(CVD_VARIABLES_STATIN)));

			patientMap.put(CVD_VARIABLES_ASPIRIN,
					c.getString(c.getColumnIndex(CVD_VARIABLES_ASPIRIN)));

			patientMap.put(CVD_VARIABLES_BP_DRUGLIST,
					c.getString(c.getColumnIndex(CVD_VARIABLES_BP_DRUGLIST)));

			patientMap.put(CVD_VARIABLES_STATIN_DRUGLIST, c.getString(c
					.getColumnIndex(CVD_VARIABLES_STATIN_DRUGLIST)));

			patientMap.put(CVD_VARIABLES_ASPIRIN_DRUGLIST, c.getString(c
					.getColumnIndex(CVD_VARIABLES_ASPIRIN_DRUGLIST)));

			patientMap.put(CVD_VARIABLES_BP_REASON,
					c.getString(c.getColumnIndex(CVD_VARIABLES_BP_REASON)));

			patientMap.put(CVD_VARIABLES_STATIN_REASON,
					c.getString(c.getColumnIndex(CVD_VARIABLES_STATIN_REASON)));

			patientMap
					.put(CVD_VARIABLES_ASPIRIN_REASON, c.getString(c
							.getColumnIndex(CVD_VARIABLES_ASPIRIN_REASON)));

			// getting smoking related values

			patientMap.put(CVD_VARIABLES_SH_SMOKING_QUESTION, c.getString(c
					.getColumnIndex(CVD_VARIABLES_SH_SMOKING_QUESTION)));
			patientMap.put(CVD_VARIABLES_SH_CURRSMO,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_CURRSMO)));
			patientMap.put(CVD_VARIABLES_SH_AGEATSMO,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_AGEATSMO)));
			patientMap.put(CVD_VARIABLES_SH_QUITSMO,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_QUITSMO)));
			patientMap.put(CVD_VARIABLES_SH_CURR_CHEW,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_CURR_CHEW)));
			patientMap.put(CVD_VARIABLES_SH_AGEAT_CHEW,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_AGEAT_CHEW)));

			patientMap.put(CVD_VARIABLES_SH_QUIT_CHEW,
					c.getString(c.getColumnIndex(CVD_VARIABLES_SH_QUIT_CHEW)));

			patientMap.put(CVD_VARIABLES_TH_BP,
					c.getString(c.getColumnIndex(CVD_VARIABLES_TH_BP)));

			patientMap.put(CVD_VARIABLES_TH_LLTT,
					c.getString(c.getColumnIndex(CVD_VARIABLES_TH_LLTT)));
			patientMap.put(CVD_VARIABLES_TH_APTT,
					c.getString(c.getColumnIndex(CVD_VARIABLES_TH_APTT)));

			// getting average bp values

			if (c.getString(c.getColumnIndex(CVD_VARIABLES_SBP_AVG)) != null) {
				patientMap.put(CVD_VARIABLES_SBP_AVG,
						c.getString(c.getColumnIndex(CVD_VARIABLES_SBP_AVG)));
			}

			if (c.getString(c.getColumnIndex(CVD_VARIABLES_DBP_AVG)) != null) {

				patientMap.put(CVD_VARIABLES_DBP_AVG,
						c.getString(c.getColumnIndex(CVD_VARIABLES_DBP_AVG)));
			}

			if (c.getString(c.getColumnIndex(CVD_VARIABLES_HR_AVG)) != null) {
				patientMap.put(CVD_VARIABLES_HR_AVG,
						c.getString(c.getColumnIndex(CVD_VARIABLES_HR_AVG)));

			}

		}
		closemainlineDatabase();

		return patientMap;

	}

	/* Helper class for RiskAssesment */

	private class RiskAssessmentDBHelper extends SQLiteOpenHelper {

		RiskAssessmentDBHelper(Context context) {

			super(context, DB_PATH + DB_NAME_RISK_ASSESSMENT, null,
					DB_VERSION_RISK_ASSESSMENT);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub

			// creating required tables
			db.execSQL(TABLE_PHC_CREATE);
			db.execSQL(TABLE_VILLAGE_CREATE);
			db.execSQL(TABLE_LOCALITY_CREATE);
			db.execSQL(TABLE_PATIENT_RISK_CREATE);
			db.execSQL(TABLE_USER_CREATE);
			db.execSQL(TABLE_MAINLINE_HT_CREATE);
			db.execSQL(TABLE_PRIORITY_LIST_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			// on upgrade drop older tables
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHC);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_VILLAGE);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCALITY);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAINLINE_CACHE_HT);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRIORITY_LIST);

			onCreate(db);
		}
	}

	public void addToUploadDataTable(String tableHousehold, long get_id) {
		// TODO Auto-generated method stub
		openmainlineWritableDatabase();
		Date cDate = new Date();

		String fDate_time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
				.format(cDate);

		// String fTime = new SimpleDateFormat("HH:mm:ss").format(cDate);

		ContentValues cv = new ContentValues();
		cv.put(KEY_UPLOAD_TABLE, tableHousehold);
		cv.put(KEY_UPLOAD_TABLE_ROW_ID, get_id);
		cv.put(KEY_IS_UPLOADED, 0);
		cv.put(KEY_DATE_MODIFIED, fDate_time);
		cv.put(KEY_DATE_UPLOAD, fDate_time);

		ml_db.insert(TABLE_UPLOAD_STATUS, null, cv);

		closemainlineDatabase();

	}

	/*public ArrayList<UploadDataModel> getModifiedRecords() {

		ArrayList<UploadDataModel> modifiedRecords = new ArrayList<UploadDataModel>();

		String selectQuery = "SELECT * FROM " + TABLE_UPLOAD_STATUS;

		openmainlineWritableDatabase();

		Cursor c = ml_db.rawQuery(selectQuery, null);
		if (c != null && c.getCount() > 0) {
			UploadDataModel record;

			c.moveToFirst();
			do {
				record = new UploadDataModel();

				record.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
				record.setUpload_table_name(c.getString(c
						.getColumnIndex(KEY_UPLOAD_TABLE)));
				record.setUpload_row_id(c.getInt(c
						.getColumnIndex(KEY_UPLOAD_TABLE_ROW_ID)));
				record.setModified_time(c.getString(c
						.getColumnIndex(KEY_DATE_MODIFIED)));
				record.setUpload_time(c.getString(c
						.getColumnIndex(KEY_DATE_UPLOAD)));
				record.setIs_uploaded(c.getInt(c
						.getColumnIndex(KEY_IS_UPLOADED)));
				modifiedRecords.add(record);

			} while (c.moveToNext());
		}

		closemainlineDatabase();

		return modifiedRecords;
	}
*/
	public void deleteRecordFromPendingRecords(long get_id) {
		// TODO Auto-generated method stub

		try {

			openmainlineWritableDatabase();
			ml_db.delete(TABLE_UPLOAD_STATUS, KEY_ID + " = ?",
					new String[] { String.valueOf(get_id) });

		} catch (SQLiteException e) {

			e.printStackTrace();
		} finally {

			closemainlineDatabase();
		}

	}

	public void insertEncounterData(JSONArray results, String patientId) {

		try {

			String uuid;

			openmainlineWritableDatabase();

			ContentValues initialValues;

			ml_db.delete(TABLE_ENCOUNTER, KEY_PATIENT_ID + " = ?",
					new String[] { String.valueOf(patientId) });

			for (int i = 0; i < results.length(); i++) {

				initialValues = new ContentValues();

				JSONObject jsonObj = results.getJSONObject(i);

				initialValues.put(KEY_PATIENT_ID, patientId);

				initialValues.put(KEY_UUID, jsonObj.getString("uuid"));

				uuid = jsonObj.getString("uuid");

				initialValues.put(KEY_ENCOUNTERDATE_TIME, utils.AppUtil
						.convertSqliteDateToDate(jsonObj
								.getString("encounterDatetime")));

				JSONObject encountertype = jsonObj
						.getJSONObject("encounterType");

				initialValues.put(KEY_ENCOUNTERTYPE,
						encountertype.getString("name"));

				initialValues.put(KEY_DATE_UPLOAD, getCurrentDateTime());

				initialValues.put(KEY_ENCOUNTER_STATUS, NO);

				ml_db.delete(TABLE_ENCOUNTER, KEY_UUID + " = ?",
						new String[] { String.valueOf(uuid) });

				ml_db.insert(TABLE_ENCOUNTER, null, initialValues);

			}

		} catch (SQLiteException e) {

			e.printStackTrace();
		} catch (JSONException e) {

			e.printStackTrace();

		} finally {

			closemainlineDatabase();
		}

	}

	public void updateEncounterData(ContentValues initialValues,
			long patientId, String uuid) {

		try {

			openmainlineWritableDatabase();

			// updating row
			ml_db.update(TABLE_ENCOUNTER, initialValues, KEY_PATIENT_ID
					+ " = ?" + " AND " + KEY_UUID + " = ?", new String[] {
					String.valueOf(patientId), uuid });

			// updateStatusinPatientTableforObs(String.valueOf(patientId));

		} catch (SQLiteException e) {

			e.printStackTrace();
		} finally {

			closemainlineDatabase();
		}

	}

	public void insertPatientList(List<Map<String, String>> patientList,
			int flag) {

		try {

			String patientIdentifier = "";

			insertPLScoresInPatientTable(patientList);

			openmainlineWritableDatabase();

			if (flag == 1) {

				ml_db.delete(TABLE_PATIENTIDENTIFIERS, null, null);
			}

			ContentValues initialValues = new ContentValues();

			for (Map<String, String> mapObject : patientList) {

				initialValues.put(KEY_PATIENTIDENTIFIER,
						mapObject.get(KEY_PATIENTIDENTIFIER));

				if (mapObject.containsKey(KEY_PL_SCORE)) {

					if (!mapObject.get(KEY_PL_SCORE).equals("")
							|| mapObject.get(KEY_PL_SCORE).length() > 0) {

						initialValues.put(KEY_PL_SCORE,
								mapObject.get(KEY_PL_SCORE));

					} else {

						initialValues.put(KEY_PL_SCORE, NO);
					}

				} else {

					initialValues.put(KEY_PL_SCORE, NO);
				}

				if (mapObject.containsKey(KEY_LAST_PL_SCORE)) {

					if (!mapObject.get(KEY_LAST_PL_SCORE).equals("")
							|| mapObject.get(KEY_LAST_PL_SCORE).length() > 0) {

						initialValues.put(KEY_LAST_PL_SCORE,
								mapObject.get(KEY_LAST_PL_SCORE));

					} else {

						initialValues.put(KEY_LAST_PL_SCORE, NO);
					}

				} else {

					initialValues.put(KEY_LAST_PL_SCORE, NO);
				}

				initialValues.put(KEY_UPLOAD_STATUS, NO);

				initialValues.put(KEY_OBS_UPDATE_STATUS, NO);

				patientIdentifier = mapObject.get(KEY_PATIENTIDENTIFIER);

				ml_db.delete(TABLE_PATIENTIDENTIFIERS, KEY_PATIENTIDENTIFIER
						+ " = ?",
						new String[] { String.valueOf(patientIdentifier) });

				ml_db.insert(TABLE_PATIENTIDENTIFIERS, null, initialValues);

			}

		} catch (SQLiteException e) {

			e.printStackTrace();
		} finally {

			closemainlineDatabase();
		}

	}

	private void insertPLScoresInPatientTable(
			List<Map<String, String>> patientList) {
		// TODO Auto-generated method stub

		try {

			ContentValues initialValues = new ContentValues();

			openRiskAssesmentWritableDatabase();

			if (patientList.size() > 0) {

				for (Map<String, String> mapObject : patientList) {

					if (!mapObject.isEmpty()) {

						if (mapObject.containsKey(KEY_PL_SCORE)) {

							if (!mapObject.get(KEY_PL_SCORE).equals("")
									|| mapObject.get(KEY_PL_SCORE).length() > 0) {

								initialValues.put(KEY_PL_SCORE,
										mapObject.get(KEY_PL_SCORE));

							} else {

								initialValues.put(KEY_PL_SCORE, NO);
							}

						} else {

							initialValues.put(KEY_PL_SCORE, NO);
						}

						if (mapObject.containsKey(KEY_LAST_PL_SCORE)) {

							if (!mapObject.get(KEY_LAST_PL_SCORE).equals("")
									|| mapObject.get(KEY_LAST_PL_SCORE)
											.length() > 0) {

								initialValues.put(KEY_LAST_PL_SCORE,
										mapObject.get(KEY_LAST_PL_SCORE));

							} else {

								initialValues.put(KEY_LAST_PL_SCORE, NO);
							}

						} else {

							initialValues.put(KEY_LAST_PL_SCORE, NO);
						}

						ra_db.update(TABLE_PATIENT, initialValues,
								KEY_PATIENT_ID + " = ?",
								new String[] { mapObject
										.get(KEY_PATIENTIDENTIFIER) });

					}

				}

			}
		} catch (SQLiteException k) {

			k.printStackTrace();
		} finally {

			closeRiskAssesmentDatabase();
		}

	}

	public String getPatientIdentifiers() {

		String patientId = "-1";

		try {

			String selectQuery = "SELECT " + KEY_PATIENTIDENTIFIER + "  FROM "
					+ TABLE_PATIENTIDENTIFIERS + " WHERE " + KEY_UPLOAD_STATUS
					+ "='" + NO + "'" + "AND " + KEY_OBS_UPDATE_STATUS + "='"
					+ NO + "'" + " ORDER BY " + KEY_ID + " ASC LIMIT 1";

			openmainlineWritableDatabase();

			Cursor c = ml_db.rawQuery(selectQuery, null);

			if (c != null && c.getCount() > 0) {

				c.moveToFirst();

				if (c.getString(0) != null) {

					patientId = c.getString(0);

				}
				c.close();

			}

		} catch (SQLiteException e) {

			e.printStackTrace();

		} finally {

			closemainlineDatabase();
		}

		return patientId;
	}

	public String getPatientIdentifiersforObs() {

		String patientId = null;

		try {

			String selectQuery = "SELECT " + KEY_PATIENTIDENTIFIER + "  FROM "
					+ TABLE_PATIENTIDENTIFIERS + " WHERE " + KEY_UPLOAD_STATUS
					+ "='" + YES + "'" + "AND " + KEY_OBS_UPDATE_STATUS + "='"
					+ NO + "'" + " ORDER BY " + KEY_ID + " ASC LIMIT 1";

			openmainlineWritableDatabase();

			Cursor c = ml_db.rawQuery(selectQuery, null);

			if (c != null && c.getCount() > 0) {

				c.moveToFirst();

				if (c.getString(0) != null) {

					patientId = c.getString(0);

				}
				c.close();

			}

		} catch (SQLiteException e) {

			e.printStackTrace();

		} finally {

			closemainlineDatabase();
		}

		return patientId;
	}

	public int countPatients() {

		int result = 0;

		try {

			openmainlineWritableDatabase();

			String selectQuery = "SELECT COUNT(*) FROM "
					+ TABLE_PATIENTIDENTIFIERS;

			Cursor c = ml_db.rawQuery(selectQuery, null);

			if (c != null) {

				c.moveToFirst();

				if (c.getInt(0) > 0) {
					result = c.getInt(0);

				}
				c.close();

			}

		} catch (SQLiteException e) {

			e.printStackTrace();

		} finally {

			closemainlineDatabase();
		}

		return result;
	}

	public String getLastEncounterUUID(String patient_Id) {

		String result = "-1";

		try {

			openmainlineWritableDatabase();

			String selectQuery = "SELECT " + KEY_UUID + " FROM  "
					+ TABLE_ENCOUNTER + " WHERE " + KEY_PATIENT_ID + "='"
					+ patient_Id + "'" + " AND " + KEY_ENCOUNTER_STATUS + "="
					+ NO + " ORDER BY " + KEY_ID + " ASC LIMIT 1";

			Cursor c = ml_db.rawQuery(selectQuery, null);

			if (c != null && c.getCount() > 0) {

				c.moveToFirst();

				if (c.getString(0) != null) {
					result = c.getString(0);

				}
				c.close();

			}

		} catch (SQLiteException e) {

			e.printStackTrace();
		} finally {

			closemainlineDatabase();
		}

		return result;

	}

	public void insertObservations(Map<String, String> obsMap, String patientId) {

		try {

			String currentEncounter = "";

			openmainlineWritableDatabase();

			// add stub to insert observations into database...

			ContentValues contentValues = new ContentValues();

			contentValues.put(KEY_PATIENT_ID, patientId);

			Set<String> obsSet = obsMap.keySet();

			for (String obsKey : obsSet) {

				/*
				 * if (!(obsKey.equalsIgnoreCase("doa year") ||
				 * obsKey.equalsIgnoreCase("doa day") ||
				 * obsKey.equalsIgnoreCase("doa month") ||
				 * obsKey.equalsIgnoreCase("smoker_diag") ||
				 * obsKey.equalsIgnoreCase("risk_score") ||
				 * obsKey.equalsIgnoreCase("cvd_risk") ||
				 * obsKey.equalsIgnoreCase("docref")))
				 */{

					contentValues.put(obsKey, obsMap.get(obsKey));

				}

				if (obsKey.equalsIgnoreCase(CVD_VARIABLE_CURRENT_ENCOUNTER)) {

					currentEncounter = obsMap
							.get(CVD_VARIABLE_CURRENT_ENCOUNTER);
				}

			}

			// getting values from patient table which were not came from
			// service

			Map<String, String> patientMap = getPatientData(Long
					.parseLong(patientId));

			contentValues.put(CVD_VARIABLES_P_FIRSTNAME,
					patientMap.get(KEY_PATIENT_FNAME));

			contentValues.put(CVD_VARIABLES_P_SURNAME,
					patientMap.get(KEY_PATIENT_LNAME));

			contentValues.put(KEY_GENDER, patientMap.get(KEY_GENDER));

			ml_db.delete(
					TABLE_MAINLINE_RISKASSES_HT,
					CVD_VARIABLE_CURRENT_ENCOUNTER + " = ?" + "AND "
							+ KEY_PATIENT_ID + " = ?",
					new String[] { String.valueOf(currentEncounter), patientId });
			if (obsMap.size() > 1) {

				ml_db.insert(TABLE_MAINLINE_RISKASSES_HT, null, contentValues);

			}

			closemainlineDatabase();

			String lastEncounterUUID = getLastEncounterUUID(patientId);

			updateEncounterTable(patientId, lastEncounterUUID);
			// updaing encounter in patient table

			updatepatientEncoutersInPatienttable(Long.parseLong(patientId));

			updateAsha(Long.parseLong(patientId), persistenceData.getUserName());

			// loading patient data from patient table for surname,
			// firstname,gender;

			// why this method calling here

			// getpatientdata(Integer.parseInt(patientId));

		} catch (Exception k) {
			k.printStackTrace();
		}

	}

	public void getpatientdata(long patient_id) {

		try {
			HashMap<String, String> map = getPatientData(patient_id);

			ContentValues values = new ContentValues();

			values.put(PATIENT_ID, map.get(KEY_PATIENT_ID));

			values.put(CVD_VARIABLES_P_CNUMBER,
					map.get(CVD_VARIABLES_P_CNUMBER));

			values.put(CVD_VARIABLES_P_FIRSTNAME, map.get(KEY_PATIENT_LNAME));

			values.put(CVD_VARIABLES_P_SURNAME, map.get(KEY_PATIENT_FNAME));

			values.put(CVD_VARIABLES_P_CONTACTNUM, map.get(KEY_CONTACT_NUMBER));

			values.put(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE,
					map.get(KEY_IS_CONTACT_NUMBER_SHARED));

			values.put(CVD_VARIABLES_P_DOB, map.get(CVD_VARIABLES_P_DOB));

			values.put(CVD_VARIABLES_P_GENDER, map.get(KEY_GENDER));

			values.put(CVD_VARIABLES_P_ADDRESS, map.get(KEY_ADDRESS));

			values.put(CVD_VARIABLES_P_AGE, map.get(KEY_AGE));

			values.put(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE,
					map.get(KEY_AGE_CHECKED));

			updatePatientdata(values, patient_id);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}
	}

	public void updatePatientdata(ContentValues initialValues, long id) {

		openmainlineWritableDatabase();

		// updating row
		ml_db.update(TABLE_MAINLINE_RISKASSES_HT, initialValues, KEY_PATIENT_ID
				+ " = ?", new String[] { String.valueOf(id) });

		closemainlineDatabase();
	}

	public void updateEncounterTable(String patientId, String lastuuid) {
		// TODO Auto-generated method stub

		ContentValues contentValues = new ContentValues();

		contentValues.put(KEY_ENCOUNTER_STATUS, YES);

		updateEncounterData(contentValues, Long.parseLong(patientId), lastuuid);

	}

	public void updateStatusinPatientTableforObs(String patientId) {
		// TODO Auto-generated method stub

		try {
			ContentValues initialValues = new ContentValues();

			initialValues.put(KEY_OBS_UPDATE_STATUS, YES);

			initialValues.put(KEY_UPLOAD_STATUS, YES);

			ml_db.update(TABLE_PATIENTIDENTIFIERS, initialValues,
					KEY_PATIENTIDENTIFIER + " = ?",
					new String[] { String.valueOf(patientId) });

		} catch (SQLiteException k) {

			k.printStackTrace();
		}

	}

	/*public void insertServerdataToDB(Encounter encounter) {

		try {

			openmainlineWritableDatabase();

			List<Obs> observations = encounter.getObs();

			ContentValues values = new ContentValues();

			values.put(CVD_VARIABLE_LAST_ENCOUNTER,
					encounter.getEncounterDatetime());

			for (Obs obser : observations) {

				String string = obser.getDisplay();
				String[] part = string.split(":");
				String part1 = part[0];
				String part2 = part[1];
				values.put(part1, part2);

			}
			ml_db.insert(TABLE_MAINLINE_CACHE_HT, null, values);

		} catch (SQLiteException k) {

			k.printStackTrace();
		} finally {

			closemainlineDatabase();

		}

	}*/

	private Map<String, String> getEncounterDetailsFromEncounterTable(
			String uuid) {

		Map<String, String> encounterDetails = null;

		try {

			String selectQuery = "SELECT " + KEY_ENCOUNTERDATE_TIME + ","
					+ KEY_ENCOUNTERTYPE + " FROM " + TABLE_ENCOUNTER
					+ " WHERE " + KEY_UUID + "='" + uuid + "'";

			Cursor mCursor = ml_db.rawQuery(selectQuery, null);

			if (mCursor != null && mCursor.getCount() > 0) {

				encounterDetails = new HashMap<String, String>();

				mCursor.moveToFirst();

				if (mCursor.getString(mCursor
						.getColumnIndex(KEY_ENCOUNTERDATE_TIME)) != null) {

					encounterDetails.put(KEY_ENCOUNTERDATE_TIME, mCursor
							.getString(mCursor
									.getColumnIndex(KEY_ENCOUNTERDATE_TIME)));

				}
				if (mCursor
						.getString(mCursor.getColumnIndex(KEY_ENCOUNTERTYPE)) != null) {

					encounterDetails.put(KEY_ENCOUNTERTYPE, mCursor
							.getString(mCursor
									.getColumnIndex(KEY_ENCOUNTERTYPE)));

				}

			}

		} catch (SQLiteException k) {

			k.printStackTrace();
		}

		return encounterDetails;
	}

	public boolean doesObsAlreadyExists(String patient_ID, String uuid) {

		boolean result = false;

		try {

			openmainlineWritableDatabase();

			Map<String, String> encounterDetailsMap = getEncounterDetailsFromEncounterTable(uuid);

			String selectQuery = "SELECT COUNT(*) FROM "
					+ TABLE_MAINLINE_RISKASSES_HT + " WHERE " + KEY_PATIENT_ID
					+ "='" + patient_ID + "' AND " + CVD_VARIABLE_CREATED_DATE
					+ "='" + encounterDetailsMap.get(KEY_ENCOUNTERDATE_TIME)
					+ "' AND " + CVD_VARIABLE_CURRENT_ENCOUNTER + "='"
					+ encounterDetailsMap.get(KEY_ENCOUNTERTYPE) + "'";

			Cursor mCursor = ml_db.rawQuery(selectQuery, null);

			if (mCursor != null) {

				mCursor.moveToFirst();

				if (mCursor.getInt(0) > 0) {

					result = true;

				}

				mCursor.close();
			}

		} catch (SQLiteException k) {

			k.printStackTrace();

		} finally {

			closemainlineDatabase();
		}
		return result;

	}

	public List<String> gettingAllEncounterUUIDs(String patientID) {

		List<String> uuids = new ArrayList<String>();

		try {

			openmainlineWritableDatabase();

			String selectQuery = "SELECT " + KEY_UUID + " FROM "
					+ TABLE_ENCOUNTER + " WHERE " + KEY_PATIENT_ID + "='"
					+ patientID + "'";

			Cursor cursor = ml_db.rawQuery(selectQuery, null);

			if (cursor != null && cursor.getCount() > 0) {

				cursor.moveToFirst();
				do {

					uuids.add(cursor.getString(cursor.getColumnIndex(KEY_UUID)));

				} while (cursor.moveToNext());

			}

		} catch (SQLiteException k) {

			k.printStackTrace();
		} finally {

			closemainlineDatabase();
		}

		return uuids;
	}

	public void insertRequestData(String requestData, String patientID) {

		try {

			openmainlineWritableDatabase();

			ContentValues contentValues = new ContentValues();

			contentValues.put(KEY_PATIENT_ID, patientID);

			contentValues.put(KEY_REQUEST_DATA, requestData);

			contentValues.put(CVD_VARIABLE_CREATED_DATE, getCurrentDateTime());

			contentValues.put(KEY_UPLOAD_STATUS, KEY_PENDING);

			contentValues.put(KEY_RETRY_COUNT, 0);

			contentValues.put(KEY_ATTEMPT, 0);

			contentValues.put(KEY_PROC_GUID, utils.AppUtil.getRandomNumber());

			ml_db.insert(TABLE_UPLOAD_PATIENT_STATUS, null, contentValues);

		} catch (SQLiteException k) {

			k.printStackTrace();

		} finally {

			closemainlineDatabase();
		}

	}

	/*public RequestModel getPendingRecords() {

		RequestModel requestModel = new RequestModel();

		HashMap<String, String> map = new HashMap<String, String>();

		String result = "-1";

		String selectQuery = "SELECT " + KEY_REQUEST_DATA + " FROM  "
				+ TABLE_UPLOAD_PATIENT_STATUS + " WHERE " + KEY_UPLOAD_STATUS
				+ "='" + KEY_PENDING + "'" + " ORDER BY " + KEY_ID
				+ " ASC LIMIT 1";

		try {

			openmainlineWritableDatabase();

			Cursor cursor = ml_db.rawQuery(selectQuery, null);

			if (cursor != null && cursor.getCount() > 0) {

				cursor.moveToFirst();

				if (cursor.getString(cursor.getColumnIndex(KEY_REQUEST_DATA)) != null) {

					result = cursor.getString(cursor
							.getColumnIndex(KEY_REQUEST_DATA));

					String[] str = result.split(",");

					for (int i = 0; i < str.length; i++) {

						String mapEntry = str[i];

						String[] key_value = mapEntry.split(":");

						map.put(key_value[0], key_value[1]);

					}

					requestModel.setRequestString(result);

				}

				System.out.println(map.toString());

			}

		} catch (SQLiteException k) {

			k.printStackTrace();
		} finally {

			closemainlineDatabase();
		}
		return requestModel;

	}*/

	public Map<String, String> getPatientProcedureRequest() {

		HashMap<String, String> map = new HashMap<String, String>();

		String selectQuery = "SELECT * FROM  " + TABLE_UPLOAD_PATIENT_STATUS
				+ " WHERE " + KEY_UPLOAD_STATUS + "='" + KEY_PENDING + "' AND "
				+ KEY_RETRY_COUNT + "<" + HTTPConstants.RETRY_COUNT
				+ " ORDER BY " + KEY_RETRY_COUNT + " ASC LIMIT 1";

		try {

			openmainlineWritableDatabase();

			Cursor cursor = ml_db.rawQuery(selectQuery, null);

			if (cursor != null && cursor.getCount() > 0) {

				cursor.moveToFirst();

				if (cursor.getString(cursor.getColumnIndex(KEY_PATIENT_ID)) != null) {

					map.put(KEY_PATIENT_ID, cursor.getString(cursor
							.getColumnIndex(KEY_PATIENT_ID)));

				}

				if (cursor.getString(cursor.getColumnIndex(KEY_REQUEST_DATA)) != null) {

					map.put(KEY_REQUEST_DATA, cursor.getString(cursor
							.getColumnIndex(KEY_REQUEST_DATA)));

				}

				if (cursor.getString(cursor.getColumnIndex(KEY_PROC_GUID)) != null) {

					map.put(KEY_PROC_GUID, cursor.getString(cursor
							.getColumnIndex(KEY_PROC_GUID)));

				}

				if (cursor.getString(cursor.getColumnIndex(KEY_RETRY_COUNT)) != null) {

					map.put(KEY_RETRY_COUNT, cursor.getString(cursor
							.getColumnIndex(KEY_RETRY_COUNT)));

				}

				if (cursor.getString(cursor.getColumnIndex(KEY_RETRY_COUNT)) != null) {

					map.put(KEY_ATTEMPT, cursor.getString(cursor
							.getColumnIndex(KEY_ATTEMPT)));

				}

				if (cursor.getString(cursor
						.getColumnIndex(CVD_VARIABLE_CREATED_DATE)) != null) {

					map.put(CVD_VARIABLE_CREATED_DATE, cursor.getString(cursor
							.getColumnIndex(CVD_VARIABLE_CREATED_DATE)));

				}

			} else {

				map.put(KEY_PATIENT_ID, "-1");
				map.put(KEY_REQUEST_DATA, "-1");
				map.put(KEY_PROC_GUID, "-1");
				map.put(KEY_RETRY_COUNT, "-1");
				map.put(CVD_VARIABLE_CREATED_DATE, "-1");
				map.put(KEY_ATTEMPT, "-1");
			}

		} catch (SQLiteException k) {

			k.printStackTrace();
		} finally {

			closemainlineDatabase();
		}
		return map;

	}

	public int deleteUploadedPatientRecord(String patientId) {

		int count = 0;

		try {

			openmainlineWritableDatabase();

			count = ml_db.delete(TABLE_UPLOAD_PATIENT_STATUS, KEY_PATIENT_ID
					+ "=" + patientId, null);

		} catch (SQLiteException k) {

			k.printStackTrace();
		} finally {

			closemainlineDatabase();
		}

		return count;
	}

	public void updateRetryCountToZero() {

		try {

			openmainlineWritableDatabase();

			String query = "UPDATE " + TABLE_UPLOAD_PATIENT_STATUS
					+ " SET retry_count = 0";

			ml_db.execSQL(query);

		} catch (SQLiteException k) {

			k.printStackTrace();

		} finally {

			closemainlineDatabase();
		}

	}

	public void updatePendingPatientRetryCount(String patient_id) {
		// TODO Auto-generated method stub

		try {

			openmainlineWritableDatabase();

			String query = "UPDATE " + TABLE_UPLOAD_PATIENT_STATUS
					+ " SET retry_count = retry_count+1,attempt = attempt+1"
					+ " WHERE " + KEY_PATIENT_ID + "='" + patient_id + "'";

			ml_db.execSQL(query);

		} catch (SQLiteException k) {

			k.printStackTrace();

		} finally {

			closemainlineDatabase();
		}

	}

	public String getTeluguName(String name_en) {

		openRiskAssesmentReadbleDatabase();

		String name_te = "";

		String selectQuery = "SELECT  " + KEY_NAME_TE + " FROM "
				+ TABLE_TELUGU_NAMES + "  WHERE  " + KEY_NAME_EN + "='"
				+ name_en + "'";

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();

			name_te = "(" + c.getString(c.getColumnIndex(KEY_NAME_TE)) + ")";

		}

		closeRiskAssesmentDatabase();

		return name_te;

	}

	public String getPatientName(String pid) {

		openRiskAssesmentReadbleDatabase();

		String name_fte = "", name_lte = "";

		String selectQuery = "SELECT  * FROM " + TABLE_PATIENT_TELUGU_NAMES
				+ "  WHERE  " + KEY_PATIENT_ID + "='" + pid + "'";

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();
			name_fte = /* "("+ */c.getString(c
					.getColumnIndex(KEY_PATIENT_FNAME_TE));
			name_lte = c.getString(c.getColumnIndex(KEY_PATIENT_LNAME_TE))/* +")" */;

		}

		closeRiskAssesmentDatabase();

		return name_fte + " " + name_lte;

		// return name_fte;

	}

	public String getPatientNameInTelugu(String pid,
			boolean isRequiredfirstNames) {

		openRiskAssesmentReadbleDatabase();

		String name_fte = "", name_lte = "";

		String selectQuery = "SELECT  * FROM " + TABLE_PATIENT_TELUGU_NAMES
				+ "  WHERE  " + KEY_PATIENT_ID + "='" + pid + "'";

		Cursor c = ra_db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0) {

			c.moveToFirst();
			name_fte = /* "("+ */c.getString(c
					.getColumnIndex(KEY_PATIENT_FNAME_TE));
			name_lte = c.getString(c.getColumnIndex(KEY_PATIENT_LNAME_TE))/* +")" */;

		}

		closeRiskAssesmentDatabase();

		if (isRequiredfirstNames) {

			return name_fte;

		} else {

			return name_lte;
		}
		// return name_fte;

	}

}
