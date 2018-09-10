package com.cynosurecreations.mithostels;

/**
 * Created by Shiva on 23/07/16.
 */
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class RequestOutpassFragment extends Fragment {

    boolean flag;
    private String rcid,blockno,sname,sregno,branch,course,roomno,visitdate,returndate,timeout,returntime,address,nod,contact;
    private String JSON_STRING,type,profile;
    private Spinner rc;
    private Button addPPT,selectRc;
    private EditText name,regno,room,visitdt,returndt,timeot,rettime,visitaddress,nodays,contactph;
    private RadioGroup radioPreGroup;
    private RadioButton radioPreButton;
    private Spinner dropdown,classID;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        flag=false;

        final View view = inflater.inflate(R.layout.fragment_request_outpass,null);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        type = sharedPreferences.getString(Config.TYPE_SHARED_PREF,"Not Available");
        profile = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        addPPT = (Button) view.findViewById(R.id.buttonAddPPT);
        selectRc = (Button) view.findViewById(R.id.buttonSelectRc);

        name = (EditText) view.findViewById(R.id.editTextStdName);
        regno = (EditText) view.findViewById(R.id.editTextStdRegno);
        room = (EditText) view.findViewById(R.id.editTextRoom);
        visitdt = (EditText) view.findViewById(R.id.editTextVisitDate);
        returndt = (EditText) view.findViewById(R.id.editTextReturnDate);
        timeot = (EditText) view.findViewById(R.id.editTextTimeOut);
        rettime = (EditText) view.findViewById(R.id.editTextReturnTime);
        visitaddress = (EditText) view.findViewById(R.id.editTextAddress);
        nodays = (EditText) view.findViewById(R.id.editTextNod);
        contactph = (EditText) view.findViewById(R.id.editTextContactPhone);
        classID = (Spinner) view.findViewById(R.id.spinnerClassID);

        getJSON3();
        radioPreGroup = (RadioGroup) view.findViewById(R.id.radioGroupFileType);

        rc = (Spinner) view.findViewById(R.id.spinnerRc);
        dropdown = (Spinner) view.findViewById(R.id.spinner2);
        String[] items = new String[]{"Aerospace Engineering", "Automobile Engineering", "Instrumentation Engineering","Production Technology","Rubber and Plastic Technology","Electronics Engineering","Computer Technology","Information Technology"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        visitdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showDatePicker(1);
            }
        });

        visitdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(1);
            }
        });

        returndt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showDatePicker(2);
            }
        });

        returndt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(2);
            }
        });

        regno.setText(profile);
        regno.setFocusable(false);

        timeot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(1);
            }
        });

        timeot.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showTimePicker(1);
            }
        });

        rettime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(2);
            }
        });

        rettime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showTimePicker(2);
            }
        });

        selectRc.setVisibility(View.GONE);

        classID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                blockno = classID.getSelectedItem().toString().trim();
                int iend = blockno.indexOf(" ");
                if (iend != -1)
                    blockno = blockno.substring(0 , iend);
                getJSON2();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        addPPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                int hr = c.get(Calendar.HOUR_OF_DAY);
                int min = c.get(Calendar.MINUTE);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);

                String curdate = Integer.toString(year);

                if(month<=9)
                    curdate = curdate + "0" + month;
                else
                    curdate = curdate + month;

                if(day<=9)
                    curdate = curdate + "0" + day;
                else
                    curdate = curdate + day;

                if(hr<=9)
                    curdate = "0" + hr;
                else
                    curdate = curdate + hr;

                if(min<=9)
                    curdate = curdate + "0" + min;
                else
                    curdate = curdate + min;

                int selectedId = radioPreGroup.getCheckedRadioButtonId();
                rcid = rc.getSelectedItem().toString();
                int iend = rcid.indexOf(" ");
                if (iend != -1)
                    rcid = rcid.substring(0 , iend);

                sname = name.getText().toString().trim();
                sregno = regno.getText().toString().trim();
                course = dropdown.getSelectedItem().toString();
                roomno = room.getText().toString().trim();
                visitdate = visitdt.getText().toString().trim();
                returndate = returndt.getText().toString().trim();
                timeout = timeot.getText().toString().trim();
                returntime = rettime.getText().toString().trim();
                address = visitaddress.getText().toString().trim();
                nod = nodays.getText().toString().trim();
                contact = contactph.getText().toString().trim();


                if(selectedId != -1 && rcid!=null && sname!=null && sregno!=null && course!=null && roomno!=null && visitdate!=null && returndate!=null && timeout!=null && returntime!=null && address!=null && nod!=null && contact!=null) {

                    radioPreButton = (RadioButton) view.findViewById(selectedId);
                    branch = radioPreButton.getText().toString().trim();

                    String requestdate = visitdate.substring(6,10)+visitdate.substring(3,5)+visitdate.substring(0,2)+timeout.substring(0,2)+timeout.substring(5,7);
                    //Toast.makeText(getActivity(), requestdate + " : " + curdate, Toast.LENGTH_SHORT).show();

                    if(requestdate.compareTo(curdate)<0) {
                        Toast.makeText(getActivity(), "Sorry! Outpass should be requested min 4 hours before leaving", Toast.LENGTH_SHORT).show();
                    }
                    else if(contact.length()!=10) {
                        Toast.makeText(getActivity(), "Contact number should be 10 digits", Toast.LENGTH_SHORT).show();
                    }
                    else if(flag==false) {


                        addMaterial();

                        flag=true;
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Outpass requested", Toast.LENGTH_LONG).show();
                        //((FacultyMainActivity) getActivity()).AddMaterial();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Please fill in all the details", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public void showTimePicker(int choice) {

        TimePickerFragment newFragment = new TimePickerFragment();
        Calendar c = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("hour", c.get(Calendar.HOUR_OF_DAY));
        args.putInt("min", c.get(Calendar.MINUTE));
        newFragment.setArguments(args);

        if(choice == 1)
        {
            newFragment.setCallBack(ontime1);
        }
        else if(choice == 2)
        {
            newFragment.setCallBack(ontime2);
        }
        newFragment.show(getFragmentManager(), "timePicker");
    }

    TimePickerDialog.OnTimeSetListener ontime1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int min) {

            if(hour<=9 && min<=9)
                timeot.setText("0"+hour+" : 0"+min);
            else if(hour<=9 && min>9)
                timeot.setText("0"+hour+" : "+min);
            else if(hour>9 && min<=9)
                timeot.setText(hour+" : 0"+min);
            else if(hour>9 && min>9)
                timeot.setText(hour+" : "+min);
        }
    };

    TimePickerDialog.OnTimeSetListener ontime2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int min) {

            if(hour<=9 && min<=9)
                rettime.setText("0"+hour+" : 0"+min);
            else if(hour<=9 && min>9)
                rettime.setText("0"+hour+" : "+min);
            else if(hour>9 && min<=9)
                rettime.setText(hour+" : 0"+min);
            else if(hour>9 && min>9)
                rettime.setText(hour+" : "+min);
        }
    };

    private void showDatePicker(int choice) {
        DatePickerFragment date = new DatePickerFragment();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        if(choice == 1)
        {
            date.setCallBack(ondate1);
        }
        else if(choice == 2)
        {
            date.setCallBack(ondate2);
        }
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month,
                              int day) {

            String visit="";
            month = month + 1;
            if(day<=9)
                visit=visit+"0"+Integer.toString(day);
            else
                visit=visit+Integer.toString(day);
            if(month<=9)
                visit=visit+"/0"+Integer.toString(month)+"/";
            else
                visit=visit+"/"+Integer.toString(month)+"/";
            visit = visit + Integer.toString(year);

            visitdt.setText(visit);
        }
    };

    DatePickerDialog.OnDateSetListener ondate2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month,
                              int day) {

            String visit="";
            month = month + 1;
            if(day<=9)
                visit=visit+"0"+Integer.toString(day);
            else
                visit=visit+Integer.toString(day);
            if(month<=9)
                visit=visit+"/0"+Integer.toString(month)+"/";
            else
                visit=visit+"/"+Integer.toString(month)+"/";
            visit = visit + Integer.toString(year);

            returndt.setText(visit);
        }
    };

    private void getJSON2(){
        class GetJSON2 extends AsyncTask<Void,Void,String> {

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
                populateCourses();
            }
        }
        GetJSON2 gj = new GetJSON2();
        gj.execute();
    }

    private void populateCourses(){
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
            rc.setAdapter(adapter2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addMaterial() {

        class AddMaterial extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Adding...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(), s.trim(), Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();

                params.put(Config.KEY_OUT_RCID, rcid);
                params.put(Config.KEY_OUT_SNAME, sname);
                params.put(Config.KEY_OUT_SREGNO, sregno);
                params.put(Config.KEY_OUT_BRANCH, course);
                params.put(Config.KEY_OUT_COURSE, branch);
                params.put(Config.KEY_OUT_ROOM, roomno);
                params.put(Config.KEY_OUT_VISITDT, visitdate);
                params.put(Config.KEY_OUT_RETURNDT, returndate);
                params.put(Config.KEY_OUT_TIMEOUT, timeout);
                params.put(Config.KEY_OUT_RETURNTIME, returntime);
                params.put(Config.KEY_OUT_ADDRESS, address);
                params.put(Config.KEY_OUT_NOD, nod);
                params.put(Config.KEY_OUT_CONTACTNO, contact);
                params.put(Config.KEY_OUT_BLOCKNO, blockno);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_OUTPASS, params);
                return res;
            }
        }

        AddMaterial as = new AddMaterial();
        as.execute();
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

            }
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classids);
            classID.setAdapter(adapter1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                populate();
            }
        }
        GetJSON2 gj = new GetJSON2();
        gj.execute();
    }
}
