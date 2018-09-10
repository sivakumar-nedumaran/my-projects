package com.cynosurecreations.mithostels;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class ViewOPStudentsFragment extends Fragment {


    private EditText editTextDate;
    boolean flag;
    private Spinner classID,course;
    private String blockno,bno,rc,cdate;
    private String JSON_STRING;
    private LinearLayout studentlist;
    private TextView dov;
    private Button button;
    private String type, regno, caller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        flag=false;
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_view_atd,null);
        editTextDate = (EditText) view.findViewById(R.id.editTextDate);
        studentlist = (LinearLayout) view.findViewById(R.id.attendance);
        button = (Button) view.findViewById(R.id.buttonAttend);
        classID = (Spinner) view.findViewById(R.id.spinnerClassID);
        course = (Spinner) view.findViewById(R.id.spinnerCourse);
        dov = (TextView) view.findViewById(R.id.dov);

        caller = getArguments().getString("caller");

        if(caller.equals("denied"))
        {
            classID.setVisibility(View.GONE);
            course.setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.blocktt)).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.rctt)).setVisibility(View.GONE);
            editTextDate.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            dov.setText("Date of visit");
            button.setText("View Denied Outpasses");
        }
        else if(caller.equals("viewop"))
        {
            course.setVisibility(View.GONE);
            editTextDate.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            getJSON2();
        }

        editTextDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showDatePicker();
            }
        });

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        classID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                blockno = classID.getSelectedItem().toString().trim();
                int iend = blockno.indexOf(" ");
                if (iend != -1)
                    blockno = blockno.substring(0 , iend);
                //Toast.makeText(getActivity(), blockno , Toast.LENGTH_SHORT).show();
                course.setVisibility(View.VISIBLE);
                editTextDate.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                getJSON();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String regno = ((EditText) view.findViewById(R.id.editTextID)).getText().toString().trim();

                if(flag==false) {

                    if(caller.equals("denied"))
                    {
                        rc = ((FacultyMainActivity)getActivity()).profileid;
                        cdate = editTextDate.getText().toString().trim();

                    }
                    else if(caller.equals("viewop"))
                    {
                        bno = blockno;
                        rc = course.getSelectedItem().toString().trim();
                        cdate = editTextDate.getText().toString().trim();
                        int iend = rc.indexOf(" ");
                        if (iend != -1)
                            rc = rc.substring(0 , iend);
                    }
                    studentlist.setVisibility(view.VISIBLE);
                    getJSON3();
                    flag=true;
                }
            }
        });

        return view;
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month,
                              int day) {

            month = month + 1;
            editTextDate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }
    };

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Fetching Data","Wait...",false,false);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_RCS,blockno);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                populate2();
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void populate2(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            ArrayList<String> courses = new ArrayList<String>();

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                String facid = jo.getString(Config.TAG_FACID);
                String name = jo.getString(Config.TAG_FACNAME);

                if (!courses.contains(facid)) {
                    courses.add(facid + " - " + name);
                }

            }
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, courses);
            course.setAdapter(adapter2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            classID.setAdapter(adapter1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON2(){
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

    private void getJSON3(){
        class GetJSON3 extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Fetching Data","Wait...",false,false);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s="";
                if(caller.equals("denied"))
                {
                    s = rh.sendGetRequest2Param(Config.URL_GET_DENIED_OUTPASSES,rc,cdate);
                }
                else if(caller.equals("viewop"))
                {
                    s = rh.sendGetRequest3Params(Config.URL_GET_ABS,bno,rc,cdate);
                }
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                //Toast.makeText(getActivity(), JSON_STRING , Toast.LENGTH_SHORT).show();
                viewabs();
            }
        }
        GetJSON3 gj = new GetJSON3();
        gj.execute();
    }

    private void viewabs(){
        ((FacultyMainActivity)getActivity()).viewOPStudents(JSON_STRING,caller);
    }


}
