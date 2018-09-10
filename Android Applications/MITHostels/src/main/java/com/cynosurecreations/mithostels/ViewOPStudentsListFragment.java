package com.cynosurecreations.mithostels;

import android.content.Context;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewOPStudentsListFragment extends Fragment implements ListView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING, type, regno, caller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_all_faculties,null);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        JSON_STRING = getArguments().getString("JSON");
        caller = getArguments().getString("caller");
        //Toast.makeText(getActivity(), caller, Toast.LENGTH_LONG).show();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        type = sharedPreferences.getString(Config.TYPE_SHARED_PREF,"Not Available");

        showEmployee2();
        return view;
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
                String room = jo.getString(Config.TAG_OUT_ROOM);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,"Outpass ID : " + id);
                employees.put(Config.TAG_NAME, "Room: " + room + " - Name: " + name);
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

        //Toast.makeText(getActivity(), empId, Toast.LENGTH_LONG).show();

        if(caller.equals("viewop"))
        {
            ((FacultyMainActivity)getActivity()).outpassid=empId;
            ((FacultyMainActivity)getActivity()).viewOutpass();
        }
        else if(caller.equals("denied"))
        {
            ((FacultyMainActivity)getActivity()).outpassid=empId;
            ((FacultyMainActivity)getActivity()).approveDeniedOutpass();
        }

    }
}
