package org.pnuemoniaproject.hathi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import db.utils.GMailSender;
import db.utils.Mail;
import db.utils.johnUsefulMethods;
import devices.RecordActivity;

public class firstMenu extends AppCompatActivity {

    private static Button newPatient, oldPatient, quickMeasure;
    private Mail m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        johnUsefulMethods.initializePatientVariables();

        newPatient = (Button) findViewById(R.id.createNew);
        oldPatient = (Button) findViewById(R.id.lookOld);
        quickMeasure = (Button) findViewById(R.id.quick);


        newPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(firstMenu.this, johnactivity.class));
                finish();
            }
        });

        oldPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(firstMenu.this, johnactivity.class));
                //finish();
            }
        });

        quickMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /*
                try {
                    GMailSender sender = new GMailSender("reports@starstudy.org", "R3p0rt5z");
                    sender.sendMail("This is Subject",
                            "This is Body",
                            "john.prince@eng.ox.ac.uk",
                            "john.prince.ox@gmail");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
                */

                // startActivity(new Intent(firstMenu.this, RecordActivity.class));
               m = new Mail("reports@starstudy.org", "R3p0rt5z");
                sendEmail(view);

                //finish();
            }
        });

    }

    public void sendEmail(View view){
        String[] toArr = {"reports@starstudy.org"}; // This is an array, you can add more emails, just separate them with a coma
        m.setTo(toArr); // load array to setTo function
        m.setFrom("reports@starstudy.org"); // who is sending the email
        m.setSubject("subject");
        m.setBody("Test \n test tes test tes test");

        try {
            //m.addAttachment("/sdcard/myPicture.jpg");  // path to file you want to attach
            if(m.send()) {
                // success
                Toast.makeText(firstMenu.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
            } else {
                // failure
                Toast.makeText(firstMenu.this, "Email was not sent.", Toast.LENGTH_LONG).show();
            }
        } catch(Exception e) {
            // some other problem
            Toast.makeText(firstMenu.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
        }

    }




}
