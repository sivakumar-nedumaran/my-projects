package com.cynosurecreations.feras;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.test.mock.MockPackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ViewTBLFragment extends Fragment {

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
        final View view = inflater.inflate(R.layout.view_tbl_layout,null);
        // skype message button click event code here
        ((Button) view.findViewById(R.id.buttonViewSBC)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((PlayerMainActivity)getActivity()).latitude=latitude;
                ((PlayerMainActivity)getActivity()).longitude=longitude;
                ((PlayerMainActivity)getActivity()).viewAreaTeams();


            }
        });

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

        btnShowLocation = (Button) view.findViewById(R.id.button);

        // show location button click event
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

        _latitude = (TextView) view.findViewById(R.id.latitude);
        _longitude = (TextView) view.findViewById(R.id.longitude);
        return view;
    }
}
