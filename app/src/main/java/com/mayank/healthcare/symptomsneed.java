package com.mayank.healthcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class symptomsneed extends AppCompatActivity {
    EditText e1;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptomsneed);
        e1=(EditText)findViewById(R.id.s1);
        b=(Button)findViewById(R.id.button3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=e1.getText().toString();
                Intent box =new Intent(symptomsneed.this,predictor.class);
                box.putExtra("data",s);
                startActivity(box);

            }
        });


    }




}
