package com.mayank.healthcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class appointement extends AppCompatActivity implements FetchDataListener {

    ArrayList<HashMap<String, String>> contactList;
    String emaildoctor;
    String emailpatient;
    Button button;
    private String TAG = predictor.class.getSimpleName();
    private ProgressDialog pDialog;
    // URL to get contacts JSON
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointement);
        Intent intent = getIntent();
        emaildoctor = intent.getStringExtra("emaildoctor");
        emailpatient = intent.getStringExtra("emailpatient");
        String u = "https://phc-rest-api.herokuapp.com/getdocdetails/full?e=" + emaildoctor;
        u = u.replaceAll(" ", "%20");
        url = u;
        button = (Button) findViewById(R.id.book);
        FetchData fetchData = new FetchData();
        fetchData.setArgs(url, this);
        fetchData.execute();
    }

    @Override
    public void onPostExecute(String jsonStr) {
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                // Getting JSON Array node
                JSONArray details = jsonObj.getJSONArray("details");
                // looping through All Contacts
                for (int i = 0; i < details.length(); i++) {
                    JSONObject c = details.getJSONObject(i);

                    String fname = c.getString("fname");
                    String memail = c.getString("email");
                    String mspeciality = c.getString("speciality");
                    String mfees = c.getString("fees");
                    String mphone = c.getString("phone");
                    String mqualifications = c.getString("qualifications");

                    TextView name = (TextView) findViewById(R.id.name);
                    TextView email = (TextView) findViewById(R.id.email);
                    TextView speciality = (TextView) findViewById(R.id.speciality);
                    TextView fees = (TextView) findViewById(R.id.fee);
                    TextView phone = (TextView) findViewById(R.id.phone);
                    TextView qualifications = (TextView) findViewById(R.id.qualification);

                    name.setText(fname);
                    email.setText(memail);
                    speciality.setText(mspeciality);
                    fees.setText(mfees);
                    phone.setText(mphone);
                    qualifications.setText(mqualifications);

                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });
        }

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        confirmbooking.class);
                intent.putExtra("emaildoctor", emaildoctor);
                intent.putExtra("emailpatient", emailpatient);
                startActivity(intent);
                finish();
            }


        });

    }

    @Override
    public void onPreExecute() {
        // Showing progress dialog
        pDialog = new ProgressDialog(appointement.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

}
