package com.cynosurecreations.feras;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewOutpassFragment extends Fragment implements View.OnClickListener {

    private String JSON_STRING;
    private Button btnJointeam;
    private EditText teamname, teamid,description,esport,estate,ecity,leader;
    TextView _latitude, _longitude;
    private String id,caller;
    private String type,profile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_outpass,null);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        type = sharedPreferences.getString(Config.TYPE_SHARED_PREF,"Not Available");
        profile = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        //caller = getArguments().getString("caller");

        teamname = (EditText) view.findViewById(R.id.editTextTeamName);
        teamid = (EditText) view.findViewById(R.id.editTextTeamID);
        estate = (EditText) view.findViewById(R.id.editTextState);
        ecity = (EditText) view.findViewById(R.id.editTextCity);
        esport = (EditText) view.findViewById(R.id.editTextSport);
        leader = (EditText) view.findViewById(R.id.editTextLeader);
        description = (EditText) view.findViewById(R.id.editTextDescription);
        _latitude = (TextView) view.findViewById(R.id.latitude);
        _longitude = (TextView) view.findViewById(R.id.longitude);
        btnJointeam = (Button) view.findViewById(R.id.buttonApprove);

        if(type.equals("Player")) {

            id = ((PlayerMainActivity) getActivity()).outpassid;
        }

        btnJointeam.setOnClickListener(this);

        teamid.setText(id);

        getTeam();

        return view;
    }

    private void getTeam(){
        class GetTeam extends AsyncTask<Void,Void,String>{
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
                //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                showOutpass(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_TEAM,id);
                return s;
            }
        }
        GetTeam ge = new GetTeam();
        ge.execute();
    }

    private void showOutpass(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String tname = c.getString(Config.KEY_TEAM_TEAMNAME);
            String tid = c.getString(Config.KEY_TEAM_TEAMID);
            String tcity = c.getString(Config.KEY_TEAM_CITY);
            String tstate = c.getString(Config.KEY_TEAM_STATE);
            String tsport = c.getString(Config.KEY_TEAM_SPORT);
            String tdesc = c.getString(Config.KEY_TEAM_DESCRIPTION);
            String tlati = c.getString(Config.KEY_TEAM_LATITUDE);
            String tlongi = c.getString(Config.KEY_TEAM_LONGITUDE);
            String tleaderid = c.getString(Config.KEY_TEAM_LEADERID);

            teamname.setText(tname);
            teamname.setFocusable(false);
            teamid.setText(tid);
            teamid.setFocusable(false);
            esport.setText(tsport);
            esport.setFocusable(false);
            estate.setText(tstate);
            estate.setFocusable(false);
            ecity.setText(tcity);
            ecity.setFocusable(false);
            description.setText(tdesc);
            description.setFocusable(false);
            leader.setText(tleaderid);
            leader.setFocusable(false);
            _latitude.setText("Latitude : "+tlati);
            _longitude.setText("Longitude : "+tlongi);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        if(v == btnJointeam){
            ((PlayerMainActivity)getActivity()).playerid=leader.getText().toString().trim();
            ((PlayerMainActivity)getActivity()).viewPlayer();
        }
    }
}