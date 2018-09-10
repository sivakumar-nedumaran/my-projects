package com.cynosurecreations.feras;

/**
 * Created by Shiva on 23/07/16.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.HashMap;
import android.test.mock.MockPackageManager;

public class CreateTeamFragment extends Fragment {

    boolean flag;
    private String city, state, tname, tid, sport, tdesc, leaderid, lati, longi;
    private String JSON_STRING,type,profile;
    private Spinner spinnercity;
    private Button addteam, showformteam;
    private EditText teamname, teamid,description;
    private Spinner spinnersport, spinnerstate;
    private LinearLayout team;
    TextView _latitude, _longitude;

    Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
    // GPSTracker class
    GPSTracker gps;
    double latitude,longitude;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        flag=false;

        final View view = inflater.inflate(R.layout.fragment_request_outpass,null);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        type = sharedPreferences.getString(Config.TYPE_SHARED_PREF,"Not Available");
        profile = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        if(type.equals("Player"))
        {
            leaderid = ((PlayerMainActivity)getActivity()).playerid;
        }

        Toast.makeText(getActivity(), "You are creating team", Toast.LENGTH_LONG).show();

        addteam = (Button) view.findViewById(R.id.buttonAddTeam);
        showformteam = (Button) view.findViewById(R.id.buttonShowFormTeam);
        teamname = (EditText) view.findViewById(R.id.editTextTeamName);
        teamid = (EditText) view.findViewById(R.id.editTextTeamID);
        description = (EditText) view.findViewById(R.id.editTextDescription);
        spinnerstate = (Spinner) view.findViewById(R.id.spinnerState);
        team = (LinearLayout) view.findViewById(R.id.team);
        spinnercity = (Spinner) view.findViewById(R.id.spinnerCity);
        btnShowLocation = (Button) view.findViewById(R.id.button);
        _latitude = (TextView) view.findViewById(R.id.latitude);
        _longitude = (TextView) view.findViewById(R.id.longitude);

        //spinnerstate = (Spinner) view.findViewById(R.id.spinner1);
        String[] items = new String[]{"Andhra_Pradesh", "Arunachal_Pradesh","Assam","Bihar","Chhattisgarh","Dadra_&_Nagar_Haveli", "Daman_&_Diu", "Delhi","Goa","Gujarat","Haryana", "Himachal_Pradesh", "Jammu_and_Kashmir","Jharkhand","Karnataka","Kerala", "Lakshadweep", "Madhya_Pradesh","Maharashtra","Manipur","Meghalaya", "Mizoram", "Nagaland","Orissa","Pondicherry", "Punjab", "Rajasthan","Sikkim","Tamil_Nadu","Tripura", "Uttar_Pradesh", "Uttarakhand"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnerstate.setAdapter(adapter);

        spinnersport = (Spinner) view.findViewById(R.id.sport);
        String[] items2 = new String[]{"Cricket","Volley Ball","Basket_Ball","Yoga","Swimming","Badminton"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items2);
        spinnersport.setAdapter(adapter2);

        team.setVisibility(View.GONE);

        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(getActivity());

                // check if GPS enabled
                if(gps.canGetLocation()){

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    // \n is for new line
                    //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                    _latitude.setText("Latitude: " + latitude);
                    _longitude.setText("Longitude: " + longitude);
                    _latitude.setVisibility(View.VISIBLE);
                    _longitude.setVisibility(View.VISIBLE);
                    //btnShowLocation.setVisibility(View.GONE);

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });

        spinnerstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                state = spinnerstate.getSelectedItem().toString().trim();
                //Toast.makeText(PlayerRegister.this, state , Toast.LENGTH_SHORT).show();
                getJSON2();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        //leaderid = profile;

        //showformteam.setVisibility(View.GONE);

        addteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddTeam();

            }
        });

        showformteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                team.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void AddTeam()
    {
        city = spinnercity.getSelectedItem().toString();
        tname = teamname.getText().toString().trim();
        tid = teamid.getText().toString().trim();
        sport = spinnersport.getSelectedItem().toString();
        tdesc = description.getText().toString().trim();
        String latit = _latitude.getText().toString().trim();
        String longit = _longitude.getText().toString().trim();
        lati = latit.substring(latit.indexOf(" ")+1);
        longi = longit.substring(longit.indexOf(" ")+1);


        if(city !=null && tname !=null && tid !=null && lati!=null && longi!=null && tdesc !=null && state != null && sport != null) {

            if(flag==false) {

                //Toast.makeText(getActivity(), , Toast.LENGTH_LONG).show();
                //addTeam();

                class AddTeam extends AsyncTask<Void,Void,String>{

                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(getActivity(),"Adding...","Wait...",false,false);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected String doInBackground(Void... v) {
                        HashMap<String,String> params = new HashMap<>();
                        params.put(Config.KEY_TEAM_CITY, city);
                        params.put(Config.KEY_TEAM_TEAMNAME, tname);
                        params.put(Config.KEY_TEAM_TEAMID, tid);
                        params.put(Config.KEY_TEAM_SPORT, sport);
                        params.put(Config.KEY_TEAM_DESCRIPTION, tdesc);
                        params.put(Config.KEY_TEAM_STATE, state);
                        params.put(Config.KEY_TEAM_LATITUDE, lati);
                        params.put(Config.KEY_TEAM_LONGITUDE, longi);
                        params.put(Config.KEY_TEAM_LEADERID, leaderid);

                        RequestHandler rh = new RequestHandler();
                        String res = rh.sendPostRequest(Config.URL_ADD_TEAM, params);
                        return res;
                    }
                }

                AddTeam ae = new AddTeam();
                ae.execute();

                flag=true;
            }
            else
            {
                Toast.makeText(getActivity(), "Team has already been created!", Toast.LENGTH_LONG).show();
                //((PlayerMainActivity) getActivity()).AddTeam();
            }
        }
        else
        {
            Toast.makeText(getActivity(), "Please fill in all the details", Toast.LENGTH_LONG).show();
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
                String s = rh.sendGetRequestParam(Config.URL_GET_CITIES,state);
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

            classids.add("Select your city");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                String cityname = jo.getString(Config.TAG_CITYNAME);

                if (!classids.contains(cityname)) {
                    classids.add(cityname);
                }
                //Toast.makeText(getActivity(), cityname , Toast.LENGTH_SHORT).show();


            }
            //String[] items = new String[]{"Professor", "Associate Professor", "Assistant Professor (Sr.Gr)","Assistant Professor","Teaching Fellow"};
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classids);
            spinnercity.setAdapter(adapter1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
