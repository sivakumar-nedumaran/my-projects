package com.cynosurecreations.mithostels;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FacultyRegister extends AppCompatActivity implements View.OnClickListener{

    //Defining views
    private EditText editTextName;
    private EditText editTextContact;
    private EditText editTextID;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private RadioGroup radioPreGroup;
    private RadioButton radioPreButton;
    private Button buttonAdd;
    private EditText editTextEmail;
    private EditText editTextPasswd;
    private EditText editTextPasswd2;
    private EditText editTextFacregpass;
    private Spinner dropdown,block;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_register);
        //Initializing views
        editTextID = (EditText) findViewById(R.id.editTextID);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextContact = (EditText) findViewById(R.id.editTextContact);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPasswd = (EditText) findViewById(R.id.editTextPasswd);
        editTextPasswd2 = (EditText) findViewById(R.id.editTextPasswd2);
        editTextFacregpass = (EditText) findViewById(R.id.editTextFacregpass);
        radioSexGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioPreGroup=(RadioGroup)findViewById(R.id.radioGroupPrefix);
        block = (Spinner)findViewById(R.id.spinnerBlock);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Warden", "Deputy Warden", "Executive warden","Resident Counsellor","Hostel Committee Member"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        getJSON2();

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
    }

    private void getJSON2(){
        class GetJSON2 extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FacultyRegister.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_BLOCKS);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                //Toast.makeText(getActivity(), JSON_STRING , Toast.LENGTH_SHORT).show();
                populate();
            }
        }
        GetJSON2 gj = new GetJSON2();
        gj.execute();
    }

    private void populate(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            ArrayList<String> classids = new ArrayList<String>();

            classids.add("Select your block");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                String blockno = jo.getString(Config.TAG_BLOCKNO);
                String blockname = jo.getString(Config.TAG_BLOCKNAME);

                if (!classids.contains(blockno + " - " + blockname)) {
                    classids.add(blockno + " - " + blockname);
                }
                //Toast.makeText(getActivity(), classid + " " + course , Toast.LENGTH_SHORT).show();


            }
            //String[] items = new String[]{"Professor", "Associate Professor", "Assistant Professor (Sr.Gr)","Assistant Professor","Teaching Fellow"};
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(FacultyRegister.this, android.R.layout.simple_spinner_dropdown_item, classids);
            block.setAdapter(adapter1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //Adding an employee
    private void addEmployee(){

        boolean cancel = false;
        View focusView = null;

        final String desg = dropdown.getSelectedItem().toString();

        final String contact = editTextContact.getText().toString().trim();
        final String facregpass = editTextFacregpass.getText().toString().trim();

        final String email = editTextEmail.getText().toString().trim();

        String blocks = block.getSelectedItem().toString().trim();
        int iend = blocks.indexOf(" ");
        if (iend != -1)
            blocks = blocks.substring(0 , iend);

        //final String blockno = blocks;

        final String passwd = editTextPasswd.getText().toString().trim();
        final String passwd2 = editTextPasswd2.getText().toString().trim();

        final String name;
        String tempblock="";
        String temp_name = editTextName.getText().toString().trim();

        final String id = editTextID.getText().toString().trim();
        final String gender;

        if (TextUtils.isEmpty(temp_name)) {
            editTextName.setError(getString(R.string.error_field_required));
            focusView = editTextName;
            cancel = true;
        } else if (!isNameValid(temp_name)) {
            editTextName.setError(getString(R.string.error_invalid_name));
            focusView = editTextName;
            cancel = true;
        }

        if (blocks.equals("Select")) {
            if(desg.equals("Resident Counsellor")){
                Toast.makeText(this, "Block mandatory for RC" , Toast.LENGTH_SHORT).show();
                focusView = block;
                cancel = true;
            }
            else{
                tempblock = "";//nothing done
            }
        }
        else
        {
            if(!desg.equals("Resident Counsellor")){
                tempblock = "";
            }
            else{
                tempblock = blocks;
            }
        }

        final String blockno = tempblock;

        if (TextUtils.isEmpty(facregpass)) {
            editTextFacregpass.setError(getString(R.string.error_field_required));
            focusView = editTextFacregpass;
            cancel = true;
        }

        int selectedId2=radioPreGroup.getCheckedRadioButtonId();
        if(selectedId2!=-1) {
            radioPreButton = (RadioButton) findViewById(selectedId2);
            name = radioPreButton.getText().toString() + " " + temp_name;
        }
        else
        {
            focusView = radioPreGroup;
            cancel = true;
            Toast.makeText(FacultyRegister.this,"Please select your honorific",Toast.LENGTH_LONG).show();
            name = "name";
        }

        int selectedId=radioSexGroup.getCheckedRadioButtonId();
        if(selectedId!=-1) {
            radioSexButton = (RadioButton) findViewById(selectedId);
            gender = radioSexButton.getText().toString();
        }
        else
        {
            focusView = radioSexGroup;
            cancel = true;
            Toast.makeText(FacultyRegister.this,"Please select your gender",Toast.LENGTH_LONG).show();
            gender = "gender";
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError(getString(R.string.error_field_required));
            focusView = editTextEmail;
            cancel = true;

        } else if (!isEmailValid(email)) {
            editTextEmail.setError(getString(R.string.error_invalid_email));
            focusView = editTextEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(contact)) {
            editTextContact.setError(getString(R.string.error_field_required));
            focusView = editTextContact;
            cancel = true;
        } else if (!isContactValid(contact)) {
            editTextContact.setError(getString(R.string.error_invalid_contact));
            focusView = editTextContact;
            cancel = true;
        }

        if (!isPasswordValid(passwd)) {
            editTextPasswd.setError(getString(R.string.error_invalid_password));
            focusView = editTextPasswd;
            editTextPasswd.setText("");
            editTextPasswd2.setText("");
            cancel = true;
        }

        if(!passwd.equals(passwd2)) {
            //popup msg
            Toast.makeText(FacultyRegister.this,"Passwords don't match! ", Toast.LENGTH_SHORT).show();
            editTextPasswd.setText("");
            editTextPasswd2.setText("");
            focusView = editTextPasswd;
            cancel = true;
        }

        if (cancel)
        {
            focusView.requestFocus();
            return;
        }

        class AddEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FacultyRegister.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(FacultyRegister.this,s,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(FacultyRegister.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_NAME,name);
                params.put(Config.KEY_EMP_DESG,desg);
                params.put(Config.KEY_EMP_CONTACT,contact);
                params.put(Config.KEY_EMP_BLOCK,blockno);
                params.put(Config.KEY_EMP_EMAIL,email);
                params.put(Config.KEY_EMP_FACREGPASS,facregpass);
                params.put(Config.KEY_EMP_PASSWD,passwd);
                params.put(Config.KEY_EMP_GENDER,gender);
                params.put(Config.KEY_EMP_ID,id);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }
    private boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
        return name.length() > 5;
    }

    private boolean isContactValid(String contact) {
        //TODO: Replace this with your own logic
        return contact.length() == 10;
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addEmployee();
        }
    }
}
