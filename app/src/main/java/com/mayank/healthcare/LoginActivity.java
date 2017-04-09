package com.mayank.healthcare;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Mayank S Rawat on 3/12/2017.
 */
public class LoginActivity extends Activity implements FetchDataListener {
    String email;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnLogin;
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void checkLogin(final String email, final String password) {
        FetchData fetchData = new FetchData();

        String url = "https://phc-rest-api.herokuapp.com/loginp/p?email=" +
                email + "&pass=" + password;

        fetchData.setArgs(url, this);
        fetchData.execute();
    }

    @Override
    public void onPostExecute(String result) {
        try {
            loadingDialog.dismiss();
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();

            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getBoolean("status")) {
                Intent intent = new Intent(LoginActivity.this, Doctorlist.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, jsonObject.getString("error_msg"), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPreExecute() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("Please wait");
        loadingDialog.show();
    }
}
