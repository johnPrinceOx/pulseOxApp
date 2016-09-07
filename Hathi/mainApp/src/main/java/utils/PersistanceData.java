package utils;

import java.util.Map;

import db.DatabaseVariables;
import db.utils.UserModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PersistanceData extends DatabaseVariables {
	Context context;

	public PersistanceData(Context ctx) {

		context = ctx;
	}

	public Map<String, String> getTeluguFontMap(String vill_id) {

		return loadMapSavedPreferences(vill_id);
	}

	public void setTeluguFontMap(Map<String, String> telugufontmap,String vill_id) {

		saveMapPreferences(telugufontmap,vill_id);
	}
	
	public Map<String, String> getLocTeluguFontMap(String vill_id) {

		return loadLocMapSavedPreferences(vill_id);
	}

	public void setLocTeluguFontMap(Map<String, String> telugufontmap,String vill_id) {

		saveLocMapPreferences(telugufontmap,vill_id);
	}

	public String getLanguage() {

		return loadSavedPreferences(KEY_LANGUAGE);
	}

	public void setLanguage(String language) {

		savePreferences(KEY_LANGUAGE, language);
	}

	private static final String KEY_EMAIL = "email_id";

	private static final String KEY_PHONENUM = "phonenum";

	private static final String KEY_ISALARMSET = "alarm_status";

	public String getUsernameforUpload() {

		return loadSavedPreferences(KEY_USERNAME_UPLOAD);
	}

	public void setUsernameforUpload(String username) {

		savePreferences(KEY_USERNAME_UPLOAD, username);
	}

	public String getPasswordforUpload() {

		return loadSavedPreferences(KEY_PASSWORD_UPLOAD);
	}

	public void setPasswordforUpload(String password) {

		savePreferences(KEY_PASSWORD_UPLOAD, password);
	}

	public String getDeviceIMEI() {

		return loadSavedPreferences(KEY_DEVICE_IMEI);
	}

	public void setDeviceIMEI(String imei) {

		savePreferences(KEY_DEVICE_IMEI, imei);
	}

	public String getAlarmStatus() {

		return loadSavedPreferences(KEY_ISALARMSET);
	}

	public void setAlarmStatus(String alarmstatus) {

		savePreferences(KEY_ISALARMSET, alarmstatus);
	}

	public String getUpdatedTime() {

		return loadSavedPreferences(KEY_ALARM_UPDATED_TIME);
	}

	public void setUpdatedTime(String updatedtime) {

		savePreferences(KEY_ALARM_UPDATED_TIME, updatedtime);
	}

	public String getEmailid() {
		return loadSavedPreferences(KEY_EMAIL);
	}

	public void setEmailid(String emailid) {
		savePreferences(KEY_EMAIL, emailid);
	}

	public String getPhonenum() {
		return loadSavedPreferences(KEY_PHONENUM);
	}

	public void setPhonenum(String phonenum) {
		savePreferences(KEY_PHONENUM, phonenum);
	}

	public String getUserFName() {

		return loadSavedPreferences(KEY_FNAME);
	}

	public void setUserFName(String user_fname) {

		savePreferences(KEY_FNAME, user_fname);
	}

	public String getUserLName() {

		return loadSavedPreferences(KEY_LNAME);
	}

	public void setUserLName(String user_lname) {

		savePreferences(KEY_LNAME, user_lname);
	}

	public String getUserId() {

		return loadSavedPreferences(KEY_USERID);
	}

	public void setUserId(String userId) {

		savePreferences(KEY_USERID, userId);
	}

	public String getUserName() {
		return loadSavedPreferences(KEY_USERNAME);
	}

	public void setUserName(String userName) {
		savePreferences(KEY_USERNAME, userName);
	}

	public String getPassword() {
		return loadSavedPreferences(KEY_PASSWORD);
	}

	public void setPassword(String password) {
		savePreferences(KEY_PASSWORD, password);
	}

	public String getRole() {
		return loadSavedPreferences(KEY_ROLE);
	}

	public void setRole(String role) {
		savePreferences(KEY_ROLE, role);
	}

	public String getVillage() {
		return loadSavedPreferences(KEY_VILLAGE);
	}

	public void setVillage(String village) {
		savePreferences(KEY_VILLAGE, village);
	}

	public String getPhc() {
		return loadSavedPreferences(KEY_PHC_NAME);
	}

	public void setPhc(String phc) {
		savePreferences(KEY_PHC_NAME, phc);
	}

	public void setQuickreviewFlag(String quickreviewflag) {

		savePreferences(KEY_QUICK_REVIEW_FLAG, quickreviewflag);

	}

	public String getQuickreviewFlag() {

		return loadSavedPreferences(KEY_QUICK_REVIEW_FLAG);
	}

	public void setAppFirstInstallFlag(String app_first_install) {

		savePreferences(KEY_APP_FIRST_INSTAL_FLAG, app_first_install);

	}

	public String getAppFirstInstallFlag() {

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String firstTime = sharedPreferences.getString(
				KEY_APP_FIRST_INSTAL_FLAG, "true");

		if (firstTime.equalsIgnoreCase("true")) {
			savePreferences(KEY_APP_FIRST_INSTAL_FLAG, "false");
		}

		return firstTime;

	}
	
	private void saveLocMapPreferences(Map<String, String> map, String vill_id) {

		SharedPreferences pref = context.getSharedPreferences("vill_loc"+vill_id,
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		for (String s : map.keySet()) {

			editor.putString(s, map.get(s));
		}
		editor.commit();

	}

	private Map<String, String> loadLocMapSavedPreferences(String vill_id) {

		SharedPreferences pref = context.getSharedPreferences("vill_loc"+vill_id,
				Context.MODE_PRIVATE);

		Map<String, String> map = (Map<String, String>) pref.getAll();

		return map;

	}

	private void saveMapPreferences(Map<String, String> map, String vill_id) {

		SharedPreferences pref = context.getSharedPreferences("vill_"+vill_id,
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		for (String s : map.keySet()) {

			editor.putString(s, map.get(s));
		}
		editor.commit();

	}

	private Map<String, String> loadMapSavedPreferences(String vill_id) {

		SharedPreferences pref = context.getSharedPreferences("vill_"+vill_id,
				Context.MODE_PRIVATE);

		Map<String, String> map = (Map<String, String>) pref.getAll();

		return map;

	}

	private void savePreferences(String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private String loadSavedPreferences(String key) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String value = sharedPreferences.getString(key, "");
		return value;
	}

	public void clearPreferenceData() {
		setUserFName("");
		setUserLName("");
		setUserId("");
		setUserName("");
		setPassword("");
		setRole("");
		setVillage("");
		setPhc("");
		setAlarmStatus("");
	}

	public void setUserData(UserModel usermodel) {

		setUserFName(usermodel.getFname());

	}

	public void clearSettings() {
		setEmailid("");
		setPhonenum("");
	}
}
