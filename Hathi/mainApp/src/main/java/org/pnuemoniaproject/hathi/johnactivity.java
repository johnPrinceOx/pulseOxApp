package org.pnuemoniaproject.hathi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Toast;

import db.CVDVariables;
import db.utils.johnUsefulMethods;
import devices.RecordActivity;

public class johnactivity extends AppCompatActivity {



    private static Button backStepButton;
    private static Button button_login;

    private EditText firstName, lastName, age, id;
    private boolean wantoExit;

    ArrayList<String> allData = new ArrayList<String>();

    private RadioGroup Gender;
    private RadioButton Male;
    private RadioButton Female;
    private String radio = "";
    private Boolean allComplete = false;

    static HashMap<String,String> basicInfo = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.johnactivity);
        allData = onClickButtonListener();
        //onRadioClickListener();
    }

    public ArrayList<String> onClickButtonListener() {
        button_login = (Button) findViewById(R.id.buttonLog);
        button_login.setEnabled(false);

        backStepButton = (Button) findViewById(R.id.backIntro);
        backStepButton.setEnabled(true);


        Gender = (RadioGroup) findViewById(R.id.Gender);
        Male = (RadioButton) findViewById(R.id.Male);
        Female =(RadioButton) findViewById(R.id.Female);

        firstName = (EditText) findViewById(R.id.editFirst);
        lastName = (EditText) findViewById(R.id.editLast);
        age = (EditText) findViewById(R.id.editAge);
        id = (EditText) findViewById(R.id.editID);

        final ArrayList<String> basicData = new ArrayList<String>();


        backStepButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //		appLogger.LogEvent(EventType.BUTTON_PRESSED, "BackStepButton", "");
                gotoPreviousScreen();


            }
        });




        Gender.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.Male) {
                    radio = "Male";
                    basicInfo.put("Gender","Male");
                } else if (checkedId == R.id.Female) {
                    radio = "Female";
                    basicInfo.put("Gender","Female");
                }
                if (allFieldsComplete(firstName.getText().toString() , lastName.getText().toString(),age.getText().toString(),id.getText().toString())) {

                    button_login.setEnabled(true);
                }
                else
                {
                    button_login.setEnabled(false);
                }
            }
        });

            button_login.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date today = Calendar.getInstance().getTime();
                String reportDate = df.format(today);

                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String Age = age.getText().toString();
                String P_ID = id.getText().toString();

                basicData.add(reportDate);
                basicData.add(fName);
                basicData.add(lName);
                basicData.add(Age);
                basicData.add(P_ID);
                basicData.add(radio);
                basicData.add(Long.toString(System.currentTimeMillis()));

                if (allFieldsComplete(firstName.getText().toString() , lastName.getText().toString(),age.getText().toString(),id.getText().toString())) {

                    button_login.setEnabled(true);

                    storeInCSV(basicData);
                    int rowPointer = johnUsefulMethods.writeData(basicData);

                    //basicData.add(Integer.toString(rowPointer));

                    CVDVariables.J_VARIABLES_ROW_POINTER = Integer.toString(rowPointer);
                    CVDVariables.J_VARIABLES_FIRST_NAME = fName;
                    CVDVariables.J_VARIABLES_LAST_NAME = lName;
                    CVDVariables.J_VARIABLES_P_ID = P_ID;

                    try {
                        johnUsefulMethods.saveInCSVFile(basicData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Intent forward = new Intent(johnactivity.this,
                            RecordActivity.class);
                    startActivity(forward);
                }
                else
                {
                            button_login.setEnabled(false);
                }

            }
            }

            );

            return basicData;

        }


    private void storeInCSV( ArrayList<String> Data){

        //String csvFile = "/sdcard/P308.csv";
        //String csvName = "/sdcard/" + Data.get(1) + Data.get(3)+".csv";
        String csvName = "/sdcard/Numberofrows4.csv";

        try {
            FileWriter writer = new FileWriter(csvName);
            List<String> messages = Arrays.asList("Date", "First Name", "Last Name", "Age", "Patient ID", "Gender", "Current Time In Millis");
            usefulMethods.writeLine(writer, messages);
            usefulMethods.writeLine(writer, Data);
            //
            //usefulMethods.writeLine(writer, Arrays.asList(fName, lName, Age, P_ID));
            //
            writer.flush();
            writer.close();


            FileOutputStream fOut = openFileOutput("file name here", MODE_WORLD_READABLE);
            String str = "data";
            fOut.write(str.getBytes());
            fOut.close();

        } catch (IOException ie) {
            ie.printStackTrace();
        }


    }

    public static Boolean allFieldsComplete(String var1, String var2, String Var3, String Var4){


        if (var1.length() > 0 && var2.length() > 0
                && Var3.length() > 0 && (Var4.length() > 0))
        {
            return true;
        } else
            return false;
    }

    private void gotoPreviousScreen() {
		/*
		 * Place a dialog to prompt user that all the data will be lost by
		 * navigating to previous screen.
		 * on ok click execute below code. on cancel do nothing
		 *
		 */
        AlertDialog.Builder builder = new AlertDialog.Builder(johnactivity.this);

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
        Intent back = new Intent(johnactivity.this,
                firstMenu.class);
        startActivity(back);
		/* add event to logger */
        //appLogger.LogEvent(EventType.ACTIVITY_ENDED, "Step1-Registration", "going back");
    }


}
