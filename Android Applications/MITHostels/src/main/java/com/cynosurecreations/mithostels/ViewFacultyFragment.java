package com.cynosurecreations.mithostels;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewFacultyFragment extends Fragment implements View.OnClickListener {

    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextDesg;
    private EditText editTextGender;
    private EditText editTextContact;
    private Button buttonUpdate;
    private Button buttonDelete;
    private EditText editTextEmail;
    private EditText editTextBlock;
    private EditText editTextPasswd;
    private EditText editTextPasswd2;
    private String id;
    private TextView passwd,blocktext;
    private TextView passwd2;
    private Spinner block,dropdown;
    private String JSON_STRING;

    private String type,profile;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_faculty,null);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        type = sharedPreferences.getString(Config.TYPE_SHARED_PREF,"Not Available");
        profile = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");


        if(type.equals("Faculty"))
        {
            id = ((FacultyMainActivity)getActivity()).facultyid;
        }
        else if(type.equals("Student"))
        {
            id = ((StudentMainActivity)getActivity()).facultyid;
        }


        editTextId = (EditText) view.findViewById(R.id.editTextId);
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextBlock = (EditText) view.findViewById(R.id.editTextBlock);
        editTextDesg = (EditText) view.findViewById(R.id.editTextDesg);
        editTextContact = (EditText) view.findViewById(R.id.editTextContact);
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPasswd = (EditText) view.findViewById(R.id.editTextPasswd);
        editTextGender = (EditText) view.findViewById(R.id.editTextGender);
        buttonUpdate = (Button) view.findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) view.findViewById(R.id.buttonDelete);
        editTextPasswd2 = (EditText) view.findViewById(R.id.editTextPasswd2);
        passwd =(TextView) view.findViewById(R.id.textPasswd);
        passwd2 =(TextView) view.findViewById(R.id.textPasswd2);
        blocktext = (TextView) view.findViewById(R.id.textblock);

        block = (Spinner) view.findViewById(R.id.spinnerBlock);

        dropdown = (Spinner) view.findViewById(R.id.spinner1);
        String[] items = new String[]{"Warden", "Deputy Warden", "Executive warden","Resident Counsellor","Hostel Committee Member"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        block.setVisibility(View.VISIBLE);
        editTextBlock.setVisibility(View.VISIBLE);
        blocktext.setVisibility(View.VISIBLE);

        editTextId.setText(id);

        if(type.equals("Student") || id != profile)
        {
            buttonUpdate.setVisibility(View.GONE);
            editTextId.setFocusable(false);
            editTextName.setFocusable(false);
            editTextGender.setFocusable(false);
            editTextDesg.setVisibility(View.VISIBLE);
            editTextDesg.setFocusable(false);
            editTextBlock.setVisibility(View.VISIBLE);
            editTextBlock.setFocusable(false);
            editTextContact.setFocusable(false);
            editTextEmail.setFocusable(false);
            editTextPasswd.setVisibility(view.GONE);
            editTextPasswd2.setVisibility(view.GONE);
            passwd.setVisibility(view.GONE);
            passwd2.setVisibility(view.GONE);
            dropdown.setVisibility(View.GONE);
            block.setVisibility(View.GONE);
        }
        else
        {
            dropdown.setVisibility(View.VISIBLE);
            block.setVisibility(View.VISIBLE);
            editTextDesg.setVisibility(View.GONE);
            editTextBlock.setVisibility(View.GONE);
        }


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                String temp = dropdown.getSelectedItem().toString().trim();
                if (temp.equals("Resident Counsellor"))
                    block.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        getJSON3();

        return view;
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_EMP,id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String name = c.getString(Config.TAG_NAME);
            String desg = c.getString(Config.TAG_DESG);
            String contact = c.getString(Config.TAG_CONTACT);
            String email = c.getString(Config.TAG_EMAIL);
            String passwd = c.getString(Config.TAG_PASSWD);
            String gender = c.getString(Config.TAG_GENDER);
            String id = c.getString(Config.TAG_ID);
            String blockno = c.getString(Config.TAG_BLOCKNO);

            editTextId.setText(id);
            editTextName.setText(name);
            editTextDesg.setText(desg);
            editTextContact.setText(contact);
            editTextEmail.setText(email);
            editTextPasswd.setText(passwd);
            editTextGender.setText(gender);
            editTextPasswd2.setText(passwd);

            int position = getIndex(block,blockno);
            if(position!=99) {
                block.setSelection(position);
                editTextBlock.setText(block.getItemAtPosition(position).toString());
            }
            else
            {
                block.setVisibility(View.GONE);
                editTextBlock.setVisibility(View.GONE);
                blocktext.setVisibility(View.GONE);
            }

            position = getIndex2(dropdown,desg);
            dropdown.setSelection(position);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int getIndex(Spinner spinner, String myString){

        for (int i=0;i<spinner.getCount();i++){
            String temp = spinner.getItemAtPosition(i).toString();
            int iend = temp.indexOf(" ");
            if (iend != -1)
                temp = temp.substring(0 , iend);
            if (temp.equals(myString)){
                return i;
            }
        }
        return 99;
    }

    private int getIndex2(Spinner spinner, String myString){

        for (int i=0;i<spinner.getCount();i++){
            String temp = spinner.getItemAtPosition(i).toString();
            if (temp.equals(myString)){
                return i;
            }
        }
        return 0;
    }

    private void getJSON3(){
        class GetJSON2 extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Fetching Data","Wait...",false,false);
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
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classids);
            block.setAdapter(adapter1);
            getEmployee();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee()
    {

        boolean cancel = false;
        View focusView = null;

        final String desg = dropdown.getSelectedItem().toString().trim();

        final String contact = editTextContact.getText().toString().trim();

        final String email = editTextEmail.getText().toString().trim();

        final String passwd = editTextPasswd.getText().toString().trim();
        final String passwd2 = editTextPasswd2.getText().toString().trim();

        final String name = editTextName.getText().toString().trim();

        final String id = editTextId.getText().toString().trim();
        final String gender = editTextGender.getText().toString().trim();

        String blocks = block.getSelectedItem().toString().trim();
        String tempblock="";
        int iend = blocks.indexOf(" ");
        if (iend != -1)
            blocks = blocks.substring(0 , iend);

        if (blocks.equals("Select")) {
            if(desg.equals("Resident Counsellor")){
                Toast.makeText(getActivity(), "Block mandatory for RC" , Toast.LENGTH_SHORT).show();
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
        if (TextUtils.isEmpty(name)) {
            editTextName.setError(getString(R.string.error_field_required));
            focusView = editTextName;
            cancel = true;
        } else if (!isNameValid(name)) {
            editTextName.setError(getString(R.string.error_invalid_name));
            focusView = editTextName;
            cancel = true;
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
            Toast.makeText(getActivity(),"Passwords don't match! ", Toast.LENGTH_SHORT).show();
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

        class UpdateEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                ((FacultyMainActivity)getActivity()).facultyid="";
                ((FacultyMainActivity)getActivity()).viewAllFaculties();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_EMP_ID,id);
                hashMap.put(Config.KEY_EMP_NAME,name);
                hashMap.put(Config.KEY_EMP_DESG,desg);
                hashMap.put(Config.KEY_EMP_CONTACT,contact);
                hashMap.put(Config.KEY_EMP_EMAIL,email);
                hashMap.put(Config.KEY_EMP_PASSWD,passwd);
                hashMap.put(Config.KEY_EMP_GENDER,gender);
                hashMap.put(Config.KEY_EMP_BLOCK,blockno);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_EMP,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    private boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
        return name.length() > 4;
    }

    private boolean isContactValid(String contact) {
        //TODO: Replace this with your own logic
        return contact.length() == 10;
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            updateEmployee();
        }

        if(v == buttonDelete){
            //confirmDeleteEmployee();
        }
    }
}