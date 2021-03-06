package com.cynosurecreations.mithostels;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //Defining views
    private EditText editTextEmail;
    private EditText editTextPassword;
    private AppCompatButton buttonLogin;
    private RadioGroup radioPreGroup;
    private RadioButton radioPreButton;

    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActionBar actionBar = getActionBar(); //or getSupportActionBar();
        //actionBar.hide();
        setContentView(R.layout.activity_login);

        //Initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        radioPreGroup=(RadioGroup)findViewById(R.id.radioGroupPrefix);
        buttonLogin = (AppCompatButton) findViewById(R.id.buttonLogin);

        //Adding click listener
        buttonLogin.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){

            type = sharedPreferences.getString(Config.TYPE_SHARED_PREF, "Not Available");
            //We will start the Profile Activity
            if(type.equals("Faculty")) {
                Intent intent = new Intent(LoginActivity.this, FacultyMainActivity.class);
                startActivity(intent);
                finish();
            }
            else if(type.equals("Student")) {
                Intent intent = new Intent(LoginActivity.this, StudentMainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void login() {
        //Getting values from edit texts

        int selectedId = radioPreGroup.getCheckedRadioButtonId();
        if(selectedId != -1) {

            final String id = editTextEmail.getText().toString().trim();
            final String password = editTextPassword.getText().toString().trim();
            radioPreButton = (RadioButton) findViewById(selectedId);
            final String type = radioPreButton.getText().toString().trim();

            //Toast.makeText(LoginActivity.this, type, Toast.LENGTH_LONG).show();

            if (type.equals("Faculty")) {
                Toast.makeText(LoginActivity.this, "Attempting log in", Toast.LENGTH_LONG).show();
                //Creating a string request
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //If we are getting success from server
                                if (response.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {
                                    //Creating a shared preference
                                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                    //Creating editor to store values to shared preferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    //Adding values to editor
                                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                    editor.putString(Config.EMAIL_SHARED_PREF, id);
                                    editor.putString(Config.TYPE_SHARED_PREF, type);

                                    //Saving values to editor
                                    editor.commit();

                                    //Starting profile activity
                                    Intent intent = new Intent(LoginActivity.this, FacultyMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    //If the server response is not success
                                    //Displaying an error message on toast
                                    Toast.makeText(LoginActivity.this, "Invalid User ID or Password", Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        //Adding parameters to request
                        params.put(Config.KEY_EMAIL, id);
                        params.put(Config.KEY_PASSWORD, password);

                        //returning parameter
                        return params;
                    }
                };

                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
            else if (type.equals("Student")) {
                Toast.makeText(LoginActivity.this, "Attempting log in", Toast.LENGTH_LONG).show();
                //Creating a string request
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.STD_LOGIN_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //If we are getting success from server
                                if (response.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {
                                    //Creating a shared preference
                                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                    //Creating editor to store values to shared preferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    //Adding values to editor
                                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                    editor.putString(Config.EMAIL_SHARED_PREF, id);
                                    editor.putString(Config.TYPE_SHARED_PREF, type);

                                    //Saving values to editor
                                    editor.commit();

                                    //Starting profile activity
                                    Intent intent = new Intent(LoginActivity.this, StudentMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    //If the server response is not success
                                    //Displaying an error message on toast
                                    Toast.makeText(LoginActivity.this, "Invalid User ID or Password", Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        //Adding parameters to request
                        params.put(Config.KEY_EMAIL, id);
                        params.put(Config.KEY_PASSWORD, password);

                        //returning parameter
                        return params;
                    }
                };

                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
        }
        else
        {
            Toast.makeText(LoginActivity.this, "Please select the portal", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonLogin) {

            login();
            //Intent i = new Intent(Login.this,ToDoActivity.class);

            //startActivity(i);
        }
        if (v.getId() == R.id.linkSignup) {

            int selectedId = radioPreGroup.getCheckedRadioButtonId();

            if (selectedId != -1) {
                radioPreButton = (RadioButton) findViewById(selectedId);
                final String type = radioPreButton.getText().toString();

                //Toast.makeText(LoginActivity.this, type, Toast.LENGTH_LONG).show();

                if (type.equals("Faculty")) {
                    Toast.makeText(LoginActivity.this, "Faculty Registration", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, FacultyRegister.class);
                    startActivity(intent);
                }
                else if (type.equals("Student")) {
                    Toast.makeText(LoginActivity.this, "Student Registration not required. Please login with your register number and given password", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(LoginActivity.this, "Please select the portal to register", Toast.LENGTH_LONG).show();
            }
        }
        if (v.getId() == R.id.help) {

            Toast.makeText(LoginActivity.this, "Help desk", Toast.LENGTH_LONG).show();
            Intent i = new Intent(LoginActivity.this,HelpCenter.class);
            startActivity(i);

        }
        //Calling the login function

    }
}
