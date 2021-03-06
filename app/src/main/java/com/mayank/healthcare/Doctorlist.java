package com.mayank.healthcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Doctorlist extends AppCompatActivity {


    ArrayList<HashMap<String, String>> contactList;
    String emaildoctor;
    String emailpatient;
    private String TAG = predictor.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    // URL to get contacts JSON
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorlist);
        Intent box = getIntent();
        emailpatient = box.getStringExtra("email");
        url = "https://phc-rest-api.herokuapp.com/getdocdetails/some";
        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewdoctor);
        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Doctorlist.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

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
                        emaildoctor = c.getString("email");
                        String speciality = c.getString("speciality");
                        String fees = c.getString("fees");


                        // Phone node is JSON Object

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("fname", fname);
                        contact.put("speciality", speciality);

                        // contact.put("percentage", percentage);


                        // adding contact to contact list
                        contactList.add(contact);
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

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    Doctorlist.this, contactList,
                    R.layout.activity_doctor, new String[]{"fname", "speciality"
            }, new int[]{R.id.fname, R.id.speciality
            });

            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    //HashMap<String,String> selectedFromList = (HashMap<String, String>) lv.getItemAtPosition(position);
                    Intent intent = new Intent(Doctorlist.this, appointement.class);
                    intent.putExtra("emaildoctor", emaildoctor);
                    intent.putExtra("emailpatient", emailpatient);
                    startActivity(intent);
                }
            });
        }

    }


}
