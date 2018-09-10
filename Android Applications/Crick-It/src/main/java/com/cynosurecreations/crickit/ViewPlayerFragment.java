package com.cynosurecreations.feras;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.test.mock.MockPackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.cynosurecreations.feras.R.id.buttonAdd;
import static com.cynosurecreations.feras.R.id.buttonUpload;
import static com.cynosurecreations.feras.R.id.editTextID;
import static com.cynosurecreations.feras.R.id.latitude;
import static com.cynosurecreations.feras.R.id.longitude;

public class ViewPlayerFragment extends Fragment implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextContact;
    private EditText editTextEmail;
    private EditText editTextPasswd;
    private EditText editTextPasswd2;
    private EditText editTextPincode;
    private EditText editTextSkypeID;
    private Spinner state, city, spinnersport;
    private String JSON_STRING;
    private String statename,cityname;
    private EditText editTextId;
    private EditText editTextState;
    private EditText editTextGender;
    private Button buttonUpdate;
    private Button buttonChat;
    private EditText editTextCity;
    private EditText editTextSport;
    private String id;
    private TextView passwd;
    private TextView passwd2;
    private TextView contact;
    private TextView skypeid;
    private TextView editTextImage;
    private Button buttonChoose;
    private ImageView imageView;
    private String imgurl;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    private String type,profile;
    TextView _latitude, _longitude;
    Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    // GPSTracker class
    GPSTracker gps;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_player,null);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        type = sharedPreferences.getString(Config.TYPE_SHARED_PREF,"Not Available");
        profile = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");


        if(type.equals("Player"))
        {
            id = ((PlayerMainActivity)getActivity()).playerid;
        }
        //id = "shiva5796";
        Toast.makeText(getActivity(), "You are viewing "+id , Toast.LENGTH_LONG).show();


        editTextId = (EditText) view.findViewById(R.id.editTextId);
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextCity = (EditText) view.findViewById(R.id.editTextCity);
        editTextState = (EditText) view.findViewById(R.id.editTextState);
        editTextContact = (EditText) view.findViewById(R.id.editTextContact);
        editTextPincode = (EditText) view.findViewById(R.id.editTextPincode);
        editTextSkypeID = (EditText) view.findViewById(R.id.editTextSkypeID);
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPasswd = (EditText) view.findViewById(R.id.editTextPasswd);
        editTextGender = (EditText) view.findViewById(R.id.editTextGender);
        buttonUpdate = (Button) view.findViewById(R.id.buttonUpdate);
        buttonChat = (Button) view.findViewById(R.id.buttonChat);
        editTextPasswd2 = (EditText) view.findViewById(R.id.editTextPasswd2);
        passwd =(TextView) view.findViewById(R.id.textPasswd);
        passwd2 =(TextView) view.findViewById(R.id.textPasswd2);
        contact =(TextView) view.findViewById(R.id.textContact);
        skypeid =(TextView) view.findViewById(R.id.textSkypeID);
        _latitude = (TextView) view.findViewById(latitude);
        _longitude = (TextView) view.findViewById(longitude);
        btnShowLocation = (Button) view.findViewById(R.id.button);
        buttonChoose = (Button) view.findViewById(R.id.buttonChoose);
        //buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        editTextImage = (TextView) view.findViewById(R.id.editTextImage);
        editTextSport = (EditText) view.findViewById(R.id.editTextSport);

        //blocktext = (TextView) view.findViewById(R.id.textblock);

        city = (Spinner) view.findViewById(R.id.spinnerCity);

        state = (Spinner) view.findViewById(R.id.spinnerState);
        String[] items = new String[]{"Select your state","Andhra_Pradesh", "Arunachal_Pradesh","Assam","Bihar","Chhattisgarh","Dadra_&_Nagar_Haveli", "Daman_&_Diu", "Delhi","Goa","Gujarat","Haryana", "Himachal_Pradesh", "Jammu_and_Kashmir","Jharkhand","Karnataka","Kerala", "Lakshadweep", "Madhya_Pradesh","Maharashtra","Manipur","Meghalaya", "Mizoram", "Nagaland","Orissa","Pondicherry", "Punjab", "Rajasthan","Sikkim","Tamil_Nadu","Tripura", "Uttar_Pradesh", "Uttarakhand"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        state.setAdapter(adapter);

        spinnersport = (Spinner) view.findViewById(R.id.sport);
        String[] items2 = new String[]{"Select your sport","Cricket","Volley_Ball","Basket_Ball","Yoga","Swimming","Badminton"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items2);
        spinnersport.setAdapter(adapter2);

        buttonUpdate.setOnClickListener(this);
        buttonChat.setOnClickListener(this);

        //city.setVisibility(View.VISIBLE);
        //editTextCity.setVisibility(View.VISIBLE);
        //blocktext.setVisibility(View.VISIBLE);

        editTextId.setText(id);
        editTextId.setFocusable(false);

        if(id != profile)
        {
            buttonUpdate.setVisibility(View.GONE);
            editTextId.setFocusable(false);
            editTextName.setFocusable(false);
            editTextGender.setFocusable(false);
            editTextState.setVisibility(View.VISIBLE);
            editTextState.setFocusable(false);
            editTextCity.setVisibility(View.VISIBLE);
            editTextCity.setFocusable(false);
            editTextEmail.setFocusable(false);
            editTextPasswd.setVisibility(view.GONE);
            editTextPasswd2.setVisibility(view.GONE);
            passwd.setVisibility(view.GONE);
            btnShowLocation.setVisibility(view.GONE);
            passwd2.setVisibility(view.GONE);
            contact.setVisibility(view.GONE);
            skypeid.setVisibility(view.GONE);
            state.setVisibility(View.GONE);
            editTextImage.setVisibility(View.GONE);
            buttonChoose.setVisibility(View.GONE);
            city.setVisibility(View.GONE);
            spinnersport.setVisibility(View.GONE);
            editTextSkypeID.setVisibility(View.GONE);
            editTextContact.setVisibility(View.GONE);
        }
        else
        {
            state.setVisibility(View.VISIBLE);
            city.setVisibility(View.VISIBLE);
            editTextImage.setVisibility(View.VISIBLE);
            buttonChoose.setVisibility(View.VISIBLE);
            editTextState.setVisibility(View.GONE);
            editTextCity.setVisibility(View.GONE);
            editTextSport.setVisibility(View.GONE);
            buttonChat.setVisibility(View.GONE);
            btnShowLocation.setVisibility(view.VISIBLE);
        }


        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                statename = state.getSelectedItem().toString().trim();
                //Toast.makeText(getActivity(), statename , Toast.LENGTH_SHORT).show();
                getJSON2();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

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



        buttonChoose.setOnClickListener(this);
        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(getActivity());

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // \n is for new line
                    //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                    _latitude.setText("Latitude: " + latitude);
                    _longitude.setText("Longitude: " + longitude);

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });



        getPlayer();

        return view;
    }

    private void getPlayer(){
        class GetPlayer extends AsyncTask<Void,Void,String>{
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
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_PLAYER,id);
                return s;
            }
        }
        GetPlayer ge = new GetPlayer();
        ge.execute();
    }

    private void showEmployee(String json){



        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            //Toast.makeText(getActivity(), json , Toast.LENGTH_SHORT).show();

            String name = c.getString(Config.TAG_NAME);
            String sport = c.getString(Config.TAG_SPORT);
            statename = c.getString(Config.TAG_STATE);
            String contact = c.getString(Config.TAG_CONTACT);
            String email = c.getString(Config.TAG_EMAIL);
            String passwd = c.getString(Config.TAG_PASSWD);
            String gender = c.getString(Config.TAG_GENDER);
            String id = c.getString(Config.TAG_ID);
            cityname = c.getString(Config.TAG_CITY);
            String skypeid = c.getString(Config.TAG_SKYPEID);
            imgurl = c.getString(Config.TAG_IMGURL);
            String pincode = c.getString(Config.TAG_PINCODE);
            String latitude = c.getString(Config.TAG_LATITUDE);
            String longitude = c.getString(Config.TAG_LONGITUDE);

            editTextId.setText(id);
            editTextName.setText(name);
            editTextState.setText(statename);
            editTextContact.setText(contact);
            editTextEmail.setText(email);
            editTextPasswd.setText(passwd);
            editTextSkypeID.setText(skypeid);
            editTextPincode.setText(pincode);
            editTextGender.setText(gender);
            editTextPasswd2.setText(passwd);
            editTextState.setText(statename);
            editTextCity.setText(cityname);
            editTextSport.setText(sport);
            //imageView.setImageURI(imgurl);
            _latitude.setText("Latitude: "+latitude);
            _longitude.setText("Longitude: "+longitude);




            int position = getIndex(state,statename);
            state.setSelection(position);

            int position2 = getIndex(spinnersport,sport);
            spinnersport.setSelection(position2);

            getJSON2();



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int getIndex(Spinner spinner, String myString){

        for (int i=0;i<spinner.getCount();i++){
            String temp = spinner.getItemAtPosition(i).toString();
            /*int iend = temp.indexOf(" ");
            if (iend != -1)
                temp = temp.substring(0 , iend);*/
            if (temp.equals(myString)){
                return i;
            }
        }
        return 0;
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
                String s = rh.sendGetRequestParam(Config.URL_GET_CITIES,statename);
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
            city.setAdapter(adapter1);

            int position = getIndex2(city,cityname);
            city.setSelection(position);
            //editTextCity.setText(city.getItemAtPosition(position).toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }
        new DownloadImageTask(imageView).execute(imgurl);*/
        /*URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        imageView.setImageBitmap(bmp);*/
        //Toast.makeText(getActivity(), imgurl, Toast.LENGTH_LONG).show();
        new ImageLoadTask(imgurl, imageView).execute();
    }

    private void updateEmployee()
    {

        boolean cancel = false;
        View focusView = null;

        final String statename = state.getSelectedItem().toString();
        final String sport = spinnersport.getSelectedItem().toString();
        final String contact = editTextContact.getText().toString().trim();
        final String pincode = editTextPincode.getText().toString().trim();
        final String skypeid = editTextSkypeID.getText().toString().trim();
        //final String sport = editTextSkypeID.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String imgname = editTextImage.getText().toString().trim();
        final String cityname = city.getSelectedItem().toString().trim();


        String lati = _latitude.getText().toString().trim();
        String longi = _longitude.getText().toString().trim();
        final String latitude = lati.substring(lati.indexOf(" ")+1);
        final String longitude = longi.substring(longi.indexOf(" ")+1);

        //Toast.makeText(PlayerRegister.this, latitude + " " + longitude , Toast.LENGTH_SHORT).show();

        //final String blockno = blocks;

        final String passwd = editTextPasswd.getText().toString().trim();
        final String passwd2 = editTextPasswd2.getText().toString().trim();

        final String name = editTextName.getText().toString().trim();

        final String id = editTextId.getText().toString().trim();
        final String gender = editTextGender.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            editTextName.setError(getString(R.string.error_field_required));
            focusView = editTextName;
            cancel = true;
        } else if (!isNameValid(name)) {
            editTextName.setError(getString(R.string.error_invalid_name));
            focusView = editTextName;
            cancel = true;
        }

        if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)) {
            btnShowLocation.setError(getString(R.string.error_field_required));
            focusView = btnShowLocation;
            cancel = true;
        }

        if (cityname.equals("Select your city")) {
            Toast.makeText(getActivity(), "Please select your city" , Toast.LENGTH_SHORT).show();
            focusView = city;
            cancel = true;
        }
        if (statename.equals("Select your state")) {
            Toast.makeText(getActivity(), "Please select your state" , Toast.LENGTH_SHORT).show();
            focusView = state;
            cancel = true;
        }
        if (sport.equals("Select your sport")) {
            Toast.makeText(getActivity(), "Please select your sport" , Toast.LENGTH_SHORT).show();
            focusView = spinnersport;
            cancel = true;
        }

        if (TextUtils.isEmpty(gender)) {
            editTextGender.setError(getString(R.string.error_field_required));
            focusView = editTextGender;
            cancel = true;
        }
        else if (!isGenderValid(gender)) {
            editTextGender.setError(getString(R.string.error_invalid_gender));
            focusView = editTextGender;
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

        if (TextUtils.isEmpty(pincode)) {
            editTextPincode.setError(getString(R.string.error_field_required));
            focusView = editTextPincode;
            cancel = true;
        }

        if (TextUtils.isEmpty(skypeid)) {
            editTextSkypeID.setError(getString(R.string.error_field_required));
            focusView = editTextSkypeID;
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
                if (editTextImage.getText().equals("Update profile to take effect")) {
                    uploadMultipart();
                }
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                //((PlayerMainActivity)getActivity()).playerid ="";
                //((PlayerMainActivity)getActivity()).viewAllFaculties();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_EMP_ID,id);
                hashMap.put(Config.KEY_EMP_NAME,name);
                hashMap.put(Config.KEY_EMP_STATE,statename);
                hashMap.put(Config.KEY_EMP_CONTACT,contact);
                hashMap.put(Config.KEY_EMP_EMAIL,email);
                hashMap.put(Config.KEY_EMP_PASSWD,passwd);
                hashMap.put(Config.KEY_EMP_GENDER,gender);
                hashMap.put(Config.KEY_EMP_CITY,cityname);
                hashMap.put(Config.KEY_EMP_SKYPEID,skypeid);
                hashMap.put(Config.KEY_EMP_LATITUDE,latitude);
                hashMap.put(Config.KEY_EMP_LONGITUDE,longitude);
                hashMap.put(Config.KEY_EMP_PINCODE,pincode);
                hashMap.put(Config.KEY_EMP_SPORT,sport);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_PLAYER,hashMap);

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

    private boolean isGenderValid(String gender) {
        //TODO: Replace this with your own logic
        return gender.equals("male") || gender.equals("female") || gender.equals("Female") || gender.equals("Male");
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
        if (v == buttonChoose) {
            showFileChooser();
        }
        if(v == buttonChat){
            Toast.makeText(getActivity(), "Fetching the skype id" , Toast.LENGTH_SHORT).show();
            String skypeName = editTextSkypeID.getText().toString().trim();
            String mySkypeUri = "skype:"+skypeName+"?chat";
            SkypeUri(getActivity(), mySkypeUri);
        }
    }

    // Make sure you are sign in skype application if you not then you need to sign in

    public void SkypeUri(Context myContext, String mySkypeUri) {

        // Make sure the Skype for Android client is installed.
        if (!isSkypeClientInstalled(myContext)) {
            goToMarket(myContext);
            return;
        }
        Uri skypeUri = Uri.parse(mySkypeUri);
        Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);
        myIntent.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myContext.startActivity(myIntent);

        return;
    }

    /**
     * Determine whether the Skype for Android client is installed on this device.
     */
    public boolean isSkypeClientInstalled(Context myContext) {
        PackageManager myPackageMgr = myContext.getPackageManager();
        try {
            myPackageMgr.getPackageInfo("com.skype.raider", PackageManager.GET_ACTIVITIES);
        }
        catch (PackageManager.NameNotFoundException e) {
            return (false);
        }
        return (true);
    }

    /**
     * Install the Skype client through the market: URI scheme.
     */

    public void goToMarket(Context myContext) {
        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myContext.startActivity(myIntent);
        return;
    }
    // Make sure you are sign in skype application if you not then you need to sign in

    /*
    * This is the method responsible for image upload
    * We need the full image path and the name for the image in this method
    * */
    public void uploadMultipart() {
        //getting name for the image
        String name = editTextId.getText().toString().trim();

        //Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(getActivity(), uploadId, Constants.UPLOAD_URL_UPDATE)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        editTextImage.setText("Update profile to take effect");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }

}

