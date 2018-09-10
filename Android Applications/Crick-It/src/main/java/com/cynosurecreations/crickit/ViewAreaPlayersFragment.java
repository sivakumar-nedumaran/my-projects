package com.cynosurecreations.feras;

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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewAreaPlayersFragment extends Fragment implements ListView.OnItemClickListener {

    private ListView listView;
    private String JSON_STRING, type, playerid, caller;
    private double latitude,longitude;
    //private ImageLoader imageLoader;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_all_faculties,null);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        //NetworkImageView
        //NetworkImageView networkImageView = new NetworkImageView(getContext());

        //Initializing ImageLoader
        //imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        //imageLoader.get(images.get(position), ImageLoader.getImageListener(networkImageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        //Setting the image url to load
        //networkImageView.setImageUrl(images.get(position),imageLoader);

        latitude = getArguments().getDouble("latitude");
        longitude = getArguments().getDouble("longitude");

        Toast.makeText(getActivity(), "Your Location is - \nLat: " + Double.toString(latitude) + "\nLong: " + Double.toString(longitude), Toast.LENGTH_LONG).show();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        type = sharedPreferences.getString(Config.TYPE_SHARED_PREF,"Not Available");

        if(type.equals("Player"))
        {
            playerid = ((PlayerMainActivity)getActivity()).profileid;
            getJSON();
        }

        return view;
    }
    /*private void showEmployee1(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            //Toast.makeText(getActivity(), JSON_STRING, Toast.LENGTH_LONG).show();
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                String id = jo.getString(Config.TAG_ID);
                String distance = jo.getString(Config.TAG_DIST);
                //String dist = distance.substring(1,6);
                String name = jo.getString(Config.TAG_NAME);
                String city = jo.getString(Config.TAG_CITY);

                Toast.makeText(getActivity(), id + " " + name + " " + city + " " + distance, Toast.LENGTH_LONG).show();


                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID, id);
                employees.put(Config.TAG_NAME,"Player name : " + name);
                employees.put(Config.TAG_CITY, city + ", " + distance.substring(0,distance.indexOf('.')+3) + " miles away");
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list, R.layout.list_item,
                new String[]{Config.TAG_ID, Config.TAG_NAME, Config.TAG_CITY},
                new int[]{R.id.id, R.id.name, city});

        listView.setAdapter(adapter);
        Toast.makeText(getActivity(), "Success" , Toast.LENGTH_LONG).show();
    }*/

    private void showEmployee1(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_ID);
                String name = jo.getString(Config.TAG_NAME);
                String city = jo.getString(Config.TAG_CITY);
                String dist = jo.getString(Config.TAG_DIST);
                //String imgurl = jo.getString(Config.TAG_IMGURL);
                double distan = Double.parseDouble(dist);
                //Toast.makeText(getActivity(), String.format("%.3g%n", distan), Toast.LENGTH_LONG).show();


                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,id);
                employees.put(Config.TAG_NAME,"Player : "+name);
                employees.put(Config.TAG_CITY,"Lives in "+city);
                employees.put(Config.TAG_AWAY,"Distance : " +String.format("%.3g", distan) + " miles away");
                //employees.put(Config.TAG_IMGURL,imgurl);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list, R.layout.list_item,
                new String[]{Config.TAG_ID,Config.TAG_NAME,Config.TAG_CITY,Config.TAG_AWAY},
                new int[]{R.id.id, R.id.name, R.id.city, R.id.away});

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
                //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                showEmployee1();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest2Param(Config.URL_GET_NEARBY_PLAYERS, Double.toString(latitude), Double.toString(longitude));
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(Config.TAG_ID).toString();
        //empId = empId.substring(empId.indexOf(' '+1),empId.indexOf('-')-1);
        Toast.makeText(getActivity(), empId, Toast.LENGTH_LONG).show();


        if(type.equals("Player"))
        {
            ((PlayerMainActivity)getActivity()).playerid=empId;
            ((PlayerMainActivity)getActivity()).viewPlayer();
        }
    }
}
