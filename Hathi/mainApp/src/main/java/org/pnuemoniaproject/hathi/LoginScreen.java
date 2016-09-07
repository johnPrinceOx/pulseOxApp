package org.pnuemoniaproject.hathi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import db.UserList;
import eula.Eula;
import db.CVDBase;
import db.DatabaseConnecter;
import db.DatabaseVariables;
import utils.PersistanceData;
import db.utils.UserModel;
//import org.ibme.gi.mainRCT_healthtrackercvd.logger.CVDappLogger;
//import org.ibme.gi.mainRCT_healthtrackercvd.logger.CVDappLogger.EventType;
//import org.ibme.gi.mainRCT_healthtrackercvd.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import db.CVDVariables;

public class LoginScreen extends CVDVariables implements OnClickListener {

	// private PopupWindow pw;
	private Button tv_asha = null;
	private Button tv_doctor = null;

	private Button button_go = null;

	private LinearLayout linearLay = null;

	private Button lang_setting;

	private EditText pass = null;
	private EditText uname = null;
	private boolean result = false;
	private AlertDialog.Builder alertDialogBuilder;
	private AlertDialog alertDialog;
	private TextView welcomeUser;

	DatabaseConnecter dbconnector;

	PersistanceData persistancedata;

	String option_selected;

	String username, password, village, phc, role, userfname, userlname;

	Configuration config = new Configuration();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.login_page);

		initializeViews();
		dbconnector = new DatabaseConnecter(this);

		initializeButtonListeners();

		persistancedata = new PersistanceData(getApplicationContext());

		Eula.show(LoginScreen.this);
		prefs = getSharedPreferences(COMMON, Context.MODE_PRIVATE);
		/*appLogger = new CVDappLogger(CVDVariables.getAppLoggerFileName(),
				LoginScreen.this, prefs);
		*//* add event to logger *//*
		appLogger.LogEvent(EventType.ACTIVITY_STARTED, "LoginScreen", "");
*/
	}

	private void initializeButtonListeners() {

		button_go.setOnClickListener(this);
		/*
		 * tv_asha.setOnClickListener(this); tv_doctor.setOnClickListener(this);
		 * <<<<<<< .mine
		 */

		lang_setting.setOnClickListener(this);

	}

	private void initializeViews() {

		/*
		 * tv_asha = (Button) findViewById(R.id.tvAsha); tv_doctor = (Button)
		 * findViewById(R.id.tvDoctor);
		 */

		lang_setting = (Button) findViewById(R.id.lang_setting);

		button_go = (Button) findViewById(R.id.delgo_button);

		/*
		 * welcomeUser = (TextView) findViewById(R.id.welcome_user_display);
		 */
		uname = (EditText) findViewById(R.id.del_user);
		pass = (EditText) findViewById(R.id.del_pass);

		/* tv_doctor.setSelected(true); */

	}

	@Override
	public void onClick(View v) {

		int id = v.getId();

		if (id == R.id.lang_setting) {

			setLanguage();
		}

		/*
		 * if (id == R.id.tvAsha) {
		 * 
		 * tv_asha.setSelected(true); tv_doctor.setSelected(false);
		 * welcomeUser.setText(getResources().getString(R.string.asha));
		 * appLogger.LogEvent(EventType.BUTTON_PRESSED, "ASHA_BUTTON_PRESSED",
		 * ""); }
		 * 
		 * if (id == R.id.tvDoctor) {
		 * 
		 * tv_doctor.setSelected(true); tv_asha.setSelected(false);
		 * welcomeUser.setText(getResources().getString(R.string.doctor));
		 * appLogger.LogEvent(EventType.BUTTON_PRESSED, "DOCTOR_BUTTON_PRESSED",
		 * ""); }
		 */
		if (id == R.id.delgo_button) {

			//pasted from previous version
			UserList u = new UserList(LoginScreen.this, uname.getText()
					.toString(), pass.getText().toString());
			result = u.checkForUserAuth();
			Log.i(Constants.TAG, "isAuthenticated: " + String.valueOf(result));

			if (result) {
				Intent intent;
				//Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
				//Intent intent1 = new Intent(LoginScreen.this, HomeScreen.class);
				//Intent intent2 = new Intent(LoginScreen.this, HomeScreen.class);


				commitToSharedPrefs(u.getuType(), uname.getText().toString(), u.getuID(), u.getUnderPhc());
				if(u.getuType().toString().equalsIgnoreCase(Constants.USER_TYPE_COMMUNITYSTREAM))
				{
					intent = new Intent(LoginScreen.this, firstMenu.class);//HomeScreen.class);
				}
				else
				{
					intent = new Intent(LoginScreen.this, firstMenu.class);//HomeScreen.class);
				}
				Bundle bundle = new Bundle();
				bundle.putString("user", u.getuType());
				bundle.putString("name", uname.getText().toString());
				bundle.putString("id", u.getuID());
				bundle.putString("phc", u.getUnderPhc());
				bundle.putString("village", u.getuLocality());
				bundle.putLong("startTime", System.currentTimeMillis());
				intent.putExtras(bundle);
				Log.i(Constants.TAG, "user type: " + u.getuType());
				//insertAuthDetailstoDB(uname.getText().toString(), u.getuType(),u.getuLocality(), u.getuID(), u.getPass());
			//	appLogger.LogEvent(EventType.BUTTON_PRESSED, "GO_BUTTON_PRESSED", "Authentication Successful");
				startActivity(intent);
			//	appLogger.LogEvent(EventType.ACTIVITY_ENDED, "LoginScreen", "");
			//	appLogger.flush();
				finish();
			} else {
				showTryAgainDialog();
			//	appLogger.LogEvent(EventType.BUTTON_PRESSED, "GO_BUTTON_PRESSED", "Authentication ERROR");
			}

		}
			//Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
			//finish();

			//this gets info from database, instead get it from java class
			/*boolean doesRecordexist = dbconnector.userAuth(
					DatabaseVariables.TABLE_USER, uname.getText().toString(),
					pass.getText().toString());
*/
			// ////////////////////////////////////////////////////////////////////////////////////////

			/*if (doesRecordexist) {

				UserModel us = dbconnector.getUserData(uname.getText()
						.toString(), pass.getText().toString());

				usermodel = new UserModel();

				usermodel.setVillage(us.getVillage());

				usermodel.setPhc(us.getPhc());
				usermodel.setRole(us.getRole());

				username = uname.getText().toString();
				password = pass.getText().toString();
				village = us.getVillage();
				phc = us.getPhc();
				role = us.getRole();

				persistancedata.setUserFName(us.getFname());
				persistancedata.setUserLName(us.getLname());

				persistancedata.setUserName(username);
				persistancedata.setPassword(password);
				persistancedata.setVillage(village);

				persistancedata.setPhc(usermodel.getPhc());
				// persistancedata.setVillage(usermodel.getVillage());
				persistancedata.setRole(role);

				Intent intent = new Intent(LoginScreen.this, HomeScreen.class);

				UserModel user = new UserModel();

				user.setUsername(uname.getText().toString());
				user.setPassword(pass.getText().toString());

				dbconnector
						.deleteTableRecordsByTableName(DatabaseVariables.TABLE_MAINLINE_CACHE_HT);
				startActivity(intent);

				*//*appLogger.LogEvent(EventType.BUTTON_PRESSED,
						"GO_BUTTON_PRESSED", "Authentication Successful");

				appLogger.LogEvent(EventType.ACTIVITY_ENDED, "LoginScreen", "");
				appLogger.flush();*//*
				finish();
			} else {
				showTryAgainDialog();
				*//*appLogger.LogEvent(EventType.BUTTON_PRESSED,
						"GO_BUTTON_PRESSED", "Authentication ERROR");*//*
			}*/

		}


	private void setLanguage() {
		// TODO Auto-generated method stub

		final Dialog lsetting = new Dialog(this);
		lsetting.setContentView(R.layout.lang_settings);
		/* set the title */
		lsetting.setTitle(getResources().getString(R.string.lang_setting));

		final RadioGroup langset = (RadioGroup) lsetting
				.findViewById(R.id.aradiolang);

		if (persistancedata.getLanguage() != null) {

			if (persistancedata.getLanguage().equalsIgnoreCase("telugu")) {

				langset.check(R.id.telugu);

			} else {

				langset.check(R.id.english);
			}
		} else {

			langset.check(R.id.english);
		}

		lsetting.show();

		langset.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				if (checkedId == R.id.english) {

					persistancedata.setLanguage("english");

					config.locale = Locale.ENGLISH;

					Locale myLocale;

					myLocale = new Locale("en");
					
					Locale.setDefault(myLocale);

					lsetting.dismiss();

					getResources().updateConfiguration(config, null);

					restartActivity();
				}

				else if (checkedId == R.id.telugu) {

					Locale myLocale;

					persistancedata.setLanguage("telugu");

					myLocale = new Locale("te");
					Locale.setDefault(myLocale);
					config.locale = myLocale;
					lsetting.dismiss();
					getResources().updateConfiguration(config, null);

					restartActivity();

					onConfigurationChanged(config);

				}
			}
		});

	}

	protected void restartActivity() {

		Intent intent = new Intent(LoginScreen.this, LoginScreen.class);
		startActivity(intent);
		finish();
	}

	protected void commitToSharedPrefs(String UserType, String UserName,
			String UserID, String UserPHC) {
		prefs = getSharedPreferences(COMMON, Context.MODE_WORLD_WRITEABLE);
		editorShared = prefs.edit();
		editorShared.clear();
		editorShared.putString(COMMON_USER_TYPE, UserType);
		editorShared.putString(COMMON_USER_NAME, UserName);
		editorShared.putString(COMMON_USER_ID, UserID);
		editorShared.putString(COMMON_USER_PHC, UserPHC);
		editorShared.commit();
	}

	protected void insertAuthDetailstoDB(String user, String type,
			String village, String uid, String pass) {
		// if not present
		// boolean uPresent= isUserIDPresent();
		// test - insert some values onto the CREDENTIALS table
		Date cDate = new Date();
		String fDateNtime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS")
				.format(cDate);
		ContentValues values = new ContentValues();
		values.put(CVDBase.USER, user);
		values.put(CVDBase.TYPE, type);
		values.put(CVDBase.USER_ID, uid);
		values.put(CVDBase.VILLAGE, village);
		values.put(CVDBase.USER_PASS, pass);
		values.put(CVDBase.USER_LOG_IN_TIME, fDateNtime);
		getContentResolver().insert(CVDBase.CONTENT_URI_CREDENTIALS, values);
		Log.i(Constants.TAG, "Credential storage in DB successful");

	}

	protected void showTryAgainDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle(getResources().getString(
				R.string.auth_error));

		// set dialog message
		alertDialogBuilder
				.setMessage(
						getResources().getString(R.string.user_info_incorrect))
				.setCancelable(false)
				.setPositiveButton(getResources().getString(R.string.string_ok),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity
								// MainActivity.this.finish();
							}
						});
		// .setNegativeButton("No",new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog,int id) {
		// // if this button is clicked, just close
		// // the dialog box and do nothing
		// dialog.cancel();
		// }
		// });
		//
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// refresh your views here
		super.onConfigurationChanged(newConfig);
	}

	/*
	 * private void restartActivity() { Intent intent = getIntent(); finish();
	 * startActivity(intent); }
	 */

}
