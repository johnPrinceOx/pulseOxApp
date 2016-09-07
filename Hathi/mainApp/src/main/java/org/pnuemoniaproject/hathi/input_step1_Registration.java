package org.pnuemoniaproject.hathi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//import org.ibme.gi.algorithm.patientTemplate;
import db.CVDVariables;
import org.pnuemoniaproject.hathi.Constants;

import org.pnuemoniaproject.hathi.R;
import org.pnuemoniaproject.hathi.Constants.EventCalendar;
import db.CVDBase;
//import org.ibme.gi.healthtrackercvd.db.CVDBaseProvider;
//import org.ibme.gi.healthtrackercvd.logger.CVDappLogger;
//import org.ibme.gi.healthtrackercvd.logger.CVDappLogger.EventType;
import widgets.MyActionBar;
import org.json.JSONObject;

/*
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
*/

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class input_step1_Registration extends CVDVariables {
	private static final String CLASSNAME = "Hathi";//CVDBaseProvider.class.getSimpleName();
	private TextView tv_pBirthDate, tv_pickApproxAge;

	private EditText et_pID, et_pFname, et_pLname, et_pCNumber, et_address, et_contactNum, et_AgefromDob;

	private Button backStepButton, nextStepButton;

	private ImageView im_PickDOB, im_infoCNumber, im_infoLName;

	private RadioGroup radioSexGroup;
	
	private Spinner villageSpinner;


	private long id;
	private String firstName;
	private String lastName;



	private CheckBox chk_ddmm, chk_ph;
	


	static final int DATE_DIALOG_ID = 0;
	private int genderInt = -1;
	private int mYear = 1950;
	private int mMonth = 01;
	private int mDay = 01;
	private EditText answer;
	private String DEFAULT_VALUE = "defaultValue";
	private String villageName;
	private String userID;
	
	private boolean wantoExit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		//setContentView(R.layout.step1_register_patient);
		setContentView(R.layout.step1_alternate);

		//currentPatient = new patientTemplate();
		prefs = getSharedPreferences(COMMON, Context.MODE_PRIVATE);
		userID = prefs.getString(COMMON_USER_ID,
				VARIABLE_MISSING);
		/*appLogger = new CVDappLogger(CVDVariables.getAppLoggerFileName(), input_step1_Registration.this, prefs);
		*//* add event to logger *//*
		appLogger.LogEvent(EventType.ACTIVITY_STARTED, "Step1-Registration", "");
*/
		initializeActionBar();
		initializeViews();

		initializeNavBarandFooter();
		
		initializeListeners();
		TextWatchers();
		setPIDno();

		RetrieveFromSharedPrefs();

	}

	private void setPIDno() {
		int temp = generateSimplePID();
		et_pID.setText(String.valueOf(temp));

	}

	private int generateSimplePID() {
		
		StringBuilder simplePID = new StringBuilder();
		
		/*patientID = ashaID . PHC_code . (2digits of (3digit_randomnumber + (dd) + (mm)))*/
		
		String result1 = prefs.getString(COMMON_USER_ID, VARIABLE_MISSING);
			if (!(result1.equalsIgnoreCase(VARIABLE_MISSING))) {
				//GET THE USERID
				simplePID.append(result1);
			}
			else
			{  //append the absolute value of variable missing(-1), which is 1
				simplePID.append(Math.abs(Integer.valueOf(result1)));
				//simplePID.append(  Integer.valueOf(result1)*(-1) );
			}
		
		
		Log.i(Constants.TAG, "first part of SimplePID is " + simplePID.toString());
		
		if (prefs.getString(COMMON_USER_PHC, "1") != null) {
			String result2 = prefs.getString(COMMON_USER_PHC, VARIABLE_MISSING);
			if (!(result2.equalsIgnoreCase(VARIABLE_MISSING))) {
				//GET THE PHC CODE
				simplePID.append(result2);
			}
			else
			{  //append the absolute value of variable missing(-1), which is 1
				simplePID.append((Integer.valueOf(result2))*(-1));
			}
		}
		Log.i(Constants.TAG, "second part of SimplePID is " + simplePID.toString());
		
		Calendar c = Calendar.getInstance(); 
		int present_day = c.get(Calendar.DAY_OF_MONTH);
		int present_month = c.get(Calendar.MONTH);
		int present_time_seconds = c.get(Calendar.SECOND);
		
		Random rand = new Random();
		/*for returning a 4 digit random no, use:
		 int randnum = rand.nextInt(9999);*/
		int randnum = rand.nextInt(999);
		
		int finalappendPart = (int) (present_day + present_month + present_time_seconds + randnum)%100;
		Log.i(Constants.TAG, "penultimate part of SimplePID is " + (present_day + present_month + present_time_seconds + randnum));
		
		simplePID.append(finalappendPart);
		
		Log.i(Constants.TAG, "final part of SimplePID is " + simplePID.toString());
		
		return Integer.valueOf(simplePID.toString());
	}

	public void initializeActionBar() {

		MyActionBar header = (MyActionBar) findViewById(R.id.action_bar);
		header.initHeader(input_step1_Registration.this);

	}



	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);// must store the new intent unless getIntent() will
							// return the old one

		// flag = sendResulttoSana(flag);
		// checkitout();
	}

	private void initializeNavBarandFooter() {

		backStepButton = (Button) findViewById(R.id.btn_back_step);
		nextStepButton = (Button) findViewById(R.id.btn_next_step);

		backStepButton.setVisibility(View.VISIBLE);
		nextStepButton.setVisibility(View.VISIBLE);

		nextStepButton.setEnabled(false);

	}

	public void initializeViews() {

//		navStep_RP = new NavigationBar(this, this);

		tv_pBirthDate = (TextView) findViewById(R.id.showMyDate);
		tv_pickApproxAge = (TextView) findViewById(R.id.pickApproxAge);

		et_pID = (EditText) findViewById(R.id.EditText_patientID);
		et_pCNumber = (EditText) findViewById(R.id.EditText_consentform);
		et_pFname = (EditText) findViewById(R.id.EditText_firstName);
		et_pLname = (EditText) findViewById(R.id.EditText_LastName);
		et_address = (EditText) findViewById(R.id.EditText_Address);
		villageSpinner = (Spinner) findViewById(R.id.village_spinner);
		et_contactNum = (EditText) findViewById(R.id.EditText_ContactNum);
		et_AgefromDob = (EditText) findViewById(R.id.EditText_Age_fromDob);
	
		radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
		chk_ddmm = (CheckBox) findViewById(R.id.chk_ddmm);
		chk_ph = (CheckBox) findViewById(R.id.chk_phone);
		im_PickDOB = (ImageView) findViewById(R.id.DOBcalendar_drawing);
		im_infoCNumber = (ImageView) findViewById(R.id.infoBtn_consentNumber);
		im_infoLName = (ImageView) findViewById(R.id.infoBtn_lastName);
		initVillageSpinner();

	}

	private void initVillageSpinner() {
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.village_spinner, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		villageSpinner.setAdapter(adapter);
		
	}



	private void initializeListeners() {

		im_infoCNumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/* add event to logger */
		//		appLogger.LogEvent(EventType.INFO_VIEWED, "ConsentNumber_info", "");
				showPopup(v,
						getResources()
								.getString(R.string.RP_ConsentNumber_info));

			}
		});

		im_infoLName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
		//		appLogger.LogEvent(EventType.INFO_VIEWED, "LastName_info", "");
				showPopup(v, getResources()
						.getString(R.string.RP_LastName_info));

			}
		});

		backStepButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

		//		appLogger.LogEvent(EventType.BUTTON_PRESSED, "BackStepButton", "");
				gotoPreviousScreen();
			

			}
		});

		nextStepButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		//		appLogger.LogEvent(EventType.BUTTON_PRESSED, "NextStepButton", "");
				if (allFieldsFilled()) {

		//			appLogger.LogEvent(EventType.ACTIVITY_ENDED,"Step1-Registration", "");
		//			appLogger.flush();
					//String myString = "Step1-Registration";
					/* test the post request */
				//	async_post(myString);

					
					//startActivity(new Intent(input_step1_Registration.this,HomeScreen.class));
					/*see if the user is a doctor or asha*/
					prefs = getSharedPreferences(COMMON, Context.MODE_PRIVATE);
					String ashaOrdoc = prefs.getString(COMMON_USER_TYPE,
							VARIABLE_MISSING);

					Log.i(Constants.TAG, "ashaordoc at Step2 is " + ashaOrdoc);
					Intent riskfactors=null;

					if (ashaOrdoc.equalsIgnoreCase(Constants.USER_TYPE_COMMUNITYSTREAM)) {
						riskfactors = new Intent(input_step1_Registration.this,
								CommunityStream1.class);
					}
					else
					{
						riskfactors = new Intent(input_step1_Registration.this,
								HospiStream1.class);
					}
					if(riskfactors!=null)
					{
						startActivity(riskfactors);
					//	appLogger.LogEvent(EventType.ACTIVITY_ENDED, "Step2-PatientHistory", "");
					//	appLogger.flush();
						finish();
					}
					finish();

				}
				else
				{
		//		appLogger.LogEvent(EventType.NOTE, "All_fields_not_filled", "Cannot proceed to Step2");
				makeToastwithText(getResources().getString(
						R.string.Toast_fillallfields));
				}
			}
		});

		radioSexGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {


				if (checkedId == R.id.radioMale) {
					genderInt = 1;

					//tv_pickApproxAge.setText(R.string.male_approx_Age);

				} else if (checkedId == R.id.radioFemale) {
					genderInt = 0;
					//tv_pickApproxAge.setText(R.string.female_approx_Age);

				}

				if (allFieldsFilled()) {

					nextStepButton.setEnabled(true);
				}

				/* add event to logger */
		/*		appLogger.LogEvent(EventType.RADIO_BUTTON_PRESSED,
						"Gender Radio button pressed",
						"value:" + "  " + String.valueOf(genderInt));*/

			}
		});
		
		villageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
									   int position, long id) {
//			makeToastwithText(parent.getItemAtPosition(position).toString());
				if(position!=0)
				{
				villageName = parent.getItemAtPosition(position).toString();
				/* add event to logger */
				/*appLogger.LogEvent(EventType.BUTTON_PRESSED,
						"Select Village Drop Down list pressed",
						"value:" + "  " + villageName);*/
				} else {
					villageName = VARIABLE_MISSING;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		/*
		 * // display the current date updateDisplay();
		 */
		im_PickDOB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
				/* add event to logger */
				/*appLogger.LogEvent(EventType.BUTTON_PRESSED,
						"Calendar for picking DOB",	"");*/
			}
		});

		tv_pickApproxAge.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/* add event to logger */
				/*appLogger.LogEvent(EventType.INFO_VIEWED,
						"DOB_EVENTPICKER button pressed", "");*/
				// get current state of Gender RadioButton
				Log.i(Constants.TAG, String.valueOf(genderInt));
				if ((genderInt != -1)) {
					String[] items = null;
					// i.e only when male or female is selected,
					// Dialog dob_dlg = new Dialog(getBaseContext());
					// LayoutInflater li = (LayoutInflater)
					// RegisterPatient_Step1.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					// View dlg_v1 = li.inflate(R.layout.dob_eventdialog, null,
					// false);
					// ListView dlg_lview = (ListView)
					// dob_dlg.findViewById(R.id.populateListView);

					AlertDialog.Builder builder = new AlertDialog.Builder(
							input_step1_Registration.this);
					builder.setTitle(input_step1_Registration.this
							.getResources().getString(
									R.string.eventpicker_heading));
					ListView dlg_lview = new ListView(
							input_step1_Registration.this);
					dlg_lview.setDivider(input_step1_Registration.this
							.getResources().getDrawable(R.drawable.divider));
					dlg_lview.setDividerHeight(1);
					if (genderInt == 1) // male
					{
						items = input_step1_Registration.this.getResources()
								.getStringArray(R.array.List_eventPicker_Male);
					} else if (genderInt == 0) // female
					{
						items = input_step1_Registration.this
								.getResources()
								.getStringArray(R.array.List_eventPicker_Female);
					}

					if (items != null) {
						Log.i(Constants.TAG, items.toString());
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								getBaseContext(),
								android.R.layout.simple_list_item_1, items);
						dlg_lview.setAdapter(adapter);
					}

					builder.setView(dlg_lview);
					final Dialog dialog = builder.create();
					dialog.show();

					// dob_dlg.setContentView(dlg_v1);
					// dob_dlg.show();

					if (items != null) {
						if (genderInt == 1) {

							final Constants constant = new Constants();
							final EventCalendar eventCal = constant
									.insertDates();
							dlg_lview.setItemsCanFocus(false);

							dlg_lview
									.setOnItemClickListener(new OnItemClickListener() {
										@Override
										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											// your code
											Log.d(Constants.TAG, "clicked "
													+ String.valueOf(position));
											int month = constant.getEventmonth(
													position + 1, eventCal);
											int year = constant.getEventYear(
													position + 1, eventCal);
											if (month != -1) {
												// set month in calendar.
												mMonth = (month - 1);
											}
											if (year != -1) {
												// set month in calendar.
												mYear = year;
											}
											Log.i(Constants.TAG,
													"month is "
															+ String.valueOf(mMonth)
															+ " year is "
															+ String.valueOf(mYear));

											/*launch alert dialog to ask how old ashas were*/
											final Dialog ageEntryDialog = new Dialog(input_step1_Registration.this);
											ageEntryDialog.setContentView(R.layout.event_calendar_auto_age);
											ageEntryDialog.setTitle(input_step1_Registration.this.getResources().getString(R.string.how_old_title));
											/* */

											// set the custom dialog components - text, image and button
											TextView question = (TextView) ageEntryDialog.findViewById(R.id.textView_how_old);
											question.setText(getResources().getString(R.string.how_old));
											answer = (EditText) ageEntryDialog.findViewById(R.id.editText_age_eventcal);
//											answer.setImageResource(R.drawable.ic_launcher);

											Button okButton = (Button) ageEntryDialog.findViewById(R.id.button_ok);
											Button cancelButton = (Button) ageEntryDialog.findViewById(R.id.button_cancel);
											// if button is clicked, close the custom dialog
											okButton.setOnClickListener(new OnClickListener() {
												@Override
												public void onClick(View v) {
													if (answer != null && answer.length() != 0) {
														int numberTominus = -1;
														numberTominus = Integer.valueOf(answer.getText().toString());
														mYear = mYear - numberTominus;
														updateDisplay_withAge();
														tv_pBirthDate.setText(mDay + "/" + (mMonth + 1) + "/" + (mYear));
													}
													ageEntryDialog.dismiss();
												}
											});
											cancelButton.setOnClickListener(new OnClickListener() {
												@Override
												public void onClick(View v) {
													ageEntryDialog.dismiss();
												}
											});
											ageEntryDialog.show();

//											showDialog(DATE_DIALOG_ID);
											/* add event to logger */
											/*appLogger
													.LogEvent(
															EventType.INFO_VIEWED,
															"DOB_EVENT_OPTION_MALE",
															"choice: "
																	+ "month is "
																	+ String.valueOf(mMonth)
																	+ " year is "
																	+ String.valueOf(mYear));*/

											dialog.dismiss();
										}
									});

						}
					}
				} else {
					makeToastwithText(getResources().getString(
							R.string.toast_fillgender));
					/* add event to logger */
					/*appLogger.LogEvent(EventType.INFO_VIEWED,
							"Gender not selected but info button pressed", "");*/

				}
			}

		});

	}

	/*public void async_post(String myLogs) {

		// do a twiiter search with a http post
		aq = new AQuery(input_step1_Registration.this);
		String url = "http://192.168.0.8:8888/Pilot_project1/index.php";
		// "http://requestb.in/1kohksl1";
		// "http://search.twitter.com/search.json";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("from", "HealthTrackerCVD");
		params.put("log", myLogs);

		aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject json, AjaxStatus status) {
				Log.i(Constants.TAG, json.toString());
				// showResult(json);

			}
		});

	}*/

	private void TextWatchers() {

		et_pID.addTextChangedListener(pID_Txtwatcher);
		et_pFname.addTextChangedListener(p_Fname_TxtWatcher);
		et_pCNumber.addTextChangedListener(pConsentno_TxtWatcher);
		et_pLname.addTextChangedListener(p_Mname_TxtWatcher);
		et_contactNum.addTextChangedListener(p_contact_phNo_TxtWatcher);
		et_address.addTextChangedListener(p_address_TxtWatcher);
		tv_pBirthDate.addTextChangedListener(date_TxtWatcher);
		et_AgefromDob.addTextChangedListener(agebox_TxtWatcher);
		
		/*click listeners for the check boxes*/
		chk_ddmm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		    {
		        if ( isChecked )
		        {
		        	/* add event to logger */
					/*appLogger.LogEvent(EventType.ET_ENTERED,
							"CheckBox_DD/MM_unsure",
							"value:" + "  " + String.valueOf(isChecked));*/
		        }
		        else
		        {
		        	/* add event to logger */
				/*	appLogger.LogEvent(EventType.ET_ENTERED,
							"CheckBox_DD/MM_unsure",
							"value:" + "  " + String.valueOf(isChecked));*/
		        }

		    }
		});
		/*click listeners for the check boxes*/
		chk_ph.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		    {
		        if ( isChecked )
		        {
		        	/* add event to logger */
					/*appLogger.LogEvent(EventType.ET_ENTERED,
							"CheckBox_PhoneNumber_Shared",
							"value:" + "  " + String.valueOf(isChecked));*/
		        }
		        else
		        {
		        	/* add event to logger */
					/*appLogger.LogEvent(EventType.ET_ENTERED,
							"CheckBox_PhoneNumber_Shared",
							"value:" + "  " + String.valueOf(isChecked));*/
		        }

		    }
		});
		
	}

	// Patient ID Text Watcher
	private TextWatcher pID_Txtwatcher = new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

			if (s.length() >= 3 && s.length() <= 4) {

				if (allFieldsFilled()) {

					nextStepButton.setEnabled(true);
				}

			} else if (s.equals("") || s.length() == 0)
				nextStepButton.setEnabled(false);
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

			if (s.length() > 0) {

				if (allFieldsFilled()) {

					nextStepButton.setEnabled(true);
				}

			} else if (s.equals("") || s.length() == 0) {
				nextStepButton.setEnabled(false);
			}
		}

		@Override
		public void afterTextChanged(Editable s) {

			/* add event to logger */
			/*appLogger.LogEvent(EventType.ET_ENTERED,
					"Patient ID txtview afterTxtchanged",
					"value:" + "  " + s.toString());*/

			if (s.equals("") || s.length() == 0) {
				nextStepButton.setEnabled(false);
			}
		}

	};

	// Age EditBox - autocompletion of in this case, updating the calendar display
		private TextWatcher agebox_TxtWatcher = new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

				if (s.length()==2 || s.length()==3) {
				} 
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				
			}

			@Override
			public void afterTextChanged(Editable s) {

				/* add event to logger */
				/*appLogger.LogEvent(EventType.ET_ENTERED,
						"Age_fromDoBCalendar txtview afterTxtchanged",
						"value:" + "  " + s.toString());*/
				
				if (s.length()==2 || s.length()==3 
						&& (Integer.valueOf(s.toString())>10 && Integer.valueOf(s.toString())<120) ) {
					int enteredAge = Integer.valueOf(s.toString());
					Calendar c = Calendar.getInstance(); 
					int presentyear = c.get(Calendar.YEAR);
					Log.i(Constants.TAG, "entered Age is " + String.valueOf(enteredAge) + " presentyear (date chosen) is " + String.valueOf(presentyear));
					mYear = presentyear - enteredAge;
					updateDisplay();
				} 
				
			}

		};
	
	// Firstname Text Watcher
	private TextWatcher p_Fname_TxtWatcher = new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

			if (s.length() > 0) {

				if (allFieldsFilled()) {

					nextStepButton.setEnabled(true);
				}

			} else if (s.equals("") || s.length() == 0)
				nextStepButton.setEnabled(false);
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

			if (s.length() > 0) {

				if (allFieldsFilled()) {

					nextStepButton.setEnabled(true);
				}

			} else if (s.equals("") || s.length() == 0) {
				nextStepButton.setEnabled(false);
			}
		}

		@Override
		public void afterTextChanged(Editable s) {

			/* add event to logger */
			/*appLogger.LogEvent(EventType.ET_ENTERED,
					"Patient FirstName txtview afterTxtchanged", "value:"
							+ "  " + s.toString());*/

			if (s.equals("") || s.length() == 0) {
				nextStepButton.setEnabled(false);
			}

		}

	};

	// SurName Text Watcher
	private TextWatcher p_Mname_TxtWatcher = new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

			if (s.length() > 0) {

				if (allFieldsFilled()) {

					nextStepButton.setEnabled(true);
				}

			} else if (s.equals("") || s.length() == 0)
				nextStepButton.setEnabled(false);
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

			if (s.length() > 0) {
				if (allFieldsFilled()) {

					nextStepButton.setEnabled(true);
				}
			} else if (s.equals("") || s.length() == 0) {
				nextStepButton.setEnabled(false);
			}
		}

		@Override
		public void afterTextChanged(Editable s) {

			/* add event to logger */
			/*appLogger.LogEvent(EventType.ET_ENTERED,
					"Patient Surname afterTxtchanged",
					"value:" + "  " + s.toString());*/

			if (s.equals("") || s.length() == 0) {
				nextStepButton.setEnabled(false);
			}
		}

	};
	
	// ConsentNumber Text Watcher
			private TextWatcher pConsentno_TxtWatcher = new TextWatcher() {

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {

					
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before,
						int count) {

					
				}

				@Override
				public void afterTextChanged(Editable s) {

					/* add event to logger */
					/*appLogger.LogEvent(EventType.ET_ENTERED,
							"ConsentNumber afterTxtchanged",
							"value:" + "  " + s.toString());*/

					
				}
			};
		
		// Address Text Watcher
		private TextWatcher p_address_TxtWatcher = new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				
			}

			@Override
			public void afterTextChanged(Editable s) {

				/* add event to logger */
			/*	appLogger.LogEvent(EventType.ET_ENTERED,
						"Address afterTxtchanged",
						"value:" + "  " + s.toString());*/

				
			}
		};
	
		
			// Contact Phone NUmber Text Watcher
				private TextWatcher p_contact_phNo_TxtWatcher = new TextWatcher() {

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {

						
					}

					@Override
					public void onTextChanged(CharSequence s, int start, int before,
							int count) {

						
					}

					@Override
					public void afterTextChanged(Editable s) {

						/* add event to logger */
					/*	appLogger.LogEvent(EventType.ET_ENTERED,
								"Contact Phone Number afterTxtchanged",
								"value:" + "  " + s.toString());*/

						
					}
				};
		
	// Date Text Watcher
	private TextWatcher date_TxtWatcher = new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

			if (s.length() > 0) {

				if (allFieldsFilled()) {

					nextStepButton.setEnabled(true);
				}
			} else if (s.equals("") || s.length() == 0)
				nextStepButton.setEnabled(false);
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

			if (s.length() > 0) {
				if (allFieldsFilled()) {

					nextStepButton.setEnabled(true);
				}
			} else if (s.equals("") || s.length() == 0) {
				nextStepButton.setEnabled(false);
			}
		}

		@Override
		public void afterTextChanged(Editable s) {

			/* add event to logger */
			/*appLogger.LogEvent(EventType.ET_ENTERED,
					"Date of Birth txtview afterTxtchanged", "value:" + "  "
							+ s.toString());*/

			if (s.equals("") || s.length() == 0) {
				nextStepButton.setEnabled(false);
			}
		}

	};

	@Override
	protected void makeToastwithText(String toast_text) {
		Context context = getApplicationContext();
		CharSequence text = toast_text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	protected void commitToSharedPrefs() {

		prefs = getSharedPreferences(CVD_VARIABLES, Context.MODE_PRIVATE);
		editorShared = prefs.edit();
		editorShared.putString(CVD_VARIABLES_PID, et_pID.getText().toString());
		editorShared.putString(CVD_VARIABLES_P_SURNAME, et_pLname.getText()
				.toString());
		editorShared.putString(CVD_VARIABLES_P_FIRSTNAME, et_pFname.getText()
				.toString());
		editorShared.putString(CVD_VARIABLES_P_DOB, tv_pBirthDate.getText()
				.toString());
		editorShared.putString(CVD_VARIABLES_P_CNUMBER, et_pCNumber.getText()
				.toString());
		editorShared.putString(CVD_VARIABLES_P_ADDRESS, et_address.getText()
				.toString());
		editorShared.putString(CVD_VARIABLES_P_VILLAGE, villageName);
		editorShared.putString(CVD_VARIABLES_P_CONTACTNUM, et_contactNum.getText()
				.toString());
		editorShared.putString(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE,(chk_ddmm.isChecked()? String.valueOf(YES):String.valueOf(NO)) );
		editorShared.putString(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE,(chk_ph.isChecked()? String.valueOf(YES):String.valueOf(NO)) );
		// get Present date
		Date cDate = new Date();
		String fDate = new SimpleDateFormat("dd/MM/yyyy").format(cDate);
		editorShared.putString(CVD_VARIABLES_P_DOA, fDate);

		// get selected radio button from radioGroup
		int selectedId = radioSexGroup.getCheckedRadioButtonId();
		// find the radiobutton by returned id

		if (selectedId == R.id.radioMale) {

			editorShared.putString(CVD_VARIABLES_P_GENDER, "1");

		} else if (selectedId == R.id.radioFemale) {
			editorShared.putString(CVD_VARIABLES_P_GENDER, "2");
		}
		editorShared.commit();
		
		//pushtoCache();

	}

	private void pushtoCache() {
		
		Date cDate = new Date();
		// get Present date
		String fDate = new SimpleDateFormat("dd/MM/yyyy").format(cDate);
		String fDateNtime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(cDate);
		ContentValues values = new ContentValues();
		values.put(CVDBase.PID, et_pID.getText().toString());
		values.put(CVDBase.P_SURNAME, et_pLname.getText()
				.toString());
		values.put(CVDBase.P_GIVENNAME, et_pFname.getText()
				.toString());
		values.put(CVDBase.P_DOB, tv_pBirthDate.getText()
				.toString());
		values.put(CVDBase.P_CONSENT_NO, et_pCNumber.getText()
				.toString());
		values.put(CVDBase.P_ADDRESS, et_address.getText()
				.toString());
		values.put(CVDBase.P_VILLAGE, villageName);
		values.put(CVDBase.P_CONTACTNUMBER, et_contactNum.getText()
				.toString());
		values.put(CVDBase.P_DOB_CHKBOX_DDMM_UNSURE, (chk_ddmm.isChecked() ? String.valueOf(YES) : String.valueOf(NO)));
		values.put(CVDBase.P_CHKBOX_CONTACTPHONE_UNSURE, (chk_ph.isChecked() ? String.valueOf(YES) : String.valueOf(NO)));
	
		values.put(CVDBase.P_DOA, fDate);

		// get selected radio button from radioGroup
		int selectedId = radioSexGroup.getCheckedRadioButtonId();
		// find the radiobutton by returned id

		if (selectedId == R.id.radioMale) {

			values.put(CVDBase.P_GENDER, "1");

		} else if (selectedId == R.id.radioFemale) {
			values.put(CVDBase.P_GENDER, "2");
		}
		values.put(CVDBase.USER_ID, userID);
		values.put(CVDBase.RA_PROCEDURE_COMPLETED, Constants.RA_NOT_COMPLETED);
		values.put(CVDBase.USER_LOG_IN_TIME, fDateNtime);
		
		/*is this patient record already there ? */
		boolean doesRecordexist = checkforRecord(userID, et_pID.getText().toString());
		
		if(doesRecordexist)
		{
			/*Update if record already exists*/
			String[] args = {et_pID.getText().toString()};
			getContentResolver().update(CVDBase.CONTENT_URI_CACHE, values, CVDBase.PID + " LIKE ?", args);
		}
		else
		{	/*Create if record does not exist*/
		getContentResolver().insert(CVDBase.CONTENT_URI_CACHE, values);
		}
		Log.i(Constants.TAG, "cache for " + CLASSNAME + "successful");
	}

	private boolean checkforRecord(String uID, String pID) {
		String[] mProjection =
			{
					BaseColumns._ID,    // Contract class constant for the _ID column name
			    CVDBase.PID
			};
		// Defines a string to contain the selection clause
				String mSelectionClause = CVDBase.USER_ID + " = ? AND " + CVDBase.PID + " = ? ";
				
				// Initializes an array to contain selection arguments
				String[] mSelectionArgs = {uID, pID};
				
				Cursor myCursor = getContentResolver().query(
						CVDBase.CONTENT_URI_CACHE,  // The content URI of the words table
					    mProjection,                       // The columns to return for each row
					    mSelectionClause,                   // Either null, or the word the user entered
					    mSelectionArgs,                    // Either empty, or the string the user entered
					    null);                       // The sort order for the returned rows
				
				Log.i(Constants.TAG, "Record" + myCursor.toString());
				if(myCursor.getCount()>0)
				{
					myCursor.close();
					return true;
				}
				else
				{
					myCursor.close();
					return false;
				}
				/*if(myCursor==null)
				{	
					myCursor.close();
					return false;
				}
				else
				{	myCursor.close();
					return true;
				}*/
	}

	protected void RetrieveFromSharedPrefs() {
		prefs = getSharedPreferences(CVD_VARIABLES, Context.MODE_PRIVATE);

		if (prefs.getString(CVD_VARIABLES_PID, DEFAULT_VALUE) != null) {
			String result = prefs.getString(CVD_VARIABLES_PID, DEFAULT_VALUE);
			if (!(result.equalsIgnoreCase(DEFAULT_VALUE))) {
				et_pID.setText(result);
			}
		}

		if (prefs.getString(CVD_VARIABLES_P_CNUMBER, DEFAULT_VALUE) != null) {
			String result = prefs.getString(CVD_VARIABLES_P_CNUMBER,
					DEFAULT_VALUE);
			if (!(result.equalsIgnoreCase(DEFAULT_VALUE))) {
				et_pCNumber.setText(result);
			}
		}

		if (prefs.getString(CVD_VARIABLES_P_SURNAME, DEFAULT_VALUE) != null) {
			String result = prefs.getString(CVD_VARIABLES_P_SURNAME,
					DEFAULT_VALUE);
			if (!(result.equalsIgnoreCase(DEFAULT_VALUE))) {
				et_pLname.setText(result);
			}
		}

		if (prefs.getString(CVD_VARIABLES_P_FIRSTNAME, DEFAULT_VALUE) != null) {
			String result = prefs.getString(CVD_VARIABLES_P_FIRSTNAME,
					DEFAULT_VALUE);
			if (!(result.equalsIgnoreCase(DEFAULT_VALUE))) {
				et_pFname.setText(result);
			}
		}
		
		if (prefs.getString(CVD_VARIABLES_P_VILLAGE, VARIABLE_MISSING) != null) {

			String result = prefs.getString(CVD_VARIABLES_P_VILLAGE,
					DEFAULT_VALUE);
			if (!(result.equalsIgnoreCase(VARIABLE_MISSING))) {

				ArrayAdapter myAdap = (ArrayAdapter) villageSpinner
						.getAdapter(); // cast to an ArrayAdapter

				int spinnerPosition = myAdap.getPosition(result);

				// set the default according to value
				villageSpinner.setSelection(spinnerPosition);
			}
		}

		if (prefs.getString(CVD_VARIABLES_P_DOB, DEFAULT_VALUE) != null) {
			String result = prefs.getString(CVD_VARIABLES_P_DOB, DEFAULT_VALUE);
			if (!(result.equalsIgnoreCase(DEFAULT_VALUE))) {
//				tv_pBirthDate.setText(result);
				String[] dob_inp_parts = result.split("/");
				mMonth = Integer.parseInt(dob_inp_parts[1]);
				mDay = Integer.parseInt(dob_inp_parts[0]);
				mYear = Integer.parseInt(dob_inp_parts[2]);
				updateDisplay_withAge();
				
			}
		}
		
		if(prefs.getString(CVD_VARIABLES_P_ADDRESS,DEFAULT_VALUE) != null)			
		{
			String result = prefs.getString(CVD_VARIABLES_P_ADDRESS,DEFAULT_VALUE);
			if( !(result.equalsIgnoreCase(DEFAULT_VALUE)))
			{
				et_address.setText(result);
			}
		}
		
		if(prefs.getString(CVD_VARIABLES_P_CONTACTNUM,DEFAULT_VALUE) != null)			
		{
			String result = prefs.getString(CVD_VARIABLES_P_CONTACTNUM,DEFAULT_VALUE);
			if( !(result.equalsIgnoreCase(DEFAULT_VALUE)))
			{
				et_contactNum.setText(result);
			}
		}
		
		if(prefs.getString(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE,DEFAULT_VALUE) != null)			
		{
			String result = prefs.getString(CVD_VARIABLES_P_DOB_CHKBOX_DDMM_UNSURE,DEFAULT_VALUE);
			if( !(result.equalsIgnoreCase(DEFAULT_VALUE)))
			{
				if (result.equalsIgnoreCase(String.valueOf(YES)))
				{
					//checked
					chk_ddmm.setChecked(true);
				}
				else if (result.equalsIgnoreCase(String.valueOf(NO)))
				{
					//not checked
					chk_ddmm.setChecked(false);
				}
			}
		}
		
		if(prefs.getString(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE,DEFAULT_VALUE) != null)			
		{
			String result = prefs.getString(CVD_VARIABLES_P_DOB_CHKBOX_SHAREDPHONE,DEFAULT_VALUE);
			if( !(result.equalsIgnoreCase(DEFAULT_VALUE)))
			{
				if (result.equalsIgnoreCase(String.valueOf(YES)))
				{
					//checked
					chk_ph.setChecked(true);
				}
				else if (result.equalsIgnoreCase(String.valueOf(NO)))
				{
					//not checked
					chk_ph.setChecked(false);
				}
			}
		}
		
		if (prefs.getString(CVD_VARIABLES_P_GENDER, DEFAULT_VALUE) != null) {
			String result = prefs.getString(CVD_VARIABLES_P_GENDER,
					DEFAULT_VALUE);
			if (!(result.equalsIgnoreCase(DEFAULT_VALUE))) {
				if (result.equalsIgnoreCase("1")) {
					RadioButton male = (RadioButton) findViewById(R.id.radioMale);
					male.setChecked(true);
				} else if (result.equalsIgnoreCase("2")) {
					RadioButton female = (RadioButton) findViewById(R.id.radioFemale);
					female.setChecked(true);
				}
			}
		}

	}

	protected boolean allFieldsFilled() {
		if (et_pID.length() > 0 && et_pFname.length() > 0
				&& et_pLname.length() > 0 && tv_pBirthDate.length() > 0
				&& (radioSexGroup.getCheckedRadioButtonId() != -1)) {
			return true;
		} else
			return false;
	}

	private void updateDisplay() {
		/*if you are using the age box and want to update the calendar, then use this method*/
		this.tv_pBirthDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mDay).append("/").append(mMonth + 1).append("/")
				.append(mYear).append(""));
	}
	
	private void updateDisplay_withAge() {
		/*call this if you want to update the age box as well */
		this.tv_pBirthDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mDay).append("/").append(mMonth + 1).append("/")
				.append(mYear).append(""));
		
		Calendar c = Calendar.getInstance(); 
		int presentyear = c.get(Calendar.YEAR);
		int patients_dob_year = presentyear - mYear;
		Log.i(Constants.TAG, "presentyear is " + String.valueOf(presentyear) + " mYear (date chosen) is " + String.valueOf(mYear));
		this.et_AgefromDob.setText(String.valueOf(patients_dob_year));
	}
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay_withAge();
			tv_pBirthDate.setVisibility(View.VISIBLE);

			/* add event to logger */
			String dob = String.valueOf(mDay) + String.valueOf(mMonth)
					+ String.valueOf(mYear);
			/*appLogger.LogEvent(EventType.BUTTON_PRESSED, "DoB listener",
					"value:" + "  " + dob);*/

		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		/* add event to logger */
		//appLogger.LogEvent(EventType.ACTIVITY_RESUMED,"","");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub

		super.onPause();
		/* add event to logger */
	//	appLogger.LogEvent(EventType.ACTIVITY_PAUSED,"","");
	}

	public void showPopup(View anchorView, String infoText) {

		View popUpView = getLayoutInflater().inflate(R.layout.info_popup, null);

		TextView tv_info = (TextView) popUpView.findViewById(R.id.tv_info);

		tv_info.setText(infoText);
		

		PopupWindow mpopup = new PopupWindow(popUpView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

		mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
		mpopup.setBackgroundDrawable(new BitmapDrawable());
		mpopup.setOutsideTouchable(true);

		int OFFSET_X = 30;
		int OFFSET_Y = 30;

		int[] location = new int[2];

		anchorView.getLocationOnScreen(location);
		Point p = new Point(location[0], location[1]);

		mpopup.setBackgroundDrawable(new BitmapDrawable());
		mpopup.showAtLocation(popUpView, Gravity.NO_GRAVITY, p.x + OFFSET_X,
				p.y + OFFSET_Y);

	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//appLogger.LogEvent(EventType.BUTTON_PRESSED, "android.Back Button", "");
		gotoPreviousScreen();
		
		super.onBackPressed();
		
		
	}

	private void gotoPreviousScreen() {
		/*
		 * Place a dialog to prompt user that all the data will be lost by 
		 * navigating to previous screen.
		 * on ok click execute below code. on cancel do nothing 
		 *  
		 */
		AlertDialog.Builder builder = new AlertDialog.Builder(input_step1_Registration.this);
		
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

	protected void exitToHome() {
		Intent back = new Intent(input_step1_Registration.this,
				LoginScreen.class);
		back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(back);
		/* add event to logger */
		//appLogger.LogEvent(EventType.ACTIVITY_ENDED, "Step1-Registration", "going back");
		input_step1_Registration.this.finish();
	}
}
