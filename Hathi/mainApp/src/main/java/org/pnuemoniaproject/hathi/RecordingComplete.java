package org.pnuemoniaproject.hathi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import devices.RecordActivity;

public class RecordingComplete extends AppCompatActivity {

    private static Button returnHome, recordSamePatient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recording_complete);

        returnHome = (Button) findViewById(R.id.returnHome);
        recordSamePatient = (Button) findViewById(R.id.recordSamePatient);


        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecordingComplete.this, firstMenu.class));
                finish();
            }
        });

        recordSamePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecordingComplete.this, RecordActivity.class));
                finish();
            }
        });




    }
}
