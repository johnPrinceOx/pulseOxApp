package org.pnuemoniaproject.hathi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import db.utils.johnUsefulMethods;
import devices.RecordActivity;

public class firstMenu extends AppCompatActivity {

    private static Button newPatient, oldPatient, quickMeasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        johnUsefulMethods.initializePatientVariables();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
                startActivity(new Intent(firstMenu.this, RecordActivity.class));
                finish();
            }
        });



    }




    /*
    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.createNew) {

            startActivity(new Intent(firstMenu.this, johnactivity.class));
            finish();
        }
        else if (id == R.id.lookOld) {

            //startActivity(new Intent(firstMenu.this, HospiStream2.class));
            //finish();
        }

        else if (id == R.id.quick) {
            startActivity(new Intent(firstMenu.this,NewMeasurement.class));
        }

    }
*/



}
