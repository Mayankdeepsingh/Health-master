package com.mayank.healthcare;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mayank.healthcare.FetchData;
import com.mayank.healthcare.FetchDataListener;
import com.mayank.healthcare.R;

import java.util.Random;

/**
 * Created by Mayank S Rawat on 4/6/2017.
 */
public class confirmbooking extends Activity implements FetchDataListener {
    String patientemail;
    String doctoremail;
    private EditText patientname;
    private TextView txtPEmail;
    private TextView txtDEmail;
    private Button confirm;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmbooking);
        Intent intent = getIntent();
        patientemail = intent.getExtras().getString("emailpatient");
        doctoremail = intent.getExtras().getString("emaildoctor");

        patientname = (EditText) findViewById(R.id.name);
        confirm = (Button) findViewById(R.id.btnConfirm);
        logout = (Button) findViewById(R.id.btnlogout);
        txtPEmail = (TextView) findViewById(R.id.patientemail);
        txtDEmail = (TextView) findViewById(R.id.doctoremail);
        txtPEmail.setText(patientemail);
        txtDEmail.setText(doctoremail);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random = new Random();

                String url = "https://phc-rest-api.herokuapp.com/book?" +
                        "pe=" + patientemail +
                        "&de=" + doctoremail +
                        "&pn=" + patientname.getText() +
                        "&id=" + random.nextInt(10000);

                FetchData fetchData = new FetchData();
                fetchData.setArgs(url, confirmbooking.this);
                fetchData.execute();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(confirmbooking.this,LoginActivity.class);
                startActivity(intent);
                finish();


            }
        });
    }




    @Override
    public void onPostExecute(String result) {
//        loadingDialog.dismiss();
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onPreExecute() {
//        loadingDialog = new ProgressDialog(this);
//        loadingDialog.setMessage("Please wait");
//        loadingDialog.show();

    }
}

