package org.pnuemoniaproject.hathi;

import java.util.Calendar;
import java.util.Locale;

import db.CVDVariables;
import db.DatabaseConnecter;
import utils.PersistanceData;
//import org.ibme.gi.mainRCT_healthtrackercvd.R;
//import org.ibme.gi.receiver.UpdateEncounterDataReceiver;
import utils.AppUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

public class DashBoard extends CVDVariables {


	PersistanceData persistanceData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splashscreen);

		persistanceData = new PersistanceData(getApplicationContext());

//		initializeopenMRSresources();

		Handler handler = new Handler();
		
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {

				//copyDatabase();

				setSelectedLanguage();

				if (persistanceData.getUserName().length() <= 0) {
					Intent myIntent = new Intent();
					myIntent.setClass(DashBoard.this, LoginScreen.class);
					startActivity(myIntent);
					finish();
				} else {

					Intent myIntent = new Intent();
					myIntent.setClass(DashBoard.this, HomeScreen.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

					startActivity(myIntent);
					finish();

				}
			}
		}, 3000);

		// Set repeating of alarm every one hour i.e 60*60*1000

		// callingBroadCatReceiver(60 * 60 * 1000);

	}

	private void setSelectedLanguage() {
		// TODO Auto-generated method stub

		Configuration config = new Configuration();

		if (persistanceData.getLanguage() != null
				&& (persistanceData.getLanguage().equalsIgnoreCase("telugu") || AppUtil
						.isDeviceTeluguLocaleSelected())) {

			Locale myLocale;

			myLocale = new Locale("te");

			Locale.setDefault(myLocale);

			persistanceData.setLanguage("telugu");

			config.locale = myLocale;

			getResources().updateConfiguration(config, null);

			// onConfigurationChanged(config);

		} else {

			persistanceData.setLanguage("english");

			config.locale = Locale.ENGLISH;

			getResources().updateConfiguration(config, null);

		}

	}

	private void initializeopenMRSresources() {
		// TODO Auto-generated method stub

		persistanceData.setDeviceIMEI(AppUtil.getDeviceIMEI(this));

		persistanceData.setUsernameforUpload(AppUtil.getDefaultUsername(this));

		persistanceData.setPasswordforUpload(AppUtil.getDefaultPassword(this));
	}

	/*private void callingBroadCatReceiver(int repeatValue) {

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

		// After after 2 seconds
		String[] time;
		String time1 = "06:00";

		if (persistanceData.getUpdatedTime().equals("")) {

			time = time1.split(":");
		} else {
			time = persistanceData.getUpdatedTime().split(":");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());

		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) + 1);

		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));

		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				repeatValue, pi);

	}*/

}
