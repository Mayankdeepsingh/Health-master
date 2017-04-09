package com.mayank.healthcare;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Mayank S Rawat on 3/12/2017.
 */
public class RegisterActivity extends Activity implements FetchDataListener {
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputPhone;
   // private EditText inputMaxpatients;
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputPhone = (EditText) findViewById(R.id.phone);
       // inputMaxpatients = (EditText) findViewById(R.id.maxpatients);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        //final Spinner spinner = (Spinner) findViewById(R.id.spinner);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String phone = inputPhone.getText().toString().trim();
               // String maxpatients = inputMaxpatients.getText().toString().trim();
                //String spinnertext = spinner.getSelectedItem().toString();
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phone.isEmpty()) {
                    registerUser(name, email, password,phone);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner
        //ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_item, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        //staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
       // spinner.setAdapter(staticAdapter);

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void registerUser(final String name, final String email,
                              final String password, final String phone) {

        String url = "https://phc-rest-api.herokuapp.com/registerp/p?f=" + name +
                "&e=" + email +
                "&p=" + password+
                "&ph=" +phone
               ;
        url= url.replaceAll(" ", "%20");

        FetchData fetchData = new FetchData();

        fetchData.setArgs(url, this);
        fetchData.execute();
    }

    @Override
    public void onPostExecute(String result) {
        loadingDialog.dismiss();
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        //String s = result.trim();
    }

    @Override
    public void onPreExecute() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("Please wait");
        loadingDialog.show();
    }}