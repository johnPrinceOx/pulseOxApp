package org.pnuemoniaproject.hathi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class vietnam_info extends AppCompatActivity {

    private static Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vietnam_info);


        back = (Button) findViewById(R.id.backToLogin);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(vietnam_info.this, LoginScreen.class));
                finish();
            }
        });




    }
}
