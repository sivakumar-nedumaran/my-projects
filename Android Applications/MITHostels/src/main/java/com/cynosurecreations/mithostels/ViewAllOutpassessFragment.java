package com.cynosurecreations.mithostels;

import android.app.ProgressDialog;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewAllOutpassessFragment extends Fragment implements ListView.OnItemClickListener {

    private ListView listView;
    private String JSON_STRING, type, regno, caller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_all_faculties,null);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        caller = getArguments().getString("caller");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        type = sharedPreferences.getString(Config.TYPE_SHARED_PREF,"Not Available");

        if(type.equals("Student"))
        {
            regno = ((StudentMainActivity)getActivity()).profileid;
            getJSON();
        }
        else if(type.equals("Faculty"))
        {
            regno = ((FacultyMainActivity)getActivity()).profileid;
            getJSON2();
        }

        return view;
    }
    private void showEmployee1(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                String id = jo.getString(Config.TAG_OUT_ID);
                String approval = jo.getString(Config.TAG_OUT_APPROVAL);
                String visitdate = jo.getString(Config.TAG_OUT_VISITDATE);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,"Outpass ID : " + id);
                employees.put(Config.TAG_NAME, "Visit date: " + visitdate + " - Approval: " + approval);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list, R.layout.list_item,
                new String[]{Config.TAG_ID, Config.TAG_NAME},
                new int[]{R.id.id, R.id.name});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee1();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_STD_OUTPASSES,regno);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void getJSON2(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee2();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s="";
                if(caller.equals("approve"))
                {
                    s = rh.sendGetRequestParam(Config.URL_GET_APP_OUTPASSES,regno);
                }
                else if(caller.equals("faculty"))
                {
                    s = rh.sendGetRequestParam(Config.URL_GET_RC_OUTPASSES,regno);
                }
                else if(caller.equals("denied"))
                {
                    s = rh.sendGetRequestParam(Config.URL_GET_DENIED_OUTPASSES,regno);
                }
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showEmployee2(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                String id = jo.getString(Config.TAG_OUT_ID);
                String name = jo.getString(Config.TAG_OUT_NAME);
                String visitdate = jo.getString(Config.TAG_OUT_VISITDATE);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,"Outpass ID : " + id);
                employees.put(Config.TAG_NAME, "Name: " + name + " - Visit date: " + visitdate);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list, R.layout.list_item,
                new String[]{Config.TAG_ID, Config.TAG_NAME},
                new int[]{R.id.id, R.id.name});

        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(Config.TAG_ID).toString();
        empId = empId.substring(13);

        if(type.equals("Faculty"))
        {
            ((FacultyMainActivity)getActivity()).outpassid=empId;
            if(caller.equals("approve"))
            {
                ((FacultyMainActivity)getActivity()).approveOutpass();
            }
            else if(caller.equals("faculty"))
            {
                ((FacultyMainActivity)getActivity()).viewOutpass();
            }
            else if(caller.equals("denied"))
            {
                ((FacultyMainActivity)getActivity()).approveDeniedOutpass();
            }
        }
        else if(type.equals("Student"))
        {
            ((StudentMainActivity)getActivity()).outpassid=empId;
            ((StudentMainActivity)getActivity()).viewOutpass();
        }

    }
}
