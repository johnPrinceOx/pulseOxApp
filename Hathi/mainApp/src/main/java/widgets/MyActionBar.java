package widgets;

import db.CVDVariables;
import org.pnuemoniaproject.hathi.HomeScreen;
import org.pnuemoniaproject.hathi.LoginScreen;
import org.pnuemoniaproject.hathi.R;
import db.DatabaseConnecter;
import db.DatabaseVariables;
import utils.PersistanceData;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyActionBar extends LinearLayout {

	public static final String TAG = MyActionBar.class.getSimpleName();

	private Activity parentactivity;

	PersistanceData persistancedata;

	DatabaseConnecter dbConnecter;

	public SharedPreferences prefs;

	public SharedPreferences.Editor editorShared;

	public MyActionBar(Context newContext) {
		super(newContext);
	}

	public MyActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyActionBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void initHeader(Activity activity) {
		parentactivity = activity;

		inflateHeader();
	}

	private void inflateHeader() {

		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.custom_actionbar, this);

		onclickListener listener = new onclickListener();

		Button refreshButton = (Button) findViewById(R.id.refreshButton);

		Button logout_btn = (Button) findViewById(R.id.logout_icon);

		logout_btn.setOnClickListener(listener);

		Button home_btn = (Button) findViewById(R.id.home_Icon);

		home_btn.setOnClickListener(listener);

		TextView title_tv = (TextView) findViewById(R.id.title_label);

		if (parentactivity instanceof LoginScreen == true) {

			logout_btn.setVisibility(GONE);
			home_btn.setVisibility(GONE);

		}
		/*else if (parentactivity instanceof HomeScreen == true) {

			home_btn.setVisibility(GONE);

			refreshButton.setVisibility(View.VISIBLE);

		} else if (parentactivity instanceof GraphActivity == true
				|| parentactivity instanceof CVDoutput_step5_ProspectChanges == true) {

			logout_btn.setVisibility(GONE);
			home_btn.setVisibility(GONE);
		}*/

		String titleText = getUserName();

		title_tv.setText(titleText);

	}

	private String getUserName() {

		if (parentactivity instanceof LoginScreen) {
			/*
			 * Login screen no user is logged in hence display application name
			 * as title
			 */
			return getResources().getString(R.string.app_name);
		} else {

			/*
			 * write code to get logged in user details and return user name to
			 * display as title in rest of screens
			 */
			return getResources().getString(R.string.app_name);

		}
	}

	protected class onclickListener implements OnClickListener {

		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {

			final Intent openActivity = new Intent();
			openActivity.setAction(Intent.ACTION_VIEW);

			AlertDialog alertDialog = new AlertDialog.Builder(parentactivity)
					.create();

			switch (v.getId()) {

			case R.id.logout_icon:

				// Setting Dialog Title

				// Setting Dialog Message

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.alert);

				alertDialog.setMessage(getResources().getString(
						R.string.logout_prompt));

				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// Write your code here to execute after dialog
								// closed
							/*	persistancedata = new PersistanceData(
										getContext());
								persistancedata.clearPreferenceData();
								dbConnecter = new DatabaseConnecter(

								getContext());
								try {

									if (CVDVariables.selectedPatientRecord != null) {

										dbConnecter
												.deleterow(CVDVariables.selectedPatientRecord
														.getPatient_ID());

									}

									dbConnecter
											.deleteTableRecordsByTableName(DatabaseVariables.TABLE_MAINLINE_CACHE_HT);
									
									
									

								} catch (Exception e) {
									// TODO: handle exception

									e.printStackTrace();
								}

								openActivity
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

								openActivity
										.setClassName(
												"org.ibme.gi.mainRCT_healthtrackercvd",
												"org.ibme.gi.mainRCT_healthtrackercvd.LoginScreen");
								parentactivity.startActivity(openActivity);
								parentactivity.finish();*/

								openActivity
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								openActivity
										.setClassName(
												"org.pnuemoniaproject.hathi",
												"org.pnuemoniaproject.hathi.LoginScreen");
								parentactivity.startActivity(openActivity);
								parentactivity.finish();

							}
						});
				alertDialog.setButton2(getResources()
						.getString(R.string.cancel),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// Write your code here to execute after dialog
								// closed

							}
						});

				// Showing Alert Message
				alertDialog.show();

				break;

			case R.id.home_Icon:

				if (parentactivity instanceof HomeScreen == false) {

					// prefs = parentactivity.getSharedPreferences(
					// CVDVariables.CVD_VARIABLES, Context.MODE_PRIVATE);
					// editorShared = prefs.edit();
					// editorShared.clear();
					// editorShared.commit();

					alertDialog.setIcon(R.drawable.alert);

					alertDialog.setMessage(getResources().getString(
							R.string.home_prompt));

					// Setting OK Button
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// Write your code here to execute after
									// dialog
									// closed
									/*dbConnecter = new DatabaseConnecter(
											getContext());

									try {
										dbConnecter
												.deleterow(CVDVariables.selectedPatientRecord
														.getPatient_ID());

										dbConnecter
												.deleteTableRecordsByTableName(DatabaseVariables.TABLE_MAINLINE_CACHE_HT);

									} catch (Exception e) {

										e.printStackTrace();
									}
									openActivity
											.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									openActivity
											.setClassName(
													"org.ibme.gi.mainRCT_healthtrackercvd",
													"org.ibme.gi.mainRCT_healthtrackercvd.HomeScreen");
									parentactivity.startActivity(openActivity);
									parentactivity.finish();*/

									openActivity
											.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									openActivity
											.setClassName(
													"org.pnuemoniaproject.hathi",
													"org.pnuemoniaproject.hathi.LoginScreen");
									parentactivity.startActivity(openActivity);
									parentactivity.finish();

								}
							});

					alertDialog.setButton2(
							getResources().getString(R.string.cancel),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// Write your code here to execute after
									// dialog
									// closed

								}
							});

					// Showing Alert Message
					alertDialog.show();

				}

				break;

			}
		}
	}

}