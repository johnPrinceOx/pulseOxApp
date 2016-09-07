package org.pnuemoniaproject.hathi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import db.CVDVariables;
import db.DatabaseConnecter;
import utils.AppUtil;
import utils.PersistanceData;
import widgets.MyActionBar;

//import org.ibme.gi.mainRCT_healthtrackercvd.logger.CVDappLogger;
//import org.ibme.gi.mainRCT_healthtrackercvd.logger.CVDappLogger.EventType;
//import org.ibme.gi.receiver.UpdateEncounterDataReceiver;

public class CommunityStream1 extends CVDVariables implements OnClickListener {

	Button newMeasurement, followUp;
	private Button backStepButton, nextStepButton;
	private boolean wantoExit;
	private String ashaOrdoc, username, userID, userPHC;
	PersistanceData persistancedata;

	DatabaseConnecter dbConnector = null;

	private TextView user, userrol;

	Button refreshButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		//persistancedata = new PersistanceData(getApplicationContext());

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.community_stream1);
		// PushLink.start(this, R.drawable.alert, Constants.PUSHLINK_API_KEY);
		this.prefs = getSharedPreferences(COMMON, Context.MODE_PRIVATE);
		//appLogger = new CVDappLogger(CVDVariables.getAppLoggerFileName(),HomeScreen.this, prefs);

		/* add event to logger */
		//appLogger.LogEvent(EventType.ACTIVITY_STARTED, "HomeScreen", "");
		initializeActionBar();
		initializeViews();
		initializeNavBarandFooter();
		initializeButtonListeners();

//		checkingAuthentication();

		dbConnector = new DatabaseConnecter(this);

		// Set repeating of alarm every one hour i.e 60*60*1000

/*
		if (persistancedata.getAlarmStatus().isEmpty()
				|| persistancedata.getAlarmStatus().equalsIgnoreCase("0")) {

			setRepeatingAlarm(Constants.ALARM_REPEAT_INTERVAL);
		}
*/

		/*if (persistancedata != null
				&& persistancedata.getLanguage().equalsIgnoreCase("telugu")) {

			long villageid = dbConnector.getVillageid(persistancedata
					.getVillage());

			if (persistancedata != null
					&& persistancedata.getTeluguFontMap(String
							.valueOf(villageid)) != null
					&& persistancedata.getTeluguFontMap(
							String.valueOf(villageid)).isEmpty()) {

				new TelugufontAsyntask().execute();

			} else {

				DatabaseConnecter.telugunames = persistancedata
						.getTeluguFontMap(String.valueOf(villageid));

				DatabaseConnecter.loctelugunames = persistancedata
						.getLocTeluguFontMap(String.valueOf(villageid));

			}

		}*/

	}

	private void initializeNavBarandFooter() {

		backStepButton = (Button) findViewById(R.id.btn_back_step);
		nextStepButton = (Button) findViewById(R.id.btn_next_step);

		backStepButton.setVisibility(View.VISIBLE);
		nextStepButton.setVisibility(View.INVISIBLE);

		nextStepButton.setEnabled(false);

	}

	protected class TelugufontAsyntask extends AsyncTask<Void, Void, Boolean> {

		private ProgressDialog dialog = new ProgressDialog(CommunityStream1.this);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			dialog.setMessage(CommunityStream1.this.getResources().getString(
					R.string.search_querying));
			dialog.setCancelable(false);
			dialog.show();

		}

		@Override
		protected Boolean doInBackground(Void... arg0) {

			loadTeluguFont();

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub

			try {

				dialog.dismiss();

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}

	private void loadTeluguFont() {
		// TODO Auto-generated method stub

		long villageid = dbConnector.getVillageid(persistancedata.getVillage());

		dbConnector.loadTeluguFontFromDB(villageid);

	}

	/*private void setRepeatingAlarm(int repeatValue) {

		ComponentName receiver = new ComponentName(getApplicationContext(),
				UpdateEncounterDataReceiver.class);

		PackageManager pm = getApplicationContext().getPackageManager();

		pm.setComponentEnabledSetting(receiver,

		PackageManager.COMPONENT_ENABLED_STATE_ENABLED,

		PackageManager.DONT_KILL_APP);

		AlarmManager am = (AlarmManager) getApplicationContext()
				.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(getApplicationContext(),
				UpdateEncounterDataReceiver.class);

		intent.setAction("org.ibme.gi.baseline_healthtracker.START_SYNC_ALL");

		PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(),
				0, intent, 0);

		String[] time;

		String time1 = "06:00";

		if (persistancedata.getUpdatedTime().equals("")) {

			time = time1.split(":");

		} else {

			time = persistancedata.getUpdatedTime().split(":");
		}

		Calendar calendar = Calendar.getInstance();

		calendar.setTimeInMillis(System.currentTimeMillis());

		// calendar.set(Calendar.DAY_OF_MONTH,
		// calendar.get(Calendar.DAY_OF_MONTH)+1);

		// calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
		// calendar.set(Calendar.MINUTE,Integer.parseInt(time[1]));

		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				repeatValue, pi);

		persistancedata.setAlarmStatus("1");

	}*/

	static Date toNearestWholeHour(Date d) {
		Calendar c = new GregorianCalendar();

		c.setTime(d);

		if (c.get(Calendar.MINUTE) >= 30)
			c.add(Calendar.HOUR, 1);

		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		return c.getTime();
	}

	/*private void checkingAuthentication() {
		// TODO Auto-generated method stub

		String role = getString(R.string.doctor_name);
		if (persistancedata.getRole().equalsIgnoreCase(role)) {

			newPatientButton.setText(getString(R.string.practice_session));
		} else if (persistancedata.getRole().equalsIgnoreCase("admin")) {

			newPatientButton.setVisibility(View.GONE);
			reviewPatientButton.setVisibility(View.GONE);
			manageUsers.setVisibility(View.VISIBLE);
		}

	}*/

	public void initializeActionBar() {

		MyActionBar header = (MyActionBar) findViewById(R.id.action_bar);
		header.initHeader(CommunityStream1.this);

		refreshButton = (Button) findViewById(R.id.refreshButton);

		/*if (persistancedata.getRole().equalsIgnoreCase(
				getString(R.string.asha_name))
				|| persistancedata.getRole().equalsIgnoreCase(
						getString(R.string.doctor_name))) {

			refreshButton.setVisibility(View.VISIBLE);
		} else {

			refreshButton.setVisibility(View.GONE);
		}*/

		refreshButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (AppUtil.isInternetAvailable(CommunityStream1.this)) {

					// refreshButton.startAnimation(animFadein);

					//doUpload();
					showAlert(getString(R.string.canUpload_internet_msg));

				} else {

					showAlert(getString(R.string.enable_internet_msg));
				}

			}
		});

	}

	/*protected void doUpload() {
		// TODO Auto-generated method stub

		Intent uploadIntent = new Intent();

		uploadIntent
				.setAction("org.ibme.gi.baseline_healthtracker.START_SYNC_ALL");
		sendBroadcast(uploadIntent);

	}*/

	public void initializeViews() {
	//	plistingButton = (Button) findViewById(R.id.button_plisting);
		user = (TextView) findViewById(R.id.username);

		userrol = (TextView) findViewById(R.id.userrole);

		/*user.setText(" " + persistancedata.getUserFName() + " "
				+ persistancedata.getUserLName());
		userrol.setText("" + persistancedata.getRole());
		if (persistancedata.getRole().equalsIgnoreCase(
				getString(R.string.doctor_name))
				|| persistancedata.getRole().equalsIgnoreCase("staffnurse")
				|| persistancedata.getRole().equalsIgnoreCase("admin")) {

			plistingButton.setVisibility(View.GONE);

		}*/




		/*if (persistancedata.getRole().equalsIgnoreCase("admin")) {

			settingsButton.setVisibility(View.VISIBLE);
		} else {

			settingsButton.setVisibility(View.GONE);
		}*/

	//	navStep_GS = new NavigationBar(this, this);

		newMeasurement = (Button) findViewById(R.id.button_newmeasurement);
		followUp = (Button) findViewById(R.id.button_followUp);

		prefs = getSharedPreferences(COMMON, Context.MODE_PRIVATE);
		ashaOrdoc = prefs.getString(COMMON_USER_TYPE, VARIABLE_MISSING);
		username = prefs.getString(COMMON_USER_NAME, VARIABLE_MISSING);
		userID = prefs.getString(COMMON_USER_ID, VARIABLE_MISSING);
		userPHC = prefs.getString(COMMON_USER_PHC, VARIABLE_MISSING);

		Date cDate = new Date();
		String fDate = new SimpleDateFormat("dd/MM/yyyy").format(cDate);
		String fTime = new SimpleDateFormat("HH:mm:ss:SSS").format(cDate);



	}

	/*private void initializeNavBar() {
		navStep_GS.setEnabledButtons(Nav_disable, Nav_disable, Nav_disable,
				Nav_disable, Nav_disable);

	}*/

	public void initializeButtonListeners() {

		newMeasurement.setOnClickListener(this);
		followUp.setOnClickListener(this);

		//plistingButton.setOnClickListener(this);
		backStepButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//		appLogger.LogEvent(EventType.BUTTON_PRESSED, "BackStepButton", "");
				gotoPreviousScreen();


			}
		});
	}

	protected void exitToHome() {
		Intent back = new Intent(CommunityStream1.this,
				input_step1_Registration.class);
		startActivity(back);
		/* add event to logger */
		//appLogger.LogEvent(EventType.ACTIVITY_ENDED, "Step1-Registration", "going back");
		CommunityStream1.this.finish();
	}


	private void gotoPreviousScreen() {
		/*
		 * Place a dialog to prompt user that all the data will be lost by
		 * navigating to previous screen.
		 * on ok click execute below code. on cancel do nothing
		 *
		 */
		AlertDialog.Builder builder = new AlertDialog.Builder(CommunityStream1.this);

		builder.setIcon(R.drawable.alert);

		builder.setMessage(getResources().getString(
				R.string.logout_prompt));

		// Setting OK Button
		builder
				.setPositiveButton( getResources().getString(R.string.string_ok)  ,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
												int which) {

//						{
//							alertDialog.dismiss();
//						}
								wantoExit = true;

								dialog.dismiss();
								exitToHome();
							}
						});
		builder.setNegativeButton(
				getResources().getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
										int which) {
						// Write your code here to execute after
						// dialog
						// closed
						wantoExit = false;
						dialog.cancel();
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
//		prefs = getSharedPreferences(CVD_VARIABLES,
//				Context.MODE_PRIVATE);
//		editorShared = prefs.edit();
//		editorShared.clear();
//		editorShared.commit();
		/*
				if(wantoExit)
				{

				}*/

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	//	navStep_GS.setPresentStep(NavStep1);
		// PushLink.setCurrentPopUpTarget(this);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// stop animation

	}

	@Override
	public void onClick(View v) {

		int id = v.getId();
		if (id == R.id.button_newmeasurement) {
			prefs = getSharedPreferences(CVD_VARIABLES, Context.MODE_PRIVATE);
			editorShared = prefs.edit();
			editorShared.clear();
			editorShared.commit();
			prefs = getSharedPreferences(COMMON, Context.MODE_PRIVATE);
			editorShared.putString(COMMON_USER_TYPE, ashaOrdoc);
			editorShared.putString(COMMON_USER_NAME, username);
			editorShared.putString(COMMON_USER_ID, userID);
			editorShared.putString(COMMON_USER_PHC, userPHC);
			editorShared.commit();
		//	appLogger.LogEvent(EventType.BUTTON_PRESSED, "NewPatient", "");
			//if (persistancedata.getRole().equalsIgnoreCase(
			//		this.getString(R.string.doctor_name))) {

				//Intent intent = new Intent(HomeScreen.this,
				//		RegistrationActivityForDoctor.class);

				//intent.putExtra("newpracticesession", "1");

				//startActivity(intent);

			//}
			/*else {

				startActivity(new Intent(HomeScreen.this, PatientSearch.class));

			}*/
			/*appLogger.LogEvent(EventType.ACTIVITY_ENDED, "HomeScreen", "");
			appLogger.flush();
			finish();*/
			startActivity(new Intent(CommunityStream1.this, NewMeasurement.class));
			finish();
		}
		else if (id == R.id.button_followUp) {
		//	appLogger.LogEvent(EventType.BUTTON_PRESSED, "ReviewPatient", "");
		//	startActivity(new Intent(HomeScreen.this,ReviewPatient_Search.class));
		//	finish();
		}
		/*else if (id == R.id.button_plisting) {
			startActivity(new Intent(HomeScreen.this,
					Priority_listing_Activity.class));
			finish();
		}*/


	}

	/*private void openpdf() {
		*//*
		 * // open telugu manual - this is if external storage is available
		 * Intent intent = new Intent(Intent.ACTION_VIEW); String fileName =
		 * "telugu_manual.pdf"; File path =
		 * Environment.getExternalStorageDirectory(); String OutputfileName =
		 * path + "/" + fileName; File file = new File( OutputfileName );
		 * intent.setDataAndType( Uri.fromFile( file ), "application/pdf" );
		 * startActivity(intent);
		 *//*

		// since its not available, lets pull up the bit from the assets folder

		new PDFtask().execute();

	}*/

	/*protected class PDFtask extends AsyncTask<Void, Void, Boolean> {
		private final ProgressDialog dialog = new ProgressDialog(
				HomeScreen.this);

		protected void onPostExecute() {
			if (dialog.isShowing()) {
				dialog.setMessage(HomeScreen.this.getResources().getString(
						R.string.ab_risk));
				dialog.dismiss();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage(HomeScreen.this.getResources().getString(
					R.string.ab_risk));
			dialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			*//*
			 * if (dialog.isShowing()) {
			 * dialog.setMessage(HomeScreen.this.getResources
			 * ().getString(R.string.ab_risk)); }
			 *//*PdfHandler pdf = new PdfHandler(HomeScreen.this);
			pdf.openPdf("telugu_manual.pdf");
			return null;
		}

	}*/

	/*public boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if ("org.ibme.gi.service.UpdateEncounterDataService"
					.equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}*/

}
